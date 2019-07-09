/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	/* 
	 * Abstraction function:
	 *	vertices contains a set of L(type) data which represents each vertex
	 * 	edges contains a list of Edge<L>(type) which represents each
	 *  vertices and edges constitute a directed graph
	 *  
	 * Representation invariant:
	 *	any two vertices of an edge must be contained in vertices
	 *	any two elements in vertices or edges shouldn't be the same
	 *	
	 * Safety from rep exposure:
	 * 	all representations are defined private and final
	 *  vertices() which is the only Observer method, makes defensive copies so that the client can't change the rep vertices outside
	 *    	
	 */

	// constructor
	public ConcreteEdgesGraph() {
		checkRep();
	}

	// checkRep
	private void checkRep() {
		for (Edge<L> edge : edges) {
			assert vertices.contains(edge.getSource());
			assert vertices.contains(edge.getTarget());
		}
	}

	@Override
	public boolean add(L vertex) {
		if (vertices.contains(vertex)) {
			checkRep();
			return false;
		} else {
			vertices.add(vertex);
			checkRep();
			return true;
		}
	}

	@Override
	public int set(L source, L target, int weight) {
		int tempWeight = 0;
		if (weight != 0) {
			if (!vertices.contains(source))
				vertices.add(source);
			if (!vertices.contains(target))
				vertices.add(target);
			for (Edge<L> edge : edges) {
				if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
					tempWeight = edge.getWeight();
					edges.remove(edge);
					edges.add(new Edge<L>(source, target, weight));
					checkRep();
					return tempWeight;
				}
			}
			edges.add(new Edge<L>(source, target, weight));
			checkRep();
			return 0;
		} else {
			for (Edge<L> edge : edges) {
				if (edge.getSource().equals(source)) {
					tempWeight = edge.getWeight();
					edges.remove(edge);
					checkRep();
					return tempWeight;
				}
			}
			checkRep();
			return 0;
		}
	}

	@Override
	public boolean remove(L vertex) {
		if (vertices.contains(vertex)) {
			Iterator<Edge<L>> iterator = edges.iterator();
			while (iterator.hasNext()) {
				Edge<L> tempEdge = iterator.next();
				if (tempEdge.getSource().equals(vertex))
					iterator.remove();
				else if (tempEdge.getTarget().equals(vertex))
					iterator.remove();
			}
			for(L v : vertices) {
				if(v.equals(vertex)) {
					vertices.remove(vertex);
					break;
				}
			}
			vertices.remove(vertex);
			checkRep();
			return true;
		} else {
			checkRep();
			return false;
		}
	}

	@Override
	public Set<L> vertices() {
		Set<L> verticeSet = new HashSet<L>(vertices);
		checkRep();
		return verticeSet;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> sourcesMap = new HashMap<L, Integer>();
		for (Edge<L> edge : edges) {
			if (edge.getTarget().equals(target)) {
				sourcesMap.put(edge.getSource(), edge.getWeight());
			}
		}
		checkRep();
		return sourcesMap;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> targetsMap = new HashMap<L, Integer>();
		for (Edge<L> edge : edges) {
			if (edge.getSource().equals(source)) {
				targetsMap.put(edge.getTarget(), edge.getWeight());
			}
		}
		checkRep();
		return targetsMap;
	}

	@Override
	public String toString() {
		StringBuilder graph = new StringBuilder();
		for(Edge<L> edge : edges) {
			graph.append(edge.toString() + "\n");
		}
		return graph.toString();
	}
}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {

	private final L source;
	private final L target;
	private final int weight;


	/* Abstraction function:
	 * 	AF(source, target, weight) = {an edge weighing weight from source to target}
	 * 
	 * Representation invariant:
	 * 	source which represents an edge's start, shouldn't be null
	 * 	target which represents an edge's end, shouldn't be null
	 * 	weight which represents an edge's weight, should be non-negative
	 * 	weight is zero representing that this edge doesn't exist
	 * 
	 * Safety from rep exposure:
	 * 	all representations are defined private and final. And L(type) is immutable according to spec.
	 */

	// constructor
	public Edge(L s, L t, int w) {
		this.source = s;
		this.target = t;
		this.weight = w;
	}

	// checkRep
	private void checkRep() {
		assert weight >= 0;
		assert source != null;
		assert target != null;
	}

	/**
	 * Observer
	 *
	 * @return a copy of source
	 */
	public L getSource() {
		L source = this.source;
		checkRep();
		return source;
	}

	/**
	 * Observer
	 * 
	 * @return a copy of target
	 */
	public L getTarget() {
		L target = this.target;
		checkRep();
		return target;
	}

	/**
	 * Observer
	 * 
	 * @return a copy of weight
	 */
	public int getWeight() {
		int weight = this.weight;
		checkRep();
		return weight;
	}
	
	@Override
	public String toString() {
		StringBuilder edge = new StringBuilder();
		edge.append(this.source.toString() + "--" + String.valueOf(this.weight) + "-->" + this.target.toString());
		return edge.toString();
	}

}
