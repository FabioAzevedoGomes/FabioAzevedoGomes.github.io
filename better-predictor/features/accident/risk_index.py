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

        if len(accident_list) > 0:
            total_accidents: np.float64 = len(accident_list)
            total_light_injuries: np.float64 = sum([accident['fatality']['light_injuries'] for accident in accident_list])
            total_serious_injuries: np.float64 = sum([accident['fatality']['serious_injuries'] for accident in accident_list])
            total_deaths: np.float64 = sum([accident['fatality']['deaths'] for accident in accident_list])
            
            feature.set_value(max(
                min(((total_light_injuries / RiskIndex.MAX_LIGHT) / total_accidents), 1.0),
                min(((total_serious_injuries / RiskIndex.MAX_SERIOUS) / total_accidents), 1.0),
                min(((total_deaths / RiskIndex.MAX_DEATHS) / total_accidents), 1.0)
            ))
            #feature.set_value(RiskIndex.RISK_INDEX_MAX)

        #feature.set_value(len(accident_list))
        return feature
    
    def normalize(self) -> np.float64:
        return self.normalize_to(self.normalized_range)
