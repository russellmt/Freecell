import java.awt.image.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.*;
import javax.swing.*;

public class CardLoader extends Thread {

	public static String directoryPath = "res/";

	private CardChooserPanel cardChooserPanel;
	private Map<FaceAndSuit, BufferedImage> imageMap;

	public CardLoader(Map<FaceAndSuit, BufferedImage> imageMap) {
		this.imageMap = imageMap;
	}

	public CardLoader(Map<FaceAndSuit, BufferedImage> imageMap, CardChooserPanel cardChooserPanel) {
		this.imageMap = imageMap;
		this.cardChooserPanel = cardChooserPanel;
	}

	@Override
	public void run() {
		FaceAndSuit key;
		BufferedImage image;

		String[] fileNames = getResourceList();
		for (String fileName : fileNames) {
			if (fileName.startsWith(".") || fileName.startsWith(directoryPath + ".")) continue;

            String prefix = "/";
            if (!fileName.startsWith(directoryPath)) {
                prefix = "res/";
            }
            image = constructImage(prefix + fileName);
			key = FaceAndSuit.parseFaceAndSuit(fileName);
			imageMap.put(key, image);
		}

		if (cardChooserPanel != null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Face[] faces = Face.values();
					Suit[] suits = Suit.values();

					List<CardChooserControl> itemList = cardChooserPanel.getCardChooserControls();
					for (CardChooserControl item : itemList) {
						item.getFaceCombo().setModel(new DefaultComboBoxModel<Face>(faces));
						item.getSuitCombo().setModel(new DefaultComboBoxModel<Suit>(suits));
					}
					cardChooserPanel.repaint();
				}
			});
		}
	}

    //this method grabs a list of all the image resources depending on whether it is run as a class or as a jar file
    public static String[] getResourceList() {
        CodeSource src = CardLoader.class.getProtectionDomain().getCodeSource();
        URL location = src.getLocation();
        String[] fileNames = null;

        //test to determine if jar is running
        String className = CardLoader.class.getName();
        String classJar = CardLoader.class.getResource("/" + className + ".class").toString();

        if (classJar.startsWith("jar:")) {
            List<String> nameList = new ArrayList<>();
            String entryName;

            try {
                ZipInputStream zip = new ZipInputStream(location.openStream());
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    entryName = entry.getName();
                    if (entryName.startsWith(directoryPath) && !entryName.equals(directoryPath)) {
                        nameList.add(entryName);
                    }
                }
                fileNames = nameList.toArray(new String[] {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File directory = new File(directoryPath);
            fileNames = directory.list();
        }
        return fileNames;
    }

	public static BufferedImage constructImage(String fileName) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(CardLoader.class.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static BufferedImage constructImage(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
