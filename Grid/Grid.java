import java.util.ArrayList;

public class Grid<T> {

	ArrayList<ArrayList<T>> grid;
	int row;
	int col;
	T nullElement;

	public Grid(int inputRow, int inputCol, T inputNullElement) {
		this.row = inputRow;
		this.col = inputCol;
		this.nullElement = inputNullElement;
		this.grid = new ArrayList<ArrayList<T>>();
		for(int i=0; i<this.row; i++) {
			grid.add(new ArrayList<T>());
			for(int j=0; j<this.col; j++) {
				this.grid.get(i).add(this.nullElement);
			}
		}
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public void setGridElement(int inputRow, int inputCol, T data) {
		this.grid.get(inputRow).set(inputCol, data);
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
		Grid<Integer> test = new Grid<Integer>(3,5, new Integer("0"));
		System.out.println("Row: " + test.getRow());
		System.out.println("Col: " + test.getCol());

		test.setGridElement(0,1, new Integer("1"));
		test.setGridElement(0,2, new Integer("2"));
		test.setGridElement(0,3, new Integer("3"));
		test.setGridElement(0,4, new Integer("4"));

		test.setGridElement(1,0, new Integer("5"));
		test.setGridElement(1,1, new Integer("6"));
		test.setGridElement(1,2, new Integer("7"));
		test.setGridElement(1,3, new Integer("8"));
		test.setGridElement(1,4, new Integer("9"));

		test.printRow(1);
		test.printCol(1);
		test.printGrid();
	}

}
