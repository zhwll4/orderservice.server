package orderservice.server.service.app.mq.message;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import orderservice.server.core.mq.rabbit.MessageSenderBase;

public class MessageSender extends MessageSenderBase {

	
	private static AmqpTemplate orderTopicTemplate;
	
	@Resource(name = "orderTopicTemplate")
	public void setOrderTopicTemplate(AmqpTemplate amqpTemplate){
		MessageSender.orderTopicTemplate = amqpTemplate;
	}
	
	
	public static void sendOrderStatueMessage(String msflag,Object data){
		MessageSender.orderTopicTemplate.convertAndSend("orderservice.user.order.statue."+(StringUtils.isEmpty(msflag)?"default":msflag), data);
	}
	
}
