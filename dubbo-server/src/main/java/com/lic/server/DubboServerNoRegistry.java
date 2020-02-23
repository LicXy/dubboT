package com.lic.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lic.service.UserService;
import com.lic.serviceImpl.UserServiceImpl;

import java.io.IOException;

public class DubboServerNoRegistry {

    public void openService(int port){
        //服务配置
        ServiceConfig<UserService> serviceConfig = new ServiceConfig();
        //设置服务接口
        serviceConfig.setInterface(UserService.class);
        //设置开放协议
        serviceConfig.setProtocol(new ProtocolConfig("dubbo",port));

        //设置一个空的注册中心
        serviceConfig.setRegistry(new RegistryConfig(RegistryConfig.NO_AVAILABLE));

        //设置服务所在当前应用
        serviceConfig.setApplication(new ApplicationConfig("server-app"));

        UserServiceImpl ref = new UserServiceImpl();
        //设置服务实现对象
        serviceConfig.setRef(ref);
        //暴露服务
        serviceConfig.export();
        System.out.println("服务已开启! 端口号为:"+serviceConfig.getExportedUrls().get(0).getPort());
        ref.setPort(serviceConfig.getExportedUrls().get(0).getPort());
    }

    public static void main(String[] args) throws IOException {
        new DubboServerNoRegistry().openService(-1);
        System.in.read();
    }

}
