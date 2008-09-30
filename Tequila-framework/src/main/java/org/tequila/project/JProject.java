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
import org.tequila.template.wrapper.JProjectWrapper;

/**
 * Esta clase modela los proyectos externos de tipo java b�sico
 * @author iberck
 */
public abstract class JProject implements ExternalProject {

    protected static final Log log = LogFactory.getLog(JProject.class);
    private String path;

    protected JProject(String path) {
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

    @Override
    public JProjectWrapper getProjectWrapper() {
        return new JProjectWrapper();
    }

    /**
     * M�todo para introducir el proyecto externo al classpath del framework
     * con el objetivo poder ocupar los recursos externos en tiempo de ejecuci�n.
     * @see InternalClassPath
     */
    protected void addToInternalClassPath() {
        InternalClassPath.addResource(getPath() + File.separator + getClassesPath());
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
        log.debug("Validando el proyecto");

        // validar que exista la ruta del proyecto
        if (!exists(getPath())) {
            throw new ProjectException("No existe el proyecto '" + getPath() + "'");
        }

        File f = new File(getPath());
        if (!f.canWrite()) {
            throw new ProjectException("El proyecto '" + getPath() + "' es de solo lectura");
        }

        // valida que se encuentre la carpeta sources
        String src = getAbsolutePath(getSourcesPath());
        if (!exists(src)) {
            throw new ProjectException("No existe la carpeta sources '" + src + "' dentro del " +
                    "proyecto");
        }

        // valida que se encuentre la carpeta classes
        String classes = getAbsolutePath(getClassesPath());
        if (!exists(classes)) {
            throw new ProjectException("No existe la carpeta classes '" + classes + "' dentro del " +
                    "proyecto");
        }

        // valida que se encuentre la carpeta test siempre y cuando el proyecto java la soporte
        if (getTestPath() != null) {
            String test = getAbsolutePath(getTestPath());
            if (!exists(test)) {
                throw new ProjectException("No existe la carpeta test '" + test + "' dentro del " +
                        "proyecto");
            }
        }
    }

    /**
     * Valida si existe el directorio
     * @param path
     * @return true si existe, false si no existe
     */
    protected boolean exists(String directory) {
        File f = new File(directory);
        return f.exists() && f.isDirectory();
    }

    /**
     * Obtiene la ruta absoluta del un directorio dentro de acuerdo al proyecto 
     * externo, por ejemplo si desea obtener la ruta absoluta del directorio classes, 
     * deberia utilizar este metodo como sigue:
     * getAbsolutePath(getClassesPath());
     * 
     * Si su proyecto netbeans externo fuere c:/NbApp1 y quisiera la ruta classes,
     * este m�todo retornar�a 
     * c:/NbApp1/build/classes
     * 
     * @param dir
     * @return
     */
    protected String getAbsolutePath(String dir) {
        return getPath() + File.separator + dir;
    }
}
