package com.liang.j2ee.util.exception;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;


/**
 * 功能概述：<br>
 * 统一国际化资源信息提供器
 * 
 * @author liangxing
 */
public final class TextMessenger {

    /** 异常调用默认全局文本处理 */
    public static final TextMessenger               globalTextMessenger = new TextMessenger("serviceCodeMsg");

    /** 上下文 */
    private static final Map<String, TextMessenger> context             = new HashMap<String, TextMessenger>();

    /**  */
    private String                                  baseName;

    /**   */
    private ResourceBundle                          resourceBundle;

    /**
     * 资源文件ClassPath去掉后缀后的字符串，例如globalMessages.properties则传入globalMessages
     * 
     * @param baseName
     * @return
     */
    public static final TextMessenger getInstance(String baseName) {
        TextMessenger tm = context.get(baseName);
        if (tm != null) {
            return tm;
        }

        tm = new TextMessenger(baseName);
        if (tm.resourceBundle != null) {
            context.put(tm.baseName, tm);
            return tm;
        }

        System.out.println(TextMessenger.class.getCanonicalName() + " WARN: ,BaseName[" + baseName + "] is not exists，return globalTextMessenger!");

        return globalTextMessenger;
    }

    /**
     * @param baseName
     */
    private TextMessenger(String baseName){
        this.baseName = baseName;
        if (StringUtils.isBlank(baseName)) {
            return;
        }

        try {
            this.resourceBundle = ResourceBundle.getBundle(baseName, Locale.CHINESE);
        } catch (MissingResourceException mre) {
            this.resourceBundle = null;
            System.out.println(TextMessenger.class.getCanonicalName() + " WARN: init fail, BaseName[" + baseName + "] is not exists!");
        } catch (Exception e) {
            this.resourceBundle = null;
            e.printStackTrace();
        }
    }

    /**
     * 取得无参数据消息
     * 
     * @param key
     * @param params
     * @return
     */
    private String getTextFromBundle(String key, Object[] params) {
        try {
            if (this.resourceBundle != null && this.resourceBundle.containsKey(key)) {
                return this.resourceBundle.getString(key);
            }

            if (globalTextMessenger.resourceBundle == null) {
                return TextMessenger.getDefaultText(key, params);
            }

            if (globalTextMessenger.resourceBundle.containsKey(key)) {
                return globalTextMessenger.getTextFromBundle(key, params);
            }
        } catch (Exception e) {
            // do nothing
        }

        return TextMessenger.getDefaultText(key, params);
    }

    /**
     * 取得带参数据的消息
     * 
     * @param key
     * @param params
     * @return
     */
    public String getText(String key, Object... params) {
        if (params == null || params.length <= 0) {
            return this.getTextFromBundle(key, params);
        }

        return formatMessage(this.getTextFromBundle(key, params), params, Locale.CHINESE);
    }

    /**
     * 通过IMessageInfo取得信息
     * 
     * @param messageInfo
     * @param params
     * @return
     */
    public String getMessage(IErrorMessage messageInfo, Object... params) {
        if (params == null || params.length <= 0) {
            return this.getTextFromBundle(messageInfo.getKey(), params);
        }

        return TextMessenger.formatMessage(this.getTextFromBundle(messageInfo.getKey(), params), params, Locale.CHINESE);
    }

    /**
     * @param key
     * @param params
     * @return
     */
    private static String getDefaultText(String key, Object[] params) {
        if (params != null && params.length == 1) {
            return "{0}";
        } else {
            return key;
        }
    }

    /**
     * 使用参数格式化消息模板
     * 
     * @param msgTpl
     * @param params
     * @param locale
     * @return
     */
    public static final String formatMessage(String msgTpl, Object[] params, Locale locale) {
        if (msgTpl == null || StringUtils.isBlank(msgTpl)) {
            return "";
        }

        MessageFormat mf = new MessageFormat(msgTpl, locale);
        String message = mf.format(params);
        return message;
    }
}

