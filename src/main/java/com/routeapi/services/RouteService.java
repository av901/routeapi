package com.routeapi.services;

import java.util.List;
import java.util.Map;

import com.routeapi.model.Edge;
import com.routeapi.model.Node;
import com.routeapi.model.Path;
import com.routeapi.model.Route;

public interface RouteService {
	
	void createRoute(List<Node> nodes, List<Edge> edges);

	void addNodes(long routeID, String newNodeName, Map<String, Object> map) throws Exception;
	
	void deleteNodes(long routeID, List<String> nodeNames) throws Exception;
	
	void updateCosts(long routeID, List<List<Object>> updatedEdges) throws Exception;
	
	Path getOptimalPath(long routeID, String sourceNode, String destinationNode) throws Exception;
	
	List<Route> getAllRoutes();
}
