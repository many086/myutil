
package com.liang.j2ee.util.exception;

/**
 * @author liangxing 2016年5月31日 下午3:16:29
 */
public interface IMessage {
    /**
     * 取得信息定义Key
     * 
     * @return
     */
    String getKey();

    /**
     * 取得当前信息对应的国际化文本
     * 
     * @return
     */
    String getMessage(Object... params);
}
