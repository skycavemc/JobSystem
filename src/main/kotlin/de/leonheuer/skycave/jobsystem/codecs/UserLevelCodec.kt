package de.leonheuer.skycave.jobsystem.codecs

import de.leonheuer.skycave.jobsystem.model.UserLevel
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class UserLevelCodec : Codec<UserLevel> {

    override fun encode(writer: BsonWriter, value: UserLevel?, encoderContext: EncoderContext?) {
        if (value == null) {
            writer.writeNull()
            return
        }
        writer.writeDouble(value.experience)
    }

    override fun getEncoderClass(): Class<UserLevel> {
        return UserLevel::class.java
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): UserLevel {
        return UserLevel(reader.readDouble())
    }

}