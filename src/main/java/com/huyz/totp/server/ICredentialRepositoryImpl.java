package com.huyz.totp.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warrenstrange.googleauth.ICredentialRepository;

public class ICredentialRepositoryImpl implements ICredentialRepository {

	private Map<String, String> map = new HashMap<>();

	@Override
	public String getSecretKey(String userName) {
		return map.get(userName);
	}

	@Override
	public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
//		System.out.println(userName);
//		System.out.println(secretKey);
//		System.out.println(validationCode);
//		System.out.println(scratchCodes);
		map.put(userName, secretKey);
	}

}
