package sk.svb.servocontroller.UsbDevice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

import sk.svb.servocontroller.service.UsbDeviceService;


/**
 * Created by mbodis on 2/12/16.
 */
public class ServoController {

    public static final String TAG = ServoController.class.getName();

    private static final int MY_USB_VENDOR_ID = 4616;
    private static final int MY_USB_PRODUCT_ID3 = 2069;

    // speed range 100 - 9999
    public static final int SPEED_NORMAL = 1500;
    public static final int SPEED_FASTEST = 100;
    public static final int SPEED_SLOWEST = 9000;

    // limits
    public static final int LEFT_LIMIT = 100;
    public static final int RIGHT_LIMIT = 2600;

    // rotation range  100 - 2600
    public static int LOCATION_CENTER = 1500; // use as center default



    /**
     * send direct position to servo
     * @param ctx
     * @param channel [1 - 16]
     * @param servoLocation 100 - 2600
     * @param servoSpeed
     */
    public static void sendMessage(Context ctx, int channel, int servoLocation, int servoSpeed){
        String msg = "#"+channel+"P"+servoLocation+"T"+servoSpeed+"\r\n";

        Intent mIntent = new Intent(UsbDeviceService.SEND_DATA_INTENT);
        mIntent.putExtra(UsbDeviceService.DATA_EXTRA, msg.getBytes());
        ctx.sendBroadcast(mIntent);
    }

    public static void findDeviceAndStartService(Context ctx) {
        UsbManager usbManager = (UsbManager) ctx.getSystemService(Context.USB_SERVICE);
        UsbDevice usbDevice = null;
        HashMap<String, UsbDevice> usbDeviceList = usbManager.getDeviceList();
        Log.d(TAG, "length: " + usbDeviceList.size());
        Iterator<UsbDevice> deviceIterator = usbDeviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            UsbDevice tempUsbDevice = deviceIterator.next();

            // Print device information. If you think your device should be able
            // to communicate with this app, add it to accepted products below.
            Log.d(TAG, "VendorId: " + tempUsbDevice.getVendorId());
            Log.d(TAG, "ProductId: " + tempUsbDevice.getProductId());
            Log.d(TAG, "DeviceName: " + tempUsbDevice.getDeviceName());
            Log.d(TAG, "DeviceId: " + tempUsbDevice.getDeviceId());
            Log.d(TAG, "DeviceClass: " + tempUsbDevice.getDeviceClass());
            Log.d(TAG, "DeviceSubclass: " + tempUsbDevice.getDeviceSubclass());
            Log.d(TAG, "InterfaceCount: " + tempUsbDevice.getInterfaceCount());
            Log.d(TAG, "DeviceProtocol: " + tempUsbDevice.getDeviceProtocol());

            if (tempUsbDevice.getVendorId() == MY_USB_VENDOR_ID){
                Log.i(TAG, "My device found!");

                if (tempUsbDevice.getProductId() == MY_USB_PRODUCT_ID3) {
                    Toast.makeText(ctx, "My usb device found", Toast.LENGTH_SHORT).show();
                    usbDevice = tempUsbDevice;
                }
            }
        }

        if (usbDevice == null) {
            Log.i(TAG, "No device found!");
            Toast.makeText(ctx, "no_device_found", Toast.LENGTH_LONG).show();
        } else {
            Log.i(TAG, "Device found!");
            Intent startIntent = new Intent(ctx, UsbDeviceService.class);
            PendingIntent pendingIntent = PendingIntent.getService(ctx, 0, startIntent, 0);
            usbManager.requestPermission(usbDevice, pendingIntent);
        }
    }

}
