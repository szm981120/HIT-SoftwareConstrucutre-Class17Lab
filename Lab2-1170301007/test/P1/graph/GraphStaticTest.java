/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
	

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testEmptyVerticesEmpty() {
		assertEquals("expected empty() graph to have no vertices", Collections.emptySet(), Graph.empty().vertices());
	}

	@Test
	public void testEmptyAdd() {
		Graph<Integer> graph = Graph.empty();
		assertTrue("expected true with existed vertex", graph.add(new Integer(1)));
		assertTrue("expected true", graph.vertices().contains(new Integer(1)));
	}

	@Test
	public void testNonemptyAdd() {
		Graph<Integer> graph = Graph.empty();
		graph.add(1);
		assertFalse("expected existed Human and false", graph.add(new Integer(1)));
	}

	@Test
	public void testAddSet() {
		Graph<Integer> graph = Graph.empty();
		assertEquals("expected 0 with edge not existed", 0, graph.set(new Integer(1), new Integer(2), 1));
	}

	@Test
	public void testUpdateSet() {
		Graph<Integer> graph = Graph.empty();
		assertEquals("expected initial weight 0", 0, graph.set(new Integer(1), new Integer(2), 1));
		assertEquals("expected previous weight 1", 1, graph.set(new Integer(1), new Integer(2), 2));
		assertEquals("expected present weight 2", 2, graph.targets(new Integer(1)).get(new Integer(2)).intValue());
	}

	@Test
	public void testRemoveSet() {
		Graph<Integer> graph = Graph.empty();
		graph.set(new Integer(1), new Integer(2), 1);
		assertEquals("expected previous weight 1", 1, graph.set(new Integer(1), new Integer(2), 0));
		assertFalse("expected false with no edge Human->equal", graph.targets(new Integer(1)).containsKey(new Integer(2)));
	}

	@Test
	public void testRemoveNullSet() {
		Graph<Integer> graph = Graph.empty();
		assertEquals("expected 0 with non-existent edge", 0, graph.set(new Integer(1), new Integer(2), 0));
	}

	@Test
	public void testVertexNotExistRemove() {
		Graph<Integer> graph = Graph.empty();
		assertFalse("expected false with no such vertex", graph.remove(new Integer(3)));
	}

	@Test
	public void testVertexExistRemove() {
		Graph<Integer> graph = Graph.empty();
		graph.set(new Integer(1), new Integer(2), 1);
		graph.set(new Integer(2), new Integer(3), 1);
		assertTrue("expected true with this vertex existed", graph.remove(new Integer(2)));
		assertFalse("expected false with no edge Human->equal", graph.targets(new Integer(1)).containsKey(new Integer(2)));
		assertFalse("expected false with no edge equal->all", graph.sources(new Integer(3)).containsKey(new Integer(2)));
		assertFalse("expected false with no such vertex", graph.vertices().contains(new Integer(2)));
	}

	@Test
	public void testEmptySources() {
		Graph<Integer> graph = Graph.empty();
		assertEquals("expected new graph to have no sources", Collections.emptyMap(), graph.sources(new Integer(1)));
	}

	@Test
	public void testNonemptySources() {
		Graph<Integer> graph = Graph.empty();
		graph.set(new Integer(1), new Integer(2), 1);
		Map<Integer, Integer> realMap = new HashMap<Integer, Integer>();
		realMap.put(new Integer(1), 1);
		assertEquals("expected <1, 1>", realMap, graph.sources(new Integer(2)));
	}

	@Test
	public void testEmptyTargets() {
		Graph<Integer> graph = Graph.empty();
		assertEquals("expected new graph to have no target", Collections.emptyMap(), graph.targets(new Integer(2)));
	}

	@Test
	public void testNonemptyTargets() {
		Graph<Integer> graph = Graph.empty();
		graph.set(new Integer(1), new Integer(2), 1);
		Map<Integer, Integer> realMap = new HashMap<Integer, Integer>();
		realMap.put(new Integer(2), 1);
		assertEquals("expected <2, 1>", realMap, graph.targets(new Integer(1)));
	}

}
