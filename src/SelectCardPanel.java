import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class SelectCardPanel extends JPanel {

	protected CardImagePanel cardPanel;

	public SelectCardPanel() {
		buildComponents();
	}

	protected void buildComponents() {
		CardChooserPanel cardChooserPanel = new CardChooserPanel(1, true);
		JButton button = new JButton("Show Card");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cardPanel == null) {
					initializeCardPanel();
					addCardPanel();
				}
				CardChooserControl selectionItem = cardChooserPanel.getCardSelectionItem(0);
				Face selectedFace = (Face) selectionItem.getFaceCombo().getSelectedItem();
				Suit selectedSuit = (Suit) selectionItem.getSuitCombo().getSelectedItem();
				FaceAndSuit key = new FaceAndSuit(selectedFace, selectedSuit);
				CardImagePanel cardPanel = getCardPanel();
				cardPanel.setImage(cardChooserPanel.getImageMap().get(key), key);

                		SelectCardPanel.this.revalidate();
				handleReflow();
				SelectCardPanel.this.repaint();
			}
		});

		add(cardChooserPanel);
		add(button);
	}

	//Override these three methods to make the CardDragControl
	protected void initializeCardPanel() {
		cardPanel = new CardImagePanel(false);
	}
	protected void addCardPanel() {
		add(cardPanel);
	}
	protected void handleReflow() {
		revalidate();
	}

	public CardImagePanel getCardPanel() {
		return cardPanel;
	}
}
