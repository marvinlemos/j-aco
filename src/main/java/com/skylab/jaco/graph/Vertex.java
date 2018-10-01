package com.skylab.jaco.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	private String name;
	private String description;
	private int distancia;
	private Vertex parent;
	private State state;
	private Double totalCost;
	private Double totalPheromone;
	private int nodeId;
	
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public Vertex getParent() {
		return this.parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	private List<Edge> edges;
	
	public Vertex() {
		this.totalPheromone = 0.0;
	}
	
	public Vertex(String name){
		this.name = name;
		this.totalPheromone = 0.0;
	}
	
	public Vertex(String name, String description) {
		this.name = name;
		this.description = description;
		this.totalPheromone = 0.0;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Edge> getEdges() {
		return this.edges;
	}
	
	public Vertex addEdge(Vertex vertex){
		return addEdge(vertex, 0.0);
	}
	
	public Vertex addEdge(Vertex vertex, Double peso){
		if (edges == null){
			edges = new ArrayList<Edge>();
		}
		
		Edge edge = new Edge(this, vertex, peso);
		
		edges.add(edge);
		
		return this;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
	public Double getTotalCost() {
		return this.totalCost;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	
	@Override
	public boolean equals(Object obj) {
		Vertex outro = (Vertex) obj;
		return this.name.equals(outro.getName());
	}
	
	public Vertex clone(){
		Vertex clone = new Vertex();
		
		clone.setState(this.state);
		clone.setTotalCost(this.totalCost);
		clone.setDescription(this.description);
		clone.setDistancia(distancia);
		clone.setName(name);
		clone.setNodeId(this.nodeId);
		clone.setTotalPheromone(totalPheromone);
		if (this.parent != null) clone.setParent(parent.clone());
		
		return clone;
	}

	public Double getTotalPheromone() {
		return totalPheromone;
	}

	public void setTotalPheromone(Double totalPheromone) {
		this.totalPheromone = totalPheromone;
	}

}