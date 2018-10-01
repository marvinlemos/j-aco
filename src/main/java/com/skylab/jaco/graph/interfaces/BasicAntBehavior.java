package com.skylab.jaco.graph.interfaces;

public interface BasicAntBehavior {
	
	public Double getPheromone();
	public Double getHeuristicValue();
	public void addPheromone(Integer antId, Integer cycle, Double totalCost);
	public void updatePheromone();
	

}