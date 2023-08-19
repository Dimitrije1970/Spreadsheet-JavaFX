package application;

public abstract class CellFormat {
	
	protected Cell cell;
	
	public CellFormat(Cell cell) {
		this.cell = cell;
	}
	
	public abstract void format();
	public abstract char label();
}
