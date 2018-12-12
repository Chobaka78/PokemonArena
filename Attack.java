/*
*Usman Farooqi
*Attack.java
*This is the attack class that will contain all the possible attack in an array list
*/
import java.util.*;
public class Attack{
		String name, effect;
		int energy, damage;
		public Attack(String name, int energy, int damage, String effect){ // takes in all the following perameters from the pokemon.txt file
			this.name = name; // variables of the class
			this.energy = energy;
			this.damage = damage;
			this.effect = effect;
		}
	}