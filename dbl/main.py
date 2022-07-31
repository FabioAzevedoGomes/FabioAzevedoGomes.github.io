from logging import log, ERROR, INFO
import requests
import dataclasses
import enum
import sys
from accident_loader import get_accident_list
from climate_loader import get_climate_list
from map_loader import get_streets_and_intersections

HOST = 'localhost'
PORT = 8080
ACCIDENTS_PATH = 'accidents/create'
CLIMATE_PATH = 'climate/create'
STREET_PATH = 'map/street/create'
INTERSECTION_PATH = 'map/intersection/create'


def _enum_serializer(data):

    def convert_value(obj):
        if isinstance(obj, enum.Enum):
            return obj.name
        return obj

    return dict((k, convert_value(v)) for k, v in data)


def _send_object_list(object_list: list, url: str):

    for object in object_list:
        try:
            resp = requests.post(
                url,
                json=dataclasses.asdict(
                    object, dict_factory=_enum_serializer),
                headers={'Content-Type': 'application/json'}
            )
        except requests.exceptions.ConnectionError:
            log(ERROR, f'Unable to establish connection to {url}')
            raise RuntimeError(f'Server refused connection')

        if not resp:
            raise RuntimeError(f'Server returned {resp.status_code} for {dataclasses.asdict(object, dict_factory=_enum_serializer)}')


def main(with_accidents: bool, with_climate: bool, with_map: bool):
    base_url = f'http://{HOST}:{PORT}/'

    if (with_accidents):
        _send_object_list(
            get_accident_list('accidents_clean.csv'),
            base_url + ACCIDENTS_PATH
        )

    if (with_climate):
        _send_object_list(
            get_climate_list('climate_clean.csv'),
            base_url + CLIMATE_PATH
        )

    if (with_map):
        streets, intersections = get_streets_and_intersections()
        _send_object_list(
            intersections,
            base_url + INTERSECTION_PATH
        )

        _send_object_list(
            streets,
            base_url + STREET_PATH
        )


if __name__ == '__main__':
    with_accidents = with_climate = with_map = False

    if sys.argv[1] and sys.argv[1] == '--accidents':
        with_accidents = True
    elif sys.argv[1] and sys.argv[1] == '--climate':
        with_climate = True
    elif sys.argv[1] and sys.argv[1] == '--map':
        with_map = True

    main(with_accidents, with_climate, with_map)
