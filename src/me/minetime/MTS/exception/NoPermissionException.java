package me.minetime.MTS.exception;

public class NoPermissionException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoPermissionException() {
	super("Du hast keine Rechte daf\u00dcr!");
    }
}
