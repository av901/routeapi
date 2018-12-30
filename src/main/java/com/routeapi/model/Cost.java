package com.routeapi.model;

public class Cost {
	
	private double lenght;
	
	private double speedFactor;
	
	public Cost(double length, double speedFactor) {
		this.lenght = length;
		this.speedFactor = speedFactor;
	}

	public double getLenght() {
		return lenght;
	}

	public void setLenght(double lenght) {
		this.lenght = lenght;
	}

	public double getSpeedFactor() {
		return speedFactor;
	}

	public void setSpeedFactor(double speedFactor) {
		this.speedFactor = speedFactor;
	}

}
