package com.huyz.totp.client;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class GoogleGelenate {

	
	public static void main(String[] args) {
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		final GoogleAuthenticatorKey key = gAuth.createCredentials();
		System.err.println(key.getKey());
		System.err.println(key.getVerificationCode());
		boolean isCodeValid = gAuth.authorize(key.getKey(), key.getVerificationCode());
		System.err.println(isCodeValid);
	}
}
