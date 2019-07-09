/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	/* Abstraction function:
	 * 	vertices contains a set of Vertex<L>(type) data which represents each vertex
	 * 	vertices can represent a directed graph
	 * 
	 * Representation invariant:
	 * 	any two elements in vertices shouldn't be the same
	 * 
	 * Safety from rep exposure:
	 * 	the only representation is defined private and final
	 * 	vertices() which is the only Observer method, makes a copy from List to Set
	 */

	// constructor
	public ConcreteVerticesGraph() {
		checkRep();
	}

	// checkRep
	private void checkRep() {
		for (int i = 0; i < vertices.size() - 1; i++) {
			for (int j = i + 1; j < vertices.size(); j++) {
				assert !vertices.get(i).equals(vertices.get(j));
			}
		}
	}

	@Override
	public boolean add(L vertex) {
		if (!vertices().contains(vertex)) {
			vertices.add(new Vertex<L>(vertex));
			checkRep();
			return true;
		}
		/* the vertex has already existed */
		else {
			checkRep();
			return false;
		}
	}

	@Override
	public int set(L source, L target, int weight) {
		int tempWeight = 0; // temporary component for previous weight
		/* Add or change an edge */
		if (weight != 0) {
			/* change an edge's weight */
			if (vertices().contains(source) && vertices().contains(target)) {
				/* search the source vertex and change it's target adjoining vertex */
				for (Vertex<L> vertex : vertices) {
					if (vertex.getWord().equals(source)) {
						tempWeight = vertex.getMap().get(target);
						vertex.setWeight(target, weight);
						break;
					}
				}
				checkRep();
				return tempWeight;
			}
			/* if the source or target vertex doesn't exist, add it into vertices*/
			else {
				if (!vertices().contains(source))
					vertices.add(new Vertex<L>(source));
				if (!vertices().contains(target))
					vertices.add(new Vertex<L>(target));
				for (Vertex<L> vertex : vertices) {
					if (vertex.getWord().equals(source)) {
						vertex.addEdge(target, weight);
						break;
					}
				}
				checkRep();
				return 0;
			}
		}
		/* the weight is 0, remove the edge */
		else {
			for (Vertex<L> vertex : vertices) {
				if (vertex.getWord().equals(source)) {
					tempWeight = vertex.getMap().get(target);
					vertex.removeEdge(target);
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
		if (vertices().contains(vertex)) {
			/* remove all vertex attached from the source vertex */
			for (Vertex<L> v : vertices) {
				if (v.getWord().equals(vertex)) {
					v.removeAllTargets();
					break;
				}
			}
			/* remove all vertex attaching to the source vertex */
			for (Vertex<L> v : vertices) {
				if (v.getMap().containsKey(vertex)) {
					v.removeEdge(vertex);
					break;
				}
			}
			for (Vertex<L> v : vertices) {
				if (v.getWord().equals(vertex)) {
					vertices.remove(v);
					break;
				}
			}
			checkRep();
			return true;
		}
		/* the vertex doesn't exist */
		else {
			checkRep();
			return false;
		}
	}

	@Override
	public Set<L> vertices() {
		Set<L> verticesSet = new HashSet<L>();
		for (Vertex<L> v : vertices) {
			verticesSet.add(v.getWord());
		}
		checkRep();
		return verticesSet;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> sourcesMap = new HashMap<L, Integer>();
		for (Vertex<L> v : vertices) {
			if (v.getMap().containsKey(target)) {
				sourcesMap.put(v.getWord(), v.getMap().get(target));
			}
		}
		checkRep();
		return sourcesMap;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> targetsMap = new HashMap<L, Integer>();
		for (Vertex<L> v : vertices) {
			if (v.getWord().equals(source)) {
				for (L target : v.getMap().keySet()) {
					targetsMap.put(target, v.getMap().get(target));
				}
			}
		}
		checkRep();
		return targetsMap;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Vertex<L> v : vertices) {
			sb.append(" " + v.toString());
		}
		return sb.toString();
	}

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {

	private final L word;
	private final Map<L, Integer> weightMap = new HashMap<L, Integer>();

	/* Abstraction function:
	 * 	AF(weightMap) = {v->weight | v is in vertices attached to by the vertex, weight is the weight of the edge}
	 * 	AF(word) = {the vertex's identity}
	 * 
	 * Representation invariant:
	 * 	word which represents the vertex's identity, shouldn't be null
	 * 	weightMap's value represents the weight of an edge and it must be positive
	 * 
	 * Safety from rep exposure:
	 * 	all representations are defined private and final
	 * 	getMap() which is the only Observer method, makes a defensive copy for return
	 * 
	 */

	// constructor
	public Vertex(L w) {
		this.word = w;
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert word != null;
		for (Integer weight : weightMap.values()) {
			assert weight > 0;
		}
	}

	/**
	 * Obeserver
	 * 
	 * @return a copy of weightMap
	 */
	public Map<L, Integer> getMap() {
		Map<L, Integer> weightMap = new HashMap<L, Integer>(this.weightMap);
		checkRep();
		return weightMap;
	}

	/**
	 * Observer
	 * 
	 * @return a copy of word
	 */
	public L getWord() {
		L word = this.word;
		checkRep();
		return word;
	}

	/**
	 * Mutator
	 * 
	 * add an edge into weightMap
	 * 
	 * @param t the target vertex of the edge
	 * @param w the weight of the edge
	 */
	public void addEdge(L t, int w) {
		weightMap.put(t, w);
		checkRep();
	}

	/**
	 * Mutator
	 * 
	 * set the weight of the edge
	 * 
	 * @param t the target vertex of the edge
	 * @param w the new weight of the edge
	 */
	public void setWeight(L t, int w) {
		this.weightMap.put(t, w);
		checkRep();
	}

	/**
	 * Mutator
	 * 
	 * remove the edge from weightMap
	 * 
	 * @param target the target vertex of the removed edge
	 */
	public void removeEdge(L target) {
		this.weightMap.remove(target);
		checkRep();
	}

	/**
	 * Mutator
	 * 
	 * remove all the edges from weightMap
	 */
	public void removeAllTargets() {
		for (L key : weightMap.keySet())
			weightMap.remove(key);
		checkRep();
	}

	@Override
	public String toString() {
		return this.word.toString();
	}

}
