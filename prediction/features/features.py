from enum import Enum

class FeaturesEnum(Enum):
    """
    Generic feature enum
    """

class RiskFeaturesEnum(FeaturesEnum):
    """
    Region feature type to array position mapping
    """
    TOTAL_ACCIDENTS = 0
    TOTAL_DEATHS = 1
    TOTAL_SERIOUS_INJ = 2
    TOTAL_LIGHT_INJ = 3
    AVG_RISK_LEVEL = 4
    AVG_TIME = 5


class WeatherFeaturesEnum(FeaturesEnum):
    """
    Weather feature type to array position mapping
    """
    AVG_VISIBILITY = 0
    AVG_PRECIPITATION = 1
    AVG_HUMIDITY = 2

class CoordinateFeaturesEnum(FeaturesEnum):
    """
    Coordinate feature type to arrya position mapping
    """
    LATITUDE = 0
    LONGITUDE = 1
