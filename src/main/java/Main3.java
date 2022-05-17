import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main3 {
    static Set<String> worlds = new HashSet<>();
    static Set<String> tags = Stream.of( "a").collect(Collectors.toSet());

    public static void main(String[] args) throws IOException {
        String url = "https://www.tvblog.it/post/1681999/valerio-fabrizio-salvatori-gli-inseparabili-chi-sono-pechino-express-2020";
        final Elements allElements = Jsoup.connect(url).get().getAllElements();
        Iterator<Element> iterator = allElements.iterator();

        while (iterator.hasNext()) {
            final Element element = iterator.next();
            final Iterator<Node> child = element.childNodes().iterator();
            readChild(child);
        }

        int c = 0;
        for (String world : worlds) {
            System.out.println("" + c++ +" "+ world);
        }

    }

    private static void readChild(Iterator<Node> child) {
        while (child.hasNext()) {
            final Node next = child.next();
            if (next instanceof Element) {
                final Element element = (Element) next;
                if (tags.contains(element.tagName())) {
                    removeTags(element);
                }
            }
        }
    }

    private static void removeTags(Element element) {
        final List<Node> children = element.childNodes();
        if (children.size() > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            final Iterator<Node> childIterator = children.iterator();
            while (childIterator.hasNext()) {
                final Node child = childIterator.next();
                if (child.nodeName().equals("#text")) {
                    stringBuilder.append(child.outerHtml());
                } else {
                    defineChildren(stringBuilder, child);
                    prepareToSave(stringBuilder.toString());
                }
            }

        } else {
            if (children.get(0) instanceof Element) {
                final Element el = (Element) children.get(0);
                prepareToSave(el.html());
            } else {
                prepareToSave(element.html());
            }
        }

    }

    private static void prepareToSave(String line) {
        line = line.replaceAll("&nbsp;|&amp;", "").trim();
        worlds.add(line);
    }

    private static void defineChildren(StringBuilder stringBuilder, Node child) {
        final Iterator<Node> iterator = child.childNodes().iterator();
        while (iterator.hasNext()) {
            final Node current = iterator.next();
            if (current.nodeName().equals("#text")) {
                stringBuilder.append(current.outerHtml());
            } else {
                defineChildren(stringBuilder, current);
            }
        }
    }
}
