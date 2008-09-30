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

/**
 * Esta clase modela proyectos que no tienen una estructura estandar, si se cuenta
 * con un proyecto de este tipo, se deber�a crear una instancia de esta clase
 * para definir donde se encuentra cada carpeta.
 * 
 * Muchos desarrolladores siguen sus propias convenciones de carpetas, 
 * esta clase sirve para crear proyectos que no sigan una estructura espec�fica.
 * 
 * @author iberck
 */
public class FreeJProject extends JProject {

    private final String classesPath;
    private final String sourcesPath;
    private final String testPath;

    /**
     * Crea un proyecto de estructura libre de acuerdo a los siguientes par�metros.
     * 
     * si su proyecto tiene los sources en la carpeta 'src' deber� mandar como segundo par�metro
     * "src" y as� sucesivamente.
     * 
     * @param path Path del proyecto
     * @param sourcesPath Carpeta relativa sources
     * @param classesPath Carpeta relativa a donde se encuentran los archivos .class
     * @param testPath Carpeta relativa al proyecto donde se encuentran los test
     */
    public FreeJProject(String path, String sourcesPath, String classesPath, String testPath) {
        super(path);

        this.sourcesPath = sourcesPath;
        this.classesPath = classesPath;
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
