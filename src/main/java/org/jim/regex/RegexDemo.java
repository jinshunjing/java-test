package org.jim.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author JSJ
 */
public class RegexDemo {

    public static void demoVersion() {
        String text = "2.1.3";
        String pattern = "^(.+)\\.(.+)\\.(.+)$";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        if (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                //System.out.println(m.group(i));
            }
        }
    }

    public static void demoVersion(Pattern p) {
        String text = "2.1.3";

        Matcher m = p.matcher(text);
        if (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                //System.out.println(m.group(i));
            }
        }
    }
}
