package org.wayne.roboot;

import com.alibaba.fastjson.JSONObject;
import org.wayne.roboot.entity.Content;
import org.wayne.roboot.entity.FeishuEnums;
import org.wayne.roboot.entity.FeishuMsg;
import org.wayne.roboot.service.SendFeishu;

import java.io.IOException;

/**
 * @Description:
 * @author: lwq
 */
public class doSendDemo {
    public static void main(String[] args) {
        final SendFeishu sendFeishu = new SendFeishu();
        try {
            sendFeishu.setUrl("https://open.feishu.cn/open-apis/bot/v2/hook/aa82eb74-d55f-4276-bbc7-aba55eafc9b0");
            final FeishuMsg msg = new FeishuMsg(FeishuEnums.text.getValue(), new Content("五块钱如何花3天分享"));
            final String content = JSONObject.toJSONString(msg);
            System.out.println(content);
            sendFeishu.setContent(content);
            sendFeishu.send();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
