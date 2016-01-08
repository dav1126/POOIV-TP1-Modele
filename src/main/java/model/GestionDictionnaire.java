package model;

import javafx.collections.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe qui sert a g�rer le dictionnaire
 *
 */
public class GestionDictionnaire extends Observable{
	
	/**
	 * Liste des r�sultats de la recherche
	 */
	public ArrayList<Mot> listeResultats;
	
	/**
	 * Mot s�lectionn� par l'utilisateur dans le ListView affichant les r�sultats de recherche
	 */
	public Mot motSelectionne;
	
	/**
	 * Liste des libell� des mots qui sont en consultation
	 */
	public ArrayList<String> listeMotsEnConsultation = new ArrayList<>();
	
	/**
	 * String indiquant le nom du fichier de sauvegarde
	 */
	private static final String FICHIER_SAUVEGARDE = System.getProperty("user.home") + File.separator + "dictionnaire.txt";
	
	/**
	 * Flag si il y a modification dans le dictionnaire
	 */
		public Boolean flagModif = false;
	
	/**
	 * Methode qui ajoute un mot recu en param�tre dans le dictionnaire
	 * 
	 * @param pMot
	 */
	public void ajouterMot(String pMot){
	
		Mot mot = new Mot(pMot, LocalDate.now());
		FabriqueDictionnaire.getInstance().getDictionnaire().put(pMot, mot);
	}
	
	/**
	 * Methode qui supprime un mot recu en param�tre du dictionnaire 
	 *
	 * @param pMot
	 */
	public void supprimerMot(String pMot){
		
		String msg = null;
		
		if(FabriqueDictionnaire.getInstance().getDictionnaire().containsKey(pMot)){
			
			FabriqueDictionnaire.getInstance().getDictionnaire().remove(pMot);
			msg = "Mot supprim�";
		}else{
			
			msg = "Mot introuvable";
		}
		
	}
	
	/**
	 * M�thode qui compte le nombre de mots de la d�finition entr� en param�tre
	 * @param pDef
	 * @return
	 */
	public Boolean validerDefinition(String pDef){
		
		Boolean ok = false;
		int motsMax = Integer.parseInt(FabriqueDictionnaire.getInstance().getProperties().getProperty("nbMotsDefinition"));
		if(compterNbMot(pDef) <= motsMax){
			
			ok = true;
		}
		
		return ok;
	}
	
	/**
	*Compte le nombre de mots dans le String recu en parametre
	*/
	public int compterNbMot (String s)
	{
		String trim = s.trim();
			if (trim.isEmpty())
				return 0;
			return trim.split("\\s+").length;
	}
	
	/**
	 * Methode qui v�rifie si le mot existe dans le dictionnaire
	 * 
	 * @param pMot
	 * @return
	 */
	public Boolean rechMotExiste(String pMot){
		
		if(FabriqueDictionnaire.getInstance().getDictionnaire().containsKey(pMot)){
			
			return true;
		}
		
		else 
			
			return false;
	}
	
	/**
	 * M�thode qui recherche dans le dictionnaire le mot exacte en param�tre 
	 * @param pMot
	 * @return
	 */
	public void rechMotExact(String pMot){
		
		Mot mot = null;
		listeResultats = new ArrayList<>();
		
		mot = (Mot) FabriqueDictionnaire.getInstance().getDictionnaire().get(pMot);
		
		if(mot != null){
			
			listeResultats.add(mot);
		}
	}
	
	/**
	 * Methode qui retourne les mots trouv�s dans le dictionnaire qui contienne 
	 * la s�quence du mot recu en param�tre
	 * @param regex
	 * @return
	 */
	public void rechMotPartiel(String regex){
		
		Pattern p = Pattern.compile(regex);
		listeResultats = new ArrayList<>();
		Set<String> mapKeys = FabriqueDictionnaire.getInstance().getDictionnaire().keySet();
		Iterator<String> iter = mapKeys.iterator();
		
		while(iter.hasNext()){
			
			String mot = iter.next();
			Matcher m = p.matcher(mot);
			if(m.matches()){
				
				listeResultats.add((Mot) FabriqueDictionnaire.getInstance().getDictionnaire().get(mot));
			}
		}
	}
	
