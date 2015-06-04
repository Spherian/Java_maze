//git commit -m "msg"
//git push origin master
//git pull

import java.io.*;
import point2d.*;

public class maze {

	int row, col;	
	char[][] maze, history;
	point2d S, F;

	//Constructor
	public maze(File file) {

		setMazeFromFile(file);
		this.S = findChar('S');
		this.F = findChar('F');

	}

	//Display maze on command prompt
	public void printMaze() {
		
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				System.out.print(maze[i][j]);
			}
		}

	}

	public void printHistory() {
		
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				System.out.print(history[i][j]);
			}
		}

	}

	//Check if the position is a wall or open
	public boolean isOpen(point2d pos) {

		boolean decision;

		if(pos.getY() > this.col-1 || pos.getX() > this.row-1 || pos.getY() < 0 || pos.getX() < 0) {
			decision = false;
			return decision;
		}

		if(maze[pos.getX()][pos.getY()] == 'X') {
			decision = false;
		}
		else {
			decision = true;
		}
		return decision;

	}

	//Check if there's only one S and one F
	public boolean isMazeValid() {

		boolean decision;

		if(onlyOneChar('S') && onlyOneChar('F')) {
			decision = true;
		}
		else {
			decision = false;
			return decision;
		}

		history[S.getX()][S.getY()] = '*';
		int count = 0;
		while(history[F.getX()][F.getY()] != '*') {
			System.out.println("COUNT: " + count);

			for(int i = 0; i < row; i++) {
				for(int j = 0; j < col; j++) {
					if(history[i][j] == '*') {
						point2d n = new point2d(i-1, j);
						point2d s = new point2d(i+1, j);
						point2d e = new point2d(i, j+1);
						point2d w = new point2d(i, j-1);

						if(isOpen(n) && history[n.getX()][n.getY()] != '*') {
							history[n.getX()][n.getY()] = '*';
						}						
						else if(isOpen(s) && history[s.getX()][s.getY()] != '*') {
							history[s.getX()][s.getY()] = '*';
						}
						else if(isOpen(e) && history[e.getX()][e.getY()] != '*') {
							history[e.getX()][e.getY()] = '*';
						}
 						else if(isOpen(w) && history[w.getX()][w.getY()] != '*') {
							history[w.getX()][w.getY()] = '*';
						}

					}
				}
			}

			count++;
			printHistory();
		}

		return decision;
	}

	//Check maze for one occurence of a character
	private boolean onlyOneChar(char c) {

		int charCount = 0;
		boolean decision = true;

		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				if(this.maze[i][j] == c) {
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
				if(this.maze[i][j] == c) {
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

			this.maze = new char[row][col];
			this.history = new char[row][col];

			int itr = 0;
			for(int n = 0; n < this.row; n++) {
				for(int m = 0; m < this.col; m++) {
					if(c[0] != '\n') {
						this.maze[n][m] = c[itr];
						this.history[n][m] = c[itr];
						itr++;
					}
				}
			}

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
