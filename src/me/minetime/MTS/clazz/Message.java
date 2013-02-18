package me.minetime.MTS.clazz;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import me.minetime.MTS.api.IMessage;

public class Message implements IMessage {

    private static Message m;
    private final transient ResourceBundle bundle;
    private final transient Map<String, MessageFormat> messageFormatCache = new HashMap<String, MessageFormat>();

    /* Konstruktor */
    public Message() {
	m = this;
	bundle = ResourceBundle.getBundle("messages", Locale.GERMAN);
    }

    /* Hauptfunktion */
    public static String msg(final String string, final Object... objects) {
	if (objects.length == 0)
	    return m.bundle.getString(string);
	else
	    return m.format(string, objects);

    }

    public String format(final String string, final Object... objects) {
	final String format = bundle.getString(string);
	MessageFormat messageFormat = messageFormatCache.get(format);
	if (messageFormat == null) {
	    messageFormat = new MessageFormat(format);
	    messageFormatCache.put(format, messageFormat);
	}
	return messageFormat.format(objects);
    }

}
