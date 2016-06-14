/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Jeu.Joueur;
import Jeu.ProprieteAConstruire;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Jérémy
 */
public class IhmConstruire  extends JFrame{
    private ArrayList<ProprieteAConstruire> proprietes;
    private Joueur j;
    private Observateur observateur;
    private IhmJeu ihmJeu;
    private static int propHeight = 50;
    
    public IhmConstruire(Joueur j, ArrayList<ProprieteAConstruire> proprietes, IhmJeu ihmJeu) {
        super();
        this.proprietes = proprietes;
        this.j = j;
        this.ihmJeu = ihmJeu;
        if (proprietes != null) {
            initUIComponents(proprietes);
            afficher();
        }
    }

    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    
    private void initUIComponents(ArrayList<ProprieteAConstruire> p) {
        this.setLayout(new GridLayout(p.size()+1,1));
        for (int i=0; i<p.size(); i++) {
            JPanel propriete = new JPanel();
            propriete.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            this.add(propriete);
            propriete.setLayout(new BorderLayout());
            JPanel couleur = new JPanel();
            propriete.add(couleur, BorderLayout.WEST);
            couleur.setBackground(p.get(i).getGroupe().getCouleur().getColor());
            JPanel nom = new JPanel();
            propriete.add(nom, BorderLayout.CENTER);
            JLabel nomProp = new JLabel(p.get(i).getNomCarreau());
            nom.add(nomProp);
            JPanel prix = new JPanel();
            propriete.add(prix, BorderLayout.EAST);
            JLabel prixProp = new JLabel(Integer.toString(p.get(i).getPrixMaison()));
            prix.add(prixProp);
            propriete.setName(p.get(i).getNomCarreau());
            
            
            propriete.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String nomPropriete = propriete.getName();
                    for (ProprieteAConstruire p : proprietes) {
                        if (nomPropriete.equals(p.getNomCarreau())) {
                            observateur.construire(p, j);
                            //System.out.println("PROP CONSTRUITE" + p.getNomCarreau());
                            refresh();
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }

            });
        }
        JPanel panelQuitter = new JPanel();
        this.add(panelQuitter);
        panelQuitter.setLayout(new BorderLayout());
        JButton quitter = new JButton ("Quitter");
        panelQuitter.add(quitter, BorderLayout.EAST);
        JFrame ihmConstruire = this;
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ihmConstruire.setVisible(false);
            }
        });
    }
         
    private void refresh() {
        this.setVisible(false);
        ihmJeu.refreshConst(j);
    }

    public void afficher() {
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setSize(500, propHeight+propHeight*proprietes.size());
        setVisible(true);                     

    }
}