# logback-mdc-ttl

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.ofpay/logback-mdc-ttl/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.ofpay/logback-mdc-ttl/)
[![GitHub release](https://img.shields.io/github/release/ofpay/logback-mdc-ttl.svg)](https://github.com/ofpay/logback-mdc-ttl/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

:point_right: `logback`扩展，支持跨线程池的mdc跟踪。

> 实现上集成使用了[Transmittable ThreadLocal(TTL)](https://github.com/alibaba/transmittable-thread-local) ：在使用线程池等会缓存线程的组件情况下，提供ThreadLocal值的传递功能，解决异步执行时上下文传递的问题。支持JDK 9/8/7/6。

# 示例

## 1.项目依赖

```xml
<dependency>
    <groupId>com.ofpay</groupId>
    <artifactId>logback-mdc-ttl</artifactId>
    <version>1.0.2</version>
</dependency>
```

## 2. 在`Java`的启动参数加上：

- `-Xbootclasspath/a:/path/to/transmittable-thread-local-2.x.x.jar`
- `-javaagent:/path/to/transmittable-thread-local-2.x.x.jar`

## 3. 在logback配置文件中增加`TtlMdcListener`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration >
    <!-- ...(略) -->
    <contextListener class="com.ofpay.logback.TtlMdcListener"/>

    <!--例子:  %X{uuid} 支持在跨线程池时传递-->
    <property scope="context" name="APP_PATTERN"
              value='%d{yyyy-MM-dd HH:mm:ss.SSS}|%X{uuid}|%level|%M|%C\:%L|%thread|%replace(%.-2000msg){"(\r|\n)","\t"}|"%.-2000ex{full}"%n'/>
</configuration>
```
