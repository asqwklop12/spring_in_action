package klom.spring.spring_in_action.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.messaging.converter.MessageConversionException;

public interface MessageConverter {

  Message toMessage(Object object, Session session) throws JMSException, MessageConversionException;

  Object fromMessage(Message message);
}
