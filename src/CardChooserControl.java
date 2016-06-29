import java.awt.*;
import javax.swing.*; 

public class CardChooserControl extends JPanel {
	final static Face DEFAULT_FACE = Face.Ace;
	final static Suit DEFAULT_SUIT = Suit.Hearts;

	private JComboBox<Face> faceCombo;
	private JComboBox<Suit> suitCombo;

	public CardChooserControl() {
		build();
	}

	public void build() {
		faceCombo = new JComboBox<>();
		suitCombo = new JComboBox<>();
		JLabel ofLabel = new JLabel("of");

		add(faceCombo);
		add(ofLabel);
		add(suitCombo);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(250, 35);
	}

	public void update(FaceAndSuit setting) {
		faceCombo.setSelectedItem(setting.getFace());
		suitCombo.setSelectedItem(setting.getSuit());
	}

	public void reset() {
		faceCombo.setSelectedItem(DEFAULT_FACE);
		suitCombo.setSelectedItem(DEFAULT_SUIT);
	}

	public FaceAndSuit getSetting() {
		return new FaceAndSuit((Face) faceCombo.getSelectedItem(), (Suit) suitCombo.getSelectedItem());
	}

	public JComboBox<Face> getFaceCombo() {
		return faceCombo;
	}

	public JComboBox<Suit> getSuitCombo() {
		return suitCombo;
	}
}