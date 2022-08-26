from typing import List
import numpy as np
from features.feature_wrapper import RiskFeatureWrapper, WeatherFeatureWrapper
from features.features import CoordinateFeaturesEnum, RiskFeaturesEnum
from remote_requests import get_accidents_for_date, get_weather_for_date
from .geostructures import BoundingBox, Coordinates

class Factory:
    """
    A feature factory
    """

    regions: List[List[BoundingBox]]
    domain: BoundingBox
    cell_size: float
    height_cells: int
    width_cells: int

    def __init__(self, regions: List[List[BoundingBox]], domain: BoundingBox, cell_size: float):
        self.regions = regions
        self.cell_size = cell_size
        self.domain = domain
        self.width_cells = len(regions)
        self.height_cells = len(regions[0])

    def get_risk_features(self, date: str):
        feature_matrix = np.zeros(
            shape=(
                self.width_cells,
                self.height_cells,
                len(RiskFeaturesEnum)
            ),
            dtype=float
        )
        wrapper_buffer = [[RiskFeatureWrapper.empty()] * self.height_cells] * self.width_cells

        for accident in get_accidents_for_date(date):
            accident_location = Coordinates.from_dict(
                accident['address']['location']
            )

            x, y = self.get_location_cell(accident_location)
            wrapper_buffer[x][y].add_accident(accident)

        for x in range(self.width_cells):
            for y in range(self.height_cells):
                features = wrapper_buffer[x][y].normalize()
                for z in range(len(features)):
                    feature_matrix[x][y][z] = features[z]

        return feature_matrix

    def get_weather_features(self, date: str):
        features = WeatherFeatureWrapper.empty()

        for weather in get_weather_for_date(date):
            features.add_weather(weather)

        return features.normalize()

    def get_geographical_features(self):
        centers = np.zeros(
            shape=(
                self.width_cells,
                self.height_cells,
                len(CoordinateFeaturesEnum)
            ),
            dtype=float
        )
        
        for x in range(self.width_cells):
            for y in range(self.height_cells):
                centers[x][y][0] = (self.regions[x][y].center.latitude - self.domain.north_east.latitude) \
                    / (self.domain.south_west.latitude - self.domain.north_east.latitude)
                centers[x][y][1] = (self.regions[x][y].center.longitude - self.domain.north_east.longitude) \
                    / (self.domain.south_west.longitude - self.domain.north_east.longitude)

        return centers

    def get_location_cell(self, location: Coordinates):
        x, y = self.domain.get_cell_given_size(
            location,
            self.cell_size,
            self.cell_size
        )
        return (
            min(self.width_cells - 1, x),
            min(self.height_cells - 1, y)
        )

    def summary(self):
        print(f'Total {self.width_cells * self.height_cells} regions')
        print(f'{self.width_cells} regions wide')
        print(f'{self.height_cells} regions tall')
