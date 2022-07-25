from logging import log, ERROR, INFO
import requests
import dataclasses
import enum
import json
import sys
from accident_loader import get_accident_list
from climate_loader import get_climate_list
from domain.domain_object import DomainObject

HOST = 'localhost'
PORT = 8080
ACCIDENTS_PATH = 'accidents/create'
CLIMATE_PATH = 'climate/create'


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
            raise RuntimeError(f'Server returned {resp.status_code}')


def main(with_accidents: bool, with_climate: bool):
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


if __name__ == '__main__':
    with_accidents = with_climate = False
    if sys.argv[1] and sys.argv[1] == '--accidents':
        with_accidents = True
    elif sys.argv[1] and sys.argv[1] == '--climate':
        with_climate = True

    main(with_accidents, with_climate)
