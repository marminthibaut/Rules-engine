package structure;

import java.util.*;

/**
 * Représente la base de faits d'une base de connaissances (BaseConnaissances).
 * 
 * @author ML Mugnier
 */
public class BaseFaits {
	private ArrayList<Atome> listeAtomes;// l'ensemble des faits (atomes)
	private ArrayList<Terme> ensembleTermes;// l'ensemble des termes présents

	/**
	 * Constructeur de la classe 'BaseFaits' crée une base de faits vide
	 */
	public BaseFaits() {
		listeAtomes = new ArrayList<Atome>();
		ensembleTermes = new ArrayList<Terme>();
	}

	/**
	 * Constructeur de la classe 'BaseFaits'
	 * 
	 * @param baseFaits
	 *            les faits, passés sous la forme "atom1;...;atomk"
	 */
	public BaseFaits(String baseFaits) {
		listeAtomes = new ArrayList<Atome>();
		ensembleTermes = new ArrayList<Terme>();
		creerBaseFaits(baseFaits);
	}

	/**
	 * Getter de la liste des atomes de la base de faits.
	 * 
	 * @return la liste des atomes.
	 */
	public ArrayList<Atome> getListeAtomes() {
		return listeAtomes;
	}

	/**
	 * Setter de la liste des atomes de la base de faits.
	 * 
	 * @param listeAtomes
	 */
	public void setListeAtomes(ArrayList<Atome> listeAtomes) {
		this.listeAtomes = listeAtomes;
	}

	/**
	 * Getter de l'ensemble des termes de la base de faits.
	 * 
	 * @return la liste des termes
	 */
	public ArrayList<Terme> getEnsembleTermes() {
		return ensembleTermes;
	}

	/**
	 * Setter de l'ensemble des termes de la base de faits.
	 * 
	 * @param ensembleTermes
	 *            la liste des termes
	 */
	public void setEnsembleTermes(ArrayList<Terme> ensembleTermes) {
		this.ensembleTermes = ensembleTermes;
	}

	/**
	 * Retourne seulement les constantes parmis les termes de la base de faits.
	 * 
	 * @return la liste des constantes
	 */
	public ArrayList<Terme> getEnsembleConstantes() {
		Iterator<Terme> it = this.ensembleTermes.iterator();
		ArrayList<Terme> retour = new ArrayList<Terme>();

		while (it.hasNext()) {
			Terme t = it.next();
			if (t.estConstante())
				retour.add(t);
		}

		return retour;
	}

	/**
	 * Crée la base de faits à partir d'une chaine de caractères supposée
	 * formattée correctement.
	 * 
	 * @param baseFaits
	 *            la chaine de caractère décrivant la base de faits.
	 */
	private void creerBaseFaits(String baseFaits)
	// Prérequis : le format de la base de faits est supposé correct
	{
		Terme t;
		StringTokenizer st = new StringTokenizer(baseFaits, ";");
		while (st.hasMoreTokens()) {
			String s = st.nextToken(); // s représente un atome
			Atome a = new Atome(s);
			ajouterAtome(a);// On ajoute un atome à la liste des atomes
			ArrayList<Terme> termes = a.getListeTermes();
			for (int i = 0; i < termes.size(); i++) {
				t = ajouterTerme(termes.get(i));
				a.getListeTermes().set(i, t);
			}
		}
	}

	/**
	 * Ajoute des faits à la base de faits s'ils n'apparaissaient pas déjà
	 * 
	 * @param faits
	 *            les faits à ajouter (seuls ceux n'apparaissant pas dans la
	 *            base seront ajoutés)
	 */
	public void ajouterNouveauxFaits(ArrayList<Atome> faits)
	// Spec Interne : la liste des termes apparaissant dans la base est
	// également
	// mise à jour
	{
		for (int i = 0; i < faits.size(); i++)
			ajouterNouveauFait(faits.get(i));
	}

	/**
	 * Ajoute un fait à la base de faits s'il n'apparait pas déjà
	 * 
	 * @param fait
	 *            le fait à ajouter (ne sera ajouté que s'il n'apparait pas
	 *            déjà)
	 */
	public void ajouterNouveauFait(Atome fait)
	// Spec Interne : la liste des termes apparaissant dans la base est
	// également
	// mise à jour
	{
		if (!atomeExiste(fait)) {
			listeAtomes.add(fait);

			for (int j = 0; j < fait.getListeTermes().size(); j++) {
				Terme t = new Terme(fait.getListeTermes().get(j).getLabel(),
						fait.getListeTermes().get(j).estConstante());// On crée
																		// un
																		// nouveau
																		// terme
				t = ajouterTerme(t); // ceci ajoute le terme dans la liste des
										// termes de la base de faits s'il
										// n'existait pas déjà
				listeAtomes.get(listeAtomes.size() - 1).getListeTermes()
						.set(j, t);
			}

		}
	}

