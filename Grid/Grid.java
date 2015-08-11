package Grid;

import java.util.ArrayList;
import Point2d.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

	public void setGridElement(Point2d pos, T data) {
		setGridElement(pos.getX(), pos.getY(), data);
	}

	public void setGridElement(int inputRow, int inputCol, T data){
		this.grid.get(inputRow).set(inputCol, data);
	}

	public void setAll(T data){
		for(Grid<T>.Element tmpElem : this.elements()) {
				setGridElement(tmpElem.gridPt(), data);
		}
	}

	public void setNulls(T data){
		for(Grid<T>.Element tmpElem : this.elements()) {
			if(tmpElem.value() == null) {
				setGridElement(tmpElem.gridPt(), data);
			}
		}
	}

	public boolean compare(Grid<T> grid2) {

		if(this.row != grid2.row || this.col != grid2.col) {
			return false;
		}
	 
		for(Grid<T>.Element tmpElem : this.elements()) {
			Grid<T>.Element tmpElem2 = grid2.getElement(tmpElem.pos());
			
			T v1 = tmpElem.value();
			T v2 = tmpElem2.value();
	 
			System.out.print("Grid1: " + v1);
			System.out.println(" Grid2: " + v2);
	 
			if (v1 == null || v2 == null){
				if (v1 != v2){
					return false;
				} else {
					continue;
				}
			}
	 
			if (!v1.equals(v2)){
				return false;
			}
		}
		return true;

	}

	public void copy(Grid<T> grid2) {

		if(grid2.row != this.row || grid2.col != this.col) {
			return;
		}

		for(Grid<T>.Element tmpElem : elements()) {
			grid2.setGridElement(tmpElem.gridPt, tmpElem.value());
		}
	}

	public T getGridElement(Point2d pos){
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

	//only works for Integers...
	public Point2d getLowElementPos(Point2d pos) {
		int x = pos.getX();
		int y = pos.getY();
		Point2d n = new Point2d(x-1, y);
		Point2d s = new Point2d(x+1, y);
		Point2d e = new Point2d(x, y+1);
		Point2d w = new Point2d(x, y-1);
		ArrayList<Point2d> elements = new ArrayList<Point2d>();
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
		Point2d lowPoint2d = elements.get(0);
		T lowValue = getGridElement(lowPoint2d);
		for(int i=1; i<elements.size(); i++) {
			Point2d tmpPoint2d = elements.get(i);
			T tmpValue = getGridElement(tmpPoint2d);
			if((Integer)lowValue > (Integer)tmpValue) {
				lowValue = tmpValue;
				elementIndex = i;
			}
		}

		return elements.get(elementIndex);
		
	}

	//Is this position actually in the grid?
	public boolean inGrid(Point2d pos) {
		boolean decision = true;

		if(pos.getY() > this.col-1 || pos.getX() > this.row-1 || pos.getY() < 0 || pos.getX() < 0) {
			decision = false;
		}

		return decision;
	}

	public Point2d getLongestCharPos() {
		Point2d longestCharPos = new Point2d(0,0);
		int longestCharSize = 0;

		for(Grid<T>.Element tmpElem : this.elements()) {
			String tmpChar = "";
			if(tmpElem.value() == null) {
				tmpChar = "    ";
			}
			else {
				tmpChar = tmpElem.value().toString();
			}
			int tmpCharSize = tmpChar.length();
			if(tmpCharSize > longestCharSize) {
				longestCharSize = tmpCharSize;
				longestCharPos = tmpElem.gridPt;
			}
		}
		return longestCharPos;
	}


	public Element getElement(int pos){
		int elementRow = (int) pos/this.col;
		int elementCol = pos%this.col;
		Point2d gridPt = new Point2d(elementRow, elementCol);
		if (!inGrid(gridPt)){
			return null;
		}

		return new Element(pos);
	}


	public class Element {
		private final int pos;
		private final Point2d gridPt;

		public Element(int pos){
			this.pos = pos;
			int elementRow = (int)pos/Grid.this.col;
			int elementCol = pos%Grid.this.col;
			this.gridPt = new Point2d(elementRow, elementCol);
		}

		public int pos(){
			return pos;
		}

		public Point2d gridPt() {
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

		public String toString(){
			String str = "Pos: " + this.gridPt.toString();
			return str;
		}
	}

	public Iterable<Element> elements() {
		return () -> new Iterator<Element> () {
			private Element elt = getElement(0); //start at position 0

			public boolean hasNext() {
				return elt != null;
			}

			public Element next() {
				if (!hasNext()){
					throw new NoSuchElementException(); //required by Iterator interface to throw this
				}
				Element oldElt = elt;
				elt = oldElt.next();
				return oldElt;
			}
		};

	}	

	private void printSpaces(int numSpaces) {
		for(int i=0; i<numSpaces; i++) {
			System.out.print(" ");
		}
	}

	public void printGrid() {
		Point2d longestCharPos = this.getLongestCharPos();
		String longestStr = "";		
		if(getGridElement(longestCharPos) == null) {
			longestStr = "    ";
		}
		else {
			longestStr = getGridElement(longestCharPos).toString();
		}
		int maxLength =  longestStr.length();
		maxLength = maxLength+1; //Adding an extra space between printed cells

		for(Grid<T>.Element tmpElem : this.elements()) {
			if(tmpElem.pos%this.col == 0) {
				System.out.println();
			}
			if(tmpElem.value() == null) {
				if(maxLength > 4) {
					printSpaces(maxLength-4);
				}
				System.out.print("    ");
			}
			else {
				String tmpStr = tmpElem.value().toString();
				if(tmpStr.length() < maxLength) {
					printSpaces(maxLength-tmpStr.length());
				}
				System.out.print(tmpStr);
			}
		}
		System.out.println();	
	}

	public static void main(String[] args) {
		Grid<Integer> intGrid1 = new Grid<Integer>(3,5,0);

		System.out.println("Row: " + intGrid1.getRow());
		System.out.println("Col: " + intGrid1.getCol());

		Grid<Integer> intGrid2 = new Grid<Integer>(3,4,0);

		System.out.println("Are intGrid1 and intGrid2 the same? " + intGrid2.compare(intGrid1));

		intGrid2.setGridElement(0,1,1);
		intGrid2.setGridElement(0,2,2);
		intGrid2.setGridElement(0,3,3);
		intGrid2.setGridElement(1,0,4);
		intGrid2.setGridElement(1,1,5);
		intGrid2.setGridElement(1,2,6);
		intGrid2.setGridElement(1,3,7);
		intGrid2.setGridElement(2,0,8);
		intGrid2.setGridElement(2,1,9);
		intGrid2.setGridElement(2,2,null);
		intGrid2.setGridElement(2,3,99999);

		Point2d longestCharPos = intGrid2.getLongestCharPos();
		System.out.println("LongestChar in intGrid2: " + (String) longestCharPos.toString());

		intGrid2.printGrid();
		intGrid1.printGrid();

		Grid<Integer> intGrid3 = new Grid<Integer>(3,4,99);
		intGrid3.printGrid();
		intGrid2.copy(intGrid3);
		intGrid2.printGrid();
		System.out.println(intGrid3.compare(intGrid3));

		intGrid2.setNulls(101101101);
		intGrid2.printGrid();

		intGrid2.setAll(77);
		intGrid2.printGrid();

	}

}
