package com.example.bbk.demo.util;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextExtractorTest {

    String htmlP = "<html><head><title>First parse</title></head>"
            + "<body><p>Parsed HTML into a doc.</p></body></html>";

    String html2P = "<html><head><title>First parse</title></head>"
            + "<body><p>Parsed HTML into a doc.</p>" +
            "<p>Parsed HTML second line</p>" +
            "</body></html>";

    String htmlA = "<html><head><title>First parse</title></head>"
            + "<body><a href=\"https://www.blogo.it\" target=\"_blank\" rel=\"nofollow noopener\">Blogo</a>" +
            "</body></html>";

    String htmlComplicate = "<html><head><title>First parse</title></head>"
            + "<body><p class=\"author__name\">di <span class=\"author vcard\"><a class=\"textual-link text-link author__name\" href=\"https://www.tvblog.it/post/author/marcosalaris\">Marco Salaris</a></span></p>" +
            "</body></html>";

    String htmlComplicate2 = "<html><head><title>First parse</title></head>"
            + "<body><a href=\"http://forbesreprints.magreprints.com/\" rel=\"noopener noreferrer\" target=\"_blank\">Reprints  Permissions</a>" +
            "</body></html>";

    String htmlComplicate3 = "<html><head><title>First parse</title></head>"
            + "<body><a href=\"https://www.iheartradio.net.nz?ref=cb\" target=\"_blank\" rel=\"noopener\"><img width=\"133\" height=\"30\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIUAAAAeCAMAAAA1pS2OAAABwlBMVEUAAADh4uL///+SlJaTlZf///////+VkZOTlZeSlJa6IiiTlZe6IiiTlZeTlZf///+TlZf///+TlZf///+TlZeTlZeTlZeTlZf///////+TlZeTlZf////19fX///+TlZf///+6Iii6Iij///+6IiiTlZf///////////+6IiiLjY+TlZeTlZeTlZeTlZf///+6Iij///+6IiiTlZe6Iij///+6Iij///////+TlZeTlZf///+TlZe6Iij///////+6Iij///+TlZeTlZf///////+6Iij///+6Iii6Iij///+TlZf9/f3+/v6TlZf///+6IiiTlZe6Iij///////+TlZe6Iii6IiiTlZf////8/P2TlZe6Iij///+6Iij///+6Iij///////+TlZeTlZf///+6IiiTlZeTlZe6IiiTlZeTlZeTlZeTlZe6Iii6Iii3FxyTlZe3Fx2TlZe6Iij///////+TlZeTlZf///+6Iii6Iij///////////////////+TlZe6Iij6+vr29ve6Iii4GyH///+6IiiTlZf///+6IiiTlZe6Iij///+7Iyn///+LjY+6Iij///+6IiiTlZdSBac5AAAAk3RSTlMAAfcI8xQFBBEL+g/y2dadE/z35MxfJiH538Wnmwnx8NbHuqmnm39sEO6koHFnMiEO6tLQwrytkIyLg3MrGhcM9vTl3tvNzKWdhWAuLCj70LOsopaTkX54TEE7FgXu5+bZ0re0o6KSe3ZkVEQ8Ny0mIBoJ6uDGwryyr5SLhXhZV1BHPjczJBSJcmxoaFlUTksSm2B1wO9OAAAEiklEQVRIx+2UV1MTURSAz2bTCykkhMSQkIYJpBASgoTQe+9KE6UJKCD23gV7PWf/r/dmBQXER8sM38PeMjtzvrmnwAknnPA/UX0uW9OVefAc4GpvoKYmcHYb/jzVISpw8cOWX94FtoBRVgZ/kB7SjxiJ9BTtohY96bM1FGgEeGm+90TdNDMzACAkp2eS8HsEi1ar1e2dNBp5NWgZICNfif0JHRzlOel7/BTt0ROjZSRDmdA8fQXwIJZHHg+XmwUoq+hYqIDjmBivS4LouGO3292jl6FAbDRRkKhys9tlfqmLTXKHNfeNG+76a3CYXjKeI+OYn71Ij5Fqxm5SKEOhXTiDuKJuQLzFLJiRB44DEZNQVilNhsNV7T4HMPImKSey1WlzV4XDJl9YA9q7bQCzS0N337ypsrn74RABMj6g+QdEI43VvawmRqg3RIFtaPIsDsBpBdYCCHVYOg7qt8HgyhOAwQZzMOiZhheeYMXT7sfdVrTWegYrTYIoigl32gkAk2l7e56tJcq4ThTzOSkMhlMmMNRLMQuAzqG8e/g1Qvwt5s8R9QB8YBYBupSlzDb0DXc0qJnF7aampBlL69RBLyJeeAR9pchwfexToLUY65BjnaksklOh3ABwmuoTN3Iit3gPDHF5KK9jFgnlqACcnDQFB2Hp6KWW0BjdfL3eRf4eo36siy5dhwnEbm7h7ewcTqHC8xixI1iMqRfTFzpXXIiLDcXIqGNbTA2/lC0M7rQFYM33DJbandzCoQFgq7QmniqCKel72Wz4cnCQRj113adoiBWGkW5mL1I220KfAM6UoplbyCjudaP34eA44iN4WnH6TDGWV3CLxemBUlQ8GVRVKusn6yfTUhzgWnpZhGe+uLBvobMtC8xi1XYFCmjtVXCI16Qfu0TGKEWj1BIlf8hIvdd/WChcLpcXWxc70Zrq8CIGHy0Ue1utskVfkxoU2DrAukFKF7VJd3gbOmwxi3M2XeTct7BIqxpm4dhLRL6t/sjUYgp+Iv39nZ2zRDQfpfu78MPiVjI5uMLeohYVt8fN5vHgBfS+anAVLDqaALjFIOuRdqdzw95ewkKOSkM+pU+SruxbxIY2eV1sSN+DxyUHHNF4VxidVwG2uohxthr2LOQeUdehovsVWhfeTqQe9rWia+BpqmCxoAIA1iO3zXJdPLMtGcAxtJrvz/eXmO4YnHJ1TindGt4jllPKQkoSbXYnHGGOa9Q0AuxkuMR1OGDB5sUE65GkF63nW3G4gaWm2IsFi3Ju0clb50WliY/HmJRjBSCHWJWulPiq4vH4ks10DbTMQthsU4Y3+2NtyikBfqGxLltczexLyD3y09QSZmoRseNjc995xNoUuh6eRxfPyDQ7lw98KfSILlx02Z2TQ+h8cUO7JElK+5qFj9EiZlkStjFGNzXwK+ae6y9yC3/L+hzINKtUzVCmUkUABL7nX3ZSgzqi+twcUUXKPqsiPJ4QYdcanRY4olY0iCBjEQXt7OysxcBjCvIPoqU/YdHBcXzybwNsZdc18DeZ260GqG4U4IQTTvin+AZ4IENTQs6wBAAAAABJRU5ErkJggg==\" alt=\"iHeartRadio\"></a>" +
            "</body></html>";

    String htmlComplicate4 = "<html><head><title>First parse</title></head>"
            + "<body><svg class=\"fs-icon fs-icon--twitter\" xmlns=\"http://www.w3.org/2000/svg\" viewbox=\"0 0 60 60\">\\n <path d=\"M22.2 38.9c-3.4-.3-6.1-1.7-7.5-5.1h3.6c-3.9-1.4-5.6-4.3-5.9-8.4 1.2.6 2.3.7 3.5.7-2.1-1.6-3.6-3.4-3.7-6.1-.1-1.5.5-2.8 1.3-4 4.4 5 9.7 8.3 16.5 8.9v-2.7c.1-1.9.6-3.6 1.9-5.1 2.5-2.8 6.9-3.1 9.8-.7.2.2.5.4.7.6.2.2.4.2.7.2 1.1-.3 2-.9 3-1.4.5-.3 1.1-.6 1.7-1-.4 1.1-.9 2-1.5 2.8-.6.8-1.3 1.6-2.2 2.2 1.6-.2 3.2-.6 4.8-1.1-.3.4-.6.8-.9 1.1l-2.7 2.4c-.1.1-.2.3-.2.4.1 3.3-.6 6.5-1.9 9.6-1.9 4.2-4.7 7.5-8.6 9.9-2.5 1.5-5.2 2.4-8.1 2.9-5.5.8-10.5-.5-15.1-3.3-.1 0-.1-.1-.2-.1h.2c1.1.5 2.2.4 3.3.3 2.3-.2 4.5-.9 6.6-2.1l.9-.6v-.3z\" /></svg>" +
            "</body></html>";



    @Test
    public void basicTest(){
        final Elements allElements = Jsoup.parse(htmlP).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertTrue(strings.contains("Parsed HTML into a doc."));
        assertEquals(1, strings.size());
    }

    @Test
    public void basicTestDoubleP(){
        final Elements allElements = Jsoup.parse(html2P).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertTrue(strings.contains("Parsed HTML into a doc."));
        assertTrue(strings.contains("Parsed HTML second line"));
        assertEquals(2, strings.size());
    }

    @Test
    public void basicTestA(){
        final Elements allElements = Jsoup.parse(htmlA).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertTrue(strings.contains("Blogo"));
        assertEquals(1, strings.size());
    }

    @Test
    public void complicateTest(){
        final Elements allElements = Jsoup.parse(htmlComplicate).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertTrue(strings.contains("di Marco Salaris"));
        assertEquals(1, strings.size());
    }
    @Test
    public void complicateTest2(){
        final Elements allElements = Jsoup.parse(htmlComplicate2).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertTrue(strings.contains("Reprints Permissions"));
        assertEquals(1, strings.size());
    }

    @Test
    public void complicateTest3(){
        final Elements allElements = Jsoup.parse(htmlComplicate3).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertEquals(0, strings.size());
    }

    @Test
    public void complicateTest4(){
        final Elements allElements = Jsoup.parse(htmlComplicate4).getAllElements();
        final Set<String> strings = TextExtractor.extractText(allElements);
        assertEquals(0, strings.size());
    }

}