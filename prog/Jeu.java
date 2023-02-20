package prog;

import java.util.Random;
import src.Plateau;
import src.Joueur;

/**
 * La classe Jeu int?gre les classes du jeu des Aventuriers du Rail et impl?mente la
 * m?canique de jeu d?taill?e dans les r?gles du jeu original.
 * Les diff?rentes versions de cette classe permet de r?aliser les objectifs fix?s
 * par les huit incr?ments du Projet.
 * @ACOMPLETER
 */


public class Jeu {
    
	/**
	 * Le plateau de jeu
	 * @since Incr?ment 1
	 * @FOURNI
	 */
	Plateau plateau;

	/**
	 * L'interface graphique du jeu
	 * @since Incr?ment 1
	 * @FOURNI
	 */
	IHM ihm;


	 /**
	 * Le tableau des joueurs du jeu
	 * @since Incr?ment 2
	 */
	//d?clarer le tableau des joueurs du jeu
	Joueur[] joueurs;
	int nombreDeJoueurs = 5;

	/**
	 * L'indice du joueur courant dans le tableau de joueurs
	 * @since Incr?ment 2
	 */
	//d?clarer l'indice du joueur courant dans le tableau de joueurs
	int indiceJoueurCourant;
	
	/**
	 * Indique que le dernier tour de la partie commence pour chacun des joueurs<br>
	 * Initialis? ? <code>faux</code>.
	 * @since Incr?ment 2
	 */
	// d?clarer un attribut qui indique si le dernier tour de la partie commence
	boolean dernierTour = false;

		/**
	 * Nombre d'actions du joueur courant pendant le tour
	 * @since Incr?ment 3
	 * @EXTENSION
	 */
	//d?clarer un attribut pour le nombre d'actions du joueur courant
	int nombreAction = 0;

	
	/**
	 * Le nombre de joueurs restants ? jouer lorsque le dernier tour
	 *        est d?tect?
	 * @since Incr?ment 4
	 */
	//d?clarer le nombre de joueurs restant ? jouer
	int joueursRestantsAJouer = -1;
	
	// ACCESSEURS

		/**
	 * @return le plateau de jeu
	 * @since Incr?ment 1
	 * @FOURNI
	 */
	Plateau getPlateau(){
		return this.plateau;
	}

	Joueur[] getJoueurs(){
		return this.joueurs;
	}

		/**
	 * @since Incr?ment 4
	 */
	//d?clarer les accesseurs pour les nouveaux attributs
	int getJoueursRestantsAJouer(){
		return joueursRestantsAJouer;
	}

	/**
	 * @since Incr?ment 4
	 */
	//d?clarer l'accesseur pour en lecture pour l'attribut dernierTour
	boolean isDernierTour(){
		return dernierTour;
	}

	/**
	 * @since Incr?ment 4
	 */
	//d?clarer l'accesseur pour en ?criture (mutateur) pour l'attribut dernierTour

	void setDernierTour(boolean dTour){
		this.dernierTour = dTour;
	}


	/**
	 * Cr?e le jeu des Aventuriers du Rail.
	 * Initialise le plateau de jeu et l'interface homme-machine (IHM)
	 * @since Incr?ment 1
	 * @FOURNI
	 */
	Jeu(){
		plateau = new Plateau();
		ihm = new IHM();
	}



	/**
	 * Affiche le plateau de jeu (pr?alablement initialis?) dans la fen?tre principale 
	 * de l'application
	 * @since Incr?ment 1
	 */
	void affichePlateau(){
		// FOURNI
		this.ihm.afficheFenetre("Les Aventuriers du Rail",1366,768,"White");
		for(Etat e : plateau.getEtats()){
			this.ihm.dessineEtat(e);
		}
		//Afficher les villes et les routes
		for (Route r : plateau.getRoutes()){
			this.ihm.dessineRoute(r);
		}
		for (Ville v : plateau.getVilles()){
			this.ihm.dessineVille(v);
		}
		affichePioches();
		
	}


