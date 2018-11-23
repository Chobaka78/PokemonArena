import java.util.*;
import java.io.*;
public class Pokemon{
	private ArrayList<String> info = new ArrayList<String>();
	public String name, type, resistance, weakness;
	public int energy, hp, nums_attack, startHP, leastEnergy;
	ArrayList<Attack> Attack = new ArrayList<>();
	
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
		(for int i = 0; i < nums_attack; i ++){
			
		}
	}
	
	
}