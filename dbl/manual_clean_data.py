import csv

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
with open("data/accidents_altered.csv", newline='', encoding="utf8") as csvfile:
    data = list(csv.reader(csvfile, delimiter=';'))
    for record in data[1:]:
        row = dict(zip(data[0], record))

        if (row['lat'] == '0.0' or row['long'] == '0.0'):

            street1 = row['street1']
            street2 = row['street2']

            from_map = get_long_lat_from_map(street1, street2)
            if (from_map):
                row['long'] = from_map[0]
                row['lat'] = from_map[1]
                updated_dictionary.append(row)
                continue

            print(f'Street 1: \"{street1}\"')
            print(f'Street 2: \"{street2}\"')
            lat_long_str = input('Input lat/long: ')  # -30.026144, -51.148043
            lat_long_pair = lat_long_str.split(',')
            lat = lat_long_pair[0].strip()
            long = lat_long_pair[1].strip()

            print(f'\"{lat}\"')
            print(f'\"{long}\"')
            add_long_lat_to_map(street1, street2, long, lat)

            row['lat'] = lat
            row['long'] = long

            backup = open('backup.txt', "a")
            backup.write(f'{street1}, {street2}, {lat}, {long}\n')
            backup.close()

        updated_dictionary.append(row)

# Write output
headers = ['id', 'date', 'weekday', 'time', 'type', 'long', 'lat', 'street1', 'street2', 'region', 'deaths',
           'light_injuries', 'serious_injuries', 'total_cars', 'total_buses', 'total_motorcycles', 'total_trucks', 'total_other']

accidents_out_file = open("data/accidents_corrected.csv", "w", newline='')
data = csv.DictWriter(accidents_out_file, delimiter=';', fieldnames=headers)
data.writerow(dict((heads, heads) for heads in headers))
data.writerows(updated_dictionary)
accidents_out_file.close()
