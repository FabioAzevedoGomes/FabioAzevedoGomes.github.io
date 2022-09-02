from dataclasses import dataclass
from typing import Dict, Tuple
from math import floor
import inspect
import numpy as np

from .coordinates import Coordinates

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
    width: np.float64
    height: np.float64
    x: int
    y: int

    def __init__(self, south_west: Coordinates, north_east: Coordinates, x: int, y: int):
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

        self.x = x
        self.y = y

    @staticmethod
    def from_points(point_pair: Dict[str, Dict[str, np.float64]]) -> "BoundingBox":
        return BoundingBox(
            south_west=Coordinates.from_dict(point_pair['first']),
            north_east=Coordinates.from_dict(point_pair['second']),
            x=-1,
            y=-1
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

    @classmethod
    def from_dict(cls, env):      
        return cls(**{
            k: v for k, v in env.items() 
            if k in inspect.signature(cls).parameters
        })
