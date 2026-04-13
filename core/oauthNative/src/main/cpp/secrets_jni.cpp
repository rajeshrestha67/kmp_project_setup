#include <jni.h>
#include <cstring>

namespace {

constexpr unsigned char kXorKey = 0x5Au;

// XOR-obfuscated payloads (not encryption — raises bar vs. plain strings in DEX).
// Regenerate when changing credentials: xor each byte with kXorKey.
constexpr unsigned char kIdEnc[] = {
    0x10, 0x1d, 0x0f, 0x0a, 0x1d, 0x11, 0x18, 0x14, 0x68, 0x17, 0x00};
constexpr unsigned char kSecretEnc[] = {
    0x68, 0x6a, 0x6f, 0x68, 0x6e, 0x63, 0x00};

jstring decodeToJString(JNIEnv *env, const unsigned char *enc) {
    size_t len = 0;
    while (enc[len]) {
        ++len;
    }
    char buf[256];
    if (len >= sizeof(buf)) {
        return env->NewStringUTF("");
    }
    for (size_t i = 0; i < len; ++i) {
        buf[i] = static_cast<char>(enc[i] ^ kXorKey);
    }
    buf[len] = '\0';
    return env->NewStringUTF(buf);
}

} // namespace

extern "C" {

JNIEXPORT jstring JNICALL
Java_dev_rajesh_mobile_1banking_oauthnative_OAuthNativeBridge_clientId(JNIEnv *env, jclass /* clazz */) {
    return decodeToJString(env, kIdEnc);
}

JNIEXPORT jstring JNICALL
Java_dev_rajesh_mobile_1banking_oauthnative_OAuthNativeBridge_clientSecret(JNIEnv *env, jclass /* clazz */) {
    return decodeToJString(env, kSecretEnc);
}

}
