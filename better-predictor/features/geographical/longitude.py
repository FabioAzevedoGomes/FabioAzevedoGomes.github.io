import numpy as np

from features.range import Range
from features.base_feature import Feature
from dataset_objects.coordinates import Coordinates
from dataset_objects.bbox import BoundingBox

class Longitude(Feature):
    """
    The center longitude of a region
    """

    def __init__(self, lon_min: np.float64, lon_max: np.float64):
        super().__init__(
            range_from=Range.between(lon_min, lon_max),
            range_to=Range.between(0, 1),
            name="Longitude"
        )
        
    @classmethod
    def create_for(cls, center: Coordinates, bound: BoundingBox) -> "Longitude":
        feature = Longitude(bound.south_west.longitude, bound.north_east.longitude)
        feature.set_value(center.longitude)
        return feature
