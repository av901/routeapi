package com.routeapi.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.routeapi.model.Edge;
import com.routeapi.model.Node;
import com.routeapi.model.RouteData;
import com.routeapi.services.RouteService;

@Controller   
@RequestMapping(path="/route")
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	@RequestMapping(value="/addRoute", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addNewRoute(@RequestBody RouteData data) {
		List<String> nodes = data.getNodes();
		List<List<Object>> edges = data.getEdges();
		boolean valid = true;
		for(List<Object> edge : edges) {
			String node1 = (String)edge.get(0);
			String node2 = (String)edge.get(1);
			if(!nodes.contains(node1) || !nodes.contains(node2)) {
				valid = false;
				break;
			}
		}
		if(valid) {
			List<Node> nodeList = new ArrayList<Node>();
			List<Edge> edgeList = new ArrayList<Edge>();
			Map<String, Node> nodeMap = new HashMap<String, Node>();
			for(String node: nodes) {
				Node newNode = new Node();
				newNode.setName(node);
				nodeMap.put(node, newNode);
				nodeList.add(newNode);
			}
			for(List<Object> edge : edges) {
				String node1 = (String)edge.get(0);
				String node2 = (String)edge.get(1);
				Edge newEdge = new Edge();
				newEdge.setNode1(nodeMap.get(node1));
				newEdge.setNode2(nodeMap.get(node2));
				newEdge.setLength(Double.parseDouble(edge.get(2).toString()));
				newEdge.setSpeedFactor(Double.parseDouble(edge.get(3).toString()));
				edgeList.add(newEdge);
			}
			routeService.createRoute(nodeList, edgeList);
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
		
	}
	
	@RequestMapping(value="/addNode", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addNewNode(@RequestBody Map<String, Object> jobj) {
		int routeID = (int)jobj.get("route_id");
		String nodeName = (String)jobj.get("node_name");
		Map<String, Object> adjNodes = (Map<String, Object>)jobj.get("adj_nodes");
		try {
			routeService.addNodes(routeID, nodeName, adjNodes);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteNode", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deteteNode(@RequestBody Map<String, Object> jobj) {
		int routeID = (int)jobj.get("route_id");
		List<String> nodes = (List<String>)jobj.get("nodes");
		try {
			routeService.deleteNodes(routeID, nodes);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateEdges", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> updateEdge(@RequestBody Map<String, Object> jobj) {
		int routeID = (int)jobj.get("route_id");
		List<List<Object>> edges = (List<List<Object>>)jobj.get("edges");
		try {
			routeService.updateCosts(routeID, edges);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
}
