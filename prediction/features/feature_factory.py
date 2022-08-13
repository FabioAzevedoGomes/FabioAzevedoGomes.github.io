from typing import Tuple
import numpy as np

from remote_requests import get_bounding_box, get_accidents_for_date, get_weather_for_date
from .geostructures import BoundingBox, Coordinates
from .feature_wrapper import RiskFeatureWrapper, WeatherFeatureWrapper
from .features import RiskFeaturesEnum, WeatherFeaturesEnum, CoordinateFeaturesEnum


class FeatureFactory:
    """
    A factory for creating feature arrays for given dates
    """
    REGION_WIDTH_KM = REGION_HEIGTH_KM = .5
    region_bounding_box: BoundingBox
    region_width_cells: int
    region_height_cells: int
    total_regions: int
    total_region_features: int

    def __init__(self):
        self.region_bounding_box = BoundingBox.from_points(get_bounding_box())
        self.region_width_cells = round(
            self.region_bounding_box.width / FeatureFactory.REGION_WIDTH_KM
        )
        self.region_height_cells = round(
            self.region_bounding_box.height / FeatureFactory.REGION_HEIGTH_KM
        )
        self.total_regions = self.region_height_cells * self.region_width_cells
        self.total_region_features = \
            len(RiskFeaturesEnum) + \
            len(WeatherFeaturesEnum) + \
            len(CoordinateFeaturesEnum)

    def get_location_cell(self, location: Coordinates) -> Tuple[int, int]:
        x, y = self.region_bounding_box.get_cell_given_size(
            location,
            FeatureFactory.REGION_WIDTH_KM,
            FeatureFactory.REGION_HEIGTH_KM
        )
        return (
            min(self.region_width_cells - 1, x),
            min(self.region_height_cells - 1, y)
        )

    def get_weather_features_for_date(self, date: str) -> np.ndarray:
        features = WeatherFeatureWrapper.empty()

        for weather in get_weather_for_date(date):
            features.add_weather(weather)

        return features.normalize()

    def get_risk_features_for_date(self, date: str) -> np.ndarray:
        """
        Returns a 3D matrix (width, height, features) of the region risk features for that date
        """
        feature_matrix = np.zeros(
            shape=(
                self.region_width_cells,
                self.region_height_cells,
                len(RiskFeaturesEnum)
            ),
            dtype=float
        )

        wrapper_buffer = [[RiskFeatureWrapper.empty()] *
                          self.region_height_cells] * self.region_width_cells

        for accident in get_accidents_for_date(date):
            accident_location = Coordinates.from_dict(
                accident['address']['location']
            )

            x, y = self.get_location_cell(accident_location)
            wrapper_buffer[x][y].add_accident(accident)

        for x in range(self.region_width_cells):
            for y in range(self.region_height_cells):
                features = wrapper_buffer[x][y].normalize()
                feature_matrix[x, y, :] = features

        return feature_matrix

    def get_geo_features(self) -> np.ndarray:
        centers = np.zeros(
            shape=(
                self.region_width_cells,
                self.region_height_cells,
                len(CoordinateFeaturesEnum)
            ),
            dtype=float
        )

        for x in range(self.region_width_cells):
            for y in range(self.region_height_cells):
                centers[x][y][:] = self.get_region_center(x, y)

        return centers

    def get_region_center(self, region_x: int, region_y: int) -> np.ndarray:
        return Coordinates(
            latitude=(
                ((region_x + 0.5) / self.region_width_cells) *
                (self.region_bounding_box.north_east.latitude -
                 self.region_bounding_box.south_west.latitude)
            ) + self.region_bounding_box.south_west.latitude,
            longitude=(
                ((region_y + 0.5) / self.region_height_cells) *
                (self.region_bounding_box.north_east.longitude -
                 self.region_bounding_box.south_west.longitude)
            ) + self.region_bounding_box.south_west.longitude
        ).to_array()

    def get_number_of_regions(self) -> float:
        return self.region_height_cells * self.region_width_cells

    def get_regions(self):
        return [(x, y) for x in range(self.region_width_cells) for y in range(self.region_height_cells)]
