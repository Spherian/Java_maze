package Robot;
import Point2d.*;
import Grid.*;

public class Robot {
	
	String id;
	Point2d currentPos;
	char symbol;
	Grid<Character> map = new Grid<Character>(3,3);

	public Robot(String name, Point2d mazeStart, char robotSymbol) {
		this.id = name;
		this.currentPos = mazeStart;
		this.symbol = robotSymbol;
		this.map.setAll(' ');
	}

	public String getID(){
		return this.id;
	}

	public Point2d getCurrentPos() {
		return this.currentPos;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public Grid<Character> getMap() {
		return this.map;
	}

	public void setCurrentPos(Point2d newPos) {
		this.currentPos = newPos;
	}

	public String toString() {
		return "ID: " + this.id + "\n" + "Pos: " + this.currentPos;
	}

	public void updateMap(Point2d pos, char value) {

		int newRow = map.getRow();
		int newCol = map.getCol();
		Grid<Character> newMap = new Grid<Character>(newRow, newCol);

		if(pos.getX() >= map.getRow()) {
			newRow = pos.getX()+1;
			newMap = new Grid<Character>(newRow, newCol);
		}
		if(pos.getY() >= map.getCol()) {
			newCol = pos.getY()+1;
			newMap = new Grid<Character>(newRow, newCol);
		}

		newMap.setAll(' ');			
		map.copyTo(newMap);
		map = newMap;
		map.setGridElement(pos, value);

	}

	public static void main(String[] args) {
		Point2d start = new Point2d(0,0);
		Robot testRobot = new Robot("Spyhunter", start, 'R');
		System.out.println(testRobot.toString());
		Point2d finish = new Point2d(5,5);
		testRobot.setCurrentPos(finish);
		System.out.println(testRobot.toString());

		testRobot.getMap().printGrid();
		testRobot.updateMap(new Point2d(0,0), 'D');
		testRobot.getMap().printGrid();

		testRobot.updateMap(new Point2d(5,8), 'G');
		testRobot.getMap().printGrid();

		testRobot.updateMap(new Point2d(9,9), 'T');
		testRobot.getMap().printGrid();
	}
}
