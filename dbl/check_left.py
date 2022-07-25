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


row_num = 0
unique_rows = 0
singles = 0
with open("data/accidents_altered.csv", newline='', encoding="utf8") as csvfile:
    data = list(csv.reader(csvfile, delimiter=';'))
    for record in data[1:]:
        row = dict(zip(data[0], record))
        if (row['lat'] == '0.0' or row['long'] == '0.0'):
            row_num += 1
            lat_long_pair = get_long_lat_from_map(
                row['street1'], row['street2'])
            if (lat_long_pair):
                pass
            else:
                if (not row['street2']):
                    singles += 1
                add_long_lat_to_map(
                    row['street1'], row['street2'], "0.0", "0.0")
                unique_rows += 1

print(f'{row_num} rows left')
print(f'{unique_rows} unique')
print(f'{singles} single streets left')
