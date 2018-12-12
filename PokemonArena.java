/*
*Usman Farooqi
*PokemonArena.java
*This program will take 4 pokemons of the users choice from a list of 28 and have the user fight the ramaining 24
*The enemy pokemons will be picked randomally
*At the end of every round all pokemons will gain 10 energy, and at the end of every battle the user pokemons will gain hp and energy
*If the user defeats all the remaining pokemons they are crowned "Trainer supreme"
*/
import java.io.*;
import java.util.*;
public class PokemonArena{
	private static ArrayList<Pokemon> PokemonArray = new ArrayList<Pokemon>(); // arraylist that contains all the pokemons 
	private static ArrayList<Pokemon> pokemonTeam = new ArrayList<Pokemon>(); // Arraylist that contains all the pokemons the user choses as their team
	public static Scanner kb = new Scanner(System.in);
	public static Pokemon user; // making an user object
	public static Pokemon enemy; // making an enemy object
	public static boolean disable; // a boolean variable the checks if the pokemon is diabled
	public static String name; //  player name
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

		else if(answer == 2){ //  if the user enteres no then end the game
			System.out.println("Okay then, goodbye!");
		}
	}

	public static void pickpokemon(){ // this method allows the user to pick their pokemons
		int num = 0;
		for(int i = 0; i < PokemonArray.size(); i++){ 
			System.out.printf("%3d. %s" + "\n",i+1,PokemonArray.get(i));
		}
		
		while(pokemonTeam.size() <4){ // making sure more than 4 pokemons are not picked
			System.out.println("\n" + "Pick a pokemon: ");
			int picked = kb.nextInt();
			if(picked > 0 && picked <= 28){ // making sure anything above 28 or below 0 is entered
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
		
		System.out.println("Your team is" + pokemonTeam); // prints our the team
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
		return user; // returns the user 
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
			turn = true; // if the random number is 0 then its the users turn
		}
		if(num == 1){
			turn = false; // else the enemy goes
		}
	}
	
	// loading the file 
	public static void load() throws IOException{ // making sure it catches any exceptions when laoding the file
		int tot = 0;
		Scanner fin = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
		int numbers = Integer.parseInt(fin.nextLine()); // this ignores the number 28 in the begining

		for (int i=0 ; i<numbers ; i++){
		
			PokemonArray.add(new Pokemon(fin.nextLine())); // reading the file
		}
	}
	
	public static void playgame(){ // main method controls the battle 
		System.out.println("\n" + "-----BATTLE-----" + "\n");
		while(true){
			battle(); // calls the battle function
			if(user.hp == 0){ //If the user pokemon had fainted 
				System.out.println("Oh no! " + user.name + " has fainted");
				System.out.println("\n" + "------END BATTLE------" + "\n"); // end the battle
				pokemonTeam.remove(user); // remove the pokemon
				chooseUser(); // choose new pokemon
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
				System.out.println("All your pokemons have gained 20 hp and 50 energy!!!" + "\n");
				System.out.println("You have " + PokemonArray.size() + " pokemons remaining" + "\n");
				PokemonArray.remove(enemy); // remove the pokemon
				if(pokemonTeam.size() > 1){ // if the user has more than 1 pokemon ask if they want to switch
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
				chooseEnemy(); // choose a new random enemy
				// battle has ended so all the user pokemons will gain 20 hp and 50 energy
				for (int i = 0; i < pokemonTeam.size(); i++){ 
					pokemonTeam.get(i).hp += 20;
					pokemonTeam.get(i).hp = Math.min(pokemonTeam.get(i).hp, pokemonTeam.get(i).originalHP); // but doesn't go over the amount of hp the Pokemon started with
					pokemonTeam.get(i).energy = 50;
				}
				disable = false; // user pokemon has fainted so disalbe is set to false
				System.out.println("\n" + "------BATTLE------" + "\n");
			}
			if(pokemonTeam.size() == 0 || PokemonArray.size() == 0){ // checks if all the users pokemons have fainted or all enemy pokemon have fainted
				endgame(); // run endgame method
				break;
			}
		}// running the game

	}

	public static void battle(){ // this method controls the battle for every round
		if (turn == true){
			if(pokemonTeam.size() > 0){
				int choice;
				if(user.energy < user.energy_cost){ // if the user doesnt have enough energy then let them pick to pass or retreat
					System.out.println("You don't have enough energy for this attack");
					System.out.println("What would you like to do with " + user.name + "?");
			    	System.out.println("1. Pass     2. Retreat");
			    	choice = kb.nextInt() + 1;
				}
				else{ // else ask the user what they want to do with the pokemon
					System.out.println("What would you like to do with " + user.name + "?");
			    	System.out.println("1. Attack      2. Pass     3. Retreat");
			    	choice = kb.nextInt();
				}
				// if the user wants to attack
				if(choice == 1){
					System.out.println("You have " + user.nums_attack + " attacks to choose from " + "     " + user.hp + " hp || " + user.energy + " energy");
					for (int i = 0; i < user.nums_attack; i++){
			    		System.out.println(i+1 + ". " + user.attack.get(i).name + ", Energy: " + user.attack.get(i).energy + ", Damage: " + user.attack.get(i).damage + ", Effect: " + user.attack.get(i).effect);
					}
					// keep asking the user what attack they want to pick
					while(true){
						int choose = kb.nextInt() - 1;
						if(choose + 1> 0 && choose  < user.nums_attack && user.attack.get(choose).energy <= user.energy){
							System.out.println("\n" + "-----Start round-----" + "\n");
							fight(user, enemy, choose); // runs the fight method
							break;
						}
						else if(user.attack.get(choose).energy > user.energy){ // if the user choose to attack but doesnt have energy for 1 attack but can pick another one with suffiecent energy
							System.out.println("You don't have enough for this attack please pick a different one.");
						}
						else{
							System.out.println("Invalid entry please choose a valid entry");
						}
					}
				
					if(stunned == true){ // if the enemy pokemon is stunned then dont change the turn 
						turn = true;
					}
					else if(stunned == false){ // if the enemy isnt stunned then switch turns
						turn = false;
					}
				}
				if(choice == 2){ // passing the users turn
					System.out.println("You have passed your turn");
					turn = false;
				}
				if(choice == 3){ // the user has retreated 
					System.out.println(user.name + " retreat!!!!");
					chooseUser();
					turn = false;
				}
				else if(choice == 3 && pokemonTeam.size() == 1){ // if the user chose to retreat but only has 1 pokemon
					System.out.println("You only have one pokemon you can not reatreat!!!");
				}
			}
		}//end turn = true

		if(turn == false){ // enemys turn
			if(enemy.hp != 0){ // as long as the enemy's health is not 0
				if(enemy.energy_cost <= enemy.energy){ //if the enemy has suffiecent energy 
					int e_attpick = (int)(Math.random()*enemy.nums_attack); // randomly choose from attacks
					while(true){
						System.out.println("\n" + "-----Start round-----" + "\n");
						fight(enemy, user, e_attpick); // runs the fight method
						break;
					}
					if(stunned == true){ // checks if the user pokemon is stunned if it is then turn stays the same
						turn = false;
					}
					else if(stunned == false){ // checks if the user pokemon isnt stunned and changes turn
						turn = true;
					}
				}
				else{ // if the enemy pokemon doesnt have enough energy their turn is passed
					System.out.println(enemy.name + "doesn't have enough energy for this round and has passed");
					turn = true;
				}
			}
		}//end turn = false
		// at the end of each round increase the energy of all pokemons to a minimum of 50
		for(int i = 0; i < pokemonTeam.size(); i++){
			pokemonTeam.get(i).energy += 10;
			pokemonTeam.get(i).energy = Math.min(pokemonTeam.get(i).energy, 50); // makes sure energy doesn't go over 50
		}
		// increase all enemy pokemon's energy 
		if(enemy.energy < 50){
			enemy.energy += 10;
			if(enemy.energy > 50){
				enemy.energy = 50;
			}
		}
	}

	// this method controls all special effects and damage 
	public static void fight(Pokemon attacker, Pokemon attacked, int number){ // takes 3 perameters attacker, attacked, number which is used for the attack
		int cost = attacker.attack.get(number).energy; // energy cost
		int damage = attacker.attack.get(number).damage; // damage
		String special = attacker.attack.get(number).effect; // effects
		int chance = 0; // chance of sucess
		stunned = false; // set stunned to false every time 

		System.out.println(attacker.name + " attacked " + attacked.name + " with " + attacker.attack.get(number).name);
		System.out.println("This attack does " + attacker.attack.get(number).damage + " damage");

		if(attacker.type.equals(attacked.resistance)){ // if the attackers type is the attacked resistance 
			System.out.println("This attack wasnt very effective");
			damage = damage/2; // cut damage in half
		}
		if(attacker.type.equals(attacked.weakness)){ // if the attackers type is the attacked weakness
			System.out.println("This attack was very effective");
			damage = damage*2; // multiply damage by 2
		}
		if(special.equals("wild card")){ // wild card has 50 percent chance of sucess
			chance = (int)Math.random()*2;
			if(chance == 0){
				System.out.println(attacker.name + " missed the attack!");
				damage = 0; // if it fails then no damage is done
			}
			else{
				damage = damage; // else damage is carried out
			}
		}
		if(special.equals("wild storm")){ // wild storm has 50 percent chance of sucess
			int amount = 0;
			while(true){
				chance = (int)(Math.random()*2);
				if(chance == 0){ // if it suceeds 
					amount +=1; // increase the amount of attcks by 1
				}
				if(chance == 1){
					break; // else do nothing
				}
			}
			System.out.println(attacker.name + " hit " + attacked.name + " " + amount + " times");
			damage = damage*amount; // will attack "amount" number of times
		}

		if (special.equals("stun")){ // if the attacked pokemon is stunned
			chance = (int)(Math.random() * 2); // 50 percent chance of this happening
			if(chance == 0){
				System.out.println(attacked.name + " was stunned");
				stunned = true;
			}
			else{
				System.out.println(attacker.name + " failed to stun " + attacked.name);
				stunned = false;
			}	
		}

		if(special.equals("recharge")){ // if the effect is recharge
			attacker.energy += 20; // add 20 energy
		}

		if(special.equals("disable")){ // if the effect is disable
			if(disable == false){ // makes sure that disable isnt already active
				disable = true;
				System.out.println(attacked.name + " was disabled for this battle");
				for (int i = 0; i < attacked.nums_attack; i++){
					if(attacked.attack.get(i).damage > 0){
						attacked.attack.get(i).damage -= 10; // decrease the amount of damage by 10
					}
				}
			}
		}

		if(special.equals(" ")){ // if the special is nothing 
			System.out.println("This attack had no special effect");
		}

		attacked.hp -= damage; // subtract the damage from the attacked pokemons health
		attacked.hp = Math.max(0,attacked.hp); // make sure it doesnt drop below 0
		if(attacker.energy > 0){ // if the cost of energy is greator than 0 (so enemy energy doesnt enter -ve)
			attacker.energy -= cost;
			if(attacker.energy < 0){
				attacker.energy = 0; // if it does set it to 0
			}
		}
		System.out.println(enemy.name + " has " + enemy.hp + " hp || " + enemy.energy + " energy");
		System.out.println(user.name + " has " + user.hp + " hp || " + user.energy + " energy");
		if(attacked.hp > 0){ // if the attacked pokemon hasnt fainted end the round
			System.out.println("\n" + "------End round------" + "\n");
			System.out.println("All pokemons gained 10 energy!!!" + "\n");
		}
	}
	// this method contols the end game response based on if the player won or lost
	public static void endgame(){ 
		if(pokemonTeam.size() == 0){ // if all user pokemons have been defeated 
			System.out.println("You couldnt defeat all the pokemons, you had " + PokemonArray.size() + " pokemons remaining, you lose.");
		}
		else if(PokemonArray.size() == 0){ // if all enemy pokemons have been defeated
			System.out.println("Congratulations you defeated all the pokemons you are now crowned, Trainer Supreme!!!!!");
		}
	}

}
