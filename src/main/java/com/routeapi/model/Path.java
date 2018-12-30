package com.routeapi.model;

import java.util.List;

public class Path {
	
	private List<Edge> edges;
	
	public Path(List<Edge> edges){
		this.edges = edges;
	}

	public List<Edge> getEdges(){
		return edges;
	}
}
