package simulation;

import java.awt.image.RescaleOp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;

public class Simulator {
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;

	public Simulator() throws IOException {
		makeWorld();
		executedDisasters = new ArrayList<>();
		citizens =  new ArrayList<Citizen>();
		emergencyUnits =  new ArrayList<Unit>();
		plannedDisasters =new ArrayList<Disaster>();
		buildings =  new ArrayList<ResidentialBuilding>();
		loadCitizens("citizens.csv");
		loadBuildings("buildings.csv");
		loadUnits("units.csv");
		loadDisasters("disasters.csv");
	}

	private void makeWorld() {
		world = new Address[10][10];
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				world[i][j] = new Address(i, j);
			}
		}
	}

	private void loadUnits(String filePath) throws IOException {
		ArrayList<String> units = readFile(filePath);

		for (String unitLine : units) {
			String[] data = unitLine.split(",");
			String type = data[0];
			String unitID = data[1];
			int stepsPerCycle = Integer.parseInt(data[2]);
			Address location = world[0][0];
			Unit unit = null;
			switch (type) {
			case "AMB":
				unit = new Ambulance(unitID, location, stepsPerCycle);
				break;
			case "DCU":
				unit = new DiseaseControlUnit(unitID, location, stepsPerCycle);
				break;
			case "FTK":
				unit = new FireTruck(unitID, location, stepsPerCycle);
				break;
			case "GCU":
				unit = new GasControlUnit(unitID, location, stepsPerCycle);
				break;
			default: // EVC
				int cap = Integer.parseInt(data[3]);
				unit = new Evacuator(unitID, location, stepsPerCycle, cap);
				break;
			}
			emergencyUnits.add(unit);
		}
	}

	private void loadBuildings(String filePath) throws IOException {
		ArrayList<String> bs = readFile(filePath);
		for (String buildingLine : bs) {
			String[] data = buildingLine.split(",");
			int x = Integer.parseInt(data[0]);
			int y = Integer.parseInt(data[1]);
			ArrayList<Citizen> citizens = findCitizens(x,y);
			ResidentialBuilding building =  new ResidentialBuilding(world[x][y]);
			building.getOccupants().addAll(citizens);
			buildings.add(building);
		}
	}

	private void loadCitizens(String filePath) throws IOException {
		ArrayList<String> cs = readFile(filePath);
		for (String citizenLine : cs) {
			String[] data = citizenLine.split(",");
			int x = Integer.parseInt(data[0]);
			int y = Integer.parseInt(data[1]);
			Address location = world[x][y];
			String id = data[2];
			String name = data[3];
			int age = Integer.parseInt(data[4]);
			Citizen citizen = new Citizen(location, id, name, age);
			citizens.add(citizen);
		}
	}

	private void loadDisasters(String filePath) throws IOException {
		ArrayList<String> ds = readFile(filePath);
		for (String disasterLine : ds) {
			String[] data = disasterLine.split(",");
			int startCycle = Integer.parseInt(data[0]);
			String type = data[1];
			String id = data[2];
			Disaster disaster = null;
			switch (type) {
			case "INJ":
				disaster = new Injury(startCycle, findCitizen(id));
				break;
			case "INF":
				disaster = new Infection(startCycle, findCitizen(id));
				break;
			case "FIR":
				disaster = new Fire(startCycle, findBuilding(data[2], data[3]));
				break;
			default: // "GLK"
				disaster = new GasLeak(startCycle, findBuilding(data[2], data[3]));
				break;
			}
			plannedDisasters.add(disaster);

		}
	}

	private ResidentialBuilding findBuilding(String x, String y) {
		return findBuilding(Integer.parseInt(x), Integer.parseInt(y));
	}

	private ResidentialBuilding findBuilding(int x, int y) {
		for (ResidentialBuilding building : buildings) {
			if (building.getLocation().getX() == x && building.getLocation().getY() == y)
				return building;
		}
		return null;
	}

	private Citizen findCitizen(String id) {
		for (Citizen citizen : citizens) {
			if (citizen.getNationalID().equals(id))
				return citizen;
		}
		return null;
	}
	private ArrayList<Citizen> findCitizens(int x, int y) {
		ArrayList<Citizen> citizens =  new ArrayList<Citizen>();
		for (Citizen citizen : this.citizens) {
			if (citizen.getLocation().getX() == x && citizen.getLocation().getY() == y)
				citizens.add(citizen);
		}
		return citizens;
	}
	private ArrayList<String> readFile(String path) throws IOException {
		FileReader fileReader = new FileReader(path);
		BufferedReader reader = new BufferedReader(fileReader);
		String line = null;
		ArrayList<String> lines = new ArrayList<>();
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		reader.close();
		return lines;
	}
}
