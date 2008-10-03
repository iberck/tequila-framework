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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MetaClass extends LazyDynaBean {

    private static final Log log = LogFactory.getLog(MetaClass.class);
    private Object originalObject;

    /**
     * Crea una MetaClass en base a un objeto
     * @param object
     */
    public MetaClass(Object object) {
        this.originalObject = object;

        try {
            PropertyUtils.copyProperties(this, object);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void injectProperty(String name, Object value) {
        try {
            PropertyUtils.setNestedProperty(this, name, value);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getInjectedObject() {
        try {
            // propiedades injectadas
            DynaProperty[] injectedProps = getDynaClass().getDynaProperties();

            // propiedades originales
            Field[] originalProps = originalObject.getClass().getDeclaredFields();

            // Crear nuevas propiedades
            MetaClass[] newProps = new MetaClass[injectedProps.length + originalProps.length];
            int i = 0;
            for (Field originalProp : originalProps) {
                newProps[i++] = new MetaClass(originalProp);
            }
            for (DynaProperty injectedProp : injectedProps) {
                newProps[i++] = new MetaClass(injectedProp);
            }

            MetaClass clazz = new MetaClass(originalObject.getClass());
            clazz.removeProperty("declaredFields");
            PropertyUtils.setNestedProperty(clazz, "declaredFields", newProps);

            this.removeProperty("class");
            PropertyUtils.setNestedProperty(this, "class", clazz);

        } catch (IllegalAccessException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MetaClass.class.getName()).log(Level.SEVERE, null, ex);
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
