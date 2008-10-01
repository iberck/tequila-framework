/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tequila.template.wrapper;

import java.io.File;
import java.util.Map;
import junit.framework.TestCase;
import org.tequila.project.JProject;
import org.tequila.project.NbJProject;

/**
 *
 * @author iberck
 */
public class ProjectWrapTest extends TestCase {

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

    public void testProjectWrapp() {
        JProject nbProject = new NbJProject("./src/test/resources/NbApplication");

        Map projectWrap = nbProject.wrap();

        Map projectProps = (Map) projectWrap.get(JProjectWrapper.PROJECT_PROPERTIES);
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_NAME), "NbApplication");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_PATH), "./src/test/resources/NbApplication");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_SRC_PATH), "src");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_SRC_ABS_PATH), "./src/test/resources/NbApplication" + File.separator + "src");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_CLASSES_PATH), "build" + File.separator + "classes");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_CLASSES_ABS_PATH), "./src/test/resources/NbApplication" + File.separator + "build" + File.separator + "classes");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_TEST_PATH), "test");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_TEST_ABS_PATH), "./src/test/resources/NbApplication" + File.separator + "test");
    }
}
