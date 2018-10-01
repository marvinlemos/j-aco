package com.skylab.jaco.graph;

public class PheromoneDeposition {
	
	private Integer antId;
	private Integer cycle;
	
	//The length/cost of the ant's route
	private Double routeLength;

	public Integer getAntId() {
		return antId;
	}

	public void setAntId(Integer antId) {
		this.antId = antId;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Double getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(Double routeLength) {
		this.routeLength = routeLength;
	}
}
