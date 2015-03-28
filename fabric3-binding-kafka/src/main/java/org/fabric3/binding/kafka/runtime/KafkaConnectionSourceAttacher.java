package org.fabric3.binding.kafka.runtime;

import org.fabric3.api.annotation.wire.Key;
import org.fabric3.binding.kafka.provision.KafkaConnectionSource;
import org.fabric3.spi.container.builder.component.SourceConnectionAttacher;
import org.fabric3.spi.container.channel.ChannelConnection;
import org.fabric3.spi.model.physical.PhysicalConnectionTarget;
import org.fabric3.spi.runtime.event.EventService;
import org.oasisopen.sca.annotation.Reference;

/**
 *
 */
@Key("org.fabric3.binding.kafka.provision.KafkaConnectionSource")
public class KafkaConnectionSourceAttacher implements SourceConnectionAttacher<KafkaConnectionSource> {

    @Reference
    protected EventService eventService;

    @Reference
    protected KafkaConnectionManager connectionManager;

    public void attach(KafkaConnectionSource source, PhysicalConnectionTarget target, ChannelConnection connection) {
        if (!source.isDirectConnection()) {
            connectionManager.subscribe(source, connection);
        } else {
            connectionManager.getConsumer(source); // create consumer
        }
    }

    public void detach(KafkaConnectionSource source, PhysicalConnectionTarget target) {
        connectionManager.releaseConsumer(source.getChannelUri());
    }
}
