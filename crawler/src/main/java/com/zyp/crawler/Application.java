package com.zyp.crawler;

import com.zyp.crawler.download.RetrivePage;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("https")
                .host("segmentfault.com")
                .addPathSegment("/api/timelines/channel/1490000006201495")
                .addQueryParameter("_", "7f649391814bef13557236b2390a0f1d");

        Map<String, String> headers = new HashMap<>();
        headers.put("referer", "https://segmentfault.com/channel/backend");
        headers.put("cookie", "_ga=GA1.2.1222629315.1534825940; sf_remember=589440ba6ca238791fe2dfddcf7f408d; PHPSESSID=web2~amhhr4lf8h8au7sg2vfsuifg6c; Hm_lvt_e23800c454aa573c0ccb16b52665ac26=1534826325,1535190098,1535190435,1535372604; _gid=GA1.2.880249680.1535556614; Hm_lpvt_e23800c454aa573c0ccb16b52665ac26=1535628315; io=jW3TTDLC7AuZAKtBKmKC");

        try {
            RetrivePage.crawl(builder, headers);
        } catch (Exception e) {
            LOGGER.error("error: {}", e);
        }

    }

}
