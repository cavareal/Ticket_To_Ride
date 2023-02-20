package lib;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


/**
 * La classe Fenetre est la fenetre principale du jeu. La fenetre principale permet
 * d'afficher des formes, de rechercher une forme dessinée sur la fenetre, de sélectionner
 * une forme dessinée sur la fenêtre ou de supprimer une forme dessinée sur la fenêtre.
 */


public class Fenetre {
    /**
	 * Constantes utilisées pour la fonctionnalité de zoom
	 */
	public static final int SCENE_HAUTEUR_MARGE = 50;
	public static final int SCENE_LARGEUR_MARGE = 50;
	public static final double ZOOM_FACTOR_STEP = 1.02;


	/**
	 * Groupe racine contenant l'ensemble des éléments graphiques de la fenêtre
	 */
	Group groupeFormes;

	/**
	 * Liste les différents éléments affichés sur la fenêtre
	 */
	List<Node> listeFormes;

	/**
	 * Suite à la sélection d'une forme sur la fenêtre, contient la forme sélectionnée,
	 * {@code null} sinon.
	 */
	Node formeSelectionnee;
	
	/**
	 * Conteneur principal de la fenêtre
	 */
	Stage stage;
	
	/**
	 * Graphe de scène qui contient l'ensemble du contenu affichée sur la fenêtre
	 */
	Scene scene;

	/**
	 * Abcisse pour le positionnement d'une fenêtre de popup lorsqu'un évènement
	 * génère un message d'erreur
	 */
	double popupX;

	/**
	 * Ordonnée pour le positionnement d'une fenêtre de popup lorsqu'un
	 * évènement génère un message d'erreur
	 */
	double popupY;

	/**
	 * Contient le ratio utilisé pour l'affichage des éléments graphiques.
	 * Est modifié par le zoom 
	 */
	DoubleProperty zoomProperty;
	
	/**
	 * Fond de carte pour l'affichage des états, villes et routes.
	 */
	Rectangle fondCarte;
	
	// ACCESSEURS
	Group getGroupeFormes(){
		return this.groupeFormes;
	}

	List<Node> getListeFormes(){
		return this.listeFormes;
	}

	Node getFormeSelectionnee(){
		return this.formeSelectionnee;
	}

	Stage getStage(){
		return this.stage;
	}

	Scene getScene(){
		return this.scene;
	}

	double getPopupX(){
		return this.popupX;
	}

	double getPopupY(){
		return this.popupY;
	}
	
	double getRatio(){
		return this.zoomProperty.get();
	}
	
	DoubleProperty getZoomProperty() {
		return this.zoomProperty;
	}
	
