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
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_CLASSES_PATH), "build" + File.separator + "classes");
        assertEquals(projectProps.get(JProjectWrapper.PROJECT_TEST_PATH), "test");
    }
}
