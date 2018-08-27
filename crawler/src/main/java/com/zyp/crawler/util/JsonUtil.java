package com.zyp.crawler.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T loads(String str, Class<T> type) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        T object = null;
        try {
            object = objectMapper.readValue(str, type);
        } catch (IOException e) {
            LOGGER.error("json parse error: ", e);
        }

        return object;
    }


    public static <T> T loads(String str, TypeReference type) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        T object = null;
        try {
            object = objectMapper.readValue(str, type);
        } catch (IOException e) {
            LOGGER.error("json parse error: ", e);
        }

        return object;
    }


    public static JsonNode loads(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(str);
        } catch (IOException e) {
            LOGGER.error("json parse error: ", e);
        }

        return jsonNode;
    }


    public static String dumps(Object object) {
        if (object == null) {
            return null;
        }

        String str = null;
        try {
            str = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("json parse error: ", e);
        }

        return str;
    }


    public static String parseString(String body, String key) {
        if (body == null || body.isEmpty()) {
            return null;
        }

        String str = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(body).get(key);
            if (jsonNode == null) {
                return null;
            }
            if (jsonNode.isContainerNode()) {
                str = jsonNode.toString();
            } else {
                str = jsonNode.asText();
            }
        } catch (IOException e) {
            LOGGER.error("json parse error: ", e);
        }

        return str;
    }


    public static List<String> parseArray(String body, String key) {
        if (body == null || body.isEmpty()) {
            return null;
        }

        List<String> result = new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(body).get(key);
            if (jsonNode == null) {
                return null;
            }
            if (!jsonNode.isArray()) {
                if (jsonNode.isContainerNode()) {
                    result.add(jsonNode.toString());
                } else {
                    result.add(jsonNode.asText());
                }
            } else {
                Iterator<JsonNode> iterator = jsonNode.elements();
                while (iterator.hasNext()) {
                    JsonNode subNode = iterator.next();
                    if (subNode.isContainerNode()) {
                        result.add(subNode.toString());
                    } else {
                        result.add(subNode.asText());
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("json parse error: ", e);
            return null;
        }

        return result;
    }

}
