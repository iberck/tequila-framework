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
package org.tequila.template.wrapper;

import java.util.Map;
import org.tequila.project.JProject;

/**
 * Esta clase modela el wrapper del proyecto, lo cual no es otra cosa que un objeto 
 * entendible por el template, el objetivo del wrapper es dar al usuario la posibilidad 
 * de utilizar todas las propiedades del proyecto dentro de los templates.
 * Por ejemplo usted obtendr� el el path de los sources dentro de un template de 
 * la siguiente manera:
 * ${project.sources.path}
 * 
 * @author iberck
 */
public class JProjectWrapper implements TemplateObjectWrapper<JProject> {

    @Override
    public Map wrap(JProject object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
