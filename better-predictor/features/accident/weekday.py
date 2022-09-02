import numpy as np

from features.range import Range
from features.base_feature import Feature

class Weekday(Feature):
    """
    The weekday for a day
    """
    WEEKDAY_MAX = 6

    def __init__(self):
        super().__init__(
            range_from=Range.between(0, Weekday.WEEKDAY_MAX),
            range_to=Range.between(0, 1),
            name="Risk index"
        )
        
    @classmethod
    def create_for(cls, weekday: np.float64) -> "Weekday":
        feature = Weekday()
        feature.set_value(weekday) 
        return feature
