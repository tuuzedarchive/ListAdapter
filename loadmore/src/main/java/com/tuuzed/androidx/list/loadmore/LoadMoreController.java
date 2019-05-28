package com.tuuzed.androidx.list.loadmore;


public class LoadMoreController {

    public interface Callback {
        void notifyChanged();

        void notifyLoadFailed(boolean isLoadFailed);

        void notifyLoadComplete();
    }

    private boolean mLoadMoreEnabled = true;
    private boolean mIsLoadFailed = false;
    private Callback mCallback;


    public void setLoadFailed(boolean isLoadFailed) {
        if (mIsLoadFailed != isLoadFailed) {
            mIsLoadFailed = isLoadFailed;
            mCallback.notifyLoadFailed(isLoadFailed);
            setLoadMoreEnabled(!mIsLoadFailed);
        }
    }

    public void setNoMore() {
        mLoadMoreEnabled = false;
        if (mCallback != null) {
            mCallback.notifyLoadComplete();
        }
    }

    public void reset() {
        mLoadMoreEnabled = true;
    }


    void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    boolean getLoadMoreEnabled() {
        return mLoadMoreEnabled;
    }

    void setLoadMoreEnabled(boolean enabled) {
        final boolean canNotify = mLoadMoreEnabled;
        mLoadMoreEnabled = enabled;
        if (canNotify && !mLoadMoreEnabled) {
            mCallback.notifyChanged();
        }
    }


}
