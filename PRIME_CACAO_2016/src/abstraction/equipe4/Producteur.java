package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;
import java.util.ArrayList;

public class Producteur implements Acteur,IProducteur{

	private String nom; 
	private Stock stock; 
	private Journal journal;
	private Tresorerie treso;
	private ProductionBiannuelle prodBiannu;

	private MarcheProducteur marche;
	private ArrayList<ITransformateur> transformateurs;

	//Constructeur de l'acteur Producteur 2

    public Producteur(Monde monde) {
       this.nom = Constantes.NOM_PRODUCTEUR_2;
	   this.treso = new Tresorerie(this);
	   this.stock = new Stock(this);
       this.journal = new Journal("Journal de "+this.nom);
       this.prodBiannu=new ProductionBiannuelle(this,1200000);
       
       this.transformateurs= new ArrayList<ITransformateur>();
       this.getTransformateurs().add(new ResteMonde(this));
       Monde.LE_MONDE.ajouterActeur((Acteur)(this.getTransformateurs().get(0)));
       
       Monde.LE_MONDE.ajouterJournal(this.journal);

	}


	// getter

	public Journal getJournal() {
		return this.journal;
	}

	public String getNom() {
		return this.nom;
	}


	public ProductionBiannuelle getProdBiannu() {
		return this.prodBiannu;
	}

	public Stock getStock() {
		return this.stock;
	}

	public ArrayList<ITransformateur> getTransformateurs() {
		return this.transformateurs;
	}

	public Tresorerie getTreso() {
		return this.treso;
	}

	public MarcheProducteur getMarche() {
		return this.marche;
	}



	//Ajout des clients à la liste transformateurs
	public void ajoutClient(Acteur a){
		if (!this.getTransformateurs().contains(((ITransformateur)a))){
			this.getTransformateurs().add((ITransformateur)a);
		}
	}

	public void setMarche(MarcheProducteur m){
		this.marche=m;
	}

	// le next du producteur 2	
	public void next(){

		//production semi annuelle
		if (Monde.LE_MONDE.getStep()%12==1){
			// actualisation de toutes les variables du à la récolte semestrielle.
			this.getProdBiannu().production();
			this.getJournal().ajouter("Production de semi annuelle de " + this.getProdBiannu().getProductionFinale() + " en comptant les pertes de "+ this.getProdBiannu().getPerteProduction());
		}
		// modifications des stocks pour causes naturelles et prise en compte des couts de stock
		this.getStock().gererLesStock();
		// Comandes
		for (ITransformateur t : this.transformateurs){
			double qtVendu = t.annonceQuantiteDemandee(this);
			t.notificationVente(this);
			this.venteRealisee(qtVendu, (Acteur)t);
		}
	}


	// retourne un double valant la quantité disponible 
	// pour chaque transformateur a chaque step
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return (this.getStock().getStockCacao().getValeur()/(13-Monde.LE_MONDE.getStep()%12));
	}


	//Modification du stock et de la tresorerie suite a une vente
	public void venteRealisee(double qtVendue,Acteur a) {
		this.vente(qtVendue);
		this.getStock().reductionStock(qtVendue);
		this.getJournal().ajouter("Vente de " + qtVendue+" auprès de " + a.getNom() + " au step numéro "+ Monde.LE_MONDE.getStep());
	}

	// ajout de le somme récolté à la trésorerie après une vente
	public void vente(double qtVendue){		
		this.getTreso().getFond().setValeur(this, this.getTreso().getFond().getValeur()+ qtVendue*this.getMarche().getCours());
	}

// à modifier
/*
	//Ventes réalisées auprès du transformateur "Le reste du Monde"
	public void venteResteMonde(){
		//	double alea = Math.random()*(0.9-0.87)+0.87;
	}
*/
	

	public void notificationVente(CommandeProduc c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double annoncePrix() {
		// TODO Auto-generated method stub
		return 0;
	}

}
