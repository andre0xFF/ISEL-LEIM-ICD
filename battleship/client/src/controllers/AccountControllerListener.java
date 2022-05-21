package controllers;

public interface AccountControllerListener {
    void onAuthenticationSucceeded();
    void onAuthenticationFailed();
    void onCredentialsSubmitted();
}
