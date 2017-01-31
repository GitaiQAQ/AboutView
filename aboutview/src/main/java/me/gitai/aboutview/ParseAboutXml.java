package me.gitai.aboutview;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import com.github.rjeschke.txtmark.Processor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * Created by gitai on 16-2-5.
 */
public class ParseAboutXml {
    private static final String S = "s";
    private static final String TAG_ROOT = "about";
    private static final String TAG_LABEL = "label";
    private static final String TAG_ICON = "logo";
    private static final String TAG_VERSION = "ver";
    private static final String TAG_CODE = "code";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_LICENSE = "license";
    private static final String TAG_REPOSITORY = "repository";
    private static final String TAG_HOME = "home";
    private static final String TAG_HOMES = TAG_HOME + S;
    private static final String TAG_ISSUE = "issue";
    private static final String TAG_ISSUES = TAG_ISSUE + S;
    private static final String TAG_AUTHOR = "dever";
    private static final String TAG_AUTHORS = TAG_AUTHOR + S;
    private static final String TAG_DEPENDENCIE = "dependencie";
    private static final String TAG_DEPENDENCIES = TAG_DEPENDENCIE + S;
    private static final String TAG_CHANGELOG = "changelog";
    private static final String TAG_CHANGELOGS = TAG_CHANGELOG + S;
    private static final String ATTR_TITLE = "title";
    private static final String ATTR_HREF = "href";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_VERSION = "ver";
    private static final String ATTR_CODE = "code";
    private static final String ATTR_TYPE = "type";
    private static final String ATTR_ALIGN = "align";
    private static final String ATTR_EMAIL = "email";
    private static String currTag;
    public static About Parse(Context ctx, int id) throws IOException, XmlPullParserException {
        return Parse(ctx.getResources().getXml(id));
    }
    public static About Parse(XmlResourceParser parse)
            throws XmlPullParserException, IOException {
        About about = new About();
        int event = parse.getEventType();
        String text = null;
        LinkedHashMap<String, String> attrs = new LinkedHashMap<>();
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG) {
                currTag = parse.getName();
                if (!Utils.contains(new String[]{
                        TAG_ROOT,
                        TAG_LABEL,
                        TAG_CODE,
                        TAG_ICON,
                        TAG_VERSION,
                        TAG_DESCRIPTION,
                        TAG_LICENSE,
                        TAG_REPOSITORY,
                        TAG_HOME,
                        TAG_HOMES,
                        TAG_ISSUE,
                        TAG_ISSUES,
                        TAG_AUTHOR,
                        TAG_AUTHORS,
                        TAG_DEPENDENCIE,
                        TAG_DEPENDENCIES,
                        TAG_CHANGELOG,
                        TAG_CHANGELOGS
                }, currTag)) {
                    throw new XmlPullParserException(
                            String.format("Error in xml:Invalid tag \"%s\" at line(%s).",
                                    currTag,
                                    parse.getLineNumber()));
                }
                for (int i = 0; i < parse.getAttributeCount(); i++) {
                    attrs.put(
                            parse.getAttributeName(i),
                            parse.getAttributeValue(i));
                }
            } else if (event == XmlPullParser.TEXT) {
                text = parse.getText();
            } else if (event == XmlPullParser.END_TAG) {
                switch (currTag) {
                    case TAG_LABEL:
                        about.setLabel(text);
                        break;
                    case TAG_ICON:
                        about.setLogo(text);
                        break;
                    case TAG_VERSION:
                        about.setVersion(text);
                        break;
                    case TAG_CODE:
                        about.setVersionCode(text);
                        break;
                    case TAG_DESCRIPTION:
                        about.setDescription(text);
                        break;
                    case TAG_LICENSE:
                        if (attrs.isEmpty()) break;
                        about.setLicense(
                                new Url(text,
                                        attrs.get(ATTR_HREF)));
                        break;
                    case TAG_REPOSITORY:
                        if (attrs.isEmpty()) break;
                        about.setRepository(
                                new Repository(
                                        attrs.get(ATTR_TYPE),
                                        text,
                                        attrs.get(ATTR_HREF)));
                        break;
                    case TAG_HOME:
                        if (attrs.isEmpty()) break;
                        about.getHome()
                                .add(new Url(text,
                                        attrs.get(ATTR_HREF)));
                        break;
                    case TAG_ISSUE:
                        if (attrs.isEmpty()) break;
                        about.getIssues()
                                .add(new Url(text,
                                        attrs.get(ATTR_HREF)));
                        break;
                    case TAG_AUTHOR:
                        if (attrs.isEmpty()) break;
                        about.getAuthors()
                                .add(new Author(text,
                                        attrs.get(ATTR_HREF),
                                        attrs.get(ATTR_EMAIL)));
                        break;
                    case TAG_DEPENDENCIE:
                        if (attrs.isEmpty()) break;
                        License license = new License(
                                text,
                                attrs.get(ATTR_TYPE));
                        Dependencie dep = new Dependencie(attrs.get(ATTR_NAME),
                                attrs.get(ATTR_HREF),
                                attrs.get(ATTR_TITLE),
                                attrs.get(ATTR_VERSION),
                                attrs.get(ATTR_CODE),
                                attrs.get(ATTR_TYPE),
                                attrs.get(ATTR_ALIGN),
                                license);
                        about.getDependencies()
                                .add(dep);
                        break;
                    case TAG_CHANGELOG:
                        if (attrs.isEmpty()) break;
                        ChangeLog clog = new ChangeLog(attrs.get(ATTR_NAME),
                                attrs.get(ATTR_HREF),
                                attrs.get(ATTR_TYPE),
                                attrs.get(ATTR_CODE),
                                text);
                        about.getChangelogs()
                                .add(clog);
                        break;
                }
                text = null;
                attrs.clear();
            }
            event = parse.next();
        }
        parse.close();
        return about;
    }
    public static class About {
        private String label;
        private String icon;
        private String version;
        private int version_code;
        private String description;
        private List<Url> home = new ArrayList<>();
        private List<Url> issues = new ArrayList<>();
        private Url license;
        private Repository repository;
        private List<Author> authors = new ArrayList<>();
        private List<Dependencie> dependencies = new ArrayList<>();
        private List<ChangeLog> changelogs = new ArrayList<>();
        public About() {
        }
        public String getLabel() {
            return label;
        }
        public String getLabel(String defaultValue) {
            if (Utils.isEmpty(label)) {
                return defaultValue;
            }
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public String getLogo() {
            return icon;
        }
        public void setLogo(String icon) {
            this.icon = icon;
        }
        public String getVersion() {
            return version;
        }
        public String getVersion(String defaultValue) {
            if (Utils.isEmpty(version)) {
                return defaultValue;
            }
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public int getVersionCode() {
            return version_code;
        }
        public int getVersionCode(int defaultValue) {
            if (Utils.isEmpty(version_code)) {
                return defaultValue;
            }
            return version_code;
        }
        public void setVersionCode(String code) {
            try{
                this.version_code = Integer.parseInt(code);
            }catch (NumberFormatException nfe){
                this.version_code = 0;
            }
        }
        public void setVersionCode(int code) {
            this.version_code = code;
        }
        public String getDescription() {
            return description;
        }
        public String getDescription(String defaultValue) {
            if (Utils.isEmpty(description)) {
                return defaultValue;
            }
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public List<Url> getHome() {
            return home;
        }
        public List<Url> getHome(List<Url> defaultValue) {
            if (Utils.isEmpty(home)) {
                return defaultValue;
            }
            return home;
        }
        public void setHome(List<Url> home) {
            this.home = home;
        }
        public List<Url> getIssues() {
            return issues;
        }
        public List<Url> getIssues(List<Url> defaultValue) {
            if (Utils.isEmpty(issues)) {
                return defaultValue;
            }
            return issues;
        }
        public void setIssues(List<Url> issues) {
            this.issues = issues;
        }
        public Url getLicense() {
            return license;
        }
        public Url getLicense(Url defaultValue) {
            if (Url.isEmpty(license)) {
                return defaultValue;
            }
            return license;
        }
        public void setLicense(Url license) {
            this.license = license;
        }
        public Repository getRepository() {
            return repository;
        }
        public Repository getRepository(Repository defaultValue) {
            if (Repository.isEmpty(repository)) {
                return defaultValue;
            }
            return repository;
        }
        public void setRepository(Repository repository) {
            this.repository = repository;
        }
        public List<Author> getAuthors() {
            return authors;
        }
        public List<Author> getAuthors(List<Author> defaultValue) {
            if (Utils.isEmpty(authors)) {
                return defaultValue;
            }
            return authors;
        }
        public void setAuthors(List<Author> authors) {
            this.authors = authors;
        }
        public List<Dependencie> getDependencies() {
            return dependencies;
        }
        public List<Dependencie> getDependencies(List<Dependencie> defaultValue) {
            if (Utils.isEmpty(dependencies)) {
                return defaultValue;
            }
            return dependencies;
        }
        public void setDependencies(List<Dependencie> dependencies) {
            this.dependencies = dependencies;
        }
        public List<ChangeLog> getChangelogs() {
            return changelogs;
        }
        public List<ChangeLog> getChangelogs(List<ChangeLog> defaultValue) {
            if (Utils.isEmpty(changelogs)) {
                return defaultValue;
            }
            return changelogs;
        }
        public void setChangelogs(List<ChangeLog> changelogs) {
            this.changelogs = changelogs;
        }
    }
    public static class Url {
        private String html;
        private String href;
        private String name;
        public Url(String html){
            this.html = html;
        }
        public Url(String name, String href) {
            this.href = href;
            this.name = name;
            this.html = String.format("<a href=\"%s\">%s</a>", getHref(), getName());
        }
        public String getHref() {
            return href;
        }
        public void setHref(String href) {
            this.href = href;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public static boolean isEmpty(Url url) {
            return url == null || (url.href == null && url.name == null && url.html ==null);
        }
        public String getHtml() {
            return html;
        }
        public static Url fromMD(String md){
            return new Url(Processor.process(md));
        }
    }
    public static class Repository extends Url {
        public static final String TYPE_GIT = "git";
        public static final String TYPE_SVN = "svn";
        public static final int INT_TYPE_GIT = 0;
        public static final int INT_TYPE_SVN = 1;
        private int type = -1;
        public Repository(String type, String name, String href) {
            super(href, name);
            switch (type) {
                case TYPE_GIT:
                    this.type = INT_TYPE_GIT;
                case TYPE_SVN:
                    this.type = INT_TYPE_SVN;
            }
        }
        public Repository(String name, String href) {
            super(href, name);
        }
        public Repository(String html) {
            super(html);
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }
        public static boolean isEmpty(Repository rep) {
            return rep == null || (rep.getHref() == null && rep.getName() == null && rep.getType() == -1);
        }
        public static Repository fromMD(String md){
            return new Repository(Processor.process(md));
        }
    }
    public static class Author extends Url {
        private String email;
        public Author(String name, String href, String email) {
            super(name, href);
            this.email = email;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
    }
    public static class Dependencie extends Url {
        private String align;
        private String title;
        private String ver;
        private String code;
        private String type;
        private License license;
        public Dependencie(String name, String href, String title, String ver, String code, String type, String align, License license) {
            super(name, href);
            this.title = title;
            this.ver = ver;
            this.code = code;
            this.type = type;
            this.license = license;
            this.align = align;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getVer() {
            return ver;
        }
        public void setVer(String ver) {
            this.ver = ver;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public License getLicense() {
            return license;
        }
        public void setLicense(License license) {
            this.license = license;
        }
        public String getAlign() {
            return align != null ? align : "left";
        }
        public void setAlign(String align) {
            this.align = align;
        }
    }
    public static class License {
        public static final String TYPE_MD = "md";
        public static final String TYPE_HTML = "html";
        public static final String TYPE_TXT = "txt";
        private String type;
        private String text;
        public License(String text, String type) {
            this.text = text;
            this.type = Utils.isEmpty(type)?type:TYPE_TXT;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
        public Spanned toSpanned(Context ctx) {
            if (Utils.isEmpty(text)) {
                return null;
            }
            switch (type) {
                case TYPE_MD:
                    return Html.fromHtml(Processor.process(text), null, new BaseCustomTagHandler());
                case TYPE_HTML:
                    return Html.fromHtml(text, null, new BaseCustomTagHandler());
                default:
                    return new SpannableString(text);
            }
        }
    }
    public static class ChangeLog extends Url {
        public static final String TYPE_MD = "md";
        public static final String TYPE_HTML = "html";
        public static final String TYPE_TXT = "txt";
        private String type;
        private String code;
        private String content;
        public ChangeLog(String name, String href, String type, String code, String content) {
            super(name, href);
            this.code = code;
            if (Utils.isEmpty(type)) {
                this.type = TYPE_TXT;
            } else {
                this.type = type;
            }
            this.content = content;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public Spanned getContent(Context ctx) {
            return toSpanned(ctx);
        }
        public void setContent(String content) {
            this.content = content;
        }
        public Spanned toSpanned(Context ctx) {
            if (Utils.isEmpty(content)) {
                return null;
            }
            switch (type) {
                case TYPE_MD:
                    return Html.fromHtml(Processor.process(content), null, new BaseCustomTagHandler());
                case TYPE_HTML:
                    return Html.fromHtml(content, null, new BaseCustomTagHandler());
                case TYPE_TXT:
                    return new SpannableString(content);
                default:
                    return null;
            }
        }
    }
}
