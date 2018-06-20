package com.duke.framework.demo.config;

/**
 * Created duke on 2018/6/20
 */
public class DemoService {

    private String prefix;

    private String suffix;

    public DemoService() {
    }

    public DemoService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String wrap(String word) {
        return prefix + word + suffix;
    }
}
