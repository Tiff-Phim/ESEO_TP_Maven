package com.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleBLO;
import com.dto.Ville;

@RestController
@RequestMapping("/api")
class VilleController {

	@Autowired
	VilleBLO villeBLOService;

	/**
	 * Permet de récupérer toutes les villes de France.
	 * Il est possible de spécifier ou non le filtrage par codePostal.
	 * 
	 * @param codePostal
	 * @return
	 * @throws SQLException 
	 */
	@GetMapping(path = "/villes")
	@ResponseBody
	public List<Ville> getAll(@RequestParam(required=false, value="codePostal", defaultValue = "0") String codePostal) throws SQLException {
		return villeBLOService.getInfoVille(codePostal);
	}
	
	/**
	 * Permet de récupérer une ville à partir de son code postal.
	 * 
	 * @param codePostal
	 * @return ville
	 * @throws SQLException 
	 */
	@GetMapping(path = "/villes/{codePostal}")
	@ResponseBody
	public Ville getVilleByCodePostal(@PathVariable(required=true,value="codePostal") String codePostal) throws SQLException {	
		return villeBLOService.getVilleByCodePostal(codePostal);
	}
	
	/**
	 * Permet d'ajouter une ville dans la base de données.
	 * 
	 * @throws SQLException 
	 */
	@PostMapping(path = "/villes")
	@ResponseBody
	public Ville insertVille(@RequestBody Ville ville) throws SQLException {
		return villeBLOService.addVille(ville);
	}
	
	/**
	 * Permet de mettre à jour une ville.
	 * 
	 * @param ville
	 * @param codePostal
	 * @throws SQLException
	 */
	@PutMapping("/villes/{codePostal}")
	public Ville replaceVille(@RequestBody Ville ville, @PathVariable String codePostal) throws SQLException {
		return villeBLOService.updateVille(ville, codePostal);
	}
	
	/**
	 * Permet la suppression d'une ville.
	 * 
	 * @param codePostal
	 * @throws SQLException
	 */
	public void deleteVille(@PathVariable String codePostal) throws SQLException {
		villeBLOService.deleteVille(codePostal);
	}
}