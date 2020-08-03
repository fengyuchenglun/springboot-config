

​	使用springboot进行应用业务开发，为了程序更具灵活性、通用性，能够在不同的环境下使用相同的代码。我们通过读取配置文件，让应用可配置化。比如编写一个Oss上传下载文件相关的工具类，对应的不同环境oss服务器地址是不同的。如果将服务器地址硬编码到代码中，那么每次部署不同的环境，都需要开发人员修改Oss服务器地址。通过读取配置文件获取Oss服务器地址，能够极大的减少开发人员的工作量，使应用更加通用。



## Springboot加载配置属性顺序

Spring Boot用过PropertySource加载配置属性，以允许对属性值进行合理的覆盖，属性会以如下的顺序进行设值：

1. home目录下的devtools全局设置属性（~/.spring-boot-devtools.properties，如果devtools激活)。
2. 测试用例上的@TestPropertySource注解。
3. 测试用例上的@SpringBootTest#properties注解。
4. `命令行参数。`
5. 来自SPRING_APPLICATION_JSON的属性（环境变量或系统属性中内嵌的内联JSON）。
6. ServletConfig初始化参数。
7. ServletContext初始化参数。
8. 来自于java:comp/env的JNDI属性。
9. `Java系统属性（System.getProperties()）。`
10. `操作系统环境变量。`
11. RandomValuePropertySource，只包含random.*中的属性。
12. 没有打进jar包的Profile-specific应用属性（application-{profile}.properties和YAML变量）。
13. `打进jar包中的Profile-specific应用属性（application-{profile}.properties和YAML变量）。`
14. 没有打进jar包的应用配置（application.properties和YAML变量）。
15. `打进jar包中的应用配置（application.properties和YAML变量）。`
16. @Configuration类上的@PropertySource注解。
17. 默认属性（使用SpringApplication.setDefaultProperties指定）。

4、9、10，一般用于应用部署发布的时候设置属性默认值。比如JVM参数设置-Xms、-Xmx、-Xss等。对于开发者我们应该更加关注通过application.properties/YAML加载配置属性。



## application配置文件读取顺序

Spring Boot启动会扫描以下位置的application.properties或者application.yml文件作为Spring Boot的默认配置文件。

1. 当前工作目录下的/config/。
2. 当前工作目录。
3. classpath下的/config目录。
4. classpath根路径（root）。

```
工作目录
│  application.yml  // 2
│  pom.xml
│
├─config
│      application.yml  //1
│
└─src
   ├─main
   │  ├─java
   │  │  └─com
   │  │      └─duanledexianxianxian
   │  │          └─core
   │  │              │  SpringWebApplication.java
   │  │              │
   │  │              └─config
   │  │                      SystemConfiguration.java
   │  │                      SystemEnvironmentProperties.java
   │  │                      SystemProperties.java
   │  │                      SystemPropertySourceValueProperties.java
   │  │                      SystemValueProperties.java
   │  │
   │  └─resources
   │      │  application.yml  //4
   │      │
   │      └─config
   │              application.yml  //3
   │
   └─test
      └─java

```



该列表是按优先级排序的（列表中位置高的路径下定义的属性将覆盖位置低的）,所有位置的文件都会被加载，配置文件中的内容是互补配置。

> **互补配置**
>
> 1. 存在相同的配置内容，高优先级的内容会覆盖低优先级的内容
> 2. 存在不同的内容的时候，高优先级和低优先级的配置内容取**并集**

