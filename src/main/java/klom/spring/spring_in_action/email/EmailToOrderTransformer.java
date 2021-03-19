package klom.spring.spring_in_action.email;

import klom.spring.spring_in_action.Order;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EmailToOrderTransformer  {

  protected AbstractIntegrationMessageBuilder<Order> doTransform(Message message)
      throws Exception {
    return null;
  }

}
