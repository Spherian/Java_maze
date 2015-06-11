import java.io.*;
import point2d.*;
import Grid.*;

public class maze {

	int row, col;	
	point2d S, F;
	Grid<Character> mazeGrid, historyGrid;

	//Constructor
	public maze(File file) {

		setMazeFromFile(file);
		this.S = findChar('S');
		this.F = findChar('F');

	}

	//Check if the position is a wall or open
	public boolean isOpen(point2d pos) {

		boolean decision;

		if(pos.getY() > this.col-1 || pos.getX() > this.row-1 || pos.getY() < 0 || pos.getX() < 0) {
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
		System.out.println("S: (" + S.getX() + "," + S.getY() + ")");
		System.out.println("HistoryGrid(S): " + this.historyGrid.getGridElement(S));
		while(historyGrid.getGridElement(F) != '*') {
			for(int i=0; i<row; i++) {
				for(int j=0; j<col; j++) {
					if(historyGrid.getGridElement(i,j) == '*') {
						point2d n = new point2d(i-1, j);
						point2d s = new point2d(i+1, j);
						point2d e = new point2d(i, j+1);
						point2d w = new point2d(i, j-1);

						if(isOpen(n) && historyGrid.getGridElement(n) != '*') {
							historyGrid.setGridElement(n, '*');
						}						
						else if(isOpen(s) && historyGrid.getGridElement(s) != '*') {
							historyGrid.setGridElement(s, '*');
						}
						else if(isOpen(e) && historyGrid.getGridElement(e) != '*') {
							historyGrid.setGridElement(e, '*');
						}
 						else if(isOpen(w) && historyGrid.getGridElement(w) != '*') {
							historyGrid.setGridElement(w, '*');
						}

					}
				}
			}
			count++;
		}

		return decision;
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
					if(c[0] != '\n') {
						mazeGrid.setGridElement(i, j, c[itr]);
						itr++;
					}
				}
			}
			this.historyGrid = mazeGrid;
			mazeGrid.printGrid();
			System.out.println("Row: " + mazeGrid.getRow() + " Col: " + mazeGrid.getCol());
			historyGrid.printGrid();
/*
			this.maze = new char[row][col];
			this.history = new char[row][col];

			itr = 0;
			for(int n = 0; n < this.row; n++) {
				for(int m = 0; m < this.col; m++) {
					if(c[0] != '\n') {
						this.maze[n][m] = c[itr];
						this.history[n][m] = c[itr];
						itr++;
					}
				}
			}
*/
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		String maze1FileName = args[0];
		File f = new File(maze1FileName);
		
		maze myMaze = new maze(f);

		System.out.println("Is maze valid? " + myMaze.isMazeValid());

		point2d posTest1 = new point2d(3,7);

		System.out.println("Is " + posTest1.toString() + " open? " + myMaze.isOpen(posTest1));

		point2d posTest2 = new point2d(5,7);

		System.out.println("Is " + posTest2.toString() + " open? " + myMaze.isOpen(posTest2));

//		myMaze.printHistory();

	}

	

}
