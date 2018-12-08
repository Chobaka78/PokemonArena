import java.io.*;
import java.util.*;

public class PokemonArena{
	public static ArrayList<Pokemon> PokemonArray = new ArrayList<Pokemon>(); // arraylist that contains all the pokemons 
	public static ArrayList<Pokemon> pokemonTeam = new ArrayList<Pokemon>(); // Arraylist that contains all the pokemons the user choses as their team
	public static Scanner kb = new Scanner(System.in);
	public static Pokemon user; // making an user object
	public static Pokemon enemy; // making an enemy object
	public static String name; // users name this will be used later on in the game
	public static int attackChoice;
	public static int epick;
	public static boolean disable;
	public static void main(String [] args) throws IOException{ // when loading the file it may throw and exception and this will catch it
		
		int answer = 0;
		System.out.println("WELCOME TO POKEMON ARENA!!!");
		System.out.println("\n" + "Please enter your name: ");
		name = kb.nextLine().toUpperCase();
		System.out.println("Hello there, " + name + " are you ready to play pokemon Arena?");
		System.out.println("1.YES" + "\n" + "2.NO");
		answer = kb.nextInt();

		
		if(answer == 1){ // if the user enters yes then begin the game
			System.out.println("Great, let's begin, " + name + "\n");
			load();
			pickpokemon();
			System.out.println("Are you ready to battle? " + "\n" + "1. YES" + "\n" + "2. NO");
			answer = kb.nextInt();
			if(answer == 1){ // if user enters yes then start the battle
				chooseUser(); // lets the user choose their pokemon
				chooseEnemy();
				randomTurn(); // rnandomizes the turn
				//playgame(); // runs the play game method
			}
			else{ // if the user picks no as their answer then end the game 
				System.out.println("Good bye"); 
			}
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
	public static void chooseUser(){
		if(pokemonTeam.size() > 0){
			System.out.println("Choose a pokemon to fight with: ");
			for (int i = 0; i < pokemonTeam.size(); i++){
		    	System.out.println(i+1 + "." + pokemonTeam.get(i));
	    	}
	    	while (pokemonTeam.size() != 0){ // keeps asking until user gives possible answer
		    	int choice = kb.nextInt();
		    	if (choice > 0 && choice < pokemonTeam.size() + 1){
		    		user = pokemonTeam.get(choice-1); // takes chosen Pokemon from remaining Pokemon to choose from, and it becomes the user
		    		System.out.println(user.name + "! I choose you!" + "\n");
		    		break;
		    	}
		    	else{
		    		System.out.println("Invalid entry, pick another Pokemon:");
		    	}
	    	}
		}
	}

	public static void chooseEnemy(){
		if(PokemonArray.size() > 0){
			epick = (int)(Math.random()* PokemonArray.size()); // for picking a random pokemon for the enemy
			enemy = PokemonArray.get(epick);
		}
	}

	public static void randomTurn(){ // randomizes the turn
		int num = (int)(Math.random()* 2);
		if(num == 0){
			user_battle(user, enemy);
		}
		if(num == 1){
			System.out.println("\n" + "--------BATTLE--------" + "\n");
			enemy_battle(enemy, user);
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
	
	// public static void playgame(){
	// 	disable = false;
	// 	int choice = 0;
	// 	int energycost = user.leastEnergy;
	// 	chooseEnemy();
	// 	System.out.println("It's " + user.name + " vs " + enemy.name + "!!!");
	// 	if(turn == true){
	// 		System.out.println("\n" + "What would you like to do with this pokemon?" + "\n");
	// 		System.out.println("1.Attack    2.Pass    3.Retreat");
	// 		choice = kb.nextInt();
	// 		if(choice == 1 && user.energy < energycost){
	// 			System.out.println("You do not have enough energy for this attack");
	// 			choice = 2;
	// 		}
	// 		else if(choice == 1 && user.energy > energycost){
	// 			System.out.println("\n" + user.leastEnergy + "\n");
	// 			user_battle();	
	// 		}
	// 		else if(choice == 2){
	// 			System.out.println("You have passed your turn");
	// 			turn = false;
	// 		}
	// 		else if(choice == 3){
	// 			System.out.println("You have chosen to retreat");
	// 			pokemonTeam.remove(user);
	// 			chooseUser();
	// 			user_battle();
	// 			turn = false;
	// 		}

	// 	}
	// 	if(turn == false){
	// 		enemy_battle();
	// 	}
	// }

	public static void user_battle(Pokemon user, Pokemon enemy){
		disable = false;
		int round = 0;
		int answer = 0;
		int choice = 0;
		// asking the user what they would like to do with the pokemon
		System.out.println("It's " + user.name + " vs " + enemy.name);
		System.out.println("\n" + "What would you like to do with this pokemon?" + "\n");
		System.out.println("1.Attack    2.Pass    3.Retreat");
		choice = kb.nextInt();
		if(choice ==1 && user.energy < user.leastEnergy){ 
			System.out.println("You cant afford this attack");
			choice = 2; //pass
		}
		// if the choice is attack and they have sufficient energy
		else if(choice == 1 && user.energy > user.leastEnergy){
			//update on pokemon info
			System.out.println("\n" + user.name + " has " + user.energy + " energy" + "\n");
			System.out.println("You have " + user.nums_attack + " attacks to choose from:");
			for (int i = 0; i < user.nums_attack; i++){
	    		System.out.println(i+1 + ". " + user.attack.get(i).name + ", Energy: " + user.attack.get(i).energy + ", Damage: " + user.attack.get(i).damage + ", Effect: " + user.attack.get(i).effect);
			}

			while(true){
				attackChoice = kb.nextInt();
				if(enemy.hp > 0){ 
				enemy.hp -= user.attack.get(attackChoice - 1).damage;
				System.out.println("\n" + "--------BATTLE--------" + "\n");
				System.out.println(user.name + " attacks " + enemy.name + " with " + user.attack.get(attackChoice - 1).name + "\n");
				System.out.println(enemy + " has " + (enemy.hp < 0 ? "0" : enemy.hp) + " hp left"); // a terminary opperator
				user.energy -= user.attack.get(attackChoice - 1).energy;
				// if pokemon faitnts then end the battle
					if(enemy.hp <= 0){
						System.out.println(enemy.name + " fainted!");
						PokemonArray.remove(enemy);
						epick = (int)(Math.random()* PokemonArray.size());
						enemy = PokemonArray.get(epick);
						System.out.println("\n" + "------END BATTLE------" + "\n");
						System.out.println("would you like to change your pokemon?" + "\n" + "1.YES" + "\n" + "2.NO");
						answer = kb.nextInt();
						if(answer == 1){
							chooseUser();
						}
						else if (answer == 2){
							System.out.println("It's " + user.name + " vs " + enemy.name + "!!!");
							System.out.println("\n" + "--------BATTLE--------" + "\n");
						}
						break;
					}
					else{
						break;
					}
				}
			}
			enemy_battle(enemy, user);
		}
		// if the choice is to pass
		if(choice == 2){
			System.out.println("You have passed your turn" + "\n");
			enemy_battle(enemy, user);
		}

		// if the choice is to retreat
		if(choice == 3){
			System.out.println("You have chosen to retreat" + "\n");
			pokemonTeam.remove(user);
			chooseUser();
			enemy_battle(enemy, user);
		}

	}

	public static void enemy_battle(Pokemon enemy, Pokemon user){
		int eattack = (int)(Math.random()* enemy.attack.size());
		int e_attpick = (int)(Math.random()* 1);
		boolean disable = false;

		while(true){
			if(user.hp > 0){
				System.out.println(enemy.name + " has " + enemy.energy + " energy" + "\n");
				user.hp -= enemy.attack.get(eattack).damage;
				System.out.println(enemy.name + " attacks " + user.name + " with " + enemy.attack.get(eattack).name + "\n");
				System.out.println(user + " has " + (user.hp < 0 ? "0" : user.hp) + " hp left"); // a terminary opperator
				if(user.hp <= 0){
					System.out.println("Oh no! " + user.name + " fainted");
					pokemonTeam.remove(user);
					chooseUser();
					System.out.println("It's " + user.name + " vs " + enemy.name + "!!!");
					break;
				}
				else{
					break;
				}
			}
		}
		System.out.println("\n" + "------END BATTLE------" + "\n");
		for(int i = 0; i < pokemonTeam.size(); i++){
			if(pokemonTeam.get(i).energy < 50){
				pokemonTeam.get(i).energy += 10;
				if(pokemonTeam.get(i).energy > 50){
					pokemonTeam.get(i).energy = 50;
				}
			}
		}
		user_battle(user, enemy);
	}
}


/*
corrections to make
make it so that you remove the teamt that is picked from original pokemon
fix the fighting 
make a battle function
*/
