from dataclasses import dataclass
from typing import Dict, Tuple
from math import floor, radians, cos, sin, asin, sqrt
import numpy as np


@dataclass
class Coordinates:
    """
    Latitude and longitude coordinates
    """
    EARTH_RADIUS_KM = 6371.0
    latitude: float
    longitude: float

    @staticmethod
    def from_dict(coordinates: Dict[str, float]):
        return Coordinates(
            latitude=float(coordinates['latitude']),
            longitude=float(coordinates['longitude'])
        )

    @staticmethod
    def from_point(coordinates: "Coordinates"):
        return Coordinates(            
            latitude=float(coordinates.latitude),
            longitude=float(coordinates.longitude)
        )

    def distance_to(self, point: "Coordinates"):
        """
        Calculates the distance to another point using haversine formula
        """
        lon1 = self.longitude
        lat1 = self.latitude
        lon2 = point.longitude
        lat2 = point.latitude
        lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])

        dlon = lon2 - lon1
        dlat = lat2 - lat1
        a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
        c = 2 * asin(sqrt(a))

        return c * Coordinates.EARTH_RADIUS_KM

    def to_array(self) -> np.ndarray:
        return np.array([self.latitude, self.longitude])


@dataclass
class BoundingBox:
    """
    Bounding box around a region
    """
    south_west: Coordinates
    south_east: Coordinates
    north_west: Coordinates
    north_east: Coordinates
    center: Coordinates
    width: float
    height: float

    def __init__(self, south_west: Coordinates, north_east: Coordinates):
        self.south_west = south_west
        self.north_east = north_east

        self.south_east = Coordinates.from_point(south_west)
        self.south_east.longitude = north_east.longitude

        self.north_west = Coordinates.from_point(north_east)
        self.north_west.longitude = south_west.longitude

        self.center = Coordinates(
            latitude=(south_west.latitude + north_east.latitude) / 2.0,
            longitude=(south_west.longitude + north_east.longitude) / 2.0
        )

        self.height = self.south_east.distance_to(self.north_east)
        self.width = self.south_east.distance_to(self.south_west)

    @staticmethod
    def from_points(point_pair: Dict[str, Dict[str, float]]):
        return BoundingBox(
            south_west=Coordinates.from_dict(point_pair['first']),
            north_east=Coordinates.from_dict(point_pair['second']),
        )

    def get_cell_given_size(self, point: Coordinates, region_width: float, region_height: float) -> Tuple[int, int]:
        horizontal_projection = Coordinates.from_point(point)
        vertical_projection = Coordinates.from_point(point)
        horizontal_projection.latitude = self.south_west.latitude
        vertical_projection.longitude = self.south_west.longitude

        distance_horizontal = self.south_west.distance_to(horizontal_projection)
        distance_vertical = self.south_west.distance_to(vertical_projection)

        return (
            floor(distance_horizontal / region_width),
            floor(distance_vertical / region_height)
        )

    def summary(self):
        nw = self.north_west.to_array()
        ne = self.north_east.to_array()
        sw = self.south_west.to_array()
        se = self.south_east.to_array()
        print(f'NW = {nw[0]:.8f}, {nw[1]:.8f} \tNE = {ne[0]:.8f}, {ne[1]:.8f}')
        print(f'SW = {sw[0]:.8f}, {sw[1]:.8f} \tSE = {se[0]:.8f}, {se[1]:.8f}')
        print(f'Width (Longitude):\t{self.width:.8f} km /\t {abs(nw[1] - ne[1]):.8f} deg')
        print(f'Height (Latitude):\t{self.height:.8f} km /\t {abs(sw[0] - nw[0]):.8f} deg')
