# Java反射最佳实践

**概要：最简单优雅的使用反射。**  
本文的例子都可以在示例代码中看到并下载，如果喜欢请star，如果觉得有纰漏请提交issue，如果你有更好的点子可以提交pull request。本文的示例代码主要是基于[jOOR][1]行编写的，如果想了解更多请查看[这里][2]的测试代码。  
固定连接：[https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.9/reflect/reflect.md][3]

### 一、需求  
今天一个“懒”程序员突然跑过来说：“反射好麻烦，我要提点需求。”  
听到这句话后我就知道，今天一定不好过了，奇葩需求又来了。  

我们之前写反射都是要这么写：  
```java
     public static <T> T create(HttpRequest httpRequest) {
        Object httpRequestEntity = null;
        try {
            Class<T> httpRequestEntityCls = (Class<T>) Class.forName(HttpProcessor.PACKAGE_NAME + "." + HttpProcessor.CLASS_NAME);
            Constructor con = httpRequestEntityCls.getConstructor(HttpRequest.class);
            httpRequestEntity = con.newInstance(httpRequest);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) httpRequestEntity;
    }
```  
因为反射在开发中很少用（做普通的业务开发的话），仅仅在自己写一些框架和注解框架时会用到，所以对api总是不熟悉。每次用到api都要去网上查，查了后又得自己实验下，很不爽。更差劲的是这样写法可读性十分低下。我不希望这样写反射，我希望反射能像  
```JAVA
String str = new String();
```  
这样简单，**一行代码搞定！**。  
此外，有很多人都说反射影响性能，在开发的时候要避免用反射。那么什么时候该用反射，什么时候不用反射呢？用什么方式来避免反射呢？如果不明白**什么时候用反射**，就很难将反射活学活用了。


### 二、分析  
当我们接到上面需求后，我长舒一口气，因为这回的需求还比较简单。  
我相信有人会说：“反射就那几个api，一直没变过，你就不会自己去查啊，觉得麻烦就别写代码啊，不知道反射的内部细节你怎么去提高呢？”  
其实不然，重复写麻烦的代码和提高自己的代码能力是完全无关的，我实在不知道我们写了成千上万行的`findViewById`后除了知道类要和xml文件绑定外，还学到了什么。  
那么这回我们继续来挑战传统思维和模板式代码，来看看新一代的反射代码应该怎么写，如何用一行代码来反射出类。  
在做之前，来看看我们一般用反射来干嘛？  

    1. 反射构建出无法直接访问的类   
    2. set或get到无法访问的类变量
    3. 调用不可访问的方法


### 三、解决方案  
#### 3.1 一行代码写反射   
作为一个Android程序员，索性就拿`TextView`这个类开刀吧。首先定义一个类变量：  
```JAVA
TextView mTv;
```  
**通过反射得到实例：**
```JAVA
// 有参数，建立类
mTv = Reflect.on(TextView.class).create(this).get();

// 通过类全名得到类
String word = Reflect.on("java.lang.String").create("Reflect TextView").get();

// 无参数，建立类
Fragment fragment = Reflect.on(Fragment.class).create().get();
```   
**通过反射调用方法：**  
```JAVA  
// 调用无参数方法
L.d("call getText() : " + Reflect.on(mTv).call("getText").toString());

// 调用有参数方法
Reflect.on(mTv).call("setTextColor", 0xffff0000);
```  

**通过反射get、set类变量**   
TextView中有个mText变量，来看看我们怎么接近它。  
```JAVA
// 设置参数
Reflect.on(mTv).set("mText", "---------- new Reflect TextView ----------");

// 获得参数
L.d("setgetParam is " + Reflect.on(mTv).get("mText"));
```

