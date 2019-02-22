package model.disasters;

import model.infrastructure.ResidentialBuilding;
import simulation.Rescuable;

public class Collapse  {
	private int cycle;
	private ResidentialBuilding target;
	public Collapse(int cycle, ResidentialBuilding target) {
		this.cycle = cycle;
		this.target = target;
	}
	public int getCycle()
	{
		return cycle;
	}
	public void setCycle(int cycle)
	{
		this.cycle = cycle;
	}
	public ResidentialBuilding getTarget()
	{
		return target;
	}
	public void setTarget(ResidentialBuilding target)
	{
		this.target = target;
	}

}
