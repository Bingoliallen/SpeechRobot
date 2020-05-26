package com.vma.speechrobot.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.utils.DrawableUtil;
import com.example.common.utils.ViewUtil;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.MenuItemBean;
import com.vma.speechrobot.event.MainMenuEvent;
import com.vma.speechrobot.utils.UserInfoManager;
import com.vma.speechrobot.view.activity.LoginActivity;
import org.greenrobot.eventbus.EventBus;

public class MainMenuAdapter extends BaseRecyclerAdapter<MenuItemBean> {

  public MainMenuAdapter(Context context) {
    super(context);
    putItemLayoutId(R.layout.recycler_item_main_menu);
    setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<MenuItemBean>() {

      @Override
      public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, MenuItemBean data,
          int position) {
        switch (position) {
          case 0:
            EventBus.getDefault().post(new MainMenuEvent(MainMenuEvent.HOMEPAGE));
            break;
          case 1:
            EventBus.getDefault().post(new MainMenuEvent(MainMenuEvent.TASK_MANAGER));
            break;
          case 2:
            EventBus.getDefault().post(new MainMenuEvent(MainMenuEvent.TASK_CALL));
            break;
          case 3:
            UserInfoManager.delete();
            LoginActivity.clearLaunch(mContext);
            break;
        }
      }
    });
  }

  @Override
  public void onBind(ViewHolder holder, MenuItemBean item, int position) {
    holder.setText(R.id.tv_name, item.name);
    ImageView iv = holder.getView(R.id.icon);
    iv.setImageResource(item.icon);
    /*TextView tv = (TextView) holder.getView();
    tv.setText(item.name);
    DrawableUtil.setLeftDrawable(mContext, tv, item.icon, ViewUtil.dp2px(mContext, 17),
        ViewUtil.dp2px(mContext, 17));*/
  }
}
