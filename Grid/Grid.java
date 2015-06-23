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
		point2d longestPos = getLongestChar();
		String longestStr = "";		
		if(getGridElement(longestPos) == null) {
			longestStr = "    ";
		}
		else {
			longestStr = getGridElement(longestPos).toString();
		}
		int numCharsLongestStr =  longestStr.length();

		for(int i=0; i<this.row; i++) {
			for(int j=0; j<this.col; j++) {
				T tmpElement = this.grid.get(i).get(j);
				int numCharTmpStr = 0;
				String tmpStr = "";	
			
				if(tmpElement == null) {
					if(j==this.col-1) {
						System.out.println();
						break;
					}
					tmpStr = "    ";
					numCharTmpStr = tmpStr.length();
					int diff = numCharsLongestStr - numCharTmpStr;
					for(int n=0; n<diff; n++) {
						tmpStr = tmpStr + " ";
					}

				}
				else {
					tmpStr = tmpElement.toString();
					numCharTmpStr = tmpStr.length();
					int diff = numCharsLongestStr - numCharTmpStr;

					for(int n=0; n<diff; n++) {
						tmpStr = tmpStr + " ";
					}
				}

				System.out.print(tmpStr);
			}
		}
	}

	//only works for Integers...
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

	public point2d getLongestChar() {
		point2d longestCharPos = new point2d(0,0);
		int longestCharSize = 0;
		for(int i=0; i<this.row; i++) {
			for(int j=0; j<this.col; j++) {
				String tmpChar = "";
				if(getGridElement(i,j) == null) {
					tmpChar = "    ";
				}
				else {
					tmpChar = getGridElement(i,j).toString();
				}
				int tmpCharSize = tmpChar.length();
				if(tmpCharSize > longestCharSize) {
					longestCharSize = tmpCharSize;
					longestCharPos = new point2d(i,j);
				}
			}
		}
		return longestCharPos;
	}

	public Element getElement(int pos){
		int elementRow = pos/this.row;
		int elementCol = pos%this.row;
		point2d gridPt = new point2d(elementRow, elementCol);
		if (!inGrid(gridPt)){
			return null;
		}

		return new Element(pos);
	}


	public class Element {
		private final int pos;
		private final point2d gridPt;

		public Element(int pos){
			this.pos = pos;
			int elementRow = pos/Grid.this.row;
			int elementCol = pos%Grid.this.row;
			this.gridPt = new point2d(elementRow, elementCol);
		}

		public int pos(){
			return pos;
		}

		public point2d gridPt() {
			return gridPt;
		}

		public Element next(){
			return getElement(pos + 1);
		}

		public Element prev(){
			return getElement(pos - 1);
		}

		public T value(){
			return getGridElement(gridPt);
		}
	}

	public static void main(String[] args) {
		Grid<Integer> test = new Grid<Integer>(3,5,0);
		System.out.println("Grid equals itself? " + test.compare(test));
		Grid<Integer> test2 = new Grid<Integer>(3,5,0);
		System.out.println("Are test and test2 grid the same? " + test.compare(test2));

		System.out.println("Row: " + test.getRow());
		System.out.println("Col: " + test.getCol());


		test.setGridElement(0,1, 1);
		test.setGridElement(0,2, null);
		test.setGridElement(0,3, 3);
		test.setGridElement(0,4, 4);

		test.setGridElement(1,0, 5);
		test.setGridElement(1,1, 6);
		test.setGridElement(1,2, 7);
		test.setGridElement(1,3, 888888888);
		test.setGridElement(1,4, 9);

		test.setGridElement(2,1, 99);
		test.setGridElement(2,4,999999);

		test.printGrid();

		point2d results = test.getLongestChar();
		System.out.println("LongestChar: " + (String) results.toString());
		int results2 = 13/14;
		System.out.println("(int) 17/14: " + results2);
		int results3 = 13%14;
		System.out.println("17%14: " + results3);
		
//		test.printRow(1);
//		test.printCol(1);
//		test.printGrid();
	}

}
