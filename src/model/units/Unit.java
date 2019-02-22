package model.units;

import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable {
	String unitID;
	UnitState state=UnitState.IDLE;
	Address location;
	Rescuable target;
	int distanceToTarget;
	int stepsPerCycle;
	public Unit(String id, Address location, int stepsPerCycle){
		unitID=id;
		this.location=location;this.stepsPerCycle=stepsPerCycle;
	}
	public String getUnitID(){
		return unitID;}
	public void setState(UnitState s){
		state=s;
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
/*	public int getDistanceToTarget(){
		return distanceToTarget;
	}*/
	public int getStepsPerCycle(){
		return stepsPerCycle;
	}

	
	
	
	
}
