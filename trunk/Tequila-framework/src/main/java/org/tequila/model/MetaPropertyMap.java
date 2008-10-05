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

import java.util.Map;
import org.tequila.template.wrapper.MetaPropertyWrapper;
import org.tequila.template.wrapper.MetaPropertyWrapperFactory;

/**
 *
 * @author iberck
 */
public class MetaPropertyMap implements MetaProperty<Map> {

    private Map property;
    private MetaPropertyWrapperFactory factory;

    @Override
    public void setProperty(Map property) {
        this.property = property;
    }

    @Override
    public Map getProperty() {
        return property;
    }

    @Override
    public void setMetaPropertyWrapperFactory(MetaPropertyWrapperFactory factory) {
        this.factory = factory;
    }

    @Override
    public MetaPropertyWrapperFactory getMetaPropertyWrapperFactory() {
        return factory;
    }

    @Override
    public MetaPropertyWrapper getMetaPropertyWrapper() {
        return factory.getMetaPropertyMapWrapper();
    }
}
