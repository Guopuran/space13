package guopuran.bwie.com.space13.model;

import java.util.Map;

import guopuran.bwie.com.space13.util.ICallBack;
import guopuran.bwie.com.space13.util.MyCallBack;
import guopuran.bwie.com.space13.util.OkUtil;

public class ImodelImpl implements Imodel {
    @Override
    public void requestmodel(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkUtil.getInstance().getenqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object object) {
                callBack.getdata(object);
            }

            @Override
            public void faiekd(Exception e) {

            }
        });
    }
}
