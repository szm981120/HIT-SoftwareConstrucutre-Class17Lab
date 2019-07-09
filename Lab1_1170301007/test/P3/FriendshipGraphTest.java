package P3;

import static org.junit.Assert.*;
import org.junit.Test;

public class FriendshipGraphTest {

	Person rachel = new Person("Rachel");
	Person ross = new Person("Ross");
	Person ben = new Person("Ben");
	Person jack = new Person("Jack");
	Person kramer = new Person("Kramer");
	Person me = new Person("me");

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
		assertTrue("expected empty", graph.adj.isEmpty());
		graph.addVertex(rachel);
		assertTrue("expected containing rachel", graph.adj.containsKey(rachel));
	}

	/**
	 * Test addEdge
	 * 
	 * @throws Exception
	 */
	@Test
	public void addEdgeTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		assertTrue("expected empty", graph.adj.isEmpty());
		graph.addVertex(ben);
		graph.addVertex(jack);
		graph.addEdge(ben, jack);
		assertTrue("expected connection between ben and jack", graph.adj.get(ben).contains(jack));
		assertFalse("expected NO connection between ross and jack", graph.adj.get(ben).contains(ross));
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

		assertEquals("expected distance is 2", 2, graph.getDistance(jack, kramer));
		assertEquals("expected distance is 3", 3, graph.getDistance(rachel, kramer));
		assertEquals("expected distance is -1", -1, graph.getDistance(jack, me));
	}

}
