package Grid;

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
		setGridElement(pos.getX(), pos.getY(), data);
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

	public boolean compare(Grid<T> grid2) {
		boolean decision = true;

		for(int i=0; i<this.row; i++) {
			for(int j=0; j<this.col; j++) {
				if(getGridElement(i,j) != grid2.getGridElement(i,j)) {
					decision = false;
					break;
				}
			}
		}

		return decision;

	}

	public void copy(Grid<T> grid2) {
		for(int i=0; i<this.row; i++) {
			for(int j=0; j<this.col; j++) {
				grid2.setGridElement(i,j,getGridElement(i,j));
			}
		}
	}

	public T getGridElement(point2d pos){
		return getGridElement(pos.getX(), pos.getY());
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
			for(int j=0; j<this.col; j++) {
				T tmp = this.grid.get(i).get(j);
				System.out.print(tmp);
			}
			System.out.println();
		}
	}

	public point2d getLowElementPos(point2d pos) {
		int x = pos.getX();
		int y = pos.getY();
		point2d n = new point2d(x-1, y);
		point2d s = new point2d(x+1, y);
		point2d e = new point2d(x, y+1);
		point2d w = new point2d(x, y-1);
		ArrayList<point2d> elements = new ArrayList<point2d>();
		T value;

		//Find which  boxes are valid and add to arraylist element)
		if(inGrid(n) && getGridElement(n) != null) {
			elements.add(n);
		}
		if(inGrid(s) && getGridElement(s) != null) {
			elements.add(s);
		}
		if(inGrid(e) && getGridElement(e) != null) {
			elements.add(e);
		}
		if(inGrid(w) && getGridElement(w) != null) {
			elements.add(w);
		}

		int elementIndex = 0;
		point2d lowPoint2d = elements.get(0);
		T lowValue = getGridElement(lowPoint2d);
		for(int i=1; i<elements.size(); i++) {
			point2d tmpPoint2d = elements.get(i);
			T tmpValue = getGridElement(tmpPoint2d);
			if((Integer)lowValue > (Integer)tmpValue) {
				lowValue = tmpValue;
				elementIndex = i;
			}
		}

		return elements.get(elementIndex);
		
	}

	//Is this position actually in the grid?
	public boolean inGrid(point2d pos) {
		boolean decision = true;

		if(pos.getY() > this.col-1 || pos.getX() > this.row-1 || pos.getY() < 0 || pos.getX() < 0) {
			decision = false;
		}

		return decision;
	}
	
	public static void main(String[] args) {
		Grid<Integer> test = new Grid<Integer>(3,5,0);
		System.out.println("Grid equals itself? " + test.compare(test));
		Grid<Integer> test2 = new Grid<Integer>(3,5,0);
		System.out.println("Are test and test2 grid the same? " + test.compare(test2));

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

		point2d pt = new point2d(2,4);
		test.getLowElementPos(pt);
		System.out.println(test.getGridElement(pt));
		System.out.println("LOWEST:	" + test.getLowElementPos(pt));
		
//		test.printRow(1);
//		test.printCol(1);
//		test.printGrid();
	}

}
