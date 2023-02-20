package src;
/**
 * La classe ListeCartesWagon est l'implémentation d'une liste chaînée de cartes
 * "wagon" dans sa version récursive.
 * @since Incrément 7
 * @ACOMPLETER
 */
public class ListeCartesWagon {
    
	/**
	 * Représentation d'une liste vide
	 */
	static final ListeCartesWagon VIDE = new ListeCartesWagon(null, null);

	/**
	 * l'objet {@link CarteWagon} en tête de la liste de cartes wagon
	 */
	CarteWagon tete;

	/**
	 * l'objet {@link ListeCartesWagon} qui constitue le reste de la liste de
	 * cartes wagon
	 */
	ListeCartesWagon reste;

	/**
	 * Crée une liste de cartes "wagon"
	 * @param tete l'objet {@link CarteWagon} à insérer en tête
	 * @param reste l'objet {@link ListeCartesWagon} qui constitue le reste
	 */
	// déclarer le constructeur
	ListeCartesWagon(CarteWagon tete, ListeCartesWagon reste){
		this.tete = tete; 
		this.reste = reste;
	}

	// ACCESSEURS
	
	// déclarer les accesseurs en lecture pour les attributs
	CarteWagon getTete() {
		return tete;
	}

	ListeCartesWagon getReste() {
		return reste;
	}

	/**
	 * @return {@code vrai} si la liste est la liste vide, {@code faux} sinon
	 */
	// déclarer la méthode estVide

	boolean estVide() {
		if ( this == ListeCartesWagon.VIDE){
			return true ;
		}
		return false;
	}

	/**
	 * Ajoute une carte "wagon" en tête de la liste
	 * @param tete l'objet {@link CarteWagon} à ajouter
	 * @return une nouvelle liste dont la tête est {@code tete}
	 */
	// déclarer la méthode ajoute
	ListeCartesWagon ajoute(CarteWagon carte) {
		if (estVide()){
			return new ListeCartesWagon(carte,ListeCartesWagon.VIDE );
		}
		return new ListeCartesWagon (carte, new ListeCartesWagon(this.getTete(), this.getReste()));
	}


	/**
	 * @return une représentation textuelle de la liste. Chaque élement de la
	 *         liste est séparé par une virgule.
	 */

	// déclarer la méthode versChaine

	String versChaine(){
		String chaine = "";  
		if (estVide()){
			return chaine;
		}
		else if (this.reste == ListeCartesWagon.VIDE){
			return this.tete.versChaine()+ this.getReste().versChaine();
		}
		return this.tete.versChaine() + ", "+ this.getReste().versChaine();
	}

	/**
	 * Recherche le nombre d'occurrences d'une couleur donnée.
	 * @param couleur la couleur recherchée; la valeur {@code null} correspond
	 *            aux locomotives
	 * @return le nombre de cartes "wagon" correspondant à la couleur
	 * @since Incrément 7
	 */
	// déclarer la méthode getNombreOccurrences

	int getNombreOccurrences(String couleur){
		if (estVide()){
			return 0 ;
		}
		if (this.tete.getCouleur() == couleur){
			return 1 + this.getReste().getNombreOccurrences(couleur);
		}
		return this.getReste().getNombreOccurrences(couleur);
	}
}
