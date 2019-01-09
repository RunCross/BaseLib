package top.crossrun.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CRBaseFragment extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            _initRootView(inflater, container);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void _initRootView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(getRootLayoutId(), container, false);
        initRootView();
    }

    public final <T extends View> T findViewById(@IdRes int id) {
        if (rootView != null) {
            return rootView.findViewById(id);
        }
        return null;
    }

    private ProgressDialog mProgressDialog;


    protected void showProgressDialog() {
        if (mProgressDialog==null){
            mProgressDialog = new ProgressDialog(getContext(), R.style.cr_wait_dialog);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if(mProgressDialog != null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.cr_common_request_layout);
        }
    }

    protected void dissmissProgressDialog() {
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    protected abstract @LayoutRes
    int getRootLayoutId();


    protected abstract void initRootView();
}
