package structure;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Représente une base de connaissance. Composée d'une base de faits (BaseFaits)
 * et d'une base de règles.
 * 
 * @author Thibaut Marmin
 * 
 */

public class BaseConnaissances {

	private BaseFaits BF;// base de faits
	private ArrayList<Regle> BR;// base de règles
	private boolean propositionalise;
	private boolean sature;

	/**
	 * Constructeur d'un base de connaissance vide.
	 */

	public BaseConnaissances() {
		super();
		BF = new BaseFaits();
		BR = new ArrayList<Regle>();
		propositionalise = false;
		sature = false;
	}

	/**
	 * Constructeur d'une base de connaissance avec affectation de la base de
	 * faits et de la base de règles.
	 * 
	 * @param BF
	 *            La base de faits
	 * @param BR
	 *            La base de règles
	 */

	public BaseConnaissances(BaseFaits BF, ArrayList<Regle> BR) {
		super();
		this.BF = BF;
		this.BR = BR;
		propositionalise = false;
		sature = false;
	}

	/**
	 * Constructeur d'une base de connaissance à partir d'un fichier. Le fichier
	 * doit comporter un fait pas ligne, puis une ligne vide, puis une règle par
	 * ligne.
	 * 
	 * @param path
	 *            Chemin d'accès au fichier (relatif ou absolu)
	 * @throws Exception
	 *             Retourne une exception lorsque la lecture du fichier échoue
	 */

	public BaseConnaissances(String path) throws Exception {

		super();
		propositionalise = false;
		sature = false;
		BF = new BaseFaits();;
		BR = new ArrayList<Regle>();

		BufferedReader lectureFichier = null;

		lectureFichier = new BufferedReader(new FileReader(path));

		String s;
		String etape = "faits";

		try {
			while (null != (s = lectureFichier.readLine())) {
				if (s.equals("")) {
					etape = "regles";
					s = lectureFichier.readLine();
				}
				if (etape.equals("faits")) {
					this.ajouterFait(new Atome(s));
				} else if (etape.equals("regles")) {
					this.ajouterRegle(new Regle(s));
				}
			}
		} catch (Exception e) {
			throw new Exception("Fichier incorrecte : " + e);
		}

	}

	/**
	 * Ajoute une règle à la base de connaissance.
	 * 
	 * @param R
	 *            La règle
	 */
	public void ajouterRegle(Regle R) {
		this.BR.add(R);
	}

	/**
	 * Ajoute un fait à la base de connaissances si il ne contient pas de
	 * variables. Sinon il est ajouté comme règle sans hypothèse.
	 * 
	 * @param F
	 *            Le fait (l'Atome)
	 */
	public void ajouterFait(Atome F) {
		if (F.contientVariable()) {
			Regle R = new Regle(F);
			this.BR.add(R);
		} else
			this.BF.ajouterNouveauFait(F);
	}

	/**
	 * Getter de la base de faits.
	 * 
	 * @return la base de faits de la base de connaissances
	 */
	public BaseFaits getBF() {
		return BF;
	}

	/**
	 * Getter de la base de règles.
	 * 
	 * @return la base de règle associée à la base de connaissances
	 */
	public ArrayList<Regle> getBR() {
		return BR;
	}

	/**
	 * Formate la base de connaissance en une String
	 * 
	 * @return la base de connaissance formattée
	 */
	public String toString() {
		String BRs = "nombre de règles : " + BR.size() + "\n";
		for (int i = 0; i < BR.size(); i++) {
			BRs += "Regle " + i + "\n" + BR.get(i) + "\n";
		}
		return "Base de faits\n" + BF + "\nBase de règles\n" + BRs;
	}

	/**
	 * Permet de récupérer l'ensemble des constantes présentes dans la base de
	 * connaissance.
	 * 
	 * @return la liste des constantes (Terme constant) présents dans la base de
	 *         faits et dans les conclusions des règles
	 */
	public ArrayList<Terme> getEnsembleConstantes() {
		// On récupere les constantes de la BF
		ArrayList<Terme> Constantes = this.BF.getEnsembleConstantes();

		// Et celles des conlusions de la BR
		Iterator<Regle> it = BR.iterator();
		while (it.hasNext()) {
			Regle r = (Regle) it.next();

			ArrayList<Terme> lt = r.getConclusion().getListeTermes();
			for (int i = 0; i < lt.size(); ++i) {
				if (lt.get(i).estConstante() && !Constantes.contains(lt.get(i)))
					Constantes.add(lt.get(i));
			}
		}
		return Constantes;
	}

	/**
	 * Propositionalise la base de faits et la base de règles. /!\ La base de
	 * règles originale n'est pas sauvegardée. La propositionalisation ne peut
	 * avoir lieu qu'une fois.
	 */
	public void propositionalisation() {
		ArrayList<Regle> BRprop = new ArrayList<Regle>();
		ArrayList<Terme> Constantes = this.getEnsembleConstantes();

		if (!propositionalise) {
			Iterator<Regle> it = BR.iterator();
			while (it.hasNext()) {
				Regle r = (Regle) it.next();
				BRprop.addAll(r.propositionaliser(Constantes, this.BF));
			}
			this.BR = BRprop;
			propositionalise = true;
		}
	}

	/**
	 * Sature la base de fait à partir de la base de règles de la base de
	 * connaissances. Si la propositionalisation n'a pas eu lieu, alors celle-ci
	 * est déclanchée. Si la saturation à déjà eu lieu, alors celle-ci n'aura
	 * pas lieu.
	 */
	public void saturer() {
		if (!sature) {

			if (!propositionalise)
				this.propositionalisation();

			boolean fin = false;
			ArrayList<Atome> NEW = new ArrayList<Atome>();
			while (!fin) {
				NEW.clear();
				for (int i = 0; i < this.BR.size(); ++i) {
					Regle R = this.BR.get(i);
					if (!NEW.contains(R.getConclusion())
							&& !this.BF.getListeAtomes().contains(
									R.getConclusion()) && R.validePar(this.BF)) {
						NEW.add(R.getConclusion());
					}
				}

				if (NEW.isEmpty())
					fin = true;
				else {
					for (int i = 0; i < NEW.size(); ++i) {
						if (!this.BF.getListeAtomes().contains(NEW.get(i)))
							this.BF.ajouterNouveauFait(NEW.get(i));
					}
				}
			}
			sature = true;
		}
	}

	/**
	 * Permet l'intérrogation de la base de faits.
	 * 
	 * @param A
	 *            Question posée (Atome)
	 * @return
	 */
	public boolean question(Atome A) {
		if (this.BF.atomeExiste(A))
			return true;
		else
			return false;
	}

}