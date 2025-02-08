package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.GamesDao;
import model.entities.Category;
import model.entities.Games;

public class Program {

public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		GamesDao gamesDao = DaoFactory.createGamesDao();
		
		System.out.println("===== Test 1: games findById =====");
		Games games = gamesDao.findById(3);
		System.out.println(games);
		
		System.out.println();
		System.out.println("===== Test 2: games findByDepartment =====");
		Category cat = new Category(1, null);
		List<Games> gamesList = gamesDao.findByCategory(cat);
		
		gamesList.forEach(System.out::println);
		
		
		System.out.println();
		System.out.println("===== Test 3: games findAll =====");
		List<Games> allGames = gamesDao.findAll();
		
		allGames.forEach(System.out::println);
		
		
		System.out.println();
		System.out.println("===== Test 4: games insert =====");
		Games newGames = new Games(null, "Devil May Cry", 350.00, new Date(), new Category(1, null));
		gamesDao.insert(newGames);
		
		System.out.println("Inserted! new id = " + newGames.getId());
		
		
		System.out.println();
		System.out.println("===== Test 5: games update =====");
		games.setName("Skyrim");
		gamesDao.update(games);
		
		System.out.println(games);
		
		
		System.out.println("\n===== Test 6: games delete =====");
		System.out.print("Enter id to delete: ");
		int id = sc.nextInt();
		sc.nextLine();
		gamesDao.deleteById(id);
		System.out.println("Delete completed.");
		
		sc.close();
	}

}
