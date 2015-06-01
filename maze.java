//git commit -m "msg"
//git push origin master
//git pull

import java.io.*;

public class maze {

	int row, col;	
	char[][] maze;
	int[] S, F;

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

	public boolean isOpen(int[] pos) {

		boolean decision;

		if(maze[pos[0]][pos[1]] == 'X') {
			decision = false;
		}
		else {
			decision = true;
		}

		return decision;

	} 

	private int[] findChar(char c) {

		int[] pos = new int [2];

		for(int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.col; j++) {
				if(maze[i][j] == c) {
					pos[0] = i;
					pos[1] = j;
					break;
				}
			}
		}
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
		System.out.println("S pos: (" + myMaze.S[0] + "," + myMaze.S[1] + ")");
		System.out.println("F pos: (" + myMaze.F[0] + "," + myMaze.F[1] + ")");

		int[] posTest1 = new int[2];
		posTest1 = new int[]{0,0};
		int[] posTest2 = new int[2];
		posTest2 = new int[]{1,2};

		System.out.println("Is (" + posTest1[0] + "," + posTest1[1] + ") open? " + myMaze.isOpen(posTest1));
		System.out.println("Is (" + posTest2[0] + "," + posTest2[1] + ") open? " + myMaze.isOpen(posTest2));

		myMaze.printMaze();

	}

	

}
