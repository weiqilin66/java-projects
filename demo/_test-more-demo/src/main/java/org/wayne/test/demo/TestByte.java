package org.wayne.test.demo;

import java.nio.charset.StandardCharsets;

public class TestByte {
    public static void main(String[] args) {
        final byte[] bytes = "Hello world".getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes);
        final String str = new String(bytes);
        System.out.println(str);
    }
}
