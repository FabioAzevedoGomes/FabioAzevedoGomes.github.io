from dataclasses import dataclass


@dataclass
class Fatality:
    """Deaths and injuries"""
    deaths: int
    light_injuries: int
    serious_injuries: int
