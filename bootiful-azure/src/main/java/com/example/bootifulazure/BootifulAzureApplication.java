package com.example.bootifulazure;

import com.microsoft.azure.storage.blob.ContainerURL;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.ByteBuffer;
import java.time.Instant;

@EnableBinding({Source.class, Sink.class})
@SpringBootApplication
public class BootifulAzureApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulAzureApplication.class, args);
    }
}


@Log4j2
@RestController
@RequiredArgsConstructor
class SpringCloudStreamServiceBusDemo {

    private final Source source;

    @GetMapping("/send")
    public void send() {
        this.source.output().send(MessageBuilder.withPayload("Hello bootiful Azure Service Bus @ " + Instant.now() + "!").build());
    }

    @StreamListener(Sink.INPUT)
    public void incoming(org.springframework.messaging.Message<?> message) {
        log.info("new message: " + message.getPayload());
        message.getHeaders().forEach((k, v) -> {
            log.info(k + '=' + v);
        });
    }
}


@Log4j2
@Configuration
class ObjectStorageServiceDemo {

    // todo in order for this to work make sure that you click on the container ('cats'), choose 'Change Access Level',
    //  and set it to 'Container (anonymouse read access for containers and blobs)'
    @SneakyThrows
    ObjectStorageServiceDemo(ContainerURL containerURL, @Value("classpath:/cat.jpg") Resource cat) {
        var bytes = FileCopyUtils.copyToByteArray(cat.getFile());
        var blockBlobUploadResponse = containerURL//
                .createBlockBlobURL("cat.jpg")//
                .upload(Flowable.just(ByteBuffer.wrap(bytes)), bytes.length, null, null, null, null)//
                .blockingGet();
        log.info(blockBlobUploadResponse.toString());
    }
}