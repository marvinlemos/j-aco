package com.skylab.jaco.graph;
import java.util.ArrayList;
import java.util.List;

public class World {
	
	private String name;
	private String description;
	
	private Integer numberOfAnts;
	private Integer maxIterations;

	private List<Vertex> vertices;
	
	public World() {
		this.numberOfAnts = 5;
		this.maxIterations = 10;
		this.vertices = new ArrayList<>();
	}
	
	public World(Integer nOfAnts, Integer maxIterations){
		this.numberOfAnts = nOfAnts;
		this.maxIterations = maxIterations;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Vertex> getVertices() {
		return vertices;
	}
	
	public World addVertex(Vertex vertex){
		if (vertices == null){
			vertices = new ArrayList<Vertex>();
		}
		
		vertices.add(vertex);
		
		return this;
	}
	

	public void inicializar() {
		
		for (Vertex vertice : vertices){
			vertice.setState(State.UNEXPLORED);
			vertice.setDistancia(-1);
			vertice.setParent(null);
			vertice.setTotalCost(0.0);
		}
		
	}

	public Integer getNumberOfAnts() {
		return numberOfAnts;
	}

	public Integer getMaxIterations() {
		return maxIterations;
	}

	public void clearPheromonesTrail() {
		for (Vertex vertice : vertices){
			if (vertice.getEdges() != null)
				for (Edge edge : vertice.getEdges()){
					edge.clearPheromonesTrail();
				}
		}
	}
	
	public List<Edge> getArestas(){
		List<Edge> arestas = new ArrayList<>();
		for (Vertex vertex : vertices){
			if (vertex.getEdges() != null){
				arestas.addAll(vertex.getEdges());
			}
		}
		return arestas;
	}

}
