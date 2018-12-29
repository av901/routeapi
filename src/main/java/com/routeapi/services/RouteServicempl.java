package com.routeapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.routeapi.model.Cost;
import com.routeapi.model.Edge;
import com.routeapi.model.Path;

@Service
public class RouteServicempl implements RouteService {

	public void createRoute(List<String> nodeNames, List<Edge> edges) {
		// TODO Auto-generated method stub
		
	}

	public void addNode(String routeID, String newNodeName, Map<String, Cost> adjecentNodes) {
		// TODO Auto-generated method stub
		
	}

	public void deleteNode(String routeID, List<String> nodeName) {
		// TODO Auto-generated method stub
		
	}

	public void updateCosts(List<Edge> updatedEdges) {
		// TODO Auto-generated method stub
		
	}

	public Path getOptimalPath(String routeID, String sourceNode, String destinationNode) {
		// TODO Auto-generated method stub
		return null;
	}

}
