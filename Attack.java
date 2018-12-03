import java.util.*;
public class Attack{
		String name, effect;
		int energy, damage;
		public Attack(String name, int energy, int damage, String effect){
			this.name = name;
			this.energy = energy;
			this.damage = damage;
			this.effect = effect;
		}
		public String getName(){
			return name;
		}

		public int getEnergy(){
			return energy;
		}

		public int getDamage(){
			return damage;
		}

		public String getEffect(){
			return effect;
		}
	}