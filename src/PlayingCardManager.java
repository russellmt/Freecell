import java.awt.*;
import javax.swing.*;

public class PlayingCardManager {
	
	public static final String RENDER_CARD = "1";
	public static final String SELECT_CARD = "2";
	public static final String MOVE_CARD = "3";
	public static final String SELECT_HAND = "4";
	public static final String FREE_CELL = "5";

	public static void main (String[] args) {
		String selection = getSelection(args);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildPanel(selection);
			}
		});
	}

	private static String getSelection(String[] args) {
		String selection = "";
		if (args.length > 0) {
			String input = args[0];
			switch(input) {
				case RENDER_CARD:
				case SELECT_CARD:
				case MOVE_CARD:
				case SELECT_HAND:
				case FREE_CELL:
					selection = input;
					break;
				default: selection = "4";
			}
		} else {
			selection = "4";
		}
		return selection;
	}

	public static void buildPanel(String selection) {
		JFrame frame = new JFrame();
		frame.setSize(1200,800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = null;
		switch (selection) {
			case RENDER_CARD:
				panel = new UploadCardPanel();
				break;
			case SELECT_CARD:
				panel = new SelectCardPanel();
				break;
			case MOVE_CARD:
				panel = new MoveCardPanel();
				break;
			case SELECT_HAND:
				panel = new DragZone(new Dimension(700,700));
				frame.setJMenuBar(new HandSelectMenuBar(frame, panel));
				break;
			case FREE_CELL:
				panel = new FreeCellPanel();
                Container glassPane = (Container) frame.getGlassPane();
                glassPane.setLayout(null);
                frame.setGlassPane(glassPane);
				frame.setJMenuBar(new FreeCellMenuBar(frame, panel));
                GameController.initialize((FreeCellPanel) panel, glassPane);
				break;
		}
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}