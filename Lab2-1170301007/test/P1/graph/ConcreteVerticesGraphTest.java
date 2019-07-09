/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph<String>();
	}

	/*
	 * Testing ConcreteVerticesGraph...
	 */

	// Testing strategy for ConcreteVerticesGraph.toString()
	// Add an edge into the graph and call toString() to see if the output meets
	// expectation

	@Test
	public void testToString() {
		Graph<String> graph = emptyInstance();
		graph.set("person", "good", 1);
		assertEquals("expected  person good", " person good", graph.toString());
	}

	/*
	 * Testing Vertex
	 * 
	 * Testing strategy for Vertex
	 * addEdge()	add an edge and see if the target and weight meet expectation
	 * setWeight()	set an weight of an existent edge and see if the new weight meets expectation
	 * removeEdge()	remove an existent edge and see if the edge doesn't exist
	 * 
	 */

	@Test
	public void testVertex() {
		Vertex<String> vertex = new Vertex<String>("person");
		assertEquals("expected person", "person", vertex.getWord());
		vertex.addEdge("good", 1);
		assertTrue("expected true", vertex.getMap().keySet().contains("good"));
		assertEquals("expected 1", 1, vertex.getMap().get("good").intValue());
		vertex.setWeight("good", 2);
		assertEquals("expected 2", 2, vertex.getMap().get("good").intValue());
		vertex.removeEdge("good");
		assertFalse("expected false", vertex.getMap().keySet().contains("good"));
	}

}
