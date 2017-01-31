package me.gitai.aboutview;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Collection;
/**
 * Created by gitai on 16-2-19.
 */
public class Utils {
    public static boolean contains(String[] strs, String str) {
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equals(str)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.size() == 0;
    }
    public static boolean isEmpty(int _int) {
        return _int == 0;
    }
    public static boolean isEmpty(Object about) {
        return about == null;
    }
    public static PackageInfo getCurrentPackageInfo(final Context context) {
        final PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
