package com.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blo.VilleBLO;
import com.dto.Ville;

@RestController
//@RequestMapping("/path")
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
	@RequestMapping(value="/villes",method=RequestMethod.GET)
	@ResponseBody
	public ArrayList<Ville> getAll(@RequestParam(required=false, value="codePostal", defaultValue = "0") String codePostal) throws SQLException{
		return villeBLOService.getInfoVille(codePostal);
	}
	
	/**
	 * Permet de récupérer une ville à partir de son code postal.
	 * 
	 * @param codePostal
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/villes/{codePostal}", method = RequestMethod.GET)
	@ResponseBody
	public Ville getVilleByCodePostal(@PathVariable(required=true,value="codePostal") String codePostal) throws SQLException {	
		
		Ville ville = villeBLOService.getVilleByCodePostal(codePostal);
		return ville;
	}

//	// Methode POST
//		@RequestMapping(value = "/ville", method = RequestMethod.POST)
//		@ResponseBody
//		public ArrayList<Ville> appelPost(@RequestParam(required=false, value="codePostal") String monParam) {
//			System.out.println("Appel POST");
//			
//			System.out.println("mon Param: "+monParam);
//		
//			
//			ArrayList<Ville> listeVille = villeBLOService.getInfoVille(); 
//			
//			return listeVille;
//		}
}