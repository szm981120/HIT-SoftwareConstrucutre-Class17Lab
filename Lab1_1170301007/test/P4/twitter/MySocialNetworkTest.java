package P4.twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MySocialNetworkTest {

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "alyssa",
			"is it reasonable to talk about rivest so much.@jackma @bbitdiddle?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(3, "jackma", "I @jackMa don't like money really @Wangjianlin @ALYSSA",
			d3);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork
				.smarterGuessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3));

		assertFalse("expected non-empty graph", followsGraph.isEmpty());
		assertEquals(4, followsGraph.size());
		assertTrue("alyssa should follow jackma and bbitdiddle",
				followsGraph.get("alyssa").contains("jackma") && followsGraph.get("alyssa").contains("bbitdiddle"));
		assertTrue("expected connection between bbitdiddle and jackma",
				followsGraph.get("jackma").contains("bbitdiddle") && followsGraph.get("bbitdiddle").contains("jackma"));
	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<String, Set<String>>();
		followsGraph = SocialNetwork.smarterGuessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3));
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertFalse("expected non-empty list", influencers.isEmpty());
		assertTrue("The top is expected to be jackma or alyssa",
				influencers.get(0).equals("alyssa") || influencers.get(0).equals("jackma"));
	}

}
