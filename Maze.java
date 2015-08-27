import Point2d.*;
import Grid.*;
import Robot.*;

import java.io.*;

import java.util.ArrayList;
import java.util.ListIterator;

import java.util.Scanner;

public class Maze {

	int row, col, stepsToSolve;	
	Point2d S, F;
	Grid<Character> mazeGrid;
	Grid<Integer> step, solution;
	ArrayList<String> directionsFromF, directionsFromS;

	//Constructor
	public Maze(File file) {

		setMazeFromFile(file);
		this.S = findChar('S');
		this.F = findChar('F');
		isMazeValid();
		getDirections();

	}

	public boolean isIntersection(Point2d pos) {
		boolean decision = false;
		//Check to see if the given position is even open
		if(!isOpen(pos)) {
			return decision;
		}
		//If it is open, get all adjacent positions		
		//Point2d[] adjPos = pos.getAdjacentPos();
		Point2d.Adjacent adj = pos.new Adjacent();
		Point2d[] adjPos = adj.getAdjacent();

		int wallCounter = 0;
		for(int i=0; i<adjPos.length;i++) {
			Point2d tmpPos = adjPos[i];

			if(!isOpen(tmpPos)) {
				wallCounter++;
			}
		} 

		if(wallCounter <= 1) {
			decision = true;
		}

		return decision;
	}

	public ArrayList<Point2d> getAllIntersections() {
		ArrayList<Point2d> arr = new ArrayList<Point2d>();
		for(int i=1; i<this.row-1; i++) {
			for(int j=1; j<this.col-1; j++) {
				Point2d tmpPos = new Point2d(i,j);
				if(isIntersection(tmpPos)) {
					arr.add(tmpPos);
				}
			}
		}
		return arr;
	}


	//Check if the position is a wall or open
	public boolean isOpen(Point2d pos) {

		boolean decision;

		if(pos.getY() > this.col || pos.getX() >= this.row || pos.getY() < 0 || pos.getX() < 0) {
			decision = false;
			return decision;
		}

		if(mazeGrid.getGridElement(pos) == 'X') {
			decision = false;
		}
		else {
			decision = true;
		}
		return decision;

	}

	//Check if there's only one S and one F and if the maze is solveable
	public boolean isMazeValid() {
		Grid<Character> historyGrid = new Grid<Character>(this.row, this.col, null);
		this.mazeGrid.copyTo(historyGrid);
		boolean decision;

		if(onlyOneChar('S') && onlyOneChar('F')) {
			decision = true;
		}
		else {
			decision = false;
			return decision;
		}

		historyGrid.setGridElement(S, '*');
		int count = 0;
		Grid<Character> oldGridMaze = new Grid<Character>(this.row, this.col, null);
		
		this.step = new Grid<Integer>(this.row, this.col, null);
		int stepper = 0;
		this.step.setGridElement(S, 0);		

		historyGrid.copyTo(oldGridMaze);
		while(historyGrid.getGridElement(F) != '*') {
			for(int i=0; i<this.row; i++) {
				for(int j=0; j<this.col; j++) {
					if(historyGrid.getGridElement(i,j) == '*') {
						
						Point2d n = new Point2d(i-1, j);
						Point2d s = new Point2d(i+1, j);
						Point2d e = new Point2d(i, j+1);
						Point2d w = new Point2d(i, j-1);					

						if(isOpen(n) && historyGrid.getGridElement(n) != '*') {
							historyGrid.setGridElement(n, '*');
							Point2d pt = step.getLowElementPos(n);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(n,value);
						}
						else if(isOpen(s) && historyGrid.getGridElement(s) != '*') {
							historyGrid.setGridElement(s, '*');
							Point2d pt = step.getLowElementPos(s);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(s,value);
						}
						else if(isOpen(e) && historyGrid.getGridElement(e) != '*') {
							historyGrid.setGridElement(e, '*');
							Point2d pt = step.getLowElementPos(e);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(e,value);
						}
 						else if(isOpen(w) && historyGrid.getGridElement(w) != '*') {
							historyGrid.setGridElement(w, '*');
							Point2d pt = step.getLowElementPos(w);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(w,value);
						}

					}
				}

			}
			count++;
			//If stuck in a loop, the maze is unsolvable
			if(historyGrid.compare(oldGridMaze) == true) {
				decision = false;
				break;
			}
			historyGrid.copyTo(oldGridMaze);
		}
		this.stepsToSolve = step.getGridElement(F);

		return decision;
	} 

