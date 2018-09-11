package com.duke.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created duke on 2018/9/5
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public OperationCode(String name, String code, String memo, String path, String controller, String requestMethod) {
        this.name = name;
        this.code = code;
        this.memo = memo;
        this.path = path;
        this.controller = controller;
        this.requestMethod = requestMethod;
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
