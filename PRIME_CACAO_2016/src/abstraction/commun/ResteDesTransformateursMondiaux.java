package abstraction.commun;


import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;

/**
 * Cette classe repr�sente le reste des tansformateurs mondiaux.
 * le reste des tansformateurs mondiaux fait office de transformateur.
 * Les producteurs vendent du cacao au reste des tansformateurs mondiaux.
 * le reste des tansformateurs mondiaux ont une demande proportionnelle � la demande des transformateurs r�els
 *
 * @author Equipe 4 avec l'aide de l'�quipe 1.
 */

public class ResteDesTransformateursMondiaux implements Acteur, ITransformateur {

	private List<ITransformateur> transformateurs;
	
	
	
	public ResteDesTransformateursMondiaux(){
		this.transformateurs = new ArrayList<ITransformateur>();
		
	}

	/**
	 * Ajoute un transformateur r�els � la liste des transformateurs
	 * servant � calculer la quantit� mise en vente.
	 */
	public void ajouterTransformateur(ITransformateur transformateur) {
		if (!this.transformateurs.contains(transformateur) && transformateur != this) {
			this.transformateurs.add(transformateur);
		}
	}

	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		double qt =0;
		for (ITransformateur t : this.transformateurs) {
			qt += t.annonceQuantiteDemandee(p);
		}

		// le reste du monde repr�sente 83% du march�
		// on rajoute un peu d'al�atoire pour mieux coller � la r�alit�.
		double pourcentage = 82.0+Math.random()*2.0;
		// 17% correspond aux transformateurs simul�s par les autres groupe.
		// Donc en respectant les ratio on a 
		return qt / 17.0 * pourcentage;
	}

	@Override
	public void notificationVente(IProducteur p) {
		// On ne simule aucun �tat concernant le reste du monde
	}

	@Override
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_3;
	}

	@Override
	public void next() {
		// On ne simule aucun �tat concernant le reste du monde
	}

}
