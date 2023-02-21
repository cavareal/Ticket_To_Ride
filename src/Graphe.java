package src;

import lib.Donnees;

/**
 * La classe Graphe est la représentation du plateau de jeu pour le calcul des bonus du jeu.
 * Un graphe dispose d'un nombre de sommets correspondants aux villes du plateau de jeu. 
 * Pour l'implémentation des algorithmes principaux liés à la théorie des graphes, la topologie
 * du graphe représente les connexions existantes entre les sommets et est construite à partir 
 * des données du plateau de jeu et de la partie en cours.
 * @since Incrément 8
 */

public class Graphe {
    	
	/**
	 * Matrice d'entiers qui représente la topologie du graphe. Pour chaque arc
	 * existant entre deux sommets i et j, on insère la valeur 1 pour le couple
	 * [i][j] et [j][i].
	 */
	// déclarer la topologie du graphe
	int[][] topologie;
	/**
	 * Le plateau de jeu
	 */
	// déclarer le plateau de jeu
	Plateau plateau = new Plateau();
	
	/**
	 * Le nombre de sommets du graphe
	 */
	// déclarer le nombre de sommet
	int nombreSommets;

	/**
	 * Crée un graphe à partir du plateau de jeu et initialise toutes les valeurs
	 * de la matrice de topologie à 0.
	 * @param plateau le plateau de jeu
	 */
	// déclarer le constructeur

	Graphe (Plateau plateau){
		this.nombreSommets = Donnees.VILLES.length;
		this.plateau = plateau;
		this.topologie = new int[this.nombreSommets][this.nombreSommets];
		for(int i = 0; i < getNombreSommets() ; i++){
			for (int j =0; j < getNombreSommets(); j++){
				topologie[i][j] = 0;
			}
		}
	}
	
	// ACCESSEURS
	
	// déclarer les accesseurs en lecture
	int getNombreSommets(){
		return this.nombreSommets;
	}

	int[][] getTopologie(){
		return this.topologie;
	}

	/**
	 * Initialise la topologie pour représenter le graphe d'un joueur. Le graphe
	 * d'un joueur contient un arc entre deux sommets si le joueur est 
	 * propriétaire de la route entre les deux villes correspondantes
	 * @param joueur un Joueur
	 */
	// déclarer la méthode creeGraphePourUnJoueur
	void creeGraphePourUnJoueur(Joueur joueur){
		for (int i = 0; i < plateau.routes.length; i ++){
			if (plateau.getRoutes()[i].getProprietaire() == joueur){
				this.topologie[plateau.getRoutes()[i].getVilleDepart().getNumero()][plateau.getRoutes()[i].getVilleArrivee().getNumero()] = plateau.getRoutes()[i].getLongueur();
				this.topologie[plateau.getRoutes()[i].getVilleArrivee().getNumero()][plateau.getRoutes()[i].getVilleDepart().getNumero()] = plateau.getRoutes()[i].getLongueur();
			}
		}
	}
	
	/**
	 * Parcourt en profondeur le graphe du joueur à partir du numéro de la
	 * ville, passé en paramètre
	 * @param numeroVille la ville de départ du parcours
	 * @param marques tableau de marques créées lorsque l'algorithme passe dans
	 *            une ville du graphe
	 * @return le tableau de marques après parcours
	 */
	// déclarer la méthode parcoursProfondeur
	 boolean[] parcoursProfondeur (int numeroSommet, boolean[] marque) {  
		int v = 0;// est un sommet
		marque[numeroSommet] = true;
		//System.out.print("S" + numeroSommet+ " ");
   	for (int j = 0; j < this.getNombreSommets(); j++){
   		 v = this.topologie[numeroSommet][j];
   		 if(!marque[j] && v!=0){
   		 	parcoursProfondeur(j, marque);
   		 }
   	}
   	return marque;
   }

	
	/**
     * Indique si une ville d'arrivée est accessible à partir d'une ville de 
     * départ en utilisant les routes en possession du joueur.
     * @param villeDepart la ville de départ
     * @param villeArrivee la ville d'arrivée
     * @return vrai si la ville est atteignable, faux sinon
     */
	// déclarer la méthode sommetAtteignable
	boolean sommetAtteignable(int numeroSommetDepart, int numeroSommetArrivee) {
		boolean[] marques = new boolean[this.nombreSommets]; 
		marques = parcoursProfondeur(numeroSommetDepart, marques); 
		if (marques[numeroSommetArrivee]){
			return true;
		}
		else {
			return false;
		}
    }

	
	/**
	 * Calcule le nombre de groupes connexes présents dans la topologie.
     *  Nécessaire pour l'attribution du bonus "connexité"
	 * @return le nombre de groupes connexes présents dans la topologie
	 */
	// déclarer la méthode calculConnexite
	int calculConnexite(){
		return 0;
	}
	
	
	/**
	 * Crée une copie de la topologie du graphe
	 * @return une nouvelle matrice, copie de la topologie
	 */
	// TODO déclarer la méthode dupliqueTopologie
	
	/**
	 * Implémentation de l'algorithme de Floyd pour le calcul des plus courts 
	 * chemins.
	 * @return une matrice contenant la distance minimale entre deux sommets
	 */
	// TODO déclarer la méthode plusCourtsCheminsFloyd
	
	/**
	 * Calcule le diamètre du graphe en utilisant l'algorithme de Floyd.
	 * @return le diamètre du graphe
	 */
	// TODO déclarer la méthode diametre
}
