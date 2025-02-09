package application;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.entities.Category;

public class Program {

public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		
		System.out.println("===== Test 1: category findById =====");
		Category category = categoryDao.findById(3);
		System.out.println(category);
		
		
		System.out.println();
		System.out.println("===== Test 2: category findAll =====");
		List<Category> allCategories = categoryDao.findAll();
		
		allCategories.forEach(System.out::println);
		
		
		System.out.println();
		System.out.println("===== Test 3: category insert =====");
		Category newCategory = new Category(null, "Survival");
		categoryDao.insert(newCategory);
		
		System.out.println("Inserted! new id = " + newCategory.getId());
		
		
		System.out.println();
		System.out.println("===== Test 4: category update =====");
		category.setName("Skyrim");
		categoryDao.update(category);
		
		System.out.println(category);
		
		
		System.out.println("\n===== Test 5: category delete =====");
		System.out.print("Enter id to delete: ");
		int id = sc.nextInt();
		sc.nextLine();
		categoryDao.deleteById(id);
		System.out.println("Delete completed.");
		
		sc.close();
	}

}
