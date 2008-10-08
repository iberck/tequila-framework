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
package org.tequila.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author iberck
 */
public class JTextFileUtils {

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
