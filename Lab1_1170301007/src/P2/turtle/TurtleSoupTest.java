/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TurtleSoupTest {

	/**
	 * Tests that assertions are enabled.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	/**
	 * Tests calculateRegularPolygonAngle.
	 */
	@Test
	public void calculateRegularPolygonAngleTest() {
		assertEquals(60.0, TurtleSoup.calculateRegularPolygonAngle(3), 0.001);
		assertEquals(128.57, TurtleSoup.calculateRegularPolygonAngle(7), 0.01);
		assertEquals(108.0, TurtleSoup.calculateRegularPolygonAngle(5), 0.001);
	}

	/**
	 * Tests calculatePolygonSidesFromAngle.
	 */
	@Test
	public void calculatePolygonSidesFromAngleTest() {
		assertEquals(3, TurtleSoup.calculatePolygonSidesFromAngle(60.0));
		assertEquals(7, TurtleSoup.calculatePolygonSidesFromAngle(128.57));
		assertEquals(5, TurtleSoup.calculatePolygonSidesFromAngle(108.0));

	}

	/**
	 * Tests calculateBearingToPoint.
	 */
	@Test
	public void calculateBearingToPointTest() {
		assertEquals(0.0, TurtleSoup.calculateBearingToPoint(0.0, 0, 0, 0, 1), 0.001);
		assertEquals(90.0, TurtleSoup.calculateBearingToPoint(0.0, 0, 0, 1, 0), 0.001);
		assertEquals(359.0, TurtleSoup.calculateBearingToPoint(1.0, 4, 5, 4, 6), 0.001);
	}

	/**
	 * Tests calculateBearings.
	 */
	@Test
	public void calculateBearingsTest() {
		List<Integer> xpoints = new ArrayList<>();
		List<Integer> ypoints = new ArrayList<>();
		xpoints.add(0);
		xpoints.add(1);
		xpoints.add(1);
		ypoints.add(0);
		ypoints.add(1);
		ypoints.add(2);

		List<Double> result = TurtleSoup.calculateBearings(xpoints, ypoints);
		assertEquals(2, result.size());
		assertEquals(45.0, result.get(0), 0.001);
		assertEquals(315.0, result.get(1), 0.001);
	}

	/**
	 * Tests convexHull.
	 */
	@Test
	public void convexHullTest() {
		Set<Point> points = new HashSet<Point>();
		Set<Point> convexHull = new HashSet<Point>();

		assertEquals(convexHull, TurtleSoup.convexHull(points));
		
		Point p31 = new Point(3, 1);
		Point p41 = new Point(4, 1);
		Point p51 = new Point(5, 1);
		Point p62 = new Point(6, 2);
		Point p63 = new Point(6, 3);
		Point p64 = new Point(6, 4);
		Point p65 = new Point(6, 5);
		Point p66 = new Point(6, 6);
		Point p56 = new Point(5, 6);
		Point p46 = new Point(4, 6);
		Point p36 = new Point(3, 6);
		Point p26 = new Point(2, 6);
		Point p16 = new Point(1, 6);
		Point p15 = new Point(1, 5);
		Point p14 = new Point(1, 4);

		points.add(p31);
		points.add(p41);
		points.add(p51);
		points.add(p62);
		points.add(p63);
		points.add(p64);
		points.add(p65);
		points.add(p66);
		points.add(p56);
		points.add(p46);
		points.add(p36);
		points.add(p26);
		points.add(p16);
		points.add(p15);
		points.add(p14);
		
		convexHull.add(p31);
		convexHull.add(p51);
		convexHull.add(p62);
		convexHull.add(p66);
		convexHull.add(p16);
		convexHull.add(p14);
		
		
		assertEquals(convexHull, TurtleSoup.convexHull(points));
		
//		Point p11 = new Point(1, 1);
//		Point p1010 = new Point(10, 10);
//		Point p110 = new Point(1, 10);
//		Point p12 = new Point(1, 2);
//		Point p23 = new Point(2, 3);
//		Point p32 = new Point(3, 2);
//
//		points.add(p11);
//		convexHull.add(p11);
//		assertEquals(convexHull, TurtleSoup.convexHull(points));
//
//		points.add(p1010);
//		convexHull.add(p1010);
//		assertEquals(convexHull, TurtleSoup.convexHull(points));
//
//		points.add(p110);
//		convexHull.add(p110);
//		assertEquals(convexHull, TurtleSoup.convexHull(points));
//
//		points.add(p12);
//		assertEquals(convexHull, TurtleSoup.convexHull(points));
//
//		points.add(p23);
//		assertEquals(convexHull, TurtleSoup.convexHull(points));
//
//		points.add(p32);
//		convexHull.add(p32);
//		assertEquals(convexHull, TurtleSoup.convexHull(points));
	}
}
