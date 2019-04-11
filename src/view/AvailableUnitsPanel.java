package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneLayout;

import model.units.Unit;

public class AvailableUnitsPanel extends JPanel {
	private JPanel content;
	private JScrollPane scrollPane;
	public AvailableUnitsPanel()
	{
		content =  new JPanel();
		content.setAutoscrolls(true);
		setBackground(GUIHelper.SIMI_BLACK);
		content.setBackground(GUIHelper.SIMI_BLACK);
		
		addKeyListener(ESCButtonListener.getInstance());
		content.setPreferredSize(new Dimension(480,300));
		scrollPane =  new JScrollPane(content,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(480,80));
		setMaximumSize(content.getPreferredSize());
		add(scrollPane);
		validate();
	}
	public void updateUnits(ArrayList<JButton> btns)
	{
		content.removeAll();
		for(JButton btn: btns)
		{
			btn.setFocusable(false);
			btn.addKeyListener(ESCButtonListener.getInstance());
			content.add(btn);
		}
		revalidate();
	}
	
}
