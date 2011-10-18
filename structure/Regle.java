package structure;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Modélise une règle. Composée d'une hypothèse (liste d'atomes), et d'une
 * conclusion (un Atome).
 * 
 * @author ML Mugnier
 * @author Thibaut Marmin
 */

public class Regle {
	private ArrayList<Atome> hypothese;// la liste des atomes hypothèses
	private Atome conclusion;// la conclusion de la règle
	private ArrayList<Terme> ensembleTermes;// l'ensemble des termes présents
											// dans la règle

	/**
	 * Constructeur de la classe Regle.
	 * 
	 * @param regle
	 *            la règle, sous forme sous forme textuelle ; cette forme est
	 *            "atome1;atome2;...atomek", où les (k-1) premiers atomes
	 *            forment l'hypothèse, et le dernier forme la conclusion
	 */
	public Regle(String regle) {
		Terme t;
		hypothese = new ArrayList<Atome>();
		ensembleTermes = new ArrayList<Terme>();

		StringTokenizer st = new StringTokenizer(regle, ";");
		while (st.hasMoreTokens()) {
			String s = st.nextToken(); // s représente un atome
			Atome a = new Atome(s);
			hypothese.add(a);// ajout de a à la liste des atomes de l'hypothèses
								// (pour l'instant)
			ArrayList<Terme> termes = a.getListeTermes();
			for (int i = 0; i < termes.size(); i++) {
				t = ajouterTerme(termes.get(i)); // ajout à la liste des termes
				a.getListeTermes().set(i, t);

			}
		}
		// on a mis tous les atomes créés en hypothèse
		// reste à tranférer le dernier en conclusion
		conclusion = hypothese.get(hypothese.size() - 1);
		hypothese.remove(hypothese.size() - 1);
	}

	/**
	 * Constructeur d'une règle sans hypothèse.
	 * 
	 * @param A
	 *            l'atome correspondant à la conclusion
	 */
	public Regle(Atome A) {
		hypothese = new ArrayList<Atome>();
		ensembleTermes = new ArrayList<Terme>();

		conclusion = A;
		ensembleTermes.addAll(A.getListeTermes());
	}

	/**
	 * Teste si la règle contient une hypothèse.
	 * 
	 * @return Vrai si la règle possède une hypothèse vide, Faux sinon
	 */
	public boolean sansHypothese() {
		return this.hypothese.isEmpty();
	}

	/**
	 * Ajoute un terme à la liste des termes s'il n'existe pas déjà.
	 * 
	 * @param t
	 *            le terme à potentiellement ajouter
	 * @return un sommet terme, soit t s'il a été inséré, soit le sommet terme
	 *         qui existait déjà dans la liste des sommets termes
	 */
	private Terme ajouterTerme(Terme t)
	// SI : dans le cas où le terme t n'existe pas déjà dans la liste des
	// sommets termes, on l'ajoute à la bonne place
	// et on lui donne comme voisin le sommet relation se trouvant à l'index
	// "index" dans la liste des sommets relations
	// Sinon, on ajoute le sommet relation se trouvant à l'index "index" dans la
	// liste des sommets relations au sommet terme déjà existant dans la liste
	// des sommets termes
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
	 * Cherche la position où insérer le sommet terme 't'.
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
	// SE : recherche nom, de façon récursive, entre les indices début et fin de
	// la liste des termes. début et fin
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
	 * Accesseur en lecture
	 * 
	 * @return l'hypothèse de la règle
	 */
	public ArrayList<Atome> getHypothese() {
		return hypothese;
	}

	/**
	 * Accesseur en lecture
	 * 
	 * @return la conclusion de la règle
	 */
	public Atome getConclusion() {
		return conclusion;
	}

	/**
	 * Accesseur en lecture
	 * 
	 * @return l'ensemble de termes de la règle
	 */
	public ArrayList<Terme> getEnsembleTermes() {
		return ensembleTermes;
	}

