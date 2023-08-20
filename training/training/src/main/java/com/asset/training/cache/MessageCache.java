package com.asset.training.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties
@Component
@PropertySource("classpath:messages.properties")
public class MessageCache {
    private final Map<String, Map<Integer, String>> message = new HashMap<>();

    public Map<String, Map<Integer, String>> getExceptionMessages() {
        return message;
    }

    public String getSuccessMsg(Integer code) {
        if (message != null && message.get("success") != null && message.get("success").get(code) != null)
            return message.get("success").get(code);
        return "";
    }

    public String getErrorMsg(Integer code) {
        if (message != null && message.get("error") != null && message.get("error").get(code * -1) != null)
            return message.get("error").get(code * -1);
        return "";
    }

    public String getWarningMsg(Integer code) {
        if (message != null && message.get("warn") != null && message.get("warn").get(code) != null)
            return message.get("warn").get(code);
        return "";
    }

    public String replaceArgument(String msg, String arg) {
        if (msg != null && !msg.isBlank()) {
            if (arg.contains(",")) {
                msg = msg.replace("$1", arg.split(",")[0]);
                return msg.replace("$2", arg.split(",")[1]);
            }
            else
                return msg.replace("$1", arg);
        }
        return "";
    }

    public String replaceArgument(String msg, String[] args) {
        if (msg != null && !msg.isBlank()) {
            if (args.length > 0)
                for (int i = 1; i <= args.length; i++)
                    msg = msg.replace(("$" + i), args[i - 1]);
            return msg;
        }
        return "";
    }
}
