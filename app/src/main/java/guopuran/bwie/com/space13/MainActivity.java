package guopuran.bwie.com.space13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;

import guopuran.bwie.com.space13.bean.Bean;
import guopuran.bwie.com.space13.presenter.IpresenterImpl;
import guopuran.bwie.com.space13.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private String url="http://www.xieast.com/api/news/news.php?page=%d";
    private IpresenterImpl mIpresenterImpl;
    private boolean flag=true;
    private TextView text_qiehuan;
    private XRecyclerView xRecyclerView;
    private final int ITEM_COUNT=2;
    private int page;
    private XrecyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page=1;
        initPresenter();
        initView();
    }

    private void initView() {
        //获取资源ID
        text_qiehuan = findViewById(R.id.main_button_qiehuan);
        xRecyclerView = findViewById(R.id.xrecy);
        text_qiehuan.setOnClickListener(this);
        initLinear();
        //支持刷新和加载
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        changeView();
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initUrl();
            }

            @Override
            public void onLoadMore() {
                initUrl();
            }
        });
        initUrl();
    }

    private void initUrl() {
        mIpresenterImpl.requestter(String.format(url,page),new HashMap<String, String>(),Bean.class);
    }

    //互绑
    private void initPresenter() {
        mIpresenterImpl=new IpresenterImpl(this);
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIpresenterImpl.Deatch();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.main_button_qiehuan:
                List<Bean.DataBean> list = adapter.getList();

                changeView();
                adapter.setList(list);
                break;
            default:break;
        }
    }

    private void changeView() {
        if (flag){
            initLinear();
        }else{
            initGrid();
        }
        adapter = new XrecyAdapter(this,flag);
        xRecyclerView.setAdapter(adapter);
        flag=!flag;
    }

    private void initLinear() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void initGrid() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,ITEM_COUNT);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void getdata(Object object) {
        if (object instanceof Bean){
            Bean bean= (Bean) object;
            if (page==1){
                adapter.setList(bean.getData());
            }else{
                adapter.addList(bean.getData());
            }
            page++;
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }
    }
}
