package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CategoryDao;
import model.entities.Category;

public class CategoryDaoJDBC implements CategoryDao {
	
	private Connection conn;

	public CategoryDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Category obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO category "
					+ "(Name) "
					+ "VALUES "
					+ "(?)"
					, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
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
	public void update(Category obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE category "
					+ "SET Name = ? "
					+ "WHERE Id = ? "
					);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
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
			st = conn.prepareStatement("DELETE FORM category WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Category findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM category "
					+ "WHERE "
					+ "Id = ?"
					);
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Category cat = instanciateCategory(rs);
				return cat;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Category instanciateCategory(ResultSet rs) throws SQLException {
		Category cat = new Category();
		cat.setId(rs.getInt("Id"));
		cat.setName(rs.getString("Name"));
		return cat;
	}

	@Override
	public List<Category> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Category> categoriesList = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT * FROM category");
			
			rs = st.executeQuery();
			while (rs.next()) {
				Category cat = instanciateCategory(rs);
				categoriesList.add(cat);
			}
			return categoriesList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
