package model;

import java.time.LocalDate;


/**
 * 
 * Classe de l'objet mot et ses attributs
 * Libelle, definition, image, date de saisie et date de modification
 *
 */
public class Mot {

	/**
	 * Libelle du mot
	 */
	private String libelle;
	
	/**
	 * Definition du mot
	 */
	private String definition;
	
	/**
	 * Url de l'image associée au mot, sous forme de String
	 */
	private String img;
	
	/**
	 * Date de saisie du mot
	 */
	private LocalDate dateSaisie;
	
	/**
	 * Date de modification du mot
	 */
	private LocalDate dateModif;
	
	/**
	 * String qui représente le URL d'une image par défaut
	 */
	public static final String DEFAULT_URL = "No_Image_Available.png";
	
	/**
	 * Constructeur qui instancie avec les attributs de base
	 * 
	 * @param pLibelle 
	 * @param pDateSaisie
	 */
	public Mot(String pLibelle, LocalDate pDateSaisie){
		
		this.libelle = pLibelle;
		this.dateSaisie = pDateSaisie;
		this.img = DEFAULT_URL;
	}

	/**
	 * Retourne le libelle du mot
	 * @return libelle
	 */
	public String getLibelle() {
		
		return libelle;
	}

	/**
	 * Set le libelle du mot
	 * @param libelle
	 */
	public void setLibelle(String libelle) {
		
		this.libelle = libelle;
	}

	/**
	 * Retourne la definition du mot
	 * @return
	 */
	public String getDefinition() {
		
		return definition;
	}
	
	/**
	 * Set la definition du mot
	 * @param definition
	 */
	public void setDefinition(String definition) {
		
		this.definition = definition;
	}

	/**
	 * Retourne le String URL de l'image
	 * @return
	 */
	public String getImg() {
		
		return img;
	}

	/**
	 * Set le String URL de l'image
	 * @param pImg
	 */
	public void setImg(String pImg) {
		
		this.img = pImg;
	}

	/**
	 * Retourne la date de saisie du mot
	 * @return
	 */
	public LocalDate getDateSaisie() {
		
		return dateSaisie;
	}

	/**
	 * Retourne la date de modif du mot
	 * @return
	 */
	public LocalDate getDateModif() {
		
		return dateModif;
	}

	/**
	 * Set la date de modif du mot
	 * @param dateModif
	 */
	public void setDateModif(LocalDate dateModif) {
		
		this.dateModif = dateModif;
	}
	
	/**
	 * Methode qui retourne le nombre de mot maximum d'une définition
	 * @return
	 */
	public int getNbMotsMaxDefinition(){
		
		return Integer.parseInt(FabriqueDictionnaire.getInstance()
				.getProperties().getProperty("nbMotsDefinition"));
	}
	
	/**
	 * Retourne l'URL de l'image par defaut, sous forme de String
	 * @return
	 */
	public String getDefaultURL(){
		
		return (this.getClass().getClassLoader().getResource(DEFAULT_URL)).toString();
	}
}
