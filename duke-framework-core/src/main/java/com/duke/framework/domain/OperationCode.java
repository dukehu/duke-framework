package com.duke.framework.domain;

import java.util.Date;

/**
 * Created duke on 2018/9/5
 */
public class OperationCode {

    private String id;

    private String serviceId;

    private String name;

    private String code;

    private String memo;

    private String path;

    private String controller;

    private String requestMethod;

    private String creater;

    private Date createrTime;

    private String modifier;

    private Date modifiedTime;

    public OperationCode() {
    }

    public OperationCode(String name, String code, String memo, String path, String controller, String requestMethod) {
        this.name = name;
        this.code = code;
        this.memo = memo;
        this.path = path;
        this.controller = controller;
        this.requestMethod = requestMethod;
    }

    public OperationCode(String id, String serviceId, String name, String code, String memo, String path, String controller, String requestMethod, String creater, Date createrTime, String modifier, Date modifiedTime) {
        this.id = id;
        this.serviceId = serviceId;
        this.name = name;
        this.code = code;
        this.memo = memo;
        this.path = path;
        this.controller = controller;
        this.requestMethod = requestMethod;
        this.creater = creater;
        this.createrTime = createrTime;
        this.modifier = modifier;
        this.modifiedTime = modifiedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public boolean equals(OperationCode operationCode) {
        String str = "[" + operationCode.controller + "." + operationCode.code + "],";
        if (!this.name.equals(operationCode.name)) {
            System.out.println("##" + str + this.name + "-->" + operationCode.name);
            return false;
        }
        if (!this.memo.equals(operationCode.memo)) {
            System.out.println("##" + str + this.memo + "-->" + operationCode.memo);
            return false;
        }
        if (!this.path.equals(operationCode.path)) {
            System.out.println("##" + str + this.path + "-->" + operationCode.path);
            return false;
        }
        if (!this.controller.equals(operationCode.controller)) {
            System.out.println("##" + str + this.controller + "-->" + operationCode.controller);
            return false;
        }
        if (!this.requestMethod.equals(operationCode.requestMethod)) {
            System.out.println("##" + str + this.requestMethod + "-->" + operationCode.requestMethod);
            return false;
        }
        return true;
    }
}
