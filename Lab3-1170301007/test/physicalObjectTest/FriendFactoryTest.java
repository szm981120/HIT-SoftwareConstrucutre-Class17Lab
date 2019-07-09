package physicalObjectTest;

import static org.junit.Assert.*;

import org.junit.Test;

import physicalObject.ConcreteFriendFactory;
import physicalObject.Friend;
import physicalObject.FriendFactory;

public class FriendFactoryTest {

	
	/*
	 * Test strategy
	 * 	testProduce
	 * 		This tests produce.
	 * 		Call produce to create an instance to see if the instance fits expectation.
	 */
	
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testProduce() {
		FriendFactory friendFactory = new ConcreteFriendFactory();
		assertEquals("expected Friend class", Friend.class, friendFactory.produce("Wanghang", 20, 'M').getClass());
	}

}
