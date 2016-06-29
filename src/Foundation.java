import java.awt.*;
import java.util.*;
import java.util.List;

public class Foundation extends CardStack {

	private Suit suit;

	public Foundation(Suit suit) {
		super();
		this.suit = suit;
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(suit.toString(), 10, getHeight() - 10);
    }
	
	@Override
	public boolean canDrag(int index) {
		return false;
	}

	@Override
	public boolean canDrop(List<FreeCellCard> cards) {
		if (cards.size() == 1 && cards.get(0).getSuit() == suit) {
			FreeCellCard card = cards.get(0);
			if (this.cards.isEmpty()) {
				return card.getFace() == Face.Ace;
			} else {
				return card.getFace().ordinal() == this.cards.get(this.cards.size() - 1).getFace().ordinal() + 1;
			}
		}
		return false;
	}

	public Suit getSuit() {
		return suit;
	}
}