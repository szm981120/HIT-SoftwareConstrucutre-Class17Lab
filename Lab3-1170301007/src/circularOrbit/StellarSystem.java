package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import atomTransitionMemento.Memento;
import centralObject.Star;
import physicalObject.ConcretePlanetFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;
import track.StellarTrackFactory;

/**
 * StellarSystem specifies readFromFile method.
 * 
 * @author Shen
 *
 */
public class StellarSystem extends ConcreteCircularOrbit<Star, PhysicalObject> {

	private static final String numberRegex = "([0-9]*|[0-9]*.[0-9]*|[0-9].[0-9]*e[0-9]*)";
	private static final String labelRegex = "([a-zA-Z0-9]*)";
	private static final String commaRegex = "\\s*,\\s*";

	/**
	 * Constructor
	 */
	public StellarSystem() {

	}

	/**
	 * Override readFromFile. Before reading from file, it's representations must be
	 * reset. This can read from legal stellar system data file.
	 */
	@Override
	public void readFromFile(File file) {
		super.readFromFile(file);
		Pattern pattern1, pattern2, pattern3;
		Matcher matcher1, matcher2, matcher3;
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String starName = null;
			double starRadius = 0, starMass = 0;
			String planetName = null, planetState = null, planetColor = null;
			double planetRadius = 0, trackLongRadius = 0, trackShortRadius = 0, revolutionSpeed = 0, originDegree = 0;
			boolean revolutionDirection = true;
			PlanetFactory planetFactory = new ConcretePlanetFactory();
			StellarTrackFactory stellarTrackFactory = new StellarTrackFactory();
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					pattern1 = Pattern.compile("Stellar\\s*::=\\s*" + "<" + labelRegex + commaRegex + numberRegex
							+ commaRegex + numberRegex + ">");
					matcher1 = pattern1.matcher(line);
					while (matcher1.find()) {
						starName = matcher1.group(1);
						starRadius = parseNumber(matcher1.group(2));
						starMass = parseNumber(matcher1.group(3));
						this.addCentralObject(new Star(starName, starRadius, starMass));
					}

					pattern2 = Pattern.compile("Planet\\s*::=\\s*" + "<" + labelRegex + commaRegex + labelRegex
							+ commaRegex + labelRegex + commaRegex + numberRegex + commaRegex + numberRegex + commaRegex
							+ numberRegex + commaRegex + numberRegex + commaRegex + "(CW|CCW)" + commaRegex
							+ numberRegex + ">");
					matcher2 = pattern2.matcher(line);
					while (matcher2.find()) {
						planetName = matcher2.group(1);
						planetState = matcher2.group(2);
						planetColor = matcher2.group(3);
						planetRadius = parseNumber(matcher2.group(4));
						trackLongRadius = parseNumber(matcher2.group(5));
						trackShortRadius = parseNumber(matcher2.group(6));
						revolutionSpeed = parseNumber(matcher2.group(7));
						revolutionDirection = matcher2.group(8).equals("CW") ? true : false;
						originDegree = parseNumber(matcher2.group(9));
						this.addTrack(stellarTrackFactory.produce(trackLongRadius, trackShortRadius));
						this.addPhysicalObjectToTrack(
								planetFactory.produce(planetName, planetState, planetColor, planetRadius,
										revolutionSpeed, revolutionDirection, originDegree),
								stellarTrackFactory.produce(trackLongRadius, trackShortRadius));
					}

					pattern3 = Pattern.compile("Planet\\s*::=\\s*" + "<" + labelRegex + commaRegex + labelRegex
							+ commaRegex + labelRegex + commaRegex + numberRegex + commaRegex + numberRegex + commaRegex
							+ numberRegex + commaRegex + "(CW|CCW)" + commaRegex + numberRegex + ">");
					matcher3 = pattern3.matcher(line);
					while (matcher3.find()) {
						planetName = matcher3.group(1);
						planetState = matcher3.group(2);
						planetColor = matcher3.group(3);
						planetRadius = parseNumber(matcher3.group(4));
						trackLongRadius = parseNumber(matcher3.group(5));
						revolutionSpeed = parseNumber(matcher3.group(6));
						revolutionDirection = matcher3.group(7).equals("CW") ? true : false;
						originDegree = parseNumber(matcher3.group(8));
						this.addTrack(stellarTrackFactory.produce(trackLongRadius, trackLongRadius));
						this.addPhysicalObjectToTrack(
								planetFactory.produce(planetName, planetState, planetColor, planetRadius,
										revolutionSpeed, revolutionDirection, originDegree),
								stellarTrackFactory.produce(trackLongRadius, trackLongRadius));
					}
				}
				br.close();
			} catch (Exception e) {

			}
		} catch (Exception e) {

		}
	}

	/**
	 * Turn a notation number string into a real number. If this notation doesn't
	 * contain "e", then it's not a scientific notation. If it's not a scientific
	 * notation number, turn it into real number directly, else call parseScientific
	 * method to parse scientific notation.
	 * 
	 * @param s a number notation string
	 * @return real number of s
	 * @throws Exception deal with some exception when s disobeys scientific
	 *                   notation rules.
	 */
	public static double parseNumber(String s) throws Exception {
		if (s.contains("e")) {
			return parseScientific(s);
		} else {
			return Double.parseDouble(s);
		}
	}

	/**
	 * Turn a scientific notation number string into a real number.
	 * 
	 * @param s a scientific notation number string
	 * @return real number of s
	 * @throws Exception deal with some exception when s disobeys scientific
	 *                   notation rules.
	 */
	private static double parseScientific(String s) throws Exception {
		double mantissa;
		int exponent;
		String[] s_ = s.split("e");
		mantissa = Double.parseDouble(s_[0]);
		exponent = Integer.parseInt(s_[1]);
		if (!(mantissa >= 1 && mantissa < 10)) {
			throw new Exception("The mantissa is not between 1 and 10.");
		}
		if (exponent < 4) {
			throw new Exception("The exponent is less than 4.");
		}
		double result = mantissa * Math.pow(10, exponent);
		return result;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public void constructSocialNetworkCircle(List<PhysicalObject> friends, List<Edge<PhysicalObject>> physicalEdges,
			Map<PhysicalObject, Double>[] centralEdges) {
		assert false : "shouldn't call this method";
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public Memento<Star, PhysicalObject> save() {
		assert false : "shouldn't have this method";
		return null;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public void restore(Memento<Star, PhysicalObject> m) {
		assert false : "shouldn't have this method";
	}
}
