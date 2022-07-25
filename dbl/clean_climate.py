import csv
from datetime import datetime
from math import floor
from domain.date_time import ORIGINAL_DATE_FORMAT, ORIGINAL_TIME_FORMAT_CLIMATE, OUTPUT_DATE_FORMAT, OUTPUT_TIME_FORMAT
from domain.climate import get_climate_float

HEADER_SIZE = 11
HEADER_LINE = 10
MAX_VISIBILITY = 60


def get_date_obj(input_date: str):
    input_date = input_date.strip()

    if (not input_date):
        return datetime.fromordinal(1)
    else:
        return datetime.strptime(input_date, ORIGINAL_DATE_FORMAT)


def get_time_obj(input_time: str):
    input_time = input_time.strip()

    if (not input_time or input_time == '0'):
        return datetime.fromordinal(1)
    else:
        return datetime.strptime(input_time, ORIGINAL_TIME_FORMAT_CLIMATE)


def get_float_at(data: list, pos: int) -> float:
    input_lat = data[pos][0].strip()

    if (':' in input_lat):
        input_lat = input_lat.split(':')[1].strip()

    try:
        retval = float(input_lat)
    except ValueError:
        retval = 0

    return retval


def get_int_at(data: list, pos: int) -> int:
    return int(floor(get_float_at(data, pos)))


# Get data input
updated_dictionary = []
row_num = 0
with open("data/climate_alterable.csv", newline='', encoding="utf8") as csvfile:

    data = list(csv.reader(csvfile, delimiter=';'))
    station = get_int_at(data, 1)
    latitude = get_float_at(data, 2)
    longitude = get_float_at(data, 3)

    for record in data[HEADER_SIZE:]:
        row_num += 1

        row = dict(zip(data[HEADER_LINE], record))
        print(f'Processing row #{row_num}', end='\r')

        _date_obj = get_date_obj(row['Data Medicao'])
        _time_obj = get_time_obj(row['Hora Medicao'])

        date = _date_obj.strftime(OUTPUT_DATE_FORMAT)
        weekday = _date_obj.weekday()
        time = _time_obj.strftime(OUTPUT_TIME_FORMAT)

        visibility = get_climate_float(
            row['VISIBILIDADE, HORARIA(codigo)']) / MAX_VISIBILITY
        relative_humidity = get_climate_float(
            row['UMIDADE RELATIVA DO AR, HORARIA(%)']) / 100.0
        precipitation = get_climate_float(
            row['PRECIPITACAO TOTAL, HORARIO(mm)'])
        wind_speed = get_climate_float(row['VENTO, VELOCIDADE HORARIA(m/s)'])
        air_temp = get_climate_float(
            row['TEMPERATURA DO AR - BULBO SECO, HORARIA'])

        new_row = {
            'station': station,
            'long': longitude,
            'lat': latitude,
            'date': date,
            'weekday': weekday,
            'time': time,
            'visibility': visibility,
            'relative_humidity': relative_humidity,
            'precipitation': precipitation,
            'wind_speed': wind_speed,
            'air_temp': air_temp
        }

        updated_dictionary.append(new_row)

print('\n')
print(f'Processed {row_num} rows')

# Write output
headers = ['station', 'long', 'lat', 'date', 'weekday', 'time', 'visibility',
           'relative_humidity', 'precipitation', 'wind_speed', 'air_temp']

accidents_out_file = open("data/climate_altered.csv", "w", newline='')
data = csv.DictWriter(accidents_out_file, delimiter=';', fieldnames=headers)
data.writerow(dict((heads, heads) for heads in headers))
data.writerows(updated_dictionary)
accidents_out_file.close()
