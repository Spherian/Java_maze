package Robot;
import Point2d.*;

public class Robot {
	
	String id;
	Point2d currentPos;
	char symbol;

	public Robot(String name, Point2d mazeStart, char robotSymbol) {
		this.id = name;
		this.currentPos = mazeStart;
		this.symbol = robotSymbol;
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

	public void setCurrentPos(Point2d newPos) {
		this.currentPos = newPos;
	}

	public String toString() {
		return "ID: " + this.id + "\n" + "Pos: " + this.currentPos;
	}

	public static void main(String[] args) {
		Point2d start = new Point2d(0,0);
		Robot testRobot = new Robot("Spyhunter", start);
		System.out.println(testRobot.toString());
		Point2d finish = new Point2d(5,5);
		testRobot.setCurrentPos(finish);
		System.out.println(testRobot.toString());
	}
}
