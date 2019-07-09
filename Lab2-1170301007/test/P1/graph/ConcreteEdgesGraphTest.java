/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph<String>();
	}

	/*
	 * Testing ConcreteEdgesGraph
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	// Testing strategy for ConcreteEdgesGraph.toString()
	// Add an edge into the graph and call toString() to see if the output meets
	// expectation

	@Test
	public void testToString() {
		Graph<String> graph = emptyInstance();
		graph.set("person", "good", 1);
		assertEquals("expected person--1-->good", "person--1-->good\n", graph.toString());
	}

	/*
	 * Testing Edge
	 * 
	 * Testing strategy for Edge
	 * First create an Edge<String> for test
	 * see if getSource() meets the expectation output
	 * see if getTarget() meets the expectation output
	 * see if getWeight() meets the expectation output
	 */

	@Test
	public void testEdge() {
		Edge<String> edge = new Edge<String>("person", "good", 1);
		assertEquals("expected person", "person", edge.getSource());
		assertEquals("expected good", "good", edge.getTarget());
		assertEquals("expected 1", 1, edge.getWeight());
	}

}
