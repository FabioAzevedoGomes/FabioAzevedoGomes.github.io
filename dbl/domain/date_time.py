from dataclasses import dataclass


ORIGINAL_DATE_TIME_FORMAT = '%Y-%m-%d %H:%M:%S'
ORIGINAL_TIME_FORMAT_ACCIDENTS = '%H:%M:%S.%f'
ORIGINAL_TIME_FORMAT_CLIMATE = '%H%M'
ORIGINAL_DATE_FORMAT = '%Y-%m-%d'
OUTPUT_DATE_FORMAT = '%d/%m/%Y'
OUTPUT_TIME_FORMAT = '%H:%M'


@dataclass
class DateTime:
    """A date and time"""
    date: str
    weekday: int  # 0 = Monday
    hour: str
    time_of_day: int
