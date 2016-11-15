package Extractors;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Dtos.Element;
import Extractors.Common.ExtractorBase;

public class ApplicationExtractor extends ExtractorBase {

    private PackageManager _packageManager;

    public ApplicationExtractor(PackageManager packageManager) {
        _packageManager = packageManager;
    }


    @Override
    protected String GetDataSourceNameValue() {
        return "Application";
    }

    @Override
    public Collection<Element> ExtractInternal() {
        final int metaDataFilter = PackageManager.GET_META_DATA;
        List<ApplicationInfo> apps = _packageManager.getInstalledApplications(metaDataFilter);
        Collection<Element> elements = new ArrayList<Element>();
        for (ApplicationInfo app : apps) {
            PackageInfo packageInfo = null;
            try {
                packageInfo = _packageManager.getPackageInfo(app.packageName, metaDataFilter);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Element item = new Element("ApplicationName", app.loadLabel(_packageManager).toString());

            item.AddChild("PackageName", packageInfo.packageName);
            item.AddChild("FirstInstallTime", Long.toString(packageInfo.firstInstallTime));
            item.AddChild("LastUpdateTime", Long.toString(packageInfo.lastUpdateTime));
            item.AddChild("VersionName", packageInfo.versionName);
            item.AddChild("VersionCode", Integer.toString(packageInfo.versionCode));
            item.AddChild("Flags", Integer.toString(app.flags));

            elements.add(item);
        }

        return elements;

    }
}
