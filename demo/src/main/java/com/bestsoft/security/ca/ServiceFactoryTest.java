package com.bestsoft.security.ca;

import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

import cn.com.gfa.pki.ra.ws.soa.client.Result;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertDown;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertDownResult;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertRevokeReasons;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertRevokeReasons.Reasons;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertUpdate;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertUpdateDown;
import cn.com.gfa.pki.ra.ws.soa.client.SelfCertUpdateDownResult;
import cn.com.gfa.pki.ra.ws.soa.client.SelfService;
import cn.com.gfa.pki.ra.ws.soa.client.SelfUserCertInfo;
import cn.com.gfa.pki.ra.ws.soa.client.SelfUserCertReqInfo;
import cn.com.gfa.pki.ra.ws.soa.client.UserAddAndApplyRequest;
import cn.com.gfa.pki.ra.ws.soa.client.UserAddAndApplyResult;
import cn.com.gfa.pki.ra.ws.soa.client.UserAddRequest;
import cn.com.gfa.pki.ra.ws.soa.client.UserDelRequest;
import cn.com.gfa.pki.ra.ws.soa.client.UserLoadRequest;
import cn.com.gfa.pki.ra.ws.soa.client.UserLoadResult;
import cn.com.gfa.pki.ra.ws.soa.client.UserUpdateRequest;
import cn.com.gfa.pki.ra.ws.soa.factory.ServiceFactory;
import cn.com.gfa.pki.ra.ws.util.UserPropertyTool;

