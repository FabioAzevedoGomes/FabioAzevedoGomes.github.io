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
saved_locations = 0
corrected_locations = 0
with open("data/accidents_altered.csv", newline='', encoding="utf8") as csvfile:
    with open("backup.txt", "r") as backup:
        data = list(csv.reader(csvfile, delimiter=';'))

        for line in backup:
            tokens = line.split(",")
            street1 = tokens[0].strip()
            street2 = tokens[1].strip()
            long = tokens[2].strip()
            lat = tokens[3].strip().replace('\n', '')
            print(tokens)
            add_long_lat_to_map(street1, street2, long, lat)
            saved_locations += 1

        print(f'Loaded {saved_locations} locations')

        for record in data[1:]:
            row = dict(zip(data[0], record))
            if (row['lat'] == '0.0' or row['long'] == '0.0'):
                lat_long_pair = get_long_lat_from_map(
                    row['street1'], row['street2'])
                if (lat_long_pair):
                    lat = lat_long_pair[0].strip()
                    long = lat_long_pair[1].strip()
                    row['lat'] = lat
                    row['long'] = long
                    corrected_locations += 1

            updated_dictionary.append(row)

print(f'Corrected {corrected_locations} rows')

# Write output
headers = ['id', 'date', 'weekday', 'time', 'type', 'long', 'lat', 'street1', 'street2', 'region', 'deaths',
           'light_injuries', 'serious_injuries', 'total_cars', 'total_buses', 'total_motorcycles', 'total_trucks', 'total_other']

accidents_out_file = open("data/accidents_corrected.csv", "w", newline='')
data = csv.DictWriter(accidents_out_file, delimiter=';', fieldnames=headers)
data.writerow(dict((heads, heads) for heads in headers))
data.writerows(updated_dictionary)
accidents_out_file.close()
