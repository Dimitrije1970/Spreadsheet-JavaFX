package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser extends Parser {

	public JSONParser(Table table) {
		super(table);
	}

	@Override
	public void fillTable() {
		if(fileContent == null) return;
		Pattern pattern = Pattern.compile("\"([^\"]*)\"[ :]*\"([^\"]*)\"");
		Matcher matcher = pattern.matcher(fileContent);
		
		while(matcher.find()) {
			String id = matcher.group(1);
			
			char format = matcher.group(2).charAt(0);
			String value = matcher.group(2).substring(1);
			
			for(Cell cell : table.getTable()) {
				if(cell.getCellId().toString().equals(id)) {
					cell.getValueObject().setValue(value);
					cell.setText(value);
					
					try {
						if(format == 'T') {
							cell.setFormatObject(new TextFormat(cell));
						}else if(format == 'N') {
							cell.setFormatObject(new NumberFormat(cell));
						}else if(format == 'F') {
							cell.setFormatObject(new FloatFormat(cell, 4));
						}else if(format == 'D') {
							cell.setFormatObject(new DateFormat(cell));
						}
					}catch(Error e) {}
				}
			}
			
		}
		
	}
}
