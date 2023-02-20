package lib;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * La classe IHM fournit des méthodes pour dessiner et intéragir avec la fenêtre
 * principale du jeu. Chaque élément graphique affiché (i.e. un texte, une
 * image, un trait) est ajouté à la vue graphique à l'aide d'un identifiant. Cet
 * identifiant permet de retrouver l'élément graphique dans la vue lorsqu'il est
 * nécessaire de le sélectionner ou encore de le supprimer.
 */

 public class IHM {
	
	/**
	 * Abscisse d'origine du panneau des scores
	 */
	static final int PANNEAU_SCORE_Y = 100;

	/**
	 * Ordonnee d'origine du panneau des scores
	 */
	static final int PANNEAU_SCORE_X = 300;
	static final double DISPLAY_RATIO = 0.85;

	/**
	 * Valeur du symbole "tick" en unicode
	 */
	static final String TICK = "\u2713";

	/**
	 * Largeur par défaut de l'image d'une carte
	 */
	static double LARGEUR_CARTE = 44;
	
	/**
	 * Hauteur par défaut de l'image d'une carte
	 */
	static double HAUTEUR_CARTE = 62;

	/**
	 * La fenêtre principale du jeu
	 */
	Fenetre fenetre;
	
	/**
	 * L'outil de dessin sur la fenêtre de jeu
	 */
	Crayon crayon;
	
	// ACCESSEURS
	Fenetre getFenetre(){
		return this.fenetre;
	}

	Crayon getCrayon(){
		return this.crayon;
	}

	/**
	 * Affiche la fenêtre principale du jeu et initialise le crayon permettant
	 * de dessiner les formes sur la fenêtre.
	 * @param titre le titre de la fenêtre
	 * @param largeur la largeur de la fenêtre
	 * @param hauteur la hauteur de la fenêtre
	 * @param couleurFond la couleur de fond de la fenêtre
	 */
	void afficheFenetre(String titre, int largeur, int hauteur, String couleurFond){
		this.fenetre = new Fenetre();
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // for single screen environment
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
		double echelle = 
				gs.getDisplayMode().getWidth() / gs.getDisplayMode().getHeight() > DISPLAY_RATIO
		              ? gs.getDisplayMode().getWidth() / 1366.0//1280.0
		              : gs.getDisplayMode().getHeight() / 768.0;//720.0;
		this.fenetre.affiche(titre, largeur, hauteur, echelle*DISPLAY_RATIO, couleurFond);
		this.crayon = new Crayon();
		this.crayon.dessineSur(this.fenetre);
	}

	/**
	 * Dessine un état sur la fenêtre principale.
	 * Par défaut, le contour de l'état est une nuance aléatoire de bleu et de noir.
	 * @param etat l'objet {@link Etat} à dessiner
	 */
	void dessineEtat(Etat etat) {
		this.crayon.deplace(10, 10);
		this.crayon.changeEncre("noir", "bleu", 0.05 + Math.random() * 0.2);
		this.crayon.dessineChemin("etat" + etat.getNom(), etat.getContour());
	}

	/**
	 * Dessine une ville sur la fenêtre principale.
	 * Par défaut, seul le nom de la ville est dessiné sous la forme d'un texte blanc
	 * sur fond noir semi-opaque. 
	 * @param ville l'objet {@link Ville} à dessiner
	 */
	void dessineVille(Ville ville) {
		this.crayon.deplace(10, 10);
		this.crayon.translate(ville.getX(), ville.getY());
		this.crayon.changeEncre("blanc", "noir", 0.5);
		this.crayon.dessineTexte("ville" + ville.getNom(), ville.getNom(), 14, true);
	}

	/**
	 * Dessine une route sur la fenêtre principale. Par défaut, l'épaisseur de
	 * la route est de 5 points. Le dessin d'une route est composé d'une ligne
	 * droite entre les villes concernées ainsi que d'un cercle contenant la
	 * longueur de la route. Lorsque la route à un propriétaire, l'épaisseur de
	 * la route est de 8 points, la couleur de la route est remplacée par la
	 * couleur du joueur propriétaire et la longueur de la route n'est plus
	 * affichée.
	 * @param route l'objet {@link Route} à dessiner
	 */
	void dessineRoute(Route route) {
		int epaisseurRoute = 5;
		double x1 = route.getVilleDepart().getX();
		double y1 = route.getVilleDepart().getY();
		double x2 = route.getVilleArrivee().getX();
		double y2 = route.getVilleArrivee().getY();
		String texte = String.valueOf(route.getLongueur());
		this.crayon.deplace(10, 10);
		this.crayon.translate(x1, y1);
		/** @since Incrément 3 */
		if(route.getProprietaire()==null){
			this.crayon.changeEncre(route.getCouleur(), route.getCouleur(), 1);
		
		} else {
			String couleur = route.getProprietaire().getCouleur();
			this.crayon.changeEncre(couleur,couleur, 1);
			epaisseurRoute = 8;
		}
		this.crayon.dessineLigne("reproute" + route.getNom(), x2 - x1, y2 - y1, epaisseurRoute);
		try {
			Tooltip.install(this.fenetre.getListeFormes().get(this.fenetre.getListeFormes().size()-1), new Tooltip("Prendre possession d'une route"));
		} catch(Exception ex){
			System.out.println("Une erreur interne est survenue !");
		}
		this.crayon.deplace(10, 10);
		this.crayon.translate((x1 + x2) / 2, (y1 + y2) / 2);
		/** @since Incrément 3 */
		if(route.getProprietaire()==null){
			this.crayon.dessineCercle("reproute" + route.getNom(), 8);
			try{
				Tooltip.install(this.fenetre.getListeFormes().get(this.fenetre.getListeFormes().size()-1), new Tooltip("Prendre possession d'une route"));
			} catch(Exception ex){
				System.out.println("Une erreur interne est survenue !");
			}
			if (route.getCouleur().equals("blanc") || 
					route.getCouleur().equals("jaune")) {
				this.crayon.changeEncre("noir", null, 1);
			}
			else {
				this.crayon.changeEncre("blanc", null, 1);
			}
			this.crayon.dessineTexte("reproute" + route.getNom(), texte, 14, true);
			try {
				Tooltip.install(this.fenetre.getListeFormes().get(this.fenetre.getListeFormes().size()-1), new Tooltip("Prendre possession d'une route"));
			} catch(Exception ex){
				System.out.println("Une erreur interne est survenue !");
			}
		}
	}
	
	/**
	 * Dessine le bouton "suivant" à partir d'une image fournie.
	 * Ce bouton permet à un joueur de terminer son tour.
	 * Le bouton peut être affiché sélectionné (suite à un clic souris) ou non sélectionné.
	 * @param selectionne indique si le bouton doit être affiché "sélectionné" (en rouge) ou non
	 */
	void dessineBoutonSuivant(boolean selectionne){
		this.crayon.deplace(970, 445);
		try{
			Image image = new Image("next.png");
			ImageView iv = new ImageView();
			iv.setImage(image);
			iv.setId("repFin du tour");
			iv.setPreserveRatio(true);
			iv.setFitHeight(42);
			try{
				Tooltip.install(iv, new Tooltip("Passer son tour"));
			} catch(Exception ex){
				System.out.println("Une erreur interne est survenue !");
			}
			this.crayon.dessineForme("repFin du tour",iv);
		} catch(IllegalArgumentException iae){
			this.afficheMessageErreur("Erreur dans le fichier DevCube, une "
					+ "ressource n'a pu être trouvée");
		}
	}

	// DESSIN DES CARTES

	/**
	 * Dessine une carte ("wagon" ou "destination") sur la fenêtre principale.
	 * Cette version utilise des images pour afficher les cartes. Les couleurs des cartes
	 * "wagon" servent à sélectionner l'image à afficher. Lorsque l'on veut afficher le dos
	 * de la carte, on utilise la valeur "back".
	 * @since Incrément 6
	 * @param id l'identifiant de la carte à dessiner
	 * @param couleur la couleur de la carte
	 * @param verticalement l'orientation de la carte (verticale ou horizontale)
	 */
	void dessineCarte(String id, String couleur, boolean verticalement) {
		String filename = "back.png";
		if(couleur != null){
			switch(couleur){
				case "noir" : filename = "wagon_black.png";
					break;
				case "blanc" : filename = "wagon_white.png";
					break;
				case "bleu" : filename = "wagon_blue.png";
					break;
				case "vert" : filename = "wagon_green.png";
					break;
				case "orange" : filename = "wagon_orange.png";
					break;
				case "violet" : filename = "wagon_purple.png";
					break;
				case "rouge" : filename = "wagon_red.png";
					break;
				case "jaune" : filename = "wagon_yellow.png";
					break;
				default : break;
			}
		} else {
			filename = "wagon_locomotive.png";
		}
		this.crayon.translate(1, 8);
		this.crayon.changeEncre("noir", null, 0);
		try {
			Image image = new Image(getClass().getResource(filename).toExternalForm());
			ImageView iv = new ImageView();
			iv.setImage(image);
			iv.setId(id);
			iv.setPreserveRatio(true);
			iv.setFitHeight(LARGEUR_CARTE);
			if(verticalement){
				iv.setRotate(90);
			}
			try{
				Tooltip.install(iv, new Tooltip("Prendre une carte wagon"));
			} catch(Exception ex){
				System.out.println("Une erreur interne est survenue !");
			}
			this.crayon.dessineForme(id,iv);
		} catch(IllegalArgumentException iae){
			this.afficheMessageErreur("Erreur dans le fichier DevCube, une "
					+ "ressource n'a pu être trouvée");
		}
		this.crayon.translate(-1, -8);
	}

	/**
	 * Dessine les pioches du jeu.
	 * @param piocheCartesWagon la pioche de cartes "wagon"
	 * @param cartesWagonVisibles la pioche de cinq cartes "wagon" visibles
	 * @since Incrément 6
	 */
	void affichePioches(PiocheCartesWagon piocheCartesWagon,
			CarteWagon[] cartesWagonVisibles) {
		this.fenetre.effaceFormes("reppiocheCW");
		this.fenetre.effaceFormes("repdefausse");
		this.crayon.deplace(950, 30);
		// affichage du dessus de la pioche des cartes wagon
		if(!piocheCartesWagon.estVide()) {
			this.dessineCarte("reppiocheCW-1","back",false);
		}
		
		// affichage des cartes wagon visibles
		this.crayon.translate(0, (LARGEUR_CARTE + 10) + LARGEUR_CARTE + 30);
		if(cartesWagonVisibles != null) {
			for (int i = 0; i < cartesWagonVisibles.length; i++) {
				CarteWagon carte = cartesWagonVisibles[i];
				if(carte != null) {
					this.dessineCarte("reppiocheCW" + i,carte.getCouleur(),false);
				}
				this.crayon.translate(0,LARGEUR_CARTE + 5);
			}
		}
	}
	
	/**
	 * Affiche la défausse.
	 * Par défaut, on affiche deux rectangles : l'un blanc et l'autre noir
	 * pour simuler une bordure.
	 * Dans cette version, le contenu de la défausse n'est pas affiché.
	 * @since Incrément 6
	 */
	void afficheDefausse() {
		this.crayon.deplace(950+2, 30+LARGEUR_CARTE+10);
		this.crayon.changeEncre("noir", "blanc", 1.0);
		Rectangle rect = this.crayon.creeRectangle("repdefausse", HAUTEUR_CARTE+4, LARGEUR_CARTE-4);
		this.crayon.changeEncre("noir", "noir", 1.0);
		Rectangle rect2 = this.crayon.creeRectangle("repdefausse", HAUTEUR_CARTE+8, LARGEUR_CARTE);
		Tooltip.install(rect, new Tooltip("Defausse"));
		Tooltip.install(rect2, new Tooltip("Defausse"));
		this.crayon.dessineForme("repdefausse", rect2);
		this.crayon.translate(2,2);
		this.crayon.dessineForme("repdefausse", rect);
	}

	/**
	 * Affiche les cartes présentes dans la main du joueur.
	 * Lorsque le nombre de cartes d'une couleur dépasse 2, on affiche un texte supplémentaire
	 * indiquant le nombre de cartes, pour faciliter la lecture par le joueur.
	 * Cette version affiche seulement les cartes "wagon".
	 * @param joueur le joueur pour lequel on affiche les cartes
	 * @since Incrément 7
	 */
	void afficheCartesJoueur(Joueur joueur) {
		this.fenetre.effaceFormes("cj");
		this.crayon.deplace(5, 510);
		// Cartes wagon
		ListeCartesWagon listeCW = joueur.getMainCartesWagon();
		// Affichage des cartes locomotives
		int n = listeCW.getNombreOccurrences(null);
		for (int j = 0; j < n; j++) {
			this.dessineCarte("cj", null,true);
			this.crayon.translate(5,0);
		}
		if (n > 0) {
			if(n>2){ // Creation des paquets de cartes
				this.crayon.changeEncre("blanc", null, 1);
				// Sauvegarde de la position du crayon avant écriture du texte
				double originX = this.crayon.getX();
				this.crayon.translate(5,0);
				this.crayon.ecrisTexte("cj", String.valueOf(n), 40);
				// Repositionne le crayon sur le bord de la dernière carte dessinée
				this.crayon.deplace(originX, this.crayon.getY());
			}
		}
		this.crayon.translate(45,0);
		
		// Affichage des cartes de couleur
		for (int i = 0; i < Donnees.COULEURS_WAGON.length; i++) {
			n = listeCW.getNombreOccurrences(Donnees.COULEURS_WAGON[i]);
			for (int j = 0; j < n; j++) {
				this.dessineCarte("cj",Donnees.COULEURS_WAGON[i],true);
				this.crayon.translate(5,0);
			}
			if (n > 0) {
				if(n>2){// Creation des paquets de cartes
					this.crayon.changeEncre("blanc", null, 1);
					// Sauvegarde de la position du crayon avant écriture du texte
					double originX = this.crayon.getX();
					this.crayon.translate(5,0);
					this.crayon.ecrisTexte("cj", String.valueOf(n), 40);
					// Repositionne le crayon sur le bord de la dernière carte dessinée
					this.crayon.deplace(originX, this.crayon.getY());
				}
			}
			this.crayon.translate(45,0);
		}
	}

	/**
	 * Affiche un message d'erreur sur la fenêtre principale.
	 * Par défaut, le texte est affiché dans une fenêtre popup.
	 * @param message le texte à afficher
	 */
	void afficheMessageErreur(String message) {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				// Creation popup
				PopOver popup = creePopupErreur();
				Text text = crayon.creerTexte("message", message, 15, true, true);
				text.setFill(Color.RED);
				popup.setContentNode(text);
				double largeur = text.getLayoutBounds().getWidth() + 2 * 4.0;
				popup.getRoot().setPrefWidth(largeur+20);

				// Affichage popup
				popup.show(getFenetre().getStage(),getFenetre().getPopupX(),getFenetre().getPopupY());
				
				// Style CSS (après l'affichage sinon skin est null)
				((Parent)popup.getSkin().getNode()).getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
			}
		});
	}
	
	/**
	 * Crée et initialise une fenêtre popup pour l'affichage des messages d'erreur
	 * (voir {@link IHM#afficheMessageErreur(String)})
	 * @return un objet {@link PopOver}
	 */
	PopOver creePopupErreur(){
		PopOver popOver = new PopOver();
		DoubleProperty masterArrowSize = new SimpleDoubleProperty(8);
		ObjectProperty<ArrowLocation> masterArrowLocation = new SimpleObjectProperty<>(ArrowLocation.LEFT_CENTER);
		DoubleProperty masterCornerRadius = new SimpleDoubleProperty(6);
		popOver.arrowSizeProperty().bind(masterArrowSize);
		popOver.arrowLocationProperty().bind(masterArrowLocation);
		popOver.arrowIndentProperty().bind(new SimpleDoubleProperty(2));
		popOver.cornerRadiusProperty().bind(masterCornerRadius);
		popOver.setAnimated(true);
		popOver.setAutoFix(true);
		popOver.setAutoHide(true);
		return popOver;
	}
	
	/**
	 * Affiche une information (texte) sur la fenêtre principale.
	 * Par défaut, le texte est affiché en noir. Les informations déjà affichées ne sont
	 * pas effacées à priori, utiliser {@link #effaceInformation()} si besoin.
	 * @param message le texte à afficher
	 * @param x l'abcisse de la zone de texte
	 * @param y l'ordonnée de la zone de texte
	 * @param gras indique si l'information doit être affichée en gras ou non
	 */
	void afficheInformation(String message, int x, int y, boolean gras){
		this.crayon.deplace(x, y);
		this.crayon.changeEncre("noir", null, 0);
		if(gras){
			this.crayon.dessineTexte("info", message, 15, false, true, FontWeight.BOLD);
		} else {
			this.crayon.ecrisTexte("info", message, 15);
		}
	}
	
	/**
	 * Voir {@link #afficheInformation(String,int,int,boolean)}
	 * @param message le texte à afficher
	 * @param x l'abcisse de la zone de texte
	 * @param y l'ordonnée de la zone de texte
	 * @param gras indique si l'information doit être affichée en gras ou non
	 * @param couleur (optionnel) : la couleur du texte à afficher
	 */
	void afficheInformation(String message, int x, int y, boolean gras, String... couleur){
		this.crayon.deplace(x, y);
		this.crayon.changeEncre((couleur.length==0)?"noir":couleur[0], null, 0);
		if(gras){
			this.crayon.dessineTexte("info", message, 15, false, true, FontWeight.BOLD);
		} else {
			this.crayon.ecrisTexte("info", message, 15);
		}
	}

	// SELECTIONS
	/**
	 * Attend que le joueur demande à réaliser une action (fin du tour, sélection de route, 
	 * ou pioche d'une carte)
	 * @return le nom de l'action choisie par le joueur
	 */
	ActionUtilisateur attenteActionJoueur(){
		ActionUtilisateur action = null;
		String selection = this.fenetre.selectionneForme("rep");
		if(selection.equals("Fin du tour")){
			action = new ActionUtilisateur(TypeActionUtilisateur.FINTOUR,null);
		} else if(selection.startsWith("route")){
			action = new ActionUtilisateur(TypeActionUtilisateur.ROUTE,selection.substring(5,selection.length()));
		} else if(selection.startsWith("piocheCW")){
			action = new ActionUtilisateur(TypeActionUtilisateur.PIOCHECW,selection.substring(8,selection.length()));
		}
		return action;
	}

	/**
	 * Efface une route affichée sur la fenêtre principale
	 * @param route l'objet {@link Route} à effacer
	 */
	void effaceRoute(Route route){
		this.fenetre.effaceFormes("reproute" + route.getNom());
	}
	
	/**
	 * Efface toutes les informations affichées sur la fenêtre principale
	 */
	void effaceInformation(){
		this.fenetre.effaceFormes("info");
		this.fenetre.effaceFormes("cj");
	}
	
	/**
	 * Efface tous les boutons affichés sur la fenêtre principale
	 */
	void effaceBoutons(){
		this.fenetre.effaceFormes("repbouton");
	}

	/**
	 * Modifie la couleur de fond de la fenêtre.
	 * La couleur de fond sert à représenter la couleur du joueur courant
	 * @param couleur la couleur à afficher pour le fond de la fenêtre (i.e. 
	 * la couleur du joueur courant)
	 */
	void afficheCouleurJoueurCourant(String couleur){
		this.fenetre.changeCouleurDeFond(couleur);
	}

	/**
	 * Affiche un message aux joueurs concernant le début du dernier tour.
	 * Le message reste deux secondes affiché avant de disparaître.
	 * @since Incrément 4
	 */
	public void afficheDernierTour(){
		String message = "Dernier tour !";
		this.affichePanneauScore("gris", 30);
		this.afficheInformation(message, PANNEAU_SCORE_X+(450/3), PANNEAU_SCORE_Y+30,true);
		try{
			Thread.sleep(2000);
			this.effaceInformation();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	/**
	 * Affiche un panneau pour l'affichage des scores
	 * @param couleur la couleur de la bordure du panneau
	 * @param hauteur la hauteur du panneau pour l'affichage des informations.
	 * @since Incrément 4
	 */
	void affichePanneauScore(String couleur, int hauteur){
		// Creation du panneau des scores
		this.effaceBoutons();
		this.getFenetre().effaceFormes("cj");
		this.getCrayon().changeEncre(couleur, "blanc", 1);
		this.getCrayon().deplace(PANNEAU_SCORE_X, PANNEAU_SCORE_Y);
		this.getCrayon().dessineRectangle("cj", 450 , 70+hauteur);
	}

	/**
	 * Affiche le numéro et le score du vainqueur de la partie dans le panneau 
	 * des scores ({@link #affichePanneauScore(String, int)})
	 * @param joueur le vainqueur de la partie
	 * @param score le score du joueur (points bonus inclus)
	 * @since Incrément 8
	 */
	void afficheJoueurGagnant(Joueur joueur, int score){
		this.afficheCouleurJoueurCourant(joueur.getCouleur());
		this.affichePanneauScore(joueur.getCouleur(), 30);
		// Message de victoire
		StringBuffer message = new StringBuffer();
		message.append("Le joueur ");
		message.append(joueur.getNumero());
		message.append(" remporte la partie avec ");
		message.append(score);
		message.append(" points.\n\n\t\t\tFélicitations !!!");
		this.afficheInformation(message.toString(), PANNEAU_SCORE_X+20, PANNEAU_SCORE_Y+5,true);
	}
	
	/**
	 * Affiche un tableau récapitulatif des scores des joueurs.
	 * @param joueurs les joueurs de la partie
	 * @param indicesMaxScore les indices des joueurs ayant obtenu le score maximal
	 * @param connexites le nombre de groupes connexes de chaque joueur
	 * @param diametres le diametre de chaque joueur
	 * @param totaux le score (+bonus) de chaque joueur
	 * @since Incrément 8
	 */
	void afficheTableauScores(Joueur[] joueurs, List<Integer> indicesMaxScore, int[] connexites, int[] diametres, int[] totaux) {
		// Couleur du panneau des scores
		String couleur;
		if(indicesMaxScore.size()>1){ // au moins 2 joueurs sont à égalité
			couleur = "violet";
		} else {
			couleur = joueurs[indicesMaxScore.get(0)].getCouleur();
		}
		
		// Dessine le panneau de score
		this.affichePanneauScore(couleur, 30*(joueurs.length));
		
		// Message de victoire
		StringBuffer message = new StringBuffer();
		if(indicesMaxScore.size()>1){ // au moins 2 joueurs sont à égalité
			message.append("Les gagnants sont les joueurs ");
			for(int i=0;i<indicesMaxScore.size();i++){
				message.append(joueurs[indicesMaxScore.get(i)].getNumero()+1);
				if(i<indicesMaxScore.size()-1){
					message.append(", ");
				}
			}
			message.append(" avec "+totaux[indicesMaxScore.get(0)]+" points.");
		} else {
			message.append("Le gagnant est Joueur ");
			message.append(joueurs[indicesMaxScore.get(0)].getNumero()+1);
			message.append(" avec ");
			message.append(totaux[indicesMaxScore.get(0)]);
			message.append(" points.");
		}
		this.afficheInformation(message.toString(), PANNEAU_SCORE_X+20, PANNEAU_SCORE_Y+5,true);
		
		// Affiche l'entête du tableau
		int textePanneauScoreY = PANNEAU_SCORE_Y+50;
		afficheEnteteTableauScores(PANNEAU_SCORE_X, textePanneauScoreY);
		textePanneauScoreY += 20;
		int textePanneauScoreX = PANNEAU_SCORE_X+10;
		
		// Recherche des valeurs respectivement minimum et maximum pour la 
		// densité et le diamètre du graphe. Utilisé pour l'affichage des 
		// bénéficiaires des bonus
		int minimumConnexite = rechercheMinimum(connexites);
		int maximumDiametre = rechercheMaximum(diametres);
		
		// Affiche les résultats de chaque joueur
		for(int i=0;i<joueurs.length;i++){
			this.getCrayon().deplace(textePanneauScoreX, textePanneauScoreY+5);
			this.getCrayon().changeEncre(joueurs[i].getCouleur(), joueurs[i].getCouleur(), 1);
			this.getCrayon().dessineRectangle("jc", 8, 15);
			this.afficheInformation("Joueur "+(i+1), textePanneauScoreX+10, textePanneauScoreY,false);
			this.afficheInformation(""+joueurs[i].getScore(),textePanneauScoreX+110,textePanneauScoreY,true);
			this.afficheInformation(""+connexites[i],textePanneauScoreX+190,textePanneauScoreY,true);
			if(minimumConnexite != 0 && connexites[i] == minimumConnexite){
				this.afficheInformation(TICK,textePanneauScoreX+210,textePanneauScoreY,true,"vert");
			}
			this.afficheInformation(""+diametres[i],textePanneauScoreX+280,textePanneauScoreY,true);
			if(diametres[i]==maximumDiametre){
				this.afficheInformation(TICK,textePanneauScoreX+300,textePanneauScoreY,true,"vert");
			}
			this.afficheInformation(""+totaux[i], textePanneauScoreX+370, textePanneauScoreY, true);
			textePanneauScoreY +=20;
			this.getCrayon().deplace(textePanneauScoreX+10, textePanneauScoreY);
			this.getCrayon().dessineLigne("l", 420, 0, 1);
		}
	}

	/**
	 * Recherche de la valeur maximale dans un tableau
	 * @param tableau un tableau de valeurs
	 * @return la valeur maximale présente dans le tableau
	 * @since Incrément 8
	 */
	int rechercheMaximum(int[] tableau){
		int maxD = 0;
		if(tableau.length>0){
			maxD = tableau[0];
		}
		for(int diametre : tableau){
			if(maxD < diametre){
				maxD = diametre;
			}
		}
		return maxD;
	}

	/**
	 * Recherche de la valeur minimale
	 * @param tableau un tableau de valeurs
	 * @return la valeur minimum présente dans le tableau
	 * @since Incrément 8
	 */
	int rechercheMinimum(int[] tableau){
		int minC = 0;
		if(tableau.length>0){
			minC = tableau[0];
		}
		for(int connexite : tableau){
			if((connexite != 0) && minC > connexite){
				minC = connexite;
			}
		}
		return minC;
	}

	/**
	 * Affiche l'entête du tableau des scores aux positions fournies
	 * @param xInit abscisse de la position d'affichage de l'entête
	 * @param yInit ordonnée de la position d'affichage de l'entête
	 * @since Incrément 8
	 */
	void afficheEnteteTableauScores(int xInit, int yInit){
		this.afficheInformation("Score",xInit+100,yInit,true);
		this.afficheInformation("Connexite",xInit+160,yInit,true);
		this.afficheInformation("Diametre",xInit+250,yInit,true);
		this.afficheInformation("Total",xInit+360,yInit,true);
	}
}
