package com.example.retrofit_proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by cgy on 2018/10/31.
 */
public class ServiceMethod {
    private Builder mBuilder;

    public ServiceMethod(Builder builder) {
        this.mBuilder = builder;
    }

    /**
     * 获取具体网络请求类型
     *
     * @return methodName
     */
    public String getMethodName() {
        return mBuilder.methodName;
    }

    /**
     * @return 请求url，这里GET请求我们需要进行url参数拼接，在实际源码中我们其实是在Okhttpcall中调用requestbuilder进行统一拼接的。
     */
    public String getBaseUrl() {

        if (mBuilder.methodName.equals("GET")) {

            StringBuffer sb = new StringBuffer();
            sb.append(mBuilder.retrofit.getBaseUrl())
                    .append(mBuilder.relativeUrl);

            Map<String, Object> parameterMap = getParameter();

            if (parameterMap != null) {
                Set<String> keySet = parameterMap.keySet();
                if (keySet.size() > 0) {
                    sb.append("?");
                }
                for (String key : keySet) {
                    sb.append(key).append("=").append(parameterMap.get(key)).append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        }

        return mBuilder.retrofit.getBaseUrl();
    }

    public Map<String, Object> getParameter() {
        return mBuilder.parameterMap;
    }

    /**
     * 用于解析注解
     */
    static final class Builder {

        final Retrofit retrofit;
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;

        private Map<String, Object> parameterMap = new HashMap<>();
        private Object[] args;
        private String methodName;
        private String relativeUrl;

        Builder(Retrofit retrofit, Method method, Object[] args) {
            this.retrofit = retrofit;
            this.method = method;
            // 方法注解列表
            this.methodAnnotations = method.getAnnotations();
            // 方法参数注解列表
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            // 方法参数内容列表
            this.args = args;
        }


        public ServiceMethod build() {

            // 遍历方法注解
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            // 遍历方法参数注解
            int parameterCount = parameterAnnotationsArray.length;
            for (int p = 0; p < parameterCount; p++) {
                Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
                parseParameter(p, parameterAnnotations);
            }

            return new ServiceMethod(this);
        }

        /**
         * 解析方法注解，获取方法注解中的值，用于后续拼接url地址
         *
         * @param annotation
         */
        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value());
            }
        }


        private void parseHttpMethodAndPath(String httpMethod, String value) {
            if (httpMethod.equals("GET")) {
                methodName = "GET";
                this.relativeUrl = value;
            }
        }

        /**
         * 解析方法参数注解
         *
         * @param p                    方法参数值的index
         * @param parameterAnnotations 方法参数注解数组
         */
        private void parseParameter(int p, Annotation[] parameterAnnotations) {
            // 方法参数值
            Object value = args[p];
            // 遍历参数注解
            for (Annotation annotation : parameterAnnotations) {
                //首先需要判断参数注解类型
                if (annotation instanceof Field) {
                    Field field = (Field) annotation;
                    // 参数名称（接口参数名称）
                    String key = field.value();
                    parameterMap.put(key, value);
                }
            }
        }
    }


}
