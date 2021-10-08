package org.wayne.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wayne.api.entity.RespBeanQ;
import org.wayne.roboot.entity.Content;
import org.wayne.roboot.entity.FeishuEnums;
import org.wayne.roboot.entity.FeishuMsg;
import org.wayne.roboot.service.SendFeishu;

import java.io.IOException;

/**
 * @Description:
 * @author: lwq
 */
@RestController
@RequestMapping("/feishu")
@Slf4j
public class CallRobootController {

    @Autowired
    SendFeishu sendFeishu;
    final String demoUrl = "https://open.feishu.cn/open-apis/bot/v2/hook/aa82eb74-d55f-4276-bbc7-aba55eafc9b0";

    @RequestMapping("/text")
    public RespBeanQ callText(String url, String contentText) {
        try {
            if (url != null) {
                sendFeishu.setUrl(url);
            } else {
                sendFeishu.setUrl(demoUrl);
            }
            final FeishuMsg msg = new FeishuMsg(FeishuEnums.text.getValue(), new Content(contentText));
            final String content = JSONObject.toJSONString(msg);
            log.info("发送消息json:{}", content);
            sendFeishu.setContent(content);
            sendFeishu.send();
        } catch (IOException e) {
            log.error("飞书发送异常", e);
            return RespBeanQ.error();
        }
        return RespBeanQ.ok();
    }
}
