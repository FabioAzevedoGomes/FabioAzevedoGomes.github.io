import numpy as np

class Range:
    """
    A range of values
    """
    
    range_min: np.float64
    range_max: np.float64

    @classmethod
    def between(self, range_min: np.float64, range_max: np.float64) -> "Range":
        return Range(range_min, range_max)
    
    def __init__(self, range_min: np.float64, range_max: np.float64):
        self.range_min = range_min
        self.range_max = range_max

    def get_max(self):
        return self.range_max
    
    def get_min(self):
        return self.range_min

    def to_string(self):
        return f'[{self.range_min:.8f}, {self.range_max:.8f}]'
