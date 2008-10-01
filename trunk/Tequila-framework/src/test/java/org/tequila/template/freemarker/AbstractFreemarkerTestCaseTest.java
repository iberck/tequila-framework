package org.tequila.template.freemarker;

import java.io.File;
import java.util.Map;
import org.tequila.project.JProject;
import org.tequila.project.NbJProject;

/**
 *
 * @author iberck
 */
public class AbstractFreemarkerTestCaseTest extends AbstractFreemarkerTestCase {

    public AbstractFreemarkerTestCaseTest(String testName) {
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

    /**
     * Testea el testcase de freemarker
     */
    public void testAbstractFreemarkerTestCase() throws Exception {
        // un proyecto cualquiera
        JProject nbProject = new NbJProject("./src/test/resources/NbApplication");
        nbProject.setup();
        Map projectWrap = nbProject.getProjectWrapper().wrap(nbProject);

        String templateDef = "${project.name}, ${project.path}, ${project.srcPath}," +
                "${project.classesPath}, ${project.testPath}";

        String expected = "NbApplication, ./src/test/resources/NbApplication, src," +
                "build" + File.separator + "classes, test";

        // test case de las cadenas.
        assertEqualsFreemarkerTemplate(projectWrap, templateDef, expected);

        // test case de los archivos.
        File temFile = new File("./src/test/resources/templates/test1.ftl");
        assertEqualsFreemarkerTemplate(projectWrap, temFile, "src");
    }
}
