package com.liang.j2ee.util.exception;

/**
 * @author liangxing 2016年5月31日 下午3:17:11
 */
public interface IErrorMessage extends IMessage{
    
    /**
     * 是否是业务异常
     * 
     * @return
     */
    public boolean isBizException();
}
