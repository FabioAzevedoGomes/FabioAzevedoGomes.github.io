import csv
from logging import log, WARN
from domain.climate import Climate
from domain.date_time import DateTime
from domain.geolocation import GeoLocation


def _translate_climate(csv_record: dict, ignore_missing_data: bool=True) -> Climate:
    return Climate(
        date_time=DateTime(
            date=csv_record['date'],
            weekday=csv_record['weekday'],
            hour=csv_record['time'],
            time_of_day=int(csv_record['time'].split(':')[0]) * 3600000
        ),
        visibility=float(csv_record['visibility']),
        relative_humidity_percentage=float(csv_record['relative_humidity']),
        precipitation_mm=float(csv_record['precipitation']),
        wind_speed_ms=float(csv_record['wind_speed']),
        air_temp_celcius=float(csv_record['air_temp']),
        location=GeoLocation(
            longitude=float(csv_record['long']),
            latitude=float(csv_record['lat'])
        )
    )


def get_climate_list(climate_csv_filename: str) -> list:
    climate_list = []
    records_not_added = 0
    num_rows = 0

    with open(climate_csv_filename, newline='', encoding='utf-8') as csv_climate:
        data = list(csv.reader(csv_climate, delimiter=';'))
        for record in data[1:]:
            num_rows += 1
            print(f'Processing row {num_rows}')
            try:
                row = dict(zip(data[0], record))
                climate_list.append(_translate_climate(
                    row, ignore_missing_data=False))
            except ValueError:
                date = row['date']
                time = row['time']
                records_not_added += 1
                log(WARN, f'Record for {date} at {time} not added')

    log(WARN, f'{records_not_added} records not added')
    return climate_list
