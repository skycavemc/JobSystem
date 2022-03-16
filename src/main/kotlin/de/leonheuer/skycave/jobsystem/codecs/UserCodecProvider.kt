package de.leonheuer.skycave.jobsystem.codecs

import de.leonheuer.skycave.jobsystem.model.User
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry

class UserCodecProvider : CodecProvider {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(clazz: Class<T>?, registry: CodecRegistry): Codec<T>? {
        if (clazz == User::class.java) {
            return UserCodec(registry) as Codec<T>
        }
        return null
    }

}