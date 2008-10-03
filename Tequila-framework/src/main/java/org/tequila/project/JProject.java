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
package org.tequila.project;

import java.io.File;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tequila.framework.FrameworkClassPath;
import org.tequila.template.wrapper.ProjectWrapper;
import org.tequila.template.wrapper.ProjectWrapperFactory;

/**
 * Esta clase modela los proyectos externos de tipo java b�sico
 * @author iberck
 */
public abstract class JProject implements ExternalProject {

    protected static final Log log = LogFactory.getLog(JProject.class);
    protected ProjectWrapperFactory projectWrapperFactory;
    private final String path;

    /**
     * Crea un nuevo JProject a partir de una ruta
     * @param path Ruta del proyecto
     */
    protected JProject(String path) throws ProjectException {
        this.path = path;
    }

    /**
     * @see ExternalProject
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * Obtiene el nombre del proyecto
     * @return Nombre del proyecto
     * @throws ProjectException cuando el nombre del proyecto es nulo o el nombre
     * del proyecto termina con '/', Por ejemplo a/b/c/ --> ""
     */
    @Override
    public String getProjectName() {
        String projectName = FilenameUtils.getName(path);
        if (projectName == null || projectName.equals("")) {
            throw new ProjectException("El nombre del proyecto no es v�lido");
        }

        return projectName;
    }

    /**
     * M�todo para introducir el proyecto externo al classpath del framework
     * con el objetivo poder ocupar los recursos externos en tiempo de ejecuci�n.
     * @see RuntimeClassPath
     */
    protected void addToInternalClassPath() {
        FrameworkClassPath.addResource(getPath() + File.separator + getClassesPath());
    }

    @Override
    public ProjectWrapperFactory getProjectWrapperFactory() {
        return projectWrapperFactory;
    }

    @Override
    public void setProjectWrapperFactory(ProjectWrapperFactory projectWrapperFactory) {
        this.projectWrapperFactory = projectWrapperFactory;
    }

    /**
     * Path donde se encuentra la carpeta classes (archivos .class) 
     * dentro del proyecto, esta ruta debe ser relativa al proyecto.
     * @return
     */
    public abstract String getClassesPath();

    /**
     * Path donde se encuentra la carpeta sources (archivos .java) 
     * dentro del proyecto, esta ruta debe ser relativa al proyecto.
     * @return
     */
    public abstract String getSourcesPath();

    /**
     * Path donde se encuentra la carpeta test
     * dentro del proyecto, esta ruta debe ser relativa al proyecto.
     * Si el proyecto no tiene una ruta de test, retornar null
     * @return
     */
    public abstract String getTestPath();

    /**
     * Obtiene el autor del proyecto
     * @return
     */
    public String getAuthor() {
        return System.getProperty("user.name");
    }

    /**
     * Valida que se encuentre al menos la estructur� b�sica del proyecto.
     *  - Valida que exista la ruta del proyecto
     *  - Valida que se encuentre la carpeta sources
     *  - Valida que se encuentre la carpeta classes
     *  - Valida que se encuentre la carpeta test (si el proyecto la soporta), 
     *    si no la soporta los proyectos que extiendan de esta clase deber�n 
     *    retornar null en el m�todo getTestPath();
     * 
     * @throws org.tequila.project.ProjectException Se lanza si no es valido el
     *         proyecto
     */
    @Override
    public void validateProject() throws ProjectException {
        log.debug("Validando el proyecto '" + getPath() + "'");

        // validar que exista la ruta del proyecto
        File fprj = new File(getPath());
        if (!fprj.exists()) {
            throw new ProjectException("No existe el proyecto '" + getPath() + "'");
        }

        File f = new File(getPath());
        if (!f.canWrite()) {
            throw new ProjectException("El proyecto '" + getPath() + "' es de solo lectura");
        }

        // valida que se encuentre la carpeta sources
        String src = getSourcesPath();
        if (!existsInProject(src)) {
            throw new ProjectException("No existe la carpeta sources '" + src + "' dentro del " +
                    "proyecto");
        }

        // valida que se encuentre la carpeta classes
        String classes = getClassesPath();
        if (!existsInProject(classes)) {
            throw new ProjectException("No existe la carpeta classes '" + classes + "' dentro del " +
                    "proyecto");
        }

        // valida que se encuentre la carpeta test siempre y cuando el proyecto java la soporte
        if (getTestPath() != null) {
            String test = getTestPath();
            if (!existsInProject(test)) {
                throw new ProjectException("No existe la carpeta test '" + test + "' dentro del " +
                        "proyecto");
            }
        }
    }

    /**
     * Valida si existe el directorio dentro del proyecto
     * @param path
     * @return true si existe, false si no existe
     */
    public boolean existsInProject(String directory) {
        File f = new File(getPath() + File.separator + directory);
        return f.exists() && f.isDirectory();
    }

    /*
     * Inicializa el proyecto
     * @throws org.tequila.project.ProjectException Se puede lanzar bajo las 
     * siguientes circunstancias: 
     *  1. El proyecto no es valido
     *  2. El proyecto no se puede meter al RuntimeClasspath del framework.
     */
    public void setup() throws ProjectException {
        // validar el proyecto
        validateProject();

        // introducir el proyecto al classpath
        addToInternalClassPath();
    }

    /**
     * Obtiene el wrapper para projectos java b�sicos
     * @return
     */
    @Override
    public ProjectWrapper getProjectWrapper() {
        return projectWrapperFactory.getJProjectWrapper();
    }
}
