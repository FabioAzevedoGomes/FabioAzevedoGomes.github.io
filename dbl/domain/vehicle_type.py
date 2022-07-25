from enum import Enum


class VehicleType(Enum):
    """Vehicle types"""
    CAR = "Car"  # Auto + Taxi
    BUS = "Bus"  # Lotação + Onibus Urbano + Metropolitano + Int
    MOTORCYCLE = "Motorcycle"  # Moto
    TRUCK = "Truck"  # Caminhao
    OTHER = "Other"  # carroca + bicicleta + outro + patinete
