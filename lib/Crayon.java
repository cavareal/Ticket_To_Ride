package lib;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * La classe Crayon permet de dessiner sur la fenêtre de l'application.
 */

public class Crayon {
    
	/**
	 * La fenêtre principale du jeu
	 */
	Fenetre fenetre;
	/**
	 * L'abcisse de la position courante du crayon
	 */
	double x;
	/**
	 * L'ordonnée de la position courante du crayon
	 */
	double y;

	/**
	 * La couleur de fond de la zone dessinée par le crayon
	 */
	Color couleurFond = Color.WHITE;
	
	/**
	 * La couleur de trait du crayon
	 */
	Color couleurTrait = Color.BLACK;
	
	// ACCESSEURS
	Fenetre getFenetre(){
		return this.fenetre;
	}

	double getX(){
		return this.x;
	}

	double getY(){
		return this.y;
	}

	Color getCouleurFond(){
		return this.couleurFond;
	}

	Color getCouleurTrait(){
		return this.couleurTrait;
	}

	// METHODES DE DEPLACEMENT

	/**
	 * Positionne le crayon aux coordonnées fournies
	 * @param x l'abscisse
	 * @param y l'ordonnée
	 */
	void deplace(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Déplace le crayon  par rapport au delta fourni 
	 * @param dx le delta en abcisse
	 * @param dy le delta en ordonnée
	 */
	void translate(double dx, double dy){
		this.x += dx;
		this.y += dy;
	}

	// METHODE DE CHANGEMENT DE COULEUR
	
	/**
	 * Change la couleur du crayon et son opacité
	 * @param couleurTrait la couleur du trait
	 * @param couleurFond la couleur de fond
	 * @param opaciteFond l'opacité entre 0 et 1
	 */
	void changeEncre(String couleurTrait, String couleurFond, double opaciteFond){
		this.couleurTrait = Fenetre.creeCouleur(couleurTrait, 1.0);
		this.couleurFond = Fenetre.creeCouleur(couleurFond, opaciteFond);
	}

	// METHODES DE DESSIN

	/**
	 * Associe le crayon à une fenêtre
	 * @param fenetre la fenêtre sur laquelle dessiner les formes
	 */
	void dessineSur(Fenetre fenetre){
		this.fenetre = fenetre;
	}
	
	/**
	 * Dessine une forme de type {@link javafx.scene.shape.Shape} ou
	 * {@link javafx.scene.Node}
	 * @param id l'identifiant de la forme
	 * @param forme la forme à dessiner
	 */
	void dessineForme(String id, Node forme){
		forme.setId(id);
		forme.setTranslateX(this.x);
		forme.setTranslateY(this.y);
		this.fenetre.dessineForme(forme);
	}

	/**
	 * Crée un rectangle sans tenir compte de sa couleur de trait
	 * @param id l'identifiant du rectangle
	 * @param largeur la largeur du rectangle
	 * @param hauteur la hauteur du rectangle
	 * @return un rectangle de la couleur de fond actuelle du crayon
	 */
	Rectangle creeRectangle(String id, double largeur, double hauteur){
		Rectangle rectangle = new Rectangle();
		rectangle.setWidth(largeur);
		rectangle.setHeight(hauteur);
		rectangle.setFill(this.couleurFond);
		return rectangle;
	}

	/**
	 * Dessine un rectangle dont la couleur de trait est celle du crayon
	 * @param id l'identifiant du rectangle
	 * @param largeur la largeur du rectangle
	 * @param hauteur la hauteur du rectangle
	 */
	void dessineRectangle(String id, double largeur, double hauteur){
		Rectangle rectangle = creeRectangle(id, largeur, hauteur);
		rectangle.setStroke(this.couleurTrait);
		rectangle.setStrokeWidth(3);
		this.dessineForme(id, rectangle);
	}

	/**
	 * Dessine une ligne dont la couleur de trait est celle du crayon.
	 * La ligne est dessinée à partir de la position courante du crayon.
	 * @param id l'identifiant de la ligne
	 * @param dx l'abcisse du point d'arrivée de la ligne
	 * @param dy l'ordonnée du point d'arrivée de la ligne
	 * @param epaisseur l'épaisseur du trait de la ligne
	 */
	void dessineLigne(String id, double dx, double dy, double epaisseur){
		Line ligne = new Line();
		ligne.setEndX(dx);
		ligne.setEndY(dy);
		ligne.setStrokeWidth(epaisseur);
		ligne.setStroke(this.couleurTrait);
		this.dessineForme(id, ligne);
	}

	/**
	 * Dessine une cerle dont la couleur de fond et la couleur du trait sont
	 * celles du crayon. Le cercle à une épaisseur par défaut de 3 points.
	 * @param id l'identifiant du cercle
	 * @param rayon le rayon du cercle
	 */
	void dessineCercle(String id, double rayon){
		Circle cercle = new Circle();
		cercle.setRadius(rayon);
		cercle.setFill(this.couleurFond);
		cercle.setStroke(this.couleurTrait);
		this.dessineForme(id, cercle);
	}

	/**
	 * Dessine un chemin SVG dont la couleur de fond et la couleur du trait sont
	 * celles du crayon.
	 * @param id l'identifiant du chemin
	 * @param contour les points du chemin
	 */
	void dessineChemin(String id, String contour){
		SVGPath svg = new SVGPath();
		svg.setFill(this.couleurFond);
		svg.setContent(contour);
		svg.setFill(this.couleurFond);
		svg.setStroke(this.couleurTrait);
		this.dessineForme(id, svg);
	}

	/**
	 * Dessine un texte dont la couleur de trait est celle du crayon.
	 * @param id l'identifiant du texte
	 * @param texte le message à afficher
	 * @param taille la taille de la police de caractères
	 * @param centre indique si le texte est centré par rapport à la zone de
	 *            texte
	 * @param translate indique si la position du crayon doit être modifiée
	 *            suite à l'écriture du texte
	 * @param fontWeight précise le "poids" de la police (voir
	 *            {@link FontWeight})
	 */
	void dessineTexte(String id, String texte, int taille, boolean centre, boolean translate, FontWeight fontWeight){
		double marge = 4.0;
		Text text = new Text();
		text.setFont(Font.font("System",fontWeight,taille));
		text.setFill(this.couleurTrait);
		text.setText(texte);
		text.setY(2 * taille - text.getBaselineOffset());
		text.setX(marge);
		double largeur = text.getLayoutBounds().getWidth() + 2 * marge;
		Rectangle rectangle = creeRectangle(id, largeur, taille);
		if (centre) {
			text.setTextAlignment(TextAlignment.CENTER);
			this.translate(-largeur / 2, -taille / 2);
		}
		this.dessineForme(id, rectangle);
		this.dessineForme(id, text);
		if (translate) {
			this.translate(largeur + 2 * marge, 0);
		}
	}

	/**
	 * Dessine un texte dont la couleur de trait est celle du crayon.
	 * La position du crayon n'est pas modifiée suite à l'écriture du texte
	 * @param id l'identifiant du texte
	 * @param texte le message à écrire
	 * @param taille la taille de la police de caractères à utiliser
	 * @param centre indique si le texte est centré par rapport à la zone de texte
	 */
	void dessineTexte(String id, String texte, int taille, boolean centre){
		this.dessineTexte(id, texte, taille, centre, false,null);
	}

	/**
	 * Ecris un texte sur la fenêtre.
	 * Le texte n'est pas centré par rapport à la zone d'écriture et la position du crayon
	 * est modifiée suite à l'écriture du texte
	 * @param id l'identifiant du texte
	 * @param texte le message à écrire
	 * @param taille la taille de la police de caractères à utiliser
	 */
	void ecrisTexte(String id, String texte, int taille){
		this.dessineTexte(id, texte, taille, false, true,null);
	}
	
	/**
	 * Initialise un {@link Text} dont la couleur de trait est celle du crayon.
	 * @param id l'identifiant du texte
	 * @param texte le message à écrire
	 * @param taille la taille de la police de caractères à utiliser
	 * @param centre indique si le texte est centré par rapport à la zone de texte
	 * @param gras indique si le texte est à produire en gras ou non
	 * @return un objet {@link Text}
	 */
	Text creerTexte(String id, String texte, int taille, boolean centre, boolean gras){
		double marge = 4.0;
		Text text = new Text();
		if(gras){
			text.setFont(Font.font("System",FontWeight.BOLD,taille));
		} else {
			text.setFont(Font.font("System",FontWeight.NORMAL,taille));
		}
		
		text.setFill(this.couleurTrait);
		text.setText(texte);
		text.setY(2 * taille - text.getBaselineOffset());
		text.setX(marge);
		if (centre) {
			text.setTextAlignment(TextAlignment.CENTER);
		}
		return text;
	}

}
