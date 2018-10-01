package com.skylab.jaco.examples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.skylab.jaco.graph.ACO;
import com.skylab.jaco.graph.Vertex;
import com.skylab.jaco.graph.World;
import com.skylab.jaco.graph.interfaces.ISearch;
import com.skylab.jaco.utils.Logs;

public class Main {

	public static void main(String[] args) {
		
		World world = new World(20,20);
		
		Vertex vA = new Vertex("A");
		Vertex vB = new Vertex("B");
		Vertex vC = new Vertex("C");
		Vertex vD  = new Vertex("D");
		Vertex vE  = new Vertex("E");
		Vertex vF  = new Vertex("F");
		
		vA.addEdge(vB,10.0).addEdge(vF,7.0);
		vB.addEdge(vA,10.0).addEdge(vC,6.0);
		vC.addEdge(vB, 6.0).addEdge(vD, 9.0);
		vD.addEdge(vC, 9.0).addEdge(vE, 2.0);
		vE.addEdge(vD, 2.0).addEdge(vF,2.0);
		vF.addEdge(vE, 2.0).addEdge(vA, 7.0);
		
		world.addVertex(vA)
			.addVertex(vB)
			.addVertex(vC)
			.addVertex(vD)
			.addVertex(vE)
			.addVertex(vF);
		
		Calendar cal = Calendar.getInstance();
		Date sysDate = cal.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
		String sysDateStr = sdf.format(sysDate);
		
		Logs.sysDateStr = sysDateStr;
		
		ISearch busca = new ACO();
		List<Vertex> sources = new ArrayList<>();
		sources.add(vB);
 		busca.search(world, sources, vE);
		
		busca.print(world, vB, vE);
		
	}

}