package info.martinmarinov.drivers.usb.cxusb;

import static info.martinmarinov.drivers.usb.silabs.Si2157.Type.SI2157_CHIPTYPE_SI2141;

import android.content.Context;
import android.hardware.usb.UsbDevice;

import info.martinmarinov.drivers.DeviceFilter;
import info.martinmarinov.drivers.DvbException;
import info.martinmarinov.drivers.usb.DvbFrontend;
import info.martinmarinov.drivers.usb.DvbTuner;
import info.martinmarinov.drivers.usb.DvbUsbIds;
import info.martinmarinov.drivers.usb.silabs.Si2157;
import info.martinmarinov.drivers.usb.silabs.Si2168;

public class MygicaT230C2 extends MygicaT230 {
    public final static String MYGICA_NAME = "Mygica T230C2 DVB-T/T2/C";
    final static DeviceFilter MYGICA_T230C2 = new DeviceFilter(DvbUsbIds.USB_VID_CONEXANT, DvbUsbIds.USB_PID_MYGICA_T230C2, MYGICA_NAME);
    final static DeviceFilter MYGICA_T230C2_LITE = new DeviceFilter(DvbUsbIds.USB_VID_CONEXANT, DvbUsbIds.USB_PID_MYGICA_T230C2_LITE, MYGICA_NAME);

    private Si2168 frontend;

    MygicaT230C2(UsbDevice usbDevice, Context context, DeviceFilter deviceFilter) throws DvbException {
        super(usbDevice, context, deviceFilter);
    }

    @Override
    public String getDebugString() {
        return MYGICA_NAME;
    }

    @Override
    protected DvbFrontend frontendAttatch() throws DvbException {
        return frontend = new Si2168(this, resources, i2CAdapter, 0x64, SI2168_TS_PARALLEL, true, SI2168_TS_CLK_MANUAL, false);
    }

    @Override
    protected DvbTuner tunerAttatch() throws DvbException {
        return new Si2157(resources, i2CAdapter, frontend.gateControl(), 0x60, false, SI2157_CHIPTYPE_SI2141);
    }

}
