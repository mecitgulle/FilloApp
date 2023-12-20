package com.bt.arasholding.filloapp.ui.home.decide;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.di.component.ActivityComponent;
import com.bt.arasholding.filloapp.ui.base.BaseDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DecideDialog extends BaseDialog implements DecideDialogMvpView {

    private static final String TAG = "DecideDialog";

    @Inject
    DecideDialogMvpPresenter<DecideDialogMvpView> mPresenter;
    Unbinder unbinder;

    public static DecideDialog newInstance() {
        DecideDialog fragment = new DecideDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setUp(View view) {

    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_select_device, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            mPresenter.onAttach(this);
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
        unbinder.unbind();
    }
}