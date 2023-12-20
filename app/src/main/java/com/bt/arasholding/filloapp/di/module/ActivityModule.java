package com.bt.arasholding.filloapp.di.module;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import com.bt.arasholding.filloapp.di.ActivityContext;
import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.ui.about.AboutMvpPresenter;
import com.bt.arasholding.filloapp.ui.about.AboutMvpView;
import com.bt.arasholding.filloapp.ui.about.AboutPresenter;
import com.bt.arasholding.filloapp.ui.barcodeprinter.BarcodePrinterMvpPresenter;
import com.bt.arasholding.filloapp.ui.barcodeprinter.BarcodePrinterMvpView;
import com.bt.arasholding.filloapp.ui.barcodeprinter.BarcodePrinterPresenter;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragmentMvpPresenter;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragmentMvpView;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragmentPresenter;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpPresenter;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeMvpView;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodePresenter;
import com.bt.arasholding.filloapp.ui.delivery.cargomovement.CargoMovementMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.cargomovement.CargoMovementMvpView;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoMvpView;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoPresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryMvpView;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryPresenter;
import com.bt.arasholding.filloapp.ui.delivery.cargomovement.CargoMovementPresenter;
import com.bt.arasholding.filloapp.ui.delivery.compensation.CompensationMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.compensation.CompensationMvpView;
import com.bt.arasholding.filloapp.ui.delivery.compensation.CompensationPresenter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpPresenter;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryMvpView;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryPresenter;
import com.bt.arasholding.filloapp.ui.home.HomeMvpPresenter;
import com.bt.arasholding.filloapp.ui.home.HomeMvpView;
import com.bt.arasholding.filloapp.ui.home.HomePresenter;
import com.bt.arasholding.filloapp.ui.home.decide.DecideDialogMvpPresenter;
import com.bt.arasholding.filloapp.ui.home.decide.DecideDialogMvpView;
import com.bt.arasholding.filloapp.ui.home.decide.DecideDialogPresenter;
import com.bt.arasholding.filloapp.ui.lazer.LazerMvpPresenter;
import com.bt.arasholding.filloapp.ui.lazer.LazerMvpView;
import com.bt.arasholding.filloapp.ui.lazer.LazerPresenter;
import com.bt.arasholding.filloapp.ui.login.LoginMvpPresenter;
import com.bt.arasholding.filloapp.ui.login.LoginMvpView;
import com.bt.arasholding.filloapp.ui.login.LoginPresenter;
import com.bt.arasholding.filloapp.ui.nobarcode.NoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.nobarcode.NoBarcodeMvpPresenter;
import com.bt.arasholding.filloapp.ui.nobarcode.NoBarcodeMvpView;
import com.bt.arasholding.filloapp.ui.nobarcode.NoBarcodePresenter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpPresenter;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteMvpView;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRoutePresenter;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressMvpPresenter;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressMvpView;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressPresenter;
import com.bt.arasholding.filloapp.ui.settings.SettingsMvpPresenter;
import com.bt.arasholding.filloapp.ui.settings.SettingsMvpView;
import com.bt.arasholding.filloapp.ui.settings.SettingsPresenter;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentMvpPresenter;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentMvpView;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentPresenter;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerMvpPresenter;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerMvpView;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerPresenter;
import com.bt.arasholding.filloapp.ui.shippingoutput.ShippingOutputMvpPresenter;
import com.bt.arasholding.filloapp.ui.shippingoutput.ShippingOutputMvpView;
import com.bt.arasholding.filloapp.ui.shippingoutput.ShippingOutputPresenter;
import com.bt.arasholding.filloapp.ui.splash.SplashMvpPresenter;
import com.bt.arasholding.filloapp.ui.splash.SplashMvpView;
import com.bt.arasholding.filloapp.ui.splash.SplashPresenter;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodeMvpPresenter;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodeMvpView;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodePresenter;
import com.bt.arasholding.filloapp.utils.BeepManager;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    MultiDeliveryMvpPresenter<MultiDeliveryMvpView> provideMultiDeliveryMvpPresenter(MultiDeliveryPresenter<MultiDeliveryMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    CompensationMvpPresenter<CompensationMvpView> provideCompensationMvpPresenter(CompensationPresenter<CompensationMvpView> presenter){
        return presenter;
    }
    @Provides
    @PerActivity
    BluetoothFragmentMvpPresenter<BluetoothFragmentMvpView> provideBluetoothFragmentPresenter(BluetoothFragmentPresenter<BluetoothFragmentMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    AboutMvpPresenter<AboutMvpView> provideAboutPresenter(AboutPresenter<AboutMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    BarcodePrinterMvpPresenter<BarcodePrinterMvpView> provideBarcodePrinterPresenter(BarcodePrinterPresenter<BarcodePrinterMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    ShipmentMvpPresenter<ShipmentMvpView> provideShipmentPresenter(ShipmentPresenter<ShipmentMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    CargoMovementMvpPresenter<CargoMovementMvpView> provideCargoMovementPresenter(CargoMovementPresenter<CargoMovementMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    TextBarcodeMvpPresenter<TextBarcodeMvpView> provideTextBarcodePresenter(TextBarcodePresenter<TextBarcodeMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    DecideDialogMvpPresenter<DecideDialogMvpView> provideDecideDialogPresenter(DecideDialogPresenter<DecideDialogMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    SettingsMvpPresenter<SettingsMvpView> provideSettingsPresenter(SettingsPresenter<SettingsMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomePresenter(HomePresenter<HomeMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShippingOutputMvpPresenter<ShippingOutputMvpView> provideShippingOutputPresenter(
            ShippingOutputPresenter<ShippingOutputMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CargoBarcodeMvpPresenter<CargoBarcodeMvpView> provideShippingOutputMvpPresenter(
            CargoBarcodePresenter<CargoBarcodeMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    DeliverCargoMvpPresenter<DeliverCargoMvpView> provideDeliverCargoMvpPresenter(
            DeliverCargoPresenter<DeliverCargoMvpView> presnter){
        return presnter;
    }

    @Provides
    @PerActivity
    DeliveryMvpPresenter<DeliveryMvpView> provideDeliveryMvpPresenter(
            DeliveryPresenter<DeliveryMvpView> presnter){
        return presnter;
    }

    @Provides
    @PerActivity
    ExpeditionRouteMvpPresenter<ExpeditionRouteMvpView> provideExpeditionRouteMvpPresenter(
            ExpeditionRoutePresenter<ExpeditionRouteMvpView> presnter){
        return presnter;
    }

    @Provides
    @PerActivity
    ExpeditionRouteAddressMvpPresenter<ExpeditionRouteAddressMvpView> provideExpeditionAddressRouteMvpPresenter(
            ExpeditionRouteAddressPresenter<ExpeditionRouteAddressMvpView> presnter){
        return presnter;
    }

    @Provides
    @PerActivity
    LazerMvpPresenter<LazerMvpView> provideLazerMvpPresenter(
            LazerPresenter<LazerMvpView> presnter){
        return presnter;
    }

    @Provides
    @PerActivity
    ShipmentLazerMvpPresenter<ShipmentLazerMvpView> provideShipmentLazerMvpPresenter(
            ShipmentLazerPresenter<ShipmentLazerMvpView> presnter){
        return presnter;
    }

    @Provides
    @PerActivity
    NoBarcodeMvpPresenter<NoBarcodeMvpView> provideNoBarcodeMvpPresenter(
            NoBarcodePresenter<NoBarcodeMvpView> presnter){
        return presnter;
    }

    @Provides
    BeepManager provideBeepManager(){
        return new BeepManager(mActivity);
    }
}
