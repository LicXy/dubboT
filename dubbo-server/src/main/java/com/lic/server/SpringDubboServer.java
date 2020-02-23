package com.lic.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringDubboServer {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-provide.xml");
        ((ClassPathXmlApplicationContext) context).start();
        System.in.read();
    }

}
