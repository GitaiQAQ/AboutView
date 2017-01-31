# AboutView

This is among one of the most used module and was for quite some time being developed.

It provides rich text data, is open source and presented in a way that is usable and friendly.

![https://github.com/GitaiQAQ/SMSCodeHelper](https://ooo.0o0.ooo/2017/01/31/5890835e9c8eb.png)

## How to use?

### Configuring Project `.../main/res/xml/about.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<about>
    <label>Captchas Tools</label>
    <description>
        验证码处理工具,检查短信中的验证码并给予在通知栏和Toast显示,输出至剪贴板等操作
    </description>
    <license href="http://gitai.me">GPLv3+</license>
    <repository type="git" href="https://github.com/jashkenas/coffeescript.git">coffeescript@jashkenas</repository>
    <homes>
        <home href="http://gitai.me/smscodehelper">Website</home>
        <home href="https://github.com/gitaiQAQ/SmsCodeHelper">Github</home>
    </homes>
    <issues>
        <issue href="https://github.com/gitaiQAQ/SmsCodeHelper/issues">Issues</issue>
    </issues>
    <devers>
        <dever href="https://github.com/RikkaW">RikkaW</dever>
        <dever email="i@gitai.me" href="https://github.com/gitaiQAQ">Gitai</dever>
    </devers>
    <dependencies>
        <dependencie title="SystemBarTint" href="https://github.com/jgilfelt/SystemBarTint" name="SystemBarTint@Github" ver="1.0" code="1" type="txt">
Copyright 2013 readyState Software Limited
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.</dependencie>
    </dependencies>
    <changelogs>
        <changelog name="v0.0.4" code="4" type="md"><![CDATA[
* 增加简述(即说明)
* 增加配置项
    - 自定义触发关键词
    - 自定义来源及解析正则
* 反推服务商
* 拨号盘暗码:`*#*#767#*#*`(SMS)
* 增加原生5.0以上Overview Screen卡片颜色
        ]]></changelog>
        <changelog name="v0.0.2" code="2" type="md"><![CDATA[
* 新图标[@萌萌的小雅酱](http://www.coolapk.com/u/436688)
        ]]></changelog>
        <changelog name="v0.0.1" code="1" type="md"><![CDATA[
* 增加`魔趣 OS` 验证码解析 [SDK](http://opengrok.mokeedev.com/mkl-mr1/xref/external/mokee/MoKeeSDKs/libMoKeeCloud/libMoKeeCloud.jar)
        ]]></changelog>
    </changelogs>
</about>

```

`label`: Title of aboutview, the method `setTitle` can override this.

`description`: Description

`license`: License

`repository`: Repository

`homes`: Project home links

`issues`: Issues or BBS, and other places where can report

`devers`: Author

`dependencies`: Add the license of dependencies here

`changelogs`: Add the log here, suppert lightweight markup language with plain text formatting syntax or HTML


## Build

1. `import` class `me.gitai.aboutview.AboutView;`
2. Setup Layout
```java
AboutView aboutview = new AboutView(getContext(), R.xml.about)
    .initData()
    .setTitle(getContext().getString(R.string.title))
    .setVersion(BuildConfig.VERSION_NAME + "-" + BuildConfig.BUILD_TYPE)
    .setVersionCode(BuildConfig.VERSION_CODE)
    .setLogo(R.mipmap.ic_launcher)
    .build();
```
3. Inset or set `aboutview` as child view
