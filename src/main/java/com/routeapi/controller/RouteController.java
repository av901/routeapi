package com.routeapi.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody String addNewRoute(@RequestBody RouteData data) {
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
				newEdge.setLenght((double)edge.get(2));
				newEdge.setSpeedFactor((double)edge.get(3));
				edgeList.add(newEdge);
			}
			routeService.createRoute(nodeList, edgeList);
			return "success";
		}
		return "failure";
	}
	
	
	
}
