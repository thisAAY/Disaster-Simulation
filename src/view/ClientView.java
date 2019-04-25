package view;

import controller.GUIListener;

public class ClientView  extends ConnectionView{
	public ClientView(GUIListener guiListener, String titile) {
		super(guiListener, titile,true);
	} 
}
