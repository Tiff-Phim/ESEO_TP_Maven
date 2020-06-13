package com.blo;

import java.sql.SQLException;
import java.util.ArrayList;

import com.dto.Ville;

public interface VilleBLO {
	public ArrayList<Ville> getInfoVille(String monParam) throws SQLException ;
	public Ville getVilleByCodePostal(String codePostal) throws SQLException ;
}
