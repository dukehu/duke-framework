package com.duke.framework.sqllog;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created duke on 2018/6/28
 */
@ConfigurationProperties(prefix = "duke.sqllog", ignoreInvalidFields = true)
public class SqllogProperties {
    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
