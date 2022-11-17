package main.java.by.client.entity;

import main.java.by.client.entity.request.RequestType;

import java.io.Serializable;

public class StudentRequest implements Serializable {
    private RequestType requestType;
    private Object body;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Object getBody() {
        return this.body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
