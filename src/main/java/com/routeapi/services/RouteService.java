package com.routeapi.services;

import java.util.List;
import java.util.Map;

import com.routeapi.model.Cost;
import com.routeapi.model.Edge;
import com.routeapi.model.Node;
import com.routeapi.model.Path;
import com.routeapi.model.Route;

public interface RouteService {
	
	void createRoute(List<Node> nodes, List<Edge> edges);

	void addNodes(long routeID, List<String> newNodeNames, Map<String, Cost> adjecentNodes);
	
	void deleteNodes(long routeID, List<String> nodeNames);
	
	void updateCosts(List<Edge> updatedEdges);
	
	Path getOptimalPath(long routeID, String sourceNode, String destinationNode);
	
	List<Route> getAllRoutes();
}