	/**
	 * Methode qui supprime de la liste des mots situ� apr�s la date 
	 * entr�e en param�tre 
	 *  
	 * @param pDate
	 */
	public void rechAvantDateSaisie( LocalDate pDate){
		
		ArrayList<Mot> liste = new ArrayList<>();
		
		for(Mot m: listeResultats){
		
			if(m.getDateSaisie().isBefore(pDate)){
				
				liste.add(m);
			}	
		}
		
		listeResultats = liste;
	}
	
	/**
	 * Methode qui supprime de la liste des mots situ� avant la date 
	 * entr�e en param�tre
	 * 
	 * @param pDate
	 */
	public void rechApresDateSaisie(LocalDate pDate){
		
		ArrayList<Mot> liste = new ArrayList<>();
		
		for(Mot m: listeResultats){
		
			if(m.getDateSaisie().isAfter(pDate)){
				
				liste.add(m);
			}
		}
		
		listeResultats = liste;
	}
	
	/**
	 * Methode qui supprime de la liste des mots situ� avant la date 
	 * entr�e en param�tre
	 * 
	 * @param pDate
	 */
	public void rechApresDateModif(LocalDate pDate){
		
		ArrayList<Mot> liste = new ArrayList<>();
		
		for(Mot m: listeResultats){
			
			if(m.getDateModif() != null){
			
				if(m.getDateModif().isAfter(pDate)){
					
					liste.add(m);
				}
			}	
		}
		
		listeResultats = liste;
	}
	
	/**
	 * Methode qui supprime de la liste des mots ceux qui n'ont pas d'image
	 * 
	 * @param pDate
	 */
	public void rechAvantDateModif(LocalDate pDate){
		
		ArrayList<Mot> liste = new ArrayList<>();
		
		for(Mot m: listeResultats){
			
			if(m.getDateModif() != null){
			
				if(m.getDateModif().isBefore(pDate)){
					
					liste.add(m);
				}
			}	
		}
		
		listeResultats = liste;
	}
	
	/**
	 * Methode qui supprime de la liste des mots entr�s en param�tre situ� avant la date 
	 * entr�e en param�tre
	 * 
	 * @param pDate
	 */
	public void rechSelonImage(){
			
			ArrayList<Mot> liste = new ArrayList<>();
			
			for(Mot m: listeResultats){
				System.out.println(m.getImg());
				if(!m.getImg().matches(".*"+Mot.DEFAULT_URL)){
						
						liste.add(m);
				}	
			}
			
			listeResultats = liste;
			
		}
	
	/**
	 * Methode qui notifie les observateurs
	 */
	public void notifyObs(ArrayList<Mot> pListe){
		
		setChanged();
		notifyObservers(pListe);
	}
	
	/**
	 * Permet d'ajouter le libell� du mot pass� en param�tre dans la liste de mot en consultation
	 * @param pMot
	 */
	public void ajouterMotEnConsultation(Mot pMot)
	{
		listeMotsEnConsultation.add(motSelectionne.getLibelle());
	}
	
	/**
	 * Permet de supprimer le libell� du mot pass� en param�tre de la liste de mot en consultation
	 * @param pMot
	 */
	public void supprimerMotEnConsultation(Mot pMot)
	{
		listeMotsEnConsultation.remove(pMot.getLibelle());
	}
	
	public void sauvegardeFichier() throws IOException
	{
		BufferedWriter bufferWrite = null;	
				
				bufferWrite= new BufferedWriter(new FileWriter(FICHIER_SAUVEGARDE, true));
				Set<Map.Entry<String, Mot>> mapEntries = FabriqueDictionnaire.getInstance().getDictionnaire().entrySet();
				Iterator<Map.Entry<String, Mot>> iter = mapEntries.iterator();
				
				while(iter.hasNext())
				{	
					Mot mot = iter.next().getValue();
					String formattedImagePath = mot.getImg().replace("\\", "\\\\");// We have to replace the backslash (\) with a double backslash (\\) because it is an escape character
					//The string to be saved in the file (CSV format)
						String motSaveString = mot.getLibelle() + ";" + mot.getDefinition() + ";" + formattedImagePath + ";" + mot.getDateSaisie() +";" + mot.getDateModif();
					bufferWrite.write(motSaveString);
					bufferWrite.newLine();
				}
				
				bufferWrite.close();
			
	}
	/**
	 * M�thode servant � charger le dictionnaire en m�moire
	 */
	public void loadDictionnaire()
	{
		FabriqueDictionnaire.getInstance();
	}
}
