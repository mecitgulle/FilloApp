package com.bt.arasholding.filloapp.di.component;

import com.bt.arasholding.filloapp.di.PerActivity;
import com.bt.arasholding.filloapp.di.module.ActivityModule;
import com.bt.arasholding.filloapp.ui.about.AboutFragment;
import com.bt.arasholding.filloapp.ui.barcodeprinter.BarcodePrinterActivity;
import com.bt.arasholding.filloapp.ui.bluetooth.BluetoothFragment;
import com.bt.arasholding.filloapp.ui.camera.CameraFragment;
import com.bt.arasholding.filloapp.ui.cargobarcode.CargoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.delivercargo.DeliverCargoActivity;
import com.bt.arasholding.filloapp.ui.delivermultiplecustomerwaybill.DeliverMultipleCustomerActivity;
import com.bt.arasholding.filloapp.ui.delivery.DeliveryActivity;
import com.bt.arasholding.filloapp.ui.delivery.cargomovement.CargoMovementFragment;
import com.bt.arasholding.filloapp.ui.delivery.compensation.CompensationFragment;
import com.bt.arasholding.filloapp.ui.delivery.multidelivery.MultiDeliveryFragment;
import com.bt.arasholding.filloapp.ui.home.HomeActivity;
import com.bt.arasholding.filloapp.ui.home.decide.DecideDialog;
import com.bt.arasholding.filloapp.ui.lazer.LazerActivity;
import com.bt.arasholding.filloapp.ui.login.LoginActivity;
import com.bt.arasholding.filloapp.ui.nobarcode.NoBarcodeActivity;
import com.bt.arasholding.filloapp.ui.route.ExpeditionRouteActivity;
import com.bt.arasholding.filloapp.ui.route.address.ExpeditionRouteAddressActivity;
import com.bt.arasholding.filloapp.ui.settings.SettingsActivity;
import com.bt.arasholding.filloapp.ui.shipment.ShipmentActivity;
import com.bt.arasholding.filloapp.ui.shipment.info.ShipmentInfoFragment;
import com.bt.arasholding.filloapp.ui.shipment.lazer.ShipmentLazerActivity;
import com.bt.arasholding.filloapp.ui.shipment.search.SearchShipmentFragment;
import com.bt.arasholding.filloapp.ui.shippingoutput.ShippingOutputActivity;
import com.bt.arasholding.filloapp.ui.splash.SplashActivity;
import com.bt.arasholding.filloapp.ui.textbarcode.TextBarcodeActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(SplashActivity splashActivity);

    void inject(ShippingOutputActivity shippingOutputActivity);

    void inject(CargoBarcodeActivity cargoBarcodeActivity);

    void inject(DeliverCargoActivity deliverCargoActivity);

    void inject(DeliveryActivity deliveryActivity);

    void inject(SettingsActivity activity);

    void inject(DecideDialog decideDialog);

    void inject(TextBarcodeActivity textBarcodeActivity);

    void inject(CameraFragment cameraFragment);

    void inject(BluetoothFragment bluetoothFragment);

    void inject(ShipmentActivity shipmentActivity);

    void inject(AboutFragment aboutFragment);

    void inject(ShipmentInfoFragment shipmentInfoFragment);

    void inject(SearchShipmentFragment searchShipmentFragment);

    void inject(BarcodePrinterActivity barcodePrinterActivity);

    void inject(MultiDeliveryFragment multiDeliveryFragment);

    void inject(CompensationFragment compensationFragment);

    void inject(CargoMovementFragment cargoMovementFragment);

    void inject(ExpeditionRouteActivity expeditionRouteActivity);

    void inject(ExpeditionRouteAddressActivity expeditionRouteAddressActivity);

    void inject(LazerActivity lazerActivity);

    void inject(ShipmentLazerActivity shipmentLazerActivity);

    void inject(DeliverMultipleCustomerActivity deliverMultipleCustomerActivity);

    void inject(NoBarcodeActivity noBarcodeActivity);
}