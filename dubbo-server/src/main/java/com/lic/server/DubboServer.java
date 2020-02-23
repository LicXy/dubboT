package com.lic.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.lic.service.UserService;
import com.lic.serviceImpl.UserServiceImpl;

import java.io.IOException;

/**
 * 疑问:
 *    1. 一个服务对应一个ServiceConfig, 每个服务需要暴露一个端口, 如果有很多个服务呢 ?
 *     答: 这里的每个服务是指服务类, 所以可以在接口中定义多个服务处理方法,即可
 *    2. 服务端和客户端都需要依赖于服务接口, 那么在项目部署时是不是也需要在两端都进行接口部署 ?
 *    答: 可以把接口以及其他公用部分打包, 部署在客户端和服务端
 */
public class DubboServer {

    public void openService(int port){
        //服务配置
        ServiceConfig<UserService> serviceConfig = new ServiceConfig();
        /**
         * 设置服务接口
         */
        serviceConfig.setInterface(UserService.class);
        /**
         * 设置开放协议
         */
        serviceConfig.setProtocol(new ProtocolConfig("dubbo",port));

        /**
         * 使用multicast广播注册中心暴露服务地址
         */
        serviceConfig.setRegistry(new RegistryConfig("multicast://224.1.2.3:11111"));

        /**
         * 设置服务所在当前应用
         */
        serviceConfig.setApplication(new ApplicationConfig("server-app"));

        UserServiceImpl ref = new UserServiceImpl();
        /**
         * 设置服务实现对象
         */
        serviceConfig.setRef(ref);
        //暴露服务
        serviceConfig.export();
        System.out.println("服务已开启! 端口号为:"+serviceConfig.getExportedUrls().get(0).getPort());
        ref.setPort(serviceConfig.getExportedUrls().get(0).getPort());
    }

    public static void main(String[] args) throws IOException {
        new DubboServer().openService(-1);
        System.in.read();
    }

}
