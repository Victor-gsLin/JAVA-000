package com.victor.geektime.java.class1;

import java.io.*;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{

    private static final String FILE_PATH = "/Users/victor/CodeData/GeekTime/java/class1/src/main/java/com/victor/geektime/java/class1/Hello.xlass";

    public static void main(String[] args) throws Exception {
        Class<?> class_ = new HelloClassLoader().findClass("Hello");
        Method method = class_.getMethod("hello");
        method.setAccessible(true);
        method.invoke(class_.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(FILE_PATH);
        byte[] decodeBytes = dealBytes(getBytes(file));
        return defineClass(name, decodeBytes, 0, decodeBytes.length);
    }

    private byte[] getBytes(File file) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                ByteArrayOutputStream out = new ByteArrayOutputStream(1024)) {
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private byte[] dealBytes(byte[] bytes) {
        int length = bytes.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte)(255 - bytes[i]);
        }
        return result;
    }

}
