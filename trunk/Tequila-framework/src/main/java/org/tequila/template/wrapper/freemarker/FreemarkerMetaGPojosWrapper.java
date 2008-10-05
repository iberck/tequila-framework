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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.tequila.model.GMetaPojo;
import org.tequila.model.MetaPojo;
import org.tequila.template.wrapper.MetaPojosWrapper;

/**
 *
 * @author iberck
 */
public class FreemarkerMetaGPojosWrapper implements MetaPojosWrapper<GMetaPojo> {

    FreemarkerMetaGPojosWrapper() {
    }

    @Override
    public Map wrap(List<GMetaPojo> metaPojos) {
        List metaPojosInjected = new ArrayList();

        for (MetaPojo metaPojo : metaPojos) {
            metaPojosInjected.add(metaPojo.createInjectedObject());
        }

        Map m = new HashMap();
        m.put(MetaPojosWrapper.POJOS_KEY, metaPojosInjected);

        return m;
    }
}
