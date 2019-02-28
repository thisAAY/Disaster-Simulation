package model.disasters;

import simulation.Rescuable;
import simulation.Simulatable;

abstract public class Disaster implements Rescuable,Simulatable {
private int startCycle;
private Rescuable target;
private boolean active;
public Disaster(int startCycle,Rescuable target) {
	this.startCycle=(startCycle);
	this.target=(target);
	active =false;
}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean X) {
		active =X;
	}


	public int getStartCycle() {
		return startCycle;
	}


	public void setStartCycle(int startCycle) {
		this.startCycle = startCycle;
	}


	public Rescuable getTarget() {
		return target;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
