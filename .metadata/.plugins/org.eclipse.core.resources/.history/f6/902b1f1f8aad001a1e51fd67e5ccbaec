package com.blo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.VilleDAO;
import com.dto.Ville;

@Service
public class VilleBLOImpl implements VilleBLO {

	@Autowired
	private VilleDAO villeDAO;

	public ArrayList<Ville> getInfoVille(String monParam) {
		ArrayList<Ville> listeVille;

		if (monParam != null) {
			listeVille = villeDAO.findAllVilles(monParam);
		} else {
			listeVille = villeDAO.findAllVilles(monParam);
		}
		return listeVille;
	}

}