package com.duke.framework.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

/**
 * Created duke on 2018/7/2
 * <p>
 * 文件工具类
 */
@Slf4j
public class FileUtils {


    /**
     * 得到文件后缀名
     *
     * @param originalFileName 文件原始名称，如：time.png
     * @return 文件后缀
     */
    public static String getFileSuffix(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length());
    }


    /**
     * 拼装上传文件的文件夹路径
     *
     * @param serviceId 服务id
     * @return 文件夹路径
     */
    public static String getRelativeFilePath(String serviceId) {
        Calendar calendar = Calendar.getInstance();
        // 如："/blog/2018/07/02"
        return "/nologin/" + serviceId +
                "/files/" + calendar.get(Calendar.YEAR) +
                "/" + (calendar.get(Calendar.MONTH) + 1) +
                "/" + calendar.get(Calendar.DATE);
    }

    /**
     * 拼装上传文件的文件夹路径（文件预览时pdf的文件路径）
     *
     * @param serviceId 服务id
     * @return 文件夹路径
     */
    public static String getPdfPreviewRelativeFilePath(String serviceId) {
        Calendar calendar = Calendar.getInstance();
        return "/nologin/" + serviceId +
                "/pdf_preview/" + calendar.get(Calendar.YEAR) +
                "/" + (calendar.get(Calendar.MONTH) + 1) +
                "/" + calendar.get(Calendar.DATE);
    }

    /**
     * pdf转word之后pdf的文件夹路径
     *
     * @param serviceId 服务id
     * @return 文件夹路径
     */
    public static String getPdfRelativeFilePath(String serviceId) {
        Calendar calendar = Calendar.getInstance();
        return "/nologin/" + serviceId +
                "/pdf/" + calendar.get(Calendar.YEAR) +
                "/" + (calendar.get(Calendar.MONTH) + 1) +
                "/" + calendar.get(Calendar.DATE);
    }

    /**
     * pdf转word之后word的文件夹路径
     *
     * @param serviceId 服务id
     * @return 文件夹路径
     */
    public static String getWordRelativeFilePath(String serviceId) {
        Calendar calendar = Calendar.getInstance();
        return "/nologin/" + serviceId +
                "/word/" + calendar.get(Calendar.YEAR) +
                "/" + (calendar.get(Calendar.MONTH) + 1) +
                "/" + calendar.get(Calendar.DATE);
    }


    /**
     * 拼装上传文件的文件夹路径（文件分块上传时块的文件路径）
     *
     * @param serviceId 服务id
     * @return 文件夹路径
     */
    public static String getChunkRelativeFilePath(String serviceId) {
        Calendar calendar = Calendar.getInstance();
        return "/nologin/" + serviceId +
                "/chunk/" + calendar.get(Calendar.YEAR) +
                "/" + (calendar.get(Calendar.MONTH) + 1) +
                "/" + calendar.get(Calendar.DATE);
    }

    /**
     * 截取文件名称，去掉后缀
     *
     * @param originalFileName 文件原始名称
     * @return 文件名称
     */
    public static String getFileName(String originalFileName) {
        return originalFileName.substring(0, originalFileName.lastIndexOf("."));
    }

    /**
     * 下载文件夹
     *
     * @param response 响应
     * @param fileName 文件名
     * @param file     文件
     */
    public static void readDownloadFile(HttpServletResponse response, String fileName, File file)
            throws IOException {
        response.reset();
        response.setContentType("application/x-download");
        response.setCharacterEncoding("utf-8");
        FileInputStream is = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        fileName = new String((fileName).getBytes("utf-8"), "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        response.setHeader("Access-Control-Expose-Headers", "fileSize");
//        response.setHeader("fileSize", fileSize);
        byte[] bytes = new byte[1024];
        int len;
        log.info("文件下载开始：" + fileName);
        while ((len = is.read(bytes)) != -1) {
            out.write(bytes, 0, len);
            out.flush();
        }
        log.info("文件下载结束：" + fileName);
        is.close();
        out.close();
    }

    /**
     * 删除文件夹
     *
     * @param baseDownloadLocation 文件路径
     */
    public static void delete(String baseDownloadLocation) {
        log.info(baseDownloadLocation);
        // 删除zip包
        File zpiFile = new File(baseDownloadLocation + ".zip");
        if (zpiFile.exists()) {
            zpiFile.delete();
        }

        File file = new File(baseDownloadLocation);

        if (!file.exists()) {
            log.info("删除文件失败:" + baseDownloadLocation + "不存在！");
            return;
        }

        if (file.isFile()) {
            // 删除文件
            boolean deleted = file.delete();
            log.info("删除文件:" + baseDownloadLocation + (deleted ? ",成功" : "，失败！"));
        } else {
            // 删除文件夹
            try {
                deleteDirectory(file);
                log.info("删除文件夹：" + baseDownloadLocation + "成功");
            } catch (IOException e) {
                log.info("删除文件夹：" + baseDownloadLocation + "失败！");
            }
        }
    }
}
