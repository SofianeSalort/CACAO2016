package abstraction.equipe1;

import java.util.HashMap;
import java.util.List;

import abstraction.commun.ITransformateurD;
import abstraction.commun.ITransformateurP;
import abstraction.commun.MarcheProducteur;

/**
 * Classe pour calculer la quantite mise en vente pour chaque transformateur
 */
public class IntelligenceEconomique {

	/** Liste des transformateurs en relation avec notre producteur */
	private List<ITransformateurP> transformateurs;

	/** Coefficients de somme unite correspondant a l'importance des ventes realisees */
	private HashMap<ITransformateurP,Double> importanceTransformateurs;

	/** Stock de notre producteur */
	private Stock stock;

	/** Quantites mises en vente pour chaque transformateur pour le step en cours */
	private HashMap<ITransformateurP,Double> quantitesMisesEnVente;

	/**
	 * Coefficients associes au stock produit a differentes dates.
	 * Le cacao le plus ancien est en tete.
	 */
	private final double[] coeffPerissabilite = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
	
	public IntelligenceEconomique(List<ITransformateurP> t, Stock s) {
		this.transformateurs = t;
		this.importanceTransformateurs = new HashMap<ITransformateurP,Double>();
		this.stock = s;
		this.quantitesMisesEnVente = new HashMap<ITransformateurP,Double>();
	}
	
	/**
	 * Prend en compte l'existence du transformateur apres son ajout a la liste des transformateurs pour l'ajouter aux
	 * HashMap internes.
	 */
	public void prendreEnCompte(ITransformateurP t) {
		this.quantitesMisesEnVente.put(t, 0.0);
		// Mise a jour des coefficients d'importance pour avoir une somme unite
		for (ITransformateurP tr : this.transformateurs) {
			this.importanceTransformateurs.put(tr, 1.0/this.transformateurs.size());
		}
	}
	
	private double calculerEnvieDeVendre() {
		double rapport = (MarcheProducteur.LE_MARCHE.getCours()-MarcheProducteur.PRIX_MINIMUM)/(MarcheProducteur.PRIX_MAXIMUM-MarcheProducteur.PRIX_MINIMUM);
		return (1.0 + Math.sin((rapport - 0.5) * Math.PI)) / 2.0;
	}
	
	private double calculerBesoinDeVendre() {
		double besoin = 0.0;
		for (int i = 0; i<this.coeffPerissabilite.length; i++) {
			besoin += this.coeffPerissabilite[i]*this.stock.getStockParStep(i);
		}
		return besoin;
	}
	
	private double calculerOffreTotale() {
		double envie = calculerEnvieDeVendre();
		double besoin = calculerBesoinDeVendre();
		return besoin*(1.0-envie) + this.stock.getQuantite()*envie;
	}
	
	private void actualiserImportanceTransformateurs() {
		double totalDemandes = 0.0;
		for (ITransformateurP t : this.transformateurs) {
			totalDemandes += t.annoncePrix()*t.annonceQuantiteDemandee();
		}
		
		if(totalDemandes != 0.0) {
			for (ITransformateurP t : this.transformateurs) {
				double valeur = t.annoncePrix()*t.annonceQuantiteDemandee()/totalDemandes;
				double valeurAmortie = this.importanceTransformateurs.get(t)*0.8 + valeur*0.2;
				this.importanceTransformateurs.put(t, valeurAmortie);
			}
		}
	}
	
	private void actualiserQuantitesMisesEnVente() {
		double offreTotale = calculerOffreTotale();
		for (ITransformateurP t : this.transformateurs) {
			double valeur = offreTotale*this.importanceTransformateurs.get(t);
			this.quantitesMisesEnVente.put(t, valeur);
		}
	}
	
	public void actualiser() {
		this.actualiserImportanceTransformateurs();
		this.actualiserQuantitesMisesEnVente();
	}
	
	public double donnerQuantiteMiseEnVente(ITransformateurP t) {
		return this.quantitesMisesEnVente.get(t);
	}
}
