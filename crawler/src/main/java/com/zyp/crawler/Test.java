package com.zyp.crawler;

import com.zyp.crawler.util.RetrivePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args){

        try {
            RetrivePage.downloadPage("http://www.baidu.com");
        }catch (Exception e){
            LOGGER.error("error: {}", e);
        }
    }

}