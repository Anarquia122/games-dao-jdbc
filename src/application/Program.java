package application;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dao.GamesDao;
import model.entities.Category;
import model.entities.Games;
import model.exceptions.DomainException;

public class Program {

public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		CategoryDao cDao = DaoFactory.createCategoryDao();
		GamesDao gDao = DaoFactory.createGamesDao();
		
		int n = 1;
		
		do {
			System.out.println("=================");
			System.out.println("0 - Finish");
			System.out.println("1 - Games");
			System.out.println("2 - Category");
			System.out.println("=================");
			
			n = sc.nextInt();
			sc.nextLine();
			
			try {
				if (n == 1) {
					//Games
					System.out.println();
					System.out.println("1 - Insert new game");
					System.out.println("2 - Update game");
					System.out.println("3 - Delete game by id");
					System.out.println("4 - Find game by id");
					System.out.println("5 - Find game by category");
					System.out.println("6 - Find all games");
					System.out.println("------------------------");
					
					n = sc.nextInt();
					sc.nextLine();
					switch (n) {
						case 1: {
							System.out.println();
							System.out.println("Enter with game data:");
							System.out.print("Name: ");
							String name = sc.nextLine();
							
							System.out.print("Price: ");
							double price = sc.nextDouble();
							sc.nextLine();
							
							System.out.print("Release date (dd/mm/yyyy): ");
							String releaseDate = sc.next();
							sc.nextLine();
							
							System.out.print("Category Id: ");
							int cateId = sc.nextInt();
							sc.nextLine();
							
							Games game = new Games(null, name, price, new Date(sdf.parse(releaseDate).getTime()), new Category(cateId, null));
							gDao.insert(game);
							System.out.println("Inserted! new id = " + game.getId());
							break;
						} case 2: {
							System.out.println();
							System.out.print("Game id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Games game = gDao.findById(id);
							if (game == null) {
								System.out.println("This game doesn't exist.");
								break;
							}
							
							System.out.println("Game found!");
							System.out.println("Enter with data to update:");
							System.out.print("Name: ");
							game.setName(sc.nextLine());
							
							System.out.println("Price: ");
							game.setPrice(sc.nextDouble());
							sc.nextLine();
							
							gDao.update(game);
							System.out.println("Update successful!");
							break;
						} case 3: {
							System.out.println();
							System.out.print("Game id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Games game = gDao.findById(id);
							if (game == null) {
								System.out.println("This game doesn't exist.");
								break;
							}
							
							System.out.println("Game found!");
							System.out.println(game);
							System.out.print("Write 'delete' to confirm: ");
							String confirm = sc.next();
							sc.nextLine();
							if (confirm.equalsIgnoreCase("delete")) {
								gDao.deleteById(id);
								System.out.println("Game deleted!");
							} else {
								System.out.println("Game not deleted!");
							}
							break;
						} case 4: {
							System.out.println();
							System.out.print("Game id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Games game = gDao.findById(id);
							if (game != null) {
								System.out.println("Game found!");
								System.out.println(game);
							} else {
								System.out.println("This game doesn't exist.");
							}
							break;
						} case 5: {
							System.out.println();
							System.out.print("Category Id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Category cat = cDao.findById(id);
							if (cat == null) {
								System.out.println("This category doesn't exist.");
								break;
							}
							
							List<Games> gamesList = gDao.findByCategory(cat);
							gamesList.forEach(System.out::println);
							break;
						} case 6: {
							List<Games> gamesList = gDao.findAll();
							gamesList.forEach(System.out::println);
							break;
						} default: {
							throw new DomainException(n + " isn't an option");
						}
					}
				} else if (n == 2) {
					//Category
					System.out.println();
					System.out.println("1 - Insert new category");
					System.out.println("2 - Update category");
					System.out.println("3 - Delete category by id");
					System.out.println("4 - Find category by id");
					System.out.println("5 - Find all categories");
					System.out.println("------------------------");
					
					n = sc.nextInt();
					sc.nextLine();
					switch (n) {
						case 1: {
							System.out.println();
							System.out.println("Enter with category data:");
							System.out.print("Name: ");
							String name = sc.nextLine();
							
							Category cat = new Category(null, name);
							cDao.insert(cat);
							
							System.out.println("Inserted! new id = " + cat.getId());
							break;
						} case 2: {
							System.out.println();
							System.out.print("Category id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Category cat = cDao.findById(id);
							if (cat == null) {
								System.out.println("This category doesn't exist!");
								break;
							}
							
							System.out.println("Enter with data to update");
							System.out.print("Name: ");
							cat.setName(sc.nextLine());
							
							cDao.update(cat);
							System.out.println("Update successful!");
							break;
						} case 3: {
							System.out.println();
							System.out.print("Category id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Category cat = cDao.findById(id);
							if (cat == null) {
								System.out.println("This category doesn't exist!");
								break;
							}
							
							System.out.println("Category found!");
							System.out.println(cat);
							System.out.print("Write 'delete' to confirm: ");
							String confirm = sc.next();
							sc.nextLine();
							if (confirm.equalsIgnoreCase("delete")) {
								cDao.deleteById(id);
								System.out.println("Category deleted!");
							} else {
								System.out.println("Category not deleted.");
							}
							break;
						} case 4: {
							System.out.print("Category id: ");
							int id = sc.nextInt();
							sc.nextLine();
							
							Category cat = cDao.findById(id);
							if (cat != null) {
								System.out.println("Category found!");
								System.out.println(cat);
							} else {
								System.out.println("This category doesn't exist");
							}
							break;
						} case 5: {
							List<Category> categoryList = cDao.findAll();
							categoryList.forEach(System.out::println);
							break;
						} default: {
							throw new DomainException(n + " isn't an option");
						}
					}
				} else {
					//erro
					if (n == 0) {
						System.out.println("Program ended.");
					} else {
						throw new DomainException(n + " isn't an option");
					}
				}
			} catch (ParseException e) {
				throw new DomainException(e.getMessage());
			} catch (DomainException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
		} while (n > 0);
		
		sc.close();
	}

}
