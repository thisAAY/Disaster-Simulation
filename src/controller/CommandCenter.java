package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.ESCButtonListener;
import view.GUIHelper;
import view.MainScreen;

public class CommandCenter implements SOSListener,GUIListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	private Unit selectedUnit;
	
	private MainScreen mainScreen;
	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		mainScreen = new MainScreen(this);
		
		buildCity();
		buildAvailableUnits();

	}
	public void buildAvailableUnits()
	{
		mainScreen.getAvailableUnitsPanel().updateUnits(createUnits());
	}
	public void buildCity()
	{
		mainScreen.getCityPanel().updateCells(createCells());
	}
	public ArrayList<JButton> createUnits()
	{
		ArrayList<JButton> btns = new ArrayList<JButton>();
		for(Unit u : emergencyUnits)
		{
			if(u.getState() == UnitState.IDLE)
			{
				JButton btn =  GUIHelper.makeImageButton(getImagePath(u));
				btn.putClientProperty("unit", u);
				btns.add(btn);
			}
		}
		return btns;
	}
	private String getImagePath(Unit u)
	{
		if(u instanceof Ambulance)
			return "src\\ambulance.png";
		if(u instanceof DiseaseControlUnit)
			return "src\\diseaseUnit.png";
		if(u instanceof Evacuator)
			return "src\\police-van.png";
		if(u instanceof FireTruck)
			return "src\\fire-truck.png";
		
			return "src\\train.png";
	}
	public JButton[] [] createCells()
	{
		JButton[] [] btns = new JButton [10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = new JButton();
				button.addKeyListener(ESCButtonListener.getInstance());
				button.setBorder(BorderFactory.createLineBorder(GUIHelper.SIMI_BLACK, 2));
				button.setBackground(Color.white);
				btns[i][j] = button;
			}
		for(Citizen citizen : visibleCitizens)
		{
			JButton button = btns[citizen.getLocation().getX()] [citizen.getLocation().getY()];
			button.setToolTipText("<html>" + citizen.toString().replaceAll("\n", "<br>") + "</html>");
			button.setBackground(GUIHelper.CITIZEN_COLOR);
			button.putClientProperty("target", citizen);
		}
		for(ResidentialBuilding building : visibleBuildings)
		{
			JButton button = btns[building.getLocation().getX()] [building.getLocation().getY()];
			button.setToolTipText("<html>" + building.toString().replaceAll("\n", "<br>") + "</html>");
			button.putClientProperty("target", building);
			button.setBackground(GUIHelper.BUILDING_COLOR);
		}
//		
		for(Unit unit : emergencyUnits)
		{
			JButton button = btns[unit.getLocation().getX()] [unit.getLocation().getY()];
			JButton unitBtn = GUIHelper.makeScalledImageButton(getImagePath(unit),new Dimension(32, 32));
			unitBtn.setBackground(button.getBackground());
			unitBtn.setMaximumSize(button.getSize());
			btns[unit.getLocation().getX()] [unit.getLocation().getY()] = unitBtn;
		}
		return btns;
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
	@Override
	public void nextCycle() {
		if(engine.checkGameOver())
		{
			JOptionPane.showMessageDialog(null, "Game Over");
			return;
		}
		try {
			engine.nextCycle();
			mainScreen.getCityPanel().updateCells(createCells());
			buildAvailableUnits();

			mainScreen.getControlPanel().updateCurrentCycle(engine.getCurrentCycle());
			mainScreen.getControlPanel().updateNumberOfCausalties(engine.calculateCasualties());

		} catch (CannotTreatException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (BuildingAlreadyCollapsedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		} catch (CitizenAlreadyDeadException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	@Override
	public void onUnitSelected(JButton btn) {
		Unit u = (Unit) btn.getClientProperty("unit");
		selectedUnit = u;
	}
	@Override
	public void onUnSelectUnit() {
		selectedUnit = null;
	}
	@Override
	public void onCellSelected(JButton btn) {
		Object obj =  btn.getClientProperty("target");
		if(obj != null)
		{
			Rescuable target = (Rescuable) obj;
			if(selectedUnit != null)
			{
				try {
					selectedUnit.respond(target);
					selectedUnit = null;
					buildAvailableUnits();
				} catch (CannotTreatException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Can't treat", JOptionPane.ERROR_MESSAGE);
				} catch (IncompatibleTargetException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Incompatabile target", JOptionPane.ERROR_MESSAGE);
				};
			}
		}
	}

}
