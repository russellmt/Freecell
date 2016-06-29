import java.awt.Dimension;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;

public class CardChooserPanel extends JPanel {
	private List<CardChooserControl> cardChooserControls;
	private Map<FaceAndSuit, BufferedImage> imageMap;
	private boolean bordered;

	public CardChooserPanel (int rows) {
		this(rows, false);
	}

	public CardChooserPanel (int rows, boolean bordered) {
		cardChooserControls = new ArrayList<>();
		imageMap = new HashMap<>();
		build(rows);
	}

	public void build(int rows) {
		if (bordered) {
			setBorder(BorderFactory.createEtchedBorder());
		}
		for (int i = 0; i < rows; i++) {
			CardChooserControl item = new CardChooserControl();
			cardChooserControls.add(item);
			add(item);
		}

		final Map<FaceAndSuit, BufferedImage> map = imageMap;
		new CardLoader(map, this).start();
		revalidate();
	}

	@Override
	public Dimension getPreferredSize() {
		int length = cardChooserControls.size() + 1;
		Dimension size = cardChooserControls.get(0).getPreferredSize();
		return new Dimension(size.width, size.height * length);
	}

	public void update(List<FaceAndSuit> settingList) {
		for (int i = 0; i < settingList.size(); i++) {
			cardChooserControls.get(i).update(settingList.get(i));
		}
	}

	public void reset() {
		for (CardChooserControl item : cardChooserControls) {
			item.reset();
		}
	}

	public List<FaceAndSuit> getSettings() {
		List<FaceAndSuit> settings = new ArrayList<>();
		for (CardChooserControl item : cardChooserControls) {
			settings.add(item.getSetting());
		}
		return settings;
	}

	public List<CardChooserControl> getCardChooserControls() {
		return cardChooserControls;
	}

	public CardChooserControl getCardSelectionItem(int index) {
		return cardChooserControls.get(index);
	}

	public Map<FaceAndSuit, BufferedImage> getImageMap() {
		return imageMap;
	}

	public boolean isBordered() {
		return bordered;
	}

}