import java.io.*;
import point2d.*;
import Grid.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class maze {

	int row, col, stepsToSolve;	
	point2d S, F;
	Grid<Character> mazeGrid, historyGrid;
	Grid<Integer> step;

	//Constructor
	public maze(File file) {

		setMazeFromFile(file);
		this.S = findChar('S');
		this.F = findChar('F');

	}

	public boolean isIntersection(point2d pos) {
		boolean decision = false;
		//Check to see if the given position is even open
		if(!isOpen(pos)) {
			return decision;
		}
		//If it is open, get all adjacent positions		
		point2d[] adjPos = pos.getAdjacentPos();
		
		int wallCounter = 0;
		for(int i=0; i<adjPos.length;i++) {
			point2d tmpPos = adjPos[i];

			if(!isOpen(tmpPos)) {
				wallCounter++;
			}
		} 

		if(wallCounter <= 1) {
			decision = true;
		}

		return decision;
	}

	public ArrayList<point2d> getAllIntersections() {
		ArrayList<point2d> arr = new ArrayList<point2d>();
		for(int i=1; i<this.row-1; i++) {
			for(int j=1; j<this.col-1; j++) {
				point2d tmpPos = new point2d(i,j);
				if(isIntersection(tmpPos)) {
					arr.add(tmpPos);
				}
			}
		}
		return arr;
	}


	//Check if the position is a wall or open
	public boolean isOpen(point2d pos) {

		boolean decision;

		if(pos.getY() > this.col || pos.getX() > this.row || pos.getY() < 0 || pos.getX() < 0) {
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

		boolean decision;

		if(onlyOneChar('S') && onlyOneChar('F')) {
			decision = true;
		}
		else {
			decision = false;
			return decision;
		}

		this.historyGrid.setGridElement(S, '*');
		int count = 0;
		Grid<Character> oldGridMaze = new Grid<Character>(this.row, this.col, null);
		
		this.step = new Grid<Integer>(this.row, this.col, null);
		int stepper = 0;
		this.step.setGridElement(S, 0);		

		historyGrid.copy(oldGridMaze);
		while(historyGrid.getGridElement(F) != '*') {
			for(int i=0; i<this.row; i++) {
				for(int j=0; j<this.col; j++) {
					if(historyGrid.getGridElement(i,j) == '*') {
						point2d n = new point2d(i-1, j);
						point2d s = new point2d(i+1, j);
						point2d e = new point2d(i, j+1);
						point2d w = new point2d(i, j-1);

						if(isOpen(n) && historyGrid.getGridElement(n) != '*') {
							historyGrid.setGridElement(n, '*');
							point2d pt = step.getLowElementPos(n);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(n,value);
						}						
						else if(isOpen(s) && historyGrid.getGridElement(s) != '*') {
							historyGrid.setGridElement(s, '*');
							point2d pt = step.getLowElementPos(s);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(s,value);
						}
						else if(isOpen(e) && historyGrid.getGridElement(e) != '*') {
							historyGrid.setGridElement(e, '*');
							point2d pt = step.getLowElementPos(e);
							Integer value = step.getGridElement(pt);
							value = value+1;
							this.step.setGridElement(e,value);
						}
 						else if(isOpen(w) && historyGrid.getGridElement(w) != '*') {
							historyGrid.setGridElement(w, '*');
							point2d pt = step.getLowElementPos(w);
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
			historyGrid.copy(oldGridMaze);
		}
		this.stepsToSolve = step.getGridElement(F);

		this.step.printGrid();

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
	private point2d findChar(char c) {

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

		point2d pos = new point2d(x,y);

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
			this.historyGrid = mazeGrid;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void printMaze() {
		this.mazeGrid.printGrid();
	}

	public void printHistory() {
		this.historyGrid.printGrid();
	}

	public void printStep() {
		this.step.printGrid();
	}

	private boolean isStepOpen(point2d pos) {
		boolean decision;

		if(pos.compare(this.S) || pos.compare(this.F)) {
			decision = true;
			return decision;
		}

		if(pos.getY() > this.col || pos.getX() > this.row || pos.getY() < 0 || pos.getX() < 0) {
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

	public boolean isDeadEnd(point2d pos) {
		boolean decision = false;
		if(!isStepOpen(pos)) {
			return decision;
		}	

		point2d[] adjArr = pos.getAdjacentPos();

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

	public ArrayList<point2d> getAllDeadEnd() {

		ArrayList<point2d> deadEnds = new ArrayList<point2d>();
		for(int i=0; i<this.row-1; i++) {
			for(int j=0; j<this.col-1; j++) {
				point2d tmpPos = new point2d(i,j);
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
		ArrayList<point2d> deadEnds = getAllDeadEnd();

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
		ArrayList<String> directions = new ArrayList<String>();
		point2d tmpPos = new point2d(S.getX(), S.getY());
		point2d[] adjTmpPos = tmpPos.getAdjacentPos();
		int value = 0;
		for(int i=0; i<adjTmpPos.length; i++) {
			if(isStepOpen(adjTmpPos[i])) {
				directions.add(getStrDirection(i));
				tmpPos = adjTmpPos[i];
				value = this.step.getGridElement(adjTmpPos[i]);
			}
		}	

		for(int x=0; x<this.stepsToSolve; x++) {
			adjTmpPos = tmpPos.getAdjacentPos();
			for(int i=0; i<adjTmpPos.length; i++) {
				if(isStepOpen(adjTmpPos[i]) && this.step.getGridElement(adjTmpPos[i]) > value) {
					directions.add(getStrDirection(i));
					tmpPos = adjTmpPos[i];
					value = this.step.getGridElement(adjTmpPos[i]);
				}
			}
			
		}

		ListIterator itr = directions.listIterator();
		while(itr.hasNext()) {
			System.out.println(itr.next() + " to " + itr.nextIndex());
		}

		return directions;
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

	public static void main(String[] args) {

		String maze1FileName = args[0];
		File f = new File(maze1FileName);
		
		maze myMaze = new maze(f);
		System.out.println("Is maze valid? " + myMaze.isMazeValid());
		System.out.println("Number of steps to solve maze: " + myMaze.getStepsToSolveMaze());
		
		myMaze.getAllDeadEnd();
		myMaze.getOptimalSteps();
		myMaze.printStep();
		myMaze.getDirections();
	}

	

}
