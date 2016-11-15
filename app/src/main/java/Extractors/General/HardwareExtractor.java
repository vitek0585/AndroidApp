package Extractors.General;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import Dtos.Element;
import Extensions.Delegate;
import Extensions.StringExtension;
import Extractors.Common.ExtractorBase;

public class HardwareExtractor extends ExtractorBase {

    private final String PathToCpuFrequency = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
    private final String PathToCpuInfo = "/proc/cpuinfo";
    private final String SecodaryStorage = "SECONDARY_STORAGE";

    private final String Device = "Device";
    private final String DisplayName = "Display";
    private final String DisplaySizeWeight = "DisplaySizeWeight";
    private final String DisplaySizeHeight = "DisplaySizeHeight";
    private final String PhysicalSize = "PhysicalSize";
    private final String Ram = "Ram";
    private final String RamTotalSize = "RamTotalSize";
    private final String Battery = "Battery";
    private final String Storage = "Storage";
    private final String MainStorage = "MainStorage";
    private final String ExternalStorage = "ExternalStorage";
    private final String Cpu = "Cpu";
    private final String CpuName = "CpuName";
    private final String CoreCount = "CoreCount";
    private final String CpuFrequency = "CpuFrequency";
    private final String CameraName = "CameraName";
    private final String CameraCount = "CameraCount";
    private final String CameraFacing = "CameraFacing";
    private final String VideoSizesSupported = "VideoSizesSupported";
    private final String PictureSizesSupported = "PictureSizesSupported";
    private final String CameraResolution = "CameraResolution";
    private final String Features = "Features";
    private final String AvailableFeatures = "AvailableFeatures";

    private WindowManager _windowManager;
    private ActivityManager _activityService;
    private Context _applicationContext;
    private PackageManager _packageManager;

    public HardwareExtractor(WindowManager windowManager, ActivityManager activityService, Context applicationContext) {
        _windowManager = windowManager;
        _activityService = activityService;
        _applicationContext = applicationContext;
        _packageManager = applicationContext.getPackageManager();
    }

    @Override
    protected String GetDataSourceNameValue() {
        return "Hardware";
    }

    @Override
    public Collection<Element> ExtractInternal() {
        ArrayList<Element> elements = new ArrayList<Element>();
        elements.add(GetDeviceInfo());
        elements.add(GetDisplayInfo());
        elements.add(GetRamInfo());
        elements.add(GetBatteryInfo());
        elements.add(GetStorageInfo());

        try {
            Element element = GetCpuInfo();
            elements.add(element);
        } catch (IOException ex) {
            elements.add(new Element(Cpu, ex));
        }

        elements.add(GetCameraInfo());
        elements.add(GetFeatures());

        return elements;
    }

    private Element GetDeviceInfo() {
        Element deviceInfo = new Element(Device);
        deviceInfo.AddChild("Brand", Build.BRAND);
        deviceInfo.AddChild("Model", Build.MODEL);
        deviceInfo.AddChild("Release", Build.VERSION.RELEASE);
        deviceInfo.AddChild("Product", Build.PRODUCT);

        return deviceInfo;
    }

