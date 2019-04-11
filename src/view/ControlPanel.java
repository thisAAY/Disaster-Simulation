package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GUIListener;

public class ControlPanel extends JPanel implements ActionListener {
	private JLabel currentCycleView,causaltiesView;
	private JButton nextCycleBtn;
	private GUIListener listener;
	public ControlPanel (GUIListener listener)
	{
		this.listener = listener;
		setLayout(new GridLayout(1,3));
		addCurrentCycleView();
		addCauslatiesView();
		addNextCycleButton();
		setBackground(GUIHelper.SIMI_BLACK);
		setMaximumSize(new Dimension(500, nextCycleBtn.getPreferredSize().height));
		revalidate();
	}
	private void addCurrentCycleView()
	{
		currentCycleView = new JLabel("Current Cycle: 00");
		currentCycleView.setForeground(Color.WHITE);
		currentCycleView.addKeyListener(ESCButtonListener.getInstance());
		add(currentCycleView);
	}
	private void addCauslatiesView()
	{
		causaltiesView = new JLabel("Number of Casualties: 00");
		causaltiesView.setForeground(Color.WHITE);
		causaltiesView.addKeyListener(ESCButtonListener.getInstance());
		add(causaltiesView);
	}
	private void addNextCycleButton()
	{
		nextCycleBtn =  new JButton("Next Cycle");
		nextCycleBtn.setBackground(GUIHelper.SIMI_BLACK);
		nextCycleBtn.setForeground(Color.WHITE);
		nextCycleBtn.addKeyListener(ESCButtonListener.getInstance());
		nextCycleBtn.addActionListener(this);
		
		add(nextCycleBtn);
	}
	public void updateCurrentCycle(int currentCycle)
	{
		currentCycleView.setText(String.format("Current Cycle: %02d", currentCycle));
	}
	public void updateNumberOfCausalties(int causalties)
	{
		causaltiesView.setText(String.format("Number of Casualties: %02d", causalties));
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		listener.nextCycle();
	}
}
