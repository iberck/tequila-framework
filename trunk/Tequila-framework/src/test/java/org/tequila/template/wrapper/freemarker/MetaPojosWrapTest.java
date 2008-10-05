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
        bean1.injectPojoProperty("injectedProperty1", "value");

        jMetaPojos.add(bean1);
        jMetaPojos.add(bean2);
        jMetaPojos.add(bean3);

        TemplateEngine engine = new FreemarkerEngine();
        MetaPojosWrapper metaPojosWrapper = engine.getEngineWrappersFactory().getMetaPojosWrapper();
        Map metaPojosWrapped = (Map) metaPojosWrapper.wrap(jMetaPojos);

        // template1 test (simpleName)
        StringBuilder t1 = new StringBuilder();
        t1.append("<#list metaPojos as pojo>");
        t1.append("${pojo.class.simpleName},");
        t1.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "Bean1,Bean2,Bean3,", t1.toString());

        // test de los fields y propiedad inyectada a la clase
        StringBuilder t2 = new StringBuilder();
        t2.append("<#list metaPojos as pojo>");
        t2.append("<#list pojo.class.declaredFields as field>");
        t2.append("${field.name},");
        t2.append("</#list>");
        t2.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "injectedProperty1,prop1,prop1_1,prop2,prop3,", t2.toString());

    // test de inyeccion de fields a las clases

    }

    public void testMetaPojosInjectedFieldsFreemarker() throws Exception {

        // crear lista de metapojos
        List<MetaPojo> jMetaPojos = new ArrayList();
        JMetaPojo bean1 = new JMetaPojo("org.tequila.template.wrapper.freemarker.Bean2");

        // injectar field
        bean1.injectFieldProperty("prop2", "primaryKey", "value");
        jMetaPojos.add(bean1);

        TemplateEngine engine = new FreemarkerEngine();
        MetaPojosWrapper metaPojosWrapper = engine.getEngineWrappersFactory().getMetaPojosWrapper();
        Map metaPojosWrapped = (Map) metaPojosWrapper.wrap(jMetaPojos);

        // solo debe tener la propiedad prop2
        StringBuilder t2 = new StringBuilder();
        t2.append("<#list metaPojos as pojo>");
        t2.append("<#list pojo.class.declaredFields as field>");
        t2.append("${field.name},");
        t2.append("</#list>");
        t2.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "prop2,", t2.toString());

        // Field inyectado a nivel clase
        StringBuilder t3 = new StringBuilder();
        t3.append("<#list metaPojos as pojo>");
        t3.append("<#if pojo.prop2.primaryKey??>si existe<#else>no existe</#if>");
        t3.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "si existe", t3.toString());

        // test ??
        StringBuilder t4 = new StringBuilder();
        t4.append("<#list metaPojos as pojo>");
        t4.append("<#if pojo.prop2.anyProperty??>si existe<#else>no existe</#if>");
        t4.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "no existe", t4.toString());

        // field inyectado a nivel declaredFields
        StringBuilder t6 = new StringBuilder();
        t6.append("<#list metaPojos as pojo>");
        t6.append("<#list pojo.class.declaredFields as field>");
        t6.append("<#if field.primaryKey??>");
        t6.append("declaredField inyectado satisfactoriamente");
        t6.append("<#else>");
        t6.append("error");
        t6.append("</#if>");
        t6.append("</#list>");
        t6.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "declaredField inyectado satisfactoriamente", t6.toString());
    }

    public void testGeneralInjection() throws Exception {
        // crear lista de metapojos
        List<MetaPojo> jMetaPojos = new ArrayList();
        JMetaPojo bean1 = new JMetaPojo("org.tequila.template.wrapper.freemarker.Bean2");

        // injectar field
        bean1.injectPojoProperty("nuevaPropiedad", new Integer(5));
        bean1.injectFieldProperty("nuevaPropiedad", "validate", "view");//inyectar propiedad nueva
        bean1.injectFieldProperty("prop2", "primaryKey", "value");//inyectar propiedad existente
        jMetaPojos.add(bean1);

        TemplateEngine engine = new FreemarkerEngine();
        MetaPojosWrapper metaPojosWrapper = engine.getEngineWrappersFactory().getMetaPojosWrapper();
        Map metaPojosWrapped = (Map) metaPojosWrapper.wrap(jMetaPojos);

        // propiedades
        StringBuilder t2 = new StringBuilder();
        t2.append("<#list metaPojos as pojo>");
        t2.append("<#list pojo.class.declaredFields as field>");
        t2.append("${field.name},");
        t2.append("</#list>");
        t2.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "prop2,nuevaPropiedad,", t2.toString());

        // fields existente inyectado
        StringBuilder t3 = new StringBuilder();
        t3.append("<#list metaPojos as pojo>");
        t3.append("<#list pojo.class.declaredFields as field>");
        t3.append("<#if field.name=='prop2' && field.primaryKey??>");
        t3.append("field existente inyectado ${field.name} ${field.primaryKey}");
        t3.append("</#if>");
        t3.append("</#list>");
        t3.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "field existente inyectado prop2 value", t3.toString());

        // fields nuevo inyectado
        StringBuilder t4 = new StringBuilder();
        t4.append("<#list metaPojos as pojo>");
        t4.append("<#list pojo.class.declaredFields as field>");
        t4.append("<#if field.name=='nuevaPropiedad' && field.validate??>");
        t4.append("metafield inyectado ${field.name} ${field.validate}");
        t4.append("</#if>");
        t4.append("</#list>");
        t4.append("</#list>");
        assertEqualsFreemarkerTemplate(metaPojosWrapped, "metafield inyectado nuevaPropiedad view", t4.toString());
    }
}
