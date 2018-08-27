package com.zyp.crawler.util;

import java.io.File;
import java.io.FileOutputStream;

public class RetrivePage {

    public static boolean downloadPage(String url) throws Exception {
        String result = HttpUtil.getForObject(url);

        FileOutputStream outputStream = new FileOutputStream(new File("temp.txt"));
        outputStream.write(result.getBytes());

        outputStream.close();

        return true;
    }

}