	/**
	 * M?thode principale de la classe Jeu
	 * Lance le jeu des Aventuriers du Rail et impl?mente le d?roulement pr?vu par les
	 * r?gles du jeu et la mod?lisation associ?e (voir Diagrammes d'activit? UML)
	 * @since Incr?ment 1
	 * @param args les param?tres de l'application
	 */
	public static void main(String[] args){
		Jeu jeu = new Jeu();
		jeu.creeJoueurs(jeu.nombreDeJoueurs);
		jeu.initialisePioches();
		jeu.affichePlateau();
		jeu.distribueCartesWagon();
		do {
			jeu.tourDeJeu();
		}while(!jeu.isDernierTour() || jeu.joueursRestantsAJouer >=0);
 
		
	}


	
	/**
	 * Appel des m?thodes n?cessaires pour ex?cuter un tour de jeu des ADR
	 * @since Incr?ment 2
	 */
	// d?clarer la m?thode tourDeJeu
	void tourDeJeu(){
		
		afficheJoueurCourant();
		demandeAction();
		affichePioches();
		ihm.effaceInformation();

		if(isDernierTour() && getJoueursRestantsAJouer()<0)
		{
			joueursRestantsAJouer = this.joueurs.length;
		}
		
	}
	

	/**
	 * Cr?e un nombre de joueurs pour les jeu des Aventuriers du Rail
	 * Les joueurs sont initialis? avec les couleurs fournies {@link Donnees#COULEURS_JOUEUR}
	 * @since Incr?ment 2
	 * @param nbJoueurs le nombre de joueurs ? cr?er
	 * @EXTENSION : la couleur d'un joueur est tir?e al?atoirement
	 */

	void creeJoueurs(int nombreDeJoueurs){
		this.joueurs = new Joueur[nombreDeJoueurs];
		for(int i=0;i<joueurs.length;i++){
			joueurs[i] = new Joueur(i,Donnees.COULEURS_JOUEUR[i]);
		}
		
	}



	

	/**
	 * @since Incr?ment 2
	 * @return l'objet {@link Joueur} qui correspond au joueur courant (dont
	 *         c'est le tour de jeu)
	 */
	//renvoyer le joueur courant
	Joueur getJoueurCourant(){
		return this.getJoueurs()[indiceJoueurCourant];
	}



	/**
	 * Passe au joueur suivant
	 * @since Incr?ment 2
	 */
	//changer de joueur courant

	void changeJoueur(){
		this.indiceJoueurCourant ++;
		if (this.indiceJoueurCourant >= getJoueurs().length){
			this.indiceJoueurCourant = 0;
		}
		nombreAction=0;
	}





	/**
	 * Demande au joueur une action ? r?aliser pendant son tour et ex?cute l'action 
	 * demand?e.
	 * Lorsque l'utilisateur r?alise une action, un objet de la classe 
	 * {@link ActionUtilisateur} est cr?? et un type d'action y est associ?.
	 * Les types d'actions disponibles sont list?s dans la classe {@link TypeActionUtilisateur}.
	 * Les types d'actions servent ? identifier et traiter chaque type d'action 
	 * de mani?re sp?cifique.
	 * @since Incr?ment 2
	 */
	void demandeAction() {
		// FOURNI
		this.ihm.effaceBoutons();
		this.ihm.dessineBoutonSuivant(false);
		ActionUtilisateur reponse = this.ihm.attenteActionJoueur();
		switch(reponse.getType()){
			case FINTOUR : // changer de joueur
				this.changeJoueur();
				this.afficheJoueurCourant();
				nombreAction = 0;
				break;
			case ROUTE : //le joueur clique sur une route 
				if(nombreAction == 0){
					this.selectionRoute(reponse.getParametre());
					if (dernierTour == true){
						joueursRestantsAJouer --;
					}
					break;
				}
				else {
					ihm.afficheMessageErreur(" Tu n'as plus d'action disponible !");
					break;
				}
			case PIOCHECW : // le joueur pioche une carte 
				if (nombreAction == 0){
					if(Integer.valueOf(reponse.getParametre()) != -1 && piocheVisible[Integer.valueOf(reponse.getParametre())].couleur == null){ // la couleur d'une loco est null
						this.piocheCarteWagon(Integer.valueOf(reponse.getParametre()));
						nombreAction += 2;
						break;
					}
					else{
						this.piocheCarteWagon(Integer.valueOf(reponse.getParametre()));
						nombreAction++;
						break;
					}
				}
				else if (nombreAction == 1){
					if(Integer.valueOf(reponse.getParametre()) != -1 && piocheVisible[Integer.valueOf(reponse.getParametre())].couleur == null){
						ihm.afficheMessageErreur(" Tu ne peux pas prendre une locomotive ! ");
					}
					else{
						this.piocheCarteWagon(Integer.valueOf(reponse.getParametre()));
						nombreAction++;
						break;
					}
				}
				else {
					ihm.afficheMessageErreur(" Tu n'as plus d'action disponible !");
					break;
				}
				
			default : break;
		}
	}




