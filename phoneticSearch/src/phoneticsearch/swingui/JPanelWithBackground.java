package phoneticsearch.swingui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Image backgroundImage;

	public JPanelWithBackground(final Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
