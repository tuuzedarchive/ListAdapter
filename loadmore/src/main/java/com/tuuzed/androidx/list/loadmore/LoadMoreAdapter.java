package com.tuuzed.androidx.list.loadmore;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.tuuzed.androidx.list.loadmore.internal.Utils;

import java.util.List;

class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private static final int TYPE_NO_MORE = Integer.MIN_VALUE + 2;
    private static final int TYPE_LOAD_FAILED = Integer.MIN_VALUE + 3;

    private RecyclerView.Adapter mOriginalAdapter;

    private View mFooterView;
    private int mFooterResId;

    private View mNoMoreView;
    private int mNoMoreResId;

    private View mLoadFailedView;
    private int mLoadFailedResId;

    private RecyclerView mRecyclerView;
    private OnLoadMoreListener mOnLoadMoreListener;

    private LoadMoreController mLoadMoreController;
    private boolean mIsLoading;
    private boolean mShouldRemove;
    private boolean mShowNoMoreEnabled;
    private boolean mIsLoadFailed;

    private final RecyclerView.AdapterDataObserver mObserver = new InnerAdapterDataObserver();
    /**
     * Deciding whether to trigger loading
     * 判断是否触发加载更多
     */
    private final RecyclerView.OnScrollListener mOnScrollListener = new OnScrollListenerImpl();

    LoadMoreAdapter(
            @NonNull RecyclerView.Adapter originalAdapter,
            @NonNull OnLoadMoreListener onLoadMoreListener,
            @NonNull LoadMoreController loadMoreController,
            @Nullable View footerView, @LayoutRes int footerResId,
            @Nullable View noMoreView, @LayoutRes int noMoreResId,
            @Nullable View loadFailedView, @LayoutRes int loadFailedResId
    ) {
        this.mFooterView = footerView;
        this.mFooterResId = footerResId;
        this.mNoMoreView = noMoreView;
        this.mNoMoreResId = noMoreResId;
        this.mLoadFailedView = loadFailedView;
        this.mLoadFailedResId = loadFailedResId;
        this.mOnLoadMoreListener = onLoadMoreListener;
        this.mOriginalAdapter = originalAdapter;
        this.mOriginalAdapter.registerAdapterDataObserver(mObserver);
        this.mLoadMoreController = loadMoreController;
        this.mLoadMoreController.setCallback(new LoadMoreControllerCallbackImpl());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            if (mFooterResId != View.NO_ID) {
                mFooterView = Utils.inflate(parent, mFooterResId);
            }
            if (mFooterView != null) {
                return new ViewHolders.Footer(mFooterView);
            }
            View view = Utils.inflate(parent, R.layout.loadmore_footer);
            return new ViewHolders.Footer(view);
        } else if (viewType == TYPE_NO_MORE) {
            if (mNoMoreResId != View.NO_ID) {
                mNoMoreView = Utils.inflate(parent, mNoMoreResId);
            }
            if (mNoMoreView != null) {
                return new ViewHolders.NoMore(mNoMoreView);
            }
            View view = Utils.inflate(parent, R.layout.loadmore_nomore);
            return new ViewHolders.NoMore(view);
        } else if (viewType == TYPE_LOAD_FAILED) {
            if (mLoadFailedResId != View.NO_ID) {
                mLoadFailedView = Utils.inflate(parent, mLoadFailedResId);
            }
            View view = mLoadFailedView;
            if (view == null) {
                view = Utils.inflate(parent, R.layout.loadmore_loadfailed);
            }
            return new ViewHolders.LoadFailed(view, mLoadMoreController, mOnLoadMoreListener);
        }

        return mOriginalAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (holder instanceof ViewHolders.Footer) {
            // 当 recyclerView 不能滚动的时候(item 不能铺满屏幕的时候也是不能滚动的)
            // call loadMore
            if (!canScroll() && mOnLoadMoreListener != null && !mIsLoading) {
                mIsLoading = true;
                // fix Cannot call this method while RecyclerView is computing a layout or scrolling
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mOnLoadMoreListener.onLoadMore(mLoadMoreController);
                    }
                });
            }
        } else if (holder instanceof ViewHolders.NoMore || holder instanceof ViewHolders.LoadFailed) {
            // pass
        } else {
            //noinspection unchecked
            mOriginalAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        int count = mOriginalAdapter.getItemCount();
        return getLoadMoreEnabled() ? count + 1 : mShowNoMoreEnabled ?
                count + 1 : count + (mShouldRemove ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mOriginalAdapter.getItemCount() && mIsLoadFailed) {
            return TYPE_LOAD_FAILED;
        }
        if (position == mOriginalAdapter.getItemCount() && (getLoadMoreEnabled() || mShouldRemove)) {
            return TYPE_FOOTER;
        } else if (position == mOriginalAdapter.getItemCount() && mShowNoMoreEnabled && !getLoadMoreEnabled()) {
            return TYPE_NO_MORE;
        }
        return mOriginalAdapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        final int itemViewType = getItemViewType(position);
        if (mOriginalAdapter.hasStableIds() && itemViewType != TYPE_FOOTER &&
                itemViewType != TYPE_LOAD_FAILED && itemViewType != TYPE_NO_MORE) {
            return mOriginalAdapter.getItemId(position);
        }
        return super.getItemId(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(mOnScrollListener);

        // 当为 GridLayoutManager 的时候, 设置 footerView 占据整整一行.
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            // 获取原来的 SpanSizeLookup,当不为 null 的时候,除了 footerView 都应该返回原来的 spanSize
            final GridLayoutManager.SpanSizeLookup originalSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int itemViewType = getItemViewType(position);
                    if (itemViewType == TYPE_FOOTER || itemViewType == TYPE_NO_MORE ||
                            itemViewType == TYPE_LOAD_FAILED) {
                        return gridLayoutManager.getSpanCount();
                    } else if (originalSizeLookup != null) {
                        return originalSizeLookup.getSpanSize(position);
                    }

                    return 1;
                }
            });
        }
    }

    /**
     * clean
     */
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(mOnScrollListener);
        mOriginalAdapter.unregisterAdapterDataObserver(mObserver);
        mRecyclerView = null;
    }


    private boolean canScroll() {
        if (mRecyclerView == null) {
            throw new NullPointerException("mRecyclerView is null, you should setAdapter(recyclerAdapter);");
        }
        return mRecyclerView.canScrollVertically(-1);
    }

    // 取到最后的一个节点
    private static int last(int[] lastPositions) {
        int last = lastPositions[0];
        for (int value : lastPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }


    private boolean getLoadMoreEnabled() {
        return mLoadMoreController.getLoadMoreEnabled() && mOriginalAdapter.getItemCount() >= 0;
    }

    /**
     * update last item
     */
    private void notifyFooterHolderChanged() {
        if (getLoadMoreEnabled()) {
            LoadMoreAdapter.this.notifyItemChanged(mOriginalAdapter.getItemCount());
        } else if (mShouldRemove) {
            mShouldRemove = false;

            /*
              fix IndexOutOfBoundsException when setLoadMoreEnabled(false) and then use onItemRangeInserted
              @see android.support.v7.widget.RecyclerView.Recycler#validateViewHolderForOffsetPosition(RecyclerView.ViewHolder)
             */
            int position = mOriginalAdapter.getItemCount();
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder instanceof ViewHolders.Footer) {
                LoadMoreAdapter.this.notifyItemRemoved(position);
            } else {
                LoadMoreAdapter.this.notifyItemChanged(position);
            }
        }
    }

    private class OnScrollListenerImpl extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!getLoadMoreEnabled() || mIsLoading) {
                return;
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mOnLoadMoreListener != null) {
                boolean isBottom;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    isBottom = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1;
                } else if (layoutManager instanceof LinearLayoutManager) {
                    isBottom = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition()
                            >= layoutManager.getItemCount() - 1;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager sgLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] into = new int[sgLayoutManager.getSpanCount()];
                    sgLayoutManager.findLastVisibleItemPositions(into);

                    isBottom = last(into) >= layoutManager.getItemCount() - 1;
                } else {
                    // TODO
                    isBottom = false;
                }
                if (isBottom) {
                    mIsLoading = true;
                    mOnLoadMoreListener.onLoadMore(mLoadMoreController);
                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    private class LoadMoreControllerCallbackImpl implements LoadMoreController.Callback {

        @Override
        public void notifyChanged() {
            mShouldRemove = true;
        }

        @Override
        public void notifyLoadFailed(boolean isLoadFailed) {
            mIsLoadFailed = isLoadFailed;
            notifyFooterHolderChanged();
        }

        @Override
        public void notifyLoadComplete() {
            mShowNoMoreEnabled = true;
            notifyItemChanged(getItemCount());
        }
    }

    private class InnerAdapterDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            if (mShouldRemove) {
                mShouldRemove = false;
            }
            LoadMoreAdapter.this.notifyDataSetChanged();
            mIsLoading = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mShouldRemove && positionStart == mOriginalAdapter.getItemCount()) {
                mShouldRemove = false;
            }
            LoadMoreAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            mIsLoading = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mShouldRemove && positionStart == mOriginalAdapter.getItemCount()) {
                mShouldRemove = false;
            }
            LoadMoreAdapter.this.notifyItemRangeChanged(positionStart, itemCount, payload);
            mIsLoading = false;
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            // when no data is initialized (has loadMoreView)
            // should remove loadMoreView before notifyItemRangeInserted
            if (mRecyclerView.getChildCount() == 1) {
                LoadMoreAdapter.this.notifyItemRemoved(0);
            }
            LoadMoreAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            notifyFooterHolderChanged();
            mIsLoading = false;
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mShouldRemove && positionStart == mOriginalAdapter.getItemCount()) {
                mShouldRemove = false;
            }
            /*
               use notifyItemRangeRemoved after clear item, can throw IndexOutOfBoundsException
               @link RecyclerView#tryGetViewHolderForPositionByDeadline
               fix java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position
             */
            boolean shouldSync = false;
            if (mLoadMoreController.getLoadMoreEnabled() && mOriginalAdapter.getItemCount() == 0) {
                mLoadMoreController.setLoadMoreEnabled(false);
                shouldSync = true;
                // when use onItemRangeInserted(0, count) after clear item
                // recyclerView will auto scroll to bottom, because has one item(loadMoreView)
                // remove loadMoreView
                if (getItemCount() == 1) {
                    LoadMoreAdapter.this.notifyItemRemoved(0);
                }
            }
            LoadMoreAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            if (shouldSync) {
                mLoadMoreController.setLoadMoreEnabled(true);
            }
            mIsLoading = false;
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mShouldRemove && (fromPosition == mOriginalAdapter.getItemCount() || toPosition == mOriginalAdapter.getItemCount())) {
                throw new IllegalArgumentException("can not move last position after setLoadMoreEnabled(false)");
            }
            LoadMoreAdapter.this.notifyItemMoved(fromPosition, toPosition);
            mIsLoading = false;
        }
    }
}
