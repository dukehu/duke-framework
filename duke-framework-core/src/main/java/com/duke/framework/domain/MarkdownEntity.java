package com.duke.framework.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created duke on 2018/10/25
 */
@Data
@Setter
@Getter
public class MarkdownEntity {
    private static String TAG_WIDTH = "<style type=\"text/css\"> %s { width:85%%} </style>";

    /**
     * 前缀
     */
    private String htmlPrefix;

    /**
     * 后缀
     */
    private String htmlSuffix = "</body>\n</html>";

    /**
     * 最外网的div标签， 可以用来设置样式，宽高，字体等
     */
    private Map<String, String> divStyle = new ConcurrentHashMap<>();

    /**
     * 转换后的html文档
     */
    private String html;

    public MarkdownEntity() {
    }

    public MarkdownEntity(String html) {
        this.html = html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return htmlPrefix + "\n<div " + parseDiv() + ">\n" + html + "\n</div>" + htmlSuffix;
    }


    private String parseDiv() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : divStyle.entrySet()) {
            builder.append(entry.getKey()).append("=\"")
                    .append(entry.getValue()).append("\" ");
        }
        return builder.toString();
    }


    public void addDivStyle(String attrKey, String value) {
        if (divStyle.containsKey(attrKey)) {
            divStyle.put(attrKey, divStyle.get(attrKey) + " " + value);
        } else {
            divStyle.put(attrKey, value);
        }
    }


    public void addWidthCss(String tag) {
        String wcss = String.format(TAG_WIDTH, tag);
        htmlPrefix += wcss;
    }
}
