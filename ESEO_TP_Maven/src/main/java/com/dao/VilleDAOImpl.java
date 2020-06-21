package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.config.JDBCConfiguration;
import com.dto.Ville;

@Repository
public class VilleDAOImpl implements VilleDAO {

	// Attributs d'un objet Ville
	private static final String ATTRIBUT_CODE_COMMUNE = "Code_commune_INSEE";
	private static final String ATTRIBUT_NOM_COMMUNE = "Nom_commune";
	private static final String ATTRIBUT_CODE_POSTAL = "Code_postal";
	private static final String ATTRIBUT_LIBELLE_ACHEMINEMENT = "Libelle_acheminement";
	private static final String ATTRIBUT_LIGNE = "Ligne_5";
	private static final String ATTRIBUT_LATITUDE = "Latitude";
	private static final String ATTRIBUT_LONGITUDE = "Longitude";

	// Requêtes SQL
	private static final String SELECT_VILLE = "SELECT * FROM ville_france";
	private static final String SELECT_VILLE_CODE_POSTAL = "SELECT * FROM ville_france WHERE Code_postal=?";
	private static final String INSERT_VILLE = "INSERT INTO ville_france (Code_commune_INSEE,Nom_commune,Code_postal,"
			+ "Libelle_acheminement,Ligne_5,Latitude,Longitude) VALUES (?,?,?,?,?,?,?)";
	private static final String UPDATE_VILLE = "UPDATE ville_france SET Code_commune_INSEE=?, Nom_commune=?, "
			+ "Libelle_acheminement=?, Ligne_5=?, Latitude=?, Longitude=? WHERE Code_postal=?";
	private static final String DELETE_VILLE_CODE_POSTAL = "DELETE * FROM ville_france WHERE Code_postal=?";

	// Gestion des erreurs
	private static final String ECHEC_RECHERCHE_OBJET = "Échec de la recherche de l'objet.";
	private static final String ECHEC_CREATION_OBJET = "Échec de la création de l'objet, aucune ligne ajoutée dans la table.";
	private static final String ECHEC_MODIFICATION_OBJET = "Échec de la mise à jour de l'objet, aucune ligne modifiée dans la table.";
	private static final String ECHEC_SUPPRESSION_OBJET = "Échec de la suppression de l'objet.";

	private static Logger logger = Logger.getLogger(VilleDAOImpl.class.getName());

	/**
	 * Fait la correspondance (le mapping) entre une ligne issue de la table
	 * ville_france (un ResultSet) et un bean Ville.
	 * 
	 * @param resultSet la ligne issue de la table ville_france.
	 * @return ville le bean dont on souhaite faire la correspondance.
	 * @throws SQLException
	 */
	protected Ville recupererVille(ResultSet resultSet) throws SQLException {
		Ville ville = new Ville();
		ville.setCodeCommune(resultSet.getString(ATTRIBUT_CODE_COMMUNE));
		ville.setNomCommune(resultSet.getString(ATTRIBUT_NOM_COMMUNE));
		ville.setCodePostal(resultSet.getString(ATTRIBUT_CODE_POSTAL));
		ville.setLibelleAcheminement(resultSet.getString(ATTRIBUT_LIBELLE_ACHEMINEMENT));
		ville.setLigne(resultSet.getString(ATTRIBUT_LIGNE));
		ville.setLatitude(resultSet.getDouble(ATTRIBUT_LATITUDE));
		ville.setLongitude(resultSet.getDouble(ATTRIBUT_LONGITUDE));
		return ville;
	}

	/**
	 * Initialise une requête préparée.
	 * 
	 * @param connection          la connexion à la BDD.
	 * @param sql                 la requête SQL.
	 * @param returnGeneratedKeys le boolean qui permet de générer des ID ou pas.
	 * @param objets              la liste d'objets à insérer dans la requête.
	 * @return preparedStatement la requête préparée initialisée.
	 * @throws SQLException
	 */
	protected static String initialisationRequetePreparee(String sql, Object... objets) {
		String[] listeSQL = (sql + " ").split("\\?");
		StringBuilder newSQL = new StringBuilder(listeSQL[0]);
		for (int i = 0; i < objets.length; i++) {
			newSQL.append("\"" + objets[i] + "\"" + listeSQL[i + 1]);
		}
		return newSQL.toString().replaceAll("\"null\"", "null");
	}

