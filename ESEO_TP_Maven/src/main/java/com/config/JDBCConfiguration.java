package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDBCConfiguration {

	private static Logger logger = Logger.getLogger(JDBCConfiguration.class.getName());

	@Bean
	public static Connection getConnection() {

		String dbDriver = "com.mysql.jdbc.Driver";

		String bdd = "villeFrance";
		String url = "jdbc:mysql://localhost:3306/" + bdd;
		String user = "utilisateur";
		String pa = "utilisateur";
		Connection connection = null;
		// L'essaie de connexion à votre base de donées
		try {
			Class.forName(dbDriver);
			// création de la connexion
			connection = DriverManager.getConnection(url, user, pa);
		} catch (ClassNotFoundException e) {
			logger.log(Level.WARN, "Erreur pendant la récupération du Driver (" + dbDriver + ")" + e.getMessage(), e);
		} catch (SQLException e1) {
			logger.log(Level.WARN, "Erreur pendant la creation de la connexion à la BDD." + e1.getMessage(), e1);
		}
		return connection;
	}
}
