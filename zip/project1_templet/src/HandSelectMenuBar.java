import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class HandSelectMenuBar extends JMenuBar {

	public static final int HAND_SIZE = 5;

	public static final int CARD_WIDTH = 150;
	public static final int CARD_HEIGHT = 225;
	public static final int GAP_BETWEEN_CARDS = 20;

	private Integer xOffset, yOffset;

	private JFrame frameParent;
	private JPanel paneParent;
	private List<CardImagePanel> handList;
	
	public HandSelectMenuBar (JFrame frameParent, JPanel paneParent) {
		this.frameParent = frameParent;
		this.paneParent = paneParent;
		this.handList = new ArrayList<>();

		build();
	}

	private void build() {
		final HandChooserDialog dialog = new HandChooserDialog(frameParent);

		JMenu cardsMenu = new JMenu("Cards");
		JMenuItem chooseItem = new JMenuItem("Choose");

		chooseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(true);
				if (dialog.getAcceptedConfig() != null) {
					populateCardHand(dialog);
				}
			}
		});

		cardsMenu.add(chooseItem);
		add(cardsMenu);
	}

	private void initializePositions() {
		int totalCardWidth = CARD_WIDTH * HAND_SIZE + GAP_BETWEEN_CARDS * (HAND_SIZE - 1);
		int totalCardHeight = CARD_HEIGHT;
		this.xOffset = (paneParent.getWidth() - totalCardWidth) / 2;
		this.yOffset = (paneParent.getHeight() - totalCardHeight) / 2;
	}

	public void populateCardHand(HandChooserDialog dialog) {
		CardImagePanel card;
		FaceAndSuit key;
		List<FaceAndSuit> config = dialog.getAcceptedConfig();
		Map<FaceAndSuit, BufferedImage> imageMap = dialog.getCardChooserPanel().getImageMap();

		if (xOffset == null) {
			initializePositions();
		}

		for (int i = 0; i < HAND_SIZE; i++) {
			if (handList.size() <= i) {
				card = new CardImagePanel(true, new Dimension(CARD_WIDTH,CARD_HEIGHT));
				handList.add(card);
				paneParent.add(card);
                card.setSize(card.getPreferredSize());
                card.setLocation(xOffset + i * (CARD_WIDTH + GAP_BETWEEN_CARDS), yOffset);
			} else {
				card = handList.get(i);
			}
			key = config.get(i);
			card.setImage(imageMap.get(key), key);
			card.repaint();
			paneParent.repaint();
		}
	}

	public JFrame getFrameParent() {
		return frameParent;
	}

	public JPanel getPaneParent() {
		return paneParent;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}
}
