package application;

public class CSVParser extends Parser{
	
	public CSVParser(Table table) {
		super(table);
	}

	@Override
	public void fillTable() {
		if(fileContent == null) return;
		
		String[] lines = fileContent.split("\\n");
		
		for(Cell cell : table.getTable()) {
			cell.getValueObject().setValue("");
			cell.setText("");
		}
		
		for(int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(",");
			
			for(int j = 0; j < values.length; j++) {
				table.getTable().get(i * 10 + j).getValueObject().setValue(values[j]);
				table.getTable().get(i * 10 + j).setText(values[j]);
				table.getTable().get(i * 10 + j).setFormatObject(new TextFormat(table.getTable().get(i * 10 + j)));
			}
		}
	}
	
}
