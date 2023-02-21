package src;

import lib.Donnees;

/**
 * La classe Plateau représente le plateau de jeu des Aventuriers du Rail.
 * Il se compose d'un ensemble d'objets {@link Etat}, d'un ensemble d'objets {@link Ville} et d'un
 * ensemble d'objets {@link Route}.
 * @since Incrément 1
 * @ACOMPLETER
 */

public class Plateau {
    
	/**
	 * La liste des états du plateau de jeu
	 * @FOURNI
	 */
	Etat[] etats;

	/**
	 * La liste des villes du plateau de jeu
	 */
	Ville[] villes;

	/**
	 * La liste des routes du plateau de jeu
	 */
	Route[] routes;
	
	/**
	 * Crée le plateau de jeu
	 */
	public Plateau() {
		initialiseEtats(); // @FOURNI
		// Compléter avec l'initialisation des villes et des routes
		initialiseVilles();
		initialiseRoutes();
		
	}

	// ACCESSEURS
	/**
	 * @return les états du plateau de jeu
	 * @FOURNI
	 */
	public Etat[] getEtats() {
		return this.etats;
	}

	// TODO Déclarer les accesseurs
	
	public Ville[] getVilles()
	{
		return this.villes;
	}

	public Route[] getRoutes()
	{
		return this.routes;
	}



	
	
	/**
	 * Initialise l'ensemble des états du jeu à partir des données fournies (
	 * {@link Donnees#ETATS}) Les données utilisées sont transformées pour
	 * isoler le nom de l'état et son "contour".
	 * @FOURNI
	 */
	void initialiseEtats() {
		this.etats = new Etat[Donnees.ETATS.length];
		for(int i=0;i<Donnees.ETATS.length;i++){
			etats[i] = new Etat(Donnees.ETATS[i][0],Donnees.ETATS[i][1]);
		}
	}





	
	/**
	 * Initialise l'ensemble des villes du jeu à partir des données fournies (
	 * {@link Donnees#VILLES}) Les données utilisées sont transformées pour
	 * isoler le nom de la ville, son abscisse et son ordonnée.
	 */
	// Initialiser les villes du plateau de jeu
	void initialiseVilles()
	 {
	 	this.villes = new Ville[Donnees.VILLES.length]; // on initialise toujours la taille du tableau 
	 	for(int i=0;i<Donnees.VILLES.length;i++)
	 	{
	 		String[] tableauSplit = Donnees.VILLES[i].split(" ");
	 		String nomVille = tableauSplit[0];
	 		if (tableauSplit.length > 3){
	 			for (int j=1; j < tableauSplit.length-2; j++){
	 				nomVille += " " + tableauSplit[j];
	 			}
	 		}
	 		villes[i] = new Ville(nomVille,Double.valueOf(tableauSplit[tableauSplit.length-2]),Double.valueOf(tableauSplit[tableauSplit.length-1]), i);
	 	}
	 }


	 
	
	/**
	 * Initialise l'ensemble des routes du jeu à partir des données fournies (
	 * {@link Donnees#ROUTES}) Les données utilisées sont transformées pour
	 * isoler le nom de la ville de départ, le nom de la ville d'arrivée et la
	 * couleur de la route. La taille de la route est calculée à partir des
	 * coordonnées des villes ({@link Route#getLongueur()})
	 */
	// Initialiser les routes du plateau de jeu

	void initialiseRoutes(){
		this.routes = new Route[Donnees.ROUTES.length];
		for(int i=0;i<Donnees.ROUTES.length;i++){
			routes[i] = new Route(rechercheVille(Donnees.ROUTES[i][0]),rechercheVille(Donnees.ROUTES[i][1]),Donnees.ROUTES[i][2]);
		}
		
	}

	
	
	/**
	 * Recherche d'une ville à partir de son nom
	 * @param nom le nom de la ville
	 * @return un objet {@link Ville} correspond au nom recherché, {@code null} sinon
	 */
	// Implementer la recherche d'une ville
	Ville rechercheVille(String ville)
	{
		for (int i=0; i < villes.length; i++){
			if (villes[i].getNom().equals(ville)){
				return villes[i];
			}
		}
		return null;
	}

	
	/**
	 * Recherche d'une route à partir de son nom
	 * @param nom le nom de la route
	 * @return un objet {@link Route} correspond au nom recherché, {@code null} sinon
	 * @EXTENSION
	 */
	// Implementer la recherche d'une route
	
	public Route rechercheRoute(String route)
	{
		for (int i=0; i < routes.length; i++){
			if (routes[i].getNom().equals(route)){
				return routes[i];
			}
		}
		return null;
	}
}
