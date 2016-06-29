import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

public abstract class CardStack extends JPanel {
	
	public static final int MAX_DEFAULT_ROWS = 7;	//max number of rows in a stack column by at the start of the game
	public static final int CARD_WIDTH = 100;
	public static final int CARD_HEIGHT = 150;
	protected List<FreeCellCard> cards;		//list of all the cards in the stack

	public CardStack () {
		cards = Collections.synchronizedList(new ArrayList<>());
		build();
	}

	protected void build() {
		setLayout(null);
        setBorder(BorderFactory.createEtchedBorder());
		setSize(getPreferredSize());
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println();
            }
			public void mouseReleased(MouseEvent e) {
				//call FreeCellManager here
			}
		});
	}

	public List<FreeCellCard> getCards() {
		return cards;
	}

    public FreeCellCard getLastCard() {
        return cards.get(cards.size() - 1);
    }

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getPreferredWidth(), getPreferredHeight());
	}

	public int getPreferredWidth() {
		return CARD_WIDTH;
	}

	public int getPreferredHeight() {
		return CARD_HEIGHT;
	}

	public abstract boolean canDrag(int index);

	public abstract boolean canDrop(List<FreeCellCard> cards);

	public void doDrop(List<FreeCellCard> cards) {
        CardStack source = GameController.getSourceStack();
        for (FreeCellCard card : cards) {
            source.getCards().remove(card);
            this.cards.add(card);
        }
        GameController.bindCardsToStack(this);
        GameController.handleGameEnd();
	}

    public void addNewCard(FaceAndSuit key, Map<FaceAndSuit, BufferedImage> imageMap) {
        FreeCellCard card = new FreeCellCard(this, getCards().size(), new Dimension(CARD_WIDTH, CARD_HEIGHT));
        card.setImage(imageMap.get(key), key);
        cards.add(card);

        add(card);
        card.setLocation(getCardLocation(card.getStackIndex()));
        setComponentZOrder(card, 0);
        /**temp*/ GameController.stacks.add(this);
    }

	//use this for resetting the game
	public void clearCards() {
		cards.clear();
		removeAll();
	}

	public Point getCardLocation(int index) {
		return new Point(0,0);
	}

	public Point getCardOffset(int index) {
		return new Point(0,0);
	}
}