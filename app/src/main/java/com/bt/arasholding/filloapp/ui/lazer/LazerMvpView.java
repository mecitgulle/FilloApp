package com.bt.arasholding.filloapp.ui.lazer;

import com.bt.arasholding.filloapp.ui.base.MvpView;

public interface LazerMvpView extends MvpView {
    void updateSayac();
    void updateToolbarTitle();
    void showTokenExpired();
    void focusText();
}
