import java.util.*;
import java.io.*;
public class Pokemon{
	public String name, type, resistance, weakness;
	public int energy, hp, nums_attack, startHP, leastEnergy;
	ArrayList<Attack> attack = new ArrayList<>();
	
	public Pokemon(String stats){
		String [] items = stats.split(",");
		energy = 50;
		name = items[0];
		hp = Integer.parseInt(items[1]);
		type = items[2];
		resistance = items[3];
		weakness = items[4];
		nums_attack = Integer.parseInt(items[5]);
		leastEnergy = Integer.parseInt(items[7]);
		for (int i = 0; i < nums_attack; i ++){
			attack.add(new Attack(items[6 + i*4], Integer.parseInt(items[7 + i*4]), Integer.parseInt(items[8 + i*4]), items[9 + i*4]));
			if(attack.get(i).energy < leastEnergy){
				leastEnergy = attack.get(i).energy;
			}	
		}
		startHP = Integer.parseInt(items[1]);
	}
	
	public String toString(){
		return  name;
	}
	
	class Attack{
		String name, effect;
		int energy, damage;
		public Attack(String name, int energy, int damage, String effect){
			this.name = name;
			this.energy = energy;
			this.damage = damage;
			this.effect = effect;
		} 
	}
	
}