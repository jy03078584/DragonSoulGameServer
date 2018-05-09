/**
 *
 *
 * 文件名称： ChatAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 上午10:32:28
 */
package com.lk.dragon.server.module.analysis;

import net.sf.json.JSONObject;

import com.lk.dragon.server.domain.ChatDomain;
import com.lk.dragon.util.JSONUtil;

public class ChatRequestAnalysis
{

    //聊天内容关键字
    private static final String ROLE_ID_KEY = "role_id";
    private static final String ROLE_NAME_KEY = "role_name";
    private static final String GUILD_ID_KEY = "guild_id";
    private static final String CHAT_CONTENT_KEY = "content";
    private static final String CHAT_TYPE_KEY = "chatType";
    private static final String CHAT_ROLE_ID_KEY = "chatId";
    private static final String CHAT_GUILD_ID_KEY = "chatGuildId";

    
    /**
     * 获取聊天内容
     * @param json
     * @return
     */
    public static ChatDomain getChatContent(String json)
    {
        //获取data信息
        JSONObject chatData = JSONUtil.getData(json);
        //创建聊天实体
        ChatDomain chatDomain = new ChatDomain();
        chatDomain.setType(chatData.getInt(CHAT_TYPE_KEY));
        chatDomain.setContent(chatData.has(CHAT_CONTENT_KEY) ? chatData.getString(CHAT_CONTENT_KEY) : null);
        chatDomain.setRoleId(chatData.has(ROLE_ID_KEY) ? chatData.getLong(ROLE_ID_KEY) : 0);
        chatDomain.setChatRoleId(chatData.has(CHAT_ROLE_ID_KEY) ? chatData.getLong(CHAT_ROLE_ID_KEY) : 0);
        chatDomain.setChatGuildId(chatData.has(CHAT_GUILD_ID_KEY) ? chatData.getLong(CHAT_GUILD_ID_KEY) : 0);
        chatDomain.setGuildId(chatData.has(GUILD_ID_KEY) ? chatData.getLong(GUILD_ID_KEY) : 0);
        chatDomain.setRoleName(chatData.has(ROLE_NAME_KEY) ? chatData.getString(ROLE_NAME_KEY) : null);
                
        return chatDomain;
    }
    
    /**
     * 获取聊天信息内容响应字符串
     * @param chatDomain
     * @param chatType
     * @return
     */
    public static String getChatResponse(ChatDomain chatDomain, int chatType)
    {
        //聊天实体
        JSONObject chatObj = new JSONObject();
        chatObj.put(ROLE_NAME_KEY, chatDomain.getRoleName());
        chatObj.put(CHAT_CONTENT_KEY, chatDomain.getContent());
        chatObj.put(CHAT_TYPE_KEY, chatType);
        
        return chatObj.toString();
    }
}
