package com.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.dto.Ville;

public interface VilleDAO {
	
	public ArrayList<Ville> getAllVilles(String param) throws SQLException;
	public Ville getVilleByCodePostal(String codePostal) throws SQLException;

}