import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class FreeCellPanel extends DragZone {
	
	public static final int DECK_SIZE = 52;
	private CascadePanel cascadePanel;
	private FreeCellSpacePanel freeCellSpacePanel;
	private FoundationPanel foundationPanel;

	Map<FaceAndSuit, BufferedImage> imageMap;

	public FreeCellPanel () {
		super(new Dimension(1200,700), false);
	}

	protected void build() {
		super.build();
        imageMap = new HashMap<>();

		freeCellSpacePanel = new FreeCellSpacePanel();
		add(freeCellSpacePanel);
		freeCellSpacePanel.setLocation(getFreeCellSpacePanelLocation());

		foundationPanel = new FoundationPanel();
		add(foundationPanel);
		foundationPanel.setLocation(getFoundationPanelLocation());

        cascadePanel = new CascadePanel();
        add(cascadePanel);
        cascadePanel.setLocation(getCascadePanelLocation());

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                GameController.clearDraggedCards();
            }
        });

		new CardLoader(imageMap).start();
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (GameController.isPlaying()) {
            Point textPoint = getDragLimitTextLocation();
            g.drawString("Card drag limit: " + GameController.getDragLimit(), textPoint.x, textPoint.y);
        }
    }

	public Point getCascadePanelLocation() {
		int x = (getWidth() - cascadePanel.getWidth()) / 2;
		int y = freeCellSpacePanel.getHeight();
		return new Point(x, y);
	}

	public Point getFreeCellSpacePanelLocation() {
		return new Point(0, 0);
	}

	public Point getFoundationPanelLocation() {
		return new Point(getWidth() - foundationPanel.getWidth(), 0);
	}

    public Point getDragLimitTextLocation() {
        Point freeCellPoint = getFreeCellSpacePanelLocation();
        return new Point(freeCellPoint.x + freeCellSpacePanel.getWidth(), freeCellPoint.y + 20);
    }

	public void deal() {
		new Thread() {
			public void run() {
				List<FaceAndSuit> shuffledKeys = CardShuffler.shuffle();
				List<CardStack> cardStacks = cascadePanel.getCardStacks();

				for (int i = 0; i < DECK_SIZE; i++) {
					int index = i % CascadePanel.NUMBER_OF_COLUMNS;

					final FaceAndSuit key = shuffledKeys.remove(0);
					final CardStack stack = cardStacks.get(index);
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							stack.addNewCard(key, imageMap);
						}
					});
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
                GameController.setPlaying(true);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        repaint();
                    }
                });
			}
		}.start();
	}

	public void resetGame() {
        GameController.setPlaying(false);
		cascadePanel.clearAllStacks();
		freeCellSpacePanel.clearAllStacks();
		foundationPanel.clearAllStacks();
        /**test*/GameController.stacks.clear();
        repaint();
	}

    public CascadePanel getCascadePanel() {
        return cascadePanel;
    }

    public FoundationPanel getFoundationPanel() {
        return foundationPanel;
    }

    public FreeCellSpacePanel getFreeCellSpacePanel() {
        return freeCellSpacePanel;
    }
}