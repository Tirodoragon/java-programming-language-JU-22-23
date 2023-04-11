import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;

class RoutePoint {
	RoutePoint next;
	Plane plane;
	Position coordinates;
	
	public RoutePoint(Position coordinatesRP, Direction directionRP, RoutePoint nextRP) {
		coordinates = coordinatesRP;
		setPlane(directionRP);
		next = nextRP;
	}
	
	public void setPlane(Direction directionSP) {
		if (directionSP == Direction.Up)
			plane = Plane.Vertical;
		else if (directionSP == Direction.UpRight)
			plane = Plane.SlantRight;
		else if (directionSP == Direction.Right)
			plane = Plane.Horizontal;
		else if (directionSP == Direction.DownRight)
			plane = Plane.SlantLeft;
		else if (directionSP == Direction.Down)
			plane = Plane.Vertical;
		else if (directionSP == Direction.DownLeft)
			plane = Plane.SlantRight;
		else if (directionSP == Direction.Left)
			plane = Plane.Horizontal;
		else if (directionSP == Direction.UpLeft)
			plane = Plane.SlantLeft;
		else
			plane = Plane.Undefined;
	}
	
	enum Direction {
		Up, UpRight, Right, DownRight, Down, DownLeft, Left, UpLeft, Undefined
	}
	
	enum Plane {
		Vertical, SlantRight, Horizontal, SlantLeft, Undefined
	}
	
	public static Position movePoint(Position point, Direction direction) {
		if (direction == Direction.Up)
			return new Position2D(point.getCol(), point.getRow() + 1);
		else if (direction == Direction.UpRight)
			return new Position2D(point.getCol() + 1, point.getRow() + 1);
		else if (direction == Direction.Right)
			return new Position2D(point.getCol() + 1, point.getRow());
		else if (direction == Direction.DownRight)
			return new Position2D(point.getCol() + 1, point.getRow() - 1);
		else if (direction == Direction.Down)
			return new Position2D(point.getCol(), point.getRow() - 1);
		else if (direction == Direction.DownLeft)
			return new Position2D(point.getCol() - 1, point.getRow() - 1);
		else if (direction == Direction.Left)
			return new Position2D(point.getCol() - 1, point.getRow());
		else
			return new Position2D(point.getCol() - 1, point.getRow() + 1);
	}
	
	public static Direction reverseDirection(Direction direction) {
		if (direction == Direction.Up)
			return Direction.Down;
		else if (direction == Direction.UpRight)
			return Direction.DownLeft;
		else if (direction == Direction.Right)
			return Direction.Left;
		else if (direction == Direction.DownRight)
			return Direction.UpLeft;
		else if (direction == Direction.Down)
			return Direction.Up;
		else if (direction == Direction.DownLeft)
			return Direction.UpRight;
		else if (direction == Direction.Left)
			return Direction.Right;
		else if (direction == Direction.UpLeft)
			return Direction.DownRight;
		else
			return Direction.Undefined;
	}
	
	public static Direction determineDirection(Position firstPoint, Position secondPoint) {
		if (secondPoint.getCol() == firstPoint.getCol() && secondPoint.getRow() > firstPoint.getRow())
			return Direction.Up;
		else if (secondPoint.getCol() > firstPoint.getCol() && secondPoint.getRow() > firstPoint.getRow())
			return Direction.UpRight;
		else if (secondPoint.getCol() > firstPoint.getCol() && secondPoint.getRow() == firstPoint.getRow())
			return Direction.Right;
		else if (secondPoint.getCol() > firstPoint.getCol() && secondPoint.getRow() < firstPoint.getRow())
			return Direction.DownRight;
		else if (secondPoint.getCol() == firstPoint.getCol() && secondPoint.getRow() < firstPoint.getRow())
			return Direction.Down;
		else if (secondPoint.getCol() < firstPoint.getCol() && secondPoint.getRow() < firstPoint.getRow())
			return Direction.DownLeft;
		else if (secondPoint.getCol() < firstPoint.getCol() && secondPoint.getRow() == firstPoint.getRow())
			return Direction.Left;
		else if (secondPoint.getCol() < firstPoint.getCol() && secondPoint.getRow() > firstPoint.getRow())
			return Direction.UpLeft;
		else
			return Direction.Undefined;
	}
	
