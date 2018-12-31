package com.routeapi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.routeapi.model.Edge;
import com.routeapi.model.Node;
import com.routeapi.model.Path;
import com.routeapi.model.Route;
import com.routeapi.repository.RouteRepository;

public class OptimalRouteFinder {
	
	
	private final static OptimalRouteFinder OPTIMAL_ROUTE_FINDER = new OptimalRouteFinder();
	
	private final Map<Long, Map<String, Map<String, CostPair>>> paths = new HashMap<Long, Map<String, Map<String, CostPair>>>();
	
	private final Map<Long, Map<Tuple, Double>> costMap = new HashMap<>();
	
	private final Map<Long, Map<String, List<String>>> adjecentMap = new HashMap<>();
	
	private OptimalRouteFinder() {};
	
	public static OptimalRouteFinder getInstance() {
		return OPTIMAL_ROUTE_FINDER;
	}
	
	Path findRoute(long routeID, String sourceNode, String destinationNode, RouteRepository routeRepository) throws Exception{
		if(!paths.containsKey(routeID) || !paths.get(routeID).containsKey(sourceNode)) {
			synchronized(this) {
				updatePath(routeID, sourceNode, routeRepository);
			}
		}
		return getPath(routeID, sourceNode, destinationNode);
	}
	
	
	private Path getPath(long routeID, String sourceNode, String destinationNode) throws Exception {
		Map<String, Map<String, CostPair>> sourceMap = paths.get(routeID);
		Map<String, CostPair> destinationMap = sourceMap.get(sourceNode);
		CostPair node = destinationMap.get(destinationNode);
		if(node == null)
			throw new Exception("Invalid destination : " + destinationNode);
		List<String> pathOrder = new LinkedList<>();
		pathOrder.add(0, destinationNode);
		String previous = node.getViaNode();
		pathOrder.add(0, previous);
		double cost = node.getCost();
		while(!previous.equals(sourceNode)) {
			node = destinationMap.get(previous);
			previous = node.getViaNode();
			pathOrder.add(0, previous);
		}
		return new Path(pathOrder, cost);
	}


	private synchronized void updatePath(long routeID, String sourceNode, RouteRepository routeRepository) throws Exception{
		if(!paths.containsKey(routeID) || !paths.get(routeID).containsKey(sourceNode)) {
			runDijkstra(routeID, sourceNode, routeRepository);
		}
	}


	private synchronized void runDijkstra(long routeID, String sourceNode, RouteRepository routeRepository) throws Exception {
		Route route = routeRepository.findById(routeID);
		if(route == null)
			throw new Exception("No route found for ID : " + routeID);
		List<Node> nodes = route.getNodes();
		List<Edge> edges = route.getEdges();
		Map<String, Node> nodeMap = new HashMap<String, Node>();
		Map<String, Boolean> selectMap = new HashMap<String, Boolean>();
		Map<String, Double> destMap = new HashMap<String, Double>();
		for(Node node : nodes) {
			nodeMap.put(node.getName(), node);
			selectMap.put(node.getName(), false);
			destMap.put(node.getName(), Double.POSITIVE_INFINITY);
		}
		updateAdjecentMap(routeID, edges);
		updateCostMap(routeID, edges);
		selectMap.put(sourceNode, true);
		destMap.put(sourceNode, 0.0);
		Map<String, List<String>> adjMap = adjecentMap.get(routeID);
		Map<Tuple, Double> costMap1 = costMap.get(routeID);
		Map<String, String> viaMap = new HashMap<String, String>();
		for(String nn : adjMap.get(sourceNode)) {
			double cost = costMap1.get(new Tuple(nn, sourceNode));
			destMap.put(nn, cost);
			viaMap.put(nn, sourceNode);
		}
		Map<String, CostPair> allDestinationCost = new HashMap<>();
		for(int i=1; i<=nodes.size()-1; i++) {
			double minCost = Double.POSITIVE_INFINITY;
			String minNode = null;
			for(Entry<String, Double> entry : destMap.entrySet()) {
				String destNode = entry.getKey();
				double cost = entry.getValue();
				if(cost < minCost && !selectMap.get(destNode)) {
					minCost = cost;
					minNode = destNode;
				}
			}
			if(minNode != null) {
				allDestinationCost.put(minNode, new CostPair(minCost, viaMap.get(minNode)));
				selectMap.put(minNode, true);
				for(String nn : adjMap.get(minNode)) {
					if(!selectMap.get(nn)) {
						double cost = costMap1.get(new Tuple(nn, minNode));
						if(minCost + cost < destMap.get(nn)) {
							destMap.put(nn, minCost+cost);
							viaMap.put(nn, minNode);
						}
					}
				}
			}else
				break;
		}

		Map<String, Map<String, CostPair>> path = new HashMap<>();
		path.put(sourceNode, allDestinationCost);
		paths.put(routeID, path);
	}


	private void updateCostMap(long routeID, List<Edge> edges) {
		Map<Tuple, Double> costMap1 = new HashMap<>();
		for(Edge e : edges) {
			Node node1 = e.getNode1();
			Node node2 = e.getNode2();
			double cost = e.getLength()/e.getSpeedFactor();
			costMap1.put(new Tuple(node1.getName(), node2.getName()), cost);
		}
		costMap.put(routeID, costMap1);
		
	}

	private void updateAdjecentMap(long routeID, List<Edge> edges) {
		Map<String, List<String>> adjMap = new HashMap<>();
		for(Edge e : edges) {
			Node node1 = e.getNode1();
			Node node2 = e.getNode2();
			if(!adjMap.containsKey(node1.getName())) {
				adjMap.put(node1.getName(), new ArrayList<>());
			}
			if(!adjMap.containsKey(node2.getName())) {
				adjMap.put(node2.getName(), new ArrayList<>());
			}
			adjMap.get(node1.getName()).add(node2.getName());
			adjMap.get(node2.getName()).add(node1.getName());
		}
		adjecentMap.put(routeID, adjMap);
	}


	private static class CostPair{
		private String viaNode;
		private double cost;
		
		CostPair(double cost, String viaNode){
			this.cost = cost;
			this.viaNode = viaNode;
		}
		
		public String getViaNode() {
			return viaNode;
		}
		
		public double getCost() {
			return cost;
		}
	}
	
	private static class Tuple{

		public Tuple(String string, String string2) {
			val1 = string;
			val2 = string2;
		}
		public String val1;
		public String val2;
		
		public boolean equals(Object obj) {
			if(!(obj instanceof Tuple))
				return false;
			else
			{
				Tuple t = (Tuple)obj;
				if(this.val1.equals(t.val1) && this.val2.equals(t.val2))
					return true;
				if(this.val1.equals(t.val2) && this.val2.equals(t.val1))
					return true;
			}
			return false;
			
		}
		
		public int hashCode() {
			return this.val1.hashCode() + this.val2.hashCode();
		}

	}
	
	/*private class UpdatingThread extends Thread {
		
		long routeID;
		String sourceNode;
		
		public UpdatingThread(long routeID, String sourceNode) {
			this.routeID = routeID;
			this.sourceNode = sourceNode;
		}
		public void run() {
			Map<Long, Map<String, Map<String, CostPair>>> paths = OptimalRouteFinder.getInstance().paths;
			synchronized(paths) {
				if(!paths.containsKey(routeID) || !paths.get(routeID).containsKey(sourceNode)) {
					runDijkstra(routeID, sourceNode);
					paths.notify();
				}
			}
		}
	}*/

}
