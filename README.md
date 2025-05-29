# logback-mdc-ttl

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.ofpay/logback-mdc-ttl/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.ofpay/logback-mdc-ttl/)
[![GitHub release](https://img.shields.io/github/release/ofpay/logback-mdc-ttl.svg)](https://github.com/ofpay/logback-mdc-ttl/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

:point_right: `logback`扩展，支持跨线程池的mdc跟踪。

> 实现上集成使用了[Transmittable ThreadLocal(TTL)](https://github.com/alibaba/transmittable-thread-local) ：在使用线程池等会缓存线程的组件情况下，提供ThreadLocal值的传递功能，解决异步执行时上下文传递的问题。支持 `Java 11~21`。

> [!NOTE]
> 从`v2.0+`开始，升级到`Java 11`。🚀  
> 如果需要`Java 8`的支持，使用版本`1.0.x`

---

## 1.项目依赖

```xml
<dependency>
    <groupId>com.ofpay</groupId>
    <artifactId>logback-mdc-ttl</artifactId>
    <version>2.0.0</version>
</dependency>
```

## 2. 在`Java`的启动参数加上：

- `-Xbootclasspath/a:/path/to/transmittable-thread-local-2.x.x.jar`
- `-javaagent:/path/to/transmittable-thread-local-2.x.x.jar`  
- 强制指定使用哪个 SLF4JServiceProvider 实现，从而避免多个绑定时的不确定性问题
- `-Dslf4j.provider=com.ofpay.logback.TtlSlf4JServiceProvider`

## 3. logback 示例

```xml
<configuration scan="true" scanPeriod="30 seconds">
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--例子:  %X{uuid} 支持在跨线程池时传递-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS}|%X{uuid}|%level|%M|%C:%L|%thread|%msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>

    <logger name="org.springframework" level="INFO" />
    <logger name="com.ofpay" level="DEBUG" />
</configuration>
```

## JDK 17 + springboot3.x 示例
- 参考test: `com.ofpay.logback.springboot3`