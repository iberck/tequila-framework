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

import java.util.HashMap;
import java.util.Map;
import org.tequila.model.MetaPropertyObject;
import org.tequila.template.wrapper.MetaPropertyWrapper;

/**
 *
 * @author iberck
 */
public class FreemarkerMetaPropertyObjectWrapper implements MetaPropertyWrapper<MetaPropertyObject> {

    FreemarkerMetaPropertyObjectWrapper() {
    }

    @Override
    public Map wrap(MetaPropertyObject metaPropertyObject) {
        Map root = new HashMap();
        root.put(PROPERTY_KEY, metaPropertyObject.getProperty());
        return root;
    }
}
