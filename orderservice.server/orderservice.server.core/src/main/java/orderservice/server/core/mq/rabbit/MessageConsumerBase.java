package orderservice.server.core.mq.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public abstract class MessageConsumerBase implements MessageListener {

	@Override
	public abstract void onMessage(Message message) ;

}
