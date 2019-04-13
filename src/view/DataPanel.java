package view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

abstract public class  DataPanel extends JTextArea {
	
	public DataPanel (String title,String des)
	{
		setBackground(GUIHelper.SIMI_BLACK);
		setEditable(false);
		setForeground(Color.WHITE);
		setSize(getSize());
		setText(des);
		setBorder(BorderFactory.createTitledBorder(null, title, 0, 0, getFont(), Color.WHITE));
	}
	protected void updateData(String data,boolean deleteLast)
	{
		if(deleteLast)
			setText(data);
		else
			if(!getText().contains(data))
				setText(getText() + "\n" + data);
	}
}
