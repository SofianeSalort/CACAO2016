package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;

public class Tresorerie {
	// argent totale dans la cagnote
	private Indicateur fond;
	// nous
	private Producteur prod;
	// constructeur de la tr�sorerie
	public Tresorerie(Producteur p) {
		this.fond = new Indicateur("Fond de" + p.getNom(),p,0.0);
		this.prod= p;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	}
	
	// getter production
	public Producteur getProd(){
		return this.prod;
	}

	// getter du fond
	public Indicateur getFond(){
		return this.fond;
	}	
		
}
	
