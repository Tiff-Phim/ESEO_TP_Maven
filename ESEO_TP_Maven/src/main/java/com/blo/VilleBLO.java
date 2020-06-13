package com.blo;

import java.sql.SQLException;
import java.util.List;

import com.dto.Ville;

public interface VilleBLO {
	public List<Ville> getInfoVille(String monParam) throws SQLException ;
	public Ville getVilleByCodePostal(String codePostal) throws SQLException ;
}
