package application;

import java.text.DecimalFormat;

import javafx.geometry.Pos;

public class FloatFormat extends CellFormat {

	private int numOfDecimals;
	
	public FloatFormat(Cell cell, int numOfDecimals) throws Error {
		super(cell);
		this.numOfDecimals = numOfDecimals;
		
		String temp = cell.getValueObject().getValue();
		
		int cnt = 0;
		for(int i = 0; i < temp.length(); i++) {
			if((temp.charAt(i) < '0' || temp.charAt(i) > '9') && temp.charAt(i) != '.') throw new Error("Vrednost celije ne odgovara izabranom formatu.");
			
			if(temp.charAt(i) == '.') {
				cnt++;
				
				if(cnt > 1) throw new Error("Vrednost celije ne odgovara izabranom formatu.");
			}
		}
	}

	@Override
	public void format() {
		if(cell.getValueObject().getValue() == "") return;
		double temp = Double.parseDouble(cell.getValueObject().getValue());
		
		StringBuilder sb = new StringBuilder("0.");
		for(int i = 0; i < numOfDecimals; i++) sb.append('0');
		
		cell.setText(new DecimalFormat(sb.toString()).format(temp));
		cell.setAlignment(Pos.BASELINE_RIGHT);
	}

	@Override
	public char label() {
		return 'F';
	}

}
