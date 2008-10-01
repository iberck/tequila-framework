/**
 *  Copyright (c) 2007-2008 by Carlos Gómez Montiel <iberck@gmail.com>
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
import org.tequila.project.JWebProject;

/**
 * Esta clase modela el wrapper del proyecto web, lo cual no es otra cosa que un objeto 
 * entendible por el template, el objetivo del wrapper es dar al usuario la posibilidad 
 * de utilizar todas las propiedades del proyecto dentro de los templates.
 * Por ejemplo usted obtendrá la ruta web-inf dentro de un template de 
 * la siguiente manera:
 * ${project.webinf.path}
 * 
 * Ademas podrá utilizar todas las propiedades del wrapper JProjectWrapper
 * 
 * @author iberck
 */
public class JWebProjectWrapper extends JProjectWrapper {

    /**
     * Realiza el wrapper de un proyecto java básico, hasta el momento se cuenta con
     * las siguientes propiedades:
     * @see JProjectWrapper
     * 
     * ${project.webInfPath} -> Ruta relativa a la carpeta WEB-INF del proyecto
     * ${project.webInfAbsPath} -> Ruta absoluta a la carpeta WEB-INF del proyecto
     * @param project
     * @return
     */
    @Override
    public Map wrap(JProject project) {
        JWebProject webProject = (JWebProject) project;
        Map m = super.wrap(webProject);

        Map properties = (Map) m.get(PROJECT_ROOT);
        properties.put("webInfPath", webProject.getWebInfPath());
        properties.put("webInfAbsPath", webProject.getAbsolutePath(webProject.getWebInfPath()));

        return m;
    }
}
