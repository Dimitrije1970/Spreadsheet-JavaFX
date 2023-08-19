package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Parser {
	
	protected String fileContent;
	protected Table table;
	
	public Parser(Table table) {
		this.table = table;
	}
	
	public void openFile(String fileName) {
		try {
			Path path = Paths.get("src/" + fileName);
			byte[] bytes = Files.readAllBytes(path);
			fileContent = new String(bytes);
		} catch (IOException e) {}
		
	}
	
	public abstract void fillTable();
}
