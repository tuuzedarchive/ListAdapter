package com.tuuzed.androidx.list.loadmore;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tuuzed.androidx.list.loadmore.internal.Utils;

interface ViewHolders {

    class LoadFailed extends RecyclerView.ViewHolder {

        LoadFailed(@NonNull View itemView, final LoadMoreController controller, final OnLoadMoreListener listener) {
            super(itemView);
            Utils.setItemViewFullSpan(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.setLoadFailed(false);
                    if (listener != null) {
                        listener.onLoadMore(controller);
                    }
                }
            });
        }

    }

    class Footer extends RecyclerView.ViewHolder {

        Footer(@NonNull View itemView) {
            super(itemView);
            Utils.setItemViewFullSpan(itemView);
        }

    }

    class NoMore extends RecyclerView.ViewHolder {

        NoMore(@NonNull View itemView) {
            super(itemView);
            Utils.setItemViewFullSpan(itemView);
        }

    }
}
