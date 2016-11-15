package Extractors.General;


import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;
import Dtos.Element;
import Extractors.Common.ExtractorBase;

public class GeneralDataExtractor extends ExtractorBase{

    private final String TelephonyInfo = "TelephonyInfo";
    private final String TelephonyConfiguration = "TelephonyConfiguration";

    private final String LocaleIso3Country = "LocaleIso3Country";
    private final String LocaleIso3Language = "LocaleIso3Language";

    private final String TimeZoneId = "TimeZoneId";

    private final TelephonyManager _telephonyManager;
    private final Configuration _configurationManager;

    @Override
    protected String GetDataSourceNameValue() {
        return "GeneralData";
    }

    @Override
    public Collection<Element> ExtractInternal() {
        Collection<Element> elements = new ArrayList<Element>();
        elements.add(GetTelephonyInfo());
        elements.add(GetTelephonyConfiguration());

        return elements;
    }

    public GeneralDataExtractor(TelephonyManager telephonyManager, Configuration configurationManager)
    {
        _telephonyManager = telephonyManager;
        _configurationManager = configurationManager;
    }

    private Element GetTelephonyInfo()
    {
        Element telephonyInfo = new Element(TelephonyInfo);

        telephonyInfo.AddChild("DeviceId", _telephonyManager.getDeviceId());
        telephonyInfo.AddChild("NetworkOperatorName", _telephonyManager.getNetworkOperatorName());
        telephonyInfo.AddChild("SimCountryIso", _telephonyManager.getSimCountryIso());
        telephonyInfo.AddChild("NetworkOperator", _telephonyManager.getNetworkOperator());
        telephonyInfo.AddChild("PhoneType", Integer.toString(_telephonyManager.getPhoneType()));

        return telephonyInfo;
    }

    private Element GetTelephonyConfiguration()
    {
        Element telephonyConfig = new Element(TelephonyConfiguration);

        telephonyConfig.AddChild("Touchscreen", Integer.toString(_configurationManager.touchscreen));
        telephonyConfig.AddChild("Navigation", Integer.toString(_configurationManager.navigation));
        telephonyConfig.AddChild("ScreenLayout", Integer.toString(_configurationManager.screenLayout));
        telephonyConfig.AddChild("Mcc", Integer.toString(_configurationManager.mcc));
        telephonyConfig.AddChild("Mnc", Integer.toString(_configurationManager.mnc));
        telephonyConfig.AddChild("Keyboard", Integer.toString(_configurationManager.keyboard));
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
        {
            telephonyConfig.AddChild("LocaleIso3Language", _configurationManager.locale.getISO3Language());
            telephonyConfig.AddChild("LocaleIso3Country", _configurationManager.locale.getISO3Country());

        }else{
            telephonyConfig.AddChild("LocaleIso3Language", _configurationManager.getLocales().get(0).getISO3Language());
        }

        telephonyConfig.AddChild("TimeZoneId", TimeZone.getDefault().getID());

        return telephonyConfig;
    }

}
