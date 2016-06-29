public class CascadePanel extends CardStackPanel {
	
	public static final int NUMBER_OF_COLUMNS = 8;

	@Override
	protected CardStack getNewStack(int index) {
		return new Cascade();
	}

	@Override
	protected int getNumberOfColumns() {
		return NUMBER_OF_COLUMNS;
	}
}