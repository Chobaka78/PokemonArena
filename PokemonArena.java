import java.io.*;
import java.util.*;

public class PokemonArena{
	private static ArrayList<Pokemon> PokemonArray = new ArrayList<Pokemon>(); // arraylist that contains all the pokemons 
	private static ArrayList<Pokemon> pokemonTeam = new ArrayList<Pokemon>(); // Arraylist that contains all the pokemons the user choses as their team
	public static Scanner kb = new Scanner(System.in);
	public static Pokemon user; // making an user object
	public static Pokemon enemy; // making an enemy object
	public static boolean disable; // a boolean variable the checks if the pokemon is diabled
	public static String name;
	public static boolean turn, stunned, play; // turn checks whos turn it is, stunned checks if the pokemon is stunned, play is if the game is running 

	public static void main(String [] args) throws IOException{ // when loading the file it may throw and exception and this will catch it
		startup(); // start up 
		load(); // load all the pokemons
		if(play == true){ 
		pickpokemon(); // pick pokemons
		chooseUser(); // lets the user choose their pokemon
		chooseEnemy(); // choose the enemy
			while(true){
				randomTurn(); // rnandomizes the turn
				playgame(); // runs the play game method
				break;
			}

			// this writes the amount of pokemons the player had remaining on to a file as a highscore
			String str = (name + ", " + PokemonArray.size() + " pokemons remaining");

			BufferedWriter writer = new BufferedWriter(new FileWriter("HighScore.txt", true)); 
    		writer.newLine(); // adds a line after every game
    		writer.append(str);
     
    		writer.close();
		}
	}
	// start up method that asks the user some questions
	public static void startup(){
		int answer = 0;
		System.out.println("WELCOME TO POKEMON ARENA!!!");
		System.out.println("\n" + "Please enter your name: ");
		name = kb.nextLine();
		System.out.println("Hello there, " + name + ", are you ready to play pokemon Arena?");
		System.out.println("1.YES" + "\n" + "2.NO");
		answer = kb.nextInt();

		if(answer == 1){ // if the user enters yes then begin the game
			System.out.println("Great, let's begin, " + name + "\n");
			play = true;
		}

		else if(answer == 2){
			System.out.println("Okay then, goodbye!");
		}
	}

	public static void pickpokemon(){ // this method allows the user to pick their pokemons
		int num = 0;
		for(int i = 0; i < PokemonArray.size(); i++){
			System.out.printf("%3d. %s" + "\n",i+1,PokemonArray.get(i));
		}
		
		while(pokemonTeam.size() <4){
			System.out.println("\n" + "Pick a pokemon: ");
			int picked = kb.nextInt();
			if(picked > 0 && picked <= 28){
				System.out.println(PokemonArray.get(picked-1));
				pokemonTeam.add(PokemonArray.get(picked-1));
				for (int i =0; i<pokemonTeam.size(); i++){
					PokemonArray.remove(pokemonTeam.get(i));
				}
				for(int i = 0; i < PokemonArray.size(); i++){
					System.out.printf("%3d. %s" + "\n",i+1,PokemonArray.get(i));
				}
				num += 1;
			}
			else if(num == 4){
				break;
			}
			else{
				System.out.println("Please pick a valid choice" );
			}
			
		}
		
		System.out.println("Your team is" + pokemonTeam);
	}
	public static Pokemon chooseUser(){ // user is choosing the 4 pokemons they want
		if(pokemonTeam.size() > 0){ // as long as the list of pokemons is greater than 0 so that there is no index error
			System.out.println("Choose a pokemon to fight with: ");
			for (int i = 0; i < pokemonTeam.size(); i++){
		    	System.out.println(i+1 + "." + pokemonTeam.get(i));
	    	}
	    	while (pokemonTeam.size() != 0){ // keeps asking until user gives possible answer
		    	int choice = kb.nextInt();
		    	if (choice > 0 && choice < pokemonTeam.size() + 1){
		    		user = pokemonTeam.get(choice-1); // takes chosen Pokemon from remaining Pokemon to choose from, and it becomes the user
		    		System.out.println(user.name + "! I choose you!");
		    		break;
		    	}
		    	else{
		    		System.out.println("Invalid entry, pick another Pokemon:");
		    	}
	    	}
		}
		return user;
	}

	public static void chooseEnemy(){ // randomally picks a pokemon from the remaining pokemons
		if(PokemonArray.size() > 0){
			int epick = (int)(Math.random()* PokemonArray.size()); // for picking a random pokemon for the enemy
			enemy = PokemonArray.get(epick);
			System.out.println("It's " + user.name + " vs " + enemy.name);
		}
	}

