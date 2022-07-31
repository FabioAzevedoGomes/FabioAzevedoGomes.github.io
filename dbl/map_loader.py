from typing import List
import osmnx as ox
from networkx import MultiDiGraph
from domain.geolocation import GeoLocation
from domain.street import Street, estimate_geolocation
from domain.intersection import Intersection
from logging import log, INFO

RELEVANT_NETWORK_TYPE = "drive"
POA_BOUNDING_BOX = [
    [-30.284604, -51.358393],
    [-29.937466, -51.067770]
]


def get_streets(street_network_graph: MultiDiGraph) -> List[Street]:
    edge_list = street_network_graph.edges()
    street_list = []

    print(f'Loading {len(edge_list)} streets')
    for edge in edge_list:
        edge_object = street_network_graph[edge[0]][edge[1]][0]

        names = ""
        try:
            names = edge_object['name']
        except KeyError:
            pass

        geo_location = estimate_geolocation(
            edge_object,
            street_network_graph.nodes[edge[0]],
            street_network_graph.nodes[edge[1]]
        )

        externalId = edge_object['osmid']
        if not isinstance(externalId, list):
            externalId = [externalId]

        if isinstance(names, list):
            names = ' / '.join(names)

        for id in externalId:
            directional_id = f'{edge[0]}-{id}-{edge[1]}'
            street_list.append(
                Street(
                    externalId=id,
                    directionalId = directional_id,
                    name=names,
                    location=geo_location,
                    length=edge_object['length'],
                    source_intersection_id=edge[0],
                    destination_intersection_id=edge[1]
                )
            )

    return street_list


def get_intersections(street_network_graph: MultiDiGraph) -> List[Intersection]:
    node_list = street_network_graph.nodes()
    intersection_list = []

    print(f'Loading {len(node_list)} intersections')
    for node in node_list:
        intersection_list.append(
            Intersection(
                externalId=str(node),
                location=GeoLocation(
                    longitude=street_network_graph.nodes[node]['lon'],
                    latitude=street_network_graph.nodes[node]['lat'],
                ),
                connected_street_ids=[],
                incoming_street_ids=[],
                outgoing_street_ids=[]
            )
        )

    return intersection_list


def get_streets_and_intersections():
    poa_street_graph = ox.graph_from_bbox(
        POA_BOUNDING_BOX[1][0],
        POA_BOUNDING_BOX[0][0],
        POA_BOUNDING_BOX[1][1],
        POA_BOUNDING_BOX[0][1],
        network_type=RELEVANT_NETWORK_TYPE
    )

    projected_graph = ox.project_graph(poa_street_graph)
    return get_streets(projected_graph), get_intersections(projected_graph)
