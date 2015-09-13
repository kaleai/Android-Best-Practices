### 概要  

jOOR是基于java反射api的一个简单包装类，简单却十分实用。  

jOOR这个名字是从jOOQ中得到的灵感,jOOQ是一个很棒的SQL的API。  

### 基于的库   

完全不用！

### 一个简单的示例

```JAVA
// All examples assume the following static import:
import static org.joor.Reflect.*;

String world = on("java.lang.String")  // on后面放入类的全名，这里是String类
                .create("Hello World") // 将字符串“Hello World”，传入构造方法中
                .call("substring", 6)  // 执行subString这个方法，并且传入6作为参数
                .call("toString")      // 执行toString方法
                .get();                // 得到包装好的类，这里是一个String对象
```


### 抽象代理

jOOR也可以方便的使用java.lang.reflect.Proxy的API
```java
public interface StringProxy {
  String substring(int beginIndex);
}

String substring = on("java.lang.String")
                    .create("Hello World")
                    .as(StringProxy.class) // 为包装类建立一个代理
                    .substring(6);         // 访问代理方法
```


### 和java.lang.reflect的对比

使用jOOR的代码:

```java
Employee[] employees = on(department).call("getEmployees").get();

for (Employee employee : employees) {
  Street street = on(employee).call("getAddress").call("getStreet").get();
  System.out.println(street);
}
```

用传统的反射方式写的代码:

```java
try {
  Method m1 = department.getClass().getMethod("getEmployees");
  Employee employees = (Employee[]) m1.invoke(department);

  for (Employee employee : employees) {
    Method m2 = employee.getClass().getMethod("getAddress");
    Address address = (Address) m2.invoke(employee);

    Method m3 = address.getClass().getMethod("getStreet");
    Street street = (Street) m3.invoke(address);

    System.out.println(street);
  }
}

// There are many checked exceptions that you are likely to ignore anyway 
catch (Exception ignore) {

  // ... or maybe just wrap in your preferred runtime exception:
  throw new RuntimeException(e);
}
```
### 更多示例  
建立一个测试类：  
```JAVA
package kale.androidframework;

public class Kale {

    private String name;

    private String className;

    Kale() {

    }

    Kale(String clsName) {
        this.className = clsName;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public static void method() {
        
    }
}
```  
这个类中有有参构造方法和无参构造方法，还有get和set方法。这里的类变量都是private的，有一个get方法也是private的。我们现在要尝试利用jOOR来访问变量和方法：   
```JAVA
String name = null;
        Kale kale;
        // 【创建类】
        kale = Reflect.on(Kale.class).create().get(); // 无参数 
        kale = Reflect.on(Kale.class).create("kale class name").get();// 有参数
        System.err.println("------------------> class name = " + kale.getClassName());

        // 【调用方法】
        Reflect.on(kale).call("setName","调用setName");// 多参数
        System.err.println("调用方法：name = " + Reflect.on(kale).call("getName"));// 无参数
        
        // 【得到变量】
        name = Reflect.on(kale).field("name").get();// 复杂
        name = Reflect.on(kale).get("name");// 简单
        System.err.println("得到变量值： name = " + name);
        
        // 【设置变量的值】
        Reflect.on(kale).set("className", "hello");
        System.err.println("设置变量的值： name = " + kale.getClassName());
        System.err.println("设置变量的值： name = " + Reflect.on(kale).set("className", "hello2").get("className"));  
```

### 相似的工程

Everyday Java reflection with a fluent interface:

 * http://docs.codehaus.org/display/FEST/Reflection+Module
 * http://projetos.vidageek.net/mirror/mirror/

Reflection modelled as XPath (quite interesting!)

 * http://commons.apache.org/jxpath/users-guide.html





