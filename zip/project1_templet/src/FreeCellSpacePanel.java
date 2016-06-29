import java.util.*;

public class FreeCellSpacePanel extends CardStackPanel {
	
	public static final int NUMBER_OF_FREE_CELLS = 4;

	@Override
	protected CardStack getNewStack(int index) {
		return new FreeCellSpace();
	}

	@Override
	protected int getNumberOfColumns() {
		return NUMBER_OF_FREE_CELLS;
	}

	
}