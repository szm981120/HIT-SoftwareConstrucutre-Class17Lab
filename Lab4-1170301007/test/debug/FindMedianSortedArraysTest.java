package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FindMedianSortedArraysTest {

	/*
	 * Test strategy
	 * 	The algorithm is based on dichotomy. The factors which affect algorithm are A.length and B.length,
	 * 	whether the sum of A.length and B.length is odd or even, that critical situation where A.length or B.length 
	 * 	may be 1. Besides, whether A appended by B is strictly sorted may affect the result. And some elements in A or B 
	 * 	may be equal, which probably affects the result.
	 * 
	 * 	testSameLengthStrictlySorted
	 * 		A and B have the same length and A appended by B is strictly sorted.
	 * 	testNotSameOddLengthStrictlySorted
	 * 		A and B have different length. The whole length is odd. A appended by B is strictly sorted.
	 * 	testNotSameEvenLengthStrictlySorted
	 * 		A and B have different length. The whole length is even. A appended by B is strictly sorted.
	 * 	testSameLengthNotStrictlySorted
	 * 		A and B have the same length and A appended by B is not strictly sorted.
	 * 	testNotSameOddLengthNotStrictlySorted
	 * 		A and B have different length. The whole length is odd. A appended by B is not strictly sorted.
	 * 	testNotSameEvenLengthNotStrictlySorted
	 * 		A and B have different length. The whole length is even. A appended by B is not strictly sorted.
	 * 	testSmallLength
	 * 		Analyzing variables when debugging, when index variable equals 0 or length - 1, it may cause critial situation
	 * 		and it can affect result. Therefore, let A.length = 1 and test.
	 * 	testEitherEqualElementEvenLength
	 * 		Elements in either A or B are all equal. The whole length is even. 
	 * 	testEitherEqualElementOddLength
	 * 		Elements in either A or B are all equal. The whole length is odd. 
	 * 	testBothEqualElementEvenLength
	 * 		Elements in both A and B are all equal. The whole length is even.
	 * 	testBothEqualsElementOddLength
	 * 		Elements in both A and B are all equal. The whole length is odd.
	 * 	
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testSameLengthStrictlySorted() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 2 };
		int[] B = { 3, 4 };
		assertEquals(2.5, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testNotSameOddLengthStrictlySorted() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 2, 3 };
		int[] B = { 4, 5 };
		assertEquals(3.0, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testNotSameEvenLengthStrictlySorted() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 2 };
		int[] B = { 3, 4, 5, 6 };
		assertEquals(3.5, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testSameLengthNotStrictlySorted() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 4 };
		int[] B = { 2, 3 };
		assertEquals(2.5, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testNotSameOddLengthNotStrictlySorted() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 3, 5 };
		int[] B = { 2, 6 };
		assertEquals(3.0, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testNotSameEvenLengthNotStrictlySorted() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 7 };
		int[] B = { 2, 3, 6, 8 };
		assertEquals(4.5, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testSmallLength() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 2 };
		int[] B = { 1, 3 };
		assertEquals(2.0, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testEitherEqualElementEvenLength() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 1, 1 };
		int[] B = { 5, 6, 7 };
		assertEquals(3.0, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testEitherEqualElementOddLength() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 3, 3, 3 };
		int[] B = { 2, 5, 6, 7 };
		assertEquals(3.0, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testBothEqualElementEvenLength() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 1, 1 };
		int[] B = { 5, 5, 5 };
		assertEquals(3.0, app.findMedianSortedArrays(A, B), 0);
	}

	@Test
	public void testBothEqualsElementOddLength() {
		FindMedianSortedArrays app = new FindMedianSortedArrays();
		int[] A = { 1, 1, 1, };
		int[] B = { 5, 5, 5, 5 };
		assertEquals(5.0, app.findMedianSortedArrays(A, B), 0);
	}

}