	public int getStepsToSolveMaze() {
		return this.stepsToSolve;
	}

	//Check maze for one occurence of a character
	private boolean onlyOneChar(char c) {

		int charCount = 0;
		boolean decision = true;

		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				if(this.mazeGrid.getGridElement(i,j) == c) {
					charCount++;
					if(charCount > 1) {
						decision = false;
						break;
					}
				}
			}
		}

		if(charCount == 0) {
			decision = false;
		}

		return decision;

	}

	//Finds the first occurence of a character in the maze
	private Point2d findChar(char c) {

		int x = 0;
		int y = 0;

		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				if(this.mazeGrid.getGridElement(i,j) == c) {
					x = i;
					y = j;
					break;
				}
			}
		}

		Point2d pos = new Point2d(x,y);

		return pos;
	}


	//Obtain maze size and populate 2D array of maze
	private void setMazeFromFile(File file) {
		try {
			int rowCount = 0;

			FileReader fr = new FileReader(file);
			char[] c = new char[(int) file.length()];
			fr.read(c);
			for(int i = 0; i < file.length(); i++) {
				if(c[i] == '\n') {
					rowCount++;
				}
			}			

			this.row = rowCount;
			this.col = (int)file.length()/rowCount;

			int itr = 0;
			this.mazeGrid = new Grid<Character>(this.row, this.col, ' ');
			for(int i=0; i<this.row; i++) {
				for(int j=0; j<this.col; j++) {
					mazeGrid.setGridElement(i, j, c[itr]);
					itr++;
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void printMaze() {
		this.mazeGrid.printGrid();
	}

	public void printStep() {
		this.step.printGrid();
	}

	public void printSolution() {
		this.solution.printGrid();
	}

	private boolean isStepOpen(Point2d pos) {
		boolean decision;

		if(pos.compare(this.S) || pos.compare(this.F)) {
			decision = true;
			return decision;
		}

		if(pos.getY() > this.col || pos.getX() >= this.row || pos.getY() < 0 || pos.getX() < 0) {
			decision = false;
			return decision;
		}

		if(step.getGridElement(pos) == null) {
			decision = false;
		}
		else {
			decision = true;
		}
		return decision;
	}

	public boolean isDeadEnd(Point2d pos) {
		boolean decision = false;
		if(!isStepOpen(pos)) {
			return decision;
		}	

//		Point2d[] adjArr = pos.getAdjacentPos();
		Point2d.Adjacent adj = pos.new Adjacent();
		Point2d[] adjArr = adj.getAdjacent();

		int closedCount = 0;

		for(int i=0; i<adjArr.length; i++) {
			if(!isStepOpen(adjArr[i])) {
				closedCount++;
			}
		
		}

		if(closedCount == 3) {
			decision = true;
		}

		return decision;
	}

	public ArrayList<Point2d> getAllDeadEnd() {

		ArrayList<Point2d> deadEnds = new ArrayList<Point2d>();
		for(int i=0; i<this.row-1; i++) {
			for(int j=0; j<this.col-1; j++) {
				Point2d tmpPos = new Point2d(i,j);
				if(isDeadEnd(tmpPos)) {
					if(!tmpPos.compare(this.S) || !tmpPos.compare(this.F)) {
						deadEnds.add(tmpPos);
					}
				}
			}
		}
		return deadEnds;
	}

	public void getOptimalSteps() {

		Grid<Integer> oldGrid = this.step;
		ArrayList<Point2d> deadEnds = getAllDeadEnd();

		while(deadEnds.size() > 2) {
			deadEnds = getAllDeadEnd();
			for(int x=0; x<deadEnds.size(); x++) {
				if(!deadEnds.get(x).compare(this.S) && !deadEnds.get(x).compare(this.F)) {
					this.step.setGridElement(deadEnds.get(x),null);
				}
			}
		}
	}

	public ArrayList<String> getDirections() {
		this.solution = new Grid<Integer>(this.row, this.col, null);
		this.directionsFromF = new ArrayList<String>();
		Point2d tmpPos = new Point2d(F.getX(), F.getY());
		Point2d.Adjacent adj = tmpPos.new Adjacent();
		Point2d[] adjTmpPos = adj.getAdjacent();

		int value = 0;
		for(int i=0; i<adjTmpPos.length; i++) {
			if(isStepOpen(adjTmpPos[i])) {
				this.directionsFromF.add(getStrDirection(i));
				tmpPos = adjTmpPos[i];
				value = this.step.getGridElement(adjTmpPos[i]);
				this.solution.setGridElement(adjTmpPos[i], value);
			}
		}	

		for(int x=0; x<this.stepsToSolve; x++) {
			//adjTmpPos = tmpPos.getAdjacentPos();
			adj = tmpPos.new Adjacent();
			adjTmpPos = adj.getAdjacent();
			for(int i=0; i<adjTmpPos.length; i++) {
				if(isStepOpen(adjTmpPos[i]) && this.step.getGridElement(adjTmpPos[i]) < value) {
					this.directionsFromF.add(getStrDirection(i));
					tmpPos = adjTmpPos[i];
					value = this.step.getGridElement(adjTmpPos[i]);
					this.solution.setGridElement(adjTmpPos[i], value);
				}
			}
			
		}

		return this.directionsFromF;
	}

	public void printDirectionsFromF() {
		ListIterator itr = this.directionsFromF.listIterator();
		while(itr.hasNext()) {
			int steps = this.stepsToSolve - itr.nextIndex();
			System.out.println(itr.next() + " from " + steps);
		}
	}

	private String getStrDirection(int i) {
		String results = null;
			if(i==0) {
				results = "North";
			}
			if(i==1) {
				results = "South";
			}
			if(i==2) {
				results = "East";
			}
			if(i==3) {
				results = "West";
			}
		return results;
	}

	public void addRobot(Robot r) {
		
		mazeGrid.setGridElement(S, r.getSymbol());
	}

	public void moveRobot(Robot r, Point2d pos) {
		mazeGrid.setGridElement(r.getCurrentPos(), ' ');
		mazeGrid.setGridElement(pos, r.getSymbol());
		if(!pos.compare(this.S)) {
			mazeGrid.setGridElement(this.S, 'S');
		//	r.updateMap(this.S, 'S');
		}
		if(!pos.compare(this.F)) {
			mazeGrid.setGridElement(this.F, 'F');
		//	r.updateMap(this.F, 'F');
		}

		r.updateMap(r.getCurrentPos(), ' ');
		r.updateMap(pos, r.getSymbol());

		Point2d[] adjPos = pos.new Adjacent().getAdjacent();
		for(int i=0; i<adjPos.length; i++) {
				if(adjPos[i].getX() >= 0 && adjPos[i].getY() >= 0 && adjPos[i].getX() < this.row && adjPos[i].getY() < this.col) {
					r.updateMap(adjPos[i], mazeGrid.getGridElement(adjPos[i]));
				}
		}

	}

	public boolean commandRobot(Robot robot, String move) {	

			Point2d newRobotPos = robot.getCurrentPos();

			if(move.equals("w")){
				newRobotPos = new Point2d(robot.getCurrentPos().getX()-1, robot.getCurrentPos().getY());
			}
			if(move.equals("s")) {
				newRobotPos = new Point2d(robot.getCurrentPos().getX()+1, robot.getCurrentPos().getY());
			}
			if(move.equals("a")) {
				newRobotPos = new Point2d(robot.getCurrentPos().getX(), robot.getCurrentPos().getY()-1);
			}
			if(move.equals("d")) {
				newRobotPos = new Point2d(robot.getCurrentPos().getX(), robot.getCurrentPos().getY()+1);
			}

			if(isOpen(newRobotPos)) {
				moveRobot(robot, newRobotPos);
				robot.setCurrentPos(newRobotPos);
				return true;
			}
			else {
				newRobotPos = robot.getCurrentPos();
				return false;
			}
	} 

	public static void main(String[] args) {

		String maze1FileName = args[0];
		File f = new File(maze1FileName);
		
		Maze myMaze = new Maze(f);

		Robot myRobot = new Robot("Spy Hunter", myMaze.S, 'R');
		myMaze.addRobot(myRobot);		

		Scanner sc = new Scanner(System.in);
		String tmpStr = " ";

		while(!tmpStr.equals("quit")) {
			System.out.print("Enter a command [w,a,s, or d]: ");
			tmpStr = sc.next();

			if(myMaze.commandRobot(myRobot, tmpStr)) {

			}
			else {
				System.out.println("You cannot go here or invalid command");
			}

			myRobot.getMap().printGrid();
		}

		sc.close();

//		myMaze.printDirectionsFromF();
//		myMaze.printStep();
//		myMaze.printMaze();
//		myMaze.printSolution();
	}

	

}
