package sia5;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

@Configuration
public class FileWriterIntegrationConfig {
  @Bean
  public AtomicInteger source() {
    return new AtomicInteger();
  }
  @Bean
  @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
  public GenericTransformer<String, String> upperCaseTransformer() {
    return String::toUpperCase;
  }

  @Bean
  @ServiceActivator(inputChannel = "fileWriterChannel")
  public FileWritingMessageHandler fileWriter() {
    FileWritingMessageHandler handler =
        new FileWritingMessageHandler(new File("/tmp/sia5/files"));
    handler.setExpectReply(false);
    handler.setFileExistsMode(FileExistsMode.APPEND);
    handler.setAppendNewLine(true);
    return handler;
  }

  @Bean
  public IntegrationFlow fileWriterFlow() {
    return IntegrationFlows
        .from(MessageChannels.direct("textInChannel"))
        .<String, String>transform(String::toUpperCase)
        .channel(MessageChannels.direct("fileWriterChannel"))
        .handle(Files
            .outboundAdapter(new File("/tmp/sia5/files"))
            .fileExistsMode(FileExistsMode.APPEND)
            .appendNewLine(true))
        .get();
  }

  @Bean
  public MessageChannel orderChannel() {
    return new PublishSubscribeChannel();
  }

  @Filter(inputChannel = "numberChannel", outputChannel = "even")
  public boolean evenNumberFilter(Integer number) {
    return number % 2 == 0;
  }

  public IntegrationFlow evenNumberFilterFlow(AtomicInteger number) {
    return IntegrationFlows
        .from(MessageChannels.direct("numberChannel"))
        .<Integer>filter(p -> p % 2 == 0)
        .get();

  }

  @Bean
  @Transformer(inputChannel = "numberChannel", outputChannel = "romanNumberChannel")
  public GenericTransformer<Integer, String> romanNumTransformer() {
    return null;
  }

  @Bean
  @Router(inputChannel = "numberChannel")
  public AbstractMessageRouter evenOddRouter() {
    return new AbstractMessageRouter() {
      @Override
      protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
        Integer num = (Integer) message.getPayload();
        if (num % 2 == 0) {
          return Collections.singleton(evenChannel());
        }
        return Collections.singleton(oddChannel());
      }
    };
  }

  @Bean
  public MessageChannel evenChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel oddChannel() {
    return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "someChannel")
  public MessageHandler sysoutHandler() {
    return message -> System.out.println("Message payload: " + message.getPayload());
  }

  @Bean
  public IntegrationFlow upperCaseFlow() {
    return IntegrationFlows
        .from("inChannel")
        .<String, String>transform(String::toUpperCase)
        .channel("outputChannel")
        .get();
  }

  @Bean
  @InboundChannelAdapter(
      poller = @Poller(fixedRate = "1000"), channel = "numberChannel"
  ) public MessageSource<Integer> numberSource(AtomicInteger source) {
    return () -> new GenericMessage<>(source.getAndIncrement());
  }

  @Bean
  @InboundChannelAdapter(channel = "file-channel",poller = @Poller(fixedDelay = "1000"))
  public MessageSource<File> fileReadingMessageSource() {
    FileReadingMessageSource sourceReader = new FileReadingMessageSource();
    sourceReader.setDirectory(new File(""));
    sourceReader.setFilter(new SimplePatternFileListFilter(""));
    return sourceReader;
  }

}
