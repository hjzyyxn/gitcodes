package decorator;

public class SideBorder extends Border {
	private char borderChar;
	public SideBorder(Display display, char ch) {
		super(display);
		this.borderChar = ch;
	}

	@Override
	public int getColumns() {
		// TODO Auto-generated method stub
		return display.getColumns() + 2;
	}

	@Override
	public int getRows() {
		// TODO Auto-generated method stub
		return display.getRows();
	}

	@Override
	public String getRowText(int row) {
		// TODO Auto-generated method stub
		return borderChar + display.getRowText(row) + borderChar;
	}

}
