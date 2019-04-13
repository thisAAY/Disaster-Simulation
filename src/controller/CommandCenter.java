package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit.InsertContentAction;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
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
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulator;
import view.ESCButtonListener;
import view.GUIHelper;
import view.MainScreen;

public class CommandCenter implements SOSListener, GUIListener,LogListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<Unit> emergencyUnits;
	private Unit selectedUnit;

	private MainScreen mainScreen;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);
		engine.setLogListener(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		mainScreen = new MainScreen(this);

		buildCity();
		buildUnits();

	}

	public void buildUnits() {
		mainScreen.getAvailableUnitsPanel().updateUnits(createUnits(UnitState.IDLE));
		mainScreen.getRespondingUnitsPanel().updateUnits(createUnits(UnitState.RESPONDING));
		mainScreen.getTreatingUnitsPanel().updateUnits(createUnits(UnitState.TREATING));
	}

	public void buildCity() {
		mainScreen.getCityPanel().updateCells(createCells());
	}

	public ArrayList<JButton> createUnits(UnitState state) {
		ArrayList<JButton> btns = new ArrayList<JButton>();
		for (Unit u : emergencyUnits) {
			if (u.getState() == state) {
				JButton btn = GUIHelper.makeScalledImageButton(getImagePath(u), new Dimension(48, 48));
				btn.setToolTipText("<html>" + u.toString().replaceAll("\n", "<br>") + "</html>");
				btn.putClientProperty("unit", u);
				btns.add(btn);
			}
		}
		return btns;
	}

	private String getImagePath(Unit u) {
		if (u instanceof Ambulance)
			return "src\\ambulance.png";
		if (u instanceof DiseaseControlUnit)
			return "src\\diseaseUnit.png";
		if (u instanceof Evacuator)
			return "src\\police-van.png";
		if (u instanceof FireTruck)
			return "src\\fire-truck.png";

		return "src\\train.png";
	}

	public JButton[][] createCells() {
		JButton[][] btns = new JButton[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				JButton button = new JButton();
				button.addKeyListener(ESCButtonListener.getInstance());
				button.setBorder(BorderFactory.createLineBorder(GUIHelper.SIMI_BLACK, 2));
				button.setBackground(Color.white);
				button.putClientProperty("location", new Address(i, j));
				btns[i][j] = button;
			}
		for (ResidentialBuilding building : visibleBuildings) {
			JButton button = btns[building.getLocation().getX()][building.getLocation().getY()];
			button.setToolTipText("<html>" + building.toString().replaceAll("\n", "<br>") + "</html>");
			button.putClientProperty("target", building);
			setColor(button, building);
		}
		for (Citizen citizen : visibleCitizens) {
			JButton button = btns[citizen.getLocation().getX()][citizen.getLocation().getY()];
			button.setToolTipText("<html>" + citizen.toString().replaceAll("\n", "<br>") + "</html>");
			setColor(button, citizen);
			button.putClientProperty("target", citizen);
		}
//		
		for (Unit unit : emergencyUnits) {
			if (unit instanceof Evacuator)
				continue;
			btns[unit.getLocation().getX()][unit.getLocation().getY()] = createUnitCell(btns, unit);
		}

		// Just to make sure that the evacuater will be shown if it was going from the
		// building to base
		for (Unit unit : emergencyUnits) {
			if (!(unit instanceof Evacuator))
				continue;
			btns[unit.getLocation().getX()][unit.getLocation().getY()] = createUnitCell(btns, unit);
		}
		return btns;
	}

	private JButton createUnitCell(JButton[][] btns, Unit unit) {
		JButton button = btns[unit.getLocation().getX()][unit.getLocation().getY()];
		// JButton unitBtn = GUIHelper.makeScalledImageButton(getImagePath(unit),new
		// Dimension(32, 32));
		try {
			BufferedImage buttonIcon;
			buttonIcon = ImageIO.read(new File(getImagePath(unit)));
			Image img = buttonIcon.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			button.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return button;
	}

	private void setColor(JButton btn, Rescuable rescuable) {
		boolean isCitizen = rescuable instanceof Citizen;
		Color color;
		String iconPath = null;
		if (rescuable.getDisaster() == null || !rescuable.getDisaster().isActive()) {
			if (isCitizen)
				color = GUIHelper.CITIZEN_COLOR;
			else
				color = GUIHelper.BUILDING_COLOR;
		} else {
			Disaster disaster = rescuable.getDisaster();
			if (disaster instanceof Collapse) {
				iconPath = "src\\coll.png";
				color = GUIHelper.COLLAPSE_BUILDING;
			} else if (disaster instanceof Fire) {
				color = GUIHelper.FIRE_BUILDING;
				iconPath = "src\\fire.png";
			} else if (disaster instanceof GasLeak) {
				iconPath = "src\\gasleak.png";
				color = GUIHelper.GAS_LEAK_BUILDING;
			} else if (disaster instanceof Injury) {
				iconPath = "src\\bloodloss.png";
				color = GUIHelper.FIRE_BUILDING;
			} else {
				iconPath = "src\\infection.png";
				color = GUIHelper.GAS_LEAK_BUILDING;
			}
		}
		btn.setBackground(color);
		if (iconPath != null) {
			try {
				btn.setIcon(new ImageIcon(ImageIO.read(new File(iconPath))));
			} catch (IOException e) {
				System.out.println("Cant read cell icon");
				e.printStackTrace();
			}
		}
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
		if (engine.checkGameOver()) {
			JOptionPane.showMessageDialog(null, "Game Over");
			return;
		}
		try {
			engine.nextCycle();
			mainScreen.getCityPanel().updateCells(createCells());
			buildUnits();

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
		mainScreen.getInfoPanel().updateData(u.toString());
	}

	@Override
	public void onUnSelectUnit() {
		selectedUnit = null;
	}

	public void printCell(int x, int y) {
		String citizens = "";
		String buildings = "";
		String units = "";

		for (ResidentialBuilding building : visibleBuildings)
			if (building.getLocation().getX() == x && building.getLocation().getY() == y)
				buildings += building.toString() + "\n" + building.getOccupants().toString();
		if(buildings != "")
			buildings = "Buildings:\n" + buildings;
		
		for (Citizen citizen : visibleCitizens)
			if (citizen.getLocation().getX() == x && citizen.getLocation().getY() == y)
				citizens += citizen.toString() + "\n";
		if(citizens != "")
			citizens = "Citizens:\n" + citizens;
		
		
		for (Unit unit : emergencyUnits)
			if (unit.getLocation().getX() == x && unit.getLocation().getY() == y)
				units += unit.toString() + "\n";
		if(units != "")
			units = "Units:\n" + units;
		
		String lines = buildings + citizens + units;
		if(lines != "")
			mainScreen.getInfoPanel().updateData(lines);
	}
	
	@Override
	public void onCellSelected(JButton btn) {
		Object obj = btn.getClientProperty("target");
		if (obj != null) {
			Rescuable target = (Rescuable) obj;
			printCell(target.getLocation().getX(), target.getLocation().getY());
			if (selectedUnit != null) {
				try {
					selectedUnit.respond(target);
					selectedUnit = null;
					buildUnits();
				} catch (CannotTreatException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Can't treat", JOptionPane.ERROR_MESSAGE);
				} catch (IncompatibleTargetException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Incompatabile target",
							JOptionPane.ERROR_MESSAGE);
				}
				;
			}
		}
	}

	@Override
	public void onCitizenDie(Citizen citizen) {

		mainScreen.getLogPanel().updateData(String.format("Citizen: %s has died", citizen.getName()));
	}

	@Override
	public void onDiasterStrick(Disaster disaster) {
		mainScreen.getLogPanel().updateData(String.format("A new %s diaster has striked a %s", disaster.getClass().getSimpleName(),disaster.getTarget().getClass().getSimpleName()));
	}

}
