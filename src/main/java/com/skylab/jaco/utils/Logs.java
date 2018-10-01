package com.skylab.jaco.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.skylab.jaco.conf.Constantes;
import com.skylab.jaco.graph.Vertex;

public class Logs {
	
	public static String sysDateStr = "";

	public static void writePath(Integer antId, Integer cycle, Double custoTotal, Double pheromone, Vertex destino) throws IOException{
		
		Path filePath = Paths.get(Constantes.LOG_FILE+"_"+sysDateStr+".log");
		
		if (!Files.exists(filePath)){
			Files.write(filePath, "cycle;antId;totalCoust;routePheromone;endPoint".getBytes());
		}
		
		String linha = 	"\n".concat(cycle.toString())
							.concat(";")
							.concat(antId.toString())
							.concat(";")
							.concat(custoTotal.toString())
							.concat(";")
							.concat(pheromone.toString())
							.concat(";")
							.concat(destino.getName());
		
		Files.write(filePath, linha.getBytes(), StandardOpenOption.APPEND);
	}
}
