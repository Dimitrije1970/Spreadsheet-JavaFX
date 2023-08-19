package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Main class
 * @author Dimitrije
 */
public class Main extends Application {
	
	private Parser parser;
	private Table table;
	private Cell selectedCell;
	
	private TextField cellValue;
	
	private boolean unsavedChanges = false;
	
	private void populateWindow(StackPane parent) {
		BorderPane header = new BorderPane();
		
		/////////////////////////////////////////
		Menu newTable = new Menu();
		Label newTableLabel = new Label("Nova tabela");
		
		newTableLabel.setOnMouseClicked((event)->{
			for(Cell cell : table.getTable()) {
				cell.setValueObject(new CellValue(""));
				cell.setText("");
				cell.setFormatObject(new TextFormat(cell));
			}
		});
		
		newTable.setGraphic(newTableLabel);
		
		/////////////////////////////////////////
		Menu loading = new Menu("Ucitaj iz fajla");
		
		MenuItem uop1 = new MenuItem("CSV fajl");
		MenuItem uop2 = new MenuItem("JSON fajl");
		
		TextInputDialog textDialog = new TextInputDialog();
		textDialog.setHeaderText("Unesi ime fajla: ");
		
		uop1.setOnAction((event)->{
			parser = new CSVParser(table);
			textDialog.showAndWait();
			parser.openFile(textDialog.getEditor().getText());
			parser.fillTable();
		});
		
		uop2.setOnAction((event)->{
			parser = new JSONParser(table);
			textDialog.showAndWait();
			parser.openFile(textDialog.getEditor().getText());
			parser.fillTable();
		});
		
		loading.getItems().add(uop1);
		loading.getItems().add(uop2);
		
		/////////////////////////////////////////
		Menu saving = new Menu("Sacuvaj");
		
		MenuItem cop1 = new MenuItem("CSV fajl");
		MenuItem cop2 = new MenuItem("JSON fajl");
		
		TextInputDialog fileNameDialog = new TextInputDialog();
		fileNameDialog.setHeaderText("Unesi ime fajla: ");
		
		cop1.setOnAction((event)->{
			fileNameDialog.showAndWait();
			table.saveCSV(fileNameDialog.getEditor().getText());
		});
		
		cop2.setOnAction((event)->{
			fileNameDialog.showAndWait();
			table.saveJSON(fileNameDialog.getEditor().getText());
		});
		
		saving.getItems().add(cop1);
		saving.getItems().add(cop2);
		
		/////////////////////////////////////////
		Menu columnFormatting = new Menu("Formatiraj kolonu");
		
		MenuItem cfop1 = new MenuItem("Tekst");
		MenuItem cfop2 = new MenuItem("Ceo broj");
		MenuItem cfop3 = new MenuItem("Decimalni broj");
		MenuItem cfop4 = new MenuItem("Datum");
		
		Alert cAlert = new Alert(Alert.AlertType.ERROR);
		
		TextInputDialog columnDialog = new TextInputDialog();
		columnDialog.setHeaderText("Unesi oznaku kolone za formatiranje: ");
		
		cfop1.setOnAction((event)->{
			System.out.println("AAA");
			columnDialog.showAndWait();
			for(Cell cell : table.getTable()) {
				if(("" + cell.getCellId().toString().charAt(0)).equals(columnDialog.getEditor().getText())) {
					cell.setFormatObject(new TextFormat(cell));
				}
			}
			
			unsavedChanges = true;
		});
		
		cfop2.setOnAction((event)->{
			try {
				columnDialog.showAndWait();
				for(Cell cell : table.getTable()) {
					if(("" + cell.getCellId().toString().charAt(0)).equals(columnDialog.getEditor().getText())) {
						cell.setFormatObject(new NumberFormat(cell));
					}
				}
				
				unsavedChanges = true;
			} catch (Error e) {
				cAlert.setHeaderText(e.getMessage());
				cAlert.showAndWait();
			}
		});
		
		TextInputDialog cnumOfDecimalsDialog = new TextInputDialog();
		cnumOfDecimalsDialog.setHeaderText("Unesi broj decimala: ");
		
		cfop3.setOnAction((event)->{
			try {
				columnDialog.showAndWait();
				cnumOfDecimalsDialog.showAndWait();
				
				for(Cell cell : table.getTable()) {
					if(("" + cell.getCellId().toString().charAt(0)).equals(columnDialog.getEditor().getText())) {
						cell.setFormatObject(new FloatFormat(cell, Integer.parseInt(cnumOfDecimalsDialog.getEditor().getText())));
					}
				}
				
				unsavedChanges = true;
			} catch (Error e) {
				cAlert.setHeaderText(e.getMessage());
				cAlert.showAndWait();
			}
		});
		
		cfop4.setOnAction((event)->{
			try {
				columnDialog.showAndWait();
				for(Cell cell : table.getTable()) {
					if(("" + cell.getCellId().toString().charAt(0)).equals(columnDialog.getEditor().getText())) {
						cell.setFormatObject(new DateFormat(cell));
					}
				}
				
				unsavedChanges = true;
			} catch (Error e) {
				cAlert.setHeaderText(e.getMessage());
				cAlert.showAndWait();
			}
		});
		
		columnFormatting.getItems().add(cfop1);
		columnFormatting.getItems().add(cfop2);
		columnFormatting.getItems().add(cfop3);
		columnFormatting.getItems().add(cfop4);
		
		/////////////////////////////////////////
		Menu cellFormatting = new Menu("Formatiraj celiju");
		
		MenuItem fop1 = new MenuItem("Tekst");
		MenuItem fop2 = new MenuItem("Ceo broj");
		MenuItem fop3 = new MenuItem("Decimalni broj");
		MenuItem fop4 = new MenuItem("Datum");
		
		Alert alert = new Alert(Alert.AlertType.ERROR);
		
		fop1.setOnAction((event)->{
			if(selectedCell != null) {
				selectedCell.setFormatObject(new TextFormat(selectedCell));
				unsavedChanges = true;
			}
		});
		
		fop2.setOnAction((event)->{
			if(selectedCell != null) {
				try {
					selectedCell.setFormatObject(new NumberFormat(selectedCell));
					unsavedChanges = true;
				} catch (Error e) {
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
		
		TextInputDialog numOfDecimalsDialog = new TextInputDialog();
		numOfDecimalsDialog.setHeaderText("Unesi broj decimala: ");
		
		fop3.setOnAction((event)->{
			if(selectedCell != null) {
				try {
					numOfDecimalsDialog.showAndWait();
					selectedCell.setFormatObject(new FloatFormat(selectedCell, Integer.parseInt(numOfDecimalsDialog.getEditor().getText())));
					unsavedChanges = true;
				} catch (Error e) {
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
		
		fop4.setOnAction((event)->{
			if(selectedCell != null) {
				try {
					selectedCell.setFormatObject(new DateFormat(selectedCell));
					unsavedChanges = true;
				} catch (Error e) {
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
		
		cellFormatting.getItems().add(fop1);
		cellFormatting.getItems().add(fop2);
		cellFormatting.getItems().add(fop3);
		cellFormatting.getItems().add(fop4);
		
		/////////////////////////////////////////
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(newTable);
		menuBar.getMenus().add(loading);
		menuBar.getMenus().add(saving);
		menuBar.getMenus().add(columnFormatting);
		menuBar.getMenus().add(cellFormatting);
		
		BorderPane container = new BorderPane();
	
		HBox hbox = new HBox();
		
		cellValue = new TextField();
		Button submitButton = new Button("Postavi");
		
		submitButton.setOnAction((event)->{
			if(selectedCell != null) { 
				if(cellValue.getText().length() > 0 && cellValue.getText().charAt(0) == '=') {
					Formula formula = new Formula(cellValue.getText());
					table.getTable().get(table.getTable().indexOf(selectedCell)).setValueObject(formula);

					String res = ((Formula) (selectedCell.getValueObject())).calculate(table.tableToCSVString());
					
					if(res.equals("ERROR")) {
						selectedCell.setStyle("-fx-text-fill: red;");
					}else{
						selectedCell.setStyle("-fx-text-fill: black;");
					}		
							
					selectedCell.getValueObject().setValue(res);
					selectedCell.setText(selectedCell.getValueObject().getValue());
					
					unsavedChanges = true;
				}else {
					selectedCell.setStyle("-fx-text-fill: black;");
					selectedCell.getValueObject().setValue(cellValue.getText());
					selectedCell.setFormatObject(new TextFormat(selectedCell));
					selectedCell.setText(cellValue.getText());
					unsavedChanges = true;
				}
			}
		});
		
		hbox.setPadding(new Insets(5, 5, 0, 5));
		HBox.setHgrow(cellValue, Priority.ALWAYS);
		hbox.getChildren().add(cellValue);
		hbox.getChildren().add(submitButton);
		
		header.setTop(menuBar);
		header.setCenter(hbox);
		
		
		table = new Table(20, 10, this);
		
		container.setTop(header);
		container.setCenter(table);
		
		parent.getChildren().add(container);
	}
	
	private Parent getParent() {
		return new StackPane();
	}
	
	public TextField getCellValue() {
		return cellValue;
	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	public void setSelectedCell(Cell selectedCell) {
		this.selectedCell = selectedCell;
	}

	public boolean isUnsavedChanges() {
		return unsavedChanges;
	}

	public void setUnsavedChanges(boolean unsavedChanges) {
		this.unsavedChanges = unsavedChanges;
	}

	@Override
	public void start(Stage stage) throws IOException {
		Scene scene = new Scene(getParent());
		
		populateWindow((StackPane) scene.getRoot());
		
		String[] options = {"Prekini bez cuvanja", "CSV fajl", "JSON fajl"};
		ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(options[0], options);
		choiceDialog.setContentText("Izaberi format za cuvanje tabele: ");
		
		TextInputDialog fileNameDialog = new TextInputDialog();
		fileNameDialog.setHeaderText("Unesi ime fajla: ");
		
		stage.setOnCloseRequest((event)->{
			if(unsavedChanges == true) {
				choiceDialog.showAndWait();

				if(choiceDialog.getSelectedItem().equals(options[1])) {
					fileNameDialog.showAndWait();
					table.saveCSV(fileNameDialog.getEditor().getText());
				}else if(choiceDialog.getSelectedItem().equals(options[2])) {
					fileNameDialog.showAndWait();
					table.saveJSON(fileNameDialog.getEditor().getText());
				}
			}
		});
		
		stage.setTitle("Excel");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
	
	public static void main(String[] args) {
		System.loadLibrary("Formula");
		launch();
	}
}
