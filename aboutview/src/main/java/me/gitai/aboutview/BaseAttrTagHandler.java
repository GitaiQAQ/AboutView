package me.gitai.aboutview;
import android.text.Editable;
import android.text.Html;
import org.xml.sax.XMLReader;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Created by gitai on 16-2-19.
 */
public abstract class BaseAttrTagHandler implements Html.TagHandler{
    protected String lastChar = null;
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        Map<String, String> attrs = getAttributes(xmlReader);
        lastChar = output.toString();
        handleTag(opening, tag, output, attrs, xmlReader);
    }
    protected abstract void handleTag(boolean opening, String tag, Editable output, Map<String, String> attrs, XMLReader xmlReader);
    private Map<String, String> getAttributes(XMLReader xmlReader) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            if (element == null){
                return null;
            }
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[])dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer) lengthField.get(atts);
            Map<String,String> attributes = new LinkedHashMap<>();
            for (int i = 0; i < len; i++) {
                attributes.put(data[i*5+1], data[i *5 +4]);
            }
            return attributes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
