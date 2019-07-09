package P2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import P2.FriendshipGraph;
import P2.Person;

public class FriendshipGraphTest {

	Person rachel = new Person("Rachel");
	Person ross = new Person("Ross");
	Person ben = new Person("Ben");
	Person jack = new Person("Jack");
	Person kramer = new Person("Kramer");
	Person me = new Person("me");

	/*
	 * Test strategy
	 * 	addVertexTest()
	 * 		1. Add a person into an empty graph and see if adding succeeds
	 * 		2. Add a person into a non-empty graph in which there's no ben and see if adding is success
	 * 	
	 * 	addEdgeTest()
	 * 		1. Add an edge from ben to jack into an empty graph and see if adding is succeeds
	 * 		2. Add an edge from kramer to rachel into an non-empty graph where the edge doesn't exist before
	 * 			and see if adding is succeeds
	 * 
	 * 	getDistanceTest()
	 * 		First create a non-empty friendship graph
	 * 		1. test an edge with positive weight
	 * 		2. test an edge with 0 weight, which means from one to oneself
	 * 		3. test an edge with negative weight, which means no edge
	 * 		
	 */
	/**
	 * Tests that assertions are enabled
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	/**
	 * Tests addVertex
	 * 
	 * @throws Exception
	 */
	@Test
	public void addVertexTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		assertTrue("expected empty", graph.vertices().isEmpty());
		graph.addVertex(rachel);
		assertTrue("expected containing rachel", graph.vertices().contains(rachel));
		assertFalse("expected not containing ben", graph.vertices().contains(ben));
		graph.addVertex(ben);
		assertTrue("expected containing ben", graph.vertices().contains(ben));
	}

	/**
	 * Test addEdge
	 * 
	 * @throws Exception
	 */
	@Test
	public void addEdgeTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		assertTrue("expected empty", graph.vertices().isEmpty());
		graph.addEdge(ben, jack);
		assertTrue("expected connection between ben and jack", graph.targets(ben).containsKey(jack));
		graph.addEdge(kramer, rachel);
		assertTrue("expected connection between kramer and rachel", graph.targets(kramer).containsKey(rachel));
	}

	/**
	 * Test getDistance
	 * 
	 * @throws Exception
	 */
	@Test
	public void getDistanceTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		graph.addVertex(ben);
		graph.addVertex(jack);
		graph.addVertex(kramer);
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(me);
		graph.addEdge(jack, ben);
		graph.addEdge(ben, jack);
		graph.addEdge(jack, rachel);
		graph.addEdge(rachel, jack);
		graph.addEdge(jack, ross);
		graph.addEdge(ross, jack);
		graph.addEdge(ben, kramer);
		graph.addEdge(kramer, ben);
		graph.addEdge(ross, kramer);
		graph.addEdge(kramer, ross);

		assertEquals("expected distance is 1", 1, graph.getDistance(ross, kramer));
		assertEquals("expected distance is 3", 3, graph.getDistance(rachel, kramer));
		assertEquals("expected distance is 0", 0, graph.getDistance(me, me));
		assertEquals("expected distance is -1", -1, graph.getDistance(jack, me));
	}
}
