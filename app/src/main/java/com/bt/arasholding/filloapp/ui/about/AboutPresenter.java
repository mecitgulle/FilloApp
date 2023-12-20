package com.bt.arasholding.filloapp.ui.about;

import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AboutPresenter<V extends AboutMvpView> extends BasePresenter<V> implements AboutMvpPresenter<V> {

    @Inject
    public AboutPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}