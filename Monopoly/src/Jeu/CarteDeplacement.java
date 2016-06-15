/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jeu;

/**
 *
 * @author Jérémy
 */
public class CarteDeplacement extends Carte {
    
    private Carreau deplacement;
    
    public CarteDeplacement(String nom, Carreau deplacement) {
        super(nom);
        this.deplacement = deplacement;
    }

    @Override
    public Resultat action(Joueur j) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        renvoyerNom(res);
        res.setDeplace(true);
        if (this.getDeplacement().getNumero() == -3) { //On fait reculer le joueur de 3 carreaux
            res.setDeplacement(-3);
            //j.setPositionCourante(monopoly.getCarreau((j.getPositionCourante().getNumero())-3));
        } else  if (this.getDeplacement().getNumero() == 2) { //On envoie le joueur à Belleville
            j.setPositionCourante(this.getDeplacement());
        } else if (this.getDeplacement().getNumero() < j.getPositionCourante().getNumero()) { //On calcule le carreau d'arrivée si le joueur doit passer par la case départ
            int avancer = (40+this.getDeplacement().getNumero()-(j.getPositionCourante().getNumero()));
            res.setDeplacement(avancer);
            //this.avancerJoueur(j, avancer);
        } else if (this.getDeplacement().getNumero() > j.getPositionCourante().getNumero()){ //On calcule le carreau d'arrivée
            int avancer = this.getDeplacement().getNumero() - j.getPositionCourante().getNumero();
            res.setDeplacement(avancer);
            //this.avancerJoueur(j, this.getDeplacement().getNumero()-j.getPositionCourante().getNumero());
        }
        return res;
    }

    public Carreau getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Carreau deplacement) {
        this.deplacement = deplacement;
    }
    
    
    
    
    
}
