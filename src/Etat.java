package src;

/**
 * La classe Etat représente un état des Etats-Unis à dessiner sur le plateau de jeu.
 * Chaque état dispose d'un nom et d'un contour (ensemble de coordonnées permettant 
 * de dessiner la carte).
 * @since Incrément 1
 * @FOURNI
 */


public class Etat {
    
	/**
	 * le nom de l'état
	 */
	String nom;
	
	/**
	 * les coordonnées des points permettant de dessiner l'état
	 */
	String contour;

	/**
	 * Crée un état
	 * @param nom le nom de l'état
	 * @param contour les coordonnées des points à dessiner
	 */
	Etat(String nom, String contour) {
		this.nom = nom;
		this.contour = contour;
	}

	// ACCESSEURS
	/**
	 * @return le nom de l'état
	 * @FOURNI
	 */
	String getNom() {
		return this.nom;
	}

	/**
	 * @return les coordonnées des points permettant de dessiner l'état
	 * @FOURNI
	 */
	String getContour() {
		return this.contour;
	}


}
