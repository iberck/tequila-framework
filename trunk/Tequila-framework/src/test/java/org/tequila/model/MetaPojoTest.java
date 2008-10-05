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
package org.tequila.model;

import java.lang.reflect.Field;
import junit.framework.TestCase;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author iberck
 */
public class MetaPojoTest extends TestCase {

    private static final Log log = LogFactory.getLog(MetaPojoTest.class);

    public MetaPojoTest(String testName) {
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
     * Simula como lo haria el template engine
     */
    public void testMetaPojo() throws Exception {
        MyBean bean = new MyBean();
        bean.setNombre("iberck");
        MetaPojo metaClass = new MetaPojo(bean);
        assertEquals(metaClass.get("nombre"), "iberck");

        // inyectar propiedad llamada edad
        metaClass.injectProperty("edad", "3");

        // obtener un objeto inyectado
        Object injectedObject = metaClass.getInjectedObject();

        // test de la inyeccion del objeto
        Object clazz = PropertyUtils.getNestedProperty(injectedObject, "class");
        assertEquals(clazz.getClass().getSimpleName(), "MetaPojo");

        // test de las propiedades originales del objeto
        Object[] declaredFields = (Object[]) PropertyUtils.getNestedProperty(clazz, "declaredFields");
        assertEquals(declaredFields[0].getClass().getSimpleName(), "MetaPojo");
        Object originalObject = ((MetaPojo) declaredFields[0]).getOriginalObject();
        assertTrue(originalObject instanceof java.lang.reflect.Field);
        assertEquals("nombre", ((Field) originalObject).getName());
        assertEquals("java.lang.String", ((Field) originalObject).getType().getName());
        Object nombreNP = PropertyUtils.getNestedProperty(injectedObject, "nombre");
        assertEquals("java.lang.String", nombreNP.getClass().getName());

        // test de las propiedades inyectadas
        Object dynaProp = declaredFields[1];
        assertNotNull(dynaProp);
        assertEquals(dynaProp.getClass().getSimpleName(), "MetaPojo");
        Object originalDynaObject = ((MetaPojo) dynaProp).getOriginalObject();
        assertTrue((originalDynaObject instanceof DynaProperty));

        Object edadNP = PropertyUtils.getNestedProperty(injectedObject, "edad");
        assertEquals("java.lang.String", edadNP.getClass().getName());

        // test de un metapojo a partir del nombre de la clase
        MetaPojo fromClassName = new MetaPojo("org.tequila.model.MyBean");
        assertNotNull(fromClassName);
    }
}
