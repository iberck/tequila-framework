/**
 *  Copyright (c) 2007-2008 by Carlos G�mez Montiel <iberck@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  his program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tequila.template.engine;

/**
 * Modela las excepciones en tiempo de ejecuci�n de los match de templates
 * @author iberck
 */
public class MatchException extends RuntimeException {

    /**
     * Crea una excepcion vac�a
     */
    public MatchException() {
    }

    /**
     * Crea una excepci�n con un mensaje
     * @param msg
     */
    public MatchException(String msg) {
        super(msg);
    }

    /**
     * Crea una excepci�n con un mensaje y un objeto con la excepcion
     * @param msg
     */
    public MatchException(String msg, Throwable t) {
        super(msg, t);
    }
}