/***************************************************************************
 * <pre>
 * 文件名称:  ServiceFactoryTest.java
 * 包   路   径：  cn.com.gfa.pki.ra.soa.factory
 * 版权所有:  信息技术有限公司 (C) 2012
 * </pre>
 * 
 * <pre>
 * 类描述:  
 * Revision : $Revision: 1.5 $
 * 创建人： yuanzhenchao
 * 创建时间：2013-3-19 上午11:06:26  
 * 修改人：yuanzhenchao  
 * 修改时间：2013-3-19 上午11:06:26
 * </pre>
 * 
 * <pre>
 *  1. 修改记录：
 *    -----------------------------------------------------------------------------------------------
 *              时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
 *    -----------------------------------------------------------------------------------------------
 *                  		|                 |                           |                                       
 *    -----------------------------------------------------------------------------------------------
 * </pre>
 ***************************************************************************/

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class ServiceFactoryTest {

	private static SelfService service = null;

	private static String USER_CERT_KEY_UPDATE = "cert_key_update"; // 证书内容更新
	private static String USER_CERT_RENEW_UPDATE = "cert_renew"; // 证书续签

	/**
	 * @param args
	 * @throws GeneralSecurityException
	 */
	public static void main(String[] args) throws GeneralSecurityException {
		ServiceFactory serviceFactory = ServiceFactory.getInstance("10.240.26.48", "12443", "D:\\jkzx.jks", "123456");
		service = serviceFactory.generateService();

		ServiceFactoryTest test = new ServiceFactoryTest();
		test.userAddAndApply();
		// test.loadUserCertReq("44IS02QESUETI88S");
		// test.userCertSign();

		// test.userCertKeyUpdate();
		// test.userCertUpdateSign();

		// test.userCertRenew();
		// test.userCertRenewSign();

		// test.userCertRevokeReasonMap();
		// test. userCertRevoke();

		// test.userAdd();
		// test.userUpdate();
		// test.userDelete();
		// test.userLoad();
	}

	/***** 证书签发 ****/

	/**
	 * 添加用户 同时提交证书申请
	 */
	public void userAddAndApply() {
		UserAddAndApplyRequest request = new UserAddAndApplyRequest();
		request.setCtmlName("RSA普通证书模板"); // 模板名称
		request.setDnRuleId("928814a5-1b48-438b-aae1-1fd6cb6ec3fb"); // DN 规则的ID
		request.setUserCategory("gfa_ra_userdefine_qhjk"); // 设置用户类型
		request.setValidity(365); // 设置请求证书的有效期
		// 设计用户信息
		request.setId("ddddddddddddddtest1"); // 设置用户ID
		UserPropertyTool tool = new UserPropertyTool();
		tool.setProperty("holder_name", "测试姓名1"); // 姓名
		tool.setProperty("id_type", "1"); // 证件类型
		tool.setProperty("id_no", "1234561"); // 证件号码
		tool.setProperty("company_code", "123456"); // 期货公司代码
		tool.setProperty("company_name", "01010101"); // 期货公司名称
		request.setProperties(tool.getProperties());

		UserAddAndApplyResult result = service.userAddAndApply(request);
		if (result.getResult().getResult().equals("success")) {
			System.out.println(result.getAuthCode()); // 返回证书请求的授权码
		} else {
			System.out.println("fail");
		}
	}

	/**
	 * 通过authcode 返回证书请求信息
	 * 
	 * @param authcode
	 * @return
	 */
	public void loadUserCertReq(String authcode) {
		SelfUserCertReqInfo reqInfo = service.getCertReqByAuthCode(authcode);
		if (reqInfo.getResult().getResult().equals("success")) {
			System.out.println("REQ ID:" + reqInfo.getReqId()); // 证书请求ID
			System.out.println("SUBJECT:" + reqInfo.getSubject()); // 证书请求证书的主题SUBJECT
			System.out.println("KEY TYPE:" + reqInfo.getKeyType()); // 密钥的类型 RSA
			System.out.println("KEY LENGTH:" + reqInfo.getKeyLength()); // 证书密钥的长度
			System.out.println("CTMLNAME:" + reqInfo.getCtmlName()); // 证书使用的模板
			System.out.println("VALIDITY:" + reqInfo.getValidity()); // 证书的有效期
		} else {
			System.out.println("fail");
		}
	}

	/**
	 * 根据用户证书请求信息 进行证书签发 返回证书实体内容
	 * 
	 * @param reqInfo
	 */
	public void userCertSign() {
		SelfCertDown certDown = new SelfCertDown();
		certDown.setReqId("359c921b-d19c-4988-a655-22d5babce508"); // 证书请求ID
		certDown.setNotBefore("20130325112700000"); // 时间格式：yyyyMMddHHmmssSSS
		certDown.setValidity(365); // 证书有效期
		// PKCS10 证书申请格式 ，这个参数由WEB页面调用硬件厂商根据证书的主题产生的
		certDown.setPkcs10("-----BEGIN NEW CERTIFICATE REQUEST-----MIICzzCCAbcCAQAwgYkxKTAnBgNVBAMeIE4tVv1W/ZZFdTVbUFVGUqFOLV/DAC1tS4vVdShiNwAyMREwDwYDVQQLEwgwMTAxMDEwMTEdMBsGA1UECx4UTi1W/Vb9lkV1NVtQVUZSoU4tX8MxEDAOBgNVBAcTB2JlaWppbmcxCzAJBgNVBAgTAmJqMQswCQYDVQQGEwJjbjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANgHDvGagfUgNf5/NWtVNckzysmln/L6Aazi3WUsQOstbuZTYLIbFKg0sRgFcW4hFzvrA0S9OIY8StO7apYs9bTU5thazOd0DLJ8rO/qLNG1Wmtu0ECsW3TzGcAFYh5o79Gvy6P4qPbT3Vq4ocBGJJnpFwK7JVWU/xLLHy+lE0Rh+h9I8INi7XIQg0o3bxntQz7YfVmNGoTpv1OWF950N8EEGKzw7SBeKjqW6TgHX16uQb+KVwtX8cWJVqKy/McjFywoB8ZNseD8YY3KKqHDNj7otbThKXdNGcYHOatSUrN4twSjS9f21TT30jIRsYybjtstDiZWJz3NG+6x1q/jjR8CAwEAAaAAMA0GCSqGSIb3DQEBBQUAA4IBAQCNDVZS4LdU8pqBhIDvVfC+IXuiqnc/Njz2hep4JsARszr8ayMtgsV2ZkWGqOSTXKS3VWRO/m4LUlxqxv8KlRAfL5vI2IhEZ09uW+By6iMe8rmdnu9A5melcWX8s99Su0mPj7mJCekw84ARHIFqYAWyJHunDR9FGA+P4CSGx5Ss1udDNg3vT0E4OZ28Qpmt2XXreSfuoZePSpjfpFy0ECDNZcQvNrecid6puDlqI/5sGoehxsgIfjGJg1clpTYBvkCqHU8VbRm1TZs6wtpEfJMRh+m50b2S9zFse0CNV9zbQxwi/xIAVLEvwMgVcGMJ89tdRc3+jxSLikeyc1KgnfOH-----END NEW CERTIFICATE REQUEST-----");
		SelfCertDownResult result = service.certDown(certDown);
		if (result.getResult().getResult().equals("success")) {
			System.out.println(result.getCertsn()); // 返回证书的序列号
			System.out.println(result.getP7B()); // 返回证书的实体内容 BASE64格式编码
			if (result.getDoubleCertsn() != null) { // 如果是双证书，则返回第二张证书的序列号和格式
				System.out.println(result.getDoubleCertsn());
				System.out.println(result.getDoubleP7B());
			}
		} else {
			System.out.println("fail");
		}
	}

	/***** 证书更新 ****/

	/**
	 * 
	 */
	public void userCertKeyUpdate() {
		SelfCertUpdate update = new SelfCertUpdate();
		update.setCertsn("64FC4EA30BECE7CBF5D7B27FD02BB02B"); // 需要更新的证书序列号
		update.setUpdateType(USER_CERT_KEY_UPDATE); // 请求的更新类型
													// 密钥更新：cert_key_update
													// 证书续签：cert_renew
		// 如果证书更新请求的类型是 USER_CERT_KEY_UPDATE 需设置证书的有效期开始时间和有效期天数
		// 如果证书更新请求的类型是 USER_CERT_RENEW 不需要
		update.setNotBefore("20130325115800000");
		update.setValidity(365);

		update.setSubject("CN=中国国际电子商务中心-测试用户1upd,OU=01010101,OU=中国国际电子商务中心,L=BEIJING,ST=BJ,C=CN"); // 设置更新证书使用的主题
																									// SUBJECT
		SelfUserCertReqInfo reqInfo = service.certUpdate(update);
		if (reqInfo.getResult().getResult().equals("success")) {
			System.out.println("REQ ID:" + reqInfo.getReqId());
			System.out.println("SUBJECT:" + reqInfo.getSubject());
			System.out.println("KEY TYPE:" + reqInfo.getKeyType());
			System.out.println("KEY LENGTH:" + reqInfo.getKeyLength());
			System.out.println("CTMLNAME:" + reqInfo.getCtmlName());
			System.out.println("VALIDITY:" + reqInfo.getValidity());
			System.out.println("UPDATE TYPE:" + reqInfo.getUpdateType());
		} else {
			System.out.println("fail");
		}
	}

	public void userCertUpdateSign() {
		SelfCertUpdateDown updateDown = new SelfCertUpdateDown();
		updateDown.setReqId("f0c0f723-d348-4acd-b9ec-de3d0880d1a7"); // 证书请求ID
		updateDown.setCertsn("64FC4EA30BECE7CBF5D7B27FD02BB02B"); // 被更新证书的序列号
		updateDown.setUpdateType("cert_key_update"); // 更新类型
														// 密钥更新：cert_key_update
														// 证书续签：cert_renew
		updateDown.setNotBefore("2013-03-25 11:58:00"); // 时间格式：yyyy-MM-dd
														// HH:mm:ss
		updateDown.setValidity(3650); // 证书有效期
		updateDown
				.setPkcs10("-----BEGIN NEW CERTIFICATE REQUEST-----MIIC1TCCAb0CAQAwgY8xLzAtBgNVBAMeJk4tVv1W/ZZFdTVbUFVGUqFOLV/DAC1tS4vVdShiNwAxAHUAcABkMREwDwYDVQQLEwgwMTAxMDEwMTEdMBsGA1UECx4UTi1W/Vb9lkV1NVtQVUZSoU4tX8MxEDAOBgNVBAcTB0JFSUpJTkcxCzAJBgNVBAgTAkJKMQswCQYDVQQGEwJDTjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKqaADUJR7KCz9Zi+83/hnmCuSBV6nf3e/P9t7f/HWWYAKfFn28pFrvpbl/s5HR2CrmTilxx5C4j9eo379ueDGSGf0Cr7ZwDTxaG0Hqkl3x8fdfRlR7SzqXrr1Xac2IAp1x6I8gkUsagZbdOXcrm50lUQYhxJtEnrBVdrRLdJgJU1/6ua+Z7oER9RDPiwRHuZm64cEJEKrzctQHk09SnK07jKgCY3XXDHwjd0JIU8p7WUCBxlR8P0JHchHlkhhUAGVEAipIZQLSE3rHItIMYc46xUpAqoJXa2R3DpEQc05HF/EVihDYVDlsP6sSfeYYKUibE486k5XpSH+sXFPV/zMMCAwEAAaAAMA0GCSqGSIb3DQEBBQUAA4IBAQBgL7l2L6er/gleEDs/JWudxeCJ5fvplbtKu6vKQfDLib+hgL8pxhCy2K5gj87/SGo2sFPnT1rH52EMEyCWZpCAI2sF7/yiOA+oAndrXlGHrw5dUDWoCdMVlm8TnlF6Izm0dKU0iHQ1aliWZHypUqdHuRF32pKOucntkXXP0vPKwzyzBb0XrmMwOEd2ZXf0ta6y86fab/Dj0KJ4sOcyAkLNwTDmbhhJN4wtM423yJhtnq7cZB3m4eThp3RHb6bH33ZU+orDtpR2B0+TfxnLPyrTFFWfKhBZcLssWkyxjFPp9YYJnA6Pc3hMIIWCKEdvxN46xadA0FatCYSdvXSWBgEA-----END NEW CERTIFICATE REQUEST-----"); // PKCS10
																																																																																																																																																																																																																																																																														// 证书申请格式
																																																																																																																																																																																																																																																																														// ，这个参数由WEB页面调用硬件厂商根据证书的主题产生的
		SelfCertUpdateDownResult result = service.certUpdateDown(updateDown);
		if (result.getResult().getResult().equals("success")) {
			System.out.println(result.getCertsn()); // 返回证书的序列号
			System.out.println(result.getP7B()); // 返回证书的实体内容 BASE64格式编码
			if (result.getDoubleCertsn() != null) { // 如果是双证书，则返回第二张证书的序列号和格式
				System.out.println(result.getDoubleCertsn());
				System.out.println(result.getDoubleP7B());
			}
		}
	}

	/***** 证书续签 ****/

	/**
	 * 
	 */
	public void userCertRenew() {
		SelfCertUpdate update = new SelfCertUpdate();
		update.setCertsn("34B9A05A03931B9B47723629EB0000C0"); // 需要更新的证书序列号
		update.setUpdateType(USER_CERT_RENEW_UPDATE); // 请求的更新类型
														// 密钥更新：cert_key_update
														// 证书续签：cert_renew
		// 如果证书更新请求的类型是 USER_CERT_KEY_UPDATE 需设置证书的有效期开始时间和有效期天数
		// 如果证书更新请求的类型是 USER_CERT_RENEW 不需要
		update.setNotBefore("20130325113019000");
		// update.setValidity(365);
		// update.setSubject("CN=中国国际电子商务中心-袁振朝7777777,OU=0101010101,OU=中国国际电子商务中心,L=bj,ST=北京,C=cn");
		// //设置更新证书使用的主题 SUBJECT
		SelfUserCertReqInfo reqInfo = service.certUpdate(update);
		if (reqInfo.getResult().getResult().equals("success")) {
			System.out.println("REQ ID:" + reqInfo.getReqId());
			System.out.println("SUBJECT:" + reqInfo.getSubject());
			System.out.println("KEY TYPE:" + reqInfo.getKeyType());
			System.out.println("KEY LENGTH:" + reqInfo.getKeyLength());
			System.out.println("CTMLNAME:" + reqInfo.getCtmlName());
			System.out.println("VALIDITY:" + reqInfo.getValidity());
		} else {
			System.out.println("fail");
		}
	}

	public void userCertRenewSign() {
		SelfCertUpdateDown updateDown = new SelfCertUpdateDown();
		updateDown.setReqId("6b8cc530-9ae5-4174-94ed-a7b2613ee2ed"); // 证书请求ID
		updateDown.setCertsn("34B9A05A03931B9B47723629EB0000C0"); // 被更新证书的序列号
		updateDown.setUpdateType("cert_renew"); // 更新类型 密钥更新：cert_key_update
												// 证书续签：cert_renew
		// updateDown.setNotBefore(""); //时间格式：yyyy-MM-dd HH:mm:ss
		// updateDown.setValidity(365); //证书有效期
		updateDown
				.setPkcs10("-----BEGIN NEW CERTIFICATE REQUEST-----MIIC1TCCAb0CAQAwgY8xLzAtBgNVBAMeJk4tVv1W/ZZFdTVbUFVGUqFOLV/DAC1tS4vVdShiNwAxAHUAcABkMREwDwYDVQQLEwgwMTAxMDEwMTEdMBsGA1UECx4UTi1W/Vb9lkV1NVtQVUZSoU4tX8MxEDAOBgNVBAcTB0JFSUpJTkcxCzAJBgNVBAgTAkJKMQswCQYDVQQGEwJDTjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKqaADUJR7KCz9Zi+83/hnmCuSBV6nf3e/P9t7f/HWWYAKfFn28pFrvpbl/s5HR2CrmTilxx5C4j9eo379ueDGSGf0Cr7ZwDTxaG0Hqkl3x8fdfRlR7SzqXrr1Xac2IAp1x6I8gkUsagZbdOXcrm50lUQYhxJtEnrBVdrRLdJgJU1/6ua+Z7oER9RDPiwRHuZm64cEJEKrzctQHk09SnK07jKgCY3XXDHwjd0JIU8p7WUCBxlR8P0JHchHlkhhUAGVEAipIZQLSE3rHItIMYc46xUpAqoJXa2R3DpEQc05HF/EVihDYVDlsP6sSfeYYKUibE486k5XpSH+sXFPV/zMMCAwEAAaAAMA0GCSqGSIb3DQEBBQUAA4IBAQBgL7l2L6er/gleEDs/JWudxeCJ5fvplbtKu6vKQfDLib+hgL8pxhCy2K5gj87/SGo2sFPnT1rH52EMEyCWZpCAI2sF7/yiOA+oAndrXlGHrw5dUDWoCdMVlm8TnlF6Izm0dKU0iHQ1aliWZHypUqdHuRF32pKOucntkXXP0vPKwzyzBb0XrmMwOEd2ZXf0ta6y86fab/Dj0KJ4sOcyAkLNwTDmbhhJN4wtM423yJhtnq7cZB3m4eThp3RHb6bH33ZU+orDtpR2B0+TfxnLPyrTFFWfKhBZcLssWkyxjFPp9YYJnA6Pc3hMIIWCKEdvxN46xadA0FatCYSdvXSWBgEA-----END NEW CERTIFICATE REQUEST-----"); // PKCS10
																																																																																																																																																																																																																																																																														// 证书申请格式
																																																																																																																																																																																																																																																																														// ，这个参数由WEB页面调用硬件厂商根据证书的主题产生的
		SelfCertUpdateDownResult result = service.certUpdateDown(updateDown);
		if (result.getResult().getResult().equals("success")) {
			System.out.println(result.getCertsn()); // 返回证书的序列号
			System.out.println(result.getP7B()); // 返回证书的实体内容 BASE64格式编码
			if (result.getDoubleCertsn() != null) { // 如果是双证书，则返回第二张证书的序列号和格式
				System.out.println(result.getDoubleCertsn());
				System.out.println(result.getDoubleP7B());
			}
		}
	}

	private void getUserCertReqById() {
		SelfUserCertReqInfo reqInfo = service.getCertReqById("");
	}

	/***** 证书注销 ****/

	public void userCertRevokeReasonMap() {
		SelfCertRevokeReasons reasonmap = service.getCertRevokeReasonMap();
		Reasons reasons = reasonmap.getReasons();
		List<Reasons.Entry> entrysEntries = reasons.getEntry();
		for (Iterator iterator = entrysEntries.iterator(); iterator.hasNext();) {
			Reasons.Entry entry = (Reasons.Entry) iterator.next();
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

	public void userCertRevoke() {
		Result result = service.certRevoke("153DF3FCD9DC0951F8A2BD2EE44D3340", // 被注销证书的序列号
				1, // 注销原因类型
				"reason desc" // 注销原因描述
		);
		System.out.println("证书注销： " + result.getResult());
	}

	public void userAdd() {
		UserAddRequest request = new UserAddRequest();
		request.setId("lsajfldsalfdlsfads");
		request.setUserCategory("gfa_ra_userdefine_supplier");

		UserPropertyTool tool = new UserPropertyTool();
		tool.setProperty("realname", "eclipse_test");
		tool.setProperty("unitname", "中国国际电子商务中心");
		tool.setProperty("mcity", "changsha");
		tool.setProperty("st", "hunan");
		tool.setProperty("supplierid", "01010101");
		request.setProperties(tool.getProperties());

		Result result = service.userAdd(request);
	}

	public void userUpdate() {
		UserUpdateRequest request = new UserUpdateRequest();
		// 已存在用户ID
		request.setId("lsajfldsalfdlsfads");
		request.setUserCategory("gfa_ra_userdefine_supplier");

		UserPropertyTool tool = new UserPropertyTool();
		tool.setProperty("realname", "eclipse_test_update");
		tool.setProperty("unitname", "CIECC");
		tool.setProperty("mcity", "beijing");
		tool.setProperty("st", "beijing");
		tool.setProperty("supplierid", "888888888");
		request.setProperties(tool.getProperties());

		Result result = service.userUpdate(request);
	}

	public void userDelete() {
		UserDelRequest request = new UserDelRequest();
		// 已存在用户ID
		request.setId("lsajfldsalfdlsfads");
		request.setUserCategory("gfa_ra_userdefine_supplier");
		service.userDel(request);
	}

	public void userLoad() {
		UserLoadRequest request = new UserLoadRequest();
		// 已存在用户ID
		request.setId("33c1e40d-6dff-4087-9b8a-d188be48942b");
		request.setUserCategory("gfa_ra_userdefine_supplier");

		UserLoadResult result = service.userLoad(request);
		UserLoadResult.Map properties = result.getMap();
		List<UserLoadResult.Map.Entry> entries = properties.getEntry();
		for (int i = 0; i < entries.size(); i++) {
			UserLoadResult.Map.Entry entry = entries.get(i);
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		List<SelfUserCertInfo> certs = result.getCerts();
		for (Iterator iterator = certs.iterator(); iterator.hasNext();) {
			SelfUserCertInfo selfUserCertInfo = (SelfUserCertInfo) iterator.next();
			System.out.println(selfUserCertInfo.getSerialNumber() + "\n" + selfUserCertInfo.getSubject() + "\n" + selfUserCertInfo.getCtmlName() + "\n"
					+ selfUserCertInfo.getKeyType() + "\n" + selfUserCertInfo.getKeyLength() + "\n" + selfUserCertInfo.getCertStatus() + "\n"
					+ selfUserCertInfo.getNotBefore() + "\n" + selfUserCertInfo.getNotAfter() + "\n");
		}
	}
}