	public static boolean perpendicularPlanes(Plane firstPlane, Plane secondPlane) {
		if (firstPlane == Plane.Horizontal && secondPlane == Plane.Vertical)
			return true;
		if (firstPlane == Plane.Vertical && secondPlane == Plane.Horizontal)
			return true;
		if (firstPlane == Plane.SlantLeft && secondPlane == Plane.SlantRight)
			return true;
		if (firstPlane == Plane.SlantRight && secondPlane == Plane.SlantLeft)
			return true;
		else
			return false;
	}
}

class BusLine implements BusLineInterface {
	Map<String, List<Position>> lines;
	Map<String, List<RoutePoint>> busLines;
	Map<String, Map<Integer, String>> intersectionsNames;
	Map<String, Map<Integer, Position>> intersectionsPositions;
	{
		lines = new HashMap<>();	
		busLines = new HashMap<>();
		intersectionsNames = new HashMap<>();
		intersectionsPositions = new HashMap<>();
	}
	
	class LinesPair implements BusLineInterface.LinesPair {
		String firstLine;
		String secondLine;
		LinesPair(String firstLineLP, String secondLineLP) {
			firstLine = firstLineLP;
			secondLine = secondLineLP;
		}
		@Override
		public String getFirstLineName() {
			return firstLine;
		}
		@Override
		public String getSecondLineName() {
			return secondLine;
		}
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LinesPair other = (LinesPair) obj;
			return getFirstLineName() == other.getFirstLineName() && getSecondLineName() == other.getSecondLineName();
		}
	}
	
	@Override
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint) {
		List<RoutePoint> points = new LinkedList<>();
		points.add(new RoutePoint (firstPoint, RoutePoint.Direction.Undefined, null));
		points.add(new RoutePoint (lastPoint, RoutePoint.Direction.Undefined, null));
		busLines.put(busLineName, points);
	}
	
	@Override
	public void addLineSegment(String busLineName, LineSegment lineSegment) {	
		List<RoutePoint> points = busLines.get(busLineName);
		RoutePoint.Direction direction = RoutePoint.determineDirection(lineSegment.getFirstPosition(), lineSegment.getLastPosition());
		RoutePoint next = null;
		Boolean end = false;
		for (RoutePoint point : points) {
			if (point.coordinates.hashCode() == lineSegment.getLastPosition().hashCode()) {
				point.setPlane(RoutePoint.Direction.Undefined);
				next = point;
				end = true;
				break;
			}
		}
		if (!end) {
			next = new RoutePoint(lineSegment.getLastPosition(), RoutePoint.Direction.Undefined, null);
			points.add (next);
		}
		Position temporary = new Position2D (lineSegment.getLastPosition().getCol(), lineSegment.getLastPosition().getRow());
		temporary = RoutePoint.movePoint(temporary, RoutePoint.reverseDirection(direction));
		while (temporary.hashCode() != lineSegment.getFirstPosition().hashCode()) {	
			if (next != null) {
				next = new RoutePoint (temporary, direction, next);
			}
			else {
				next = new RoutePoint (temporary, direction, null);
			}
			points.add (next);
			temporary = RoutePoint.movePoint(temporary, RoutePoint.reverseDirection(direction));
		}
		Boolean initial = false;
		for (RoutePoint point : points) {
			if (point.coordinates.hashCode() == temporary.hashCode()) {
				point.setPlane(RoutePoint.Direction.Undefined);
				point.next = next;
				initial = true;
				break;
			}
		}
		if (!initial) {
			points.add (new RoutePoint(lineSegment.getFirstPosition(), RoutePoint.Direction.Undefined, next));
		}	
	}
	
	@Override
	public void findIntersections() {	
		for (String firstLineName : busLines.keySet()) {
			List<RoutePoint> firstLine = busLines.get(firstLineName);
			for (String secondLineName : busLines.keySet()) {
				List<RoutePoint> secondLine = busLines.get(secondLineName);
				int i = 0;
				RoutePoint firstPointPrevious = firstLine.get(0);
				RoutePoint firstPoint = firstPointPrevious.next;
				RoutePoint firstPointNext = firstPointPrevious.next.next;
				while (firstPointNext != null) {
					RoutePoint secondPointPrevious = secondLine.get(0);
					RoutePoint secondPoint = secondPointPrevious.next;
					RoutePoint secondPointNext = secondPointPrevious.next.next;
					while (secondPointNext != null) {
						if (firstPoint.coordinates.equals(secondPoint.coordinates)) {
							if (RoutePoint.perpendicularPlanes (firstPoint.plane, secondPoint.plane) || (RoutePoint.perpendicularPlanes(firstPointPrevious.plane, secondPointPrevious.plane) && firstPointPrevious.plane == firstPointNext.plane && secondPointPrevious.plane == secondPointNext.plane)) {								
								if (intersectionsPositions.get(firstLineName) == null) {
									intersectionsPositions.put(firstLineName, new TreeMap<>());
								}
								intersectionsPositions.get(firstLineName).put(i, new Position2D (firstPoint.coordinates.getCol(), firstPoint.coordinates.getRow()));
								if (intersectionsNames.get(firstLineName) == null) {
									intersectionsNames.put(firstLineName, new TreeMap<>());
								}
								intersectionsNames.get(firstLineName).put(i, secondLineName);
							}
						}
						secondPointPrevious = secondPointPrevious.next;
						secondPoint = secondPoint.next;
						secondPointNext = secondPointNext.next;
					}
					i++;
					firstPointPrevious = firstPointPrevious.next;
					firstPoint = firstPoint.next;
					firstPointNext = firstPointNext.next;
				}
			}
			if (intersectionsPositions.get(firstLineName) != null) {
				lines.put(firstLineName, new ArrayList<>(firstLine.size()));
				RoutePoint point = firstLine.get(0);
				do {
					lines.get(firstLineName).add(new Position2D(point.coordinates.getCol(), point.coordinates.getRow()));
					point = point.next;
				}
				while (point != null);
			}			
		}	
	}
	
	Map<BusLineInterface.LinesPair, Set<Position>> intersectionOfLinesPair;
	@Override
	public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() {		
		intersectionOfLinesPair = new HashMap<>();
		Map<String, List<Position>> iPositions;
		if (intersectionPositions != null)
			iPositions = intersectionPositions;
		else
			iPositions = getIntersectionPositions();
		Map<String, List<String>> iNames;
		if (intersectionsWithLines != null)
			iNames = intersectionsWithLines;
		else
			iNames = getIntersectionsWithLines();
		
		for (String firstLineName : iPositions.keySet()) {
			List<Position> firstLine = iPositions.get(firstLineName);
			for (String secondLineName : iPositions.keySet()) {
				LinesPair pair = new LinesPair(firstLineName, secondLineName);
				intersectionOfLinesPair.put(pair, new HashSet<>());
				for (int i = 0; i < firstLine.size(); i++) {
					if (iNames.get(firstLineName).get(i) == secondLineName) {
						intersectionOfLinesPair.get(pair).add(firstLine.get(i));
					}		
				}
			}	
		}
		for (String firstLineName : busLines.keySet()) {
			for (String secondLineName : busLines.keySet()) {
				boolean found = false;
				LinesPair pair = new LinesPair(firstLineName, secondLineName);
				for (BusLineInterface.LinesPair secondPair : intersectionOfLinesPair.keySet()) {
					if (pair.equals(secondPair)) {
						found = true;
						break;
					}
				}
				if (!found)
					intersectionOfLinesPair.put(pair, new HashSet<>());	
			}	
		}		
		return intersectionOfLinesPair;
	}
	
	Map<String, List<Position>> intersectionPositions;
	@Override
	public Map<String, List<Position>> getIntersectionPositions() {
		intersectionPositions = new HashMap<>();
		for (String routeName : intersectionsPositions.keySet()) {
			Map<Integer, Position> route = intersectionsPositions.get(routeName);
			List<Position> points = new LinkedList<>();
			intersectionPositions.put(routeName, points);
			for (Integer pointNumber : route.keySet()) {
				points.add(route.get(pointNumber));
			}
		}	
		return intersectionPositions;
	}
	
	Map<String, List<String>> intersectionsWithLines;
	@Override
	public Map<String, List<String>> getIntersectionsWithLines() {
		intersectionsWithLines = new HashMap<>();
		for (String routeName : intersectionsNames.keySet()) {
			Map<Integer, String> route = intersectionsNames.get(routeName);
			List<String> names = new LinkedList<>();
			intersectionsWithLines.put(routeName, names);
			for (Integer pointNumber : route.keySet()) {
				names.add(route.get(pointNumber));
			}
		}	
		return intersectionsWithLines;
	}
	
	@Override
	public Map<String, List<Position>> getLines() {
		return lines;
	}
}