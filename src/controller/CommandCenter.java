package controller;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Unit;
import simulation.Rescuable;
import simulation.Simulator;
import view.GUIHelper;
import view.MainScreen;

public class CommandCenter implements SOSListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	
	
	private MainScreen mainScreen;
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		mainScreen = new MainScreen(this);
		buildCity();
	}
	public void buildCity()
	{
		JButton[] [] btns = new JButton [10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = new JButton();
				button.setBorder(BorderFactory.createEtchedBorder());
				button.setBackground(GUIHelper.SIMI_WHITE);
				btns[i][j] = button;
			}
		for(Citizen citizen : engine.getCitizens())
		{
			JButton button = btns[citizen.getLocation().getX()] [citizen.getLocation().getY()];
			button.setText(citizen.getName());
		}
		for(ResidentialBuilding building : engine.getBuildings())
		{
			JButton button = btns[building.getLocation().getX()] [building.getLocation().getY()];
			button.setText("Building " + building.getOccupants().size());
		}
		mainScreen.getCityPanel().updateCells(btns);
	}
	public static void main(String[] args) {
		try {
			CommandCenter commandCenter = new CommandCenter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void receiveSOSCall(Rescuable r) {
		
		if (r instanceof ResidentialBuilding) {
			
			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);
			
		} else {
			
			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

}
