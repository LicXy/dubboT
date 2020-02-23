package com.lic.serviceImpl;

import com.lic.service.UserService;

public class UserServiceImpl implements UserService {
    private int port;
    public String selectUser() {
        System.out.println("=====服务端逻辑执行=====");
        return "远程服务已被调用, 服务端口为:"+port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
