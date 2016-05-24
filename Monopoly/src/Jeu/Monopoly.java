package Jeu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Monopoly {
	private HashMap<String, Carreau> carreaux = new HashMap<>();
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        
        public void CreerPlateau(String dataFilename) {
		buildGamePlateau(dataFilename);
	}

	public Carreau AvancerJoueur(Joueur aJoueur, int aSommeDes) {
		Carreau carreau = aJoueur.getPositionCourante();
                
                int numCarreau = carreau.getNumero();
                
                aJoueur.setPositionCourante(aSommeDes + numCarreau);
                
                return aJoueur.getPositionCourante();
	}

	public void afficherEtatJoueurs() {
		throw new UnsupportedOperationException();
	}

	public Carreau getCarreau(int aNumCarreau) {
		throw new UnsupportedOperationException();
	}
        
        public void addJoueur (Joueur joueur) {
            joueurs.add(joueur);
        }
	
	private void buildGamePlateau(String dataFilename)
	{
		try{
			ArrayList<String[]> data = readDataFile(dataFilename, ",");
			
			//TODO: create cases instead of displaying
			for(int i=0; i<data.size(); ++i){
				String caseType = data.get(i)[0];
				if(caseType.compareTo("P") == 0){
					System.out.println("Propriété :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        ProprieteAConstruire prop = new ProprieteAConstruire(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Integer.parseInt(data.get(i)[4]), Integer.parseInt(data.get(i)[5]), null, null);
                                        carreaux.put(data.get(i)[2], prop);

                                }
				else if(caseType.compareTo("G") == 0){
					System.out.println("Gare :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        Gare gare = new Gare(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Integer.parseInt(data.get(i)[4]), Integer.parseInt(data.get(i)[5]), null);
                                        carreaux.put(data.get(i)[2], gare);
                                }
				else if(caseType.compareTo("C") == 0){
					System.out.println("Compagnie :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        Compagnie compagnie = new Compagnie(Integer.parseInt(data.get(i)[1]), data.get(i)[2], Integer.parseInt(data.get(i)[4]), Integer.parseInt(data.get(i)[5]), null);
                                        carreaux.put(data.get(i)[2], compagnie);
                                }
				else if(caseType.compareTo("AU") == 0){
					System.out.println("Case Autre :\t" + data.get(i)[2] + "\t@ case " + data.get(i)[1]);
                                        AutreCarreau autreCarr = new AutreCarreau(Integer.parseInt(data.get(i)[1]), data.get(i)[2]);
                                        carreaux.put(data.get(i)[2], autreCarr);
                                }
                                else {
                                        System.err.println("[buildGamePleateau()] : Invalid Data type");
                                }
                                
			}
			
		} 
		catch(FileNotFoundException e){
			System.err.println("[buildGamePlateau()] : File is not found!");
		}
		catch(IOException e){
			System.err.println("[buildGamePlateau()] : Error while reading file!");
		}
	}
	
	private ArrayList<String[]> readDataFile(String filename, String token) throws FileNotFoundException, IOException
	{
		ArrayList<String[]> data = new ArrayList<String[]>();
		
		BufferedReader reader  = new BufferedReader(new FileReader(filename));
		String line = null;
		while((line = reader.readLine()) != null){
			data.add(line.split(token));
		}
		reader.close();
		
		return data;
	}
}
