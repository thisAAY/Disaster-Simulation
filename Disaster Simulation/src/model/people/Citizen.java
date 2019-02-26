package model.people;

import model.disasters.Disaster;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Citizen  implements Rescuable,Simulatable {
	private Address location;
	private CitizenState state;
	private Disaster disaster;
	private String nationalID;
	private String name;
	private int age;
	private int hp;
	private int bloodLoss;
	private int toxicity;
	
	public Citizen (Address location, String nationalID, String name, int age){
		this.age=age;
		this.location=location;
		this.nationalID=nationalID;
		this.name=name;
		this.state=CitizenState.SAFE;
		hp=100;
		toxicity=0;
		bloodLoss=0;
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
	
	public void setState(CitizenState state){
		this.state=state;
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
	public void setHp(int hp){
		this.hp=hp;
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
	public void setBloodLoss(int bloodLoss){
		this.bloodLoss=bloodLoss;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
