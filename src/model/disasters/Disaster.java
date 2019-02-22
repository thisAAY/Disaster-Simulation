package model.disasters;

import simulation.Rescuable;
import simulation.Simulatable;

public abstract class  Disaster implements Simulatable {
		private int startCycle;
		private Rescuable target;
		private boolean active=false;

		public Disaster(int startCycle, Rescuable target) {
			this.startCycle=startCycle;
			this.target=target;
		}
		public abstract int  getStartCycle() ;
		public abstract Rescuable getTarget();
		public void setActive(boolean x){
			active = x;
			
		}
		public boolean getActive(){
return active;
		}

}
