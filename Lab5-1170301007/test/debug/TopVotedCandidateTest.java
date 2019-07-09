package debug;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TopVotedCandidateTest {

	/*
	 * Test strategy
	 * 	The algorithm is based on dichotomy. The factors which may affect algorithm may be numbers of candidates, 
	 * 	order of votes, query time. The test strategy tests default cases that the example indicates in spec, 
	 * 	a few equivalence cases, and a few assertion cases.
	 * 
	 * 	testDefault
	 * 		A case that example 1 indicates in spec.
	 * 	testNonAlternateVotes
	 * 		The order of votes is not alternate like the situation in default case.
	 * 	testMultiCandidates
	 * 		There are 4 candidates in persons. The order of votes is alternate.
	 * 	testMultiCandidatesNonAlternateVotes
	 * 		There are 4 candidates in persons. The order of votes is not alternate.
	 * 	testCasualTimes
	 * 		Vote times are casual not like the situation in default case.
	 * 	testPersonsTimesNotSameLength
	 * 		Test AssertionError when persons.length is not equal to times.length.
	 * 	testPersonsLengthLessThanOne
	 * 		Test AssertionError when persons.length is less than 1.
	 * 	testTimesLengthGreaterThanFiveThousand
	 * 		Test AssertionError when times.length is greater than 5000.
	 * 	testPersoniLessThanZero
	 * 		Test AssertionError when Persons[i] is less than 0.
	 * 	testPersoniGreaterThanPersonLength
	 * 		Test AssertionError when persons[i] is greater than persons.length.
	 * 	testTimeiLessThanZero
	 * 		Test AssertionError when times[i] is less than 0.
	 * 	testTimeiGreaterThanABillon
	 * 		Test AssertionError when times[i] is greater than 10e9.
	 * 	testTimesNotStrictlyIncreasing
	 * 		Test AssertionError when times is not a strictly increasing array.
	 * 	testParameterTLessThanTimes0
	 * 		Test AssertionError when parameter t in TopVotedCandidate.q(int t) is less than times[0].
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testDefault() {
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(0));
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(5));
		assertEquals(1, tvc.q(10));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
		assertEquals(0, tvc.q(20));
		assertEquals(1, tvc.q(25));
		assertEquals(0, tvc.q(30));
	}

	@Test
	public void testNonAlternateVotes() {
		int[] persons = { 0, 0, 1, 0, 1, 1, 1 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(0));
		assertEquals(0, tvc.q(5));
		assertEquals(0, tvc.q(10));
		assertEquals(0, tvc.q(15));
		assertEquals(0, tvc.q(20));
		assertEquals(1, tvc.q(25));
		assertEquals(1, tvc.q(30));
	}

	@Test
	public void testMultiCandidates() {
		int[] persons = { 0, 1, 2, 1, 0, 2, 2, 3, 3, 3 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30, 35, 40, 45 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(0));
		assertEquals(1, tvc.q(5));
		assertEquals(2, tvc.q(10));
		assertEquals(1, tvc.q(15));
		assertEquals(0, tvc.q(20));
		assertEquals(2, tvc.q(25));
		assertEquals(2, tvc.q(30));
		assertEquals(2, tvc.q(35));
		assertEquals(2, tvc.q(40));
		assertEquals(3, tvc.q(45));
	}

	@Test
	public void testMultiCandidatesNonAlternateVotes() {
		int[] persons = { 0, 0, 1, 1, 3, 3, 2, 2, 2, 3 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30, 35, 40, 45 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(0));
		assertEquals(0, tvc.q(5));
		assertEquals(0, tvc.q(10));
		assertEquals(1, tvc.q(15));
		assertEquals(1, tvc.q(20));
		assertEquals(3, tvc.q(25));
		assertEquals(3, tvc.q(30));
		assertEquals(2, tvc.q(35));
		assertEquals(2, tvc.q(40));
		assertEquals(3, tvc.q(45));
	}

	@Test
	public void testCasualTimes() {
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = { 4, 7, 13, 14, 28, 102, 333 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(4));
		assertEquals(0, tvc.q(5));
		assertEquals(1, tvc.q(7));
		assertEquals(1, tvc.q(13));
		assertEquals(0, tvc.q(14));
		assertEquals(0, tvc.q(28));
		assertEquals(0, tvc.q(50));
		assertEquals(1, tvc.q(102));
		assertEquals(0, tvc.q(333));
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPersonsTimesNotSameLength() {
		exception.expect(AssertionError.class);
		exception.expectMessage("1 <= persons.length = times.length <= 5000");
		int[] persons = { 0, 1, 1, 0, 0, 1, 0, 1 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testPersonsLengthLessThanOne() {
		exception.expect(AssertionError.class);
		exception.expectMessage("1 <= persons.length = times.length <= 5000");
		int[] persons = {};
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testTimesLengthGreaterThanFiveThousand() {
		exception.expect(AssertionError.class);
		exception.expectMessage("1 <= persons.length = times.length <= 5000");
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = new int[5001];
		for (int i = 0; i < 5001; i++) {
			times[i] = i;
		}
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testPersoniLessThanZero() {
		exception.expect(AssertionError.class);
		exception.expectMessage("0 <= persons[i] <= persons.length");
		int[] persons = { 0, -1, 1, 0, 0, 1, 0 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testPersoniGreaterThanPersonLength() {
		exception.expect(AssertionError.class);
		exception.expectMessage("0 <= persons[i] <= persons.length");
		int[] persons = { 0, 10, 1, 0, 0, 1, 0 };
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testTimeiLessThanZero() {
		exception.expect(AssertionError.class);
		exception.expectMessage("times is a strictly increasing array with all elements in [0, 10^9]");
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = { 0, -5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testTimeiGreaterThanABillon() {
		exception.expect(AssertionError.class);
		exception.expectMessage("times is a strictly increasing array with all elements in [0, 10^9]");
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = { 0, 5, 1000000001, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testTimesNotStrictlyIncreasing() {
		exception.expect(AssertionError.class);
		exception.expectMessage("times is a strictly increasing array with all elements in [0, 10^9]");
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = { 0, 5, 10, 5, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(3));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}

	@Test
	public void testParameterTLessThanTimes0() {
		exception.expect(AssertionError.class);
		exception.expectMessage("TopVotedCandidate.q(int t) is always called with t >= times[0].");
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
		int[] times = { 3, 5, 10, 15, 20, 25, 30 };
		TopVotedCandidate tvc = new TopVotedCandidate(persons, times);
		assertEquals(0, tvc.q(2));
		assertEquals(1, tvc.q(12));
		assertEquals(0, tvc.q(15));
	}
}
