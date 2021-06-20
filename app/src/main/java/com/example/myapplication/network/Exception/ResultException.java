package com.example.myapplication.network.Exception;

public class ResultException extends RuntimeException {
    private int errCode;
    private String msg;

    public ResultException(int errCode, String msg){
        super(msg);
        this.errCode = errCode;
        this.msg = msg;
    }


    public int getErrCode() {
        return errCode;
    }

    public String getMsg(){
        return msg;
    }
}
