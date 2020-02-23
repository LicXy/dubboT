package com.lic.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.lic.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DubboClientNoRegistry {
    /**
     * 基于Url构建远程服务
     * @param remoteUrl
     * @return
     */
    public UserService buildRemoteService(String remoteUrl) {
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig();
        referenceConfig.setApplication(new ApplicationConfig("client-app"));
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setUrl(remoteUrl);

        return referenceConfig.get();
    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }

    public static void main(String[] args) throws IOException {
        DubboClientNoRegistry client = new DubboClientNoRegistry();
        List<UserService> services = new ArrayList<UserService>();
        services.add(client.buildRemoteService("dubbo://127.0.0.1:20880/com.lic.service.UserService"));
        services.add(client.buildRemoteService("dubbo://127.0.0.1:20881/com.lic.service.UserService"));
        services.add(client.buildRemoteService("dubbo://127.0.0.1:20882/com.lic.service.UserService"));

        String cmd;
        int count = 0;
        while (!(cmd = read()).equals("exit")) {
            /**
             * 使用模进行轮询调用
             */
            System.out.println( services.get(count++ % services.size()).selectUser() );

        }
    }

}
