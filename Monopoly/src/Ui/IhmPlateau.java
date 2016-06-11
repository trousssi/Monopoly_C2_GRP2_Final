/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Carreau;
import Jeu.Carte;
import Jeu.Joueur;
import Jeu.Resultat;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author vivierlo
 */
public class IhmPlateau extends JPanel{
    private int x, y;
    
    private Timer timer;
    private BufferedImage fondPlateau;
    Observateur observateur;
    
    private HashMap<String, BufferedImage> pions; //Liste des pions
    private HashMap<String, Integer> joueurs; //Liste des joueurs avec String: nom joueur et Integer: numéro Carreau courant
    private int[] nbJoueursParCases;
    private String nomJoueurCourant;
    
    private boolean animationEnCours;
    
    public IhmPlateau(HashSet<String> noms) throws InterruptedException   {
        super();
        pions = new HashMap<>();
        joueurs = new HashMap<>();
        nbJoueursParCases = new int[40];
        
        String couleur[] ={"Rouge", "Bleu", "Vert", "Jaune", "Violet", "Orange"};
        int numCouleur = 0;
        //Initialisation des joueurs
        for (String nomJ : noms) {            
            try {
                joueurs.put(nomJ, 1); //positionnés sur le carreau de départ
                pions.put(nomJ, ImageIO.read(new File("src/Data/pion" + couleur[numCouleur] + ".png")));
                numCouleur++;
            } catch (IOException ex) {
                Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
        
        //Initialisation du nombre de joueurs par case
        this.initListeCase();
    }
    
    //private void trouveNbJoueursParCase() {
        //for (String nomJ : joueurs.keySet()) {
            //nbJoueursParCases[joueurs.get(nomJ)]++;//On ajoute 1 pour la case où se trouve un joueur
        //}
    //}
    
    private void initListeCase() {
        for(int i= 0; i<40; i++) {
            nbJoueursParCases[i] = 0;
        }
    }
    
    /**
    * Dessine le contenu du canvas, c'est-à-dire l'icone
    * @param g un contexte graphique
    */
    @Override
    public void paintComponent(Graphics g) {
        Dimension d = new Dimension(900, 900);
        super.setSize(d);
        super.paintComponent(g);
        
        try {
            fondPlateau = ImageIO.read(new File("src/Data/plateau.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(IhmPlateau.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g.drawImage(fondPlateau, 0, 0, (ImageObserver) observateur); //Background
        
        this.initListeCase();//Il y 0 joueurs à dessiner sur la première case
        Point p;
        System.out.println("anim = "+ animationEnCours);
        if (!animationEnCours) {//Tous les joueurs sont su la première case
            for (String nomJ : joueurs.keySet()) {
                nbJoueursParCases[joueurs.get(nomJ)-1]++;//On ajoute un joueur à dessiner
                p = trouveCoordonneesCase((nomJ), 0);
                p = positionnePionSurCase(joueurs.get(nomJ), nbJoueursParCases[joueurs.get(nomJ)-1], p.x, p.y);
                
                g.drawImage(pions.get(nomJ), p.x, p.y, (ImageObserver) observateur);    
            }
        }
                
        else if (animationEnCours) {
            System.out.println("ANIMATION ! ");
            for (String nomJ : joueurs.keySet()) {
                nbJoueursParCases[joueurs.get(nomJ)-1]++;//On va dessiner un joueur sur la bonne case
                if (nomJoueurCourant.equals(nomJ)) {
                    p = trouveCoordonneesCase((nomJ), 1);//On met le mode avancer si le joueur doit bouger
                    //nbJoueursParCases[joueurs.get(nomJ)-1]--;
                }
                else {
                    p = trouveCoordonneesCase((nomJ), 0);                                   
                }
                System.out.println("J : "+(joueurs.get(nomJ)-1));
                p = positionnePionSurCase(joueurs.get(nomJ), nbJoueursParCases[joueurs.get(nomJ)-1], p.x, p.y);
                
                g.drawImage(pions.get(nomJ), p.x, p.y, (ImageObserver) observateur);    
            }
        }
        
    }
    
    
    
    //Donne les coordonnées précises du pion après qu'il ai avancé 
    //public Point avancerPion(HashMap<String, Integer> j) throws InterruptedException {
    public Point trouveCoordonneesCase(String nomJ, int mode) { 
        int BASE = 786; // Coordonnées x et y de la case départ pour le 1er pion
        int x = 0, y = 0;        
        int numCase = joueurs.get(nomJ);
        
        if (mode == 1) { //Si on doit avancer
            numCase = this.numCarreauSuivant(numCase);//La case doit être la suivante
            joueurs.replace(nomJ, numCase);//Le joueur doit être mit à jour
        }
        
        if(numCase == 1) {//CASE DEPART
            x = BASE;// Pour le joueur courant x = 786
            y = BASE;// y = 786;
        }
        else if(numCase <= 10) { //LIGNE BAS
            x = BASE-74*(numCase-1);
            y = 844;
        }
        else if (numCase == 11) {//VISITE PRISON 
            x = 5;
            y = BASE;
        }
        else if (numCase <= 20) {//LIGNE GAUCHE
            x = 16;
            y = BASE-74*(numCase-11);
        }
        else if (numCase == 21) {//PARC GRATUIT
            x = 16;
            y = 16;
        }
        else if (numCase <= 30) {//LIGNE HAUT
            x = 120+74*(numCase-22);
            y = 16;
        }
        else if (numCase == 31) {//ALLER EN PRISON
            x = 786;
            y = 16;
        }
        else if (numCase <= 40) {//LIGNE DROITE
            x = 841;
            y = 123+74*(numCase-32);
        }
        
        return new Point(x, y);
    }
    
    private int numCarreauSuivant(int numCar) {
        if(numCar++ % 41  == 0) 
            return 1;
        else return numCar++ % 41;
    }
    
    //Sélectionne les coordonnées d'affichage du pion sur la case
    private Point positionnePionSurCase(int numCase, int nbJoueurCase, int xPion1, int yPion1) {
        int ESPACEMENT = 2; // Espacement en pixel entre 2 pion cote à cote
        int LARGEUR_PION = 21; // Largeur d'un pion en pixels
        int EMPILEMENT = 13; // Décalage en pixels entre deux pions qui s'empilent vers le bas
        int HAUTEUR_PION = 26; // Hauteur d'un pion en pixels
        int CHANGE_LIGNE = 3; // Conctante définissant le nombre de pions par ligne 
        
        if(numCase == 1) {//CASE DEPART 
            for (int i=1; i<nbJoueurCase; i++) {
                xPion1 += ESPACEMENT + LARGEUR_PION;
                if (nbJoueurCase >= CHANGE_LIGNE) {  
                    yPion1 += ESPACEMENT + HAUTEUR_PION; 
                }  
            }
            
        } else if(numCase <= 10) { //LIGNE BAS
            for (int i=1; i<nbJoueurCase; i++) {
                yPion1 += EMPILEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) {   
                    xPion1 += LARGEUR_PION + ESPACEMENT; 
                }  
            }

        } else if (numCase == 11) {//VISITE PRISON 
            for (int i=1; i<nbJoueurCase; i++) {
                yPion1 += EMPILEMENT;
            }
            
        } else if (numCase <= 20) {//LIGNE GAUCHE
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += LARGEUR_PION + ESPACEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                    yPion1 += EMPILEMENT;
                }  
            }
            
        } else if (numCase == 21) {//PARC GRATUIT
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += ESPACEMENT + LARGEUR_PION;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   yPion1 += ESPACEMENT + HAUTEUR_PION; 
                }  
            }
            
        } else if (numCase <= 30) {//LIGNE HAUT
            for (int i=1; i<nbJoueurCase; i++) {                    
                yPion1 += EMPILEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   xPion1 += LARGEUR_PION + ESPACEMENT; 
                }  
            }
            
        } else if (numCase == 31) {//ALLER EN PRISON
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += ESPACEMENT + LARGEUR_PION;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   yPion1 += ESPACEMENT + HAUTEUR_PION;
                }  
            }
            
        } else if (numCase <= 40) {//LIGNE DROITE
            for (int i=1; i<nbJoueurCase; i++) {                    
                xPion1 += LARGEUR_PION + ESPACEMENT;
                if (nbJoueurCase >= CHANGE_LIGNE) { 
                   yPion1 += EMPILEMENT;
                }  
            }
        }
        
        
        return new Point(xPion1,yPion1);
    }

    public void recupDonneesJoueur(Joueur j, Carreau positionCourante, Carreau anciennePosition) {
        //numCarreauCourant = anciennePosition.getNumero();
        nomJoueurCourant = j.getNom();
        int numCarreauDestination = positionCourante.getNumero();
        
        animationEnCours = true;
        //Tant que le joueur n'est pas sur la bonne case
        System.out.println("nomJoueurCourant = " + nomJoueurCourant + "numCarreauDestination" + numCarreauDestination);
        timer = new Timer(+400, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Test");
                    if (joueurs.get(nomJoueurCourant) == numCarreauDestination) {                        
                        System.out.println("FinTest");
                        timer.stop();
                        animationEnCours = false;
                    }
                    else {
                        repaint();
                    } 
                }
        });
        if (joueurs.get(nomJoueurCourant) != numCarreauDestination) {
            System.out.println("START Timer");
            timer.start();
        }
    }
}
