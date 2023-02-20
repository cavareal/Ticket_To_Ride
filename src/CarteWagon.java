package src;

public class CarteWagon {
    /**
 * La classe CarteWagon repr�sente une carte "wagon" du jeu. Une carte "wagon"
 * dispose d'une couleur parmis les huit couleurs du jeu (
 * {@link Donnees#COULEURS_WAGON}) ou {@code null} si c'est une locomotive.
 * 
 * @before Incr�ment 5
 * @ACOMPLETER
 */
	// Attribus
    String couleur;
    

	// A
    
    String getCouleur(){
        return couleur;
    }

	 String setCouleur(String couleur){
	 	return this.couleur = couleur;
	 }


	// Constructeur
    
    CarteWagon(String couleur){
    		this.couleur = couleur;
    }

    
	// M�thodes 
	/**
	 * Indique si la couleur de la carte est �gale � la couleur pass�e en
	 * param�tre
	 * @param couleur la couleur � tester
	 * @return {@code vrai} si la couleur de la carte est �gale � la couleur
	 *         pass�e en param�tre, {@code faux} sinon.
	 */
	
    boolean estDeCouleur(String couleur){
    	if (couleur == null && this.couleur==null){
    		return true;
    	}
    	else if (couleur == null && this.couleur!=null){
    		return false;
    	}
    	return couleur.equals(this.couleur);
    }


    
    boolean estLocomotive(){
    	return this.couleur==null ;
    }

	/**
	 * @return une repr�sentation textuelle de la carte
	 */
	// d�clarer la m�thode versChaine
    
   String versChaine(){
    	if (this.estLocomotive() == true){
    		return "Locomotive"; 
    	}
    	return "Wagon " + couleur;
   }
}
