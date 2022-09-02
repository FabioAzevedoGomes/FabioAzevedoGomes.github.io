from typing import List

from features.base_feature import Feature
from features.range import Range

class Visibility(Feature):
    """
    The precipitation for a day
    """
    
    VISIBILITY_MAX = 1
    
    def __init__(self):
        super().__init__(
            range_from=Range.between(0, Visibility.VISIBILITY_MAX),
            range_to=Range.between(0, 1),
            name="Visibility"
        )
        
    @classmethod
    def create_for(cls, weather_list: List[dict]) -> "Visibility":
        feature = Visibility()
        if len(weather_list) > 0:
            feature.set_value(sum([weather['visibility'] for weather in weather_list]) / len(weather_list))
        return feature
