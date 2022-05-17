import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2 {
    public static void main(String[] args) {
        String s =
                "<a class=\"textual-link text-link author__name\" href=\"https://www.tvblog.it/post/author/marcosalaris\">Marco Salaris</a>";
//        for (String s1 : s.split(">*</")) {
//            System.out.println(s1);
//        }
//        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\\\"([^\"]*\\\")|'[^']*'|([^'\">\\s]+))");
        Pattern pattern = Pattern.compile(">(.+?)</");
        Matcher m = pattern.matcher(s);
        String url = null;
        if (m.find()) {
            url = m.group(1); // this variable should contain the link URL
        }
        System.out.println(url);
    }
}
