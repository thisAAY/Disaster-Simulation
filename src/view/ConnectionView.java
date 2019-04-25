package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.GUIListener;

public abstract class ConnectionView extends JFrame implements ActionListener {
	private JPanel messagesPanel; 
	private JTextArea msgField;
	private boolean isFromClient;
	private GUIListener guiListener; 
	public ConnectionView(GUIListener guiListener,String titile,boolean isFromClient)
	{
		this.isFromClient =isFromClient;
		this.guiListener = guiListener;
		setTitle(String.format("Connected to %s", titile));
		setSize(300,600);
		setResizable(false);
		setLayout(new BorderLayout());
		JPanel sendingPanel = new JPanel();
		sendingPanel.setLayout(new FlowLayout());
		msgField = new JTextArea();
		sendingPanel.add(msgField);
		JButton nextBtn =  new JButton("Next");
		msgField.setPreferredSize(new Dimension(200,nextBtn.getPreferredSize().height));
		nextBtn.addActionListener(this);
		sendingPanel.add(nextBtn);
		add(sendingPanel,BorderLayout.SOUTH);
		messagesPanel = new JPanel();
		messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
		add(messagesPanel);
		setVisible(true);
	}
	public void addMessage(String message)
	{
		JLabel msgLbl  = new JLabel();
		msgLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		messagesPanel.add(new JLabel(message));
		revalidate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		guiListener.onSendMessageClicked(msgField.getText(), isFromClient);
		msgField.setText("");
	}
	
}
