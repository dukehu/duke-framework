package com.duke.framework.utils;

import com.duke.framework.domain.DBProperties;
import com.duke.framework.domain.OperationCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created duke on 2018/9/5
 */
@SuppressWarnings("ALL")
public class OperationCodeUtils {

    private static String QUERY_OPERATION_CODE_BY_SERVICE_ID = "select " +
            "id, service_id, name, code, memo, path, controller, request_method, creater, creater_time, modifier, modified_time " +
            "from auth_operation_code " +
            "where service_id = '%s'";

    private static String INSERT_OPERATION_CODE_SQL = "insert into auth_operation_code(" +
            "id, service_id, name, code, memo, path, controller, request_method, url, creater, creater_time, modifier, modified_time" +
            ")" +
            " values " +
            "(" +
            "'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'" +
            ");";

    private static String UPDATE_OPERATION_CODE_SQL = "update auth_operation_code " +
            "set " +
            "service_id = '%s'," +
            "name = '%s'," +
            "memo = '%s'," +
            "path = '%s'," +
            "controller = '%s'," +
            "request_method = '%s'," +
            "modifier = '%s'," +
            "modified_time = '%s' " +
            "where code = '%s';";

    private static String DELETE_OPERATION_CODE_SQL = "delete from auth_operation_code where code = '%s';";

    private static String CREATER = "OperationCodeUtils";

    private static String API_SUFFIX = "/api/%s";

