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

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author iberck
 */
public class MetaClassTest extends TestCase {

    private static final Log log = LogFactory.getLog(MetaClassTest.class);

    public MetaClassTest(String testName) {
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

    public void testMetaClass() {
        try {
            MyBean bean = new MyBean();
            bean.setNombre("iberck");
            MetaPojo metaClass = new MetaPojo(bean);
            assertEquals(metaClass.get("nombre"), "iberck");
            metaClass.injectProperty("edad", "3");
            Object injectedObject = metaClass.getInjectedObject();
            
            Object clazz = PropertyUtils.getNestedProperty(injectedObject, "class");
            log.debug("clazz: " + clazz);
            Object declaredFields = PropertyUtils.getNestedProperty(clazz, "declaredFields");
            log.debug("declaredFields: " + declaredFields);
            
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetaClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MetaClassTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MetaClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            //Object declaredFields = PropertyUtils.getNestedProperty(clazz, "declaredFields");
            //log.debug("declaredFields: " + declaredFields);

        
    }
}
