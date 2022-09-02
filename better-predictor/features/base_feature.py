from abc import ABC
import numpy as np

from .range import Range

class Feature(ABC):
    """
    A generic numerical feature
    """

    name: str
    value: np.float64
    original_range: Range
    normalized_range: Range

    def __init__(self, range_from: Range, range_to: Range, name: str):
        self.value = 0.0
        self.original_range = range_from
        self.normalized_range = range_to
        self.name = name

    @classmethod
    def within(cls, min_value: np.float64, max_value: np.float64, name: str = "Feature") -> "Feature":
        return Feature(range_from = Range(min_value, max_value), range_to=Range(0, 1), name=name)

    def set_value(self, new_value: np.float64):
        self.value = max(min(new_value, self.original_range.get_max()),  self.original_range.get_min())

    def normalize_to(self, range: Range) -> np.float64:
        return ((self.value - self.original_range.get_min()) / (self.original_range.get_max() - self.original_range.get_min())) * (range.get_max() - range.get_min())

    def normalize(self) -> np.float64:
        return self.normalize_to(self.normalized_range)

    def to_string(self):
        return (
            f'{self.name}:\n'
            f'Original range: {self.original_range.to_string()}\n'
            f'Normalized range: {self.normalized_range.to_string()}\n'
            f'Value: {self.value}\n'
            f'Normalized value: {self.normalize()}'
        )
