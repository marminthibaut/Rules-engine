// pour la première phase du TP1

package apps;

import java.util.Scanner;

import structure.Atome;
import structure.BaseConnaissances;

/**
 * Classe principale d'execution du programme.
 * 
 * @author Thibaut Marmin
 */

public class Application1 {

	/**
	 * Méthode main de l'Application1
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			System.out.println(Application1.intro());

			BaseConnaissances K = null;
			String atome = "";
			Atome A;
			Scanner cin = new Scanner(System.in);

			while (!(atome.equals("quit") || atome.equals("\\q"))) {
				try {
					System.out.print("$ ");
					atome = cin.nextLine();
					if (atome.equals("help") || atome.equals("\\h")) {
						System.out.println(Application1.help());
					} else if (atome.equals("quit") || atome.equals("\\q")) {
						System.out.println("Fermeture");
					} else if (atome.equals("print")) {
						if (null == K)
							System.err
									.println("BF non initialisée.\n(help ou \\h pour plus d'informations.");
						else
							System.out.println(K);
					} else if (atome.equals("load") || atome.equals("\\l")) {
						System.out.print("Nom du fichier : ");
						try {
							K = Application1.load(cin.nextLine());
						} catch (Exception e) {
							System.err
									.println("Erreur : impossible d'ouvrir le fichier");
						}
					} else if (!atome.isEmpty()) {
						if (null == K)
							System.err
									.println("BF non initialisée.\n(help ou \\h pour plus d'informations.");
						else
							System.out.println(K.question(new Atome(atome)));
					}

				} catch (Exception e) {
					System.err.println("/!\\ Exception : " + e.toString());
				}
			}

			System.out.println("Le programme s'est arrêté sans erreur");

		} catch (Exception e) {
			System.err.println("/!\\ Exception : " + e.toString());
		}

	}

	/**
	 * Chaine affiché lors du lancement de l'Application1
	 * 
	 * @return la chaine de caractères
	 */
	public static String intro() {
		String s = "";
		s += "--------------------------------------------------------\n";
		s += " Application : Moteur de règles d'ordre 1 (TP Intro IA)\n";
		s += "        Date : 14 Octobre 2011\n";
		s += "     Auteurs : Thibaut Marmin\n";
		s += "               ML Mugnier\n";
		s += "--------------------------------------------------------";

		return s;
	}

	/**
	 * Contenu de la commande help.
	 * 
	 * @return la chaine de caractère correspondante
	 */
	public static String help() {
		String s = "";
		s += "------------------------- HELP -------------------------\n";
		s += " help \t(\\h) affiche cet aide\n";
		s += " print \t(\\p) affiche la base de connaissances\n";
		s += " load \t(\\l) recharge le fichier texte\n";
		s += " quit \t(\\q) quitte l'application\n";
		s += " {atome}\t     répond à la question";

		return s;
	}

	/**
	 * Contenu de la commande.
	 * 
	 * @return la chaine de caractère correspondante à la commande load
	 */
	public static BaseConnaissances load(String path) throws Exception {
		System.out.println("Ouverture du fichier...");
		BaseConnaissances K = new BaseConnaissances(path);

		System.out.println("Propositionalisation...");
		K.propositionalisation();
		System.out.println("Saturation...");
		K.saturer();
		System.out.println("Base de connaissance chargée.");

		return K;
	}

}