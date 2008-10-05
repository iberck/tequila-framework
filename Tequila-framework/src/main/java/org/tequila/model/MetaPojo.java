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
public class MetaPojo extends LazyDynaBean {

    private static final Log log = LogFactory.getLog(MetaPojo.class);
    private Object originalObject;

    /**
     * Crea una MetaPojo en base a un objeto
     * @param object
     * @throws MetaPojoException
     */
    public MetaPojo(Object object) throws MetaPojoException {
        this.originalObject = object;

        try {
            PropertyUtils.copyProperties(this, object);
        } catch (IllegalAccessException ex) {
            throw new MetaPojoException("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            throw new MetaPojoException("InvocationTargetException", ex);
        } catch (NoSuchMethodException ex) {
            throw new MetaPojoException("NoSuchMethodException", ex);
        }
    }

    /**
     * Crea una MetaPojo a partir del nombre de una clase
     * @param className Nombre de la clase
     * @throws MetaPojoException
     */
    public MetaPojo(String className) throws MetaPojoException {
        try {
            Object instance = Class.forName(className).newInstance();
            this.originalObject = instance;
            PropertyUtils.copyProperties(this, instance);
        } catch (ClassNotFoundException ex) {
            throw new MetaPojoException("ClassNotFoundException", ex);
        } catch (InstantiationException ex) {
            throw new MetaPojoException("InstantiationException", ex);
        } catch (IllegalAccessException ex) {
            throw new MetaPojoException("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            throw new MetaPojoException("InvocationTargetException", ex);
        } catch (NoSuchMethodException ex) {
            throw new MetaPojoException("NoSuchMethodException", ex);
        }
    }

    public Object getOriginalObject() {
        return originalObject;
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

            // propiedades originales
            Field[] originalProps = originalObject.getClass().getDeclaredFields();

            // Crear nuevas propiedades
            MetaPojo[] newProps = new MetaPojo[injectedProps.length + originalProps.length];
            int i = 0;
            for (Field field : originalProps) {
                newProps[i++] = new MetaPojo(field);
            }
            for (DynaProperty injectedProp : injectedProps) {
                newProps[i++] = new MetaPojo(injectedProp);
            }

            // Modificar los objetos class y declaredFields
            MetaPojo clazz = new MetaPojo(originalObject.getClass());
            clazz.removeProperty("declaredFields");
            PropertyUtils.setNestedProperty(clazz, "declaredFields", newProps);

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
