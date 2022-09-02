from typing import Dict, List
import requests
from requests.exceptions import ConnectionError
from datetime import datetime
from dataset_objects.bbox import BoundingBox

def create_date_json_filter(date, field) -> List[dict]:
    return [
        {
            "fields": [field],
            "operator": "IS",
            "value": datetime.fromisoformat(date).strftime("%d/%m/%Y")
        }
    ]

class Remote:
    """
    Class for keeping requests to remote server
    """

    SERVER_ENDPOINT = "http://localhost:8080/"

    DATE_URL = SERVER_ENDPOINT + "accidents/get/dates"
    BBOX_URL = SERVER_ENDPOINT + "map/get/bbox"
    ACCIDNETS_QUERY_URL = SERVER_ENDPOINT + "accidents/get"
    WEATHER_QUERY_URL = SERVER_ENDPOINT + "climate/get"
    SAVE_MODEL_URL = SERVER_ENDPOINT + "prediction/model/persist"
    
    @staticmethod
    def get_bounding_box() -> BoundingBox:
        try:
            resp = requests.get(Remote.BBOX_URL)
        except requests.exceptions.ConnectionError:
            raise RuntimeError(f'Server refused connection')

        return BoundingBox.from_points(resp.json())

    @staticmethod
    def get_date_range() -> List[str]:
        try:
            resp = requests.get(Remote.DATE_URL)
        except requests.exceptions.ConnectionError:
            raise RuntimeError(f'Server refused connection')

        return resp.json()

    @staticmethod
    def get_accidents_for_date(date) -> List[dict]:
        try:
            resp = requests.post(
                url=Remote.ACCIDNETS_QUERY_URL,
                json=create_date_json_filter(date, "date.date"),
                headers={'Content-Type': 'application/json'}
            )
        except ConnectionError:
            raise RuntimeError(f'Server refused connection')

        return resp.json()
    
    @staticmethod
    def get_weather_for_date(date: str) -> List[dict]:
        try:
            resp = requests.post(
                url=Remote.WEATHER_QUERY_URL,
                json=create_date_json_filter(date, "dateTime.date"),
                headers={'Content-Type': 'application/json'}
            )
        except ConnectionError:
            raise RuntimeError(f'Server refused connection')

        return resp.json()
