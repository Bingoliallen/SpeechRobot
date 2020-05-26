package com.vma.speechrobot.adapter.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.vma.speechrobot.view.fragment.TaskManagerInfoFragment;
import com.vma.speechrobot.view.fragment.TaskManagerRecordFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Date: 2018/12/27
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskPagerNewAdapter  extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"通话记录", "基础信息"};
    //private HashMap<String, Fragment> mFragments = new HashMap<>();
    private List<Fragment> mFragments=new ArrayList<>();

    public void setmFragments(List<Fragment> mFragments) {
        this.mFragments = mFragments;
    }

    public TaskPagerNewAdapter(FragmentManager fm,List<Fragment> mFragments) {
        super(fm);
        this.mFragments=mFragments;
    }

    @Override
    public Fragment getItem(int position) {
     /*   Fragment fragment = mFragments.get(mTitles[position]);
        if (fragment == null) {
            Bundle args = new Bundle();
            args.putInt( "id", mId);
            if (position == 0) {
                fragment = new TaskManagerInfoFragment();

            } else {
                fragment = new TaskManagerRecordFragment();
            }
            fragment.setArguments(args);
        }*/
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = mFragments.get(position).hashCode();
        return hashCode;
    }

}
