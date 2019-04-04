package sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: ztq161024zsc
 * @DATE: 2019/3/18
 * @TIME: 16:39
 * @DAY: 18
 * @PROJECT_NAME: tensquare_parent
 * @PACKAGE_NAME: sms
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    public void sendSms(Map<String, String> message) throws ClientException {
        String mobile = message.get("mobile");
        String code = message.get("code");
        System.out.println("手机号:   " + message.get("mobile"));
        System.out.println("验证码:   " + message.get("code"));
        //{\"number\":\""+ map.get("code") +"\"}"
        smsUtil.sendSms(mobile, template_code, sign_name, "{\"code\":\""+code+"\"}");
    }
}
