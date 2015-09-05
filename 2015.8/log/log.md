# Log最佳实践

**概要：如何使用更好的log来调试应用。**  
本文的例子都可以在示例代码中看到并下载，如果喜欢请star，如果觉得有纰漏请提交issue，如果你有更好的点子可以提交pull request。本文的示例代码主要是基于[Logger][1]和[LogUtils][2]进行编写的，如果想了解更多请查看他们的详细解释。  
固定连接：[https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/log.md][3]  

### 需求  
我们都知道android中log是这么写的：
```JAVA
Log.d(TAG, "This is a debug log");
```  
我们在调试的时候经常会输出这行代码，这个方法有两个参数，一个是TAG，一个是真正要输出的内容。我们不愿意每次花费时间去思考应该定义什么**TAG**，我们希望有一个东西可以帮我们定义好TAG，我们只需要写正真有意义的内容就行。这才是程序员思维，而不是程序猿思维。其实就是这样一个东西：
```JAVA
Log.d("This is a debug log");
```  
如果我们更“懒”*（懒在这里是褒义词）*，可能会希望这样写：  
```JAVA
L.d("This is a debug log");
```  
等等！是不是还能更进一步，我希望终端输出的log更加**美观**，并且输出的地方是可以有一个**超链接**，这样我们直接点击输出内容的超链就可以直接定位到具体的代码中了。  
```
 D/LoggerActivity﹕ ╔══════════════════════════════════════════════════════════
 D/LoggerActivity﹕ ║ Thread: main
 D/LoggerActivity﹕ ╟──────────────────────────────────────────────────────────
 D/LoggerActivity﹕ ║ BaseActivity.onCreate  (BaseActivity.java:32)
 D/LoggerActivity﹕ ║    LoggerActivity.setViews  (LoggerActivity.java:26)
 D/LoggerActivity﹕ ╟──────────────────────────────────────────────────────────
 D/LoggerActivity﹕ ║ This is a debug log
 D/LoggerActivity﹕ ╚══════════════════════════════════════════════════════════
```   
对了，我还希望log能帮我们优雅的打印出map、list、json、array等**Object对象**，而不用我们去自己拼接。 
```
 D/LoggerActivity﹕ ╔══════════════════════════════════════════════════════════
 D/LoggerActivity﹕ ║ Thread: main
 D/LoggerActivity﹕ ╟──────────────────────────────────────────────────────────
 D/LoggerActivity﹕ ║ BaseActivity.onCreate  (BaseActivity.java:32)
 D/LoggerActivity﹕ ║    LoggerActivity.setViews  (LoggerActivity.java:38)
 D/LoggerActivity﹕ ╟──────────────────────────────────────────────────────────
 D/LoggerActivity﹕ ║ String[5] {
 D/LoggerActivity﹕ ║ [android,	ios,	wp,	linux,	window]
 D/LoggerActivity﹕ ║ }
 D/LoggerActivity﹕ ╚══════════════════════════════════════════════════════════
```
哦，还有。我不希望在release包中出现log，但懒得自己整天设置log的**开关**，能不能自动化。但有时候我们一些东西还必须在release包中调试，比如需要签名的微信登录SDK。我希望能加个**强制显示log的开关**，一旦开启无论如何都显示log信息。
```JAVA  
    // 定义是否是强制显示log的模式
    protected static final boolean LOG = false;
```

### 分析  
当我们接到了上面需求后就会觉得，产品经理真是恐怖的生物。我们现在需要为这样一个人提供一个很好的API，以满足他的需求。但这个需求不合理么，很合理，我们的宗旨就是让无意义的重复代码去死，如果死不掉就交给机器来做。我们应该做哪些真正需要我们做的事情，而不是像一个没思想的猿猴一般整天写模板式代码。  

### 解决方案  
####2.1 消灭TAG  
我们用TAG就是做定位，同时方便过滤无意义的log。那么索性把当前类名作为这样一个TAG的标识。于是，在我们自定义的log类中就用如下代码设置tag：
```JAVA
    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }
```  
这样我们就轻易的摆脱了tag的纠缠。

> 这个方法来自于豪哥的建议，这里感谢豪哥的意见。  


####2.2 将Log简化  
有人说我们IDE不都有代码提示了么，为啥还用一个L来做简化。首先用L比log能更快的得到提示，输入一个l.d就会直接显示提示，并且不会和原本的log类混淆。其次就是调用更方便。简化log这个东西太简单了，直接自定义一个L类，用作Log的输出即可。  

#### 2.3 在终端能显示当前类名并且增加超链  
这个功能其实ide是支持的，只不过我们可以通过一些神奇的方法来做到更好的效果。下面就给出两个可行的方法：
```JAVA
    /**
     * @return 当前的类名（全名）
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result = thisMethodStack.getClassName();
        return result;
    }



    /**
     * log这个方法就可以显示超链
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result += thisMethodStack.getClassName()+ ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
```