	public static void randomTurn(){ // randomizes the turn
		int num = (int)(Math.random()* 2);
		if(num == 0){
			turn = true;
		}
		if(num == 1){
			turn = false;
		}
	}
	
	
	public static void load() throws IOException{
		int tot = 0;
		Scanner fin = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		int numbers = Integer.parseInt(fin.nextLine());

		for (int i=0 ; i<numbers ; i++){
		
			PokemonArray.add(new Pokemon(fin.nextLine()));
		}
	}
	
	public static void playgame(){
		System.out.println("\n" + "-----BATTLE-----" + "\n");
		while(true){
			battle();
			if(user.hp == 0){ //If the user pokemon had fainted 
				System.out.println("Oh no! " + user.name + " has fainted");
				System.out.println("\n" + "------END BATTLE------" + "\n"); // end the battle
				pokemonTeam.remove(user);
				chooseUser();
				for(int i = 0; i < pokemonTeam.size(); i++){
					pokemonTeam.get(i).energy = 50; // reset all their energies
				}
				disable = false; // enemy pokemon has fainted so disable is set to false
				randomTurn(); // randomize the turn again
				System.out.println("\n" + "------BATTLE------" + "\n");
			}
			if(enemy.hp == 0){ // if the enemy pokemon has fainted
				System.out.println(enemy.name + " has no hp left " + enemy.name + " has fainted");
				System.out.println("\n" + "---END BATTLE---" + "\n");
				PokemonArray.remove(enemy);
				if(pokemonTeam.size() > 1){
					System.out.println("Would you like to change your pokemon" + "\n" + "1.YES" + "\n" + "2.NO"); //  ask the user if they want to change their pokemon
					while(true){
						int a = kb.nextInt();
						if(a == 1){
							chooseUser();
							break;
						}
						else if(a == 2){
							break;
						}
						else{
							System.out.println("Invalid choice please pick a valid choice");
						}
					}
				}
				chooseEnemy();
				// battle has ended so all the user pokemons will gain 20 hp and 50 energy
				for (int i = 0; i < pokemonTeam.size(); i++){ 
					pokemonTeam.get(i).hp += 20;
					pokemonTeam.get(i).hp = Math.min(pokemonTeam.get(i).hp, pokemonTeam.get(i).startHP); // but doesn't go over the amount of hp the Pokemon started with
					pokemonTeam.get(i).energy = 50;
				}
				disable = false; // user pokemon has fainted so disalbe is set to false
				System.out.println("\n" + "------BATTLE------" + "\n");
			}
			if(pokemonTeam.size() == 0){ // checks if all the users pokemons have fainted and the prints out that the user lost
				endgame();
				break;
			}
			if(PokemonArray.size() == 0){ //  checks if all the enemy pokemon have fainted and then prints out the user won
				endgame();
				break;
			}
		}// running the game

	}

