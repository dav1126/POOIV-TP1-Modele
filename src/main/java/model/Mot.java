package model;

import java.time.LocalDate;


/**
 * 
 * Classe de l'objet mot et ses attributs
 * Libelle, definition, image, date de saisie et date de modification
 *
 */
public class Mot {

	private String libelle;
	private String definition;
	private String img;
	private LocalDate dateSaisie;
	private LocalDate dateModif;
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

	public String getLibelle() {
		
		return libelle;
	}

	public void setLibelle(String libelle) {
		
		this.libelle = libelle;
	}

	public String getDefinition() {
		
		return definition;
	}
	
	/**
	 * Maximum de mots defini par la propriété
	 * @param definition
	 */
	public void setDefinition(String definition) {
		
		this.definition = definition;
	}

	public String getImg() {
		
		return img;
	}

	public void setImg(String pImg) {
		
		this.img = pImg;
	}

	public LocalDate getDateSaisie() {
		
		return dateSaisie;
	}

	public LocalDate getDateModif() {
		
		return dateModif;
	}

	public void setDateModif(LocalDate dateModif) {
		
		this.dateModif = dateModif;
	}
	/**
	 * methode qui retourne le nombre de mot maximum d'une d�finition
	 * @return
	 */
	public int getNbMotsMaxDefinition(){
		
		return Integer.parseInt(FabriqueDictionnaire.getInstance()
				.getProperties().getProperty("nbMotsDefinition"));
	}
	
	public String getDefaultURL(){
		
		return (this.getClass().getClassLoader().getResource(DEFAULT_URL)).toString();
	}
}
