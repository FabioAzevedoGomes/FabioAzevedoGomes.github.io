from typing import List
from dataclasses import dataclass
from domain.geolocation import GeoLocation


@dataclass
class Intersection:
    """An intersection of streets / A node"""
    externalId: str
    location: GeoLocation
    connected_street_ids: List[str]
    incoming_street_ids: List[str]
    outgoing_street_ids: List[str]
