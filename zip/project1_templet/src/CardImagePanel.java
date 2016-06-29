import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class CardImagePanel extends JPanel {
	
	protected BufferedImage image;
	protected Dimension scaleSize;
	protected FaceAndSuit identity;

	//drag-related fields
	protected boolean draggable;
	protected int dragStartX, dragStartY;

	public CardImagePanel(boolean draggable) {
        this(draggable, null);
	}

	public CardImagePanel(boolean draggable, Dimension scaleSize) {
        this.draggable = draggable;
		this.scaleSize = scaleSize;
        build();
	}

	protected void build() {
		setBorder(BorderFactory.createEtchedBorder());
		if (draggable) {
			MouseAdapter adapter = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					handleMousePress(e);
				}
				public void mouseDragged(MouseEvent e) {
					if (beforeDrag(e)) {
						handleMouseDrag(e);
					}
				}
                public void mouseReleased(MouseEvent e) {
                    handleMouseRelease(e);
                }
			};
			addMouseListener(adapter);
			addMouseMotionListener(adapter);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) g.drawImage(image, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		if (scaleSize != null) {
			return scaleSize;
		} else if (image != null) {
			return new Dimension((int) image.getWidth(), (int) image.getHeight());
		}
		return new Dimension(20,20);
	}


	//Drag/drop logic

	protected void handleMousePress(MouseEvent e) {
		dragStartX = e.getX();
		dragStartY = e.getY();
		if (e.getButton() == MouseEvent.BUTTON3) {
			handleRightClickEffects();
		}
		handlePressOnAttachment(e);
	}

	//empty, but can be overridden to carry action over to attached panels
	protected void handlePressOnAttachment(MouseEvent e) {}

	protected boolean beforeDrag(MouseEvent e) {
		return true;
	}

	protected void handleMouseDrag(MouseEvent e) {
		int dragEndX = e.getX(), dragEndY = e.getY();
		int distX = dragEndX - dragStartX, distY = dragEndY - dragStartY;

		Point panelLocation = CardImagePanel.this.getLocation();
		Point panelEndLocation = new Point(panelLocation.x + distX, panelLocation.y + distY);
		CardImagePanel.this.setLocation(panelEndLocation);
		CardImagePanel.this.repaint();
		handleDragOnAttachment(e);
	}

	//empty, but can be overridden to carry action over to attached panels
	protected void handleDragOnAttachment(MouseEvent e) {}

    protected void handleMouseRelease(MouseEvent e) {}

	protected void handleRightClickEffects() {
		getParent().setComponentZOrder(this, 0);
		getParent().repaint();
	}

	public boolean isDraggable() {
		return draggable;
	}



	//Image logic

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		setImage(image, null);
	}

	//this version of setImage will also transfer the image's identity to the card panel
	public void setImage(BufferedImage image, FaceAndSuit key) {
		if (scaleSize != null) {	//sure is a lot of work to scale an image...
			this.image = getScaledImage(image);
		} else {
			this.image = image;
		}
		identity = key;
	}

	public BufferedImage getScaledImage(BufferedImage oldImage) {
		int width = (int) scaleSize.getWidth(), height = (int) scaleSize.getHeight();
		BufferedImage newImage = new BufferedImage((int) scaleSize.getWidth(), (int) scaleSize.getHeight(), oldImage.getType());

		Graphics2D g = newImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(oldImage, 0, 0, width, height, 0, 0, oldImage.getWidth(), oldImage.getHeight(), null);
		g.dispose();

		return newImage;
	}




	public Face getFace() {
		return identity.getFace();
	}

	public Suit getSuit() {
		return identity.getSuit();
	}

	public Color getColor() {
		if (getSuit() == Suit.Hearts || getSuit() == Suit.Diamonds) {
			return Color.RED;
		}
		return Color.BLACK;
	}
}