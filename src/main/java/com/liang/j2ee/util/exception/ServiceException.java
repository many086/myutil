package com.liang.j2ee.util.exception;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4239826522640838526L;
    
    /** Key */
    protected String          code;

    /** Error Message */
    protected String          msg;

    protected boolean         bizException     = Boolean.TRUE;

    /**
     * @param code
     * @param msg
     */
    public ServiceException(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code
     * @param msg
     * @param bizException
     */
    public ServiceException(String code, String msg, boolean bizException){
        this(code, msg);
        this.bizException = bizException;
    }

    /**
     * @param code
     * @param msg
     * @param tr
     */
    public ServiceException(String code, String msg, Throwable tr){
        super(tr);
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code
     * @param msg
     * @param bizException
     * @param tr
     */
    public ServiceException(String code, String msg, boolean bizException, Throwable tr){
        this(code, msg, tr);
        this.bizException = bizException;
    }


    public String getCodeValue() {
        return this.code;
    }


    public String getMsg() {
        return msg;
    }

    /**
     * @return the bizException
     */
    public boolean isBizException() {
        return bizException;
    }

    /**
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("API_EXCEPTION:");
        strBuilder.append(this.code);
        strBuilder.append("|");
        if (this.isBizException()) {
            strBuilder.append("[*IGNORE*]");
        }
        strBuilder.append(msg);
        if (StringUtils.isNotBlank(super.getMessage())) {
            strBuilder.append("||superMesaage:");
            strBuilder.append(super.getMessage());
        }
        return strBuilder.toString();
    }

    /**
     * 
     * @param code
     * @return
     */
    public boolean isMatchCode(String code) {
        if (code == null) {
            return false;
        }

        return this.code.equals(code);
    }


    /*
     * @see java.lang.Throwable#fillInStackTrace()
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