	/**
	 * Ferme la connection.
	 * 
	 * @param connection la connection à fermer.
	 */
	protected static void fermeture(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Echec de la fermeture de la connexion : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Ferme le statement.
	 * 
	 * @param statement le statement à fermer.
	 */
	protected static void fermeture(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Echec de la fermeture du Statement : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Ferme le resultset.
	 * 
	 * @param resultSet le resultSet à fermer.
	 */
	protected static void fermeture(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Echec de la fermeture du ResultSet : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Liste toutes les villes présentes dans la base de données.
	 * 
	 * @throws SQLException
	 */
	public List<Ville> getAllVilles(String param) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		List<Ville> listeVille = new ArrayList<>();
		try {
			connection = JDBCConfiguration.getConnection();
			preparedStatement = connection.prepareStatement(SELECT_VILLE);
			results = preparedStatement.executeQuery();
			while(results.next()) {
				listeVille.add(recupererVille(results));
			}
		} catch (SQLException e) {
			logger.log(Level.WARN, ECHEC_RECHERCHE_OBJET, e);
		} finally {
			fermeture(preparedStatement);
			fermeture(connection);
		}
		return listeVille;
	}

	/**
	 * Recherche une ville à l'aide de son code postal.
	 * 
	 * @param codePostal codePostal de la ville recherchée.
	 */
	public Ville getVilleByCodePostal(String codePostal) throws SQLException {
		Ville ville = new Ville();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		try {
			connection = JDBCConfiguration.getConnection();
			preparedStatement = connection.prepareStatement(
					initialisationRequetePreparee(SELECT_VILLE_CODE_POSTAL, codePostal), Statement.NO_GENERATED_KEYS);
			results = preparedStatement.executeQuery();
			while(results.next()) {
				ville = recupererVille(results);
			}
		} catch (SQLException e) {
			logger.log(Level.WARN, ECHEC_RECHERCHE_OBJET, e);
		} finally {
			fermeture(preparedStatement);
			fermeture(results);
			fermeture(connection);
		}
		return ville;
	}

	/**
	 * Ajout d'une ville dans la base de données.
	 * 
	 * @param ville ville à ajoutée dans la base de données.
	 * @throws SQLException
	 */
	public Ville createVille(Ville ville) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			// création de la connexion
			connection = JDBCConfiguration.getConnection();
			preparedStatement = connection.prepareStatement(
					initialisationRequetePreparee(INSERT_VILLE, ville.getCodeCommune(), ville.getNomCommune(),
							ville.getCodePostal(), ville.getLibelleAcheminement(), ville.getLigne(),
							String.valueOf(ville.getLatitude()), String.valueOf(ville.getLongitude())),
					Statement.NO_GENERATED_KEYS);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARN, ECHEC_CREATION_OBJET, e);
		} finally {
			fermeture(preparedStatement);
			fermeture(connection);
		}
		return ville;
	}
	
	/**
	 * Mise à jour d'une ville.
	 * 
	 * @param ville la nouvelle ville
	 * @param codePostal le code postal de la ville à modifier
	 */
	public Ville updateVille(Ville ville, String codePostal) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JDBCConfiguration.getConnection();
			preparedStatement = connection.prepareStatement(initialisationRequetePreparee(UPDATE_VILLE, ville.getCodeCommune(),
					ville.getNomCommune(), ville.getLibelleAcheminement(), ville.getLigne(), ville.getLatitude(), 
					ville.getLongitude(), codePostal), Statement.RETURN_GENERATED_KEYS);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARN, ECHEC_MODIFICATION_OBJET);
		} finally {
			fermeture(preparedStatement);
			fermeture(connection);
		}
		return ville;
	}
	
	/**
	 * Suppression d'une ville.
	 * 
	 * @param ville ville à supprimer.
	 */
	public void deleteVille(String codePostal) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// Recherche de la ville à l'aide de son code postal
		Ville ville = getVilleByCodePostal(codePostal);
		try {
			connection = JDBCConfiguration.getConnection();
			preparedStatement = connection.prepareStatement(initialisationRequetePreparee(DELETE_VILLE_CODE_POSTAL, codePostal), Statement.NO_GENERATED_KEYS);
			int status = preparedStatement.executeUpdate();
			if(status == 0) {
				throw new SQLException();
			} else {
				ville.setCodePostal(null);
			}
			//
		} catch(SQLException e) {
			logger.log(Level.WARN, ECHEC_SUPPRESSION_OBJET);
		} finally {
			fermeture(preparedStatement);
			fermeture(connection);
		}
	}	
}