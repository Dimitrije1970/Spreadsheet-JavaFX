package application;

import javafx.geometry.Pos;

public class TextFormat extends CellFormat{

	public TextFormat(Cell cell) {
		super(cell);
	}

	@Override
	public void format() {
		cell.setAlignment(Pos.BASELINE_LEFT);
	}

	@Override
	public char label() {
		return 'T';
	}

}
