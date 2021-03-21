package com.larch.java.week01;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


/**
 * @author ll
 * @create 2021-03-17 21:49
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String fileToBase64 = HelloClassLoader.fileToBase64("resources/Hello.xlass");
//        System.out.println(fileToBase64);
//        String fileToBase64 = "NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfWqf7/+7yQm5r+//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+//W3mpOTkNGVnome8//4//f4/+nz/+j/5/7/7Leak5OQ09+ck56MjLOQnpuajd74/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk56RmNCshoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+NlpGLrIuNmp6S/v/4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/+f///////f/+//j/9//+//b////i//7//v////rVSP/+Tv////7/9f////n//v////7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9////+//3//r//v/z/////f/y";

//        new HelloClassLoader().findClass("Hello",fileToBase64).newInstance();
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class<?> hello = (Class<?>) helloClassLoader.findClass("Hello", fileToBase64);
        Object obj = hello.newInstance();
//
        Method hello1 = hello.getDeclaredMethod("hello");
        hello1.setAccessible(true);
        hello1.invoke(obj);


    }

    private Class<?> findClass(String name,String filePath) throws ClassNotFoundException {

        byte[] decode = decode(filePath);
        return defineClass(name,decode,0,decode.length);
    }

    private byte[] decode(String base64) {
        byte[] decode = Base64.getDecoder().decode(base64);
        for (int i = 0; i < decode.length; i++) {
            decode[i] = (byte)- (decode[i] - 255);
        }
        return decode;
    }

    /**
     * 文件转base64字符串
     * @param filePath
     * @return
     */
    public static String fileToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
