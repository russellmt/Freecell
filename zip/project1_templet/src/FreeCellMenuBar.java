import java.awt.event.*;
import javax.swing.*;

public class FreeCellMenuBar extends JMenuBar {

	private JFrame frameParent;
	private JPanel paneParent;

	private CascadePanel cascadePanel;
	
	public FreeCellMenuBar(JFrame frameParent, JPanel paneParent) {
		this.frameParent = frameParent;
		this.paneParent = paneParent;
		build();
	}

	private void build() {
		JMenu gameMenu = new JMenu("Game");
		JMenuItem newItem = new JMenuItem("New");

		newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		gameMenu.add(newItem);
		add(gameMenu);
	}

	public CascadePanel getCascadePanel() {
		return cascadePanel;
	}

	public void newGame() {
		((FreeCellPanel) paneParent).resetGame();
		((FreeCellPanel) paneParent).deal();
	}
}