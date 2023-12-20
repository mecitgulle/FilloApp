package com.bt.arasholding.filloapp.data.network;

import com.bt.arasholding.filloapp.BuildConfig;

public final class ApiEndPoint {

    public static final String ENDPOINT_SERVER_LOGIN = BuildConfig.BASE_URL
            + "/Login";
    public static final String ENDPOINT_TRACKING= BuildConfig.BASE_URL
            + "/GetExpedition";
    public static final String ENDPOINT_SHIPPING_OUTPUT= BuildConfig.BASE_URL
            + "/VehicleExit";
    public static final String ENDPOINT_CARGO_DETAIL= BuildConfig.BASE_URL
            + "/CargoDetail";
    public static final String ENDPOINT_MULTICARGO_DETAIL= BuildConfig.BASE_URL
            + "/DeliverMultipleInfo";
    public static final String ENDPOINT_DELIVER_CARGO= BuildConfig.BASE_URL
            + "/DeliverCargo";
    public static final String ENDPOINT_MULTICARGO_DELIVERED = BuildConfig.BASE_URL
            + "/DeliverMultipleCargo";
    public static final String ENDPOINT_CARGO_MOVEMENT = BuildConfig.BASE_URL
            + "/CargoAction";
    public static final String ENDPOINT_CARGO_MOVEMENT_DETAIL = BuildConfig.BASE_URL
            + "/CargoActionsDetail";
    public static final String ENDPOINT_MULTI_DELIVER_CARGO = BuildConfig.BASE_URL
            + "/MultiDeliverCargo";
    public static final String ENDPOINT_CARGO_BARCODE_TEXT = BuildConfig.BASE_URL
            + "/GetCargoBarcodeText";
    public static final String ENDPOINT_DELIVER_CARGO_IMAGE_UPLOAD = BuildConfig.BASE_URL
            + "/DeliverCargoImageUpload";
    public static final String ENDPOINT_UNDELIVERABLE_REASON = BuildConfig.BASE_URL
            + "/GetAtfUndeliverableReason";
    public static final String ENDPOINT_COMPENSATION_REASON = BuildConfig.BASE_URL
            + "/GetAtfCompensationReason";
    public static final String ENDPOINT_EXPEDITION_ROUTE = BuildConfig.BASE_URL
            + "/GetExpeditionRoute";
    public static final String ENDPOINT_EXPEDITION_ROUTEADDRESS = BuildConfig.BASE_URL
            + "/GetExpeditionRouteAddress";
    public static final String ENDPOINT_COMPENSATION_CREATE = BuildConfig.BASE_URL
            + "/CompensationCreate";
    public static final String ENDPOINT_COMPENSATION_IMAGE_UPLOAD = BuildConfig.BASE_URL
            + "/CompensationImageUpload";
    public static final String ENDPOINT_CUSTOMER_LIST = BuildConfig.BASE_URL
            + "/GetArMusteriListFilter";
    public static final String ENDPOINT_ALL_CUSTOMER_LIST = BuildConfig.BASE_URL
            + "/GetArMusteriList";
    public static final String ENDPOINT_NO_BARCODE_CARGO_LIST = BuildConfig.BASE_URL
            + "/GetNoBarcodeList";
    public static final String ENDPOINT_NO_BARCODE_ADD = BuildConfig.BASE_URL
            + "/NoBarcodeCargoCreate";
    public static final String ENDPOINT_NO_BARCODE_ATFNO_ADD = BuildConfig.BASE_URL
            + "/NoBarcodeCargoCreate";
    public static final String ENDPOINT_CUSTOMER_DELIVER_CARGO = BuildConfig.BASE_URL
            + "/DeliverMultipleCustomerWayBill";
//            + "/CompensationImageUpload";
}
