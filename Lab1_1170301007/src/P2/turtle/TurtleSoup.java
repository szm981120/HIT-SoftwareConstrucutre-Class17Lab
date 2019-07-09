/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;

import java.util.Set;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import P2.turtle.Turtle;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle     the turtle context
	 * @param sideLength length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		for (int i = 0; i < 4; i++) {
			turtle.forward(sideLength);
			turtle.turn(90);
		}
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		double angle = 180 - (double) 360 / sides;
		return angle;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		int sides = (int) Math.round(360 / (180 - angle));
		return sides;
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle     the turtle context
	 * @param sides      number of sides of the polygon to draw
	 * @param sideLength length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		double angle = calculateRegularPolygonAngle(sides);
		if(sides == 3)
			turtle.turn(30);
		else if(sides == 4) {
			
		}else
			turtle.turn(450-angle);
		for(int i=1; i<sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(angle);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		/* 对于当前点和目标点的方位作分析，可以大致分为四种情况 */
		/* 该问题分析时本分为8种情况，根据结果对各个情况合并，得到四种情况 */
		/* 两点互为上下方位 */
		if (currentX == targetX) {
			if (currentY > targetY) // 目标点在下面
				return (currentBearing >= 0 && currentBearing <= 180) ? (180 - currentBearing) : (540 - currentBearing);
			else // 目标点在上面
				return (360 - currentBearing) % 360; // 对360取余是因为如果currentBearing=0那么结果应该是0而不是360
		}
		/* 两点互为左右方位 */
		if (currentY == targetY) {
			if (currentX > targetX) // 目标点在左侧
				return (currentBearing >= 0 && currentBearing <= 270) ? (270 - currentBearing) : (630 - currentBearing);
			else // 目标点在右侧
				return (currentBearing >= 0 && currentBearing <= 90) ? (90 - currentBearing) : (450 - currentBearing);
		}
		/* beta是当前点和目标点连线与水平线所夹锐角，取值范围由-90°到90° */
		double beta = Math.atan((double) (currentY - targetY) / (currentX - targetX)) / Math.PI * 180;
		if (currentX > targetX) // 目标点在左上或左下
			return (currentBearing >= 0 && currentBearing <= 270) ? (270 - currentBearing - beta)
					: (630 - currentBearing - beta);
		else { // 目标点在右上或右下
			return (currentBearing >= 0 && currentBearing <= 90) ? (90 - currentBearing - beta)
					: (450 - currentBearing - beta);
		}
	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords list of x-coordinates (must be same length as yCoords)
	 * @param yCoords list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		List<Double> bearings = new ArrayList<Double>();
		double currentBearing = 0; // 初始朝向为y正半轴
		for (int i = 0; i < xCoords.size() - 1; i++) { // 结果列表中要有n-1个元素
			/* 复用calculateBearingToPoint方法来计算到下一个目标点的旋转角度 */
			bearings.add(calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1),
					yCoords.get(i + 1)));
			/* 更新到达下一个目标点后的朝向 */
			if (xCoords.get(i) == xCoords.get(i + 1)) // 两点是上下方位
				currentBearing = yCoords.get(i) > yCoords.get(i + 1) ? 180 : 0;
			else if (yCoords.get(i) == yCoords.get(i + 1)) { // 两点是左右方位
				currentBearing = xCoords.get(i) > xCoords.get(i + 1) ? 270 : 90;
			} else { // 两点非正方位
				double beta = Math
						.atan((double) (yCoords.get(i) - yCoords.get(i + 1)) / (xCoords.get(i) - xCoords.get(i + 1)))
						/ Math.PI * 180;
				if (yCoords.get(i) > yCoords.get(i + 1))	// 目标点在下面
					currentBearing = 180 + beta;
				else // 目标点在上面
					currentBearing = xCoords.get(i) > xCoords.get(i + 1) ? 360 + beta : beta;
			}
		}
		return bearings;
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points a set of points with xCoords and yCoords. It might be empty,
	 *               contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	public static Set<Point> convexHull(Set<Point> points) {
		Set<Point> hullPoints = new HashSet<Point>(); // 凸包点集
		Iterator<Point> iterator = points.iterator(); // 给定点集迭代器
		/* p1,p2,p3分别是遍历过程中，按序找到的凸包点 */
		Point p1 = iterator.hasNext() ? iterator.next() : null;
		Point p2 = null, p3 = null;
		Point next; // 迭代变量

		/* 给定点集不为空，否则退出 */
		if (points.size() > 0) {
			/* 开始迭代，寻找起点 */
			iterator = points.iterator();
			while (iterator.hasNext()) {
				next = iterator.next();
				/* 找到给定点集最左下的点作为起点 */
				if (next.y() < p1.y())
					p1 = next;
				else if (next.y() == p1.y() && next.x() < p1.x())
					p1 = next;
			}
			hullPoints.add(p1); // 起点加入凸包
			/* 给定点集有2个及更多的点 */
			if (points.size() > 1) {
				/* 开始迭代，寻找一个与起点不同的点作为第二个点 */
				iterator = points.iterator();
				p2 = iterator.next();
				while (p2.equals(p1))
					p2 = iterator.next();
				/* 迭代，寻找满足条件的第二个点 */
				iterator = points.iterator();
				while (iterator.hasNext()) {
					next = iterator.next();
					/* 条件：与起点不同，且与起点连线与x正半轴夹角最小，即夹角cos值最大 */
					if (!next.equals(p1) && !next.equals(p2) && cosBetweenTwoPoints(p1.x(), p1.y(), next.x(),
							next.y()) > cosBetweenTwoPoints(p1.x(), p1.y(), p2.x(), p2.y())) {
						p2 = next;
					}
					/* 还要满足在共线的点中，距离起点最远 */
					else if (cosBetweenTwoPoints(next.x(), next.y(), p1.x(), p1.y()) == cosBetweenTwoPoints(p2.x(),
							p2.y(), p1.x(), p1.y())
							&& distanceBetweenTwoPoints(next.x(), next.y(), p1.x(),
									p1.y()) > distanceBetweenTwoPoints(p2.x(), p2.y(), p1.x(), p1.y())) {
						p2 = next;
					}
				}
				hullPoints.add(p2); // 第二个点加入凸包
				/* 给定点集有多于2个点，这时凸包是凸多边形 */
				if (points.size() > 2) {
					/* 永真循环，直到遍历回到起点跳出循环 */
					while (true) {
						/* 迭代，寻找下一个点 */
						iterator = points.iterator();
						p3 = iterator.next();
						/* 寻找满足条件的下一个点 */
						while (iterator.hasNext()) {
							next = iterator.next();
							/* 与前两个点不同 */
							if (next.equals(p1) || next.equals(p2))
								continue;
							/* P3P2与P2P1夹角最小 */
							if (cosFromP1P2ToP2P3(p1.x(), p1.y(), p2.x(), p2.y(), next.x(),
									next.y()) > cosFromP1P2ToP2P3(p1.x(), p1.y(), p2.x(), p2.y(), p3.x(), p3.y())) {
								p3 = next;
							}
							/* 在共线的点里找距离P2最大的那个 */
							else if (cosFromP1P2ToP2P3(p1.x(), p1.y(), p2.x(), p2.y(), next.x(),
									next.y()) == cosFromP1P2ToP2P3(p1.x(), p1.y(), p2.x(), p2.y(), p3.x(), p3.y())
									&& distanceBetweenTwoPoints(p2.x(), p2.y(), next.x(),
											next.y()) > distanceBetweenTwoPoints(p2.x(), p2.y(), p3.x(), p3.y())) {
								p3 = next;
							}
						}
						/* 跳出循环条件，遍历回到起点 */
						if (hullPoints.contains(p3))
							break;
						else {
							/* 找到了下一个点，加入凸包，并更新P1,P2，开启下一轮迭代 */
							hullPoints.add(p3);
							Point temp = p2;
							p2 = p3;
							p1 = temp;
						}
					}
				}
			}
		}
		return hullPoints;
	}

	/**
	 * 
	 * @param x1 the x coordinate of the current point
	 * @param y1 the y coordinate of the current point
	 * @param x2 the x coordinate of the target point
	 * @param y2 the y coordinate of the target point
	 * @return cosine of the angle between the line of current and target point and
	 *         positive x-axis
	 */
	public static double cosBetweenTwoPoints(double x1, double y1, double x2, double y2) {
		double cosalpha = Math.acos((x2 - x1) / distanceBetweenTwoPoints(x1, y1, x2, y2));
		return cosalpha;
	}

	/**
	 * 
	 * @param x1 the x coordinate of P1
	 * @param y1 the y coordinate of P1
	 * @param x2 the x coordinate of P2
	 * @param y2 the y coordinate of P2
	 * @param x3 the x coordinate of P3
	 * @param y3 the y coordinate of P3
	 * @return cosine of the angle between P1P2 and P2P3
	 */
	public static double cosFromP1P2ToP2P3(double x1, double y1, double x2, double y2, double x3, double y3) {
		double P1P2X = x2 - x1, P1P2Y = y2 - y1;
		double P2P3X = x3 - x2, P2P3Y = y3 - y2;
		double costheta = (P1P2X * P2P3X + P1P2Y * P2P3Y) / (Math.sqrt(Math.pow(P1P2X, 2) + Math.pow(P1P2Y, 2))
				* Math.sqrt(Math.pow(P2P3X, 2) + Math.pow(P2P3Y, 2)));
		return costheta;
	}

	/**
	 * 
	 * @param x1 the x coordinate of current point
	 * @param y1 the y coordinate of current point
	 * @param x2 the x coordinate of target point
	 * @param y2 the y coordinate of target point
	 * @return the distance between current and target point
	 */
	public static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
		double distance = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
		return distance;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		turtle.turn(108.0);
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 5; j++) {
				turtle.forward(220);
				turtle.turn(108.0);
			}
			turtle.turn(3);
			turtle.color(PenColor.class.getEnumConstants()[i % 10]);
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		drawSquare(turtle, 40);
		drawPersonalArt(turtle);

		// draw the window
		turtle.draw();
	}

}
