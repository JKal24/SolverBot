package com.astro.smitesolver.events;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class MessageEventListener implements EventListener<MessageCreateEvent> {

    @Autowired
    private MessageProcessor processor;

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        // This event listener will only register commands starting with s! for Smite related commands
        // Any other types of commands will have a different prefix and a different listener.
        Message message = event.getMessage();

        // Standard response loop
        return Mono.just(message)
                .filter(checkMessage -> checkMessage.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(checkMessage -> checkMessage.getContent().startsWith("s!"))
                .flatMap(Message::getChannel)
                .flatMap(messageChannel -> messageChannel.createEmbed(embedCreateSpec -> {
                    String[] commands = message.getContent().substring(2)
                            .split(" ");

                    embedCreateSpec.setAuthor("Smite Solver :: Commands with s!", null, null);

                    try {
                        for (Map.Entry<String, Future<String>> entry : processor.processSolverEvent(commands).entrySet()) {
                            embedCreateSpec.addField(entry.getKey(), entry.getValue().get(), true);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        embedCreateSpec.addField(processor.getInfoName(commands[0]), "Error accessing data", false);
                    }
                }))
                .then();
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> handleError(Throwable error) {
        return EventListener.super.handleError(error);
    }

}
