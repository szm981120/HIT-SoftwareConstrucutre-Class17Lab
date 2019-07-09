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
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	/*
	 * Testing strategy 
	 * 	emptyInstance() 
	 * 		no inputs, only output is empty graph
	 * 		observe with vertices()
	 * 	add()
	 * 		input is a String, output is whether the vertex has already existed
	 * 		Partitions: 
	 * 			1.	add a vertex into an empty graph
	 * 			2.	add a vertex into a nonempty graph
	 * 	Set()
	 * 		input:	L source, label of the source vertex;
	 * 				L target, label of the target vertex;
	 * 				int weight, nonnegative weight of the edge
	 * 		output:	the previous weight of the edge from source to target, if the edge 
	 * 				doesn't exist, return 0
	 * 		Partitions:
	 * 			1.	Add an edge with weight. The edge doesn't exist before.
	 * 			2.	Change the weight of an existed edge via setting the weight to non-zero.
	 * 			3.	Remove an existed edge via setting the weight to zero.
	 * 			4.	Remove an non-existent edge.
	 * 	Remove()
	 * 		input:	L vertex, label of the vertex to remove
	 * 		output:	true if this graph included a vertex with the given label;
	 *         		otherwise false
	 *		Partitions:
	 *			1.	Remove an existed vertex
	 *			2.	Remove a non-existent vertex
	 *	Source()
	 *		input:	L target,  a label of target vertex
	 *		output:	a map where the key set is the set of labels of vertices such
	 *         		that this graph includes an edge from that vertex to target, and
	 *         		the value for each key is the (nonzero) weight of the edge from
	 *         		the key to target
	 *		Partitions:
	 *			1.	The target vertex has no source vertices attached to it.
	 *			2.	The target vertex has at least one vertex attached to it.
	 *	Targets()
	 *		input:	L source, a label of source vertex
	 *		output:	a map where the key set is the set of labels of vertices such
	 *         		that this graph includes an edge from source to that vertex, and
	 *         		the value for each key is the (nonzero) weight of the edge from
	 *         		source to the key
	 *		Partitions:
	 *			1.	The source has no edges attached to other vertices.
	 *			2.	The source has at least one edge attached to other vertices.
	 * 
	 */
	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	@Test
	public void testEmptyAdd() {
		Graph<String> graph = emptyInstance();
		assertTrue("expected true with existed vertex", graph.add("Human"));
		assertTrue("expected true", graph.vertices().contains("Human"));
	}

	@Test
	public void testNonemptyAdd() {
		Graph<String> graph = emptyInstance();
		graph.add("Human");
		assertFalse("expected existed Human and false", graph.add("Human"));
	}

	@Test
	public void testAddSet() {
		assertEquals("expected 0 with edge not existed", 0, emptyInstance().set("Human", "equal", 1));
	}

	@Test
	public void testUpdateSet() {
		Graph<String> graph = emptyInstance();
		assertEquals("expected initial weight 0", 0, graph.set("Human", "equal", 1));
		assertEquals("expected previous weight 1", 1, graph.set("Human", "equal", 2));
		assertEquals("expected present weight 2", 2, graph.targets("Human").get("equal").intValue());
	}

	@Test
	public void testRemoveSet() {
		Graph<String> graph = emptyInstance();
		graph.set("Human", "equal", 1);
		assertEquals("expected previous weight 1", 1, graph.set("Human", "equal", 0));
		assertFalse("expected false with no edge Human->equal", graph.targets("Human").containsKey("equal"));
	}

	@Test
	public void testRemoveNullSet() {
		assertEquals("expected 0 with non-existent edge", 0, emptyInstance().set("Human", "equal", 0));
	}

	@Test
	public void testVertexNotExistRemove() {
		assertFalse("expected false with no such vertex", emptyInstance().remove("Good"));
	}

	@Test
	public void testVertexExistRemove() {
		Graph<String> graph = emptyInstance();
		graph.set("Human", "equal", 1);
		graph.set("equal", "all", 1);
		assertTrue("expected true with this vertex existed", graph.remove("equal"));
		assertFalse("expected false with no edge Human->equal", graph.targets("Human").containsKey("equal"));
		assertFalse("expected false with no edge equal->all", graph.sources("all").containsKey("equal"));
		assertFalse("expected false with no such vertex", graph.vertices().contains("equal"));
	}

	@Test
	public void testEmptySources() {
		assertEquals("expected new graph to have no sources", Collections.emptyMap(), emptyInstance().sources("Human"));
	}

	@Test
	public void testNonemptySources() {
		Graph<String> graph = emptyInstance();
		graph.set("Human", "equal", 1);
		Map<String, Integer> realMap = new HashMap<String, Integer>();
		realMap.put("Human", 1);
		assertEquals("expected <Human, 1>", realMap, graph.sources("equal"));
	}

	@Test
	public void testEmptyTargets() {
		assertEquals("expected new graph to have no target", Collections.emptyMap(), emptyInstance().targets("equal"));
	}

	@Test
	public void testNonemptyTargets() {
		Graph<String> graph = emptyInstance();
		graph.set("Human", "equal", 1);
		Map<String, Integer> realMap = new HashMap<String, Integer>();
		realMap.put("equal", 1);
		assertEquals("expected <equal, 1>", realMap, graph.targets("Human"));
	}

}
