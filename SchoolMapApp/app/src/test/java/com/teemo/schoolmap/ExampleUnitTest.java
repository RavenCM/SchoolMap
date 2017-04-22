package com.teemo.schoolmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.uitl.JsonUtil;

import org.junit.Test;

import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String response = "{\"objectVersionNumber\":0,\"createdBy\":0,\"creationDate\":null,\"lastUpdatedBy\":0,\"lastUpdatedDate\":null,\"isEnable\":0,\"userId\":1,\"userTypeId\":1,\"password\":\"21232f297a57a5a743894a0e4a801fc3\",\"userBasisInformation\":{\"objectVersionNumber\":0,\"createdBy\":0,\"creationDate\":null,\"lastUpdatedBy\":0,\"lastUpdatedDate\":null,\"isEnable\":0,\"userBasisInformationId\":1,\"userId\":1,\"email\":\"admin@admin.com\",\"username\":\"admin\",\"sex\":\"SECRET\"},\"userExtraInformation\":null}";
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(response, User.class);
        List<User> userList = JsonUtil.buildObject(response, User.class);
    }
}