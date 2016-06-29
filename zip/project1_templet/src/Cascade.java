import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Cascade extends CardStack {

	public static final int MAX_CARD_AMOUNT = 26;
	public static final int OVERLAP_GAP = 30;

	@Override
	public int getPreferredHeight() {
		return (MAX_CARD_AMOUNT - 1) * OVERLAP_GAP + CARD_HEIGHT;
	}

    @Override
    public Point getCardLocation(int index) {
        int y = OVERLAP_GAP * index;
        return new Point(0, y);
    }

	@Override
	public boolean canDrag(int index) {
        List<FreeCellCard> draggedCards = GameController.createCardSublist(cards, index);
		boolean tableau = true;

		FreeCellCard currentCard, nextCard;
		for (int i = 0; i < draggedCards.size() - 1; i++) {
			currentCard = draggedCards.get(i);
			nextCard = draggedCards.get(i + 1);
			if (currentCard.getFace().ordinal() != nextCard.getFace().ordinal() + 1) {
				tableau = false;
				break;
			} else if (currentCard.getColor().equals(nextCard.getColor())) {
				tableau = false;
				break;
			}
		}

		if (tableau) {
			int dragLimit = GameController.getDragLimit();
			return draggedCards.size() <= dragLimit;
		}
		return false;
	}

	@Override
	public boolean canDrop(List<FreeCellCard> cards) {
		if (this.cards.isEmpty()) {
			return true;
		} else {
			FreeCellCard firstDragCard = cards.get(0), lastDropCard = getLastCard();
			return firstDragCard.getFace().ordinal() + 1 == lastDropCard.getFace().ordinal() &&
				firstDragCard.getColor() != lastDropCard.getColor();
		}
	}
}