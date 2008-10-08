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
package org.tequila.template.directive.freemarker;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tequila.model.project.JProject;
import org.tequila.template.directive.DirectiveException;
import org.tequila.template.directive.TemplateDirective;
import org.tequila.template.match.TemplateProcessed;
import org.tequila.template.match.TemplatesWriterPool;
import org.tequila.conf.SpringUtils;
import org.tequila.conf.ProjectHolder;

/**
 *
 * @author iberck
 */
public class JFileSection extends Observable implements TemplateDirectiveModel, TemplateDirective {

    private static final Log log = LogFactory.getLog(JFileSection.class);
    private TemplatesWriterPool pool;
    private ProjectHolder projectHolder;

    public JFileSection() {
        // TODO: Desacoplar esto, no deberia tomar el proyecto del observador
        // mas bien de un proyect holder
        pool = (TemplatesWriterPool) SpringUtils.getBean("templatesWriterPool");
        addObserver(pool);

        projectHolder = (ProjectHolder) SpringUtils.getBean("projectHolder");
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) {
        log.debug("Executing <@" + getDirectiveName() + " .../>");

        if (loopVars.length != 0) {
            throw new DirectiveException("<@" + getDirectiveName() + " .../> doesn't allow loop variables.");
        }

        // If there is non-empty nested content:
        if (body != null) {
            try {
                String test = null;
                Set<Entry<String, SimpleScalar>> entrySet = params.entrySet();
                for (Entry<String, SimpleScalar> entry : entrySet) {
                    String key = entry.getKey();
                    SimpleScalar value = entry.getValue();
                    if (key.equals("test")) {
                        test = value.getAsString();
                    } else {
                        throw new DirectiveException("<@" + getDirectiveName() + " .../> doesn't allow " + key + " parameter");
                    }
                }
                // Executes the nested body. Same as <#nested> in FTL, except
                // that we use our own writer instead of the current output writer.
                // write the file
                StringWriter sw = new StringWriter();
                BufferedWriter bw = new BufferedWriter(sw);
                body.render(bw);
                env.getOut().flush();
                bw.flush();
                sw.flush();
                bw.close();
                sw.close();

                JProject project = (JProject) projectHolder.getProject();
                String srcPath = project.getSrcPath();
                if ("true".equalsIgnoreCase(test)) {
                    srcPath = project.getTestPath();
                }

                String jFileName = JFileSection.getJFileName(sw.toString());
                String jPackagePath = JFileSection.getPackagePath(sw.toString());

                TemplateProcessed tp = new TemplateProcessed();
                tp.setOutputFolder(File.separator + srcPath + File.separator + jPackagePath);
                tp.setOutputFileName(jFileName);
                tp.setTemplateResult(sw.toString());

                super.setChanged();
                super.notifyObservers(tp);

            } catch (TemplateException ex) {
                throw new DirectiveException("Error al ejecutar la directiva '" + getDirectiveName() + "'", ex);
            } catch (IOException ex) {
                throw new DirectiveException("Error al ejecutar la directiva '" + getDirectiveName() + "'", ex);
            }
        } else {
            throw new DirectiveException("missing body");
        }
    }

    @Override
    public String getDirectiveName() {
        return "JFileSection";
    }

    public static String getJFileName(String source) {
        String fileName = null;

        final String FILENAME_REGEX = "public\\s+(\\w+\\s+)*(class|interface|enum)\\s+(\\w+)\\s+";
        Pattern pattern = Pattern.compile(FILENAME_REGEX);
        Matcher matcher = pattern.matcher(source);
        boolean found = false;

        while (matcher.find()) {
            found = true;
            fileName = matcher.group(matcher.groupCount());
        }

        if (!found) {
            throw new IllegalArgumentException(
                    "The source is not a valid java file");
        }

        return fileName + ".java";
    }

    public static String getPackagePath(String source) {
        String path = null;

        final String PATH_REGEX = "package\\s+(.+)\\s*;";
        Pattern pattern = Pattern.compile(PATH_REGEX);
        Matcher matcher = pattern.matcher(source);
        boolean found = false;

        while (matcher.find()) {
            found = true;
            path = matcher.group(1).trim();
        }

        // the file does not contain package
        if (!found) {
            return "";
        }

        return path.replace(".", File.separator);
    }
}