	/**
	 * Ajoute un terme à la liste des termes s'il n'existe pas déjà.
	 * 
	 * @param t
	 *            le terme à potentiellement ajouter
	 * @return un sommet terme, soit t s'il a été inséré, soit le sommet terme
	 *         qui existait déjà dans la liste des sommets termes
	 */
	public Terme ajouterTerme(Terme t)
	// SI : dans le cas où le terme t n'existe pas déjà dans la liste des
	// sommets termes, on l'ajoute à la bonne place
	// et on lui donne comme voisin l'atome se trouvant à l'index "index" dans
	// la liste des atomes
	// Sinon, on ajoute l'atome se trouvant à l'index "index" dans la liste des
	// atomes au sommet terme déjà existant dans la liste des sommets termes
	{
		int[] retour;

		retour = positionDichoTerme(t);// on recherche la position où ajouter t
		if (retour[0] != -1)
			ensembleTermes.add(retour[1], t);// Si t n'apparaissait pas
												// auparavant, on l'ajoute à la
												// liste des termes
		return ensembleTermes.get(retour[1]);// On retourne le terme, soit t
												// s'il a été inséré, soit le
												// terme qui existait déjà dans
												// la liste des termes
	}

	/**
	 * Cherche la position où insérer le sommet terme 't'
	 * 
	 * @param t
	 *            le sommet terme à insérer
	 * @return la position où doit être ajoutée le sommet terme
	 */
	private int[] positionDichoTerme(Terme t)
	// SE : si t se trouve dans la liste des termes, retourne son indice.
	// sinon retourne l'indice où il devrait être inséré
	// SI : appelle la méthode positionDichoRecursif en indiquant comme
	// paramètre de recherche les
	// indices de début et de fin de la liste des termes (à savoir : 0 et
	// ensembleTermes.size()-1)
	// tableauRéponses : la première cellule est à -1 si le terme apparaît déjà
	// la seconde à la place où doit être inséré le terme
	{
		int[] tableauReponses = new int[2];
		if (ensembleTermes.size() > 0)
			return positionDichoRecursifTerme(t.getLabel(), 0,
					ensembleTermes.size() - 1, tableauReponses);
		else {
			tableauReponses[0] = 0;
			tableauReponses[1] = 0;
			return tableauReponses;
		}
	}

	private int[] positionDichoRecursifTerme(String nom, int début, int fin,
			int[] tabReponses)
	// SE : recherche le nom, de façon récursive, entre les indices début et fin
	// de la liste des termes. début et fin
	// doivent obligatoirement être positifs et inférieurs à la taille de la
	// liste des termes.
	// tabReponses : la première cellule est à -1 si le terme apparaît déjà
	// la seconde à la place où doit être inséré le terme
	{
		if (début > fin) {
			tabReponses[0] = début;
			tabReponses[1] = début;
			return tabReponses; // et on sort
		}
		int milieu = (début + fin) / 2;
		int compare = ensembleTermes.get(milieu).getLabel().compareTo(nom);
		if (compare == 0)// Si le terme de nom "nom" existe déjà
		{
			tabReponses[0] = -1;
			tabReponses[1] = milieu;
			return tabReponses; // et on sort
		}
		if (compare > 0)
			return positionDichoRecursifTerme(nom, début, milieu - 1,
					tabReponses);
		return positionDichoRecursifTerme(nom, milieu + 1, fin, tabReponses);
	}

	/**
	 * Ajoute un atome à la base de faits sans autre vérification tous les
	 * termes de l'atome doivent déjà exister dans la base
	 * 
	 * @param a
	 *            l'atome à ajouter
	 * @return la position où l'atome 'a' a été ajouté (s'il existait déjà il
	 *         est ajouté en double)
	 */
	private int ajouterAtome(Atome a) {
		listeAtomes.add(a);
		return listeAtomes.size() - 1;
	}

	/**
	 * Teste l'existence d'un atome dans la base
	 * 
	 * @param a
	 *            l'atome dont on teste l'existence
	 * @return vrai si l'atome existe déjà, faux sinon
	 */
	public boolean atomeExiste(Atome a) {
		for (int i = 0; i < listeAtomes.size(); i++) {
			if (listeAtomes.get(i).equalsP(a)) {
				int j = 0;
				for (j = 0; j < a.getListeTermes().size(); j++) {
					if (!a.getListeTermes()
							.get(j)
							.equalsT(listeAtomes.get(i).getListeTermes().get(j)))
						break;
				}
				if (j == a.getListeTermes().size())
					return true;
			}
		}
		return false;
	}

	/**
	 * retourne une description de la base de faits
	 * 
	 * @return description de la base de faits
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "nombre de faits : " + listeAtomes.size() + "\n";
		s += "liste des faits : ";
		for (int i = 0; i < listeAtomes.size(); i++) {
			s += listeAtomes.get(i);
			if (i < listeAtomes.size() - 1)
				s += ",";
		}
		s += "\n";
		s += "liste des termes : ";
		{
			for (int i = 0; i < ensembleTermes.size(); i++) {
				s += ensembleTermes.get(i);
				if (i < ensembleTermes.size() - 1)
					s += " ; ";
			}
		}
		return s;
	}
}