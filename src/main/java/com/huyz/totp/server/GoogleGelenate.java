package com.huyz.totp.server;

import java.io.UnsupportedEncodingException;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.ICredentialRepository;

public class GoogleGelenate {

	static GoogleAuthenticator gAuth = null;
	static GoogleAuthenticatorKey key = null;

	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
		GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
				.setCodeDigits(6)// 验证的位数
				.setNumberOfScratchCodes(2)// 乱码个数 为了在用户失去对令牌设备的访问时提供安全保障
				.setTimeStepSizeInMillis(60)// 多少时间刷新一次
				.build();
		gAuth = new GoogleAuthenticator(config);
		ICredentialRepository repository = new ICredentialRepositoryImpl();
		gAuth.setCredentialRepository(repository);
//		key = gAuth.createCredentials();
		key = gAuth.createCredentials("admin");
		String secret = key.getKey();
		// 0时的验证代码
		int code = key.getVerificationCode();
		boolean authorize = gAuth.authorize(secret, code, 0);
		System.err.println(authorize);
		System.out.println(secret);
		while (true) {
			Thread.sleep(1000 * 5);
			int newcode = gAuth.getTotpPassword(secret);
			String codeStr = Integer.valueOf(newcode).toString().length() == 6 ? "" + newcode : "0" + newcode;
			System.err.println(codeStr);
			// gAuth.getTotpPasswordOfUser(userName)
		}

	}

}
