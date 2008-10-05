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

import java.util.HashMap;
import java.util.Map;
import org.tequila.model.MetaProperty;
import org.tequila.model.MetaPropertyMap;
import org.tequila.model.MetaPropertyObject;
import org.tequila.template.engine.FreemarkerEngine;
import org.tequila.template.engine.TemplateEngine;
import org.tequila.template.freemarker.AbstractFreemarkerTestCase;
import org.tequila.template.wrapper.EngineWrappersFactory;

/**
 *
 * @author iberck
 */
public class PropertyWrapTest extends AbstractFreemarkerTestCase {

    public PropertyWrapTest(String testName) {
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

    public void testFreemarkerPropertyMapTest() throws Exception {
        TemplateEngine engine = new FreemarkerEngine();
        EngineWrappersFactory wrappersFactory = engine.getEngineWrappersFactory();

        MetaProperty metaPropertyMap = new MetaPropertyMap();
        metaPropertyMap.setMetaPropertyWrapperFactory(wrappersFactory.getMetaPropertyWrapperFactory());

        Map m = new HashMap();
        m.put("llave", "_valor_");
        metaPropertyMap.setProperty(m);

        Map rootMap = (Map) metaPropertyMap.getMetaPropertyWrapper().wrap(metaPropertyMap);

        assertEqualsFreemarkerTemplate(rootMap, "${metaProperty.llave}", "_valor_");
    }

    public void testFreemarkerPropertyObjectTest() throws Exception {
        TemplateEngine engine = new FreemarkerEngine();
        EngineWrappersFactory wrappersFactory = engine.getEngineWrappersFactory();

        MetaProperty metaPropertyObject = new MetaPropertyObject();
        metaPropertyObject.setMetaPropertyWrapperFactory(wrappersFactory.getMetaPropertyWrapperFactory());

        Cadena cad = new Cadena("hola mundo");
        metaPropertyObject.setProperty(cad);

        Map rootMap = (Map) metaPropertyObject.getMetaPropertyWrapper().wrap(metaPropertyObject);

        assertEqualsFreemarkerTemplate(rootMap, "${metaProperty.valor}", "hola mundo");
        assertEqualsFreemarkerTemplate(rootMap, "${metaProperty.class.simpleName}", "Cadena");
    }
}
