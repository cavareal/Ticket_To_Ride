package src;
/**
 * La classe Ville représente une ville du jeu des Aventuriers du Rail.
 * Une ville dispose d'un nom, d'une abscisse et d'une ordonnée pour l'affichage
 * sur la carte.
 * @since Incrément 1
 * @ACOMPLETER
 */
public class Ville {
    
	int numero;
	
	/**
	 * le nom de la ville
	 */
	// Déclarer le nom
	String nom;
	
	/**
	 * l'abcisse des coordonnées de la ville
	 */
	// Déclarer l'abscisse
	double x;
	/**
	 * l'ordonnée des coordonnées de la ville
	 */
	// Déclarer l'ordonnee
	double y;
	/**
	 * Crée une ville
	 * @param nom le nom de la ville
	 * @param x l'abcisse de la ville
	 * @param y l'ordonnée de la ville
	 */
	// Déclarer le constructeur
	Ville(String nom, double x, double y, int numero)
	{
		this.nom = nom;
		this.x = x;
		this.y = y;
		this.numero = numero;
	}

	// ACCESSEURS
	public String getNom() {
		// Modifier l'accesseur pour qu'il renvoie le nom de la ville
		return this.nom;
	}

	public double getX() {
		// Modifier l'accesseur pour qu'il renvoie l'abscisse de la ville
		return this.x;
	}

	public double getY() {
		// Modifier l'accesseur pour qu'il renvoie l'ordonnee de la ville
		return this.y;
	}

	int getNumero(){
		return numero;
	}
	
	/**
	 * @return une représentation textuelle de la ville. On indique le nom de la
	 *         ville puis ses coordonnées, séparées par une virgule et entre
	 *         parenthèses.
	 */
	// Renvoyer la représentation textuelle de la ville
	String versChaine()
	{
		return this.nom + " (" + x + "," + y + ")";
	}
}
