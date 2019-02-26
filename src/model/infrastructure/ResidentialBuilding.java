package model.infrastructure;

import java.util.ArrayList;

import model.disasters.Disaster;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Rescuable, Simulatable {
	private Address location;
	private int structuralIntegrity = 100;
	private int fireDamage = 0;
	private int gasLevel = 0;
	private int foundationDamage = 0;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;

	public ResidentialBuilding(Address location) {
		this.location = location;
		occupants =  new ArrayList<Citizen>();
	}

	public Address getLocation() {
		return location;
	}

	public Disaster getDisaster() {
		return disaster;
	}

	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}

	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}

	public void setStructuralIntegrity(int s) {
		structuralIntegrity = s;
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
	}

	public int getGasLevel() {
		return gasLevel;
	}

	public void setGasLevel(int gasDamage) {
		this.gasLevel = gasDamage;
	}

	public int getFoundationDamage() {
		return foundationDamage;
	}

	public void setFoundationDamage(int foundationDamage) {
		this.foundationDamage = foundationDamage;
	}
}
