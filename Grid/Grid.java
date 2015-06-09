package grid;

import java.util.ArrayList;
import point2d.*;

public class Grid<T> {

	ArrayList<ArrayList<T>> grid;
	int row;
	int col;

	public Grid(int inputRow, int inputCol) {
		this.row = inputRow;
		this.col = inputCol;
		this.grid = new ArrayList<ArrayList<T>>();
		for(int i=0; i<this.row; i++) {
			grid.add(new ArrayList<T>());
			for(int j=0; j<this.col; j++) {
				this.grid.get(i).add(null);
			}
		}
	}

	public Grid(int inputRow, int inputCol, T defaultValue) {
		this(inputRow, inputCol);
		setAll(defaultValue);
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public void setGridElement(point2d pos, T data) {
		setGridElement(pos.getY(), pos.getX(), data);
	}

	public void setGridElement(int inputRow, int inputCol, T data){
		this.grid.get(inputRow).set(inputCol, data);
	}

	public void setAll(T data){
		for(int i=0; i<this.row; i++) {
			for(int j=0; j<this.col; j++) {
				setGridElement(i, j, data);
			}
		}
	}

	public void setNulls(T data){
		for(int i=0; i<this.row; i++) {
			for(int j=0; j<this.col; j++) {
				if (getGridElement(i, j) == null){
					setGridElement(i, j, data);
				}
			}
		}
	}

	public T getGridElement(point2d pos){
		return getGridElement(pos.getY(), pos.getX());
	}

	public T getGridElement(int inputRow, int inputCol){
		return this.grid.get(inputRow).get(inputCol);
	}

	public void printRow(int inputRow) {
		System.out.println(this.grid.get(inputRow));
	}

	public void printCol(int inputCol) {
		for(int i=0; i<this.row; i++) {
			System.out.println(this.grid.get(i).get(inputCol));
		}
	}

	public void printGrid() {
		for(int i=0; i<this.row; i++) {
			System.out.println(this.grid.get(i));
		}
	}
	
	public static void main(String[] args) {
		System.out.println("YOYOYO");
		Grid<Integer> test = new Grid<Integer>(3,5, 0);
		System.out.println("Row: " + test.getRow());
		System.out.println("Col: " + test.getCol());

		test.setGridElement(0,1, 1);
		test.setGridElement(0,2, 2);
		test.setGridElement(0,3, 3);
		test.setGridElement(0,4, 4);

		test.setGridElement(1,0, 5);
		test.setGridElement(1,1, 6);
		test.setGridElement(1,2, 7);
		test.setGridElement(1,3, 8);
		test.setGridElement(1,4, 9);

		test.printRow(1);
		test.printCol(1);
		test.printGrid();
	}

}
