import java.awt.*;

public class MoveCardPanel extends SelectCardPanel {
	
	@Override
	protected void initializeCardPanel() {
		cardPanel = new CardImagePanel(true, new Dimension(200,300));
	}

	@Override
	protected void addCardPanel() {
		DragZone dragZone = new DragZone(new Dimension(700,600), true);
		dragZone.add(cardPanel);
		add(dragZone);
	}

	@Override
	protected void handleReflow() {
		cardPanel.setSize(cardPanel.getPreferredSize());
	}
}