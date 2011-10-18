/**
 * Copyright (C) 2011 Thibaut Marmin
 * This file is part of Rules-engine.
 *
 * Rules-engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Rules-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Rule-engine.  If not, see <http://www.gnu.org/licenses/>.
*/

package structure;

import java.util.ArrayList;

/**
 * Représente une liste de substitutions.
 * 
 * @author Thibaut Marmin
 */

public class SubstitutionsList extends ArrayList<Substitution> {

	/**
	 * Effectue le produit cartésien entre une liste de couples de termes passée
	 * de paramètre, et la liste de substitutions.
	 * 
	 * @param sub
	 *            liste de couples de termes.
	 * @return une nouvelle SubstitutionList, résultat du produit cartésien
	 */
	public SubstitutionsList produitCartesien(ArrayList<CoupleTerme> sub) {
		SubstitutionsList NSL = new SubstitutionsList();
		if (this.isEmpty()) {
			for (int i = 0; i < sub.size(); ++i) {
				Substitution S = new Substitution();
				S.add(sub.get(i));
				NSL.add(S);
			}
		} else {
			for (int i = 0; i < this.size(); ++i) {
				for (int j = 0; j < sub.size(); ++j) {
					Substitution S = new Substitution();
					S.addAll(this.get(i));
					S.add(sub.get(j));
					NSL.add(S);
				}
			}
		}

		return NSL;
	}

	/**
	 * toString d'une liste de substitutions sous la forme {{S1},{S2},...}
	 */
	public String toString() {
		String retour = "{\n";
		for (int i = 0; i < this.size(); ++i) {
			retour += "\t" + this.get(i);
			if (i < this.size() - 1)
				retour += ",\n";
			else
				retour += "\n";
		}
		retour += "}";
		return retour;
	}
}
