package dev.rajesh.mobile_banking.oauthnative;

public final class OAuthNativeBridge {
    static {
        System.loadLibrary("oauth_native");
    }

    public static native String clientId();

    public static native String clientSecret();

    private OAuthNativeBridge() {}
}
