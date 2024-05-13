package com.example.curatorrtumirea.data.serializer

import android.util.Log
import com.example.curatorrtumirea.domain.model.EventType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object EventTypeSerializer : KSerializer<EventType> {
    override val descriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): EventType {
        return try {
            val value = decoder.decodeString()
            Log.d(this.javaClass.simpleName, value)
            EventType.valueOf(value)
        } catch (e: Exception) {
            e.printStackTrace()
            EventType.OTHER
        }
    }

    override fun serialize(encoder: Encoder, value: EventType) {
        Log.d(this.javaClass.simpleName, value.name)
        return encoder.encodeString(value.name)
    }
}