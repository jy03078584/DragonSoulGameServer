/**
 *
 *
 * 文件名称： SMSUtil.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-11 下午2:08:23
 */
package com.lk.dragon.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.cloopen.rest.sdk.CCPRestSDK;

public class SMSUtil
{
    public static void sendMessage(String bugContent, String bugModule)
    {
        // 短信内容
        SimpleDateFormat formatDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        String messContent = "异常信息\n异常模块：" + bugModule + "\n异常内容：" + bugContent;
        messContent += "\n异常发生时间：" + formatDate.format(new Date());

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf8");// 在头文件中设置转码
        NameValuePair[] data = { new NameValuePair("Uid", "hexu"), // 短信接口用户名
                new NameValuePair("Key", "1c0bdb093bf32fe200b0"), // 短信接口安全密码
                new NameValuePair("smsMob", "15196634814"), // 目的手机号码（多个手机号请用半角逗号隔开）
                new NameValuePair("smsText", messContent) }; // 短信内容，最多支持300个字，普通短信70个字/条，长短信64个字/条计费
        post.setRequestBody(data);

        try
        {
            client.executeMethod(post);
        } catch (HttpException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers)
        {
            System.out.println(h.toString());
        }
        String result = null;
        try
        {
            result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(result);

        post.releaseConnection();
    }

    public static void sendMessageDemo()
    {

        SimpleDateFormat formatDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        
        HashMap<String, Object> result = null;

        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount("aaf98f8948f34b1b0148fd64bed30775", "fe7017bca0184363a964fc08a1517ee7");// 初始化主帐号名称和主帐号令牌
        restAPI.setAppId("8a48b55148bbb4140148fe139a5e1c09");// 初始化应用ID
        result = restAPI.sendTemplateSMS("15196634814", "1", new String[] {
                "好友模块", formatDate.format(new Date()) });

        System.out.println("SDKTestGetSubAccounts result=" + result);
        if ("000000".equals(result.get("statusCode")))
        {
            // 正常返回输出data包体信息（map）
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) result
                    .get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet)
            {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else
        {
            // 异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= "
                    + result.get("statusMsg"));
        }
    }

    public static void main(String[] args)
    {
//        sendMessage("添加好友失败", "好友模块");
        sendMessageDemo();
    }
}
