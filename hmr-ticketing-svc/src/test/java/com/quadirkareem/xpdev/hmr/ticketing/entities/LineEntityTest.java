package com.quadirkareem.xpdev.hmr.ticketing.entities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LineEntityTest {

	@Test
	public void faresFactoryTest() throws URISyntaxException, IOException {
		LineFactory.loadLines();
		FaresFactory.loadFares();
	}
	
	public void lineFactoryTest() throws URISyntaxException, IOException {
		LineFactory.loadLines();
	}
	
	public void testLineCreation() throws IOException, URISyntaxException {
		// read json file data to String
		byte[] jsonData = Files.readAllBytes(
				Paths.get(Thread.currentThread().getContextClassLoader().getResource("lines/lineI.json").toURI()));

		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		// convert json string to object
		LineEntity line = objectMapper.readValue(jsonData, LineEntity.class);
		
		System.out.println(line);
	}

}
