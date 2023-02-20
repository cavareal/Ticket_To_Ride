package lib; 

/**
 * La classe ActionUtilisateur est utilisée pour récupérer les actions
 * effectuées par le joueur et les données correspondantes. L'action de
 * l'utilisateur est identifiée par une valeur de l'énumération
 * {@link TypeActionUtilisateur} et est liée à une valeur, appelée
 * {@code paramètre} sous la forme d'une chaîne de caractères.
 */

public class ActionUtilisateur{
    /**
	 * Le type de l'action réalisée par l'utilisateur
	 */
	TypeActionUtilisateur type;
	
	/**
	 * Les données produites par l'action de l'utilisateur
	 */
	String parametre;
	
	/**
	 * Crée une action utilisateur
	 * @param type le type de l'action
	 * @param param les données liées à l'action
	 */
	ActionUtilisateur(TypeActionUtilisateur type, String param){
		this.type = type;
		this.parametre = param;
	}
	
	// ACCESSEURS
	TypeActionUtilisateur getType(){
		return this.type;
	}
	
	String getParametre(){
		return this.parametre;
	}

}