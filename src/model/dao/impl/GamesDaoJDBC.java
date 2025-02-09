package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.GamesDao;
import model.entities.Category;
import model.entities.Games;

public class GamesDaoJDBC implements GamesDao {

	private Connection conn;
	
	public GamesDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Games obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO games "
					+ "(Name, Price, ReleaseDate, CategoryId) "
					+ "VALUES "
					+ "(?, ?, ?, ?)"
					, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getPrice());
			st.setDate(3, new java.sql.Date(obj.getReleaseDate().getTime()));
			st.setInt(4, obj.getCategory().getId());
			
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Games obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE games "
					+ "SET Name = ?, Price = ?, ReleaseDate = ?, CategoryId = ? "
					+ "WHERE Id = ?"
					);
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getPrice());
			st.setDate(3, new java.sql.Date(obj.getReleaseDate().getTime()));
			st.setInt(4, obj.getCategory().getId());
			st.setInt(5, obj.getId());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM games WHERE id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Games findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT games.*, category.Name AS CatName "
					+ "FROM games INNER JOIN category "
					+ "ON games.CategoryId = category.id "
					+ "WHERE games.Id = ?"
					);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Category cat = instanciateCategory(rs);
				
				Games game = instanciateGames(rs, cat);
				
				return game;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Games instanciateGames(ResultSet rs, Category cat) throws SQLException  {
		Games games = new Games();
		games.setId(rs.getInt("Id"));
		games.setName(rs.getString("Name"));
		games.setPrice(rs.getDouble("Price"));
		games.setReleaseDate(rs.getDate("ReleaseDate"));
		games.setCategory(cat);
		return games;
	}

	private Category instanciateCategory(ResultSet rs) throws SQLException {
		Category cat = new Category();
		cat.setId(rs.getInt("CategoryId"));
		cat.setName(rs.getString("CatName"));
		return cat;
	}

	@Override
	public List<Games> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null; 
		List<Games> listGames = new ArrayList<>();
		
		try {
			st = conn.prepareStatement(
					"SELECT games.*, category.Name AS CatName "
					+ "FROM games INNER JOIN category "
					+ "ON games.CategoryId = category.id "
					);
			
			rs = st.executeQuery();
			Map<Integer, Category> map = new HashMap<>();
			
			while (rs.next()) {
				Category cat = map.get(rs.getInt("CategoryId"));
				
				if (cat == null) {
					cat = instanciateCategory(rs);
					map.put(cat.getId(), cat);
				}
				
				Games game = instanciateGames(rs, cat);
				
				listGames.add(game);
			}
			
			return listGames;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Games> findByCategory(Category category) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Games> listGames = new ArrayList<>();
		
		try {
			st = conn.prepareStatement(
					"SELECT games.*, category.Name AS CatName "
					+ "FROM games INNER JOIN category "
					+ "ON games.CategoryId = category.Id "
					+ "WHERE category.id = ?"
					);
			
			st.setInt(1, category.getId());
			rs = st.executeQuery();
			Map<Integer, Category> map = new HashMap<>();
			
			while (rs.next()) {
				Category cat = map.get(rs.getInt("CategoryId"));
				
				if (cat == null) {
					cat = instanciateCategory(rs);
					map.put(cat.getId(), cat);
				}
				
				Games game = instanciateGames(rs, cat);
				
				listGames.add(game);
			}
			
			return listGames;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
