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
package org.tequila.template.engine;

import java.util.List;
import org.springframework.beans.factory.annotation.Required;
import org.tequila.model.GroupTemplates;
import org.tequila.model.TemplateDef;

/**
 *
 * @author iberck
 */
public class AtomicMatcher {

    private List<GroupTemplates> groups;
    private List<TemplateDef> templates;
    private TemplateEngine engine;

    public TemplateEngine getEngine() {
        return engine;
    }

    @Required
    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public List<GroupTemplates> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupTemplates> groups) {
        this.groups = groups;
    }

    public List<TemplateDef> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateDef> templates) {
        this.templates = templates;
    }
}
