package track;

import java.util.Objects;

/**
 * 
 * @author Shen
 *
 */
public class StellarTrack implements Track {
	// IMMUTABLE
	// factory method
	private final double longRadius;
	private final double shortRadius;

	/*
	 * Abstraction function:
	 *  AF(longRadius) = long radius of this track
	 *  AF(shortRadius) = short radius of this track
	 *  
	 * Representation invariant:
	 * 	long radius must be positive
	 * 	short radius must be positive
	 * 
	 * Safety from rep exposure:
	 * 	All representations are defined private and final.
	 * 	All representations in Observer are immutable.
	 */

	// checkRep
	private void checkRep() {
		assert this.longRadius > 0;
		assert this.shortRadius > 0;
	}

	/**
	 * Constructor
	 * 
	 * @param longRadius  long radius of track
	 * @param shortRadius short radius of track
	 */
	public StellarTrack(double longRadius, double shortRadius) {
		this.longRadius = longRadius;
		this.shortRadius = shortRadius;
		checkRep();
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public double getRadius() {
		assert false : "shouldn't call this method";
		return 0;
	}

	@Override
	public double getLongRadius() {
		return this.longRadius;
	}

	@Override
	public double getShortRadius() {
		return this.shortRadius;
	}

	/**
	 * Two tracks are equals only when they have the same long radius and short
	 * radius.
	 */
	@Override
	public boolean equals(Object track) {
		return track.getClass() == StellarTrack.class
				&& Double.doubleToLongBits(this.longRadius) == Double
						.doubleToLongBits(((StellarTrack) track).longRadius)
				&& Double.doubleToLongBits(this.shortRadius) == Double
						.doubleToLongBits(((StellarTrack) track).shortRadius);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.longRadius, this.shortRadius);
	}

}
