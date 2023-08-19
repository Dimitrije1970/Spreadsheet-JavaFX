
/**
 * Java project for POOP
 * @author Dimitrije
 */
module Projekat_POOP {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