    private Element GetDisplayInfo() {
        Element displayInfo = new Element(DisplayName);
        try {
            Display defaultDisplay = _windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);

            displayInfo.AddChild("Density", Float.toString(displayMetrics.density));
            displayInfo.AddChild("Xdpi", Float.toString(displayMetrics.xdpi));
            displayInfo.AddChild("Ydpi", Float.toString(displayMetrics.ydpi));
            double physicalWidth = Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2);
            double physicalHeight = Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2);
            displayInfo.AddChild(PhysicalSize, Double.toString(Math.sqrt(physicalWidth + physicalHeight)));
            displayInfo.AddChild(DisplaySizeWeight, Integer.toString(displayMetrics.widthPixels));
            displayInfo.AddChild(DisplaySizeHeight, Integer.toString(displayMetrics.heightPixels));
        } catch (Exception e) {
            displayInfo.AddError(DisplayName, e);
        }

        return displayInfo;
    }

    private Element GetRamInfo() {
        Element ramInfo = new Element(Ram);
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            _activityService.getMemoryInfo(memoryInfo);
            ramInfo.AddChild(RamTotalSize, Double.toString(ConvertBytesToMb(memoryInfo.totalMem)));
        } catch (Exception e) {
            ramInfo.AddError(RamTotalSize, e);
        }

        return ramInfo;
    }

    private Element GetBatteryInfo() {
        Element batteryInfo = new Element(Battery);
        try {
            int defaultValue = -1;
            IntentFilter batteryIntent = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = _applicationContext.registerReceiver(null, batteryIntent);
            batteryInfo.AddChild(BatteryManager.EXTRA_TECHNOLOGY, Integer.toString(batteryStatus.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, defaultValue)));
            return batteryInfo;
        } catch (Exception e) {
            batteryInfo.AddError(Battery, e);
        }

        return batteryInfo;
    }

    private Element GetStorageInfo() {
        Element storageInfo = new Element(Storage);

        try {
            storageInfo.AddChilds(CalculateStorage(MainStorage, Environment.getExternalStorageDirectory().getAbsolutePath()));
        } catch (Exception ex) {
            storageInfo.AddError(MainStorage, ex);
        }

        try {
            storageInfo.AddChilds(CalculateStorage(ExternalStorage, System.getenv(SecodaryStorage)));
        } catch (Exception ex) {
            storageInfo.AddError(ExternalStorage, ex);
        }

        return storageInfo;
    }

    private ArrayList<Element> CalculateStorage(String storageName, String storagePath) {

        long totalMemory = 0;
        long freeMemory = 0;

        ArrayList<Element> elements = new ArrayList<Element>();
        StatFs stat = null;
        if (storagePath != null) {
            stat = new StatFs(storagePath);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                totalMemory = stat.getBlockCount() * stat.getBlockSize();
                freeMemory = stat.getAvailableBlocks() * stat.getBlockSize();
            } else {
                totalMemory = stat.getBlockCountLong() * stat.getBlockSizeLong();
                freeMemory = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            }
        }

        elements.add(new Element(String.format("%sTotal", storageName), Double.toString(ConvertBytesToMb(totalMemory))));
        elements.add(new Element(String.format("%sFree", storageName), Double.toString(ConvertBytesToMb(freeMemory))));
        return elements;
    }

    private Element GetCpuInfo() throws IOException {
        int oneMhz = 1000;
        Element cpuInfo = new Element(Cpu);
        cpuInfo.AddChild(CoreCount, new Delegate() {
            @Override
            public String GetResult() {
                return Integer.toString(Runtime.getRuntime().availableProcessors());
            }
        });

        String cpuAbi;
        String cpuAbi2;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            cpuAbi = Build.CPU_ABI;
            cpuAbi2 = Build.CPU_ABI2;
        } else {
            cpuAbi = Build.SUPPORTED_ABIS.length > 0 ? Build.SUPPORTED_ABIS[0] : StringExtension.Empty();
            cpuAbi2 = Build.SUPPORTED_ABIS.length > 1 ? Build.SUPPORTED_ABIS[1] : StringExtension.Empty();
        }
        cpuInfo.AddChild("CpuAbi", cpuAbi);
        cpuInfo.AddChild("CpuAbi2", cpuAbi2);
        RandomAccessFile reader = null;
        try {

            reader = new RandomAccessFile(PathToCpuInfo, "r");
            String cpuInfoString = reader.readLine();
            String cpuName = StringExtension.Empty();
            String[] arrayCpuInfo = cpuInfoString.split(":");
            if (arrayCpuInfo.length > 1) {
                int cpuNameIndex = 1;
                cpuName = arrayCpuInfo[cpuNameIndex];
            }

            cpuInfo.AddChild(CpuName, cpuName);

        } catch (FileNotFoundException ex) {
            cpuInfo.AddError(CpuName, ex);
        } catch (IOException ex) {
            cpuInfo.AddError(CpuName, ex);
        } catch (Exception ex) {
            cpuInfo.AddError(CpuName, ex);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        try {

            reader = new RandomAccessFile(PathToCpuFrequency, "r");
            int totalMhz = Integer.parseInt(reader.readLine()) / oneMhz;
            cpuInfo.AddChild(CpuFrequency, Integer.toString(totalMhz));

        } catch (FileNotFoundException ex) {
            cpuInfo.AddError(CpuFrequency, ex);
        } catch (IOException ex) {
            cpuInfo.AddError(CpuFrequency, ex);
        } catch (Exception ex) {
            cpuInfo.AddError(CpuFrequency, ex);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return cpuInfo;
    }

    private Element GetCameraInfo() {
        Element cameraInfoRoot = new Element(CameraName);
        int cameraCount;
        cameraCount = android.hardware.Camera.getNumberOfCameras();

        cameraInfoRoot.AddChild(CameraCount, Integer.toString(cameraCount));

        for (int currentIndex = 0; currentIndex < cameraCount; currentIndex++) {
            Camera availableCamera = null;
            try {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                android.hardware.Camera.getCameraInfo(currentIndex, cameraInfo);

                Element cameraElement = new Element(CameraFacing, Integer.toString(cameraInfo.facing));
                availableCamera = android.hardware.Camera.open(currentIndex);
                Camera.Parameters parameters = availableCamera.getParameters();

                cameraElement.AddChild(VideoSizesSupported,
                        parameters.getSupportedVideoSizes() != null
                                ? TransformSizesToString(parameters.getSupportedVideoSizes())
                                : TransformSizesToString(parameters.getSupportedPreviewSizes()));

                List<Camera.Size> pictureSizesSupported = parameters.getSupportedPictureSizes();
                cameraElement.AddChild(PictureSizesSupported, pictureSizesSupported != null
                        ? TransformSizesToString(pictureSizesSupported)
                        : StringExtension.Empty());
                cameraElement.AddChild(CameraResolution, Double.toString(GetCameraResolution(pictureSizesSupported)));
                cameraInfoRoot.AddChild(cameraElement);
            } catch (Exception e) {
                cameraInfoRoot.AddError(CameraFacing, e);
            } finally {
                if (availableCamera != null) {
                    availableCamera.release();
                }
            }
        }

        return cameraInfoRoot;
    }

    private double GetCameraResolution(List<Camera.Size> sizes) {
        double maxPixels = 0;
        double oneMegapixel = 1024000;
        for (Camera.Size size : sizes) {
            if (size.width * size.height > maxPixels) {
                maxPixels = size.width * size.height;
            }
        }

        return maxPixels / oneMegapixel;
    }


    private Element GetFeatures() {
        Element featuresInfo = new Element(Features);
        try {
            FeatureInfo[] features = _packageManager.getSystemAvailableFeatures();
            featuresInfo.AddChild(AvailableFeatures, TransformFeaturesToString(Arrays.asList(features)));
            return featuresInfo;
        } catch (Exception e) {
            featuresInfo.AddError(Features, e);
        }

        return featuresInfo;
    }

    public double ConvertBytesToMb(long bytes) {
        return bytes / 1024f / 1024f;
    }

    private String TransformFeaturesToString(List<FeatureInfo> features) {
        List<String> items = new ArrayList<String>();
        for (FeatureInfo featureInfo : features) {
            items.add(featureInfo.name);
        }
        return ConvertArrayToString(items);
    }

    public String TransformSizesToString(List<Camera.Size> sizes) {
        List<String> items = new ArrayList<String>();
        for (Camera.Size size : sizes) {
            items.add(String.format("%1sX%2s", size.width, size.height));
        }

        return ConvertArrayToString(items);
    }

    private String ConvertArrayToString(List<String> list) {
        return TextUtils.join(";", list);
    }
}
