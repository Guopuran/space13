package guopuran.bwie.com.space13.model;

import java.util.Map;

import guopuran.bwie.com.space13.util.MyCallBack;

public interface Imodel {
    void requestmodel(String url, Map<String,String> params, Class clazz, MyCallBack callBack);
}
