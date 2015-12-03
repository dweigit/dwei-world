package com.bestsoft.security;


import com.itrus.cert.X509Certificate;
import com.itrus.cryptorole.bc.RecipientBcImpl;
import com.itrus.svm.SignerAndEncryptedDigest;
import com.itrus.util.Base64;

public class SVM {
	public static String verifySignature(String toSign, String signedData) {
		toSign = toSign == null ? "" : toSign;
		signedData = signedData == null ? "" : signedData;
		String encoding = "UTF-8"; // 使用UTF8或GBK编码集
		X509Certificate cert = null;
		if (!toSign.equals("") && !signedData.equals("")) {
			// 调用签名验证模块
			try {
				// 这一行是为了解决Windows平台Tomcat下表单提交非ASCII字符出现乱码的问题。
				// 原因是Tomcat内置编码为ISO-8859-1
				toSign = new String(toSign.getBytes("ISO-8859-1"), "UTF-8");
				RecipientBcImpl recipient = new RecipientBcImpl();
				byte[] toSignBuf = toSign.getBytes(encoding);
				SignerAndEncryptedDigest ret = recipient.verifyAndParsePkcs7(toSignBuf, Base64.decode(signedData));
				cert = X509Certificate.getInstance(ret.getSigner());
				String plainText = new String(ret.getOriData(), encoding);// 提取签名原文
				if (toSign.equals(plainText)) {
					return cert.getSubjectDNString();// 签名证书
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String to="4d24076f25d0a88c7c32a110288512c7";
		String val="MIIEywYJKoZIhvcNAQcCoIIEvDCCBLgCAQExCzAJBgUrDgMCGgUAMC8GCSqGSIb3DQEHAaAiBCA0ZDI0MDc2ZjI1ZDBhODhjN2MzMmExMTAyODg1MTJjN6CCAxUwggMRMIICeqADAgECAhBWgjrhti/f1c/oUe+CLasPMA0GCSqGSIb3DQEBBQUAMB8xCzAJBgNVBAYTAkNOMRAwDgYDVQQDEwdHT0xEIENBMB4XDTE1MDIxNTA3MTEyMVoXDTE2MDIxNTA3MTEyMVowRDELMAkGA1UEBhMCQ04xNTAzBgNVBAMeLGdOTj0AWwAyADEAMAA0ADAANAAxADkANwA4ADAANQAwADYAMAAzADQAMQBdMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu7gG8QZUWSZubP/WplKOPGnuem+mVEPVuuR01Oj7EnW+9QTtU/jOE4qSZoc5wOCHklWN7UJOTJbiJ9p+G//6wPTLjmrEfT4uqzl78aXN8mDgWOsmCy60s8dcJpGvJeWUvnuCY3Dm2UieNompbE4w3Zfubkch8EmJ7M6+1EvG0YgCu0DgayvHjfQwAv/I3CqmG+nbtvn967ipph0dpvYiDEdSTki+oEZHHBVRRKLSA3733YjtPvc5k9S9Dlroaxn+w/j2ziVz381OWI+hAe5W9Q9LjUirysgOZ344EXnAMbzkZridWktMr3xr6mxhQCrSdHIHRz/zvjcfrgqiCI3y9QIDAQABo4GkMIGhMB8GA1UdIwQYMBaAFCt+bxq3dtSUhJi6umMToK/rW0CJMB0GA1UdDgQWBBTSI73YFMakEPTye6J8zeCY2CUTYjAMBgNVHRMEBTADAQEAMFEGA1UdHwRKMEgwRqBEoEKkQDA+MQswCQYDVQQGEwJDTjEQMA4GA1UEAxMHR09MRCBDQTEMMAoGA1UECxMDQ1JMMQ8wDQYDVQQDEwZjcmw0XzAwDQYJKoZIhvcNAQEFBQADgYEACC1odkS57Ia0MhWxZ9bu9OiZbUWvVzfFG40O5l87ps3NiCSSuiDTQKtkeLeNGEe24n7mbBlkrn65af5G9/ZV0C5q/+zUZhQDi7eJbDLlgUUvhHYNuR0idX23KHxdOM1RJ0F/o45cewG65J6PPIYzlMFteQs114Pq5kRcwzW/EgExggFaMIIBVgIBATAzMB8xCzAJBgNVBAYTAkNOMRAwDgYDVQQDEwdHT0xEIENBAhBWgjrhti/f1c/oUe+CLasPMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEggEAmld399kmCEwUbbwSFyI2ZW/R7gBE6GRJg0usuRZnEIfra4SH7S0Ao8ivKkKdsNUlURUEQU/cPJBSIE8pgnDhooIC+F8/WAi2P7LdpR8/R8RI/BjeKAq+YjOxLNd3zctZwjcKntp7hkx2zKipQhIz+gIR/VSUZZfj67eLhhE+/BwxmqHfoSrGNk/5tZPlN1ywpnl8ZgUlA0k5OyS/hTm+DvJoXFUlOS1duKOZpr+m7Y5sAKkSBzDRE3x9/4MUNuLh/q96lS1apJj8d4ItGPyDVDiMkKinnBez9q4UOhlbOi/v2JjoPS5mdLbxUvDhwHprEgBo2SFs6EFcZHUxZLcSRQ==";
		System.out.println(SVM.verifySignature(to,val));	
	}
}
