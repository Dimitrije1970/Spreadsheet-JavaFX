package application;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * The class extends GridPane to arrange table cells in a grid
 * @author Dimitrije
 */
public class Table extends GridPane{
	
	private int rows, cols;
	private ArrayList<Cell> cells = new ArrayList<>();
	
	/**
	 * The constructor labels the columns and rows of the table 
	 * and initializes each cell as empty while adding it to the panel
	 * 
	 * @param rows Number of table rows
	 * @param cols Number of table columns
	 * @param parent Reference to Frame object
	 */
	public Table(int rows, int cols, Main parent) {
		this.rows = rows;
		this.cols = cols;
		
		setPadding(new Insets(5));
		
		for(int i = 0; i < cols; i++) { 
			Label label = new Label(Character.toString('A' + i));
			label.setPadding(new Insets(0, 0, 0, 5));
			add(label, i+1, 0);
		}
		
		for(int i = 0;i < rows; i++) {
			Label label = new Label(Integer.toString(i+1));
			label.setPadding(new Insets(0, 5, 0, 0));
			add(label, 0, i+1);
			
			for(int j = 0; j < cols; j++) {
				Cell temp = new Cell("", j, i);
				
				temp.setOnMouseClicked((event)->{
					parent.setSelectedCell(temp);
					parent.getCellValue().setText(parent.getSelectedCell().getText());
//					System.out.println(temp.getCellId());
				});
				
				temp.setOnAction((event)->{
					if(parent.getSelectedCell() != null) { 
						if(parent.getSelectedCell().getText().length() > 0 &&  parent.getSelectedCell().getText().charAt(0) == '=') {
							Formula formula = new Formula(parent.getSelectedCell().getText());
							cells.get(cells.indexOf(parent.getSelectedCell())).setValueObject(formula);
							
							String res = ((Formula) (parent.getSelectedCell().getValueObject())).calculate(tableToCSVString());
							
							if(res.equals("ERROR")) {
								parent.getSelectedCell().setStyle("-fx-text-fill: red;");
							}else{
								parent.getSelectedCell().setStyle("-fx-text-fill: black;");
							}
							
							parent.getSelectedCell().getValueObject().setValue(res);
							parent.getSelectedCell().setText(parent.getSelectedCell().getValueObject().getValue());
							
							parent.setUnsavedChanges(true);
						}else {
							parent.getSelectedCell().setStyle("-fx-text-fill: black;");
							parent.getSelectedCell().getValueObject().setValue(parent.getSelectedCell().getText());
							parent.getSelectedCell().setText(parent.getSelectedCell().getText());
							parent.getSelectedCell().setFormatObject(new TextFormat(parent.getSelectedCell()));
							parent.setUnsavedChanges(true);
						}
					}
				});
				
				add(temp, j+1, i+1);
				cells.add(temp);
			}
		}
	}
	
	/**
	 * Converts table to string
	 * @return A string that represents the whole file in CSV format
	 */
	public String tableToCSVString() {
		String fileContent = "";
		
		for(Cell cell : cells) {
			if(cell.getCellId().getRow() != 0) fileContent += ",";
			fileContent += cell.getValueObject().getValue();
			if(cell.getCellId().getRow() == 9) fileContent += "\n";
		}
		
		return fileContent;
	}
	
	/**
	 * This function enables saving the contents of the table 
	 * in CSV file
	 * @param fileName Name of the new file
	 */
	public void saveCSV(String fileName) {		
		String fileContent = tableToCSVString();
		
		try {
			try (PrintWriter output = new PrintWriter("src/" + fileName)) {
				output.print(fileContent);
			}
		} catch (FileNotFoundException e) {}
	}

	/**
	 * This function enables saving the content of the table
	 * in JSON format
	 * @param fileName Name of the new file
	 */
	public void saveJSON(String fileName) {
		String fileContent = "";
		fileContent += "{\n";

		for (Cell cell : cells) {
			fileContent += "\t\"";
			fileContent += cell.getCellId().toString();
			fileContent += "\":\"";
			fileContent += cell.getFormatObject().label();
			fileContent += cell.getValueObject().getValue();
			fileContent += "\",\n";
		}

		fileContent += "}\n";

		try {
			FileWriter fileWriter = new FileWriter("src/" + fileName);
			fileWriter.write(fileContent);
			fileWriter.close();
		} catch (IOException e) {}
	}
	
	/**
	 * @return Returns number of table rows
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * @return Returns number of table columns
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * @return Returns an ArrayList object that is a list of all cells in the table
	 */
	public ArrayList<Cell> getTable() {
		return cells;
	}

}
