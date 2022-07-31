from dataclasses import dataclass
from logging import log, ERROR
from domain.geolocation import GeoLocation
from shapely.geometry.linestring import LineString


@dataclass
class Street:
    """A street / A drectional edge"""
    externalId: str
    directionalId: str
    name: str
    location: GeoLocation
    length: float
    source_intersection_id: str
    destination_intersection_id: str


def estimate_geolocation(edge_object: dict, source_node: dict, dest_node: dict) -> GeoLocation:

    long: float = 0.0
    lat: float = 0.0

    try:
        if isinstance(edge_object['geometry'], LineString):
            long = (source_node['lon'] + dest_node['lon']) / 2.0
            lat = (source_node['lat'] + dest_node['lat']) / 2.0
    except KeyError:
        log(ERROR, f'Object does not have required keys: {edge_object}')    
        log(ERROR, f'Object does not have required keys: {source_node}')    
        log(ERROR, f'Object does not have required keys: {dest_node}')    
        exit()

    return GeoLocation(
        longitude=long,
        latitude=lat
    )
