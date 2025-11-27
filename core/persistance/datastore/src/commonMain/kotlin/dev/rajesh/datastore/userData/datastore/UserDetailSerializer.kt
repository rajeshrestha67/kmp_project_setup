package dev.rajesh.datastore.userData.datastore

import androidx.datastore.core.okio.OkioSerializer
import dev.rajesh.datastore.userData.model.UserDetailsLocal
import dev.rajesh.mobile_banking.crypto.Cryptography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.BufferedSink
import okio.BufferedSource
import okio.use
import org.koin.mp.KoinPlatform.getKoin

object UserDetailSerializer : OkioSerializer<UserDetailsLocal> {

    val cryptography: Cryptography = getKoin().get()

    override val defaultValue: UserDetailsLocal
        get() = UserDetailsLocal()

    //read the value from the source and then decrypt and if failed fallback to the default value
    override suspend fun readFrom(source: BufferedSource): UserDetailsLocal {
        val encryptedByte = withContext(Dispatchers.IO) {
            source.readByteArray()
        }
        return try {
            cryptography.decrypt(encryptedByte, UserDetailsLocal.serializer()) ?: defaultValue
        } catch (_: Exception) {
            defaultValue
        }
    }

    //decrypt the value and save it to the sink(source)
    override suspend fun writeTo(t: UserDetailsLocal, sink: BufferedSink) {
        sink.use {
            withContext(Dispatchers.IO) {
                cryptography.encrypt(t, UserDetailsLocal.serializer())?.let { data ->
                    it.write(data)
                }
            }
        }
    }
}