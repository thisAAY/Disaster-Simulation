package model.units;

import simulation.Address;

public abstract class MedicalUnit extends Unit{
	private int healingAmount;
	private int treatmentAmount;;
	public MedicalUnit(String id, Address location, int stepsPerCycle){
		super(id,location,stepsPerCycle);
		healingAmount = 10;
		treatmentAmount = 10;
	}
	
	public int getHealingAmount()
	{
		return healingAmount;
	}
	public void setHealingAmount(int healingAmount)
	{
		this.healingAmount = healingAmount;
	}
	public int getTreatmentAmount()
	{
		return treatmentAmount;
	}
	public void setTreatmentAmount(int treatmentAmount)
	{
		this.treatmentAmount = treatmentAmount;
	}
}
