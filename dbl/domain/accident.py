from dataclasses import dataclass
from domain.address import Address
from domain.date_time import DateTime
from domain.fatality import Fatality
from domain.vehicle_type import VehicleType
from domain.accident_type import AccidentType
from domain.domain_object import DomainObject


@dataclass
class Accident(DomainObject):
    """Accident data"""
    externalId: str
    address: Address
    date_time: DateTime
    fatality: Fatality
    involved_entities: list(VehicleType)
    type: AccidentType
