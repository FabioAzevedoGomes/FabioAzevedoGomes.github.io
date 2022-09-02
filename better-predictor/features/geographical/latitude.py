import numpy as np

from features.range import Range
from features.base_feature import Feature
from dataset_objects.coordinates import Coordinates
from dataset_objects.bbox import BoundingBox

class Latitude(Feature):
    """
    The center latitude of a region
    """

    def __init__(self, lat_min: np.float64, lat_max: np.float64):
        super().__init__(
            range_from=Range.between(lat_min, lat_max),
            range_to=Range.between(0, 1),
            name="Latitude"
        )
        
    @classmethod
    def create_for(cls, center: Coordinates, bound: BoundingBox) -> "Latitude":
        feature = Latitude(bound.south_west.latitude, bound.north_east.latitude)
        feature.set_value(center.latitude)
        return feature
