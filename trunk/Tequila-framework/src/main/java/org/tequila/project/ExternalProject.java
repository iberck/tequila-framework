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
package org.tequila.project;

import org.tequila.template.wrapper.ProjectWrapper;

/**
 * Esta clase modela los proyectos externos de los cuales se podrá obtener recursos y 
 * se depositarán los archivos generados por el framework.
 * @author iberck
 */
public interface ExternalProject {

    /**
     * Obtiene el path del proyecto
     * @return
     */
    public String getPath();

    /**
     * Obtiene el nombre del proyecto
     * @return
     */
    public String getProjectName();

    /**
     * Obtiene el autor del proyecto
     * @return
     */
    public String getAuthor();

    /**
     * Valida que el proyecto tenga una estructura básica de acuerdo a su tipo
     * @throws org.tequila.project.ProjectException Se lanza cuando la estructura
     * del proyecto no sea valida.
     */
    public void validateProject() throws ProjectException;

    /**
     * Obtiene el wrapper del proyecto
     * @return
     */
    public ProjectWrapper getProjectWrapper();
}
