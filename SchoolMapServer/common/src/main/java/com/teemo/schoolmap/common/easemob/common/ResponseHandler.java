package com.teemo.schoolmap.common.easemob.common;

import com.google.gson.Gson;
import com.teemo.schoolmap.common.easemob.client.TokenRestClient;
import io.swagger.client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created by easemob on 2017/3/16.
 * Source: 环信demo
 */
public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public Object handle(Supplier<EasemobAPI> easemobAPI) throws ApiException {
        Object result = null;
        try {
            result = easemobAPI.get().invokeEasemobAPI();
        } catch (ApiException e) {
            if (e.getCode() == 401) {
                logger.info("The current token is invalid, re-generating token for you and calling it again");
                TokenRestClient.getToken();
                try {
                    result = easemobAPI.get().invokeEasemobAPI();
                } catch (ApiException e1) {
                    logger.error(e1.getMessage());
                }
                return result;
            }
            if (e.getCode() == 429) {
                logger.warn("The api call is too frequent");
            }
            if (e.getCode() >= 500) {
                logger.info("The server connection failed and is being reconnected");
                result = retry(easemobAPI.get());
                if (result != null) {
                    return result;
                }
                logger.error("The server may be faulty. Please try again later");
            }
            Gson gson = new Gson();
            Map map = gson.fromJson(e.getResponseBody(), Map.class);
            logger.error("error_code:{} error_msg:{} error_desc:{}", e.getCode(), e.getMessage(), map.get("error_description"));
        }
        return result;
    }

    private Object retry(EasemobAPI easemobAPI) {
        long time = 5;
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.SECONDS.sleep(time);
                logger.info("Reconnection is in progress..." + i);
                Object result = easemobAPI.invokeEasemobAPI();
                if (result != null) {
                    return result;
                }
            } catch (ApiException e1) {
                time *= 3;
            } catch (InterruptedException e1) {
                logger.error(e1.getMessage());
            }
        }
        return null;
    }
}
