package com.liang.j2ee.util.exception;

/**
 * 功能概述：<br>
 * 异常创建工厂
 * 
 * @author liangxing
 */
public abstract class ExceptionFactory {

    /**
     * 创建业务异常
     * 
     * @param messageInfo
     * @param params
     * @return
     */
    public static final ServiceException makeFault(IErrorMessage messageInfo, Object... params) {
        String message = messageInfo.getMessage(params);
        ServiceException exp = new ServiceException(messageInfo.getKey(), message, messageInfo.isBizException());
        return exp;
    }

    /**
     * 创建具有异常对象的ServiceException
     * 
     * @param messageInfo
     * @param tr
     * @param params
     * @return
     */
    public static final ServiceException makeFault(IErrorMessage messageInfo, Throwable tr, Object... params) {
        String message = messageInfo.getMessage(params);
        ServiceException exp = new ServiceException(messageInfo.getKey(), message, messageInfo.isBizException(), tr);
        return exp;
    }

    /**
     * 基于已经存在的异常构建新的异常类
     * 
     * @param se
     * @return
     */
    public static final ServiceException makeFault(ServiceException se) {
        ServiceException exp = new ServiceException(se.getCodeValue(), se.getMsg(), se.isBizException());
        return exp;
    }

    

}
