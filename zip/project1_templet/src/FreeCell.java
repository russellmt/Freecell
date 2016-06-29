import javax.swing.*;

public class FreeCell {
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PlayingCardManager.buildPanel("5");
			}
		});
	}
}