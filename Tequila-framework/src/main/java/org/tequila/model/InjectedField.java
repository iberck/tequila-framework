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
package org.tequila.model;

import org.springframework.beans.factory.annotation.Required;

/**
 *
 * @author iberck
 */
public class InjectedField {

    private String fieldName;
    private String injectedPropertyName;
    private Object injectedPropertyValue;

    public String getFieldName() {
        return fieldName;
    }

    @Required
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getInjectedPropertyName() {
        return injectedPropertyName;
    }

    @Required
    public void setInjectedPropertyName(String injectedPropertyName) {
        this.injectedPropertyName = injectedPropertyName;
    }

    public Object getInjectedPropertyValue() {
        return injectedPropertyValue;
    }

    @Required
    public void setInjectedPropertyValue(Object injectedPropertyValue) {
        this.injectedPropertyValue = injectedPropertyValue;
    }
}
