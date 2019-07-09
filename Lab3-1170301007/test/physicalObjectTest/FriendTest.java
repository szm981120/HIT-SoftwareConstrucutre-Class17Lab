package physicalObjectTest;

import static org.junit.Assert.*;

import org.junit.Test;

import physicalObject.ConcreteFriendFactory;
import physicalObject.FriendFactory;
import physicalObject.PhysicalObject;

public class FriendTest {

	private static FriendFactory friendFactory = new ConcreteFriendFactory();
	private static PhysicalObject friend = friendFactory.produce("Hanxiao", 20, 'M');

	/*
	 * Test strategy
	 * 	testGetName
	 * 		This tests getName.
	 * 		Call getName to see if the result fits expectation.
	 * 	testGetDegree
	 * 		This tests getDegree.
	 * 		Because the degree of friend is generated randomly so there's no an expectation result.
	 * 		The degree test actually has been ensured by checkRep. This test has no necessary.
	 * 	testEquals
	 * 		This tests equals.
	 * 		Two friends are equal only when they have the same name, age and sex.
	 * 		Generate two friends who have the same name, age and sex and see if they are equal.
	 * 		Generate two friends whose name, age and sex are not all the same, and see if they are not equal.
	 * 	testHashCode
	 * 		This tests hashCode
	 * 		Two friends' hash codes are equal only when they are equal.  
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testGetName() {
		assertEquals("expected Hanxiao", "Hanxiao", friend.getName());
	}

	@Test
	public void testGetDegree() {
		double d = friend.getDegree();
		assertTrue(d >= 0 && d < 360);
	}

	@Test
	public void testEquals() {
		PhysicalObject friend_ = friendFactory.produce("Hanxiao", 20, 'M');
		PhysicalObject friend__ = friendFactory.produce("Hanxiao", 21, 'M');
		assertTrue(friend_.equals(friend));
		assertFalse(friend.equals(friend__));
	}

	@Test
	public void testHashCode() {
		PhysicalObject friend_ = new ConcreteFriendFactory().produce("Hanxiao", 20, 'M');
		PhysicalObject friend__ = friendFactory.produce("Hanxiao", 21, 'M');
		assertEquals(friend.hashCode(), friend_.hashCode());
		assertNotEquals(friend.hashCode(), friend__.hashCode());
	}

}
