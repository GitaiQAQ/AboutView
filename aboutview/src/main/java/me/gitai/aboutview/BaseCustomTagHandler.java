package me.gitai.aboutview;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import org.xml.sax.XMLReader;
import java.util.Map;
/**
 * Created by gitai on 16-2-28.
 */
public class BaseCustomTagHandler extends BaseAttrTagHandler{
    @Override
    public void handleTag(boolean opening, String tag, Editable output, Map<String, String> attrs, XMLReader xmlReader) {
        switch (tag){
            case "li":
                if (opening){
                    startLi(output, xmlReader);
                }else{
                    endLi(output, xmlReader);
                }
                break;
            case "code":
                if (opening){
                    startCode(output, xmlReader);
                }else{
                    endCode(output, xmlReader);
                }
                break;
            case "ul":
                if (opening){
                    startUl(output, xmlReader);
                }else{
                    endUl(output, xmlReader);
                }
                break;
            default:
        }
    }
    String ui_indent = "";
    private void startUl(Editable output, XMLReader xmlReader) {
        ui_indent = ui_indent + "\t";
    }
    private void endUl(Editable output, XMLReader xmlReader) {
        ui_indent = ui_indent.substring(1);
    }
    private void startLi(Editable output, XMLReader xmlReader) {
        if (lastChar != null && !lastChar.endsWith("\n")) output.append("\n");
        output.append(ui_indent);
        if (ui_indent.length()%2 == 0){
            output.append("\u25E6 ");
        }else{
            output.append("\u2022 ");
        }
    }
    private void endLi(Editable output, XMLReader xmlReader) {
        if (lastChar != null && !lastChar.endsWith("\n")) output.append("\n");
    }
    int code_start;
    private void startCode(Editable output, XMLReader xmlReader) {
        //output.append(" ");
        code_start = output.length();
        output.append(" ");
    }
    private void endCode(Editable output, XMLReader xmlReader) {
        output.append(" ");
        int code_end = output.length();
        output.setSpan(new ForegroundColorSpan(Color.parseColor("#f44336")), code_start, code_end, Spannable.SPAN_MARK_MARK);
        output.setSpan(new BackgroundColorSpan(Color.parseColor("#ffebee")), code_start, code_end, Spannable.SPAN_MARK_MARK);
        //output.append(" ");
    }
}