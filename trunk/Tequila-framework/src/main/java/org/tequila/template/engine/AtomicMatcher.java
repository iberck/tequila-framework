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
package org.tequila.template.engine;

import java.util.List;
import org.springframework.beans.factory.annotation.Required;
import org.tequila.model.TemplateModelsGroup;
import org.tequila.model.TemplateModel;
import org.tequila.model.project.ExternalProject;

/**
 *
 * @author iberck
 */
public class AtomicMatcher {

    private TemplateEngine engine;
    private List<TemplateModelsGroup> groupsMatch;
    private List<TemplateModel> templatesMatch;
    private ExternalProject project;

    public TemplateEngine getEngine() {
        return engine;
    }

    @Required
    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public List<TemplateModelsGroup> getGroupsMatch() {
        return groupsMatch;
    }

    public void setGroupsMatch(List<TemplateModelsGroup> groupsMatch) {
        this.groupsMatch = groupsMatch;
    }

    public List<TemplateModel> getTemplatesMatch() {
        return templatesMatch;
    }

    public void setTemplatesMatch(List<TemplateModel> templatesMatch) {
        this.templatesMatch = templatesMatch;
    }

    public ExternalProject getProject() {
        return project;
    }

    @Required
    public void setProject(ExternalProject project) {
        this.project = project;
    }

    public void match() {
        // inicializar el engine
        engine.setUpEnvironment(project);

        if (templatesMatch == null && groupsMatch == null) {
            throw new IllegalArgumentException("No se definido ning�n templatesMatch ni groupsMatch");
        }

        if (templatesMatch != null) {
            for (TemplateModel tModel : templatesMatch) {
                engine.match(tModel);
            }
        }

        if (groupsMatch != null) {
            for (TemplateModelsGroup tModelGroup : groupsMatch) {
                List<TemplateModel> templateModels = tModelGroup.getTemplateModels();
                for (TemplateModel tModel : templateModels) {
                    engine.match(tModel);
                }
            }
        }
    }
}
