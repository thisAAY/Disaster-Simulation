package model.people;

import model.disasters.Disaster;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Citizen  implements Rescuable,Simulatable {
	Address location;
	CitizenState state;
	Disaster disaster;
	String nationalID;
	String name;
	int age;
	int hp;
	int bloodLoss;
	int toxicity;
	
	public Citizen (Address location, String nationalID, String name, int age){
		this.age=age;this.location=location;this.nationalID=nationalID;this.name=name;
		this.state=CitizenState.SAFE;toxicity=0;hp=100;bloodLoss=0;
		}
	public String getName(){
		return name;
	}
	public Disaster getDisaster(){
		return disaster;
	}
	public String getNationalID(){
		return nationalID;
		
	}
	public int getAge(){
		return age;
	}
	
	public void setState(CitizenState s){
		state=s;
	}
	public CitizenState getState(){
		return state;
	}
	public Address getLocation(){
		return location;
	}
	public void setLocation(Address l){
		location = l;
	}
	public int getHp(){
		return hp;
	}
	public void setHp(int h){
		hp=h;
	}
	public int getToxicity(){
		return toxicity;
	}
	public void setToxicity(int t)
	{
		toxicity=t;
	}
	public int getBloodLoss(){
		return bloodLoss;
	}
	public void setBloodLoss(int b){
		bloodLoss=b;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
