package track;

import java.util.Objects;

/**
 * 
 * @author Shen
 *
 */
public class NormalTrack implements Track {
	// IMMUTABLE
	// factory method
	private final double radius;

	/*
	 * Abstraction function:
	 *  AF(radius) = radius of this track
	 *  
	 * Representation invariant:
	 * 	radius must be positive
	 * 
	 * Safety from rep exposure:
	 * 	All representations are defined private and final.
	 * 	All representations in Observer are immutable.
	 */

	// checkRep
	private void checkRep() {
		assert this.radius > 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param radius radius of track
	 */
	public NormalTrack(double radius) {
		this.radius = radius;
		checkRep();
	}

	@Override
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Two tracks are equals only when they have the same radius.
	 */
	@Override
	public boolean equals(Object track) {
		return track.getClass() == NormalTrack.class
				&& Double.doubleToLongBits(this.radius) == Double.doubleToLongBits(((NormalTrack) track).radius);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.radius);
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public double getLongRadius() {
		assert false : "shouldn't call this method";
		return 0;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public double getShortRadius() {
		assert false : "shouldn't call this method";
		return 0;
	}
}
