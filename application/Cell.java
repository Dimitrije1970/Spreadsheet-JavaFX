package application;

import javafx.scene.control.TextField;


/**
 * A cell abstraction
 * @author Dimitrije
 */
public class Cell extends TextField {

	private CellValue value;
	private CellIdentifier id;
	private CellFormat format;
	
	public Cell(String value, int row, int col) {
		this.value = new CellValue(value);
		this.id = new CellIdentifier(row, col);
		this.format = new TextFormat(this);
	}

	public CellValue getValueObject() {
		return value;
	}
	
	public void setValueObject(CellValue value) {
		this.value = value;
	}

	public CellIdentifier getCellId() {
		return id;
	}

	public CellFormat getFormatObject() {
		return format;
	}

	public void setFormatObject(CellFormat format) {
		this.format = format;
		format.format();
	}
	
	
}
