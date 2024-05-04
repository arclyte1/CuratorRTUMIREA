package com.example.curatorrtumirea.data.serializer

import com.example.curatorrtumirea.domain.model.EventType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object EventTypeSerializer : KSerializer<EventType> {
    override val descriptor = PrimitiveSerialDescriptor("EventType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): EventType {
        return try {
            val value = decoder.decodeString()
            EventType.valueOf(value)
        } catch (e: Exception) {
            EventType.OTHER
        }
    }

    override fun serialize(encoder: Encoder, value: EventType) {
        return encoder.encodeString(value.name)
    }
}