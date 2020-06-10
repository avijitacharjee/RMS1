package com.avijit.rms1.data.remote.responses;

public class NetworkResponse<T> extends BaseModel{
    private T data;
    private String message;

    public NetworkResponse() {
    }

    public NetworkResponse(Boolean d){
        setNetworkIsSuccessful(d);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
