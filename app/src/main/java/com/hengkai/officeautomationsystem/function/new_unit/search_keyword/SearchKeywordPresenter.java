package com.hengkai.officeautomationsystem.function.new_unit.search_keyword;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/6/1.
 */
public class SearchKeywordPresenter extends BasePresenter<SearchKeywordActivity> {

    private final SearchKeywordModel model;

    public SearchKeywordPresenter() {
        model = new SearchKeywordModel();
    }

    public void getKeywordList(String searchName) {
        model.getKeywordList(searchName, new Observer<NewUnitKeywordEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NEW_UNIT_ACTIVITY_GET_KEYWORD_LIST, d);
            }

            @Override
            public void onNext(NewUnitKeywordEntity newUnitKeywordEntity) {
                if (newUnitKeywordEntity.CODE == 1) {
                    if (newUnitKeywordEntity.DATA.size() == 0) {
                        ToastUtil.showToast("暂无相关关键词数据");
                    } else {
                        view.getKeywordList(newUnitKeywordEntity.DATA);
                    }
                } else if (newUnitKeywordEntity.CODE == 0) {
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }
}