	/**
	 * Propositionalise la règle courante, en fonction des constantes passée en
	 * paramètres.
	 * 
	 * @param Constantes
	 *            liste de termes constants sur lesquels la propositionalisation
	 *            est effectuée
	 * @param BF
	 *            la base de faits concernée par la propositionalisation (ajout
	 *            direct des faits dans le cas de règles sans hypothèse)
	 * @return La liste des règles créées par la propositionalisation (toutes
	 *         les règles instanciées possible avec les constantes passées en
	 *         paramètre)
	 */
	public ArrayList<Regle> propositionaliser(ArrayList<Terme> Constantes,
			BaseFaits BF) {

		ArrayList<Regle> Regles = new ArrayList<Regle>();
		SubstitutionsList subList = new SubstitutionsList();

		if (this.completementInstancie()) {
			Regles.add(this);
			return Regles;
		}

		for (int i = 0; i < this.getEnsembleTermes().size(); ++i) {
			if (this.getEnsembleTermes().get(i).estVariable()) { // Pour chaque
																	// variable
																	// de la
																	// regle
				Terme variable = this.getEnsembleTermes().get(i);

				ArrayList<CoupleTerme> sub = new ArrayList<CoupleTerme>();

				for (int j = 0; j < Constantes.size(); ++j) { // Pour chaque
																// constant on
																// ajoute une
																// affectation
																// possible
					Terme constante = Constantes.get(j);

					sub.add(new CoupleTerme(variable, constante));
				}
				subList = subList.produitCartesien(sub);
			}
		}

		for (int i = 0; i < subList.size(); ++i) { // Pour chaque substitution
													// on crée une nouvelle
													// regle instanciée
			Substitution S = subList.get(i);
			String s = "";

			if (this.sansHypothese()) {
				s += this.conclusion.getLabel() + "(";
				for (int j = 0; j < this.conclusion.getListeTermes().size(); ++j) {
					if (j > 0)
						s += ",";
					Terme T = this.conclusion.getListeTermes().get(j);
					if (T.estConstante())
						s += T.getLabel();
					else
						s += S.getSubstitutionFor(T);
				}
				s += ")";
				Atome A = new Atome(s);
				BF.ajouterNouveauFait(A);
			} else {
				for (int j = 0; j < this.hypothese.size(); ++j) {
					if (j > 0)
						s += ";";
					Atome A = this.hypothese.get(j);
					if (j < 0)
						s += ";";
					s += A.getLabel();
					for (int k = 0; k < A.getListeTermes().size(); ++k) {
						Terme T = A.getListeTermes().get(k);
						if (k == 0)
							s += "(";
						else
							s += ",";
						if (T.estConstante())
							s += "'" + T.getLabel() + "'";
						else
							s += S.getSubstitutionFor(T);
					}
					s += ")";
				}

				s += ";" + this.conclusion.getLabel();
				for (int j = 0; j < this.conclusion.getListeTermes().size(); ++j) {
					Terme T = this.conclusion.getListeTermes().get(j);
					if (j == 0)
						s += "(";
					else
						s += ",";
					if (T.estConstante())
						s += "'" + T.getLabel() + "'";
					else
						s += S.getSubstitutionFor(T);
				}
				s += ")";

				Regles.add(new Regle(s));
			}
		}

		return Regles;
	}

	/**
	 * Indique si la règle est validée par la base de faits passée en paramètre.
	 * 
	 * @param BF
	 *            une base de fait
	 * @return Vrai si la base de fait valide le règle, Faux sinon
	 */
	public boolean validePar(BaseFaits BF) {
		for (int i = 0; i < this.getHypothese().size(); ++i) {
			Atome A = this.getHypothese().get(i);
			if (!BF.atomeExiste(A))
				return false;
		}
		return true;
	}

	/**
	 * Indique si la règle est complètement instanciée ou non.
	 * 
	 * @return Vrai si la règle ne comporte que des constantes, faux sinon
	 */
	public boolean completementInstancie() {
		for (int i = 0; i < this.ensembleTermes.size(); ++i) {
			if (this.ensembleTermes.get(i).estVariable())
				return false;
		}
		return true;
	}

	/**
	 * toString de la règle. Affiche la taille de l'hypothèse, la liste des
	 * atomes, la conclusion et la liste des termes de la règle.
	 */
	public String toString() {
		String s = "taille hypothèse : " + hypothese.size() + "\n";
		s += "liste des atomes : ";
		for (int i = 0; i < hypothese.size(); i++) {
			s += hypothese.get(i);
			if (i < hypothese.size() - 1)
				s += ",";
		}
		s += " --> ";
		s += conclusion;
		s += "\n";
		s += "liste des termes : ";
		for (int i = 0; i < ensembleTermes.size(); i++) {
			s += ensembleTermes.get(i);
			if (i < ensembleTermes.size() - 1)
				s += " ; ";
		}
		return s + "\n";
	}

}