package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

/**
 * Classe singleton qui permet d'instancier et d'accéder à 
 * un objet de type FabriqueDictionnaire
 *
 */
public class FabriqueDictionnaire {

	private static FabriqueDictionnaire instance;
	private Properties prop;
	private Map<String, Mot> dictionnaire;
	/**
	 * String indiquant le fichier duquel le dictionnaire est créé lors de
	 * la première ouverture du programme
	 */
	private static final String fichierMots = "listeMots.txt";
	/**
	 * String indiquant le path du fichier de sauvegarde
	 */
	private static final String FICHIER_SAUVEGARDE = System.getProperty("user.home") + File.separator + "dictionnaire.txt";
	
	private FabriqueDictionnaire(){
		
		lectureProprietes();
		lectureFichierMots();
	}
	
	/**
	 * Methode qui lis le fichier de mots et crée les objets Mot qu'on place 
	 * dans une map
	 */
	private void lectureFichierMots(){		
		BufferedReader bufferRead = null;
		dictionnaire = new HashMap<String, Mot>();
		
		try
		{
			if (!new File(FICHIER_SAUVEGARDE).exists())
				bufferRead = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + fichierMots), "UTF8"));
			else
				bufferRead = new BufferedReader(new FileReader(FICHIER_SAUVEGARDE));
			
			String line = "";
			while((line = bufferRead.readLine()) != null){
				
				String[] temp_vector = line.split(";");
				Mot mot;
				if(temp_vector.length > 1){
					mot = new Mot(temp_vector[0], LocalDate.parse(temp_vector[3]));
					if (temp_vector[1].compareTo("null") != 0)
						mot.setDefinition(temp_vector[1]);
					mot.setImg(temp_vector[2]);
					if (temp_vector[4].compareTo("null") != 0)
						mot.setDateModif(LocalDate.parse(temp_vector[4]));
				}else{
					
					mot = new Mot(temp_vector[0], LocalDate.now());
				}
				
				dictionnaire.put(temp_vector[0], mot);
				
			}
		}		
	
		catch (IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("IO Error");
			alert.setContentText("File error: Cannot read " + fichierMots);
			alert.showAndWait();
		}
	
		finally
		{
			try
			{
				bufferRead.close();
			}
			catch(IOException e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("IO Error");
				alert.setContentText("File error: " + fichierMots + " stream cannot be closed)");
				alert.showAndWait();
			}
		}
	}
	
	
	
	/**
	 * Methode qui lis le fichier de propriétes
	 */
	private void lectureProprietes(){
		
		prop = new Properties();
		
		InputStream in = null;
		
		try {
			
			in = this.getClass().getResourceAsStream("../properties.xml");
			prop.loadFromXML(in);
		} catch (IOException | NullPointerException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
			
				in.close();
			} catch (IOException | NullPointerException e) {
			
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Méthode statique qui retourne l'instance du singleton.
	 * Elle créée aussi l'instance du singleton si celle-ci est inexistante.
	 * @return l'instance de la classe singleton
	 */
	public static FabriqueDictionnaire getInstance(){
		
		if(instance == null){
			
			instance = new FabriqueDictionnaire();
		}
		
		return instance;
	}
	
	/**
	 * Retourne l'instance de Properties
	 * @return instance de Properties
	 */
	public Properties getProperties(){
		
		return prop;
	}
	
	/**
	 * Methode qui retourne le dictionnaire
	 * @return
	 */
	public Map getDictionnaire(){
		
		return dictionnaire;
	}
}
