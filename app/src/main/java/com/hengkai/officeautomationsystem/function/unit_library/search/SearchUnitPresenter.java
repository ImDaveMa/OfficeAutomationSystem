package com.hengkai.officeautomationsystem.function.unit_library.search;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/28.
 */
public class SearchUnitPresenter extends BasePresenter<SearchUnitActivity> {

    private final SearchUnitModel model;

    public SearchUnitPresenter() {
        model = new SearchUnitModel();
    }

    public void getKeywordList() {
        model.getKeywordList(new Observer<NewUnitKeywordEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_KEYWORD_LIST, d);
            }

            @Override
            public void onNext(NewUnitKeywordEntity newUnitKeywordEntity) {
                if (newUnitKeywordEntity.CODE == 1) {
                    if (newUnitKeywordEntity.DATA == null || newUnitKeywordEntity.DATA.size() == 0) {
                        view.noData();
                    } else {
                        view.getKeywordList(newUnitKeywordEntity.DATA);
                    }
                } else if (newUnitKeywordEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(newUnitKeywordEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.noData();
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }

    public void getUnitList(final int ID) {
        model.getUnitList(ID, new Observer<UnitLibraryEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_UNIT_LIST, d);
            }

            @Override
            public void onNext(UnitLibraryEntity unitLibraryEntity) {
                if (unitLibraryEntity.CODE == 1) {
                    List<UnitLibraryEntity.DATABean> data = unitLibraryEntity.DATA;
                    if (ID == 0 && (data == null || data.size() == 0)) {
                        view.noData();
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getUnitList(unitLibraryEntity.DATA);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (unitLibraryEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(unitLibraryEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (ID == 0) {
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }

    public void getUnitListWithKeyword(final boolean isClick, int keywordId, final int ID) {
        model.getUnitListWithKeyword(keywordId, ID, new Observer<UnitLibraryEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_UNIT_LIST, d);
            }

            @Override
            public void onNext(UnitLibraryEntity unitLibraryEntity) {
                if (unitLibraryEntity.CODE == 1) {
                    List<UnitLibraryEntity.DATABean> data = unitLibraryEntity.DATA;
                    if (ID == 0 && (data == null || data.size() == 0)) {
                        view.noData();
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getUnitList(unitLibraryEntity.DATA);
                        } else {
                            if (isClick) {
                                view.setViewState(false);
                            }
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (unitLibraryEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(unitLibraryEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (ID == 0) {
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }

    public void getUnitListWithName(String name, final int ID) {
        model.getUnitListWithName(name, ID, new Observer<UnitLibraryEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_UNIT_LIST, d);
            }

            @Override
            public void onNext(UnitLibraryEntity unitLibraryEntity) {
                if (unitLibraryEntity.CODE == 1) {
                    List<UnitLibraryEntity.DATABean> data = unitLibraryEntity.DATA;
                    if (ID == 0 && (data == null || data.size() == 0)) {
                        view.noData();
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getUnitList(unitLibraryEntity.DATA);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (unitLibraryEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(unitLibraryEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (ID == 0) {
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }
}
