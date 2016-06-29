import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FreeCellCard extends CardImagePanel {

	private int stackIndex;
	private CardStack parentStack;
	
	public FreeCellCard (CardStack parentStack, int stackIndex, Dimension scaleSize) {
		super(true, scaleSize);
		this.parentStack = parentStack;
		this.stackIndex = stackIndex;
	}

    @Override
    protected void build() {
        setLayout(null);
        super.build();
        setSize(getPreferredSize());
    }

	@Override
	protected boolean beforeDrag(MouseEvent e) {
        if (!GameController.isPlaying()) return false;
		if (!GameController.isDraggingCards()) {
			boolean canDrag = parentStack.canDrag(stackIndex);
			if (canDrag) {
                List<FreeCellCard> stackCards = parentStack.getCards();
                List<FreeCellCard> draggedCards = GameController.createCardSublist(stackCards, stackIndex);
				GameController.bindCardsToGlassPane(draggedCards, parentStack);
			}
			return canDrag;
		}
		return true;
	}

	@Override
	protected void handlePressOnAttachment(MouseEvent e) {
		List<FreeCellCard> cards = parentStack.getCards();
		if (stackIndex < cards.size() - 1) {
			cards.get(stackIndex + 1).handleMousePress(e);
		}
	}

	@Override
	protected void handleDragOnAttachment(MouseEvent e) {
		List<FreeCellCard> cards = parentStack.getCards();
		if (stackIndex < cards.size() - 1) {
			cards.get(stackIndex + 1).handleMouseDrag(e);
		}
	}

    @Override
    protected void handleMouseRelease(MouseEvent e) {
        if (GameController.isDraggingCards()) {
            Point mousePoint = e.getLocationOnScreen();

            //retrieves the stack the card is dropped over and handles drop if drop is allowed
            Component deepComp = SwingUtilities.getDeepestComponentAt(GameController.getOuterPanel(), mousePoint.x, mousePoint.y);
            CardStack source = GameController.getSourceStack();
            if (deepComp != null) {
                CardStack stack = null;
                if (deepComp instanceof CardStack) {
                    stack = (CardStack) deepComp;
                } else if (deepComp instanceof FreeCellCard) {
                    stack = ((FreeCellCard) deepComp).getParentStack();
                }
                if (stack != null && stack != source && stack.canDrop(GameController.getDraggedCards())) {
                    stack.doDrop(GameController.getDraggedCards());
                } else {
                    GameController.bindCardsToStack(source);
                }
            }
        }
    }

	//remove right click adjusting of z-index
	@Override
	protected void handleRightClickEffects() {}

    public void setParentStack(CardStack parentStack) {
        this.parentStack = parentStack;
    }

	public CardStack getParentStack() {
		return parentStack;
	}

    public void setStackIndex(int stackIndex) {
        this.stackIndex = stackIndex;
    }

	public int getStackIndex() {
		return stackIndex;
	}

    @Override
    public String toString() {
        return "Free Cell Card: " + identity.toString();
    }
}