> **工作目录**
>
> 1. 当前工作目录通过"user.dir"设置，可以通过`System.getProperty("user.dir")`打印当前程序的工作目录。
>
> 2. 对于单项目模块工程，当前工作目录的默认为模块根目录。
>
>    ![image-20200803144748418](https://i.loli.net/2020/08/03/apONU5VRYJFWQEP.png)
>
> 3. 对于多项目模块工程，当前工作目录的默认为根模块的根目录。
>
>    ![image-20200803144848536](https://i.loli.net/2020/08/03/iXWsZAqCOpQvHV5.png)
>
> 4. 对于jar命令方式启动的springboot程序，当前工作目录的默认为jar包所在的目录。
>
>    ```shell
>    java -jar core-1.0-SNAPSHOT.jar// 工作目录为core-1.0-SNAPSHOT.jar所在目录
>    ```
>
> 5. Idea中修改工作目录方式
>
>    - 顶部工具栏 "Edit Configrations"
>
>      ![image-20200803150134463](https://i.loli.net/2020/08/03/GhIK3D4HBieEnwF.png)
>
>    - 输入你需要配置的工作目录
>
>      ![image-20200803150218907](C:%5CUsers%5C44902%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200803150218907.png)
>
>    - "OK" or "Apply"

对于单模块项目，配置文件加载顺序:

![image-20200803152410092](https://i.loli.net/2020/08/03/ugHr1xbwDQFn7lN.png)

对于多模块项目，配置文件加载顺序：

![image-20200803152635511](C:%5CUsers%5C44902%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20200803152635511.png)



## Springboot读取配置文件：

Springboot提供了多种方式，方便开发者读取配置文件。下面介绍常用的读取配置文件方式。

示例配置文件都从classpath目录下读取application.yml，需要读取的内容为系统的名称以及编码,如下所示。

```yaml
#系统相关配置
system:
#  系统名称
  name: "classpath root config"
#  系统编码
  code: "classpath root code"
```

#### **1. @Value注解**

```java
/**
 * 通过@value加载配置参数
 * @author duanledexianxianxian
 */
@Component
@Data
public class SystemValueProperties {
    /**
     * 名称
     */
    @Value("${system.name}")
    private String name;
    /**
     * 编码
     */
    @Value("${system.code}")
    private String code;
}
```

平常开发中我们看到大部分开发人员使用@value读取配置文件属性值。这种方式使用简单，对于需要获取配置属性的地方，加上@value注解即可。

但是,不太推荐使用@value读取配置属性。原因如下：

1. @value导致配置碎片化。可能多个地方使用同一个配置属性值，开发者可能会在每个读取配置值的地方都使用@value来读取相同的配置属性。

2. 在一些情况下，@value属性key，会有很长的重复前缀，代码不美观。

3. @Value无法注入static属性。

   > 使用@Value直接放在static的属性上是无法注入内容的。此方式会一直是null.
   >
   > spring 不允许/不支持把值注入到静态变量中；spring支持set方法注入，我们可以利用非静态setter 方法注入静态变量,并且使用@Value的类必须交个spring进行管理.就如同@Autowired有时无法注入一样.

4. 配置文件中没有配置属性值，@value会导致应用启动失败。

   > **Note**：
   >
   > 对于一个项目，我们通常会定义多套环境配置。比如dev、test、pre、prod。
   >
   > 很多开发人员在本地开发环境的配置文件配置了属性值，但是test、pre、prod忘记配置属性值。导致应用程序在其他环境启动失败。
   >
   > 解决方式：
   >
   > 1. 给@value设置默认值
   >
   >    ```java
   >       @Value("${system.name:null}")
   >       private String name;
   >    ```
   >
   >    在配置项的表达式后面加一个冒号和默认值就可以为@Value的变量指定默认值，当配置项没有该项的时候，就会采用默认值而不是抛出错误。
   >
   > 2. 使用@ConfigurationProperties，后面详细介绍。

#### 2. @PropertySource+@Value+@ConfigurationProperties注解

不推荐，比@value还坑。

```
/**
 * 通过@value加载配置参数
 * @author duanledexianxianxian
 */
@Component
@ConfigurationProperties(prefix = "system")
@PropertySource(value = { "/config/system.properties" })
@Data
public class SystemPropertySourceValueProperties {
    /**
     * 名称
     */
    @Value("${name}")//最好设置默认值，否则配置文件即使有相应key，也会应用启动失败
    private String name;
    /**
     * 编码
     */
    @Value("${code}")
    private String code;
}
```

如果需要按照模块读取配置文件，比如system.properties进行系统相关配置；mail.properties进行邮件相关配置。我们可以使用@PropertySource注解读取模块单独配置文件。

为@ConfigurationProperties注解设置prefix属性，可以减少输入重复@value前缀。

> @PropertySource不支持yml文件，只支持读取properties文件。
>
> 题外话：我们可以使用@PropertySource读取任意配置properties文件。

#### 3. Environment

在需要使用配置项的地方，注入环境变量，读取配置属性。

```java
@Autowired
private Environment env;
 
// 获取参数
String getProperty(String key);
```

代码可读性差。

#### 4. @ConfigurationProperties注解（`推荐`）

我们可以根据业务逻辑将定义一个模块相关的属性配置类。比如系统配置类：

```
/**
 * 系统配置类
 * @author duanledexianxianxian
 */
@Component
@ConfigurationProperties(prefix = "system")
@Data
public class SystemProperties {
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
}
```

在需要读取系统配置类的时候，直接通过@Autowired注入即可。

```
@Autowired
private SystemProperties systemProperties;
```

如果配置文件已经按照模块进行了多个文件的切分，可以配合@PropertySource注解读取配置文件。

@ConfigurationProperties的prefix用于指定属性key的公共前缀。

**@ConfigurationProperties  VS  @value**

1. @ConfigurationProperties配置集中化。所有关于system相关的属性，我们都可以在SystemProperties中获取，一目了然，代码可读性高。如果需要修改属性key，不需要找到所有引用key的地方。
2. @ConfigurationProperties即使配置文件中没有相关key，也不会导致应用启动失败。

**代码地址：**

## 总结

平常在项目开发中，一般读取的是classpath下面的application.properties/yml文件。

1. `优先使用@ConfigurationProperties来读取配置。`

   根据模块定义一个属性配置类，用于设置以及获取模块相关属性，放在config包下，类命名规则：模块名称+Properties。比如SystemProperties。

2. `如果使用@value注解，切记设置默认值,否则在某些情况下导致应用启动失败。`

对于按模块切分多个配置文件的配置读取。优先使用@ConfigurationProperties+@PropertySource方式，切记@PropertySource只能读取properties文件。





## [参考链接](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/)

1. [SpringBoot读取配置文件的6种方式](https://my.oschina.net/imlim/blog/1859091)

2. [Spring Boot官方参考文档](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html)

   

   

   