import numpy as np
from typing import List
import dateutil.parser

from dataset_objects.bbox import BoundingBox
from dataset_objects.coordinates import Coordinates
from features.accident.weekday import Weekday
from remote.remote import Remote
from features.accident.risk_index import RiskIndex
from features.weather.precipitation import Precipitation
from features.weather.visibility import Visibility

class Factory:
    """
    A factory for creating features for a date and region
    """

    @classmethod
    def get_location_cell(cls, location: Coordinates, domain: BoundingBox, cells_width: int, cells_height: int, cell_size: np.float64):
        x, y = domain.get_cell_given_size(
            location,
            cell_size,
            cell_size
        )
        return (
            min(cells_width - 1, x),
            min(cells_height - 1, y)
        )

    @classmethod
    def get_risk_features(cls,
                          date: str,
                          regions: List[List[BoundingBox]],
                          domain: BoundingBox,
                          cells_width: int,
                          cells_height: int,
                          num_features: int,
                          cell_size: np.float64
                         ) -> np.ndarray:

        feature_matrix = np.zeros(
            shape=(
                cells_width,
                cells_height,
                num_features
            ),
            dtype=np.float64
        )

        weekday = dateutil.parser.isoparse(date).weekday()
        accidents_list_per_region = [[[]  for y in range(cells_height)] for x in range(cells_width)]

        total_acc = 0

        for accident in Remote.get_accidents_for_date(date):
            accident_location = Coordinates.from_dict(
                accident['address']['location']
            )
            total_acc += 1
            x, y = Factory.get_location_cell(accident_location, domain, cells_width, cells_height, cell_size)
            accidents_list_per_region[x][y].append(accident)

        for x in range(cells_width):
            for y in range(cells_height):
                feature_matrix[x][y][0] = RiskIndex.create_for(accidents_list_per_region[x][y]).normalize()
                feature_matrix[x][y][1] = Weekday.create_for(weekday).normalize()

        return feature_matrix, total_acc

    @classmethod
    def get_weather_features(cls, date: str):
        features = np.zeros(shape=(2), dtype=np.float64)

        weather_list = Remote.get_weather_for_date(date)

        features[0] = Visibility.create_for(weather_list).normalize()
        features[1] = Precipitation.create_for(weather_list).normalize()

        return features
