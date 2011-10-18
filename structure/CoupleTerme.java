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

/**
 * Représente un couple de deux termes.
 * 
 * @author Thibaut Marmin
 */

public class CoupleTerme {
	private Terme t1;
	private Terme t2;

	/**
	 * Constructeur d'un couple de termes.
	 * 
	 * @param a
	 *            le premier terme
	 * @param b
	 *            le deuxième terme
	 */
	public CoupleTerme(Terme a, Terme b) {
		this.t1 = a;
		this.t2 = b;
	}

	/**
	 * Setter du premier terme.
	 * 
	 * @param t
	 *            le terme
	 */
	public void setT1(Terme t) {
		this.t1 = t;
	}

	/**
	 * Setter du deuxième terme.
	 * 
	 * @param t
	 *            le terme
	 */
	public void setT2(Terme t) {
		this.t2 = t;
	}

	/**
	 * Setter du premier et du deuxième terme.
	 * 
	 * @param a
	 *            le premier terme
	 * @param b
	 *            le deuxième terme
	 */
	public void set(Terme a, Terme b) {
		this.t1 = a;
		this.t2 = b;
	}

	/**
	 * Getter du premier terme.
	 * 
	 * @return le premier terme
	 */
	public Terme getT1() {
		return this.t1;
	}

	/**
	 * Getter du deuxième terme
	 * 
	 * @return le deuxième terme
	 */
	public Terme getT2() {
		return this.t2;
	}

	/**
	 * toString d'un couple de termes sous la forme (T1,T2)
	 */
	public String toString() {
		return "(" + this.t1.toString() + "," + this.t2.toString() + ")";
	}
}
