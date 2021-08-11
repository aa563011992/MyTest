package com.example.myapplication;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
    public static final Class<?>[] a = new Class[0];
    public static final Object[] b = new Object[0];

    public static Field a(Class<?> cls, String str) {
        return b(cls, str, true, true);
    }

    public static Field b(Class<?> cls, String str, boolean z, boolean z2) {
        Field declaredField = null;
        Field field = null;
        try {
            if (z) {
                try {
                    declaredField = cls.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                    if (!z2) {
                        return null;
                    }
                    Class superclass = cls.getSuperclass();
                    while (field == null && superclass != null) {
                        Field declaredField2 = null;
                        try {
                            if (z) {
                                try {
                                    declaredField2 = superclass.getDeclaredField(str);
                                } catch (NoSuchFieldException unused2) {
                                    superclass = superclass.getSuperclass();
                                }
                            } else {
                                declaredField2 = superclass.getField(str);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        field = declaredField2;
                    }
                    return field;
                }
            }
            declaredField = cls.getField(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return declaredField;
    }

    public static Method c(Class<?> cls, String str) {
        return d(cls, str, a);
    }

    public static Method d(Class<?> cls, String str, Class<?>... clsArr) {
        return e(cls, true, true, str, clsArr);
    }

    public static Method e(Class<?> cls, boolean z, boolean z2, String str, Class<?>... clsArr) {
        Method declaredMethod = null;
        Method method = null;
        try {
            if (z) {
                try {
                    declaredMethod = cls.getDeclaredMethod(str, clsArr);
                } catch (NoSuchMethodException unused) {
                    if (!z2) {
                        return null;
                    }
                    Class superclass = cls.getSuperclass();
                    while (method == null && superclass != null) {
                        Method declaredMethod2 = null;
                        if (z) {
                            try {
                                declaredMethod2 = superclass.getDeclaredMethod(str, clsArr);
                            } catch (NoSuchMethodException unused2) {
                                superclass = superclass.getSuperclass();
                            }
                        } else {
                            declaredMethod2 = superclass.getMethod(str, clsArr);
                        }
                        method = declaredMethod2;
                    }
                    return method;
                }
            }
            declaredMethod = cls.getMethod(str, clsArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return declaredMethod;
    }

    public static Object f(Object obj, String str) {
        if (!(obj == null || TextUtils.isEmpty(str))) {
            try {
                Field b = b(obj.getClass(), str, true, true);
                if (b == null) {
                    return null;
                }
                b.setAccessible(true);
                return b.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> Object g(Object obj, String str, Class<T> cls) {
        if (!(obj == null || TextUtils.isEmpty(str) || cls == null)) {
            try {
                Field b = b(obj.getClass(), str, true, true);
                if (b == null) {
                    return null;
                }
                b.setAccessible(true);
                return b.get(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object h(Method method, Object obj) {
        try {
            return method.invoke(obj, b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}