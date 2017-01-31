package me.gitai.aboutview;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by gitai on 16-2-19.
 */
public class AboutView extends LinearLayout{
    private ParseAboutXml.About about;
    private int XmlId,logoId;
    private String label,description,version;
    private int version_code;
    private List<ParseAboutXml.Url> homes,issues;
    private ParseAboutXml.Url license;
    private ParseAboutXml.Repository repository;
    private List<View> customViews = new ArrayList<>();
    public AboutView(Context context) {
        super(context, null, 0);
    }
    public AboutView(Context context, int XmlId) {
        super(context, null, 0);
        this.XmlId = XmlId;
    }
    public AboutView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    public AboutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context.obtainStyledAttributes(attrs, R.styleable.AboutView, defStyleAttr, 0));
    }
    @TargetApi(21)
    public AboutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context.obtainStyledAttributes(
                attrs, R.styleable.AboutView, defStyleAttr, defStyleRes));
    }
    private void initAttr(TypedArray ta){
        XmlId = ta.getResourceId(R.styleable.AboutView_xml, R.xml.about);
        label = ta.getString(R.styleable.AboutView_label);
        logoId = ta.getResourceId(R.styleable.AboutView_logo, android.R.drawable.sym_def_app_icon);
        description = ta.getString(R.styleable.AboutView_description);
        version = ta.getString(R.styleable.AboutView_version);
        version_code = ta.getInt(R.styleable.AboutView_version_code, 0);
        license = ParseAboutXml.Url.fromMD(ta.getString(R.styleable.AboutView_license));
        repository = ParseAboutXml.Repository.fromMD(ta.getString(R.styleable.AboutView_repository));
        for (CharSequence item:ta.getTextArray(R.styleable.AboutView_issues)) {
            issues.add(ParseAboutXml.Url.fromMD(String.valueOf(item)));
        }
        for (CharSequence item:ta.getTextArray(R.styleable.AboutView_homes)) {
            homes.add(ParseAboutXml.Url.fromMD(String.valueOf(item)));
        }
    }
    public AboutView initData(){
        if (!Utils.isEmpty(XmlId)){
            try {
                about = ParseAboutXml.Parse(getContext(), XmlId);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        //logoId = about.getLogo(logoId);
        label = about.getLabel(label);
        description = about.getDescription(description);
        version = about.getVersion(version);
        version_code = about.getVersionCode(version_code);
        license = about.getLicense(license);
        repository = about.getRepository(repository);
        issues = about.getIssues(issues);
        homes = about.getHome(homes);
        return this;
    }
    public int getXmlId() {
        return XmlId;
    }
    public AboutView setXmlId(int xmlId) {
        XmlId = xmlId;
        return this;
    }
    public String getTitle(){
        return getLabel();
    }
    public String getLabel() {
        return label;
    }
    public int getLogo() {
        return logoId;
    }
    public AboutView setLogo(int logoId) {
        this.logoId = logoId;
        return this;
    }
    public AboutView setTitle(String label){
        setLabel(label);
        return this;
    }
    public AboutView setLabel(String label) {
        this.label = label;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public AboutView setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getVersion() {
        return version;
    }
    public AboutView setVersion(String version) {
        this.version = version;
        return this;
    }
    public ParseAboutXml.Url getLicense() {
        return license;
    }
    public AboutView setLicense(ParseAboutXml.Url license) {
        this.license = license;
        return this;
    }
    public ParseAboutXml.Repository getRepository() {
        return repository;
    }
    public AboutView setRepository(ParseAboutXml.Repository repository) {
        this.repository = repository;
        return this;
    }
    public int getVersionCode() {
        return version_code;
    }
    public AboutView setVersionCode(int version_code) {
        this.version_code = version_code;
        return this;
    }
    public List<ParseAboutXml.Url> getHomes() {
        return homes;
    }
    public AboutView setHomes(List<ParseAboutXml.Url> homes) {
        this.homes = homes;
        return this;
    }
    public List<ParseAboutXml.Url> getIssues() {
        return issues;
    }
    public AboutView setIssues(List<ParseAboutXml.Url> issues) {
        this.issues = issues;
        return this;
    }
    public AboutView addCustomView(View view){
        customViews.add(view);
        return this;
    }
    public AboutView addCustomMessage(String title, String badge, Spanned content){
        return addCustomMessage(new SpannableString(title), new SpannableString(badge), content);
    }
    public AboutView addCustomMessage(Spanned title, Spannable badge, Spanned content){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.scrollitem, null);
        setText((TextView)view.findViewById(R.id.title), title);
        setText((TextView)view.findViewById(R.id.badge), badge);
        setText((TextView)view.findViewById(R.id.content), content);
        customViews.add(view);
        return this;
    }
    public AboutView build(){
        long start = System.nanoTime();
        if (Utils.isEmpty(about)){
            initData();
        }
        long end = System.currentTimeMillis();
        System.out.println("initData: "+(end-start)+"ms");
        start = end;
        inflate(getContext(), R.layout.layout_about, this);
        if (logoId == 0){
            ((ImageView)findViewById(R.id.logo)).setImageResource(android.R.drawable.sym_def_app_icon);
        }else{
            ((ImageView)findViewById(R.id.logo)).setImageResource(logoId);
        }
        setText(this, R.id.version, String.format(
                "%s(%s)",
                getVersion(),
                getVersionCode()));
        end = System.currentTimeMillis();
        System.out.println("249: "+(end-start)+"ms");
        start = end;
        String tmp_homes = null;
        for(ParseAboutXml.Url url:getHomes()){
            if (!Utils.isEmpty(tmp_homes)){
                tmp_homes = tmp_homes + " | "  + url.getHtml();
            }else{
                tmp_homes =  "<div>" + url.getHtml();
            }
        }
        tmp_homes = tmp_homes + "</div>";
        end = System.currentTimeMillis();
        System.out.println("262: "+(end-start)+"ms");
        start = end;
        TextView des = ((TextView)findViewById(R.id.des));
        des.setText(Html.fromHtml(getDescription() + tmp_homes, null, new BaseCustomTagHandler()));
        des.setMovementMethod(LinkMovementMethod.getInstance());
        end = System.currentTimeMillis();
        System.out.println("269: "+(end-start)+"ms");
        start = end;
        LinearLayout root = ((LinearLayout)this.findViewById(R.id.root));
        for (View view:customViews) {
            root.addView(view);
        }
        end = System.currentTimeMillis();
        System.out.println("277: "+(end-start)+"ms");
        start = end;
        for(ParseAboutXml.ChangeLog log: about.getChangelogs()){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.scrollitem, null);
            setText(view, R.id.title, log.getName());
            setText(view, R.id.badge, String.valueOf(log.getCode()));
            setText(view, R.id.content, log.getContent(getContext()), true);
            root.addView(view);
        }
        end = System.currentTimeMillis();
        System.out.println("289: "+(end-start)+"ms");
        start = end;
        for(ParseAboutXml.Dependencie dep: about.getDependencies()){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.scrollitem, null);
            setText(view, R.id.title, getContext().getString(R.string.bullet) + dep.getTitle(),false);
            setText(view, R.id.badge, dep.getVer());
            setText(view, R.id.content, dep.getLicense().toSpanned(getContext()), true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                TextView content = ((TextView)view.findViewById(R.id.content));
                switch (dep.getAlign()){
                    case "center":
                        content.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        break;
                    case "right":
                        content.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                        break;
                    default:
                        content.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
                        break;
                }
            }
            root.addView(view);
        }
        end = System.currentTimeMillis();
        System.out.println("316: "+(end-start)+"ms");
        start = end;
        return this;
    }
    private void setText(View perent, int id, String text){
        setText(perent, id, text, true);
    }
    private void setText(View perent, int id, String text, boolean html){
        setText(perent, id, text, html, false);
    }
    private void setText(View perent, int id, String text, boolean html, boolean scrolling){
        TextView tv = (TextView)perent.findViewById(id);
        if (Utils.isEmpty(text)){
            tv.setVisibility(View.GONE);
            return;
        }
        if (html) {
            setText(tv, Html.fromHtml(text, null, new BaseCustomTagHandler()), scrolling);
        }else {
            setText(tv, new SpannedString(text), scrolling);
        }
    }
    private void setText(View perent, int id, Spanned text){
        setText(perent, id, text, false);
    }
    private void setText(View perent, int id, Spanned text, boolean scrolling){
        TextView tv = (TextView)perent.findViewById(id);
        setText(tv, text, scrolling);
    }
    private void setText(TextView v, Spanned text){
        setText(v, text, false);
    }
    private void setText(TextView v, Spanned text, boolean scrolling){
        if (v == null){
            return;
        }
        if (Utils.isEmpty(text)){
            v.setVisibility(View.GONE);
        }else{
            v.setText(text);
        }
        v.setMovementMethod(LinkMovementMethod.getInstance());
        v.setHorizontallyScrolling(scrolling);
    }
    
}
