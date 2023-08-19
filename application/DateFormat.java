package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;

public class DateFormat extends CellFormat{

	public DateFormat(Cell cell) throws Error {
		super(cell);
		
		Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}\\.$");
		Matcher matcher = pattern.matcher(cell.getValueObject().getValue());
		boolean matchFound = matcher.find();
		if(matchFound == false && cell.getText() != "") throw new Error("Vrednost celije ne odgovara izabranom formatu.");
	}

	@Override
	public void format() {
		cell.setAlignment(Pos.CENTER);
	}

	@Override
	public char label() {
		return 'D';
	}

}
