package com.example.common.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.common.utils.ValueUtil;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

  public static final int VIEW_TYPE_DEFAULT = 0;

  protected LayoutInflater mInflater;
  protected Context mContext;
  protected List<T> mData=new ArrayList<>();
  protected SparseIntArray mItemLayoutIds;
  protected OnRecyclerItemClickListener<T> onRecyclerItemClickListener;

  public BaseRecyclerAdapter(Context context) {
    this.mContext = context;
    this.mData = new ArrayList<>();
    this.mInflater = LayoutInflater.from(context);
    mItemLayoutIds = new SparseIntArray();
  }

  @Override
  public int getItemCount() {
    return mData == null ? 0 : mData.size();
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    onBind(holder, mData.get(position), position);

    if (onRecyclerItemClickListener != null) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onRecyclerItemClickListener
              .onRecyclerItemClicked(BaseRecyclerAdapter.this, holder.itemView, mData.get(position),
                  holder.getAdapterPosition());
        }
      });
    }
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = mInflater.inflate(mItemLayoutIds.get(viewType), parent, false);
    ViewHolder holder = new ViewHolder(view);
    holder.viewType = viewType;
    return holder;
  }

  @Override
  final public int getItemViewType(int position) {
    return getItemViewType(position, mData.get(position));
  }

  /**
   * 添加item布局id
   */
  protected void putItemLayoutId(int viewType, Integer layoutId) {
    if (mItemLayoutIds.indexOfValue(viewType) != -1) {
      mItemLayoutIds.delete(viewType);
    }
    mItemLayoutIds.put(viewType, layoutId);
  }

  protected void putItemLayoutId(Integer layoutId) {
    putItemLayoutId(VIEW_TYPE_DEFAULT, layoutId);
  }

  public List<T> getData() {
    return mData;
  }

  /**
   * 增删改查
   */
  public void set(List<T> data) {
    if (data != null) {
      mData.clear();
      mData.addAll(data) ;
      notifyDataSetChanged();
    }

  }

  public void set(int startPosition, int endPosition, List<T> data) {
    for (int i = startPosition; i < endPosition && i < mData.size(); i++) {
      mData.remove(i);
    }
    mData.addAll(data);
    notifyItemRangeChanged(startPosition, endPosition - startPosition);
  }

  public void setWithPaging(int pageNum, int pageSize, List<T> data) {
    if (pageNum == 1) {
      removeAll();
    }
    int starPosition = (pageNum - 1) * pageSize;
    int endPosition = starPosition + (ValueUtil.isEmpty(data) ? pageSize : data.size());
    set(starPosition, endPosition, data);
  }

  public void add(int position, T data) {
    if (data == null) {
      return;
    }
    mData.add(position, data);
    notifyItemInserted(position);
  }

  public void add(List<T> data) {
    if (data == null || data.size() == 0) {
      return;
    }
    int positionStart = getItemCount();
    mData.addAll(data);
    notifyItemRangeInserted(positionStart, data.size());
  }

  public void update(T data) {
    if (data == null) {
      return;
    }
    int index = mData.indexOf(data);
    if (index != -1) {
      mData.set(index, data);
    }
    notifyItemChanged(index);
  }

  public void remove(T item) {
    if (item == null) {
      return;
    }
    int position = mData.indexOf(item);
    mData.remove(position);
    notifyItemRemoved(position);
  }

  public void remove(int position) {
    if (position < 0 || position >= getItemCount()) {
      return;
    }
    mData.remove(position);
    notifyItemRemoved(position);
  }

  public void removeAll() {
    mData.clear();
    notifyDataSetChanged();
  }

  /**
   * 获取item视图类型
   * 子类可以重写该方法返回多视图类型
   */
  public int getItemViewType(int position, T item) {
    return VIEW_TYPE_DEFAULT;
  }

  /**
   * 绑定数据
   */
  public abstract void onBind(final ViewHolder holder, T item, int position);

  public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
    this.onRecyclerItemClickListener = listener;
  }

  public interface OnRecyclerItemClickListener<T> {

    void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, T data, int position);
  }
}
