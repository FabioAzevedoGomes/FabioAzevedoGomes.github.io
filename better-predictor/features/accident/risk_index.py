from typing import List
import numpy as np

from features.range import Range
from features.base_feature import Feature

class RiskIndex(Feature):
    """
    The risk index of a region during a day
    """
    RISK_INDEX_MAX = 1
    MAX_DEATHS = 1
    MAX_SERIOUS = 2
    MAX_LIGHT = 3
    
    def __init__(self):
        super().__init__(
            range_from=Range.between(0, RiskIndex.RISK_INDEX_MAX),
            range_to=Range.between(0, 1),
            name="Risk index"
        )
        
    @classmethod
    def create_for(cls, accident_list: List[dict]) -> "RiskIndex":
        feature = RiskIndex()
        feature.set_value(len(accident_list))
        return feature

    def normalize(self) -> np.float64:
        return self.normalize_to(self.normalized_range)
