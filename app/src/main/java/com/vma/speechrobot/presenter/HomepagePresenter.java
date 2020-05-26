package com.vma.speechrobot.presenter;

import android.text.TextUtils;

import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.HomepageBean;
import com.vma.speechrobot.bean.StatisticsInfoBean;
import com.vma.speechrobot.bean.StatisticsLevel;
import com.vma.speechrobot.model.HomepageModel;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.IHomepagePresenter;
import com.vma.speechrobot.utils.UserInfoManager;

public class HomepagePresenter extends
        BasePresenter<IHomepagePresenter.IView, HomepageModel> implements IHomepagePresenter {

    public HomepagePresenter(IView view) {
        super(view);
    }

    @Override
    protected HomepageModel createModel() {
        return new HomepageModel();
    }

    @Override
    public void getHomepage() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.getHomepage(macKey)
                        .compose(RxHelper.io_main())
                        .map(new Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<HomepageBean>(getView().getContext()) {
                            @Override
                            public void onNext(HomepageBean homepageBean) {
                                super.onNext(homepageBean);
                                if (isAttach()) {
                                    getView().fillHomepage(homepageBean);
                                }
                            }
                        }));
    }

    @Override
    public void getHomepageStatisticsInfo(String date_type) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.getHomepageStatisticsInfo(macKey, date_type)
                        .compose(RxHelper.io_main())
                        .map(new Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<StatisticsInfoBean>(getView().getContext()) {
                            @Override
                            public void onNext(StatisticsInfoBean homepageBean) {
                                super.onNext(homepageBean);
                                if (isAttach()) {
                                    getView().fillHomepageStatisticsInfo(homepageBean);
                                }
                            }
                        }));
    }

    @Override
    public void getHomepageStatisticsInfoLevel(String date_type) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.getHomepageStatisticsInfoLevel(macKey, date_type)
                        .compose(RxHelper.io_main())
                        .map(new Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<StatisticsLevel>(getView().getContext()) {
                            @Override
                            public void onNext(StatisticsLevel homepageBean) {
                                super.onNext(homepageBean);
                                if (isAttach()) {
                                    getView().fillHomepageStatisticsInfoLevel(homepageBean);
                                }
                            }
                        }));
    }


}
