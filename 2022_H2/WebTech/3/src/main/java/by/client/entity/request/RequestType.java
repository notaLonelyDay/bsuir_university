package main.java.by.client.entity.request;

import java.io.Serializable;

public enum RequestType implements Serializable {
    EDIT,
    CREATE,
    GET,
    GET_ALL,
    REGISTER,
    LOGIN,
}
