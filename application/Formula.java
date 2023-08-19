package application;

public class Formula extends CellValue {

	public Formula(String value) {
		super(value);

	}

	public native String calculate(String table);
}
