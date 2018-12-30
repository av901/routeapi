package com.routeapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeapi.model.Cost;
import com.routeapi.model.Edge;
import com.routeapi.model.Node;
import com.routeapi.model.Path;
import com.routeapi.model.Route;
import com.routeapi.repository.EdgeRepository;
import com.routeapi.repository.NodeRepository;
import com.routeapi.repository.RouteRepository;

@Service
public class RouteServicempl implements RouteService {
	
	@Autowired
	RouteRepository routeRepository;
	
	@Autowired
	EdgeRepository edgeRepository;
	
	@Autowired
	NodeRepository nodeRepository;

	public void createRoute(List<Node> nodes, List<Edge> edges) {
		Route route = new Route();
		route.setNodes(nodes);
		route.setEdges(edges);
		Route savedRoute = routeRepository.save(route);
		nodes.stream().forEach(node ->{
			node.setRoute(savedRoute);
			nodeRepository.save(node);
		});
		edges.stream().forEach(edge -> {
			edge.setRoute(savedRoute);
			edgeRepository.save(edge);
		});
	}
	
	@Override
	public void addNodes(long routeID, List<String> newNodeNames, Map<String, Cost> adjecentNodes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNodes(long routeID, List<String> nodeNames) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateCosts(List<Edge> updatedEdges) {
		// TODO Auto-generated method stub
		
	}

	public Path getOptimalPath(long routeID, String sourceNode, String destinationNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Route> getAllRoutes() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
