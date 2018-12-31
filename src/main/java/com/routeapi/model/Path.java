package com.routeapi.model;

import java.util.List;

public class Path {
	
	private List<String> nodes;
	private double totalCost;
	
	public Path(List<String> nodes, double totalCost){
		this.nodes = nodes;
		this.totalCost = totalCost;
	}

	public List<String> getNodes(){
		return nodes;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
}
