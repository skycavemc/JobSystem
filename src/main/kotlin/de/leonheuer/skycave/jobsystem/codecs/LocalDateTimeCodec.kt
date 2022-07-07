package de.leonheuer.skycave.jobsystem.codecs

import org.bson.BsonInvalidOperationException
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import java.time.LocalDateTime

class LocalDateTimeCodec : Codec<LocalDateTime> {

    override fun encode(writer: BsonWriter, value: LocalDateTime?, encoderContext: EncoderContext?) {
        if (value == null) {
            writer.writeNull()
            return
        }
        writer.writeString(value.toString())
    }

    override fun getEncoderClass(): Class<LocalDateTime> {
        return LocalDateTime::class.java
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): LocalDateTime? {
        return try {
            LocalDateTime.parse(reader.readString())
        } catch (e: BsonInvalidOperationException) {
            reader.skipValue()
            null
        }
    }

}