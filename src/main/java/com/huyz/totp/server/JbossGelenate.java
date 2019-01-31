package com.huyz.totp.server;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.api.Clock;

public class JbossGelenate {

	public static void main(String[] args) {
		String secret = Base32.random();
		System.err.println(secret);
		Clock clock = new Clock(120);
		Totp totp = new Totp(secret, clock);
		System.err.println(totp.now());
		System.err.println(totp.verify(totp.now()));
	}

}
