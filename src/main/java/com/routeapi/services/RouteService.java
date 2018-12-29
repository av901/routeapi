package com.routeapi.services;

import java.util.List;
import java.util.Map;

import com.routeapi.model.Cost;
import com.routeapi.model.Edge;
import com.routeapi.model.Path;

public interface RouteService {
	
	void createRoute(List<String> nodeNames, List<Edge> edges);

	void addNode(String routeID, String newNodeName, Map<String, Cost> adjecentNodes);
	
	void deleteNode(String routeID, List<String> nodeName);
	
	void updateCosts(List<Edge> updatedEdges);
	
	Path getOptimalPath(String routeID, String sourceNode, String destinationNode);
}
