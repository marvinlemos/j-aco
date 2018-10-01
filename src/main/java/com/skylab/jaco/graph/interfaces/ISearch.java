package com.skylab.jaco.graph.interfaces;

import java.util.List;

import com.skylab.jaco.graph.Vertex;
import com.skylab.jaco.graph.World;

public interface ISearch {
	
	public void print(World world, Vertex source, Vertex destination);
	
	public void search(World world, List<Vertex> sources, Vertex destination);
	
	public List<Vertex> getBestSolution(Vertex source);

}