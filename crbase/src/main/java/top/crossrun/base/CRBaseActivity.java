package top.crossrun.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import top.crossrun.base.recycle.RecycleAble;
import top.crossrun.base.recycle.RecycleList;
import top.crossrun.base.recycle.RecycleObserver;
import top.crossrun.base.recycle.RecycleRegister;

public abstract class CRBaseActivity extends AppCompatActivity implements RecycleObserver {
    ViewStub viewStub;
    View title;

    RecycleList recycleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.cr_activity_base);
        title = findViewById(R.id.title);
        viewStub = findViewById(R.id.viewStub);
        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recycleList = new RecycleList();
    }

    @Override
    public void setContentView(int layoutResID) {
        viewStub.setLayoutResource(layoutResID);
        viewStub.inflate();
    }

    public void setBackViewVisibility(int visibility) {
        findViewById(R.id.title_back).setVisibility(visibility);
    }

    public void setMyTitle(String title) {
        ((TextView) findViewById(R.id.title_text)).setText(title);
    }

    public void setMyTitle(@StringRes int strId) {
        ((TextView) findViewById(R.id.title_text)).setText(strId);
    }

    /**
     * @param visibility View.Gone View.VisiAble ...
     */
    public void setMyTitleVisibility(int visibility) {
        title.setVisibility(visibility);
    }

    @Override
    public void add(RecycleAble recycleAble) {
        recycleList.add(recycleAble);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recycleList != null) {
            recycleList.recycle();
        }
    }

    private ProgressDialog mProgressDialog;


    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.cr_wait_dialog);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.cr_common_request_layout);
        }
    }

    protected void dissmissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void toast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(@StringRes int strId) {
        Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 改变状态栏颜色
     *
     * @param statusColor
     */
    public void setStatusBarColor(int statusColor) {
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

}
