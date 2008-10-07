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

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.SimpleObjectWrapper;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.tequila.model.project.InternalClassPath;
import org.tequila.model.MetaPojo;
import org.tequila.model.MetaProperty;
import org.tequila.model.TemplateModel;
import org.tequila.model.project.ExternalProject;
import org.tequila.model.project.JProject;
import org.tequila.template.wrapper.EngineWrappersFactory;
import org.tequila.template.wrapper.MetaPropertyWrapper;
import org.tequila.template.wrapper.freemarker.FreemarkerWrappersFactory;

/**
 *
 * @author iberck
 */
public class FreemarkerEngine implements TemplateEngine {

    private BeansWrapper bw_instance;
    private Map projectWrapped;
    Configuration cfg;
    private EngineWrappersFactory engineWrappersFactory;

    public FreemarkerEngine() {
        bw_instance = SimpleObjectWrapper.getDefaultInstance();
        bw_instance.setMethodsShadowItems(false);
        bw_instance.setUseCache(true);
        engineWrappersFactory = new FreemarkerWrappersFactory();
    }

    @Override
    public EngineWrappersFactory getEngineWrappersFactory() {
        return engineWrappersFactory;
    }

    @Override
    public void match(TemplateModel templateModel) {

        try {
            File template = new File(templateModel.getTemplateDef().getPath());
            String fullPath = FilenameUtils.getFullPath(template.getAbsolutePath());
            cfg.setDirectoryForTemplateLoading(new File(fullPath));
            String relativeTemplate = FilenameUtils.getName(template.getAbsolutePath());
            freemarker.template.Template freeMarkerTemplate = cfg.getTemplate(relativeTemplate);
            StringWriter sw = new StringWriter();

            // create model
            Map root = new HashMap();
            // ${project}
            root.putAll(projectWrapped);
            // ${metapojos}
            List<MetaPojo> metaPojos = templateModel.getMetaPojos();
            Object metaPojosWrapped = getEngineWrappersFactory().getMetaPojosWrapper().wrap(metaPojos);
            root.putAll((Map) metaPojosWrapped);
            // ${metaproperty}
            MetaProperty metaProperty = templateModel.getMetaProperty();
            MetaPropertyWrapper metaPropertyWrapper = getEngineWrappersFactory().getMetaPropertyWrapper();
            Object metaPropertyWrapped = metaPropertyWrapper.wrap(metaProperty);

            root.putAll((Map) metaPropertyWrapped);

            Environment env = freeMarkerTemplate.createProcessingEnvironment(root, sw);
            env.process(); // process the template
            sw.close();
        } catch (TemplateException ex) {
            String templateName = templateModel.getTemplateDef().getName();
            throw new MatchException("No se pudo hacer match del template '" +
                    templateName + "' por un error en la definición del template", ex);
        } catch (IOException ex) {
            String templateName = templateModel.getTemplateDef().getName();
            throw new MatchException("No se pudo hacer match del template '" +
                    templateName + "' por un error de i/o", ex);
        }
    }

    @Override
    public void setUpEnvironment(ExternalProject project) {
        project.setUp();

        project.setProjectWrapperFactory(
                getEngineWrappersFactory().getProjectWrapperFactory());
        projectWrapped = (Map) project.getProjectWrapper().wrap(project);

        // setup engine
        cfg = new Configuration();
        //cfg.setDirectoryForTemplateLoading(tmp);
        cfg.setObjectWrapper(bw_instance);
    }
}
