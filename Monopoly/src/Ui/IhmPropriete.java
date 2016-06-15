/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Compagnie;
import Jeu.Gare;
import Jeu.Joueur;
import Jeu.ProprieteAConstruire;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Jérémy
 */
public class IhmPropriete extends JFrame {
    private Joueur j;
    private static int propHeight = 100;
    private Color color = new Color(218,233,212);
    
    public IhmPropriete (Joueur j) {
        super();
        this.j = j;
        initUIComponents();
        afficher();
    }

    private void initUIComponents() {
        this.setTitle("Liste de vos propriétés");
        this.setLayout(new GridLayout(4,1)); //On divise la fenêtre en 4 parties :  les proprietes à construire les gares et les compagnies ainsi que le bouton fermer le fenêtre
        JPanel propsConstruire = new JPanel();
        propsConstruire.setBackground(color);
        TitledBorder titrePropConstruire = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Propriétés à Construire");
        titrePropConstruire.setTitleJustification(TitledBorder.LEFT);
        propsConstruire.setBorder(titrePropConstruire);
        this.add(propsConstruire);
        propsConstruire.setLayout(new GridLayout(j.getProprietesAconstruire().size(),1));
        if (j.getProprietesAconstruire().size() == 0) {
            propsConstruire.setLayout(new GridLayout(1,1));
            propsConstruire.add(new JLabel ("<html><font color='gray'>Vous ne possédez pas de propriétés à construire</font></html>"));
        } else {
            for (ProprieteAConstruire p : j.getProprietesAconstruire()) { //On affiche chaque propriété du joueur
                JPanel proprieteAConst = new JPanel();
                proprieteAConst.setBackground(color);
                proprieteAConst.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                propsConstruire.add(proprieteAConst);
                proprieteAConst.setLayout(new BorderLayout());
                JPanel couleur = new JPanel();
                proprieteAConst.add(couleur, BorderLayout.WEST);
                couleur.setBackground(p.getGroupe().getCouleur().getColor());
                JPanel nom = new JPanel();
                nom.setBackground(color);
                proprieteAConst.add(nom, BorderLayout.CENTER);
                JLabel nomProp = new JLabel(p.getNomCarreau());
                nom.add(nomProp);
            }
        }
        
        JPanel compagnies = new JPanel();  
        compagnies.setBackground(color);
        TitledBorder titreCompagnie = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Compagnies");
        titreCompagnie.setTitleJustification(TitledBorder.LEFT);
        compagnies.setBorder(titreCompagnie);
        this.add(compagnies);
        if (j.getCompagnies().size() == 0) { //Si le joueur n'a pas de gares on affiche un message
            compagnies.setLayout(new GridLayout(1,1));
            compagnies.add(new JLabel ("<html><font color='gray'>Vous ne possédez pas de compagnie</font></html>"));
        } else {
            compagnies.setLayout(new GridLayout(j.getCompagnies().size(),1));
            for (Compagnie c : j.getCompagnies()) { //Sinon on affiche chaque gare du joueur
                JPanel compagnie = new JPanel();
                compagnie.setBackground(color);
                compagnie.setLayout(new BorderLayout());
                JLabel nomCompagnie = new JLabel(c.getNomCarreau());
                compagnies.add(nomCompagnie, BorderLayout.CENTER);
            }
        }
        
        JPanel gares = new JPanel();
        gares.setBackground(color);
        TitledBorder titreGare = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Gares");
        titreGare.setTitleJustification(TitledBorder.LEFT);
        gares.setBorder(titreGare);
        this.add(gares);
        gares.setLayout(new GridLayout(j.getGares().size(),1));
        if (j.getGares().size() == 0) { //Si le joueur n'a pas de gares on affiche un message
            gares.setLayout(new GridLayout(1,1));
            gares.add(new JLabel ("<html><font color='gray'>Vous ne possédez pas de gare</font></html>"));
        } else {
            for (Gare g : j.getGares()) { //Sinon on affiche chaque gare du joueur
                JPanel gare = new JPanel();
                gare.setBackground(color);
                gare.setLayout(new BorderLayout());
                JLabel nomGare = new JLabel(g.getNomCarreau());
                gares.add(nomGare, BorderLayout.CENTER);
            }
        }
        
        JPanel panelQuitter = new JPanel();
        this.add(panelQuitter);
        panelQuitter.setBackground(color);
        panelQuitter.setLayout(new BorderLayout());
        JPanel panelQuitterEst = new JPanel();
        panelQuitterEst.setBackground(color);
        panelQuitter.add(panelQuitterEst, BorderLayout.EAST);
        JButton quitter = new JButton("Fermer la fenêtre");
        panelQuitterEst.add(quitter, BorderLayout.NORTH);
        
        IhmPropriete ihmProp = this;
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ihmProp.setVisible(false);
            }
        });

    }

    private void afficher() {
        setSize(500, 2*propHeight+propHeight*(j.getProprietesAconstruire().size()+j.getCompagnies().size()+j.getGares().size()));
        this.setLocationRelativeTo(null);
        setVisible(true);                     

    }
    
}
