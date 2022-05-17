package com.example.bbk.demo.util;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextExtractor {

    final static Set<String> TAGS = Stream.of("li", "p", "a").collect(Collectors.toSet());

    public static Set<String> extractText(Elements elements) {
        final List<String> result = new ArrayList<>();

        Iterator<Element> iterator = elements.iterator();

        while (iterator.hasNext()) {
            final Element element = iterator.next();
            final Iterator<Node> childIterator = element.childNodes().iterator();

            while (childIterator.hasNext()) {
                final Node node = childIterator.next();
                if (node instanceof Element) {
                    final Element el = (Element) node;

                    if (TAGS.contains(el.tagName())) {
                        final String line = removeTags(el);
                        if (!line.isEmpty()) {
                            boolean contains = false;
                            for (String s : result) {
                                contains = line.contains(s) || s.contains(line);
                            }

                            if (!contains) {
                                result.add(line);
                            }
                        }
                    }
                }
            }
        }

        return new HashSet<>(result);
    }

    @SuppressWarnings("Duplicates")
    private static String removeTags(Element element) {
        final String result;

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
                }
            }
            result = prepareToSave(stringBuilder.toString());

        } else {
            if (children instanceof Element) {
                if (children.size() == 0 && ((Element) children).hasText()) {
                    result = prepareToSave(((Element) children).html());
                } else {
                    final Element el = (Element) children.get(0);
                    result = prepareToSave(el.html());
                }
            } else {
                if (children.size() == 1) {
                    if (children.get(0) instanceof Element) {
                        final boolean img = ((Element) children.get(0)).tagName().equals("img");
                        if (!img) { //avoid if link is Image
                            result = prepareToSave(children.get(0).outerHtml());
                        } else {
                            result = "";
                        }
                    } else {
                        result = prepareToSave(children.get(0).outerHtml());
                    }
                } else {
                    result = prepareToSave(element.html());
                }
            }
        }

        return result;

    }

    private static String prepareToSave(String line) {
        return line.replaceAll("&nbsp;|&amp;", "").trim();
    }

    @SuppressWarnings("Duplicates")
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
