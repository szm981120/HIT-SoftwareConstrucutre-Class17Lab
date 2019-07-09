package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Shen
 *
 */
public class FriendshipGraph {
	public HashMap<Person, ArrayList<Person>> adj; // 邻接表

	/**
	 * construct a FriendshipGraph object while initializing adj
	 */
	public FriendshipGraph() {
		this.adj = new HashMap<Person, ArrayList<Person>>();
	}

	/**
	 * 
	 * @param person a Person object added into graph
	 * @throws Exception
	 */
	public void addVertex(Person person) throws Exception {
		for (Person p : adj.keySet()) {
			if (p.getName().equalsIgnoreCase(person.getName())) {
				throw new Exception(person.getName() + " has already existed!");
			}
		}
		ArrayList<Person> newArray = new ArrayList<Person>();
		this.adj.put(person, newArray);
	}

	/**
	 * 
	 * @param p1 one of the two connected people
	 * @param p2 the other of the two connected people
	 * @throws Exception
	 */
	public void addEdge(Person p1, Person p2) throws Exception {
		if (adj.containsKey(p1)) {
			adj.get(p1).add(p2);
		} else {
			throw new Exception("Person doesn't exist!");
		}
	}

	/**
	 * 
	 * @param p1 the person who connects
	 * @param p2 the person who is connected
	 * @return the distance between the two people p1 and p2
	 */
	public int getDistance(Person p1, Person p2) {
		int distance = 0; // 距离初始化
		Queue<Person> queue = new LinkedList<Person>(); // BFS遍历过程的队列
		/*
		 * edgeTo映射：假设A映射到B，那么表示A与B有直接关系 且在由源点到目标点的有向路径中，A在B的后面，即B是A的前置
		 */
		HashMap<Person, Person> edgeTo = new HashMap<Person, Person>();
		/* 所有人遍历标记置假 */
		for (Person p : adj.keySet())
			p.marked = false;
		p1.marked = true; // 源点遍历标记置真
		queue.add(p1); // 源点加入队列
		/* BFS遍历，得到edgeTo */
		while (!queue.isEmpty()) {
			Person tempPerson = queue.remove();
			/* 对出队的元素的所有邻接点遍历 */
			for (Person p : adj.get(tempPerson)) {
				if (!p.marked) { // 找到了一个没有遍历过的点
					edgeTo.put(p, tempPerson); // 记录前置后置关系
					p.marked = true; // 遍历标记置真
					queue.add(p);	// 一个新的遍历过的点入队
				}
			}
		}
		/* 此时BFS遍历完成，用edgeTo来反向回溯源点到目标点的路径，同时计算路径长度 */
		if (!p2.marked)
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
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		System.out.println(graph.getDistance(rachel, ben));
		System.out.println(graph.getDistance(rachel, rachel));
		System.out.println(graph.getDistance(rachel, kramer));
	}
}
