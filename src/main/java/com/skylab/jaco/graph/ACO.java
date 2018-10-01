package com.skylab.jaco.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.skylab.jaco.graph.interfaces.ISearch;
import com.skylab.jaco.utils.Logs;

public class ACO implements ISearch {


	private List<Vertex> findedPaths;
	private Vertex bestPath;
	private Double totalRoutePheromone = 0.0;

	@Override
	public void search(World world, List<Vertex> sources, Vertex destination)  {
		initFindedPaths();
	
		int nCycles = 0;
		
		while (nCycles < world.getMaxIterations()){
			//No inicio de cada ciclo, devemos limpar a trilha de deposicoes 
			//realizadas no ciclo anterior. Pois o calculo da atualizacao do
			//ferominios leva em consideracao apenas as deposicoes do ciclo atual
			world.clearPheromonesTrail();
			
			Random random = new Random();
			Vertex origem = sources.get(random.nextInt(sources.size()));
			
			for (int ant = 0; ant < world.getNumberOfAnts(); ant++){
				
				world.inicializar();
				
				origem.setState(State.FRONTIER);
				origem.setDistancia(0);
				origem.setTotalCost(0.0);
				origem.setParent(null);
				
				List<Edge> listaDeVerticesNaFronteira = new ArrayList<Edge>();
				Edge arestaPonderada = new Edge(origem, 0.0);
				listaDeVerticesNaFronteira.add(arestaPonderada);
				
				while (! listaDeVerticesNaFronteira.isEmpty()){

					Edge aPonderada = getEdgeOnFrontierWithMinorCost(listaDeVerticesNaFronteira);
					Vertex u = aPonderada.getDestination();
					Double pesoTotalDoVerticeNaFronteira = aPonderada.getWeight();
					
					Edge ap = getVizinhoBaseadoNaProbabilidade(u);
					
					Vertex v = ap.getDestination();
					Double p = ap.getWeight();
						
					if (v.getState().equals(State.UNEXPLORED)){
						Double pesoTotal = pesoTotalDoVerticeNaFronteira + p;
						Edge a = new Edge(v, pesoTotal);
							
						v.setState(State.FRONTIER);
						v.setTotalCost(pesoTotal);
						v.setParent(u);

						listaDeVerticesNaFronteira.add(a);
					}
					u.setState(State.EXPLORED);
				}
				//TODO: Aqui deve ser atualizado os feromonios
				depositPheromone(ant, nCycles, origem, destination);
				try {
					Logs.writePath(ant, nCycles, destination.getTotalCost(), totalRoutePheromone, destination);
				} catch (IOException e) {
					System.out.println("Erro ao gerar log de feromonios");
				}
			}
			atualizarValorDosFeromoniosNasArestas(origem, destination);
			saveRoute(destination);
			
			nCycles++;
		}
		
		
	}

	private void saveRoute(Vertex destination) {
		initFindedPaths();
		
		Vertex clone = destination.clone();
		findedPaths.add(clone);
		
		
	}

	private void initFindedPaths() {
		if (findedPaths == null) findedPaths = new ArrayList<Vertex>();
	}

	private void atualizarValorDosFeromoniosNasArestas(Vertex source, Vertex destination) {
		if (source.equals(destination)){
			//System.out.println("updating pheromones on the edges");	
		}else if (destination.getParent() == null){
			System.out.println("There is no path between source and destination");
		}else{
			atualizarValorDosFeromoniosNasArestas(source, destination.getParent());
			Vertex parent = destination.getParent();
			
			for (Edge edge : parent.getEdges()){
				if (edge.getDestination().equals(destination)){
					edge.updatePheromone();
				}
			}
		}
		
	}

	private Edge getVizinhoBaseadoNaProbabilidade(Vertex u){
		List<Edge> edges = u.getEdges();
		
		Edge ap = edges.get(0);;
		
		//Calcular o denominador da funcao de probalidade de selecionar uma aresta
		Double denominador = 0d;
		for (Edge edge : edges){
			if (edge.getDestination().getState().equals(State.UNEXPLORED)){
				denominador = denominador + getPheromoneTimesHeuristicaDaAresta(edge) ;
			}
		}
		
		double probabilidadeTotal = 0.0;
		for (Edge aresta : edges){
			if (aresta.getDestination().getState().equals(State.UNEXPLORED)){
				Double probabilidade = 0.0;
				probabilidade = getPheromoneTimesHeuristicaDaAresta(aresta) / denominador;
				probabilidadeTotal = probabilidadeTotal + probabilidade;
			
				Double lottery = new Random().nextDouble();
			
				if (lottery <= probabilidadeTotal){
					ap = aresta;
					break;
				}
			}
		}
		return ap;
	}
	
	private Double getPheromoneTimesHeuristicaDaAresta(Edge aresta){
		Double resultado = Math.pow(aresta.getPheromone(),0.5) * Math.pow(aresta.getValorHeuristica(), 0.5);
		
		return resultado;
	}
	
	private void depositPheromone(Integer antId, Integer cycle, Vertex origem, Vertex destino){
		totalRoutePheromone = 0.0;
		deposit(antId, cycle, destino.getTotalCost(), origem, destino);
	}
	
	private void deposit(Integer antId, Integer cycle, Double custoTotal, Vertex origem, Vertex destino){
		if (origem.equals(destino)){
			//System.out.println(origem);	
		}else if (destino.getParent() == null){
			System.out.println("There is no path between source and destination");
		}else{
			deposit(antId, cycle, custoTotal, origem, destino.getParent());
			Vertex pai = destino.getParent();
			
			for (Edge edge : pai.getEdges()){
				if (edge.getDestination().equals(destino)){
					edge.addPheromone(antId, cycle, custoTotal);
					totalRoutePheromone = totalRoutePheromone + edge.getPheromone();
				}
			}
		}
		
	}
	
	private Edge getEdgeOnFrontierWithMinorCost(List<Edge> lista){
		Edge aEscolhido = null;
		if (lista.size() == 1){
			aEscolhido = (Edge) lista.toArray()[0];
		}else{
			Double menorCusto = Double.MAX_VALUE;
			
			for (Edge ap : lista){
				Double custo = ap.getWeight();
				
				if (custo < menorCusto){
					menorCusto = custo;
					aEscolhido = ap;
				}
			}
			
		}
		lista.remove(aEscolhido);
		return aEscolhido;
	}

	@Override
	public void print(World world, Vertex origem, Vertex destino) {
		if (origem.equals(destino)){
			System.out.println(origem);	
		}else if (destino.getParent() == null){
			System.out.println("Não existe caminho de s até v");
		}else{
			print(world, origem, destino.getParent());
			System.out.println(destino);
		}
	}

	public Vertex getBestPath() {
		return bestPath;
	}

	@Override
	public List<Vertex> getBestSolution(Vertex origem) {
		// TODO Auto-generated method stub
		return null;
	}

}