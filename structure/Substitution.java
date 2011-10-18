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
 * Une substitution est une liste de couples de termes.
 * 
 * @author Thibaut Marmin
 */

public class Substitution extends ArrayList<CoupleTerme> {

	/**
	 * Retourne la substitution à partir d'un terme. Par pour la substitution
	 * {{x,'A'},{y,'B'}} et le terme T=x en paramètre, la fonction renvoit le
	 * terme 'A'.
	 * 
	 * @param T
	 *            le terme à substituer
	 * @return la substitution correspondante
	 */
	public Terme getSubstitutionFor(Terme T) {
		for (int i = 0; i < this.size(); ++i) {
			if (this.get(i).getT1().equals(T))
				return this.get(i).getT2();
		}

		return null;
	}

	/**
	 * toString d'une substitution sous la forme {{T1},{T2},...}
	 */
	public String toString() {
		String retour = "{";
		for (int i = 0; i < this.size(); ++i) {
			retour += this.get(i);
			if (i < this.size() - 1)
				retour += ",";
		}
		retour += "}";
		return retour;
	}

}
