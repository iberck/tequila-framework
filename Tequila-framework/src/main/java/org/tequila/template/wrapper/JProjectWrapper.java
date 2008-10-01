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

import java.util.HashMap;
import java.util.Map;
import org.tequila.project.JProject;

/**
 * Esta clase modela el wrapper del proyecto, lo cual no es otra cosa que un objeto 
 * entendible por el template, el objetivo del wrapper es dar al usuario la posibilidad 
 * de utilizar todas las propiedades del proyecto dentro de los templates.
 * Por ejemplo usted obtendr� el el path de los sources dentro de un template de 
 * la siguiente manera:
 * ${project.srcPath}
 * 
 * @author iberck
 */
public class JProjectWrapper implements TemplateObjectWrapper<JProject> {

    protected final static String PROJECT_ROOT = "project";

    /**
     * Realiza el wrapper de un proyecto java b�sico, hasta el momento se cuenta con
     * las siguientes propiedades:
     * 
     * ${project.path} -> Path absoluto del proyecto
     * ${project.name} -> Nombre del proyecto
     * ${project.srcPath} -> Carpeta relativa a los sources del proyecto
     * ${project.srcAbsPath} -> Carpeta absoluta a los sources del proyecto
     * ${project.classesPath} -> Carpeta relativa a los .class del proyecto
     * ${project.classesAbsPath} -> Carpeta absoluta a los .class del proyecto
     * ${project.testPath} -> Carpeta relativa a los test del proyecto
     * ${project.testAbsPath} -> Carpeta absoluta a los test del proyecto
     * 
     * @param project
     * @return
     */
    @Override
    public Map wrap(JProject project) {
        Map root = new HashMap();
        Map properties = new HashMap();

        root.put(PROJECT_ROOT, properties);
        properties.put("path", project.getPath());
        properties.put("name", project.getProjectName());
        properties.put("srcPath", project.getSourcesPath());
        properties.put("srcAbsPath", project.getAbsolutePath(project.getSourcesPath()));
        properties.put("classesPath", project.getClassesPath());
        properties.put("classesAbsPath", project.getAbsolutePath(project.getClassesPath()));
        properties.put("testPath", project.getTestPath());
        properties.put("testAbsPath", project.getAbsolutePath(project.getTestPath()));

        return root;
    }
}