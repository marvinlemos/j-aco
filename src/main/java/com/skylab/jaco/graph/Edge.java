package com.skylab.jaco.graph;

import java.util.ArrayList;
import java.util.List;

import com.skylab.jaco.conf.Constantes;

public class Edge {
	
	private static int countId = 1;
	
	private int id;
	private Vertex source;
	private Vertex destination;
	private Double weight;
	private Double pheronome;
	private List<PheromoneDeposition> depositions;
	
	private void init(){
		this.id = Edge.countId++;
	}
	
	public Edge(Vertex source, Vertex destination){
		init();
		this.source = source;
		this.destination = destination;
		this.weight = 0.0;
		this.pheronome = 1.0;
		depositions = new ArrayList<>();
		
	}
	
	public Edge(Vertex destination, Double weight){
		init();
		this.destination = destination;
		this.weight = weight;
		this.pheronome = 1.0;
		depositions = new ArrayList<>();
	}
	
	public Edge(Vertex source, Vertex destination, Double weight){
		init();
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.pheronome = 1.0;
		depositions = new ArrayList<>();
	}
	
	public Double getWeight() {
		return this.weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public Vertex getSource() {
		return this.source;
	}
	
	public Vertex getDestination() {
		return this.destination;
	}
	
	public void setDestination(Vertex destination) {
		this.destination = destination;
	}
	
	@Override
	public String toString() {
		return "Id: " + this.id + "/" + "Destination: "+ this.destination.getName();
	}

	public Double getPheromone() {
		return this.pheronome;
	}
	
	public Double getValorHeuristica(){
		return 1.0 / this.weight;
	}

	public List<PheromoneDeposition> getDepositions() {
		return depositions;
	}

	public void addPheromone(Integer antId, Integer cycle, Double custoTotal) {
		//Caso o custo total seja 0.0, não ha necessidade de inserir caso já exista alguma deposicao com a mesma formiga e ciclo
		if (custoTotal > 0 || !depositions.stream().anyMatch(d -> d.getAntId().equals(antId) && d.getCycle().equals(cycle))){
			PheromoneDeposition deposition = new PheromoneDeposition();
			
			deposition.setAntId(antId);
			deposition.setCycle(cycle);
			deposition.setRouteLength(custoTotal);
			
			depositions.add(deposition);
		}
		
	}

	public void updatePheromone() {
		Double delta = 0.0;
		for (PheromoneDeposition deposition : depositions){
			delta = delta + Constantes.Q * deposition.getRouteLength();
		}
		//Vamos subtrair, pois no nosso caso, como queremos maximizar a função,
		//queremos que o feromonio decremente o menor possível
		this.pheronome = (1 - Constantes.EVAPORATION_FACTOR) * this.pheronome + delta;
	}

	public void clearPheromonesTrail() {
		this.depositions = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object obj) {
		Edge outraAresta = (Edge) obj;
		return this.id == outraAresta.getId();
	}

	public int getId() {
		return id;
	}
}