    private static List<OperationCode> getOperationCodes(Class<?> cla) {
        // 获取特定包下所有的类(包括接口和类)
        Set<Class<?>> classes = ClassUtils.getClasses(cla.getPackage());
        List<OperationCode> operationCodes = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        if (!CollectionUtils.isEmpty(classes)) {
            List<String> tmpList = new ArrayList<>();
            classes.forEach(cls -> {
                Method[] methods = cls.getDeclaredMethods();
                if (!ObjectUtils.isEmpty(methods) && methods.length > 0) {
                    for (Method method : methods) {
                        PreAuthorize preAuthorize = method.getAnnotation(PreAuthorize.class);
                        if (ObjectUtils.isEmpty(preAuthorize)) {
                            warnings.add("##warning：类名为" + cls.getSimpleName() + "中的方法" + method.getName() + "未添加操作码！");
                            continue;
                        }
                        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                        ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
                        RequestMethod[] requestMethods = requestMapping.method();
                        String[] urls = requestMapping.value();

                        String code = preAuthorize.value();


                        Matcher m = Pattern.compile("hasAuthority\\(\'([^']*)\'\\)").matcher(code);
                        while (m.find()) {
                            String authority = m.group(1);
                            if (authority.length() > 50) {
                                errors.add("error：操作码" + authority + "的长度超过50！");
                            }
                            if (!"admin".equals(authority)) {
                                for (RequestMethod requestMethod : requestMethods) {
                                    for (String url : urls) {
                                        if (tmpList.contains(authority)) {
                                            // 操作码重复
                                            errors.add("error：重复的操作码" + code + "！");
                                        } else {
                                            OperationCode operationCode = new OperationCode(apiOperation.value(), authority, apiOperation.value(), API_SUFFIX + url, cls.getSimpleName(), requestMethod.name());
                                            operationCodes.add(operationCode);
                                            tmpList.add(authority);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            });
        }
        if (!CollectionUtils.isEmpty(errors)) {
            errors.forEach(error -> {
                System.out.println(error);
            });
            return null;
        }
        if (!CollectionUtils.isEmpty(warnings)) {
            warnings.forEach(warning -> {
                System.out.println(warning);
            });
        }
        return operationCodes;
    }

    public static void createOperationCodeSqlFile(DBProperties dbProperties, String serviceId, Class<?> cla) throws SQLException {
        Map<String, List<String>> codeSqlMap = buildCodeSql(dbProperties, serviceId, cla);
    }

    private static Map<String, List<String>> buildCodeSql(DBProperties dbProperties, String serviceId, Class<?> cla) throws SQLException {
        API_SUFFIX = String.format(API_SUFFIX, serviceId.split("-")[1]);
        // 扫描到的操作码
        List<OperationCode> operationCodes = getOperationCodes(cla);
        if (CollectionUtils.isEmpty(operationCodes)) {
            return null;
        }

        // 数据库已有的操作码
        Map<String, OperationCode> dbOperationCodeMap = getOperationCodeListFromDB(dbProperties, serviceId);

        List<String> codes = operationCodes.stream().map(OperationCode::getCode).collect(Collectors.toList());
        Set<String> dbCodes = dbOperationCodeMap.keySet();

        List<String> deleteSqls = new ArrayList<>();
        dbCodes.forEach(dbCode -> {
            if (!codes.contains(dbCode)) {
                dbOperationCodeMap.remove(dbCode);
                String deleteSql = String.format(DELETE_OPERATION_CODE_SQL, dbCode);
                deleteSqls.add(deleteSql);
            }
        });

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);

        List<String> insertSqls = new ArrayList<>();
        List<String> updateSqls = new ArrayList<>();

        operationCodes.forEach(operationCode -> {
            String code = operationCode.getCode();
            String sql;
            if (dbOperationCodeMap.containsKey(code)) {
                if (!operationCode.equals(dbOperationCodeMap.get(code))) {
                    // 数据库中已经有这些操作码
                    sql = String.format(UPDATE_OPERATION_CODE_SQL,
                            serviceId, operationCode.getName(), operationCode.getMemo(),
                            operationCode.getPath(), operationCode.getController(), operationCode.getRequestMethod(), CREATER, dateStr,
                            operationCode.getCode());
                    updateSqls.add(sql);
                }
            } else {
                // 新增操作码
                sql = String.format(INSERT_OPERATION_CODE_SQL,
                        UUID.randomUUID().toString(), serviceId, operationCode.getName(), operationCode.getCode(), operationCode.getMemo(),
                        operationCode.getPath(), operationCode.getController(), operationCode.getRequestMethod(), operationCode.getPath(), CREATER, dateStr, CREATER, dateStr);
                insertSqls.add(sql);
            }
        });

        if (!CollectionUtils.isEmpty(deleteSqls)) {
            System.out.println("#########################################################删除" + deleteSqls.size() + "条操作码#########################################################");
            deleteSqls.forEach(deleteSql -> {
                System.out.println(deleteSql);
            });
            System.out.println("#############################################################################################################################");
        } else {
            System.out.println("##没有需要删除的操作码");
        }

        if (!CollectionUtils.isEmpty(updateSqls)) {
            System.out.println("#########################################################修改" + updateSqls.size() + "条操作码#########################################################");
            updateSqls.forEach(updateSql -> {
                System.out.println(updateSql);
            });
            System.out.println("#############################################################################################################################");
        } else {
            System.out.println("##没有需要修改的操作码");
        }

        if (!CollectionUtils.isEmpty(insertSqls)) {
            System.out.println("#########################################################新增" + insertSqls.size() + "条操作码#########################################################");
            insertSqls.forEach(insertSql -> {
                System.out.println(insertSql);
            });
            System.out.println("#############################################################################################################################");
        } else {
            System.out.println("##没有需要新增的操作码");
        }

        Map<String, List<String>> codeSqlMap = new HashMap<>();
        codeSqlMap.put("insert", insertSqls);
        codeSqlMap.put("update", updateSqls);
        codeSqlMap.put("delete", deleteSqls);
        return codeSqlMap;
    }

    /**
     * 从数据库查询已有的操作码
     *
     * @param dbProperties 数据库属性
     * @param serviceId    服务id
     * @return Map
     */
    private static Map<String, OperationCode> getOperationCodeListFromDB(DBProperties dbProperties, String serviceId) throws SQLException {
        DbUtils.init(dbProperties);
        ResultSet resultSet = DbUtils.query(String.format(QUERY_OPERATION_CODE_BY_SERVICE_ID, serviceId));
        Map<String, OperationCode> dbOperationCodeMap = new HashMap<>();
        while (resultSet.next()) {
            String code = resultSet.getString("code");
            OperationCode operationCode = new OperationCode(
                    resultSet.getString("id"), resultSet.getString("service_id"), resultSet.getString("name"),
                    code, resultSet.getString("memo"), resultSet.getString("path"),
                    resultSet.getString("controller"), resultSet.getString("request_method"), resultSet.getString("creater"),
                    resultSet.getDate("creater_time"), resultSet.getString("modifier"), resultSet.getDate("modified_time")
            );
            dbOperationCodeMap.put(code, operationCode);
        }
        return dbOperationCodeMap;
    }

}
