import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static Set<String> result = new HashSet<>();

    public static void main(String[] args) throws IOException {
//        Set<String> tag = Stream.of("script", "button", "div", "svg", "style", "body", "head").collect(Collectors.toSet());
        Set<String> tag = Stream.of("li", "p", "a").collect(Collectors.toSet());

        String url = "https://www.tvblog.it/post/1681999/valerio-fabrizio-salvatori-gli-inseparabili-chi-sono-pechino-express-2020";
        final Elements allElements = Jsoup.connect(url).get().getAllElements();
        for (Element element : allElements) {
//            System.out.println("html element: " + element.html());
//            if (tag.contains(element.tagName())) {
//            System.out.println("tag is:" + element.tagName() );
//                if (element.tagName().equals("a")){
//                    final String txt = element.select("a").html();
//                    System.out.println("txt: " + txt);
//                } if (element.tagName().equals("p")){
//                    final String txt = element.select("p").html();
//                    System.out.println("txt: " + txt);
//                } else {
//                    System.out.println("html        :" + element);
//                    if (element.html().contains("a href=")) {
//                        final String txt = element.select("a").html();
//                        System.out.println("txt: " + txt);
//                    }
////                if (element.tagName().equals("p")) {
//                }

//                    parseTag(element, "p");
//            parseTag(element, "li");
                }

//            }

            }

//        }

//    private static void parseTag(Element element,String tag) {
////        if(element.tagName().equals("body")) {
////            for (String s : element.html().split("\n")) {
////                s = s.trim();
////                if (s.startsWith("<li ") || s.startsWith("<li>")){
////                    parseLi(s);
////                }
//////                s.startsWith("<h") || s.startsWith("<p")) {
//////                    System.out.println(s.trim());
//////                }
////
////            }
////            ;
//        }

        /**
        final String[] split = element.html().split("\n");
        System.out.println("tag: " + element.tagName());
        System.out.println("element: " + element);
        for (String s : split) {
            System.out.println("line" + s);
            for (String s1 : s.split(">")) {
                System.out.println("subline" + s1);
            }
            ;
        }
         */

//        for (Element element1 : ppp) {
//            final String line = element.html();
//            Pattern pattern = Pattern.compile(">(.+?)</");
//            Matcher m = pattern.matcher(line);
//            String u = null;
//            if (m.find()) {
//                String toPut = m.group(1);
//                System.out.println("to Put" + toPut);
////                result.add();
//            }
//    }

    private static void parseLi(String s) {
        if (!s.contains("rel=")) {
            System.out.println(s);
            if (s.contains("<a href=")){
                extractLink();
            } else {
                Pattern pattern = Pattern.compile(">(.+?)</");
                Matcher m = pattern.matcher(s);
                String u = null;
                if (m.find()) {
                    String toPut = m.group(1);
                    System.out.println("to Put" + toPut);
                }
            }
        }
    }

    private static void extractLink() {

    }
}
