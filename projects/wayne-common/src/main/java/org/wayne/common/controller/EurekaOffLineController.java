package org.wayne.common.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @author: lwq
 */
public class EurekaOffLineController {
    @Autowired
    ApplicationInfoManager applicationInfoManager;

    @GetMapping( "/offline")
    public void offLine(){
        applicationInfoManager.getInfo().setStatus(InstanceInfo.InstanceStatus.DOWN);
    }
    @GetMapping( "/online")
    public void onLine(){
        applicationInfoManager.getInfo().setStatus(InstanceInfo.InstanceStatus.UP);
    }
}
