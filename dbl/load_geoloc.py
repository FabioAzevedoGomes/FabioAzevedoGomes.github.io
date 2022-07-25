import json
import csv

saved_adds = open('backup.json')
saved_dict = json.load(saved_adds)
final_dict = dict()
multi_addr_dict = dict()

# Build dict street -> adds
for i in saved_dict:
    street_name = list(i.keys())[0]
    possible_adds = i[street_name]

    if (len(possible_adds) == 1):
        final_dict[street_name] = possible_adds[0]
    elif(len(possible_adds) > 1):
        multi_addr_dict[street_name] = possible_adds

saved_adds.close()

updated_dictionary = []
corrected = 0
print(f'Loaded {len(list(final_dict.keys()))} from file')

with open("data/accidents_altered.csv", newline='', encoding="utf8") as csvfile:
    data = list(csv.reader(csvfile, delimiter=';'))
    for record in data[1:]:
        row = dict(zip(data[0], record))

        if (row['lat'] == '0.0' or row['long'] == '0.0'):

            street1 = row['street1']
            street2 = row['street2']

            if (not street2 and street1 in final_dict):

                row['lat'] = final_dict[street1]['lat']
                row['long'] = final_dict[street1]['lon']
                corrected += 1

        updated_dictionary.append(row)

print(f'Corrected {corrected} rows')

# Write output
headers = ['id', 'date', 'weekday', 'time', 'type', 'long', 'lat', 'street1', 'street2', 'region', 'deaths',
           'light_injuries', 'serious_injuries', 'total_cars', 'total_buses', 'total_motorcycles', 'total_trucks', 'total_other']

accidents_out_file = open("data/accidents_corrected.csv", "w", newline='')
data = csv.DictWriter(accidents_out_file, delimiter=';', fieldnames=headers)
data.writerow(dict((heads, heads) for heads in headers))
data.writerows(updated_dictionary)
accidents_out_file.close()
