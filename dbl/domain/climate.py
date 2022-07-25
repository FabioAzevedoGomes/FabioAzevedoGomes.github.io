from dataclasses import dataclass
from domain.domain_object import DomainObject
from domain.geolocation import GeoLocation
from domain.date_time import DateTime


@dataclass
class Climate(DomainObject):
    """Meteorological data"""
    date_time: DateTime
    visibility: float
    relative_humidity_percentage: float
    precipitation_mm: float
    wind_speed_ms: float
    air_temp_celcius: float
    location: GeoLocation


def get_climate_float(input_float: str) -> float:
    input_float = input_float.strip()

    if (not input_float or input_float == 'null'):
        input_float = '0.0'

    return float(input_float)
