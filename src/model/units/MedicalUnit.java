package model.units;

import simulation.Address;

public abstract class MedicalUnit extends Unit{
	int healingAmount=10;
	int treatmentAmount=10;
	public MedicalUnit(String id, Address location, int stepsPerCycle){
		super(id,location,stepsPerCycle);
	}
	

}
