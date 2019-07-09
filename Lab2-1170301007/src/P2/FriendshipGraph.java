package P2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import P1.graph.ConcreteEdgesGraph;
import P2.FriendshipGraph;
import P2.Person;

public class FriendshipGraph extends ConcreteEdgesGraph<Person> {


	/*
	 * Abstract function:
	 * 	extends ConcreteEdgesGraph
	 * 
	 * Representation invariant:
	 * 	extends ConcreteEdgesGraph
	 * 
	 * Safety from rep exposure:
	 * 	extends ConcreteEdgesGraph
	 * 	
	 */

	// check rep extends ConcreteEdgesGraph

	/**
	 * constructor
	 */
	public FriendshipGraph() {

	}

	/**
	 * 
	 * @param person a Person object added into graph
	 * @throws Exception
	 */
	public void addVertex(Person person) throws Exception {
		for (Person p : this.vertices()) {
			if (p.getName().equalsIgnoreCase(person.getName())) {
				throw new Exception(person.getName() + " has already existed!");
			}
		}
		this.add(person);
	}

	/**
	 * 
	 * @param p1 one of the two connected people
	 * @param p2 the other of the two connected people
	 */
	public void addEdge(Person p1, Person p2) {
		this.set(p1, p2, 1);
	}

	/**
	 * 
	 * @param p1 the person who connects
	 * @param p2 the person who is connected
	 * @return the distance between the two people p1 and p2
	 */
	public int getDistance(Person p1, Person p2) {
		int distance = 0;	// distance initialization
		Queue<Person> queue = new LinkedList<Person>();	// BFS iteration queue
		/*
		 * edgeTo mapping: assume A maps to B, representing A has direct relation with B. And in all directed path
		 * from source to target, A is backward B and B is forward A.
		 */
		HashMap<Person, Person> edgeTo = new HashMap<Person, Person>();
		/* Every person iteration marked initialize false */
		for (Person p : this.vertices())
			p.setMarked(false);
		p1.setMarked(true);	// source marked true
		queue.add(p1);	// source enqueue
		/* BFS iteration, getting edgeTo */
		while (!queue.isEmpty()) {
			Person tempPerson = queue.remove();
			/* Traversing all adjacent points of the elements of the dequeue */
			for (Person p : this.targets(tempPerson).keySet()) {
				if (!p.getMarked()) { // Found a vertex that has not been traversed
					edgeTo.put(p, tempPerson); // record pre-post relationship
					p.setMarked(true);	// marked true
					queue.add(p);	// a new traversed vertex enqueue
				}
			}
		}
		/* 
		 * At this point, the BFS traversal is completed, and the edgeTo is used to reverse 
		 * the path from the source point to the target point, meanwhile the path length is calculated. 
		 */
		if (!p2.getMarked())
			distance = -1;
		else
			for (Person p = p2; !p.equals(p1); p = edgeTo.get(p))
				distance++;
		return distance;
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		System.out.println(graph.getDistance(rachel, ross));
		System.out.println(graph.getDistance(rachel, ben));
		System.out.println(graph.getDistance(rachel, rachel));
		System.out.println(graph.getDistance(rachel, kramer));
	}

}