	/**
	 * Affiche les informations (num?ro, score et nombre de wagons restants) du 
	 * joueur courant.
	 * A partir de l'incr?ment 4, affiche le score et le nombre de wagons restants
	 * au joueur.
	 * A partir de l'incr?ment 7, affiche la main du joueur
	 * @since Incr?ment 2
	 */
	//Affiche les informations du joueur courant
	
	void afficheJoueurCourant(){
		ihm.afficheInformation("Joueur " + (this.indiceJoueurCourant+1), 70 , 400, false,this.getJoueurCourant().getCouleur());
		ihm.afficheInformation("Score " + String.valueOf(getJoueurCourant().getScore()), 70, 415, false);
		ihm.afficheInformation("Wagons " + String.valueOf(getJoueurCourant().getNombreWagons()),70, 430, false);
		ihm.afficheCouleurJoueurCourant(this.getJoueurCourant().getCouleur());
		ihm.afficheCartesJoueur(this.getJoueurCourant());
	}

		/**
	 * S?lection d'une route de la part du joueur qui en devient propri?taire
	 * A partir de l'incr?ment 4, le score et le nombre de wagon restants au joueur
	 * sont mis ? jour.
	 * @param nom le nom de la route s?lectionn?e
	 * @since Incr?ment 3
	 */
	// d?clarer la m?thode selectionRoute
	void selectionRoute(String nom){
		Route route = plateau.rechercheRoute(nom);
		if (route.getProprietaire() != null) {
			ihm.afficheMessageErreur("La route est d?j? prise !");
		}
		else {
			if (route.getLongueur() > getJoueurCourant().getNombreWagons()){
				ihm.afficheMessageErreur("Tu n'as pas assez de Wagons !");
			}
			else {
				route.setProprietaire(getJoueurCourant());
				getJoueurCourant().ajouteAuScore(route.getNombrePoints());
				//mettre ? jour l'affichage
				this.ihm.effaceRoute(route);
				this.ihm.dessineRoute(route);
				this.ihm.dessineVille(route.getVilleDepart());
				this.ihm.dessineVille(route.getVilleArrivee());
				this.nombreAction = 2;
				getJoueurCourant().enleveWagons(route.getLongueur());
				detecteDernierTour();
				
			}
		}
	}

		/**
	 * V?rifie si le joueur courant a moins de 3 wagons restants et met ? jour
	 * l'attribut dernierTour.
	 * @since Incr?ment 4
	 */
	// d?clarer la m?thode detecteDernierTour
	void detecteDernierTour(){
		boolean dTour = dernierTour;
		if (dTour == false){
			if (getJoueurCourant().getNombreWagons() < 3){
				dTour = true;
			}
			else{
				dTour = false;
			}
		}
		setDernierTour(dTour);
	}



	// ------------------INCREMENT 5--------------------

	/**
	 * La pioche (l'objet {@link PiocheCartesWagon}) de cartes "wagon"
	 * @since Incr?ment 5
	 */
	// d?clarer la pioche cach?e de cartes wagon
	PiocheCartesWagon piocheCartesWagon;
	
	PiocheCartesWagon getPiocheCartesWagon(){
		return piocheCartesWagon;
	}
	/**
	 * La d?fausse (l'objet {@link PiocheCartesWagon}) de cartes "wagon"
	 * @since Incr?ment 5
	 */
	// d?clarer la d?fausse de cartes wagon
	PiocheCartesWagon defausseCartesWagon;
	PiocheCartesWagon getDefausseCartesWagon(){
		return defausseCartesWagon;
	}

	/**
	 * La pioche visible (le tableau d'objets {@link CarteWagon}) de cartes "wagon"
	 * @since Incr?ment 5
	 */
	//d?clarer la pioche visible de cartes wagon
	
	CarteWagon[] piocheVisible ;//= piochecarteswagon.extraitCartes(5);
	
	CarteWagon[] getPiocheVisible(){
		return piocheVisible ;
	}

	/**
	 * Initialise les pioches de cartes "wagon" ainsi que la d?fausse
	 * @since Incr?ment 5
	 */
	// d?clarer la m?thode initialisePioches
	void initialisePioches(){
		 this.piocheCartesWagon = new PiocheCartesWagon();
		 piocheCartesWagon.ajouteCartes(); // on remplit la pioche 
		 //piocheCartesWagon.setCartesWagon( piocheCartesWagon.melange()); // on m?lange 
		 piocheCartesWagon.melange(); // on m?lange
		 piocheCartesWagon.melange();
		 this.defausseCartesWagon = new PiocheCartesWagon(); //initialisation de la d?fausse
		 this.piocheVisible = new CarteWagon[5]; // taille de la pioche visible
		 piocheVisible = piocheCartesWagon.extraitCartes(5); // ?l?ments de la pioche visible 
	}

	

