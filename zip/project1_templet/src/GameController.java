import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameController {

    private static FreeCellPanel outerPanel;
    private static Container glassPane;
	private static CardStack sourceStack;
	private static List<FreeCellCard> draggedCards;
    private static boolean playing;

    /**temp*/public static List<CardStack> stacks;

	public static void initialize(FreeCellPanel op, Container gp) {
		outerPanel = op;
        glassPane = gp;
        playing = false;

        /**temp*/stacks = new ArrayList<>();
	}

    public static void setPlaying(boolean p) {
        playing = p;
    }

    public static boolean isPlaying() {
        return playing;
    }

    public static void handleGameEnd() {
        Component[] foundations = outerPanel.getFoundationPanel().getComponents();
        boolean finished = true;
        for (Component item : foundations) {
            if (((Foundation)item).getCards().size() < 13) {
                finished = false;
            }
        }
        if (finished) {
            playing = false;
            JOptionPane.showMessageDialog(outerPanel, "Congratulations! You have won the game!");
        }
    }

    public static FreeCellPanel getOuterPanel() {
        return outerPanel;
    }

    public static Container getGlassPane() {
        return glassPane;
    }

	public static int countEmptyCascades() {
		return outerPanel.getCascadePanel().getEmptyCount();
	}

	public static int countEmptyFreeCells() {
		return outerPanel.getFreeCellSpacePanel().getEmptyCount();
	}

    public static int getDragLimit() {
        return (countEmptyFreeCells() + 1) * (int)Math.pow(2, countEmptyCascades());
    }

	public static List<FreeCellCard> getDraggedCards() {
		return draggedCards;
	}

	public static void setDraggedCards(List<FreeCellCard> dc) {
		draggedCards = dc;
	}

    public static CardStack getSourceStack() {
        return sourceStack;
    }

    public static void setSourceStack(CardStack ss) {
        sourceStack = ss;
    }

    public static void removeSourceStack() {
        sourceStack = null;
    }

    public static void bindCardsToGlassPane(List<FreeCellCard> draggedCards, CardStack sourceStack) {
        setDraggedCards(draggedCards);
        setSourceStack(sourceStack);

        for (FreeCellCard card : draggedCards) {
            sourceStack.remove(card);
            glassPane.add(card);
            card.setLocation(SwingUtilities.convertPoint(sourceStack, card.getLocation(), glassPane));
            glassPane.setComponentZOrder(card, 0);
        }
        glassPane.setVisible(true);
        sourceStack.repaint();
    }

    public static void bindCardsToStack(CardStack destStack) {
        removeSourceStack();
        glassPane.removeAll();

        if (isDraggingCards()) {
            for (FreeCellCard card : draggedCards) {
                int index = destStack.getComponents().length;
                destStack.add(card);
                card.setLocation(destStack.getCardLocation(index));
                card.setParentStack(destStack);
                card.setStackIndex(index);
                destStack.setComponentZOrder(card, 0);
            }
            clearDraggedCards();
        }
        glassPane.setVisible(false);
        destStack.repaint();
    }

	public static void clearDraggedCards() {
		draggedCards = null;
	}

	public static boolean isDraggingCards() {
		return draggedCards != null;
	}

    public static List<FreeCellCard> createCardSublist(List<FreeCellCard> cards, int startIndex) {
        List<FreeCellCard> sublist = new ArrayList<>();
        for (int i = startIndex; i < cards.size(); i++) {
            sublist.add(cards.get(i));
        }
        return sublist;
    }
}