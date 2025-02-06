package model.dao;

import java.util.List;

import model.entities.Category;
import model.entities.Games;

public interface GamesDao {

	void insert(Games obj);
	void update(Games obj);
	void deleteById(Integer id);
	Games findById(Integer id);
	List<Games> findAll();
	List<Games> findByDepartment(Category category);
}
