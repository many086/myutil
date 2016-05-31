
package com.liang.j2ee.util.exception;



public enum ServiceExceptionCode implements IErrorMessage{

    INVALID_PARAM("ISV.INVALID_PARAM", true),

    INVALID_FILETYPE("ISV.INVALID_FILETYPE", true),

    USER_IN_BLACK_LIST("ISV.USER_IN_BLACK_LIST", true),

    DUPLICATE_INSERT("ISV.DUPLICATE_INSERT", true),

    DATA_NOT_EXIST("ISV.DATA_NOT_EXIST", true),

    SYSTEM_ERROR("ISP.SYSTEM_ERROR", false),

    DATA_PRIVILEGE_ILLEGAL("ISV.DATA_PRIVILEGE_ILLEGAL", true),

    OPERATION_PRIVILEGE_ILLEGAL("ISV.OPERATION_PRIVILEGE_ILLEGAL", true),

    BEYOND_CAPACITY("ISV.BEYOND_CAPACITY", true),

    BEYOND_AMOUNT("ISV.BEYOND_AMOUNT", true),

    BEYOND_TOTAL_PRICE("ISV.BEYOND_TOTAL_PRICE", true),

    CONFLICT_MEMBER("ISV.CONFLICT_MEMBER", true),

    UPLOAD_ATTACHMENTS_FAILED("ISV.UPLOAD_ATTACHMENTS_FAILED", true),

    EXTERNAL_ERP_SYSTEM_ERROR("EXTERNAL.EXTERNAL_ERP_SYSTEM_ERROR", false),

    EXTERNAL_ERP_INVALID_PARAM("EXTERNAL.EXTERNAL_ERP_INVALID_PARAM", false);

    private static final TextMessenger textMessenger = TextMessenger.getInstance("ServiceCodeMsg");

    /**  */
    private String                     value;

    /**  */
    private boolean                    bizException;
    /**
     * @param value
     * @param isBizException
     */
    ServiceExceptionCode(String value, boolean isBizException){
        this.value = value;
        this.bizException = isBizException;
    }

    public boolean isBizException() {
        return bizException;
    }

    public String getKey() {
        return this.name();
    }

    public String getMessage(Object... params) {
        return textMessenger.getMessage(this, params);
    }
}
