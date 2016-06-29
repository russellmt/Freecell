import java.util.*;

public class FreeCellSpace extends CardStack {
	
	@Override
	public boolean canDrag(int index) {
		return true;
	}

	@Override
	public boolean canDrop(List<FreeCellCard> cards) {
		return cards.size() == 1 && this.cards.isEmpty();
	}
}