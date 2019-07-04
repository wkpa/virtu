package ru.xkpa.virtu.common;

import java.io.Serializable;

/**
 * @author : Pavel Kurakin
 */
public class VirtuResponse<T> implements Serializable{

    private VirtuResponseStatus status;
    private T data;

    public VirtuResponse(T data) {
        this.data = data;
        status = VirtuResponseStatus.OK;
    }

    public VirtuResponse(VirtuResponseStatus virtuResponseStatus, T data) {
        this.status = virtuResponseStatus;
        this.data = data;
    }

    public VirtuResponseStatus getStatus() {
        return this.status;
    }

    public void setStatus(VirtuResponseStatus virtuResponseStatus) {
        this.status = virtuResponseStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
