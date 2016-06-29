import java.awt.*;
import javax.swing.*;

public class DragZone extends JPanel {
	
	private boolean bordered;
	private Dimension size;

	public DragZone (Dimension size) {
		this(size, false);
	}

	public DragZone (Dimension size, boolean bordered) {
		this.size = size;
		this.bordered = bordered;
		build();
	}

	protected void build() {
		if (bordered) {
			setBorder(BorderFactory.createEtchedBorder());
		}
		setLayout(null);
        setSize(getPreferredSize());
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	public boolean isBordered() {
		return bordered;
	}
}