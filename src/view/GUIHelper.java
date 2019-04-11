package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIHelper {
	public static Color SIMI_BLACK = new Color(51, 56, 64);
	public static Color SIMI_WHITE = new Color(173, 175, 179);
	public static Color BUILDING_COLOR = new Color (245,203,92);
	public static Color CITIZEN_COLOR = new Color (109,211,206);

	public static JButton makeImageButton(String path) {
		try {	
			BufferedImage buttonIcon = ImageIO.read(new File(path));
			JButton button = new JButton(new ImageIcon(buttonIcon));
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			return button;
		} catch (IOException e) {
			System.out.println("Can't read the image");
			e.printStackTrace();
			return null;
		}

	}
	public static class ExitListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
}
