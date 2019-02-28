package model.units;

import simulation.Address;

public class MedicalUnit extends Unit {
	private int healingAmount;
	private int treatmentAmount;
	public MedicalUnit(String id, Address location, int stepsPerCycle) {
		super(id, location, stepsPerCycle);
		healingAmount = 10;
		treatmentAmount = 10;
	}
}