	/**
	 * Getting rid of JavaFX unexpected errors.
	 * Should be deactivated when debugging applications.
	 * @param t the thread that causes an error
	 * @param e the exception thrown
	 */
	private static void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
        	// Ignore errors but kept for future use
        } else {
        	// Ignore errors but kept for future use
        }
    }

	/**
	 * Affiche la fenêtre principale du jeu
	 * @param title le titre de la fenêtre
	 * @param largeur la largeur de la fenêtre
	 * @param hauteur la hauteur de la fenêtre
	 * @param echelle le grossissement de la fenêtre
	 * @param couleurFond la couleur de fond de la fenêtre
	 */
	void affiche(String title, int largeur, int hauteur, double echelle, String couleurFond) {
		this.listeFormes = new ArrayList<>();
		// Initialisation du ToolKit JavaFX
		new JFXPanel();
		Platform.runLater(() -> {
			// Construction du groupe racine
			this.groupeFormes = new Group();
			Scale scale = new Scale();
			scale.setPivotX(0);
			scale.setPivotY(0);
			
			// Gestion du zoom (zoomProperty est la valeur de l'échelle)
			this.zoomProperty = new SimpleDoubleProperty(echelle);
			scale.setX(this.zoomProperty.get());
			scale.setY(this.zoomProperty.get());

			// Application du zoom lorsque la propriété est modifiée
			this.zoomProperty.addListener(new InvalidationListener() {
	            @Override
	            public void invalidated(Observable param) {
	            	boolean agrandie = (scale.getX()<((DoubleProperty)param).get());
	            	// Modification de l'échelle
	            	scale.setX(getZoomProperty().get());
	            	scale.setY(getZoomProperty().get());
	            	// Adaptation de la taille de la fenêtre au contenu
	            	if(((DoubleProperty)param).get()!=IHM.DISPLAY_RATIO){
		            	if(agrandie){
		            		getStage().setWidth(getStage().getWidth() * ZOOM_FACTOR_STEP);
		            		getStage().setHeight(getStage().getHeight() * ZOOM_FACTOR_STEP);
		            	} else {
		            		getStage().setWidth(getStage().getWidth() / ZOOM_FACTOR_STEP);
		            		getStage().setHeight(getStage().getHeight() / ZOOM_FACTOR_STEP);
		            	}
	            	}
	            }
	        });
			
			this.groupeFormes.getTransforms().add(scale);
			
			// Construction de la scène
			this.scene = new Scene(this.groupeFormes, largeur, hauteur);
			this.scene.setFill(creeCouleur(couleurFond, 1.0));

			// Capture des évènements de scrolling
			this.scene.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
	            @Override
	            public void handle(ScrollEvent event) {
	                if (event.getDeltaY() > 0) {
	                	getZoomProperty().set(getZoomProperty().get() * ZOOM_FACTOR_STEP);
	                } else if (event.getDeltaY() < 0) {
	                	getZoomProperty().set(getZoomProperty().get() / ZOOM_FACTOR_STEP);
	                }
	            }
	        });
			// Capture des évènements du clavier
			// Le facteur de zoom est remis à la valeur par défaut lorsque
			// l'utilisateur tape sur la touche "HOME"
			this.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent key) {
					if(key.getCode() == KeyCode.ESCAPE){
						getZoomProperty().set(IHM.DISPLAY_RATIO);
						getStage().setWidth(largeur);
						getStage().setHeight(hauteur);
					}
				}
			});
			
			this.stage = new Stage();
			this.stage.setResizable(false);

			// Fond de carte
			dessineFondCarte(couleurFond);
			this.groupeFormes.getChildren().add(this.fondCarte);
			
			// Construction de la fenêtre
			this.stage.setTitle(title);
			this.stage.setScene(this.scene);
			this.stage.setOnCloseRequest(event -> {
				System.exit(0);
			});
			
			// Capture des évènements "souris" lors d'un clic sur une forme
			this.groupeFormes.setOnMouseClicked(event -> {
				if (event.getTarget() instanceof Shape) {
					this.formeSelectionnee = (Shape) event.getTarget();
				} else {
					this.formeSelectionnee = (Node) event.getTarget();
				}
				this.popupX = event.getScreenX();
				this.popupY = event.getScreenY()-20;
			});
			
			// Affichage de la fenetre
			this.stage.show();
		});
	}

	/**
	 * Dessine un rectangle pour isoler la carte du plateau du fond de la fenêtre.
	 * Permet de simuler une bordure de 20 pixels sur les bords de l'interface
	 * pour afficher notamment la couleur du joueur.
	 * @param couleurFond la couleur de fond de la carte
	 */
	void dessineFondCarte(String couleurFond){
		this.fondCarte = new Rectangle();
		this.fondCarte.setId("back");
		this.fondCarte.setWidth(0.785*this.scene.getWidth());
		this.fondCarte.setHeight(0.75*this.scene.getHeight());
		this.fondCarte.setFill(Fenetre.creeCouleur(couleurFond, 1.0));
		this.fondCarte.setX(10);
		this.fondCarte.setY(10);
		this.fondCarte.setArcWidth(20);
		this.fondCarte.setArcHeight(20);
	}

	/**
	 * Dessine une forme sur la fenêtre
	 * @param forme la forme à dessiner
	 */
	void dessineForme(Node forme) {
		this.listeFormes.add(forme);
		Platform.runLater(() -> {
			if(forme!=null) {
				try{
					this.groupeFormes.getChildren().add(forme);
				} catch(Exception npe) {
					// FIXME can't figure out what make the exception thrown...
				}
			}
		});
	}

	/**
	 * Recherche les formes dessinées sur la fenêtre à partir d'un identifiant
	 * @param id l'identifiant des formes recherchées
	 * @return les formes si l'identifiant est trouvé, {@code null} sinon.
	 */
	List<Node> rechercheFormes(String id) {
		List<Node> list = this.groupeFormes.getChildrenUnmodifiable().filtered(f -> 
			f.getId().startsWith(id));
		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}
	
	/**
	 * Recherche une forme dessinée sur la fenêtre à partir de son identifiant
	 * @param id l'identifiant de la forme recherchée
	 * @return la forme si l'identifiant est trouvé, {@code null} sinon.
	 */
	Node rechercheForme(String id) {
		List<Node> list = rechercheFormes(id);
		if (list==null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * Efface une forme dessinée sur la fenêtre
	 * @param id l'identifiant de la forme à effacer
	 */
	void effaceFormes(String id) {
		Platform.runLater(() -> {
			Node forme = this.rechercheForme(id);
			while (forme != null) {
				try{
					this.groupeFormes.getChildren().remove(forme);
				} catch(Exception npe) {
					// FIXME can't figure out what make the exception thrown...
				}
				this.listeFormes.remove(forme);
				forme = this.rechercheForme(id);
			}
		});
	}

	/**
	 * Attend la sélection d'une forme spécifique par l'utilisateur (clic souris).
	 * Les formes pouvant être sélectionnées sont filtrées par un prefixe.
	 * Cette méthode est "bloquante" tant que l'utilisateur n'a pas sélectionné la forme
	 * désirée.
	 * @param prefixe le prefixe de l'identifiant de la forme.
	 * @return l'identifiant de la forme sélectionnée tronquée du préfixe
	 */
	String selectionneForme(String prefixe) {
		this.formeSelectionnee = null;
		while ((this.formeSelectionnee == null) || (this.formeSelectionnee.getId() == null)
				|| !this.formeSelectionnee.getId().startsWith(prefixe)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
				System.err.println("Thread management problems : "+ie.getMessage());
			}
		}
		return this.formeSelectionnee.getId().substring(prefixe.length());
	}

	/**
	 * Génère une couleur en Java ({@link java.awt.Color}) à partir du nom de la
	 * couleur et de la correspondance fournie par {@link Donnees#COULEURS_IHM}
	 * @param couleur la couleur demandée
	 * @param opacite l'opacité de la couleur (variant de 0 à 1)
	 * @return la couleur Java correspondant à la couleur demandée ou "blanc" si
	 *         la couleur fournie est nulle.
	 */
	static Color creeCouleur(String couleur, double opacite) {
		if (couleur == null) {
			couleur = "blanc";
			opacite = 0;
		}
		if (Donnees.COULEURS_IHM.containsKey(couleur)) {
			couleur = Donnees.COULEURS_IHM.get(couleur);
		}
		return Color.web(couleur, opacite);
	}

	/**
	 * Modifie la couleur de fond de la scene qui correspond à la fenêtre
	 * affichée.
	 * La couleur affichée a une opacité par defaut de 0.75.
	 * @param couleur la couleur de fond de la fenêtre
	 */
	void changeCouleurDeFond(String couleur){
		this.getScene().setFill(creeCouleur(couleur, .75));
	}

}
