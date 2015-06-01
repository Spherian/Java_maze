//git commit -m "msg"
//git push origin master
//git pull

import java.io.*;

public class maze {

	int row, col;	
	char[][] maze;

	public void readInputMaze(File file) {
		try {
			FileReader fr = new FileReader(file);
			char[] c = new char[(int) file.length()];
			fr.read(c);
			int i = 0;

			for(int n = 0; n < this.row; n++) {
				for(int m = 0; m < this.col; m++) {
					if(c[0] != '\n') {
						this.maze[n][m] = c[i];
						i++;
					}
				}
			}	


		} catch (Exception e) {
			System.out.println("readInputMaze exception: " + e.getMessage());
		}
		
		
	}

	public void setMazeSizeFromFile(File file) {
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

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int getMazeCol() {
		return this.col;
	}

	public int getMazeRow() {
		return this.row;
	}

	public static void main(String[] args) {
		
		maze myMaze = new maze();
		String maze1FileName = "/home/jkleung/Desktop/Java_maze/maze1.txt";
		File f = new File(maze1FileName);
		myMaze.setMazeSizeFromFile(f);

		System.out.println("Maze character count: " + f.length());
		System.out.println("Maze col: " + myMaze.getMazeCol());
		System.out.println("Maze row: " + myMaze.getMazeRow());

		myMaze.readInputMaze(f);

	}

	

}
