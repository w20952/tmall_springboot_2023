package com.david.tmall_springboot_2023.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestParameterHelper {

    public static JSONObject getRequestJson(HttpServletRequest request) throws IOException {

        StringBuffer sb = new StringBuffer("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();

        JSONObject jsonObject = JSONObject.parseObject(sb.toString());

        return jsonObject;
    }
}
