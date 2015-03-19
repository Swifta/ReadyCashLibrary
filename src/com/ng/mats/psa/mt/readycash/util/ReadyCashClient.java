package com.ng.mats.psa.mt.readycash.util;

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
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Bank_transfer;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Bank_transferE;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Bank_transferResponse;
import com.readycashng.www.ws.api._1_0.AgentServiceServiceStub.Bank_transferResponseE;
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

/*
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Balance;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.BalanceE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.BalanceResponse;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.BalanceResponseE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Bank_transfer;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Bank_transferE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Bank_transferResponse;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Bank_transferResponseE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Cash_out;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Cash_outE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Cash_outResponse;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Cash_outResponseE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Login;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.LoginE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.LoginResponse;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.LoginResponseE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Mobile_transfer;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Mobile_transferE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Mobile_transferResponse;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.Mobile_transferResponseE;
import com.readycashng.www.ws.api._1_0.test.AgentServiceServiceStub.ServiceResponse;
import com.readycashng.www.ws.api._1_0.test.TerminatingExceptionException;
 */

public class ReadyCashClient {
	private static final Logger logger = Logger.getLogger(ReadyCashClient.class
			.getName());
	// private final static String testpin = "1234";
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

					logger.info("---------------------------Parameters Being sent::::\nAmount :"
							+ moneyTransfer.getAmount()
							+ "\nRecipient : "
							+ moneyTransfer.getReceiver()
							+ "\nPIN : "
							+ moneyTransfer.getAgentPin()
							+ "\nVoucher : "
							+ moneyTransfer.getSender());

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

	public ServiceResponse sendToAgent(MoneyTransfer moneyTransfer) {
		ServiceResponse loginServiceResponse = null, transferToBankServiceResponse = null;
		if (readyCashStub == null) {
			logger.info("------------------------readyCashStub is not available");
		} else {
			logger.info("---------------------------readyCashStub is available");

		}

		return transferToBankServiceResponse;
	}

	public ServiceResponse transferToBank(MoneyTransfer moneyTransfer) {
		ServiceResponse loginServiceResponse = null, transferToBankServiceResponse = null;
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

					Bank_transferE bank_transfer = new Bank_transferE();
					Bank_transfer bankTransferParam = new Bank_transfer();
					bankTransferParam.setAccount_name(moneyTransfer
							.getBankAccountName());
					bankTransferParam.setAccount_number(moneyTransfer
							.getBankAccountNumber());
					bankTransferParam.setAmount(moneyTransfer.getAmount());
					bankTransferParam.setBank(moneyTransfer.getBank());
					bankTransferParam
							.setNarration(moneyTransfer.getReference());
					bankTransferParam
							.setPhone_number(moneyTransfer.getSender());
					bankTransferParam.setPin(moneyTransfer.getAgentPin());
					bank_transfer.setBank_transfer(bankTransferParam);
					logger.info("----------------------After setting bank transfer parameters");
					Bank_transferResponseE bankTransferResponseE = new Bank_transferResponseE();
					try {
						logger.info("----------------------After initiating bank transfer response");
						bankTransferResponseE = readyCashStub
								.bank_transfer(bank_transfer);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TerminatingExceptionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (bankTransferResponseE != null) {
						logger.info("----------------------bankTransferResponseE is not null");
						Bank_transferResponse bankTransferResponse = bankTransferResponseE
								.getBank_transferResponse();
						if (bankTransferResponse != null) {
							logger.info("----------------------bankTransferResponse is not null");
							transferToBankServiceResponse = bankTransferResponse
									.get_return();
						} else {
							logger.info("----------------------bankTransferResponse is null");

						}
					} else {
						logger.info("----------------------bankTransferResponseE is null");

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
		logger.info("----------------------initiate transfer to bank");
		return transferToBankServiceResponse;

	}

	public static void main(String[] args) throws AxisFault {
		System.out.println("Hello I am here....");
		logger.info("----------------------------------Hello World");
		System.out.println("After the loggers....");
		MoneyTransfer moneyTransfer = new ReadyCashPropertyValues()
				.getPropertyValues();
		System.out.println("After setting the property values....");
		logger.info("--------------------------------contents being sent"
				+ moneyTransfer.toString());
		// new ReadyCashClient().performCashIn(moneyTransfer);
		new ReadyCashClient().performCashout(moneyTransfer);
		// new ReadyCashClient().transferToBank(moneyTransfer);
		// new ReadyCashClient().balanceEnquiry(moneyTransfer);
	}
}
