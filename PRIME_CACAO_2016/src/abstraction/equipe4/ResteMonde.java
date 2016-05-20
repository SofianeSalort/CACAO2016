package abstraction.equipe4;

import abstraction.fourni.*;
import abstraction.commun.*;


public class ResteMonde implements Acteur, ITransformateur {
	private String nom;
	private double demande;
	private Producteur p;

	// constructeur du reste du monde
	public ResteMonde(Producteur p) {
		this.nom="Reste du monde transoformateurs";
		this.demande=0.0;
		this.p=p;
	}

	//getter demande du reste du monde
	public double getDemande() {
		return this.demande;
	}
	
	//getter du producteur auquel s'addresse la demande

	public Producteur getP() {
		return this.p;
	}

	//setter de la demande du reste du monde
	public void setDemande(double demande) {
		this.demande = demande;
	}
	
	//gestion de la vente:
	public void venteResteMonde (double demande){
		// MAJ de la tréso
		this.getP().getTreso().getFond().setValeur(this, this.getP().getTreso().getFond().getValeur()+this.getP().getMarche().getCours()*demande);
		//MAJ du stock
		this.getP().getStock().reductionStock(demande);
	}

	// getter du nom du reste du monde
	public String getNom() {
		return this.nom;
	}


	public void next() {
		// TODO Auto-generated method stub

	}

	// quantité demandée du producteur p
	public double annonceQuantiteDemandee(IProducteur p) {
		return (this.getP().getTransformateurs().get(1).annonceQuantiteDemandee(this.getP())
				+this.getP().getTransformateurs().get(2).annonceQuantiteDemandee(this.getP()))*(82.0+Math.random()*2.0)/17.0;
	}

	// 
	public void notificationVente(IProducteur p) {


	}





}
