/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author vivierlo
 */
public class IhmFinTour extends JFrame {
    private Color fond = new Color(218,233,212);
    private final int HAUTEUR_BASE = 400;
    private final int LONGUEUR_PAR_JOUEUR = 200;
    private Observateur observateur;
    private ArrayList<Joueur> joueurs;
    private HashMap<Joueur, Color> couleurJoueurs = new HashMap<>();
    private Color couleur[] = {new Color(244,28,37), new Color(8,48,82), new Color(37,152,14), new Color(90,36,0), new Color(209,1,255), new Color(255,104,0)};
    private Joueur joueurCourant = null;
    private JPanel panelStats;
    private ArrayList<JButton> boutonsProprietes = new ArrayList<>();

    public IhmFinTour(int numTour, ArrayList<Joueur> joueurs, HashMap<String, String> couleurJoueurs) {
        super();
        this.joueurs = joueurs;
        for (int i = 0; i< joueurs.size(); i++) {
            //Transfert des couleurs en String en couleurs RGB hexadecimal
            String couleurTemp = couleurJoueurs.get(joueurs.get(i).getNom()); 
            Color couleurJoueur = null;
            switch (couleurTemp) {
                case "Rouge":   couleurJoueur = this.couleur[0];break;
                case "Bleu" :   couleurJoueur = this.couleur[1];break; 
                case "Vert" :   couleurJoueur = this.couleur[2];break;
                case "Marron" : couleurJoueur = this.couleur[3];break;
                case "Violet" : couleurJoueur = this.couleur[4];break;  
                case "Orange" : couleurJoueur = this.couleur[5];break;   
            }
            this.couleurJoueurs.put(joueurs.get(i), couleurJoueur);
            boutonsProprietes.add(new JButton("Voir Propriétés"));
        }
        afficher(numTour); 
        initUIComponents();
    }
    
    public void initUIComponents() {
        this.setBackground(fond);
        this.getContentPane().setBackground(fond);

        this.setSize(LONGUEUR_PAR_JOUEUR*joueurs.size(),HAUTEUR_BASE);
        this.setLayout(new BorderLayout());
        
        panelStats = new JPanel();
        panelStats.setLayout(new GridLayout(1,joueurs.size())); //Créé un "tableau" d'affichage pour chaque joueurs
        this.add(panelStats, BorderLayout.CENTER);
        
        for (int i = 0; i< joueurs.size(); i++) { //Création du tableau d'affichage pour chaque joueur
            joueurCourant = joueurs.get(i);
            JPanel panelJoueurCourant = new JPanel();
            panelJoueurCourant.setBackground(fond);
            panelJoueurCourant.setLayout(new GridLayout(5,1));
            if (i == 0) { // Gère les bordures pour qu'il n'y ai pas 2 tracés sur la même ligne
                panelJoueurCourant.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
            } else {
                panelJoueurCourant.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 3, Color.BLACK));
            }
            //Affichage du nom
            JLabel labelNom = new JLabel(joueurCourant.getNom());
            labelNom.setForeground(couleurJoueurs.get(joueurCourant));
            labelNom.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            labelNom.setHorizontalAlignment(JLabel.CENTER);
            labelNom.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelNom);
            
            //Affichage du Cash
            JLabel labelCash = new JLabel("Cash = " + joueurCourant.getCash());
            labelCash.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            labelCash.setHorizontalAlignment(JLabel.CENTER);
            labelCash.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelCash);
            
            //Affichage de la position courante
            JLabel labelPosition = new JLabel("<html>Position: <br>" + joueurCourant.getPositionCourante().getNomCarreau() +"</html>");
            labelPosition.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            labelPosition.setHorizontalAlignment(JLabel.CENTER);
            labelPosition.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelPosition);
            
            JLabel labelCarte  = new JLabel();
            switch (joueurCourant.getCartesPrison().size()) {
                case 0: labelCarte.setText("<html> 0 carte de <br>sortie de prison </html>"); break;
                case 1: labelCarte.setText("<html> 1 carte de <br>sortie de prison </html>"); break;
                case 2: labelCarte.setText("<html> 2 cartes de <br>sortie de prison </html>"); break;
                case 3: labelCarte.setText("<html> 3 cartes de <br>sortie de prison </html>"); break;
            }
            labelCarte.setHorizontalAlignment(JLabel.CENTER);
            labelCarte.setVerticalAlignment(JLabel.CENTER);
            panelJoueurCourant.add(labelCarte);
            
            boutonsProprietes.get(i).setBackground(couleurJoueurs.get(joueurCourant));
            boutonsProprietes.get(i).setForeground(Color.WHITE);
            panelJoueurCourant.add(boutonsProprietes.get(i));
            panelStats.add(panelJoueurCourant);
               
        }
        
        //Gestion des boutons afficherProprietes
        boutonsProprietes.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IhmPropriete ihmProp = new IhmPropriete(joueurs.get(0));
                ihmProp.setTitle("Liste des propriétés de " + joueurs.get(0).getNom() + ".");
            }
        });
        boutonsProprietes.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IhmPropriete ihmProp = new IhmPropriete(joueurs.get(1));
                ihmProp.setTitle("Liste des propriétés de " + joueurs.get(1).getNom() + ".");
            }
        });
        if (joueurs.size() > 2) {
            boutonsProprietes.get(2).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IhmPropriete ihmProp = new IhmPropriete(joueurs.get(2));
                    ihmProp.setTitle("Liste des propriétés de " + joueurs.get(2).getNom() + ".");
                }
            });
        }
        if (joueurs.size() > 3) {
            boutonsProprietes.get(3).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IhmPropriete ihmProp = new IhmPropriete(joueurs.get(3));
                    ihmProp.setTitle("Liste des propriétés de " + joueurs.get(3).getNom() + ".");
                }
            });
        }
        if (joueurs.size() > 4) {
            boutonsProprietes.get(4).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IhmPropriete ihmProp = new IhmPropriete(joueurs.get(4));
                    ihmProp.setTitle("Liste des propriétés de " + joueurs.get(4).getNom() + ".");
                }
            });
        }
        if (joueurs.size() == 6) {
            boutonsProprietes.get(5).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    IhmPropriete ihmProp = new IhmPropriete(joueurs.get(5));
                    ihmProp.setTitle("Liste des propriétés de " + joueurs.get(5).getNom() + ".");
                }
            });
        }
        
        
        JButton quitter = new JButton("Fermer cette fenêtre");
        this.add(quitter,BorderLayout.SOUTH);
        
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                observateur.retourAuJeu();
            }
        });
        
    }

    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    public void afficher(int numTour) {
       this.setTitle("Statistiques de fin du tour n°" + numTour);
       this.setVisible(true);
       this.setBackground(fond);
       this.setAlwaysOnTop(true);
       this.setLocationRelativeTo(null);
    }
    
}
