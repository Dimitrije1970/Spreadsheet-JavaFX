package application;

public class CellIdentifier {

	private int row, col;
	private String id;
	
	public CellIdentifier(int row, int col) {
		this.row = row;
		this.col = col;
		
		StringBuilder sb = new StringBuilder();
		sb.append((char)('A' + (row)));
		sb.append(col+1);
		id = sb.toString();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return id;
	}
	
}
