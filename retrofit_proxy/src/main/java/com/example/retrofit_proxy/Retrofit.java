package com.example.retrofit_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cgy on 2018/10/31.
 */
public final class Retrofit {
    /**
     * 该map的作用用于缓存Retrofit解析以后的方法，因为注解解析是相对耗时的。
     */
    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    private String baseUrl;

    public Retrofit(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * 框架核心方法，用于构建接口实例
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        // 动态代理
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        //1 验证是否是接口
                        if (!service.isInterface()) {
                            throw new IllegalArgumentException("API declarations must be interfaces.");
                        }

                        //2 进行构建接口实例，同时进行方法注解、方法参数注解解析，这些参数解析完成以后，我们就可以把注解解析后的url进行拼接
                        ServiceMethod serviceMethod =
                                loadServiceMethod(method, args);

                        //3 调用 http 框架执行网络请求
                        OkHttpCall okHttpCall = new OkHttpCall(serviceMethod);

                        return okHttpCall;
                    }
                }
        );
    }

    /**
     * 进行构建接口实例
     *
     * @param method  调用的接口方法
     * @param args 方法参数内容
     * @return
     */
    private ServiceMethod loadServiceMethod(Method method, Object[] args) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null)
            return result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method, args).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }


    /**
     * Retrofit 的构建类，除了 baseUrl 以外，其他的配置参数都是可选的，在这里我们只添加 baseUrl。
     */
    public static final class Builder {

        private String baseUrl;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Retrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            return new Retrofit(baseUrl);
        }
    }

}
