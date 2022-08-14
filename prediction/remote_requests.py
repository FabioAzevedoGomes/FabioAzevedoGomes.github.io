import datetime
import requests
from typing import List, Dict

SERVER_ENDPOINT = "http://localhost:8080/"

DATE_URL = SERVER_ENDPOINT + "accidents/get/dates"
BBOX_URL = SERVER_ENDPOINT + "map/get/bbox"
ACCIDNETS_QUERY_URL = SERVER_ENDPOINT + "accidents/get"
WEATHER_QUERY_URL = SERVER_ENDPOINT + "climate/get"
SAVE_MODEL_URL = SERVER_ENDPOINT + "prediction/model/persist"

def get_bounding_box() -> Dict[str, Dict[str, float]]:
    try:
        resp = requests.get(BBOX_URL)
    except requests.exceptions.ConnectionError:
        raise RuntimeError(f'Server refused connection')

    return resp.json()


def get_date_range() -> List[str]:
    try:
        resp = requests.get(DATE_URL)
    except requests.exceptions.ConnectionError:
        raise RuntimeError(f'Server refused connection')

    return resp.json()


def create_date_json_filter(date, field) -> List[dict]:
    return [
        {
            "fields": [field],
            "operator": "IS",
            "value": datetime.datetime.fromisoformat(date).strftime("%d/%m/%Y")
        }
    ]


def get_accidents_for_date(date) -> List[dict]:
    try:
        resp = requests.post(
            url=ACCIDNETS_QUERY_URL,
            json=create_date_json_filter(date, "date.date"),
            headers={'Content-Type': 'application/json'}
        )
    except requests.exceptions.ConnectionError:
        raise RuntimeError(f'Server refused connection')

    return resp.json()


def get_weather_for_date(date: str) -> List[dict]:
    try:
        resp = requests.post(
            url=WEATHER_QUERY_URL,
            json=create_date_json_filter(date, "dateTime.date"),
            headers={'Content-Type': 'application/json'}
        )
    except requests.exceptions.ConnectionError:
        raise RuntimeError(f'Server refused connection')

    return resp.json()


def save_predictive_model(model_version: str, model_json: str):
    try:
        resp = requests.post(
            url=SAVE_MODEL_URL,
            json={"version": model_version, "modelJson": model_json},
            headers={'Content-Type': 'application/json'}
        )
    except requests.exceptions.ConnectionError:
        raise RuntimeError(f'Server refused connection')
