package abstraction.equipe2;

import abstraction.fourni.Historique;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

public class Banque {
	
	private double banque;
	private Indicateur tresorerie;
		
	
	public Banque() {
	this.banque=Constante.TRESORERIE_INITIALE;;
	}
	
	public void MiseAJourHistorique(Nestle nestle, int etape) {
		this.tresorerie.getHistorique().ajouter(nestle, etape, this.getBanque());
	}
	
	public double getBanque() {
		return banque;
	}
	
	public Indicateur getTresorerie() {
		return tresorerie;
	}

	public double CoutsFixes() {
		return Constante.CHARGES_FIXES;
	}
	
	public void ajouter(double quantit�) {
		this.banque+=quantit�;
	}
	
	
	


	public void retirer(double quantite) {
		this.banque-=quantite;
	}
}

