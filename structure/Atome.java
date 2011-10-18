package structure;

import java.util.ArrayList;

/**
 * Représente un Atome (symbole propositionnel). Il est composé d'un label (nom
 * du prédicat) et d'aucun ou plusieurs termes.
 * 
 * @author ML Mugnier
 * @author Thibaut Marmin
 */

public class Atome {
	private String label; // le prédicat de l'atome
	private ArrayList<Terme> listeTermes; // la liste de termes de cet atome

	/**
	 * Constructeur de la classe Atome Crée un atome, avec ou sans termes
	 * 
	 * @param s
	 *            l'atome, soit réduit à un nom de prédicat, soit de la forme
	 *            prédicat(liste de termes), où les termes sont séparés par des
	 *            virgules
	 */
	public Atome(String s) {

		listeTermes = new ArrayList<Terme>();
		if (s.charAt(s.length() - 1) != ')') // c'est donc un atome sans termes
			label = s;
		else {
			int cpt = 0;
			String nomAtome = "";// pour construire le label de l'atome
			String nomTerme = "";// pour construire le terme courant
			boolean constanteTerme;// le terme courant-il est une constante ou
									// non

			while (s.charAt(cpt) != ')') {
				while (s.charAt(cpt) != '(')// On récupère le label de l'atome
				{
					nomAtome += s.charAt(cpt);
					cpt++;
				}
				label = nomAtome;
				cpt++;// Pour sauter le '('
				while (s.charAt(cpt) != ')')// On va désormais s'intéresser aux
											// termes de l'atome
				{
					while (s.charAt(cpt) != ',' && s.charAt(cpt) != ')')// On
																		// récupère
																		// le
																		// label
																		// du
																		// terme
					{
						nomTerme += s.charAt(cpt);
						cpt++;
					}
					if (nomTerme.charAt(0) == '\'')// On teste si le terme est
													// une constante
					{
						constanteTerme = true;
						nomTerme = nomTerme.substring(1, nomTerme.length() - 1);// Si
																				// c'est
																				// une
																				// constante
																				// alors
																				// on
																				// supprime
																				// les
																				// "'"
					} else
						constanteTerme = false;
					Terme t = new Terme(nomTerme, constanteTerme);// On crée un
																	// nouveau
																	// terme
					listeTermes.add(t);// On ajoute le terme créé s'il
										// n'existait pas déjà
					nomTerme = "";
					if (s.charAt(cpt) == ',')
						cpt++;// Pour sauter le ','
				}

			}
		}
	}

	/**
	 * Ajoute le Terme 't' à la liste de termes de l'atome, sans autre
	 * vérification
	 * 
	 * @param t
	 *            le terme à ajouter
	 */
	public void ajoutTerme(Terme t) {
		listeTermes.add(t);
	}

	/**
	 * Getter de la liste des termes
	 * 
	 * @return Liste des termes de l'atome
	 */

	public ArrayList<Terme> getListeTermes() {
		return listeTermes;
	}

	/**
	 * Setter de la liste des termes
	 * 
	 * @param listeTermes
	 *            Liste des termes de l'atome
	 */
	public void setListeTermes(ArrayList<Terme> listeTermes) {
		this.listeTermes = listeTermes;
	}

	/**
	 * Getter du label
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	public boolean contientVariable() {
		for (int i = 0; i < this.listeTermes.size(); ++i) {
			if (this.listeTermes.get(i).estVariable())
				return true;
		}
		return false;
	}

	/**
	 * Teste l'egalité des prédicats de deux atomes avec le label et l'arité de
	 * l'atome
	 * 
	 * @param r
	 *            l'atome à tester par rapport à l'atome courant
	 * @return vrai si les deux atomes ont même prédicat, faux sinon
	 */
	public boolean equalsP(Atome r) {
		return (this.label.equals(r.label) && listeTermes.size() == r.listeTermes
				.size());
	}

	/**
	 * Teste l'egalité de deux atomes (même label et même liste de termes)
	 * 
	 * @param r
	 *            l'atome à tester par rapport à l'atome courant
	 * @return vrai si les deux atomes sont égaux, faux sinon
	 */
	public boolean equalsA(Atome r) {
		if (!equalsP(r))
			return false;
		for (int i = 0; i < listeTermes.size(); i++)
			if (!listeTermes.get(i).equals(r.listeTermes.get(i)))
				return false;
		return true;
	}

	/**
	 * Redéfinition de la fonction equals. De comparer génériquement deux
	 * atomes.
	 * 
	 * @param o
	 *            Un object
	 * @return Booléen à vrai si this = 0, faux sinon
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Atome))
			return false;
		else
			return equalsA((Atome) o);
	}

	/**
	 * Retourne la chaîne de caractères de cet atome
	 * 
	 * @return la chaîne décrivant l'atome (suivant l'écriture logique
	 *         habituelle)
	 */
	public String toString() {
		String s = label + "(";
		for (int i = 0; i < listeTermes.size(); i++) {
			s += listeTermes.get(i);
			if (i < listeTermes.size() - 1)
				s += ",";
		}
		s += ")";
		return s;
	}

}