#### 3.2 什么时候该用反射，什么时候不用反射  
又到了这样权衡利弊的时候了，首先我们明确，在日常开发中尽量不要用反射，除非遇到了必须要通过反射才能调用的方法。比如我在做一个下拉通知中心功能的时候就遇到了这样的情况。系统没有提供api，所以我们只能通过反射进行调用，所以我自己写了这样一段代码： 
```xml
<uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>
```
```JAVA
   private static void doInStatusBar(Context mContext, String methodName) {
        try {
            Object service = mContext.getSystemService("statusbar");
            Method expand = service.getClass().getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示消息中心
     */
    public static void openStatusBar(Context mContext) {
        // 判断系统版本号
        String methodName = (VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        doInStatusBar(mContext, methodName);
    }

    /**
     * 关闭消息中心
     */
    public static void closeStatusBar(Context mContext) {
        // 判断系统版本号
        String methodName = (VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        doInStatusBar(mContext, methodName);
    }
```  
先来看看利用jOOR写的`doInStatusBar`方法会简洁到什么程度：  
```JAVA
    private static void doInStatusBar(Context mContext, String methodName) {
        Object service = mContext.getSystemService("statusbar");
        Reflect.on(service).call(methodName);
    }
```   
哇，就一行代码啊，很爽吧~  
爽完了，我们就来看看反射问题吧。因为不是系统给出的api，所以谷歌在不同的版本上用了不同的方法名来做处理，用反射的话我们就必须进行版本的判断，这是需要注意的，此外反射在性能方面确实不好，这里需要谨慎。  
我的建议：  
如果一个类中有很多地方都是private的，而你的需求都需要依赖这些方法或者变量，那么比起用反射，推荐把这个类复制出来，变成自己的类，像是toolbar这样的类就可以进行这样的操作。  
在自己写框架的时候，我们肯定会用到反射，很简单的例子就是事件总线和注解框架，翔哥就说过一句话：**无反射，无框架**。也正因为是自己写的框架，所以通过反射调用的方法名和参数一般不会变，更何况做运行时注解框架的话，反射肯定会出现。在这种情况下千万不要害怕反射，索性放心大胆的做。因为它会让你完成很多不可能完成的任务。  
总结下来就是：  
实际进行日常开发的时候尽量少用反射，可以通过复制原始类的形式来避免反射。在写框架时，不避讳反射，在关键时利用反射来助自己一臂之力。


### 四、后记  
我们终于完成了用一行代码写反射，避免了很多无意义的模板式代码。需要再次说明的是，本文是依据[jOOR][4] 进行编写的，[这里][7]有原项目readme的中文翻译。  
jOOR是我无意中遇到的开源库，第一次见到它时我就知道这个是我想要的，因为那时候我被反射搞的很乱，而它简洁的编码方式给我带来了新的思考，大大提高了代码可读性。顺便一说，作者人比较好（就是死活不愿意让我放入中文的readme），技术也很不错。该项目有很详细的测试用例，并且还给出了几个类似的反射调用封装库。可见作者在写库时做了大量的调研和测试工作，让我们可以放心的运用该库*（其实就两个类）*。  
本文希望带给大家一个反射的新思路，给出一个最简单实用的反射写法，希望能被大家迅速运用到实践中去。更加重要的是，通过对jOOR的分析，让我知道了写库前应该调研类似的库，而不是完全的创造新轮子，调研和测试是代码稳定性的重要保障。

### 参考自
[http://www.cnblogs.com/tianzhijiexian/p/3906774.html][5]  
[https://github.com/tianzhijiexian/HttpAnnotation/blob/master/lib/src/main/java/kale/net/http/util/HttpReqAdapter.java][6]

### 作者  
![Jack Tony](./avatar.jpg)     

developer_kale@qq.com  
@天之界线2010


  [1]: https://github.com/jOOQ/jOOR
  [2]: https://github.com/jOOQ/jOOR/tree/master/jOOR/src/test/java/org/joor/test
  [3]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.9/reflect/reflect.md
  [4]: https://github.com/jOOQ/jOOR
  [5]: http://www.cnblogs.com/tianzhijiexian/p/3906774.html
  [6]: https://github.com/tianzhijiexian/HttpAnnotation/blob/master/lib/src/main/java/kale/net/http/util/HttpReqAdapter.java
  [7]: https://github.com/tianzhijiexian/Android-Best-Practices/blob/master/2015.9/reflect/README%20-%20chinese.md
