import java.util.*;

public class FoundationPanel extends CardStackPanel {
	
	public static final int NUMBER_OF_FOUNDATIONS = 4;

	@Override
	protected CardStack getNewStack(int index) {
		Suit suit = null;
		switch (index) {
			case 0: suit = Suit.Hearts;
				break;
			case 1: suit = Suit.Clubs;
				break;
			case 2: suit = Suit.Diamonds;
				break;
			case 3: suit = Suit.Spades;
				break;
		}

		return new Foundation(suit);
	}

	@Override
	protected int getNumberOfColumns() {
		return NUMBER_OF_FOUNDATIONS;
	}
}