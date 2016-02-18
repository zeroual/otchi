package com.otchi.domaine.Helper;

/**
 * Created by MJR2 on 2/18/2016.
 */
public class SequenceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String errCode;
    private String errMsg;

    // get, set ...

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public SequenceException(String errMsg){
        this.errMsg = errMsg;
    }
}
