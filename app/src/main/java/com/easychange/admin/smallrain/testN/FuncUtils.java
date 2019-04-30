package com.easychange.admin.smallrain.testN;

import java.lang.reflect.Method;

public class FuncUtils {

    public static Method funcMethod(Class targetClass, String methodName, Class... objects) {
        Method method = null;
        try {
            method = targetClass.getDeclaredMethod(methodName, objects);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
            return method;
        }
    }

    public static Object funcInvoke(Object targetObject, Method method, Object... objects) {
        Object object = null;
        try {
            object = method.invoke(targetObject, objects);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return object;
        }
    }
}
