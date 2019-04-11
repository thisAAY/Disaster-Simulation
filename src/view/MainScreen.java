
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.CommandCenter;

public class MainScreen extends JFrame{
	
	
	private CommandCenter controller;
	private JPanel contentPanel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JButton exitButton;
	private CityPanel cityPanel;
	public MainScreen (CommandCenter controller)
	{
		this.controller = controller;
		setTitle("Disasters");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(GUIHelper.SIMI_BLACK);
		setLayout(null);
		addScreenButtons();
		makeFullScreen();
		addCity();
		setVisible(true);
	}
	private void makeFullScreen()
	{
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		contentPanel = new JPanel();
		int h = screenSize.height - exitButton.getSize().height;
		contentPanel.setBounds(0,50,screenSize.width,h);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBackground(GUIHelper.SIMI_BLACK);
		validate();
		add(contentPanel);
	}
	private void addScreenButtons()
	{
	    exitButton =  GUIHelper.makeImageButton("src\\close-circle.png");
	    exitButton.addActionListener(new GUIHelper.ExitListener());
	    exitButton.setBounds((int)exitButton.getSize().getWidth(),(int) exitButton.getSize().getHeight(),2650, 50);
	    exitButton.setFocusable(false);
	    add(exitButton);
		validate();
	}
	private void addCity()
	{
		cityPanel =  new CityPanel(controller);
		contentPanel.add(cityPanel);
	}
	public CityPanel getCityPanel() {
		return cityPanel;
	}
	
}
