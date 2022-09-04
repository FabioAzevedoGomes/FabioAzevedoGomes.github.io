from typing import Tuple
import numpy as np
from typing import List
import dateutil.parser

from dataset_objects.bbox import BoundingBox
from dataset_objects.coordinates import Coordinates
from features.accident.weekday import Weekday
from features.geographical.latitude import Latitude
from features.geographical.longitude import Longitude
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
                         ) -> Tuple[np.ndarray, np.ndarray]:

        # [visibility, precipitation, weekday, lat, lon]
        feature_matrix = np.zeros(
            shape=(
                cells_width,
                cells_height,
                num_features
            ),
            dtype=np.float64
        )
        output_matrix = np.zeros(
            shape=(
                cells_width,
                cells_height,
            ),
            dtype=np.float64
        )

        weather_list = Remote.get_weather_for_date(date)

        date_visibility = Visibility.create_for(weather_list).normalize()
        date_precipitation = Precipitation.create_for(weather_list).normalize()
        date_weekday = Weekday.create_for(dateutil.parser.isoparse(date).weekday()).normalize()
        
        accidents_list_per_region = [[[]  for y in range(cells_height)] for x in range(cells_width)]

        for accident in Remote.get_accidents_for_date(date):
            accident_location = Coordinates.from_dict(
                accident['address']['location']
            )

            x, y = Factory.get_location_cell(accident_location, domain, cells_width, cells_height, cell_size)
            accidents_list_per_region[x][y].append(accident)

        for x in range(cells_width):
            for y in range(cells_height):
                # Features
                feature_matrix[x][y][0] = date_visibility
                feature_matrix[x][y][1] = date_precipitation
                feature_matrix[x][y][2] = Weekday.create_for(date_weekday).normalize()
                feature_matrix[x][y][3] = Latitude.create_for(regions[x][y].center, domain).normalize()
                feature_matrix[x][y][4] = Longitude.create_for(regions[x][y].center, domain).normalize()

                # Expected output
                output_matrix[x][y] = RiskIndex.create_for(accidents_list_per_region[x][y]).normalize()

        return feature_matrix, output_matrix
