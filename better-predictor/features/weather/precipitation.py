from typing import List

from features.base_feature import Feature
from features.range import Range

class Precipitation(Feature):
    """
    The precipitation for a day
    """
    
    PRECIPITATION_MAX = 10
    
    def __init__(self):
        super().__init__(
            range_from=Range.between(0, Precipitation.PRECIPITATION_MAX),
            range_to=Range.between(0, 1),
            name="Precipitation"
        )
        
    @classmethod
    def create_for(cls, weather_list: List[dict]) -> "Precipitation":
        feature = Precipitation()
        if len(weather_list) > 0:
            feature.set_value(sum([weather['precipitation_mm'] for weather in weather_list]) / len(weather_list))
        return feature
