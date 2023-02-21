package lib;

/**
 * La classe TypeActionUtilisateur sert à identifier une action de l'utilisateur
 * sur l'interface graphique de manière simplifiée.
 * Dans la version actuelle du jeu des ADR, l'interface graphique offre trois 
 * actions à l'utilisateur : (1) la fin du tour ({@code FINTOUR}), la sélection
 * d'une route ({@code ROUTE}), et la pioche d'une carte wagon ({@code PIOCHECW}).
 * @since Incrément 1
 */
public enum TypeActionUtilisateur {
	FINTOUR,
	ROUTE,
	PIOCHECW;
}

