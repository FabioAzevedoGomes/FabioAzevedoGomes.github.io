import csv
from logging import log, WARN
from domain.accident import Accident
from domain.accident_type import AccidentType
from domain.address import Address
from domain.date_time import DateTime
from domain.fatality import Fatality
from domain.geolocation import GeoLocation
from domain.vehicle_type import VehicleType


def _get_entities_list(csv_record: dict) -> list:
    entities = []

    entities.extend([VehicleType.CAR.name for _ in range(
        int(csv_record['total_cars']))])
    entities.extend([VehicleType.BUS.name for _ in range(
        int(csv_record['total_buses']))])
    entities.extend([VehicleType.MOTORCYCLE.name for _ in range(
        int(csv_record['total_motorcycles']))])
    entities.extend([VehicleType.TRUCK.name for _ in range(
        int(csv_record['total_trucks']))])
    entities.extend([VehicleType.OTHER.name for _ in range(
        int(csv_record['total_other']))])

    return entities


def _has_lat_long(csv_record: dict) -> bool:
    return not (csv_record['long'] == '0.0' or csv_record['lat'] == '0.0')


def _translate_accident(csv_record: dict, ignore_missing_data=True) -> Accident:

    if (not _has_lat_long(csv_record) and not ignore_missing_data):
        raise ValueError()

    return Accident(
        externalId = 'A' + csv_record['id'],
        address=Address(
            street1=csv_record['street1'],
            street2=csv_record['street2'],
            region=csv_record['region'],
            location=GeoLocation(
                longitude=float(csv_record['long']),
                latitude=float(csv_record['lat'])
            )),
        date_time=DateTime(
            date=csv_record['date'],
            weekday=int(csv_record['weekday']),
            hour=csv_record['time'],
            time_of_day=int(csv_record['time'].split(':')[0]) * 3600000),
        fatality=Fatality(
            deaths=int(csv_record['deaths']),
            light_injuries=int(csv_record['light_injuries']),
            serious_injuries=int(csv_record['serious_injuries'])),
        involved_entities=_get_entities_list(csv_record),
        type=AccidentType[csv_record['type'].replace(' ', '_')]
    )


def get_accident_list(accident_csv_filename: str) -> dict:
    accident_list = []
    records_not_added = 0

    with open(accident_csv_filename, newline='', encoding="utf8") as csv_accidents:
        data = list(csv.reader(csv_accidents, delimiter=';'))
        for record in data[1:]:
            try:
                row = dict(zip(data[0], record))
                accident_list.append(_translate_accident(
                    row, ignore_missing_data=False))
            except ValueError:
                id = row['id']
                records_not_added += 1
                log(WARN, f'Record {id} not added (Missing data)')

    log(WARN, f'{records_not_added} records not added')
    return accident_list
