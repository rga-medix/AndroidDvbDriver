package info.martinmarinov.drivers.usb.cxusb;

import android.content.Context;
import android.hardware.usb.UsbDevice;

import info.martinmarinov.drivers.DeviceFilter;
import info.martinmarinov.drivers.DvbException;
import info.martinmarinov.drivers.tools.SleepUtils;
import info.martinmarinov.drivers.usb.DvbUsbIds;

public class MygicaT230A extends MygicaT230C2 {
    public static final String MYGICA_T230A_NAME = "Mygica T230A DVB-T/T2/C";
    static final DeviceFilter MYGICA_T230A = new DeviceFilter(DvbUsbIds.USB_VID_CONEXANT, DvbUsbIds.USB_PID_MYGICA_T230A, MYGICA_T230A_NAME);

    MygicaT230A(UsbDevice usbDevice, Context context, DeviceFilter deviceFilter) throws DvbException {
        super(usbDevice, context, deviceFilter);
    }

    @Override
    public String getDebugString() {
        return MYGICA_T230A_NAME;
    }

    @Override
    synchronized protected void powerControl(boolean onoff) throws DvbException {
        if (!onoff) {
            cxusb_power_ctrl(false);
            cxusb_streaming_ctrl(false);
            return;
        }

        // dvbsky_identify_state() for USB_PID_MYGICA_T230A
        cxusb_gpio_ctrl(0x87, 0);
        SleepUtils.mdelay(20);
        cxusb_gpio_ctrl(0x86, 1);
        cxusb_gpio_ctrl(0x80, 0);
        SleepUtils.mdelay(100);
        cxusb_gpio_ctrl(0x80, 1);
        SleepUtils.mdelay(50);

        SleepUtils.mdelay(128);
        cxusb_ctrl_msg(CMD_DIGITAL, new byte[0], 0, new byte[1], 1);
        SleepUtils.mdelay(100);

        cxusb_streaming_ctrl(true);
    }
}