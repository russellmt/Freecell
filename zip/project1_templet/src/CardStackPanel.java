import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

public abstract class CardStackPanel extends JPanel {

	public static final int COLUMN_GAP = 20;
	public static final int EDGE_PADDING = 20;
	protected List<CardStack> cardStacks;

	public CardStackPanel () {
		cardStacks = new ArrayList<>();
		build();
	}

	protected void build() {
		setLayout(null);
		int columnCount = getNumberOfColumns();
		for (int i = 0; i < columnCount; i++) {
			CardStack stack = getNewStack(i);
            add(stack);
			stack.setLocation(new Point(i * (stack.getWidth() + COLUMN_GAP), EDGE_PADDING));
            cardStacks.add(stack);
		}
		setSize(getPreferredSize());

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println();
            }
        });
	}

	protected abstract CardStack getNewStack(int index);

	public List<CardStack> getCardStacks() {
		return cardStacks;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(getPreferredWidth(), getPreferredHeight());
	}

	public int getPreferredWidth() {
		int columnCount = getNumberOfColumns();
		return columnCount * cardStacks.get(0).getPreferredWidth() + (columnCount - 1) * COLUMN_GAP + 2 * EDGE_PADDING;
	}

	public int getPreferredHeight() {
		return cardStacks.get(0).getPreferredHeight() + 2 * EDGE_PADDING;
	}

	protected abstract int getNumberOfColumns();

	public int getEmptyCount() {
		int emptyCount = 0;
		for (CardStack stack : cardStacks) {
			if (stack.isEmpty()) emptyCount++;
		}
		return emptyCount;
	}

	//use for resetting the game
	public void clearAllStacks() {
		for (CardStack stack : cardStacks) {
			stack.clearCards();
		}
	}
}