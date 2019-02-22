package model.units;

import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable {
	private String unitID;
	private UnitState state=UnitState.IDLE;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	public Unit(String id, Address location, int stepsPerCycle){
		unitID=id;
		this.location=location;
		this.stepsPerCycle=stepsPerCycle;
	}
	public String getUnitID(){
		return unitID;}
	public void setState(UnitState state){
		this.state=state;
	}
	public UnitState getState(){
		return state;
	}
	public Address getLocation(){
		return location;
	}
	public void setLocation(Address l){
		location=l;
	}
	public Rescuable getTarget(){
		return target;
	}

	public int getStepsPerCycle(){
		return stepsPerCycle;
	}

	
	
	
	
}
