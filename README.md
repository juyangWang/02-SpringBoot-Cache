# Spring Boot 使用最最简单的缓存的例子（使用内存）

启动本地 Redis 的命令
```java
redis-server ~/myconf/redis.conf
```



说明：放入缓存的对象，再次访问的时候，就不会有 Hibernate 的 SQL 打印出来。
当我们执行了删除缓存的方法以后，再次执行查询，就会看到有 Hibernate 的 SQL 打印出来。


注意：如果我们使用 Redis 作为我们的缓存策略，我们须要配置存放入 Redis 的序列化策略。

```java
2017-03-12 08:18:12.151 ERROR 80878 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.data.redis.serializer.SerializationException: Cannot serialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to serialize object using DefaultSerializer; nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.liwei.entity.Person]] with root cause

java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.liwei.entity.Person]
	at org.springframework.core.serializer.DefaultSerializer.serialize(DefaultSerializer.java:43) ~[spring-core-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.core.serializer.support.SerializingConverter.convert(SerializingConverter.java:63) ~[spring-core-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.core.serializer.support.SerializingConverter.convert(SerializingConverter.java:35) ~[spring-core-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.data.redis.serializer.JdkSerializationRedisSerializer.serialize(JdkSerializationRedisSerializer.java:90) ~[spring-data-redis-1.7.5.RELEASE.jar:na]
	at org.springframework.data.redis.cache.RedisCache$CacheValueAccessor.convertToBytesIfNecessary(RedisCache.java:375) ~[spring-data-redis-1.7.5.RELEASE.jar:na]
	at org.springframework.data.redis.cache.RedisCache$BinaryRedisCacheElement.<init>(RedisCache.java:408) ~[spring-data-redis-1.7.5.RELEASE.jar:na]
	at org.springframework.data.redis.cache.RedisCache.put(RedisCache.java:173) ~[spring-data-redis-1.7.5.RELEASE.jar:na]
	at org.springframework.data.redis.cache.RedisCache.put(RedisCache.java:157) ~[spring-data-redis-1.7.5.RELEASE.jar:na]
	at org.springframework.cache.interceptor.AbstractCacheInvoker.doPut(AbstractCacheInvoker.java:85) ~[spring-context-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.cache.interceptor.CacheAspectSupport$CachePutRequest.apply(CacheAspectSupport.java:784) ~[spring-context-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.cache.interceptor.CacheAspectSupport.execute(CacheAspectSupport.java:417) ~[spring-context-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.cache.interceptor.CacheAspectSupport.execute(CacheAspectSupport.java:327) ~[spring-context-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.cache.interceptor.CacheInterceptor.invoke(CacheInterceptor.java:61) ~[spring-context-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) ~[spring-aop-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:655) ~[spring-aop-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at com.liwei.service.PersonServiceimpl$$EnhancerBySpringCGLIB$$e123f4c5.findOne(<generated>) ~[main/:na]
	at com.liwei.web.PersonController.getFromCache(PersonController.java:39) ~[main/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_45]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_45]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_45]
	at java.lang.reflect.Method.invoke(Method.java:497) ~[na:1.8.0_45]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:220) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:134) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:116) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:827) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:738) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:85) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:963) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:897) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:970) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:861) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:622) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:846) ~[spring-webmvc-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:729) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:230) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52) ~[tomcat-embed-websocket-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:89) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:77) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:197) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107) ~[spring-web-4.3.4.RELEASE.jar:4.3.4.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198) ~[tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:108) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:472) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:349) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:784) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:802) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1410) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [na:1.8.0_45]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [na:1.8.0_45]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-8.5.6.jar:8.5.6]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_45]
```

参考资料：
Spring Cache抽象详解
http://jinnianshilongnian.iteye.com/blog/2001040