package main.java.by.client.entity.request;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    ERROR,
    UPDATED,
    NOTFOUND,
}
