from dataclasses import dataclass
from math import sin, cos, asin, sqrt, radians
from typing import Dict
import numpy as np
import inspect

@dataclass
class Coordinates:
    """
    Latitude and longitude coordinates
    """
    EARTH_RADIUS_KM = 6371.0

    latitude: np.float64
    longitude: np.float64

    @staticmethod
    def from_dict(coordinates: Dict[str, float]):
        return Coordinates(
            latitude=float(coordinates['latitude']),
            longitude=float(coordinates['longitude'])
        )

    @staticmethod
    def from_point(coordinates: "Coordinates"):
        return Coordinates(            
            latitude=float(coordinates.latitude),
            longitude=float(coordinates.longitude)
        )

    def distance_to(self, point: "Coordinates"):
        """
        Calculates the distance to another point using haversine formula
        """
        lon1 = self.longitude
        lat1 = self.latitude
        lon2 = point.longitude
        lat2 = point.latitude
        lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])

        dlon = lon2 - lon1
        dlat = lat2 - lat1
        a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
        c = 2 * asin(sqrt(a))

        return c * Coordinates.EARTH_RADIUS_KM

    def to_array(self) -> np.ndarray:
        return np.array([self.latitude, self.longitude])
    
    @classmethod
    def from_dict(cls, env):      
        return cls(**{
            k: v for k, v in env.items() 
            if k in inspect.signature(cls).parameters
        })
