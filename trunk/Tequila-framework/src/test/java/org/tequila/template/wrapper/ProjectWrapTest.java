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

import java.io.File;
import java.util.Map;
import org.tequila.project.JProject;
import org.tequila.project.NbJProject;
import org.tequila.template.freemarker.AbstractFreemarkerTestCase;

/**
 *
 * @author iberck
 */
public class ProjectWrapTest extends AbstractFreemarkerTestCase {

    public ProjectWrapTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testFreemarkerNbProjectWrapp() throws Exception {


        JProject nbProject = new NbJProject("./src/test/resources/NbApplication");
        ProjectWrapperFactory prjWrapperFactory = new FreemarkerProjectWrapperFactory();
        nbProject.setProjectWrapperFactory(prjWrapperFactory);
        nbProject.setup();
        
        Map projectWrap = (Map) nbProject.getProjectWrapper().wrap(nbProject);
        Map projectProps = (Map) projectWrap.get(FreemarkerJProjectWrapper.PROJECT_PROPERTIES);
        assertEquals(projectProps.get(FreemarkerJProjectWrapper.PROJECT_NAME), "NbApplication");
        assertEquals(projectProps.get(FreemarkerJProjectWrapper.PROJECT_PATH), "./src/test/resources/NbApplication");
        assertEquals(projectProps.get(FreemarkerJProjectWrapper.PROJECT_SRC_PATH), "src");
        assertEquals(projectProps.get(FreemarkerJProjectWrapper.PROJECT_CLASSES_PATH), "build" + File.separator + "classes");
        assertEquals(projectProps.get(FreemarkerJProjectWrapper.PROJECT_TEST_PATH), "test");

        // freemarker test
        assertEqualsFreemarkerTemplate(projectWrap, "${project.name}", "NbApplication");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.path}", "./src/test/resources/NbApplication");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.srcPath}", "src");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.classesPath}", "build" + File.separator + "classes");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.testPath}", "test");

    }
}
