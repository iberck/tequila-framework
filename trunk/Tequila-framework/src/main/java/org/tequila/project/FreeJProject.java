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

/**
 * Esta clase modela proyectos que no tienen una estructura estandar, si se cuenta
 * con un proyecto de este tipo, se debería crear una instancia de esta clase
 * para definir donde se encuentra cada carpeta.
 * 
 * Muchos desarrolladores siguen sus propias convenciones de carpetas, 
 * esta clase sirve para crear proyectos que no sigan una estructura específica.
 * 
 * @author iberck
 */
public class FreeJProject extends JProject {

    private String classesPath;
    private String sourcesPath;
    private String testPath;

    public FreeJProject(String path) {
        super(path);
    }

    public void setClassesPath(String classesPath) {
        this.classesPath = classesPath;
    }

    public void setSourcesPath(String sourcesPath) {
        this.sourcesPath = sourcesPath;
    }

    public void setTestPath(String testPath) {
        this.testPath = testPath;
    }

    @Override
    public String getClassesPath() {
        return classesPath;
    }

    @Override
    public String getSourcesPath() {
        return sourcesPath;
    }

    @Override
    public String getTestPath() {
        return testPath;
    }
}
