package com.wisdomregulation.frame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by King2016 on 2017/1/18.
 */

public class FilterEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public String key="";//map需要的key
    public String keytext="";//中文
    public String url="";//如果是spinner需要进行网络请求访问
    public String widgettype ="EditText";//组件类型 比如时 radio还是 spinner 待完善
    public List<CodeAndString> codeAndStrings;//中文 和 code的包装类

    public FilterEntity() {
    }

    public FilterEntity(String key, String keytext, String url, String valueweighttype, List<CodeAndString> codeAndStrings) {
        this.key = key;
        this.keytext = keytext;
        this.url = url;
        this.widgettype = valueweighttype;
        this.codeAndStrings = codeAndStrings;
    }

    public List<CodeAndString> getCodeAndStrings() {
        return codeAndStrings;
    }

    public FilterEntity setCodeAndStrings(List<CodeAndString> codeAndStrings) {
        this.codeAndStrings = codeAndStrings;
        return this;
    }
    public FilterEntity setCodeAndStrings(CodeAndString... codeAndStrings) {
        this.codeAndStrings=new ArrayList<CodeAndString>();
        for (int i = 0; i < codeAndStrings.length; i++) {
            this.codeAndStrings.add(codeAndStrings[i]);
        }
        return this;
    }

    public String getKey() {
        return key;
    }

    public FilterEntity setKey(String key) {
        this.key = key;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public FilterEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getWidgettype() {
        return widgettype;
    }

    public FilterEntity setWidgettype(String widgettype) {
        this.widgettype = widgettype;
        return this;
    }

    public String getKeytext() {
        return keytext;
    }

    public FilterEntity setKeytext(String keytext) {
        this.keytext = keytext;
        return this;
    }
}
