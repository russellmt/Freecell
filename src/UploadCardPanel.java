import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class UploadCardPanel extends JPanel {
	
	public UploadCardPanel() {
		buildComponents();
	}

	private void buildComponents() {
		setBorder(BorderFactory.createEtchedBorder());

		JFileChooser fc = constructFileChooser();
		CardImagePanel cardPanel = new CardImagePanel(false);

		JButton selectImageButton = new JButton("Select Image");
		selectImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setVisible(true);
				int response = fc.showOpenDialog(UploadCardPanel.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					cardPanel.setImage(CardLoader.constructImage(fc.getSelectedFile()));
					UploadCardPanel.this.revalidate();
					cardPanel.repaint();
				}
			}
		});
		add(selectImageButton);
		add(cardPanel);

	}

	private JFileChooser constructFileChooser() {
		JFileChooser fc = new JFileChooser(new File("../res"));
		fc.setVisible(false);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				String name = file.getName();
				int dotIndex = name.indexOf('.');
				
				if (dotIndex == -1) return false;
				String extension = name.substring(dotIndex, name.length());
				switch(extension.toLowerCase()) {
					case ".png":
					case ".jpeg":
					case ".gif":
						return true;
				}
				return false;
			}
			public String getDescription() {
				return "JPG, GIF and PNG Images";
			}
		});
		return fc;
	}
}