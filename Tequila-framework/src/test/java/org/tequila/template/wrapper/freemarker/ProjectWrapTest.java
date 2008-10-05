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
package org.tequila.template.wrapper.freemarker;

import java.io.File;
import java.util.Map;
import org.tequila.model.project.JProject;
import org.tequila.model.project.NbJProject;
import org.tequila.model.project.NbJWebProject;
import org.tequila.template.engine.FreemarkerEngine;
import org.tequila.template.engine.TemplateEngine;
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

    public void testFreemarkerNbJProjectWrapp() throws Exception {
        JProject nbProject = new NbJProject("./src/test/resources/NbApplication");
        TemplateEngine engine = new FreemarkerEngine();
        nbProject.setProjectWrapperFactory(engine.getEngineWrappersFactory().getProjectWrapperFactory());
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

    public void testFreemarkerNbJWebProjectWrapp() throws Exception {
        JProject nbProject = new NbJWebProject("./src/test/resources/NbWebApplicationTest");
        TemplateEngine engine = new FreemarkerEngine();
        nbProject.setProjectWrapperFactory(engine.getEngineWrappersFactory().getProjectWrapperFactory());
        nbProject.setup();

        Map projectWrap = (Map) nbProject.getProjectWrapper().wrap(nbProject);
        Map projectProps = (Map) projectWrap.get(FreemarkerJWebProjectWrapper.PROJECT_PROPERTIES);
        assertEquals(projectProps.get(FreemarkerJWebProjectWrapper.PROJECT_NAME), "NbWebApplicationTest");
        assertEquals(projectProps.get(FreemarkerJWebProjectWrapper.PROJECT_PATH), "./src/test/resources/NbWebApplicationTest");
        assertEquals(projectProps.get(FreemarkerJWebProjectWrapper.PROJECT_SRC_PATH), "src");
        assertEquals(projectProps.get(FreemarkerJWebProjectWrapper.PROJECT_CLASSES_PATH), "build" + File.separator + "web" +
                File.separator + "WEB-INF" + File.separator + "classes");
        assertEquals(projectProps.get(FreemarkerJWebProjectWrapper.PROJECT_TEST_PATH), "test");
        assertEquals(projectProps.get(FreemarkerJWebProjectWrapper.PROJECT_WEB_INF_PATH), "web" + File.separator + "WEB-INF");

        // freemarker test
        assertEqualsFreemarkerTemplate(projectWrap, "${project.name}", "NbWebApplicationTest");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.path}", "./src/test/resources/NbWebApplicationTest");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.srcPath}", "src");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.classesPath}", "build" + File.separator + "web" +
                File.separator + "WEB-INF" + File.separator + "classes");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.testPath}", "test");
        assertEqualsFreemarkerTemplate(projectWrap, "${project.webInfPath}", "web" + File.separator + "WEB-INF");
    }
}
