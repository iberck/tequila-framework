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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.tequila.model.JMetaPojo;
import org.tequila.model.MetaPojo;
import org.tequila.template.engine.FreemarkerEngine;
import org.tequila.template.engine.TemplateEngine;
import org.tequila.template.freemarker.AbstractFreemarkerTestCase;
import org.tequila.template.wrapper.MetaPojosWrapper;

/**
 *
 * @author iberck
 */
public class MetaPojosWrapTest extends AbstractFreemarkerTestCase {

    public MetaPojosWrapTest(String testName) {
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

    public void testMetaPojosFreemarker() throws Exception {
        // crear lista de metapojos
        List<MetaPojo> jMetaPojos = new ArrayList();
        MetaPojo bean1 = new JMetaPojo("org.tequila.template.wrapper.freemarker.Bean1");
        MetaPojo bean2 = new JMetaPojo("org.tequila.template.wrapper.freemarker.Bean2");
        MetaPojo bean3 = new JMetaPojo("org.tequila.template.wrapper.freemarker.Bean3");

        // injectar propiedad
        bean1.injectProperty("injectedProperty1", "value");

        jMetaPojos.add(bean1);
        jMetaPojos.add(bean2);
        jMetaPojos.add(bean3);

        TemplateEngine engine = new FreemarkerEngine();
        MetaPojosWrapper metaPojosWrapper = engine.getEngineWrappersFactory().getMetaPojosWrapper();
        Map metaPojosWrapped = (Map) metaPojosWrapper.wrap(jMetaPojos);

        // template0 test
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "${metaPojos.class.name}", "java.util.ArrayList");

        // template1 test (simpleName)
        StringBuilder t1 = new StringBuilder();
        t1.append("<#list metaPojos as pojo>");
        t1.append("${pojo.class.simpleName},");
        t1.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, t1.toString(), "Bean1,Bean2,Bean3,");

        // test de los fields y propiedad inyectada a la clase
        StringBuilder t2 = new StringBuilder();
        t2.append("<#list metaPojos as pojo>");
        t2.append("<#list pojo.class.declaredFields as field>");
        t2.append("${field.name},");
        t2.append("</#list>");
        t2.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, t2.toString(), "prop1,prop1_1,injectedProperty1,prop2,prop3,");
    }
}
