import osmnx as ox
from logging import INFO, log

south_west = [-30.284604, -51.358393]
north_east = [-29.937466, -51.067770]

poa_street_graph = ox.graph_from_bbox(
	north_east[0],
	south_west[0],
	north_east[1],
	south_west[1],
	network_type='drive'
)
log(INFO, f'Got street graph')
psg_projected = ox.project_graph(poa_street_graph)
log(INFO, f'Projected graph')
ox.plot_graph(psg_projected)
log(INFO, f'Plotted graph')
