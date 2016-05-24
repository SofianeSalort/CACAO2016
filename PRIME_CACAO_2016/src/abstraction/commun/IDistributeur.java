 package abstraction.commun;
import java.util.HashMap;
import java.util.List;
import java.util.List;

public interface IDistributeur {	
	
	public List<CommandeDistri> demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> contreDemande (List<CommandeDistri> cd);


	/**
	 * @deprecated
	 * @return
	 */
	public double getPrix();
	
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste);

	
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf);

	

}
