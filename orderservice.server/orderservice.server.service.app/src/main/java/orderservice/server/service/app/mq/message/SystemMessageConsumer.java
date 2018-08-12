package orderservice.server.service.app.mq.message;

import org.springframework.amqp.core.Message;

import orderservice.server.core.mq.rabbit.MessageConsumerBase;

public class SystemMessageConsumer extends MessageConsumerBase {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

}
