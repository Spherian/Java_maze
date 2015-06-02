//git commit -m "msg"
//git push origin master
//git pull

import java.io.*;
import point2d.*;

public class maze {

	int row, col;	
	char[][] maze;
	point2d S, F;

	public maze(File file) {

		setMazeFromFile(file);
		this.S = findChar('S');
		this.F = findChar('F');

	}

	public void printMaze() {
		
		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				System.out.print(maze[i][j]);
			}
		}

	}

	public boolean isOpen(point2d pos) {

		boolean decision;

		if(maze[pos.getY()][pos.getX()] == 'X') {
			decision = false;
		}
		else {
			decision = true;
		}

		return decision;

	} 

	private point2d findChar(char c) {

		int x = 0;
		int y = 0;

		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				if(maze[i][j] == c) {
					x = i;
					y = j;
					break;
				}
			}
		}

		point2d pos = new point2d(x,y);

		return pos;
	}

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

			int itr = 0;
			for(int n = 0; n < this.row; n++) {
				for(int m = 0; m < this.col; m++) {
					if(c[0] != '\n') {
						this.maze[n][m] = c[itr];
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

		System.out.println("Maze character count: " + f.length());
		System.out.println("Maze col: " + myMaze.col);
		System.out.println("Maze row: " + myMaze.row);
		System.out.println("S pos: " + myMaze.S.toString());
		System.out.println("F pos: " + myMaze.F.toString());

		point2d posTest1 = new point2d(0,0);
		point2d posTest2 = new point2d(1,2);

		System.out.println("Is " + posTest1.toString() + " open? " + myMaze.isOpen(posTest1));
		System.out.println("Is " + posTest2.toString() + " open? " + myMaze.isOpen(posTest2));

		myMaze.printMaze();

	}

	

}
