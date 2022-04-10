package de.leonheuer.skycave.jobsystem.codecs

import de.leonheuer.skycave.jobsystem.enums.Job
import org.bson.BsonInvalidOperationException
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext

class JobCodec : Codec<Job> {

    override fun encode(writer: BsonWriter, value: Job?, encoderContext: EncoderContext?) {
        if (value == null) {
            writer.writeNull()
            return
        }
        writer.writeString(value.toString())
    }

    override fun getEncoderClass(): Class<Job> {
        return Job::class.java
    }

    @Throws(IllegalArgumentException::class)
    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): Job? {
        return try {
            Job.valueOf(reader.readString())
        } catch (e: BsonInvalidOperationException) {
            reader.skipValue()
            null
        }
    }

}