	public static void battle(){
		if (turn == true){
			if(pokemonTeam.size() > 0){
				System.out.println(user.name + " has " + user.energy + " energy");
				int choice;
				if(user.energy < user.energy_cost){
					System.out.println("You don't have enough energy for this attack");
					choice = 2;
				}
				else{
					System.out.println("What would you like to do with " + user.name + "?");
			    	System.out.println("1. Attack      2. Pass     3. Retreat");
			    	choice = kb.nextInt();
				}
				if(choice == 1){
					System.out.println("You have " + user.nums_attack + " attacks to choose from " + "     " + user.hp + " hp || " + user.energy + " energy");
					for (int i = 0; i < user.nums_attack; i++){
			    		System.out.println(i+1 + ". " + user.attack.get(i).name + ", Energy: " + user.attack.get(i).energy + ", Damage: " + user.attack.get(i).damage + ", Effect: " + user.attack.get(i).effect);
					}
					while(true){
						int choose = kb.nextInt() - 1;
						if(choose + 1> 0 && choose  < user.nums_attack && user.attack.get(choose).energy <= user.energy){
							System.out.println("\n" + "-----Start round-----" + "\n");
							fight(user, enemy, choose);
							break;
						}
						else if(user.attack.get(choose).energy > user.energy){
							System.out.println("You don't have enough for this attack please pick a different one.");
						}
						else{
							System.out.println("Invalid entry please choose a valid entry");
						}
					}
				
					if(stunned == true){ // if the enemy pokemon is stunned then dont change the turn 
						turn = true;
					}
					else if(stunned == false){
						turn = false;
					}
				}
				if(choice == 2){
					System.out.println("You have passed your turn");
					turn = false;
				}
				if(choice == 3){
					System.out.println(user.name + " retreat!!!!");
					chooseUser();
					turn = false;
				}
				else if(choice == 3 && pokemonTeam.size() == 1){
					System.out.println("You only have one pokemon you can not reatreat!!!");
				}
			}
		}//end turn = true

		if(turn == false){
			if(enemy.hp != 0){
				if(enemy.energy_cost <= enemy.energy){
					int e_attpick = (int)(Math.random()*enemy.nums_attack); // randomly choose from attacks
					while(true){
						System.out.println("\n" + "-----Start round-----" + "\n");
						fight(enemy, user, e_attpick);
						break;
					}
					if(stunned == true){ // checks if the user pokemon is stunned if it is then turn stays the same
						turn = false;
					}
					else if(stunned == false){ // checks if the user pokemon isnt stunned and changes turn
						turn = true;
					}
				}
				else{
					System.out.println(enemy.name + "doesn't have enough energy for this round and has passed");
					turn = true;
				}
			}
		}//end turn = false

		for(int i = 0; i < pokemonTeam.size(); i++){
			pokemonTeam.get(i).energy += 10;
			pokemonTeam.get(i).energy = Math.min(pokemonTeam.get(i).energy, 50); // makes sure energy doesn't go over 50
		}
		if(enemy.energy < 50){
			enemy.energy += 10;
			if(enemy.energy > 50){
				enemy.energy = 50;
			}
		}
	}

	public static void fight(Pokemon attacker, Pokemon attacked, int number){
		int cost = attacker.attack.get(number).energy;
		int damage = attacker.attack.get(number).damage;
		String effect = attacker.attack.get(number).effect;
		int chance = 0;
		stunned = false;

		System.out.println(attacker.name + " attacked " + attacked.name + " with " + attacker.attack.get(number).name);

		if(attacker.type.equals(attacked.resistance)){
			System.out.println("This attack wasnt very effective");
			damage = damage/2;
		}
		else if(attacker.type.equals(attacked.weakness)){
			System.out.println("This attack was very effective");
			damage = damage*2;
		}
		else if(effect.equals("wild card")){
			chance = (int)Math.random()*2;
			if(chance == 0){
				System.out.println(attacker.name + " missed the attack!");
				damage = 0;
			}
			else{
				damage = damage;
			}
		}
		else if(effect.equals("wild storm")){
			int amount = 0;
			while(true){
				chance = (int)(Math.random()*2);
				if(chance == 0){
					amount +=1;
				}
				if(chance == 1){
					break;
				}
			}
			System.out.println(attacker.name + " hit " + attacked.name + " " + amount + " times");
			damage = damage*amount;
		}

		else if (effect.equals("stun")){
			chance = (int)(Math.random() * 2);
			if(chance == 0){
				System.out.println(attacked.name + " was stunned");
				stunned = true;
			}
			else{
				System.out.println(attacker.name + " failed to stun" + attacked.name);
				stunned = false;
			}	
		}

		else if(effect.equals("recharge")){
			attacker.energy += 20;
		}

		else if(effect.equals("disable")){ // fix disable beacuse its not minus 10 its minus 10 once
			if(disable == false){
				disable = true;
				System.out.println(attacked.name + " was disabled for this battle");
				for (int i = 0; i < attacked.nums_attack; i++){
					if(attacked.attack.get(i).damage > 0){
						attacked.attack.get(i).damage -= 10;
					}
				}
			}
		}

		else{
			System.out.println("This attack had no effect");
		}

		attacked.hp -= damage;
		attacked.hp = Math.max(0,attacked.hp);
		attacker.energy -= cost;
		System.out.println(enemy.name + " has " + enemy.hp + " hp, || " + enemy.energy + " energy");
		if(attacked.hp > 0){
			System.out.println("\n" + "------End round------" + "\n");
		}
	}
	public static void endgame(){
		if(pokemonTeam.size() == 0){
			System.out.println("You couldnt defeat all the pokemons, you had " + PokemonArray.size() + " pokemons remaining, you lose.");
		}
		else if(PokemonArray.size() == 0){
			System.out.println("Congratulations you defeated all the pokemons you are now crowned, Trainer Supreme!!!!!");
		}
	}

}


/*
corrections to make
make it so that you remove the teamt that is picked from original pokemon
fix the fighting 
make a battle function
*/
