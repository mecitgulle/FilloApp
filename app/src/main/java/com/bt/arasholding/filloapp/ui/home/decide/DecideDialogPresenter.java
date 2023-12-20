package com.bt.arasholding.filloapp.ui.home.decide;

import com.bt.arasholding.filloapp.data.DataManager;
import com.bt.arasholding.filloapp.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DecideDialogPresenter<V extends DecideDialogMvpView> extends BasePresenter<V> implements DecideDialogMvpPresenter<V> {

    private static final String TAG ="DecideDialogPresenter";

    @Inject
    public DecideDialogPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}