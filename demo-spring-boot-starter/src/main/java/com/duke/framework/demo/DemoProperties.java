package com.duke.framework.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created duke on 2018/6/20
 */
@ConfigurationProperties(prefix = "duke.demo", ignoreInvalidFields = true)
public class DemoProperties {

    private String prefix = "!!!";

    private String suffix = "!!!";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
