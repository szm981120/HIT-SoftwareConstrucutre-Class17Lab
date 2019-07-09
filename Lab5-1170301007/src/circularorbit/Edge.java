package circularorbit;

/**
 * Edge represents an adjacency with weight.
 * 
 * @author Shen
 *
 * @param <E> class of physical object
 */
public class Edge<E> {

  // IMMUTABLE
  private final E source;

  private final E target;

  private final double weight;

  /*
   * Abstraction function: AF(source, target, weight) = {an edge weighing weight from source to
   * target}
   * 
   * Representation invariant: source which represents an edge's start, shouldn't be null target
   * which represents an edge's end, shouldn't be null weight which represents an edge's weight,
   * should be a decimal from 0 to 1
   * 
   * Safety from rep exposure: all representations are defined private and final. E(type) is
   * immutable according to spec so all reps are immutable.
   */
  /**
   * Constructor.
   * 
   * @param s source physical object, mustn't be null
   * @param t target physical object, mustn't be null
   * @param w weight of this edge, must be a decimal greater than 0 and no more than 1.
   */
  public Edge(E s, E t, double w) {
    this.source = s;
    this.target = t;
    this.weight = w;
  }

  /**
   * checkRep.
   */
  private void checkRep() {
    assert weight > 0 && weight <= 1;
    assert source != null;
    assert target != null;
  }

  /**
   * Observer.
   *
   * @return source physical object of this edge
   */
  public E getSource() {
    E source = this.source;
    checkRep();
    return source;
  }

  /**
   * Observer.
   * 
   * @return target physical object of this edge
   */
  public E getTarget() {
    E target = this.target;
    checkRep();
    return target;
  }

  /**
   * Observer.
   * 
   * @return weight of this edge
   */
  public double getWeight() {
    double weight = this.weight;
    checkRep();
    return weight;
  }

  /**
   * Two edges are equal only when they have the same source and the same target.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    return obj != null && obj.getClass() == Edge.class
        && ((((Edge<E>) obj).getSource().equals(this.source)
            && ((Edge<E>) obj).getTarget().equals(this.target))
            || (((Edge<E>) obj).getSource().equals(this.target)
                && ((Edge<E>) obj).getTarget().equals(this.source)));
  }

  /**
   * Because the equality of edges links with equality of source and target, hash code of edge
   * should be a particular combination of source's and target's hash code.
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.source.hashCode();
    result = 31 * result + this.target.hashCode();
    return result;
  }

  /**
   * Override toString. Print the Edge.
   */
  @Override
  public String toString() {
    StringBuilder edge = new StringBuilder();
    edge.append(this.source.toString() + "--" + String.valueOf(this.weight) + "-->"
        + this.target.toString());
    checkRep();
    return edge.toString();
  }
}
