import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class HandChooserDialog extends JDialog {

	private CardChooserPanel cardChooserPanel;
	private List<FaceAndSuit> acceptedConfig;
	
	public HandChooserDialog (JFrame owner) {
		super(owner, "Select Hand", true);
		build();
	}

	private void build() {
		setSize(getPreferredSize());
		setResizable(false);
		setLayout(new FlowLayout());
		cardChooserPanel = new CardChooserPanel(5);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptedConfig = cardChooserPanel.getSettings();
				HandChooserDialog.this.setVisible(false);
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HandChooserDialog.this.setVisible(false);
			}
		});

		add(cardChooserPanel);
		add(okButton);
		add(cancelButton);
		setVisible(false);
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (acceptedConfig != null) { 
			cardChooserPanel.update(acceptedConfig);
		} else {
			cardChooserPanel.reset();	
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(250, 300);
	}

	public CardChooserPanel getCardChooserPanel() {
		return cardChooserPanel;
	}

	public List<FaceAndSuit> getAcceptedConfig() {
		return acceptedConfig;
	}
}