package com.routeapi.model;

import java.io.Serializable;
import java.util.List;

public class RouteData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<String> nodes;
	
	List<List<Object>> edges;

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public List<List<Object>> getEdges() {
		return edges;
	}

	public void setEdges(List<List<Object>> edges) {
		this.edges = edges;
	}
	
	
}
