package application;

import javafx.geometry.Pos;

public class NumberFormat extends CellFormat {
	
	public NumberFormat(Cell cell) throws Error {
		super(cell);
		
		String temp = cell.getValueObject().getValue();
		
		for(int i = 0; i < temp.length(); i++) {
			if(temp.charAt(i) < '0' || temp.charAt(i) > '9') throw new Error("Vrednost celije ne odgovara izabranom formatu.");
		}
	}

	@Override
	public void format() {
		cell.setAlignment(Pos.BASELINE_RIGHT);
	}

	@Override
	public char label() {
		return 'N';
	}

}
