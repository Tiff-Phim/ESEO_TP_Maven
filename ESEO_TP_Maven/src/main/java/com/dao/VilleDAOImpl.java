package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.config.JDBCConfiguration;
import com.dto.Ville;

@Repository
public class VilleDAOImpl implements VilleDAO {
	
	
	public ArrayList<Ville> findAllVilles(String Param) {
		ArrayList<Ville> listeVille = new ArrayList<Ville>();
		
		Connection con = JDBCConfiguration.getConnection();
		
		ResultSet results = null;
		String requete = "SELECT * FROM ville_france ";
		
		try {
			Statement stmt = con.createStatement();
			results = stmt.executeQuery(requete);
			
			while(results.next()) {
				Ville ville = new Ville();
				
				ville.setCodeCommune(results.getString("Code_commune_INSEE"));
				ville.setNomCommune(results.getString("Nom_commune"));
				ville.setCodePostal(results.getString("Code_postal"));
				ville.setLibelleAcheminement(results.getString("Libelle_acheminement"));
				ville.setLigne(results.getString("Ligne_5"));
				
				listeVille.add(ville);
			}
			
		}catch(SQLException e ) {
			//Traitement de l'exception
		}
		return listeVille;
		
	}

}