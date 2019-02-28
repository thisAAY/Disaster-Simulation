package simulation;

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
		world = new Address[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				world[i][j] = new Address(i, j);
			}
		}
		executedDisasters = new ArrayList<>();
		citizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		plannedDisasters = new ArrayList<Disaster>();
		buildings = new ArrayList<ResidentialBuilding>();
		loadCitizens("citizens.csv");
		loadBuildings("buildings.csv");
		loadUnits("units.csv");
		loadDisasters("disasters.csv");
	}

	
	public ArrayList<String> readfile(String filePath) throws IOException {
		ArrayList<String> arr = new ArrayList<String>();
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			arr.add(currentLine);
		}
		br.close();
		return arr;
	}

	private void loadUnits(String filePath) throws IOException {
		String x = "";
		String y = "";
		int z = 0;
		int r = 0;
		ArrayList<String> b = readfile(filePath);

		for (int i = 0; i < b.size(); i++) {
			String Line = b.get(i);
			String[] q = Line.split(",");
			y = q[1];
			x = q[0];
			z = Integer.parseInt(q[2]);
			if (x.equals("EVC")) {
				r = Integer.parseInt(q[3]);
				emergencyUnits.add(new Evacuator(y, world[0][0], z, r));
			} else if (x.equals("AMB"))
				emergencyUnits.add(new Ambulance(y, world[0][0], z));
			else if (x.equals("DCU"))
				emergencyUnits.add(new DiseaseControlUnit(y, world[0][0], z));
			else if (x.equals("FTK"))
				emergencyUnits.add(new FireTruck(y, world[0][0], z));
			else if (x.equals("GCU"))
				emergencyUnits.add(new GasControlUnit(y, world[0][0], z));

		}
	}

	private void loadBuildings(String filePath) throws IOException {
		int x = 0;
		int y = 0;
		ArrayList<String> b = readfile(filePath);

		for (int i = 0; i < b.size(); i++) {
			String Line = b.get(i);
			String[] q = Line.split(",");
			x = Integer.parseInt(q[0]);
			y = Integer.parseInt(q[1]);
			ResidentialBuilding building = new ResidentialBuilding(world[x][y]);
			building.getOccupants().addAll(findCitizens(x, y));
			buildings.add(building);
		}

	}

	private ArrayList<Citizen> findCitizens(String x, String y) {
		return findCitizens(Integer.parseInt(x), Integer.parseInt(y));
	}

	private ArrayList<Citizen> findCitizens(int x, int y) {
		ArrayList<Citizen> cs = new ArrayList<>();
		for (Citizen citizen : citizens) {
			if (citizen.getLocation().getX() == x && citizen.getLocation().getY() == y)
				cs.add(citizen);
		}

		return cs;
	}

	private void loadCitizens(String filePath) throws IOException {
		int x = 0;
		int y = 0;
		String id = "";
		String name = "";
		int age = 0;
		ArrayList<String> b = readfile(filePath);
		for (int i = 0; i < b.size(); i++) {
			String Line = b.get(i);
			String[] q = Line.split(",");
			x = Integer.parseInt(q[0]);
			y = Integer.parseInt(q[1]);
			id = q[2];
			name = q[3];
			age = Integer.parseInt(q[4]);
			citizens.add(new Citizen(world[x][y], id, name, age));
		}
	}

	public Citizen targetCitzien(String NationalID) {
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getNationalID().equals(NationalID))
				return citizens.get(i);
		}
		return null;

	}

	public ResidentialBuilding targetBuilding(int x, int y) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation().getX() == x && buildings.get(i).getLocation().getY() == y)
				return buildings.get(i);
		}
		return null;
	}

	private void loadDisasters(String filePath) throws IOException {
		int x = 0;
		String y = "";
		String target_id = "";

		ArrayList<String> b = readfile(filePath);

		for (int i = 0; i < b.size(); i++) {
			String Line = b.get(i);
			String[] q = Line.split(",");
			x = Integer.parseInt(q[0]);// start cycle
			y = q[1];// Disaster type
			target_id = q[2];
			int w = 0;
			if (y.equals("INJ"))
				plannedDisasters.add(new Injury(x, targetCitzien(target_id)));
			else if (y.equals("INF"))
				plannedDisasters.add(new Infection(x, targetCitzien(target_id)));
			else if (y.equals("FIR")) {
				w = Integer.parseInt(q[3]);
				plannedDisasters.add(new Fire(x, targetBuilding(Integer.parseInt(target_id), w)));
			}
			else  {
				w = Integer.parseInt(q[3]);
				plannedDisasters.add(new GasLeak(x, targetBuilding(Integer.parseInt(target_id), w)));
			}

		}
	}
}
