package com.lic.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.lic.service.UserService;

import java.io.IOException;
public class DubboClient {
    /**
     * 基于Url构建远程服务
     * @param remoteUrl
     * @return
     */
    public UserService buildRemoteService(String remoteUrl) {
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig();
        //设置注册中心, 从广播中接收信息
        referenceConfig.setRegistry(new RegistryConfig("multicast://224.1.2.3:11111"));
        referenceConfig.setApplication(new ApplicationConfig("client-app"));
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setUrl(remoteUrl);
        referenceConfig.setLoadbalance("roundrobin");
        return referenceConfig.get();
    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }

    public static void main(String[] args) throws IOException {
        DubboClient client = new DubboClient();
        UserService userService = client.buildRemoteService(null);
        String cmd;
        int count = 0;
        while (!(cmd = read()).equals("exit")) {
            System.out.println( userService.selectUser() );

        }
    }

}
