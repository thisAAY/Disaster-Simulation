package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.CommandCenter;

public class CityPanel extends JPanel {
	private CommandCenter controller;

	public CityPanel(CommandCenter controller) {
		this.controller = controller;
		setLayout(new GridLayout(10, 10));
		setBackground(GUIHelper.SIMI_BLACK);
	}

	public void updateCells(JButton[][] cells) {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = cells[i][j];
				button.setBorder(BorderFactory.createEtchedBorder());
				button.setBackground(GUIHelper.SIMI_WHITE);
				add(button);
			}
		validate();
	}
}
