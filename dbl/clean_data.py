import csv
from datetime import datetime
from domain.date_time import ORIGINAL_DATE_TIME_FORMAT, OUTPUT_DATE_FORMAT, ORIGINAL_TIME_FORMAT_ACCIDENTS, OUTPUT_TIME_FORMAT
from domain.accident_type import clean_accident_type
from domain.geolocation import clean_coord, is_a_valid_geolocation_for_poa
from domain.region_type import clean_region


def get_int_from_str(input_val: str):

    input_val = input_val.strip()

    if (not input_val):
        return 0
    else:
        input_val = input_val.replace('.', '')
        return int(input_val)


def get_date_obj(input_date: str):

    input_date = input_date.strip()

    if (not input_date):
        return datetime.fromordinal(1)
    else:
        return datetime.strptime(input_date, ORIGINAL_DATE_TIME_FORMAT)


def get_time_obj(input_time: str):

    input_time = input_time.strip()

    if (not input_time):
        return datetime.fromordinal(1)
    else:
        return datetime.strptime(input_time, ORIGINAL_TIME_FORMAT_ACCIDENTS)


# street1street2 -> (long, lat)
global_location_map = {}


def add_long_lat_to_map(street1: str, street2: str, long: str, lat: str):
    if (street1 + street2 in global_location_map):
        pass
    elif (street2 + street1 in global_location_map):
        pass
    else:
        global_location_map[street1 + street2] = (long, lat)


def get_long_lat_from_map(street1: str, street2: str):
    if (street1 + street2 in global_location_map):
        return global_location_map[street1 + street2]
    elif (street2 + street1 in global_location_map):
        return global_location_map.get(street2 + street1)
    else:
        return None


# Get data input
updated_dictionary = []
row_num = 0
invalid_locations = 0
corrected_locations = 0
with open("data/accidents_alterable.csv", newline='', encoding="utf8") as csvfile:
    data = list(csv.reader(csvfile, delimiter=';'))
    for record in data[1:]:
        row_num += 1
        row = dict(zip(data[0], record))
        print(f'Processing row #{row_num}', end='\r')

        _date_obj = get_date_obj(row['data'][:-1])
        _time_obj = get_time_obj(row['hora'][:-1])

        id = row['idacidente']
        date = _date_obj.strftime(OUTPUT_DATE_FORMAT)
        weekday = _date_obj.weekday()
        time = _time_obj.strftime(OUTPUT_TIME_FORMAT)
        type = clean_accident_type(row['tipo_acid'])

        longitude = clean_coord(row['longitude'])
        latitude = clean_coord(row['latitude'])
        street1 = row['log1'].strip().upper()
        street2 = row['log2'].strip().upper()
        region = clean_region(row['regiao'])

        deaths = get_int_from_str(row['mortes']) + \
            get_int_from_str(row['morte_post'])
        light_injuries = get_int_from_str(row['feridos'])
        serious_injuries = get_int_from_str(row['feridos_gr'])

        total_cars = get_int_from_str(row['auto']) + \
            get_int_from_str(row['taxi'])
        total_buses = get_int_from_str(row['lotacao']) + \
            get_int_from_str(row['onibus_urb']) + \
            get_int_from_str(row['onibus_met']) + \
            get_int_from_str(row['onibus_int'])
        total_motorcycles = get_int_from_str(row['moto'])
        total_trucks = get_int_from_str(row['caminhao'])
        total_other = get_int_from_str(row['bicicleta']) + \
            get_int_from_str(row['outro']) + \
            get_int_from_str(row['carroca']) + \
            get_int_from_str(row['patinete'])

        if (is_a_valid_geolocation_for_poa(latitude, longitude)):
            add_long_lat_to_map(street1, street2, longitude, latitude)
        else:
            invalid_locations += 1
            correction = get_long_lat_from_map(street1, street2)
            if (correction):
                longitude = correction[0]
                latitude = correction[1]
                corrected_locations += 1
            else:
                longitude = '0.0'
                latitude = '0.0'

        new_row = {
            'id': id,
            'date': date,
            'weekday': weekday,
            'time': time,
            'type': type,
            'long': longitude,
            'lat': latitude,
            'street1': street1,
            'street2': street2,
            'region': region,
            'deaths': deaths,
            'light_injuries': light_injuries,
            'serious_injuries': serious_injuries,
            'total_cars': total_cars,
            'total_buses': total_buses,
            'total_motorcycles': total_motorcycles,
            'total_trucks': total_trucks,
            'total_other': total_other
        }

        updated_dictionary.append(new_row)

print('\n')
print(f'Processed {row_num} rows')
print(f'{invalid_locations} rows had invald lat/long')
print(f'{corrected_locations} of these were corrected')
print('Attempting post-processing of locations...')
uncorrected = invalid_locations - corrected_locations
row_num = 0
corrected_locations = 0

for row in updated_dictionary:
    row_num += 1
    if (not is_a_valid_geolocation_for_poa(row['lat'], row['long'])):
        print(f'Processing row #{row_num}', end='\r')
        correction = get_long_lat_from_map(row['street1'], row['street2'])
        if (correction):
            row['long'] = correction[0]
            row['lat'] = correction[1]
            corrected_locations += 1

uncorrected -= corrected_locations
print('\n')
print(f'Corrected {corrected_locations} more rows from post-processing')
print(f'{uncorrected} rows need to be corrected manually')

# Write output
headers = ['id', 'date', 'weekday', 'time', 'type', 'long', 'lat', 'street1', 'street2', 'region', 'deaths',
           'light_injuries', 'serious_injuries', 'total_cars', 'total_buses', 'total_motorcycles', 'total_trucks', 'total_other']

accidents_out_file = open("data/accidents_altered.csv", "w", newline='')
data = csv.DictWriter(accidents_out_file, delimiter=';', fieldnames=headers)
data.writerow(dict((heads, heads) for heads in headers))
data.writerows(updated_dictionary)
accidents_out_file.close()
