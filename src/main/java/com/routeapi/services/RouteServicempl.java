package com.routeapi.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeapi.model.Edge;
import com.routeapi.model.Node;
import com.routeapi.model.Path;
import com.routeapi.model.Route;
import com.routeapi.repository.EdgeRepository;
import com.routeapi.repository.NodeRepository;
import com.routeapi.repository.RouteRepository;

@Service
@Transactional
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
	public void addNodes(long routeID, String newNodeName, Map<String, Object> adjacentNodes) throws Exception {
		Route route = routeRepository.findById(routeID);
		if(route == null)
			throw new Exception("No route found for ID : " + routeID);
		List<Edge> exstingEdges = route.getEdges();
		List<Node> existingNodes = route.getNodes();
		//check this node should not same to any existing node name
		Map<String, Node> nodeMap = new HashMap<String, Node>();
		for(Node node : existingNodes) {
			if(node.getName().equals(newNodeName))
				throw new Exception("The same node name already exist");
			nodeMap.put(node.getName(), node);
		}
		Node node2 = new Node();
		node2.setRoute(route);
		node2.setName(newNodeName);
		//check the validity of adjacent node and create edges
		for(Entry<String, Object> entry : adjacentNodes.entrySet()) {
			String adjNodeName = entry.getKey();
			Object cost = entry.getValue();
			List<Object> ja = (List<Object>)cost;
			if(ja.size() != 2)
				throw new Exception("invalid input");
			if(nodeMap.containsKey(adjNodeName)) {
				Edge edge = new Edge();
				edge.setNode1(nodeMap.get(adjNodeName));
				edge.setNode2(node2);
				edge.setLength(Double.parseDouble(ja.get(0).toString()));
				edge.setSpeedFactor(Double.parseDouble(ja.get(1).toString()));
				edge.setRoute(route);
				exstingEdges.add(edge);
			}else {
				throw new Exception("No node of name : " + adjNodeName + " found in route");
			}
		}
		existingNodes.add(node2);
		routeRepository.save(route);
	}

	@Override
	public void deleteNodes(long routeID, List<String> nodeNames) throws Exception {
		Route route = routeRepository.findById(routeID);
		if(route == null)
			throw new Exception("No route found for ID : " + routeID);
		List<Node> existingNodes = route.getNodes();
		Map<String, Node> nodeMap = new HashMap<String, Node>();
		for(Node node : existingNodes) {
			if(nodeNames.contains(node.getName()))
				nodeMap.put(node.getName(), node);
		}
		if(nodeMap.size() != nodeNames.size())
			throw new Exception("Some nodes not found in the route");
		for(Entry<String, Node> entry : nodeMap.entrySet()) {
			long nodeId = entry.getValue().getId();
			List<Edge> edges = edgeRepository.findByNode1IdOrNode2Id(nodeId, nodeId);
			edgeRepository.delete(edges);
			nodeRepository.deleteById(nodeId);
			existingNodes.remove(entry.getValue());
		}
		routeRepository.save(route);
	}
	
	public void updateCosts(long routeID, List<List<Object>> updatedEdges) throws Exception{
		Route route = routeRepository.findById(routeID);
		if(route == null)
			throw new Exception("No route found for ID : " + routeID);
		List<Node> existingNodes = route.getNodes();
		Map<String, Node> nodeMap = new HashMap<String, Node>();
		for(Node node : existingNodes) {
			nodeMap.put(node.getName(), node);
		}
		for(List<Object> edgeDetail : updatedEdges) {
			String node1Name = (String)edgeDetail.get(0);
			String node2Name = (String)edgeDetail.get(1);
			Node node1 = nodeMap.get(node1Name);
			if(node1 == null) {
				throw new Exception("Invalid node name: " + node1Name);
			}
			Node node2 = nodeMap.get(node2Name);
			if(node2 == null) {
				throw new Exception("Invalid node name: " + node2Name);
			}
		}
		for(List<Object> edgeDetail : updatedEdges) {
			String node1Name = (String)edgeDetail.get(0);
			String node2Name = (String)edgeDetail.get(1);
			double length = Double.parseDouble(edgeDetail.get(2).toString());
			double speedFactor = Double.parseDouble(edgeDetail.get(3).toString());
			long node1Id = nodeMap.get(node1Name).getId();
			long node2Id = nodeMap.get(node2Name).getId();
			Edge edge = edgeRepository.findByNode1IdAndNode2IdOrNode2IdAndNode1Id(node1Id, node2Id, node1Id, node2Id);
			if(edge != null) {
				edge.setLength(length);
				edge.setSpeedFactor(speedFactor);
				edgeRepository.save(edge);
			}
		}		
	}

	public Path getOptimalPath(long routeID, String sourceNode, String destinationNode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Route> getAllRoutes() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
