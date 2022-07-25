# curl "https://nominatim.openstreetmap.org/search?q=R+DR+CAMPOS+VELHO,+PORTO+ALEGRE&format=json"
import csv
import json
import time
import urllib.request

BASE_URL = 'https://nominatim.openstreetmap.org/search?q='
SLEEP_TIME = 2

# Remove later
saved_adds = open('backup_2.json')
saved_dict = json.load(saved_adds)
final_dict = dict()

# Build dict street -> adds for single address responses
for i in saved_dict:
    street_name = list(i.keys())[0]
    possible_adds = i[street_name]
    final_dict[street_name] = possible_adds

saved_adds.close()

updated_dictionary = []
corrected = 0
print(f'Loaded {len(list(final_dict.keys()))} from file')

# Get data input
row_num = 0
backup = open('backup.json', "a")
backup.write('[\n')
backup.close()
with open("data/accidents_altered.csv", newline='', encoding="utf8") as csvfile:
    data = list(csv.reader(csvfile, delimiter=';'))
    for record in data[1:]:
        row = dict(zip(data[0], record))

        if (row['lat'] == '0.0' or row['long'] == '0.0'):

            street1 = row['street1']
            street2 = row['street2']

            if (not street2 and street1 not in final_dict):

                url = BASE_URL + \
                    street1.replace(' ', '+') + \
                    ',PORTO+ALEGRE' + '&format=json'

                time.sleep(SLEEP_TIME)
                req = urllib.request.Request(
                    url,
                    data=None,
                    headers={
                        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.47 Safari/537.36'
                    }
                )

                try:

                    response = urllib.request.urlopen(req)

                    if (response):
                        print('Response: ', end='\n')
                        resp_data = response.read().decode('utf-8')
                        print(resp_data)

                        backup = open('backup.json', "a")
                        backup.write(f'{{ \"{street1}\" : ')
                        backup.write(resp_data)
                        backup.write(' },\n')
                        backup.close()
                        row_num += 1

                except UnicodeEncodeError:
                    pass

print(f'Geocoded {row_num} streets')

backup = open('backup.json', "a")
backup.write('\n]')
backup.close()
