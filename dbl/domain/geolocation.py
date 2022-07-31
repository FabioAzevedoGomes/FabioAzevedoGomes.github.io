from dataclasses import dataclass

MIN_VALID_LAT_POA = -32
MAX_VALID_LAT_POA = -28
MIN_VALID_LONG_POA = -53
MAX_VALID_LONG_POA = -49


@dataclass
class GeoLocation:
    """Coordinates"""
    longitude: float
    latitude: float


def clean_coord(input_val: str):

    if (not input_val or input_val == '0'):
        return '0.0'

    # Remove spaces
    input_val = input_val.strip()

    # Remove decimal places if any
    input_val = input_val.replace('.', '')

    # Convert to int
    int_val = int(input_val)

    # Take abs
    absolute_val = abs(int_val)

    # Put negative value
    input_val = -1 * absolute_val

    # Convert back to string
    output_string = str(input_val)

    # Put decimal place back
    return output_string[:3] + '.' + output_string[3:]


def is_a_valid_geolocation_for_poa(lat_str: str, long_str: str) -> bool:
    latitude = float(lat_str)
    longitude = float(long_str)

    return latitude >= MIN_VALID_LAT_POA and \
        latitude <= MAX_VALID_LAT_POA and \
        longitude >= MIN_VALID_LONG_POA and \
        longitude <= MAX_VALID_LONG_POA
