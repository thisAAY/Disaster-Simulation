package controller;

import model.disasters.Disaster;
import model.people.Citizen;

public interface LogListener {
	void onCitizenDie(Citizen citizen);
	void onDiasterStrick(Disaster disaster);
}