#### 2.4 让log更加美观  
人们对美的追求真是无止境，更加美丽的log也能方便我们一下子区分什么是系统打印的，什么是我们自己应用打印的。做到这点也比较简单，就是在输出前做点字符串拼接的工作。  
```JAVA
 private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private static String TAG = "PRETTYLOGGER";
```  
因为打印log也是消耗性能的，所以我建议最多只保留出现某些异常（这些异常轻于Exception）时打印的log，在调试时打印的log在提交代码前请全部清除。 

#### 2.5 让log支持输出object、map、list、array、jsonStr等对象  
这个需求实现起来也比较容易，如果是简单的POJO的对象，我们用反射得到对象的类变量，通过字符串拼接的方式最终输出值。如果是map等数组结构，那么就用其内部的遍历依次输出值和内容。如果是json的字符串，就需要判断json的`{}`,`[]`这样的特殊字符进行换行处理。  

#### 2.6 增加log自动化和强制开关  
区分release和debug版本有系统自带的BuildConfig.DEBUG变量，用这个就可以控制是否显示log了。强制开关也很简单，在log初始化的最后判断强制开关是否打开，如果打开那么就覆盖之前的显示设置，直接显示log。转为代码就是这样：
```JAVA
public class BaseApplication extends Application {

    // 定义是否是强制显示log的模式
    protected static final boolean LOG = false;

    @Override
    public void onCreate() {
        super.onCreate();
        L.init()               // default PRETTYLOGGER or use just init()
                //.setMethodCount(2);            // default 2
                //.hideThreadInfo()             // default shown
                .setMethodOffset(1);           // default 0
        // 在debug下，才显示log
        L.init().setLogLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
        // 如果是强制显示log，那么无论在什么模式下都显示log
        if (BaseApplication.LOG) {
            L.init().setLogLevel(LogLevel.FULL);
        }
    }
}
```

### 最终结果  
首先建立一个activity，在里面输出各种类型的数据。为了测试Inner class和Object的效果，我专门建立了一个很简单的内部类User：
```JAVA
class User {

        private String name;

        private String sex;

        User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public void log() {
            show();
        }

        private void show() {
            L.d("user");
        }
    }
```  
激动人心的测试开始了：  
```JAVA
        // string
        String str = fromIntent("key");
        L.d(str != null ? str : "hello world");

        // json
        L.json("[{\"CityId\":18,\"CityName\":\"西安\",\"ProvinceId\":27,\"CityOrder\":1},{\"CityId\":53,\"CityName\":\"广州\",\"ProvinceId\":27,\"CityOrder\":1}]'");

        // object
        L.Object(new User("jack", "f"));

        // list
        L.Object(TestUtil.getLongStringList(this));

        // array
        L.Object(TestUtil.getShortStringArr());

        // arrays
        double[][] doubles = {
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33},
                {1.2, 1.6, 1.7, 30, 33}
        };
        L.Object(doubles);

        // sub class
        new User("name", "sex").log();  
```  
结果如下：  
简单的string类型：
![此处输入图片的描述][5]  

Json字符串:    
![此处输入图片的描述][6]  

Object对象：  
![此处输入图片的描述][7]

数组类型：  
![此处输入图片的描述][8]  
![此处输入图片的描述][9]  

内部类：  
![此处输入图片的描述][10]

### 后记  
我们可以看到即使一个最简单的log都有很多点是可优化的，而且看到了我们之前一直写的模板式代码是多么枯燥乏味。通过这篇文章，大家可以看到一个优化的过程，相信大家都会喜欢最终的简单、美观、方便的log类去调试应用。当然，我知道还是有很多人不喜欢，那么不妨提出更好的解决方案来一起讨论。相信我们的最终目的是一致的，那就是让开发越来越简便，越来越优雅~

### 参考自
[http://ihongqiqu.com/blog/2014/10/16/android-log/][11]  
[https://github.com/pengwei1024/LogUtils][12]  
[https://github.com/orhanobut/logger][13]    

### 作者  
![Jack Tony][14]     

developer_kale@.com  
@天之界线2010



  [1]: https://github.com/orhanobut/logger
  [2]: https://github.com/pengwei1024/LogUtils
  [3]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/log.md
  [4]: https://www.zybuluo.com/shark0017/note/163330
  [5]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/string.png?raw=true
  [6]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/json.png?raw=true
  [7]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/pojo.png?raw=true
  [8]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/array.png?raw=true
  [9]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/arrayes.png?raw=true
  [10]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/innerCls.png?raw=true
  [11]: http://ihongqiqu.com/blog/2014/10/16/android-log/
  [12]: https://github.com/pengwei1024/LogUtils
  [13]: https://github.com/orhanobut/logger
  [14]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.8/log/avatar.jpg?raw=true
