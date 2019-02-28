package model.units;

import simulation.Address;
import java.util.ArrayList;

import model.people.Citizen;
public abstract class PoliceUnit extends Unit {
	private int maxCapacity;
	private int distanceToBase;
	private ArrayList<Citizen> passengers;
	public PoliceUnit(String unitID, Address location, int stepsPerCycle,int maxCapacity) {
		super(unitID, location, stepsPerCycle);
		this.maxCapacity=(maxCapacity);
		passengers =  new ArrayList<>();
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public int getDistanceToBase() {
		return distanceToBase;
	}
	public void setDistanceToBase(int distanceToBase) {
		this.distanceToBase = distanceToBase;
	}
	


	
}
