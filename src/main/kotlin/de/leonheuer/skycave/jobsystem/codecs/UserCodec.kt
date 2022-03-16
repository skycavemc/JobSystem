package de.leonheuer.skycave.jobsystem.codecs

import de.leonheuer.skycave.jobsystem.enums.Job
import de.leonheuer.skycave.jobsystem.model.User
import de.leonheuer.skycave.jobsystem.model.UserLevel
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistry
import java.time.LocalDateTime
import java.util.*

class UserCodec(codecRegistry: CodecRegistry) : Codec<User> {

    private val uuidCodec: Codec<UUID> = codecRegistry.get(UUID::class.java)
    private val jobCodec: Codec<Job> = codecRegistry.get(Job::class.java)
    private val localDateTimeCodec: Codec<LocalDateTime> = codecRegistry.get(LocalDateTime::class.java)
    private val userLevelCodec: Codec<UserLevel> = codecRegistry.get(UserLevel::class.java)

    override fun encode(writer: BsonWriter, value: User?, encoderContext: EncoderContext?) {
        if (value != null) {
            writer.writeStartDocument()
            writer.writeName("uuid")
            uuidCodec.encode(writer, value.uuid, encoderContext)
            writer.writeName("job")
            jobCodec.encode(writer, value.job, encoderContext)
            writer.writeName("last_job_change")
            localDateTimeCodec.encode(writer, value.lastJobChange, encoderContext)
            writer.writeName("free_job_changes")
            writer.writeInt32(value.freeJobChanges)
            writer.writeName("experience")
            userLevelCodec.encode(writer, value.level, encoderContext)
            writer.writeEndDocument()
        }
    }

    override fun getEncoderClass(): Class<User> {
        return User::class.java
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): User {
        val user = User()
        reader.readStartDocument()
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            when (reader.readName()) {
                "_id" -> user.id = reader.readObjectId()
                "uuid" -> user.uuid = uuidCodec.decode(reader, decoderContext)
                "job" -> user.job = jobCodec.decode(reader, decoderContext)
                "last_job_change" -> user.lastJobChange = localDateTimeCodec.decode(reader, decoderContext)
                "free_job_changes" -> user.freeJobChanges = reader.readInt32()
                "experience" -> user.level = userLevelCodec.decode(reader, decoderContext)
            }
        }
        reader.readEndDocument()
        return user
    }

}