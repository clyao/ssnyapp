package com.ssnyapp.wxapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssnyapp.common.utils.Query;
import com.ssnyapp.wxapp.domain.CompanyDO;
import com.ssnyapp.wxapp.domain.JobFairDO;
import com.ssnyapp.wxapp.domain.JobSeekerDO;
import com.ssnyapp.wxapp.service.CompanyService;
import com.ssnyapp.wxapp.service.JobFairService;
import com.ssnyapp.wxapp.service.JobSeekerService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by clyao on 2017/12/06.
 */
@RequestMapping("/wxapp")
@Controller
public class WXAppApiController {

    private String accessToken = null;

    private int expiresIn = 0;

    //ssnyapp小程序用户登录接口
    @ResponseBody
    @GetMapping(value = {"/index/wxLogin"}, produces = "application/json;charset=UTF-8")
    public String wxLogin(@RequestParam Map<String, Object> params){
        /*HttpClientBuilder httpClientBuilder = null;
        CloseableHttpClient closeableHttpClient = null;
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        String result = null;
        try {
            String jsCode = params.get("code").toString();
            String wxUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=wx53216e3ac4ef6791&secret=009acbb612e72b61aa11d205e9ecf175&js_code="+jsCode+"&grant_type=authorization_code";
            httpClientBuilder = HttpClientBuilder.create();
            closeableHttpClient = httpClientBuilder.build();
            httpGet = new HttpGet(wxUrl);
            httpResponse = closeableHttpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }*/
        return null;
    }

    @ResponseBody
    @GetMapping(value = {"/index/sendTemplate"}, produces = "application/json;charset=UTF-8")
    private String sendTemplate(@RequestParam Map<String, Object> params){

        String openId = params.get("openId").toString();
        String formId = params.get("formId").toString();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        stringBuffer.append("\"touser\":");
        stringBuffer.append("\""+openId+"\"");
        stringBuffer.append(",");
        stringBuffer.append("\"template_id\":");
        stringBuffer.append("\"otuu-Hcew5fjIQhpJKopM2qwcdou1D8QCBmmEN-yfm4\"");
        stringBuffer.append(",");
        stringBuffer.append("\"page\":");
        stringBuffer.append("\"/pages/index/index\"");
        stringBuffer.append(",");
        stringBuffer.append("\"form_id\":");
        stringBuffer.append("\""+formId+"\"");
        stringBuffer.append(",");
        stringBuffer.append("\"data\": {");
        stringBuffer.append("\"keyword1\":{");
        stringBuffer.append("\"value\":");
        stringBuffer.append("\"此功能正在完善中\"");
        stringBuffer.append("},");
        stringBuffer.append("\"keyword2\":{");
        stringBuffer.append("\"value\":");
        stringBuffer.append("\"2017年12月6日\"");
        stringBuffer.append("},");
        stringBuffer.append("\"keyword3\":{");
        stringBuffer.append("\"value\":");
        stringBuffer.append("\"clyao\"");
        stringBuffer.append("}");
        stringBuffer.append("},");
        stringBuffer.append("\"emphasis_keyword\": \"keyword1.DATA\"");
        stringBuffer.append("}");

        /*String accessToken = getAccessToken();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+accessToken);
        StringEntity entity = new StringEntity(stringBuffer.toString(),"utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        CloseableHttpResponse closeableHttpResponse = null;
        String result = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            result = EntityUtils.toString(closeableHttpResponse.getEntity(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }*/
        return "支付功能还在申请中";
    }

    private String getAccessToken(){
        HttpClientBuilder httpClientBuilder = null;
        CloseableHttpClient closeableHttpClient = null;
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        String result = null;
        if((int)System.currentTimeMillis()>expiresIn){
            try {
                String wxUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx53216e3ac4ef6791&secret=009acbb612e72b61aa11d205e9ecf175";
                httpClientBuilder = HttpClientBuilder.create();
                closeableHttpClient = httpClientBuilder.build();
                httpGet = new HttpGet(wxUrl);
                httpResponse = closeableHttpClient.execute(httpGet);
                entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity);
                if(result.indexOf("errcode")==-1){
                    JSONObject jsonObject = JSON.parseObject(result);
                    accessToken = jsonObject.get("access_token").toString();
                    //为了和微信服务器同步，将凭证过期时间提前一分钟
                    expiresIn = (int)System.currentTimeMillis() + 7194000;
                    System.out.println(accessToken);
                    System.out.println(expiresIn);
                    System.out.println((int)System.currentTimeMillis());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return accessToken;
            }
        }else{
            return accessToken;
        }
    }

}
