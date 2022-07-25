from enum import Enum


class RegionType(Enum):
    """City regions"""
    NORTH = "North"
    SOUTH = "South"
    EAST = "East"
    CENTER = "Center"
    WEST = "West"
    NONE = "None"


def clean_region(input_region: str):

    input_region = input_region.strip().upper()

    if (input_region == 'NORTE'):
        return RegionType.NORTH.value.upper()
    elif(input_region == 'SUL'):
        return RegionType.SOUTH.value.upper()
    elif(input_region == 'LESTE'):
        return RegionType.EAST.value.upper()
    elif(input_region == 'CENTRO'):
        return RegionType.CENTER.value.upper()
    elif(input_region == 'OESTE'):
        return RegionType.WEST.value.upper()
    else:
        return ''
