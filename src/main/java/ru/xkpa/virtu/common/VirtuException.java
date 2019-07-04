package ru.xkpa.virtu.common;

/**
 * @author Pavel Kurakin
 */
public class VirtuException extends RuntimeException {

    private final VirtuResponseStatus virtuResponseStatus;

    public VirtuException(final VirtuResponseStatus virtuResponseStatus, final String message) {
        super(message);
        this.virtuResponseStatus = virtuResponseStatus;
    }

    public VirtuResponseStatus getVirtuResponseStatus() {
        return virtuResponseStatus;
    }
}
