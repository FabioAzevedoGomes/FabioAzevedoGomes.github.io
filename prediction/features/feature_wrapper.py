from abc import abstractmethod
import numpy as np

from .features import FeaturesEnum, WeatherFeaturesEnum, RiskFeaturesEnum


class FeatureWrapper:
    """
    A generic feature wrapper
    """
    _feature_array: np.ndarray

    def __init__(self, feature_array: np.ndarray):
        self._feature_array = feature_array

    def set_feature(self, feature: FeaturesEnum, value: float):
        self._feature_array[feature.value] = value

    def get_feature(self, feature: FeaturesEnum):
        return self._feature_array[feature.value]

    @abstractmethod
    def normalize(self) -> np.ndarray:
        raise NotImplementedError()


class WeatherFeatureWrapper(FeatureWrapper):
    """
    A feature wrapper for weather features
    """
    HEAVY_RAIN_PRECIPITATION_THRESHOLD_MM = 10.0
    weather_count: int

    def __init__(self, feature_array: np.ndarray):
        super().__init__(feature_array)
        self.weather_count = 0

    @staticmethod
    def empty():
        return WeatherFeatureWrapper(np.zeros(shape=len(WeatherFeaturesEnum), dtype=float))

    def add_weather(self, weather: dict):
        self.weather_count += 1
        self.set_feature(
            WeatherFeaturesEnum.AVG_HUMIDITY,
            self.get_feature(WeatherFeaturesEnum.AVG_HUMIDITY) +
            weather['relative_humidity_percentage']
        )
        self.set_feature(
            WeatherFeaturesEnum.AVG_PRECIPITATION,
            self.get_feature(WeatherFeaturesEnum.AVG_PRECIPITATION) +
            weather['precipitation_mm']
        )
        self.set_feature(
            WeatherFeaturesEnum.AVG_VISIBILITY,
            self.get_feature(WeatherFeaturesEnum.AVG_VISIBILITY) +
            weather['visibility']
        )

    def normalize(self) -> np.ndarray:
        if self.weather_count != 0:
            self.set_feature(
                WeatherFeaturesEnum.AVG_HUMIDITY,
                self.get_feature(WeatherFeaturesEnum.AVG_HUMIDITY) /
                self.weather_count
            )
            self.set_feature(
                WeatherFeaturesEnum.AVG_PRECIPITATION,
                min(1.0, (self.get_feature(WeatherFeaturesEnum.AVG_PRECIPITATION) /
                          self.weather_count) / WeatherFeatureWrapper.HEAVY_RAIN_PRECIPITATION_THRESHOLD_MM)
            )
            self.set_feature(
                WeatherFeaturesEnum.AVG_VISIBILITY,
                self.get_feature(WeatherFeaturesEnum.AVG_VISIBILITY) /
                self.weather_count
            )

        return self._feature_array


class RiskFeatureWrapper(FeatureWrapper):
    """
    A feature wrapper for risk features
    """
    TIME_OF_DAY_MAX = 82800000
    MAX_ACCIDENTS_PER_REGION = 3
    DEATHS_MAX = 3
    SERIOUS_INJURIES_MAX = 5
    LIGHT_INJURIES_MAX = 25
    accident_count: int

    def __init__(self, feature_array: np.ndarray):
        super().__init__(feature_array)
        self.accident_count = 0

    @staticmethod
    def empty():
        return RiskFeatureWrapper(np.zeros(shape=len(RiskFeaturesEnum), dtype=float))

    def get_risk_index(self):
        return (
            3 * self.get_feature(RiskFeaturesEnum.TOTAL_DEATHS) +
            2 * self.get_feature(RiskFeaturesEnum.TOTAL_SERIOUS_INJ) +
            1 * self.get_feature(RiskFeaturesEnum.TOTAL_LIGHT_INJ)
        ) / 6

    def add_accident(self, accident: dict):
        self.accident_count += 1
        self.set_feature(
            RiskFeaturesEnum.TOTAL_DEATHS,
            self.get_feature(RiskFeaturesEnum.TOTAL_DEATHS) +
            accident['fatality']['deaths']
        )
        self.set_feature(
            RiskFeaturesEnum.TOTAL_SERIOUS_INJ,
            self.get_feature(RiskFeaturesEnum.TOTAL_SERIOUS_INJ) +
            accident['fatality']['serious_injuries']
        )
        self.set_feature(
            RiskFeaturesEnum.TOTAL_LIGHT_INJ,
            self.get_feature(RiskFeaturesEnum.TOTAL_LIGHT_INJ) +
            accident['fatality']['light_injuries']
        )
        self.set_feature(
            RiskFeaturesEnum.AVG_TIME,
            self.get_feature(RiskFeaturesEnum.AVG_TIME) +
            accident['date_time']['time_of_day']
        )

    def normalize(self) -> np.ndarray:
        if self.accident_count != 0:
            self.set_feature(
                RiskFeaturesEnum.TOTAL_ACCIDENTS,
                min(1.0, self.accident_count /
                    RiskFeatureWrapper.MAX_ACCIDENTS_PER_REGION)
            )
            self.set_feature(
                RiskFeaturesEnum.TOTAL_DEATHS,
                min(1.0, (self.get_feature(RiskFeaturesEnum.TOTAL_DEATHS) /
                    self.accident_count) / RiskFeatureWrapper.DEATHS_MAX)
            )
            self.set_feature(
                RiskFeaturesEnum.TOTAL_SERIOUS_INJ,
                min(1.0, (self.get_feature(RiskFeaturesEnum.TOTAL_SERIOUS_INJ) /
                    self.accident_count) / RiskFeatureWrapper.SERIOUS_INJURIES_MAX)
            )
            self.set_feature(
                RiskFeaturesEnum.TOTAL_LIGHT_INJ,
                min(1.0, (self.get_feature(RiskFeaturesEnum.TOTAL_LIGHT_INJ) /
                    self.accident_count) / RiskFeatureWrapper.LIGHT_INJURIES_MAX)
            )
            self.set_feature(
                RiskFeaturesEnum.AVG_RISK_LEVEL,
                min(1.0, self.get_risk_index())
            )
            self.set_feature(
                RiskFeaturesEnum.AVG_TIME,
                (self.get_feature(RiskFeaturesEnum.AVG_TIME)
                 / self.accident_count) / RiskFeatureWrapper.TIME_OF_DAY_MAX
            )

        return self._feature_array