	// ------------------INCREMENT 6--------------------

	/**
	 * Affiche les pioches de cartes et la d?fausse
	 * @since Incr?ment 6
	 */
	// TODO d?clarer la m?thode affichePioches
	void affichePioches(){
		ihm.affichePioches(getPiocheCartesWagon(), getPiocheVisible());
		ihm.afficheDefausse();
	}

	/**
	 * Pioche d'une carte "wagon" de la part du joueur, soit dans la pioche
	 * cach?e, soit dans la pioche visible A partir de l'incr?ment 7, la carte
	 * pioch?e est ajout?e ? la main du joueur
	 * @param indice l'indice de la carte pioch?e visible s?lectionn?e, ou -1 si
	 *            la carte est pioch?e dans la pioche cach?e.
	 * @since Incr?ment 6
	 */
	// d?clarer la m?thode piocheCarteWagon
	void piocheCarteWagon(int entier){
		if ( entier == - 1){
			this.getJoueurCourant().prendsCarteWagon(getPiocheCartesWagon().depile());
		}
		if (entier >= 0 && entier < 5){
			CarteWagon carteWagonPioche = getPiocheCartesWagon().depile();
			this.getJoueurCourant().prendsCarteWagon(piocheVisible[entier]);
			piocheVisible[entier] = carteWagonPioche;
		}
	}
	
// ------------------- incr�ment 7 -----------------------
	/**
	 * Distribue quatre cartes "wagon" ? chaque joueur
	 * Les cartes "wagon" sont retir?es de la pioche de cartes "wagon"
	 * @since Incr?ment 7
	 */
	// d?clarer la m?thode distribueCartesWagon
	void distribueCartesWagon(){
		int nombreJoueurs = joueurs.length;
		for(int k = 0; k < 4; k++){
			for (int i = 0; i < nombreJoueurs; i++){
				joueurs[i].prendsCarteWagon(getPiocheCartesWagon().depile());
			}
		}
	}

// -------------------------- incrément 8 ---------------------------------
	/**
	 * Recherche l'indice du joueur dont le diam�tre est maximum.
	 * @param diametres les diam�tres de chaque joueur
	 * @return l'indice du joueur dont le diam�tre est maximum
	 * @since Incr�ment 8
	 */
	// TODO d�clarer la m�thode maxDiametre

	/**
	 * Recherche l'indice des joueurs dont le nombre de groupes connexes
	 *  est minimum. Plus le nombre de groupes connexes est faible, plus le r�seau  
	 *  du joueur est dense.
	 * @param connexites le nombre de groupes connexes de chaque joueur
	 * @return l'indice du joueur dont le nombre de groupes
	 * connexes est minimum.
	 * @since Incr�ment 8
	 */
	// TODO d�clarer la m�thode minGroupesConnexes

	/**
	 * Calcule et affiche le joueur qui remporte la partie
	 * @since Incr�ment 8
	 */
	// TODO d�clarer la m�thode afficheVainqueur

	/**
	 * Calcule et affiche les scores de la partie pour l'ensemble des joueurs.
	 * @since Incr�ment 8
	 * @EXTENSION
	 */
	// TODO d�clarer la m�thode afficheScores

	/**
	 * Recherche le ou les indices des joueurs dont le diam�tre est maximum.
	 * @param diametres les diam�tres de chaque joueur
	 * @return une liste contenant l'indice du joueur dont le diam�tre est maximum 
	 * ou plusieurs indices en cas d'�galit�.
	 * @since Incr�ment 8
	 * @EXTENSION
	 */
	// TODO rechercher les diam�tres maximums ex-aequo

	/**
	 * Recherche le ou les indices des joueurs dont le nombre de groupes connexes
	 *  est minimum. Plus le nombre de groupes connexes est faible, plus le r�seau  
	 *  du ou des joueur(s) est dense.
	 * @param connexites le nombre de groupes connexes de chaque joueur
	 * @return une liste contenant l'indice du joueur dont le nombre de groupes
	 * connexes est minimum ou plusieurs indices en cas d'�galit�.
	 * @since Incr�ment 8
	 * @EXTENSION
	 */
	// TODO rechercher les nombres de groupes connexes minimums ex-aequo
}
