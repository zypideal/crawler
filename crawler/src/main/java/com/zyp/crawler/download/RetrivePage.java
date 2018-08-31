package com.zyp.crawler.download;

import com.zyp.crawler.util.HttpUtil;
import com.zyp.crawler.util.JsonUtil;
import com.zyp.crawler.util.TimeUtil;
import okhttp3.HttpUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class RetrivePage {

    public static boolean downloadPage(String url) throws Exception {
        String result = HttpUtil.get(url, null);

        FileOutputStream outputStream = new FileOutputStream(new File("page.txt"));
        outputStream.write(result.getBytes());

        outputStream.close();

        return true;
    }


    public static boolean parsePage(String url) throws Exception {
        String result = HttpUtil.get(url, null);
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



    public static void crawl(HttpUrl.Builder builder, Map<String, String> headers) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(new File("segmentfault.txt"));
        String offset = String.valueOf(TimeUtil.getCurrentTimeInMillis());

        while (true){
            HttpUrl httpUrl = builder.addQueryParameter("offset", offset).build();
            String response = HttpUtil.get(httpUrl.toString(), headers);
            List<String> data = JsonUtil.parseArray(response, "data");

            if(data.isEmpty()){
                break;
            }

            for (String item : data) {
                String title = JsonUtil.parseString(item, "title");
                String createDate = TimeUtil.format(Long.parseLong(JsonUtil.parseString(item, "offset")));
                String votes = JsonUtil.parseString(item, "votes");
                String link = "https://www.segmentfault.com" + JsonUtil.parseString(item, "url");
                String user = JsonUtil.parseString(item, "user");
                String author = JsonUtil.parseString(user, "name");
                String homePage = "https://www.segmentfault.com" + JsonUtil.parseString(user, "url");
                String excerpt = JsonUtil.parseString(item, "excerpt");

                outputStream.write(("title : " + title).getBytes());
                outputStream.write("\n".getBytes());

                outputStream.write(("date : " + createDate).getBytes());
                outputStream.write("\n".getBytes());

                outputStream.write(("votes : " + votes).getBytes());
                outputStream.write("\n".getBytes());

                outputStream.write(("link : " + link).getBytes());
                outputStream.write("\n".getBytes());

                outputStream.write(("author : " + author).getBytes());
                outputStream.write("\n".getBytes());

                outputStream.write(("homepage : " + homePage).getBytes());
                outputStream.write("\n".getBytes());


                outputStream.write(("excerpt : " + excerpt).getBytes());
                outputStream.write("\n".getBytes());

                outputStream.write("\n".getBytes());
                outputStream.write("\n".getBytes());
                outputStream.write("\n".getBytes());
            }

            List<String> message = JsonUtil.parseArray(response, "message");
            offset = message.get(0);
            builder.removeAllQueryParameters("offset");
        }

        outputStream.close();
    }

}
