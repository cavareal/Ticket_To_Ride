package src;

import lib.IHM;
/**
 * La classe Joueur repr?sente un joueur des Aventuriers du Rail.
 * Un joueur dispose d'un num?ro, d'une couleur, d'un nombre de wagon et d'un score.
 * A partir de l'incr?ment 7, le joueur est associ? ? une liste de cartes qui constituent sa main. 
 * @since Incr?ment 2
 * @ACOMPLETER
 */

public class Joueur {
    /**
	 * le num?ro du joueur
	 */
	// TODO d?clarer le num?ro du joueur
	int numero;

	/**
	 * la couleur du joueur
	 */
	// TODO d?clarer la couleur du joueur
	String couleur;
	
	/**
	 * le nombre de wagons restants au joueur
	 */
	// TODO d?clarer le nombre de wagons du joueur
	int nombreWagons = 45;
	
	/**
	 * le score du joueur
	 */
	// TODO d?clarer le score du joueur
	int score = 0;
	
	/**
	 * Cr?e un joueur et initialise son score ? 0 et son nombre de wagon ? 45, suivant
	 * les r?gles du jeu.
	 * A partir de l'incr?ment 7, initialise la main du joueur.
	 * @param numero le num?ro du joueur
	 * @param couleur la couleur du joueur
	 */
	// D?clarer le constructeur

	public Joueur(int numero, String couleur){
		this.numero = numero;
		this.couleur = couleur;
	}


	// ACCESSEURS
	
	public int getNumero(){
		return numero;
	}

	public String getCouleur(){
		return couleur;
	}

	public int getNombreWagons(){
		return nombreWagons;
	}

	public int getScore(){
		return score;
	}
	

	/**
	 * Ajoute un nombre de points au score du joueur.
	 * Le nombre de points ? ajouter peut ?tre n?gatif.
	 * @param nombrePoints le nombre de points ? ajouter au score du joueur
	 * @since Incr?ment 4
	 */
	// d?clarer la m?thode ajouteAuScore
	public void ajouteAuScore(int scr){
		score += scr;
	}
	
	/**
	 * Retire des wagons au joueur
	 * @param nombreWagons le nombre de wagon ? retirer
	 * @since Incr?ment 4
	 */
	// d?clarer la m?thode enleveWagons
	public void enleveWagons(int nombreDeWagonAEnlever){
		if (nombreDeWagonAEnlever >=0){
			nombreWagons -= nombreDeWagonAEnlever;
			if (nombreWagons > 45){
			nombreWagons = 45;
			}
			else if (nombreWagons < 0) {
				nombreWagons = 0;
			}
		}
		else{
			IHM ihm = new IHM();
			ihm.afficheMessageErreur("Le nombre de Wagon � enlever est n�gatif !");
		}
	}
// ---------------------- incrément 7 -----------------------------
	/**
	 * L'objet {@link ListeCartesWagon} qui repr�sente la main du joueur.
	 * @since Incr�ment 7 
	 */
	// d�clarer la main du joueur
	ListeCartesWagon mainCartesWagon = ListeCartesWagon.VIDE;

	/**
	 * @return la main (liste de cartes "wagon") du joueur
	 * @since Incr�ment 7
	 */
	// d�clarer l'accesseur en lecture de la main du joueur
	public ListeCartesWagon getMainCartesWagon(){
		return mainCartesWagon;
	}

	/**
	 * Associe une liste de cartes "wagon" au joueur
	 * @param mainCartesWagon l'objet {@link ListeCartesWagon} qui correspond �
	 *            la liste de cartes "wagon"
	 * @since Incr�ment 7
	 */
	// d�clarer l'accesseur en �criture de la main du joueur
	void setMainCartesWagon(ListeCartesWagon wagon){
		mainCartesWagon = wagon;
	}

	/**
	 * Ajoute une carte "wagon" � la main du joueur
	 * @param carte l'objet {@link CarteWagon} � ajouter
	 * @since Incr�ment 7
	 */
	// d�clarer la m�thode prendCarteWagon
	public void prendsCarteWagon(CarteWagon carte){
		mainCartesWagon = mainCartesWagon.ajoute(carte);
	}
}
