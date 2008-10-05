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
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author iberck
 */
public class JMetaPojo extends LazyDynaBean {

    private static final Log log = LogFactory.getLog(JMetaPojo.class);

    /**
     * Crea una JMetaPojo a partir del nombre de una clase
     * @param className Nombre de la clase
     * @throws MetaPojoException
     */
    public JMetaPojo(String className) throws MetaPojoException {
        this(instantiateClass(className));
    }

    /**
     * Crea una JMetaPojo en base a un objeto
     * @param object
     * @throws MetaPojoException
     */
    public JMetaPojo(Object instance) throws MetaPojoException {
        try {
            PropertyUtils.copyProperties(this, instance);
        } catch (IllegalAccessException ex) {
            throw new MetaPojoException("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            throw new MetaPojoException("InvocationTargetException", ex);
        } catch (NoSuchMethodException ex) {
            throw new MetaPojoException("NoSuchMethodException", ex);
        }
    }

    private static Object instantiateClass(String className) throws MetaPojoException {
        try {
            return Class.forName(className).newInstance();
        } catch (InstantiationException ex) {
            throw new MetaPojoException("InstantiationException", ex);
        } catch (IllegalAccessException ex) {
            throw new MetaPojoException("IllegalAccessException", ex);
        } catch (ClassNotFoundException ex) {
            throw new MetaPojoException("ClassNotFoundException", ex);
        }
    }

    public void injectProperty(String name, Object value) throws MetaPojoException {
        try {
            PropertyUtils.setNestedProperty(this, name, value);
        } catch (IllegalAccessException ex) {
            throw new MetaPojoException("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            throw new MetaPojoException("InvocationTargetException", ex);
        } catch (NoSuchMethodException ex) {
            throw new MetaPojoException("NoSuchMethodException", ex);
        }
    }

    public Object getInjectedObject() throws MetaPojoException {
        try {
            // propiedades injectadas
            DynaProperty[] injectedProps = getDynaClass().getDynaProperties();

            // Modificar los objetos class y declaredFields
            JMetaPojo clazz = new JMetaPojo(this.getClass());
            clazz.removeProperty("declaredFields");
            PropertyUtils.setNestedProperty(clazz, "declaredFields", injectedProps);

            this.removeProperty("class");
            PropertyUtils.setNestedProperty(this, "class", clazz);

        } catch (IllegalAccessException ex) {
            throw new MetaPojoException("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            throw new MetaPojoException("InvocationTargetException", ex);
        } catch (NoSuchMethodException ex) {
            throw new MetaPojoException("NoSuchMethodException", ex);
        }

        return this;
    }

    /**
     * Borra dinamicamente la propiedad
     * @param name Property name
     */
    protected void removeProperty(String propertyName) {
        ((LazyDynaClass) getDynaClass()).remove(propertyName);
    }
}
