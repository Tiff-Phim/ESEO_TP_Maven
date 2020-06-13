package com.dto;

import static org.junit.Assert.*;

import org.junit.Test;

public class VilleTest {
	
	@Test
	public void testGetterSetterCodeCommune() {
		Ville ville = new Ville();
		ville.setCodeCommune("01001");
		assertEquals("Le code commune attendu est le 01001 le résultat obtenu est : " + ville.getCodeCommune(),"01001",ville.getCodeCommune());
	}
	
	@Test
	public void testGetterSetterNomCommune() {
		Ville ville = new Ville();
		ville.setNomCommune("ARMIX");
		assertEquals("Le nom attendu est ARMIX, le résultat obtenu est : " + ville.getNomCommune(),"ARMIX",ville.getNomCommune());
	}
	
	@Test
	public void testGetterSetterCodePostal() {
		Ville ville = new Ville();
		ville.setCodePostal("01640");
		assertEquals("Le code postal attendu est 01640, le résultat obtenu est : " + ville.getCodePostal(),"01640",ville.getCodePostal());
	}
	
	@Test
	public void testGetterSetterLibelleAcheminement() {
		Ville ville = new Ville();
		ville.setLibelleAcheminement("BOISSEY");
		assertEquals("Le libellé d'acheminement attendu est BOISSEY, le résultat obtenu est : " + ville.getLibelleAcheminement(),"BOISSEY",ville.getLibelleAcheminement());
	}
	
	@Test
	public void testGetterSetterLigne() {
		Ville ville = new Ville();
		ville.setLigne("CHANES");
		assertEquals("La ligne attendue est CHANES, le résultat obtenu est : " + ville.getLigne(),"CHANES",ville.getLigne());
	}
	
	@Test
	public void testGetterSetterLatitude() {
		Ville ville = new Ville();
		ville.setLatitude(46.3890219242);
		assertEquals("La latitude attendue est 46.3890219242, le résultat obtenu est : " + ville.getLatitude(),Double.valueOf("46.3890219242"),ville.getLatitude());		
	}
	
	@Test
	public void testGetterSetterLongitude() {
		Ville ville = new Ville();
		ville.setLongitude(4.97400450524);
		assertEquals("La longitude attendue est 4.97400450524, le résultat obtenu est : " + ville.getLongitude(),Double.valueOf("4.97400450524"),ville.getLongitude());		
	}

}
