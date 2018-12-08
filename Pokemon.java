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
		for (int i = 0; i < nums_attack; i ++){
			attack.add(new Attack(items[6 + i*4], Integer.parseInt(items[7 + i*4]), Integer.parseInt(items[8 + i*4]), items[9 + i*4]));
			leastEnergy = Integer.parseInt(items[7 + i*4]);
		}
		startHP = Integer.parseInt(items[1]);
	}
	
	public String toString(){
		return  name;
	}
}





