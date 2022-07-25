from enum import Enum


class AccidentType(Enum):
    """Accident types"""
    COLLISION = "Collision"  # ABALROAMENTO + CHOQUE + COLISAO
    RUN_OVER = "Run Over"  # ATROPELAMENTO
    ROLLOVER = "Rollover"  # CAPOTAGEM
    OTHER = "Other"  # QUEDA + TOMBAMENTO + EVENTUAL + INCENDIO + NAO_CADASTRADO


def clean_accident_type(input_type: str) -> str:

    input_type = input_type.strip()

    if (input_type == 'ABALROAMENTO'):
        return AccidentType.COLLISION.value.upper()
    elif(input_type == 'ATROPELAMENTO'):
        return AccidentType.RUN_OVER.value.upper()
    elif(input_type == 'CHOQUE'):
        return AccidentType.COLLISION.value.upper()
    elif(input_type == 'COLISÃO'):
        return AccidentType.COLLISION.value.upper()
    elif(input_type == 'QUEDA'):
        return AccidentType.OTHER.value.upper()
    elif(input_type == 'TOMBAMENTO'):
        return AccidentType.OTHER.value.upper()
    elif(input_type == 'EVENTUAL'):
        return AccidentType.OTHER.value.upper()
    elif(input_type == 'CAPOTAGEM'):
        return AccidentType.ROLLOVER.value.upper()
    elif(input_type == 'Incêndio'):
        return AccidentType.OTHER.value.upper()
    else:
        return AccidentType.OTHER.value.upper()
