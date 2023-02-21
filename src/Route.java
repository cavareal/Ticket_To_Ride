package src;
/**
 * La classe Route repr�sente une route dans le jeu des Aventuriers du Rail.
 * Une route dispose d'une ville de d�part, d'une ville d'arriv�e et d'une couleur.
 * A partir de l'incr�ment 3, une route pourra �tre poss�d�e par un joueur.
 * @since Incr�ment 1
 * @ACOMPLETER
 */
public class Route {
    
	/**
	 * La ville de d�part de la route
	 */
	// D�clarer la ville de d�part
	Ville villeDepart;
	
	/**
	 * La ville d'arriv�e de la route
	 */
	// D�clarer la ville d'arriv�e
	Ville villeArrivee;
	
	/**
	 * La couleur de la route
	 */
	// D�clarer la couleur
	String couleur;

	
	/**
	 * Le joueur propri�taire d'une route lorsque cet attribut est
	 *        renseign�, {@code null} sinon.
	 * @since Incr�ment 3
	 * 
	 */
	// d�clarer le proprietaire de la route
	Joueur proprietaire;
	
	/**
	 * @return le propri�taire de la route s'il existe, {@code null} sinon.
	 * @since Incr�ment 3
	 */
	// accesseur en lecture du proprietaire de la route
	public Joueur getProprietaire(){
		return this.proprietaire;
	}
	
	/**
	 * Affecte un joueur en tant que propri�taire de la route
	 * @param proprietaire l'objet {@link Joueur} qui devient propri�taire de la route
	 * @since Incr�ment 3
	 */
	// accesseur en �criture du proprietaire de la route
	public void setProprietaire(Joueur joueur){
		this.proprietaire = joueur;
	}
	
	
	/**
	 * Cr�e une route
	 * @param ville1 la ville de d�part
	 * @param ville2 la ville d'arriv�e
	 * @param couleur la couleur de la route
	 */
	// D�clarer le constructeur
	public Route(Ville villeDepart, Ville villeArrivee, String couleur)
	{
		this.villeDepart = villeDepart;
		this.villeArrivee = villeArrivee;
		this.couleur = couleur;
	}
	

	// ACCESSEURS
	public Ville getVilleDepart() {
		// Modifier l'accesseur pour qu'il renvoie la ville de d�part de la route
		return this.villeDepart;
	}

	public Ville getVilleArrivee() {
		// Modifier l'accesseur pour qu'il renvoie la ville d'arriv�e de la route
		return this.villeArrivee;
	}

	public String getCouleur() {
		// Modifier l'accesseur pour qu'il renvoie la couleur de la route
		return this.couleur;
	}

	/**
	 * G�n�re le nom de la route � partir des noms des villes reli�es, s�par�s
	 * par un tiret '-'
	 * @return le nom de la route
	 */
	public String getNom() {
		// Modifier la m�thode pour qu'elle renvoie le nom de la ville
		return villeDepart.getNom() + " - " + villeArrivee.getNom();
	}
	
	/**
	 * Le calcul de la longueur de la route d�pend des coordonn�es des villes
	 * sur l'�cran. Pour respecter le ratio pr�sent sur le plateau de jeu
	 * original, on propose d'impl�menter l'algorithme suivant : 1. on calcule
	 * la distance entre la ville de d�part et la ville d'arriv�e (longueur du
	 * segment) 2. on utilise la valeur 33 pour appliquer ratio sur la distance
	 * calcul�e 3. on conserve le minimum entre la longueur obtenue apr�s ratio
	 * et la valeur maximale 6.
	 * 
	 * @return la longueur de la route (entre 1 et 6), calcul�e en fonction de
	 *         la position des villes
	 */
	public int getLongueur() {
		// Modifier la m�thode pour qu'elle renvoie la longueur de la route
		double x1 = this.villeDepart.getX();
	 	double x2 = this.villeArrivee.getX();
	 	double y1 = this.villeDepart.getY();
	 	double y2 = this.villeArrivee.getY();

    	double distance = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    	double ratio = distance/33;
    	int ratioInt = (int) ratio;
    	if (ratioInt > 6)
    	{
        	ratioInt = 6;
    	};
		return ratioInt ;
	}
	
	/**
	 * Transforme la longueur de la route en un nombre de points qu'elle
	 * rapporte au joueur qui en devient propri�taire
	 * @return une valeur enti�re entre 1 et 15; une autre valeur indique une
	 *         erreur dans les donn�es de la route
	 */
	//Transformer la longueur de la route en points
	public int getNombrePoints()
	{
		int l = this.getLongueur();
		int[] tabPoints = {1, 2, 4, 7, 10, 15};
		return  tabPoints[l-1];
	}
	
	
	/**
	 * @return une repr�sentation textuelle de la route. On indique le nom de la
	 *         route puis sa couleur et sa longueur s�par�es par un tiret '-'.
	 */
	//Renvoyer la repr�sentation textuelle de la route
	String versChaine()
	{
		return villeDepart.getNom() + " - " + villeArrivee.getNom() + " /" + this.couleur +"-"+ getLongueur() ;
	}
}
