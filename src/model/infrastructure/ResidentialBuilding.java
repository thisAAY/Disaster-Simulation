package model.infrastructure;

import java.util.ArrayList;

import model.disasters.Disaster;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Rescuable,Simulatable {
	Address location;
	int structuralIntegrity=100;
	int fireDamage=0;
	int gasLevel=0;
	int foundationDamage=0;
	ArrayList<Citizen> occupants;
	Disaster disaster;
	public ResidentialBuilding(Address location)
	{
		this.location=location;
	}
public Address getLocation(){
	return location;
}
public Disaster getDisaster (){
	return disaster;
}
public ArrayList<Citizen> getOccupants(){
	return occupants;
}
public int getStructuralIntegrity(){
	return structuralIntegrity;
}
public void setStructuralIntegrity(int s){
	structuralIntegrity=s;
}
public int getFireDamage(){
	return fireDamage;
}
public void setFireDamage(int f){
	fireDamage=f;
}
public int getGasDamage(){
	return gasLevel;
}
public void setGasDamage(int g){gasLevel=g;}public int getFoundationDamage(){
	return foundationDamage;
}
public void setFoundationDamage(int fd){
	foundationDamage=fd;
}
}
