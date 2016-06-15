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
public class CarteArgent extends Carte {
    
    private int prix;
    
    public CarteArgent(String nom, int prix) {
        super(nom);
        this.prix = prix;
    }

    @Override
    public Resultat action(Joueur j) {
        Resultat res = new Resultat();
        res.setNomCarreau(j.getPositionCourante().getNomCarreau());
        renvoyerNom(res);
        if (this.getPrix() > 0) { //On crédite le joueur de la valeur de la prix de la carte
            j.crediter(this.getPrix());
        } else if (this.getPrix() < -1){    //On fait payer le joueur de la valeur de la prix de la carte
            j.payer(Math.abs(this.getPrix()));
        } else if (this.getPrix() == -1) {  //On met à true la valeur anniversaire dans resultat
            res.setAnniversaire(true);
        } else {
            System.out.println("ERREUR CARTE AR");
        }
        
        return res;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
    
    
    
}
