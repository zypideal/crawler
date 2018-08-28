package com.zyp.crawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;

public class RetrivePage {

    public static boolean downloadPage(String url) throws Exception {
        String result = HttpUtil.get(url);

        FileOutputStream outputStream = new FileOutputStream(new File("page.txt"));
        outputStream.write(result.getBytes());

        outputStream.close();

        return true;
    }


    public static boolean parsePage(String url) throws Exception {
        String result = HttpUtil.get(url);
        Document document = Jsoup.parse(result);

        FileOutputStream outputStream = new FileOutputStream(new File("content.txt"));

        Elements article = document.body().select(".news-item");
        for (Element element : article) {
            // title
            Elements title = element.select(".news__item-title");
            outputStream.write(title.get(0).text().getBytes());
            outputStream.write("\n".getBytes());

            // introduction
            Elements introduction = element.select(".article-excerpt");
            outputStream.write(introduction.get(0).text().getBytes());
            outputStream.write("\n".getBytes());

            // like
            Elements like = element.select(".votes-num");
            outputStream.write(like.get(0).text().getBytes());
            outputStream.write("\n".getBytes());

            // author & time
            Elements author = element.select(".author");
            outputStream.write(author.get(0).text().getBytes());
            outputStream.write("\n".getBytes());

            outputStream.write("\n".getBytes());

        }

        return true;
    }

}
