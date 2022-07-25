from domain.geolocation import GeoLocation
from domain.region_type import RegionType
from dataclasses import dataclass


@dataclass
class Address:
    """An address with street, region and coords"""
    street1: str
    street2: str
    region: RegionType
    location: GeoLocation
