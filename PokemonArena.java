import java.io.*;
import java.util.*;

public class PokemonArena{
	private static ArrayList<String> PokemonArray = new ArrayList<String>();
	public static void main(String [] args){
		try{
			int tot = 0;
			Scanner fin = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));

			while(fin.hasNextLine()){
				PokemonArray.add(fin.nextLine());
			}
			System.out.println(PokemonArray);
		}
		catch(IOException ex){
			System.out.println("Umm, where is nums.txt?");
		}
	}
}