package klom.spring.spring_in_action;

import java.util.HashMap;
import java.util.Map;
import javax.jms.Destination;
import klom.spring.spring_in_action.Ingredient.Type;
import klom.spring.spring_in_action.data.IngredientRepository;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

@SpringBootApplication
public class SpringInActionApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringInActionApplication.class, args);
  }

  @Bean
  public Destination orderQueue() {
    return new ActiveMQQueue("tacocloud.order.queue");
  }

  @Bean
  public MessageConverter messageConverter() {
    var messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setTypeIdPropertyName("_typeId");
    Map<String, Class<?>> typeIdMappings = new HashMap<>();
    messageConverter.setTypeIdMappings(typeIdMappings);
    return messageConverter;
  }

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
        repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
        repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
      }
    };
  }
}
