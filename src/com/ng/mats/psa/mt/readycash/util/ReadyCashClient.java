package com.ng.mats.psa.mt.readycash.util;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.ng.mats.psa.mt.readycash.model.MoneyTransfer;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Login;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.LoginE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.LoginResponse;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.LoginResponseE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Mobile_transfer;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Mobile_transferE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Mobile_transferResponse;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Mobile_transferResponseE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.ServiceResponse;
import com.readycashng.www.ws.api._1_0.TerminatingExceptionException;

public class ReadyCashClient {
	private static final Logger logger = Logger.getLogger(ReadyCashClient.class
			.getName());
	private final static String testpin = "1234";
	private static AgentServiceServiceStub readyCashStub;

	public static ServiceResponse performCashIn(MoneyTransfer moneyTransfer) {
		ServiceResponse loginServiceResponse = null, moneyTransferServiceResponse = null;
		logger.info("----------------------perform cash in");
		LoginE login = new LoginE();
		Login loginParam = new Login();
		loginParam.setEmail(moneyTransfer.getAgentUsername());
		loginParam.setPassword(moneyTransfer.getReadyCashPin());
		logger.info("----------------------after setting login parameters");
		login.setLogin(loginParam);
		LoginResponseE loginResponseE = new LoginResponseE();
		logger.info("----------------------after instantiating login response");
		try {
			readyCashStub = new AgentServiceServiceStub();
			logger.info("----------------------after instantiating agent service stub");
			loginResponseE = readyCashStub.login(login);
			logger.info("----------------------after doing login from stub");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			logger.info("----------------------Remote exception for login");
			e.printStackTrace();
		}
		if (loginResponseE != null) {
			logger.info("----------------------loginResponseE is not null");
			LoginResponse loginResponse = loginResponseE.getLoginResponse();
			if (loginResponse != null) {
				logger.info("----------------------loginResponse is not null");
				loginServiceResponse = loginResponse.get_return();
				if (loginServiceResponse != null) {
					logger.info("----------------------ServiceResponse is not null");
					Mobile_transfer mobileTransferParam = new Mobile_transfer();
					mobileTransferParam.setAmount(moneyTransfer.getAmount());
					mobileTransferParam.setPhone_number_payer(moneyTransfer
							.getSender());
					mobileTransferParam.setPhone_number_recipient(moneyTransfer
							.getReceiver());
					mobileTransferParam.setPin(testpin);
					Mobile_transferE mobileTransfer = new Mobile_transferE();
					mobileTransfer.setMobile_transfer(mobileTransferParam);
					Mobile_transferResponseE mobileTransferResponseE = new Mobile_transferResponseE();
					logger.info("----------------------mobileTransferResponseE instantiation");
					try {
						mobileTransferResponseE = readyCashStub
								.mobile_transfer(mobileTransfer);
						logger.info("----------------------after initiating mobile transfer");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						logger.info("----------------------RemoteException on mobile transfer");
						e.printStackTrace();
					} catch (TerminatingExceptionException e) {
						// TODO Auto-generated catch block
						logger.info("----------------------TerminatingExceptionException on mobile transfer");
						e.printStackTrace();
					}
					if (mobileTransferResponseE != null) {
						logger.info("----------------------mobileTransferResponseE is not null");
						Mobile_transferResponse mobileTransferResponse = new Mobile_transferResponse();
						mobileTransferResponse = mobileTransferResponseE
								.getMobile_transferResponse();
						if (mobileTransferResponse != null) {
							logger.info("----------------------mobileTransferResponse is not null");
							moneyTransferServiceResponse = mobileTransferResponse
									.get_return();
						} else {
							logger.info("----------------------mobileTransferResponse is null");
						}
					} else {
						logger.info("----------------------mobileTransferResponseE is null");
					}
				} else {
					logger.info("----------------------ServiceResponse is null");
				}
			} else {
				logger.info("----------------------loginResponse is null");
			}
		} else {
			logger.info("----------------------loginResponseE is null");
		}
		logger.info("----------------------initiate cash in");
		return moneyTransferServiceResponse;

	}

	public boolean performCashout() {
		boolean status = false;
		logger.info("----------------------initiate cash in");
		return status;
	}

	public static String hmacSha1(String value, String key) {
		try {
			// Get an hmac_sha1 key from the raw key bytes
			byte[] keyBytes = key.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

			// Get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);

			// Compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(value.getBytes());

			// Convert raw bytes to Hex
			byte[] hexBytes = new Hex().encode(rawHmac);

			// Covert array of Hex bytes to a String
			return new String(hexBytes, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		logger.info("----------------------------------Hello World");
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		String key = "ABCDEDF00000FFFF";
		String password = "password";

		String hashedPassword = hmacSha1(password, key);
		String hashedPswd = HmacUtils.hmacSha1(key, password);
		moneyTransfer.setAmount(BigDecimal.valueOf(10000));
		moneyTransfer.setMmo("readycash");
		moneyTransfer.setAgentUsername("mats@mats.com");
		logger.info("------------------------the hmac" + hashedPassword);
		logger.info("------------------------the second hmac" + hashedPswd);
		moneyTransfer.setReadyCashPin(hashedPassword);
		moneyTransfer.setReceiver("2348063305711");
		moneyTransfer.setSender("2348092041723");
		// check that dev branch is working
		System.out.println("======================"
				+ performCashIn(moneyTransfer));
	}

}
