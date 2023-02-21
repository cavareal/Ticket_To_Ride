package src;

import lib.Donnees;

/**
 * La classe PiocheCartesWagon est l'implémentation d'une pile abstraite dont le
 * stockage des éléments est réalisé dans un tableau.
 * @since Incrément 5
 * @ACOMPLETER
 */

 import java.util.Random;

public class PiocheCartesWagon {
    /**
	 * Le tableau interne pour stocker les cartes de la pile
	 */
	// déclarer le tableau de cartes wagon
	//CarteWagon wagon[];
	CarteWagon[] cartes;

	/**
	 * l'indice du sommet de la pile
	 */
	// déclarer l'indice du sommet de la pile
	int indiceSommet = -1;

	/**
	 * Crée une pile de cartes "wagon". On initialise le tableau de façon à
	 * pouvoir stocker 110 cartes "wagon", comme indiqué dans la règle du jeu.
	 */
	// déclarer le constructeur
	public PiocheCartesWagon(){
		this.cartes = new CarteWagon[110]; //110 cartes max dans les règles
	}

	// ACCESSEURS
	// déclarer les accesseurs en lecture pour les attributs

	CarteWagon[] getCartesWagon(){
		return this.cartes;
	}

	CarteWagon[] setCartesWagon(CarteWagon[] pioche){
		return this.cartes = pioche;
	}
	/**
	 * @return {@code vrai} si l'indice du sommet est égal à -1, {@code faux}
	 *         sinon
	 */
	// déclarer la méthode estVide
	public boolean estVide(){
		if (indiceSommet == -1){
			return true;
		}
		return false;
	}

	/**
	 * @return {@code vrai} si l'indice du sommet est égale à la taille du
	 *         tableau de cartes, {@code faux} sinon
	 */
	//déclarer la méthode estPleine
	boolean estPleine() {
		if (this.cartes.length -1 == indiceSommet){
			return true;
		}
		return false;
	}


	/**
	 * Empile une carte "wagon"
	 * @param carte l'objet {@link CarteWagon} à empiler. Erreur si la pile est
	 *            pleine
	 */
	// déclarer la méthode empile
	void empile(CarteWagon wagon) {
		if (estPleine()){
			throw new Error ("Impossible d’empiler une carte : pile pleine");
		}
		indiceSommet ++;
		cartes[indiceSommet] = wagon;
	}


	/**
	 * Renvoie la carte "wagon" située au sommet de la pile, mais ne la retire pas de
	 * la pile.
	 * @return l'objet {@link CarteWagon} présent au sommet de la pile. Erreur
	 *         si la pile est vide
	 */
	// déclarer la méthode getSommet
	CarteWagon getSommet(){
		if (estVide()){
			throw new Error ("la pioche est vide");
		}
		return cartes[indiceSommet];
	}
	/**
	 * Dépile une carte "wagon"
	 * @return l'objet {@link CarteWagon} présent au sommet de la pile. Erreur
	 *         si la pile est vide
	 */
	// déclarer la méthode depile
	public CarteWagon depile() {
		if (estVide()){
			throw new Error ("la pioche est vide");
		}
		indiceSommet --;
		return cartes[indiceSommet+1];
	}


	/**
	 * @return une représentation textuelle de la pile. On indique pour chaque
	 *         élément sa position dans la pile
	 */
	// déclarer la méthode versChaine
	String versChaine(){
		String chaine = "";
		if (estVide()){
			return "";
		}
		if (!estVide()){
			for(int i = 0; i < this.indiceSommet +1 ; i++){
				chaine += String.valueOf(i + 1) + ": " +this.cartes[i].versChaine() + "\n";
			}
		}
		return chaine;
	}
	
	/**
	 * Crée les 110 cartes "wagon" (12 par couleur et 14 locomotives) du jeu et
	 * remplit la pioche.
	 */
	//déclarer la méthode ajouteCartes
	public void ajouteCartes(){
		String tabCouleur[] = Donnees.COULEURS_WAGON;
		for(int i = 0; i < tabCouleur.length; i++){
			for(int j = 0; j < 12 ; j++){
				CarteWagon carte = new CarteWagon(tabCouleur[i]);
				empile(carte);
			}
		}
		for (int k = 0; k < 14; k++){
			CarteWagon loco = new CarteWagon(null);
			empile(loco);
		}
	}

	/**
	 * Mélange la pile de cartes "wagon" pour créer un tirage aléatoire
	 * @EXTENSION
	 */
	// déclarer la méthode melange
	public void melange(){
		Random random = new Random();
		for (int n = 0; n < 1000; n++){
			int n1 = random.nextInt(cartes.length);
			int n2 = random.nextInt(cartes.length);
			CarteWagon carte = cartes[n1];
			cartes[n1] = cartes[n2];
			cartes[n2] = carte;
		}
	}
	void melange1(){
		Random random = new Random();
		CarteWagon piocheMelange[] = new CarteWagon[110];
		int[] tabIndice = new int[110];
		for (int i = 0; i < tabIndice.length; i++){
			tabIndice[i] = i + 1;
		}
		int taille = tabIndice.length;
		for (int i = 0; i < 110 ; i ++){
			int indiceRetire = random.nextInt(taille); // indice de l'objet qu'on retire
			piocheMelange[i] = cartes[indiceRetire]; // on remplit le tableau 
			for(int j = indiceRetire; j < taille - 1;j++){
				tabIndice[j] = tabIndice[j+1]; // on enlève le trou
			}
			taille --; // on diminue la taille de tabIndice
			System.out.println(i + String.valueOf(piocheMelange[i].getCouleur()));
			}
			
		cartes = piocheMelange;
	}
	
	void melange2(){
		CarteWagon inter; 
		int indice = indiceSommet;
		for (int i = 0; i < indiceSommet + 1; i ++){
			int c = (int)(Math.random()*indice);
			inter = cartes[indice];
			cartes[indice]=cartes[c];
			cartes[c] = inter;
		}
		for (int i = 0; i < indiceSommet + 1; i ++){
			int c = (int)(Math.random()*indice);
			inter = cartes[indice];
			cartes[indice]=cartes[c];
			cartes[c] = inter;
		}
		for (int i = 0; i < indiceSommet + 1; i ++){
			int c = (int)(Math.random()*indice);
			inter = cartes[indice];
			cartes[indice]=cartes[c];
			cartes[c] = inter;
		}
	}
	

	/**
	 * Extrait un sous-ensemble de la pile de cartes "wagon"
	 * @param nombreCartes le nombre de cartes "wagon" à extraire
	 * @return un tableau d'objets {@link CarteWagon} contenant
	 *         {@code nombreCartes} cartes "wagon"
	 */
	// déclarer la méthode extraitCartes
	public CarteWagon[] extraitCartes(int nombre){
		CarteWagon[] tabExtrait = new CarteWagon[nombre];
		int c = 0;
		for (int i = 0; i < nombre; i++){
			if(estVide()){
				CarteWagon[] tabExtrait2 = new CarteWagon[c]; // copy de tableau de bonne longeur
				for (int j = 0; j < c; j++){
					tabExtrait2[j] = tabExtrait[j];
				}
				return tabExtrait2;
			}
			tabExtrait[i] = cartes[indiceSommet];
			depile();
			c++;
			}
		return tabExtrait;
	}
}
