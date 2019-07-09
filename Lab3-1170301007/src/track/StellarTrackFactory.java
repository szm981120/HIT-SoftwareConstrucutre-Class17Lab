package track;

/**
 * StellarTrackFactory provides a method to create a StellarTrack instance.
 * 
 * @author Shen
 *
 */
public class StellarTrackFactory {

	/**
	 * Constructor
	 */
	public StellarTrackFactory() {

	}

	/**
	 * Create a stellar track.
	 * 
	 * @param longRadius  long radius of the track
	 * @param shortRadius short radius of the track
	 * @return a StellarTrack instance
	 */
	public Track produce(double longRadius, double shortRadius) {
		return new StellarTrack(longRadius, shortRadius);
	}

}
