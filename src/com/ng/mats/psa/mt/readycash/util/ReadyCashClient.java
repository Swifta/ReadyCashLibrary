package com.ng.mats.psa.mt.readycash.util;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.axis2.AxisFault;
import org.apache.commons.codec.binary.Hex;

import com.ng.mats.psa.mt.readycash.model.MoneyTransfer;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Balance;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.BalanceE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.BalanceResponse;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.BalanceResponseE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Cash_out;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Cash_outE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Cash_outResponse;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Cash_outResponseE;
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

	public ReadyCashClient() throws AxisFault {
		readyCashStub = new AgentServiceServiceStub();
		readyCashStub._getServiceClient().getOptions().setManageSession(true);
		long timeOutInMilliSeconds = (5 * 36 * 1000);
		readyCashStub._getServiceClient().getOptions()
				.setTimeOutInMilliSeconds(timeOutInMilliSeconds);
	}

	public static ServiceResponse balanceEnquiry(MoneyTransfer moneyTransfer) {
		ServiceResponse loginServiceResponse = null, moneyTransferServiceResponse = null, balanceServiceResponse = null;
		if (readyCashStub == null) {
			logger.info("------------------------readyCashStub is not available");
		} else {
			logger.info("---------------------------readyCashStub is available");

		}
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

					logger.info("----------------------loginServiceResponse is not null");
					logger.info("Login Service Response????\nAvailable Balance:"
							+ loginServiceResponse.getAvailableBalance()
							+ "::\nTransaction Date::"
							+ loginServiceResponse.getTransDatetime()
							+ "::\nStatement::"
							+ loginServiceResponse.getStatement()
							+ "::\nStan::"
							+ loginServiceResponse.getStan()
							+ "::\nRetref::"
							+ loginServiceResponse.getRetref()
							+ "::\nExtendedMsg::"
							+ loginServiceResponse.getExtendedMsg()
							+ "::\nLedger Balance::"
							+ loginServiceResponse.getLedgerBalance()
							+ "::\nDescription::"
							+ loginServiceResponse.getDesc()
							+ "::\nCode::"
							+ loginServiceResponse.getCode());
					BalanceE balanceE = new BalanceE();
					Balance balanceParam = new Balance();
					balanceParam.setPin(moneyTransfer.getAgentPin());
					balanceE.setBalance(balanceParam);
					BalanceResponseE balanceResponseE = new BalanceResponseE();
					logger.info("----------------------balanceResponseE instantiation");
					try {
						balanceResponseE = readyCashStub.balance(balanceE);
						logger.info("----------------------after initiating balance");
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						logger.info("----------------------RemoteException onbalance");
						e.printStackTrace();
					} catch (TerminatingExceptionException e) {
						// TODO Auto-generated catch block
						logger.info("----------------------TerminatingExceptionException on balance");
						e.printStackTrace();
					}
					if (balanceResponseE != null) {
						logger.info("----------------------balanceResponseE is not null");
						BalanceResponse balanceResponse = new BalanceResponse();
						balanceResponse = balanceResponseE.getBalanceResponse();
						if (balanceResponse != null) {
							logger.info("----------------------balanceResponse is not null");
							balanceServiceResponse = balanceResponse
									.get_return();
							if (balanceServiceResponse != null) {
								logger.info("Balance Transfer Response????\nAvailable Balance:"
										+ balanceServiceResponse
												.getAvailableBalance()
										+ "::\nTransaction Date::"
										+ balanceServiceResponse
												.getTransDatetime()
										+ "::\nStatement::"
										+ balanceServiceResponse.getStatement()
										+ "::\nStan::"
										+ balanceServiceResponse.getStan()
										+ "::\nRetref::"
										+ balanceServiceResponse.getRetref()
										+ "::\nExtendedMsg::"
										+ balanceServiceResponse
												.getExtendedMsg()
										+ "::\nLedger Balance::"
										+ balanceServiceResponse
												.getLedgerBalance()
										+ "::\nDescription::"
										+ balanceServiceResponse.getDesc()
										+ "::\nCode::"
										+ balanceServiceResponse.getCode());
							} else {
								logger.info("----------------------balanceServiceResponse is null");
							}
						} else {
							logger.info("----------------------balanceServiceResponse is null");
						}
					} else {
						logger.info("----------------------balanceServiceResponseE is null");
					}
				} else {
					logger.info("----------------------loginServiceResponse is null");
				}
			} else {
				logger.info("----------------------loginResponse is null");
			}
		} else {
			logger.info("----------------------loginResponseE is null");
		}
		logger.info("----------------------initiate balance enquiry");
		return moneyTransferServiceResponse;

	}

	public static ServiceResponse performCashIn(MoneyTransfer moneyTransfer) {
		ServiceResponse loginServiceResponse = null, moneyTransferServiceResponse = null;
		if (readyCashStub == null) {
			logger.info("------------------------readyCashStub is not available");
		} else {
			logger.info("---------------------------readyCashStub is available");

		}
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

					logger.info("----------------------loginServiceResponse is not null");
					logger.info("Login Service Response????\nAvailable Balance:"
							+ loginServiceResponse.getAvailableBalance()
							+ "::\nTransaction Date::"
							+ loginServiceResponse.getTransDatetime()
							+ "::\nStatement::"
							+ loginServiceResponse.getStatement()
							+ "::\nStan::"
							+ loginServiceResponse.getStan()
							+ "::\nRetref::"
							+ loginServiceResponse.getRetref()
							+ "::\nExtendedMsg::"
							+ loginServiceResponse.getExtendedMsg()
							+ "::\nLedger Balance::"
							+ loginServiceResponse.getLedgerBalance()
							+ "::\nDescription::"
							+ loginServiceResponse.getDesc()
							+ "::\nCode::"
							+ loginServiceResponse.getCode());
					Mobile_transfer mobileTransferParam = new Mobile_transfer();
					mobileTransferParam.setAmount(moneyTransfer.getAmount());
					// mobileTransferParam.setPhone_number_payer(moneyTransfer
					// .getAgentUsername());
					mobileTransferParam.setPhone_number_recipient(moneyTransfer
							.getReceiver());
					mobileTransferParam.setPin(moneyTransfer.getAgentPin());
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
							if (moneyTransferServiceResponse != null) {
								logger.info("Money Transfer Service Response????\nAvailable Balance:"
										+ moneyTransferServiceResponse
												.getAvailableBalance()
										+ "::\nTransaction Date::"
										+ moneyTransferServiceResponse
												.getTransDatetime()
										+ "::\nStatement::"
										+ moneyTransferServiceResponse
												.getStatement()
										+ "::\nStan::"
										+ moneyTransferServiceResponse
												.getStan()
										+ "::\nRetref::"
										+ moneyTransferServiceResponse
												.getRetref()
										+ "::\nExtendedMsg::"
										+ moneyTransferServiceResponse
												.getExtendedMsg()
										+ "::\nLedger Balance::"
										+ moneyTransferServiceResponse
												.getLedgerBalance()
										+ "::\nDescription::"
										+ moneyTransferServiceResponse
												.getDesc()
										+ "::\nCode::"
										+ moneyTransferServiceResponse
												.getCode());
							} else {
								logger.info("----------------------moneyTransferServiceResponse is null");
							}
						} else {
							logger.info("----------------------mobileTransferResponse is null");
						}
					} else {
						logger.info("----------------------mobileTransferResponseE is null");
					}
				} else {
					logger.info("----------------------loginServiceResponse is null");
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

	public static ServiceResponse performCashout(MoneyTransfer moneyTransfer) {
		ServiceResponse loginServiceResponse = null, cashoutServiceResponse = null;
		if (readyCashStub == null) {
			logger.info("------------------------readyCashStub is not available");
		} else {
			logger.info("---------------------------readyCashStub is available");

		}
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

					logger.info("----------------------loginServiceResponse is not null");
					logger.info("Login Service Response????\nAvailable Balance:"
							+ loginServiceResponse.getAvailableBalance()
							+ "::\nTransaction Date::"
							+ loginServiceResponse.getTransDatetime()
							+ "::\nStatement::"
							+ loginServiceResponse.getStatement()
							+ "::\nStan::"
							+ loginServiceResponse.getStan()
							+ "::\nRetref::"
							+ loginServiceResponse.getRetref()
							+ "::\nExtendedMsg::"
							+ loginServiceResponse.getExtendedMsg()
							+ "::\nLedger Balance::"
							+ loginServiceResponse.getLedgerBalance()
							+ "::\nDescription::"
							+ loginServiceResponse.getDesc()
							+ "::\nCode::"
							+ loginServiceResponse.getCode());
					logger.info("---------------------------readyCashStub is available");
					Cash_outE cash_out = new Cash_outE();
					Cash_out cashoutParam = new Cash_out();
					cashoutParam.setAmount(moneyTransfer.getAmount());
					cashoutParam.setPhone_number_recipient(moneyTransfer
							.getReceiver());
					cashoutParam.setPin(moneyTransfer.getAgentPin());
					cashoutParam.setVoucher(moneyTransfer.getSender());
					cash_out.setCash_out(cashoutParam);

					Cash_outResponseE cashoutResponseE = new Cash_outResponseE();
					Cash_outResponse cashoutResponse = new Cash_outResponse();
					try {
						cashoutResponseE = readyCashStub.cash_out(cash_out);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TerminatingExceptionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (cashoutResponseE != null) {
						logger.info("----------------------cashoutResponseE is not null");
						cashoutResponse = cashoutResponseE
								.getCash_outResponse();
						if (cashoutResponse != null) {
							logger.info("----------------------cashoutResponse is not null");
							cashoutServiceResponse = cashoutResponse
									.get_return();
							if (cashoutServiceResponse != null) {
								logger.info("cashoutServiceResponse????\nAvailable Balance:"
										+ cashoutServiceResponse
												.getAvailableBalance()
										+ "::\nTransaction Date::"
										+ cashoutServiceResponse
												.getTransDatetime()
										+ "::\nStatement::"
										+ cashoutServiceResponse.getStatement()
										+ "::\nStan::"
										+ cashoutServiceResponse.getStan()
										+ "::\nRetref::"
										+ cashoutServiceResponse.getRetref()
										+ "::\nExtendedMsg::"
										+ cashoutServiceResponse
												.getExtendedMsg()
										+ "::\nLedger Balance::"
										+ cashoutServiceResponse
												.getLedgerBalance()
										+ "::\nDescription::"
										+ cashoutServiceResponse.getDesc()
										+ "::\nCode::"
										+ cashoutServiceResponse.getCode());
							} else {
								logger.info("----------------------cashoutServiceResponse is null");
							}
						} else {
							logger.info("----------------------cashoutResponse is null");
						}
					} else {
						logger.info("----------------------cashoutResponseE is null");
					}

				} else {
					logger.info("----------------------loginServiceResponse is null");
				}
			} else {
				logger.info("----------------------loginResponse is null");
			}
		} else {
			logger.info("----------------------loginResponseE is null");
		}
		logger.info("----------------------initiate cash out");
		return cashoutServiceResponse;
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

	public static void main(String[] args) throws AxisFault {
		logger.info("----------------------------------Hello World");
		MoneyTransfer moneyTransfer = new MoneyTransfer();
		String key = "ABCDEDF00000FFFF";
		String password = "password";

		String hashedPassword = hmacSha1(password, key);
		String hashedPswd = HmacUtils.hmacSha1(key, password);
		moneyTransfer.setAmount(BigDecimal.valueOf(1150));
		moneyTransfer.setMmo("readycash");
		moneyTransfer.setAgentUsername("mats@mats.com");
		logger.info("------------------------the hmac" + hashedPassword);
		logger.info("------------------------the second hmac" + hashedPswd);
		moneyTransfer.setReadyCashPin(password);
		moneyTransfer.setReceiver("08034083054");
		// moneyTransfer.setSender("08034083054");
		moneyTransfer.setAgentPin("0000000000000000");
		moneyTransfer.setSender("442597481485");
		moneyTransfer.setReference("405573");
		// check that dev branch is working
		logger.info("--------------------------------contents being sent"
				+ moneyTransfer.toString());
		// new ReadyCashClient().performCashIn(moneyTransfer);
		new ReadyCashClient().performCashout(moneyTransfer);
		// new ReadyCashClient().balanceEnquiry(moneyTransfer);
	}

}
