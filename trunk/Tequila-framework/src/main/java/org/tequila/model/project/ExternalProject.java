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
package org.tequila.model.project;

import org.springframework.beans.factory.annotation.Required;
import org.tequila.template.wrapper.ProjectWrapper;
import org.tequila.template.wrapper.ProjectWrapperFactory;

/**
 * Esta clase modela los proyectos externos de los cuales se podr� obtener recursos y 
 * se depositar�n los archivos generados por el framework.
 * @author iberck
 */
public interface ExternalProject {

    @Required
    public void setPath(String path);

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
     * Valida que el proyecto tenga una estructura b�sica de acuerdo a su tipo
     * @throws org.tequila.project.ProjectException Se lanza cuando la estructura
     * del proyecto no sea valida.
     */
    public void validateProject() throws ProjectException;

    /**
     * Obtiene el wrapper del proyecto, el wrapper del proyecto servir� para saber
     * con que tipo de clase debe crear el framework el objeto para la plantilla.
     * Por ejemplo, cada proyecto debe decir si va a utilizar un wrapper para un proyecto
     * java simple o un wrapper para un proyecto web, precisamente este m�todo es para que
     * cada proyecto diga que wrapper envolver� al proyecto.
     * @return
     */
    public ProjectWrapper getProjectWrapper();

    /**
     * Asigna la fabrica para envolver proyectos. La fabrica de envoltura de proyectos
     * sirve para saber para que template engine ser� creado el objeto.
     * @param pwf
     */
    public void setProjectWrapperFactory(ProjectWrapperFactory pwf);

    /**
     * Obtiene la fabrica de wrappers
     * @return
     */
    public ProjectWrapperFactory getProjectWrapperFactory();

}
