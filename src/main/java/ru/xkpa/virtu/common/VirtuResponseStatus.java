package ru.xkpa.virtu.common;

/**
 * @author : Pavel Kurakin
 */
public enum VirtuResponseStatus {

    ERROR(false),
    OK(true);

    private boolean success;

    VirtuResponseStatus(boolean success) {
        this.success = success;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return name();
    }

}
