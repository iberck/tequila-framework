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

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tequila.template.wrapper.JProjectWrapper;

/**
 * Esta clase modela los proyectos externos de tipo java básico
 * @author iberck
 */
public abstract class JProject implements ExternalProject {

    protected static final Log log = LogFactory.getLog(JProject.class);
    private final String path;
    protected JProjectWrapper projectWrapper;

    /**
     * Crea un nuevo JProject a partir de una ruta
     * @param path Ruta del proyecto
     * @throws org.tequila.project.ProjectException Se puede lanzar bajo las 
     * siguientes circunstancias: 
     *  1. El proyecto no es valido
     *  2. El proyecto no se puede meter al RuntimeClasspath del framework.
     */
    protected JProject(String path) throws ProjectException {
        this.path = path;

        // wrapper del proyecto.
        projectWrapper = new JProjectWrapper();

        // validar el proyecto
        validateProject();

        // introducir el proyecto al classpath
        RuntimeClassPath.addResource(getPath() + File.separator + getClassesPath());
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
            throw new ProjectException("El nombre del proyecto no es válido");
        }

        return projectName;
    }

    @Override
    public Map wrap() {
        return projectWrapper.wrap(this);
    }

    /**
     * Método para introducir el proyecto externo al classpath del framework
     * con el objetivo poder ocupar los recursos externos en tiempo de ejecución.
     * @see RuntimeClassPath
     */
    protected void addToInternalClassPath() {
        RuntimeClassPath.addResource(getPath() + File.separator + getClassesPath());
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
     * Valida que se encuentre al menos la estructurá básica del proyecto.
     *  - Valida que exista la ruta del proyecto
     *  - Valida que se encuentre la carpeta sources
     *  - Valida que se encuentre la carpeta classes
     *  - Valida que se encuentre la carpeta test (si el proyecto la soporta), 
     *    si no la soporta los proyectos que extiendan de esta clase deberán 
     *    retornar null en el método getTestPath();
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
     * este método retornaría 
     * c:/NbApp1/build/classes
     * 
     * @param dir
     * @return
     */
    public String getAbsolutePath(String dir) {
        return getPath() + File.separator + dir;
    }

    /**
     * Esta clase modela el classpath interno del framework y sirve para introducir
     * recursos de un proyecto externo al classpath interno con el propósito de
     * obtener hacer uso de ellos en tiempo de ejecución.
     * 
     * @author iberck
     */
    protected static class RuntimeClassPath {

        private static final Class<?>[] parameters = new Class[]{URL.class};

        /**
         * Agrega una url al classloader interno
         * @see RuntimeClassPath
         * @param url
         * @throws ProjectException No se puede agregar el recurso al classpath interno
         */
        private static void addURL(URL url) throws ProjectException {
            log.debug("Agregando el recurso '" + url + "' al classpath interno");

            try {
                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                Class<?> clazz = URLClassLoader.class;

                Method method = clazz.getDeclaredMethod("addURL", parameters);
                method.setAccessible(true);
                method.invoke(classLoader, new Object[]{url});
            } catch (Exception ex) {
                throw new ProjectException("No se puede agregar recurso " +
                        "'" + url + "' al classpath interno", ex);
            }
        }

        /**
         * Agrega un recurso de un proyecto externo al classpath interno
         * @param resource Archivo donde donde se encuentra el recurso o los recursos.
         *
         * Por ejemplo, para agregar las clases del proyecto c:/Proyect1 al classpath interno,
         * debería agregar la carpeta classes de la siguiente forma:
         * RuntimeClassPath.addFile(new File("c:/Proyect1/classes"));
         * lo cual permitiría crear objetos del proyecto externo en tiempo de ejecución.
         * Nota: No se pueden agregar archivos .class de forma individual, debe ser
         * toda la carpeta classes del proyecto externo.
         *
         * Otro ejemplo, para agregar el archivo c:/Proyect1/conf/app-context.xml al
         * classpath interno, deberá hacer algo como esto:
         * RuntimeClassPath.addFile(new File("c:/Proyect1/conf/app-context.xml"));
         * y a partir de ese momento lo tendrá a disposición.
         *
         * @throws ProjectException: En caso que no exista el recurso o
         * no se puede agregar al classpath interno.
         */
        static void addResource(File resource) throws ProjectException {
            if (!resource.exists()) {
                throw new ProjectException("No se pudo agregar el recurso " + "al " +
                        "classpath interno ya que no existe");
            }

            try {
                addURL(resource.toURI().toURL());
            } catch (MalformedURLException ex) {
                throw new ProjectException("No se pudo agregar el recurso al classpath interno", ex);
            }
        }

        /**
         * @see addResource(File resource)
         * @param resource
         * @throws ProjectException
         */
        static void addResource(String resource) throws ProjectException {
            addResource(new File(resource));
        }
    }
}
