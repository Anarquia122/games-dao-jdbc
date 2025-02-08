package model.dao;

import db.DB;
import model.dao.impl.GamesDaoJDBC;

public class DaoFactory {

	public static GamesDao createGamesDao() {
		return new GamesDaoJDBC(DB.getConnection());
	}
}
