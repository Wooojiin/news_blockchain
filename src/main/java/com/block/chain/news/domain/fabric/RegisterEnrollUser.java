/****************************************************** 
 *  Copyright 2018 IBM Corporation 
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */
package com.block.chain.news.domain.fabric;

import org.hyperledger.fabric.sdk.*;

import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 
 * @author Balaji Kadambi
 *
 */



public class RegisterEnrollUser {

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";



	private static HFClient hfClient;
	private static Channel channel;

	public static void main(String args[]) {

		/**
		 *
		 * TODO
		 * 1. startPJT
		 * 2. goalcomplete
		 * 3. endPJT
		 * 4. stopPJT => 목표 미달성 즉, 사용자의 요청에 의해서 java에서 실행할 수 있는 형태로 되어 있다.
		 *
		 * */

		String CA_SERVER_URL = "http://l02bch4.p.ssafy.io:8054";
		String CA_SERVER_ADMIN_NAME = "admin";
		String CA_SERVER_PEM_FILE = "fabric-ca.pem";
		String ORG_NAME = "HFTeam2";
		String ORG_MSP_NAME = "HFTeam2MSP";
		String ORG_ADMIN_NAME = "Admin@HFTeam2";
		String PEER_NAME = "peer0.HFTeam2";
		String PEER_URL = "grpc://l02bch4.p.ssafy.io:8051";
		String PEER_PEM_FILE = "fabric-peer.pem";
		String ORDERER_NAME = "orderer0.ordererorg";
		String ORDERER_URL = "grpc://l02bch4.p.ssafy.io:7050";
		String ORDERER_PEM_FILE = "fabric-orderer.pem";
		String USER_NAME = "Admin@HFTeam2";
		String USER_SECRET = "HFTeam2pwd";
		String CHANNEL_NAME = "team2channel";

		try {
			Util.cleanUp();
			String caUrl = CA_SERVER_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(USER_NAME);
			adminUserContext.setAffiliation(ORG_NAME);
			adminUserContext.setMspId(ORG_MSP_NAME);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(USER_NAME, USER_SECRET);

			// Register and Enroll user to Org1MSP
			UserContext userContext = new UserContext();
			String name = "user"+System.currentTimeMillis();
			userContext.setName(name);
			userContext.setAffiliation(ORG_NAME);
			userContext.setMspId(ORG_MSP_NAME);

			String eSecret = caClient.registerUser(name, ORG_NAME);

			userContext = caClient.enrollUser(userContext, eSecret);

			System.out.println(userContext.getAccount());
			System.out.println(userContext.getAffiliation());
			System.out.println(userContext.getEnrollment());
			System.out.println(userContext.getMspId());
			System.out.println(userContext.getName());

			FabricClient fabClient = new FabricClient(adminUserContext);

			ChannelClient channelClient = fabClient.createChannelClient(CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(PEER_NAME, PEER_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
			Orderer orderer = fabClient.getInstance().newOrderer(ORDERER_NAME, ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();



//			TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//			ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_31").build();
//			request.setChaincodeID(ccid);
//			request.setFcn("doNotCompeleteGoal");
////			[projectID]
//			String[] arguments = { "qwerPJT"};
//			request.setArgs(arguments);
//			request.setProposalWaitTime(1000);
//
//			Map<String, byte[]> tm2 = new HashMap<>();
//			tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//			// in transient map
//			tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//			tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//			tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//			request.setTransientMap(tm2);
//
//			Iterator<String> mapIter = tm2.keySet().iterator();
//
//			while(mapIter.hasNext()){
//
//				String key = mapIter.next();
//				byte[] value = tm2.get(key);
//
//				System.out.println(key+" : "+value);
//
//			}
//
//
//			Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
//
//			System.out.println("+++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++++++++++++++++++++++++++");
//
//			List<ProposalResponse> invalid = responses.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
//			if (!invalid.isEmpty()) {
//				invalid.forEach(response -> {
//					System.out.println(response.getMessage());
//				});
//			}
//			CompletableFuture<BlockEvent.TransactionEvent> cf =  channel.sendTransaction(responses);

			String[] args1 = {"searchDonation","qwer2","qwerPJT"};


			Collection<ProposalResponse>  responses1Query = channelClient.queryByChainCode("donationtest_v_51", "query", args1);
			System.out.println(responses1Query.size());
			for (ProposalResponse pres : responses1Query) {
				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
				System.out.println(stringResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}


//			User검색

//			String[] args1 = {"searchUser","qwer"};
//
//
//			Collection<ProposalResponse>  responses1Query = channelClient.queryByChainCode("donationtest_v_11", "query", args1);
//			System.out.println(responses1Query.size());
//			for (ProposalResponse pres : responses1Query) {
//				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
//				System.out.println(stringResponse);
//			}

//
//searchDonationProject
//			PJT검색

//			String[] args1 = {"searchDonationProject","qwerPJT"};
//
//
//			Collection<ProposalResponse>  responses1Query = channelClient.queryByChainCode("donationtest_v_15", "query", args1);
//			System.out.println(responses1Query.size());
//			for (ProposalResponse pres : responses1Query) {
//				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
//				System.out.println(stringResponse);
//			}


//			Block Hash 검색

//			BlockchainInfo channelInfo = channel.queryBlockchainInfo();
//
//			long height = channelInfo.getHeight();
//			long MAX_LOG_COUNT = 10;
//
//			System.out.println("Channel height: " + height);
//
//			for (long current = height - 1; current > -1; --current) {
//				BlockInfo returnedBlock = channel.queryBlockByNumber(current);
//				final long blockNumber = returnedBlock.getBlockNumber();
//
//				System.out.println(String.format("Block #%d - Previous hash id: %s", blockNumber, Hex.encodeHexString(returnedBlock.getPreviousHash())));
//				System.out.println(String.format("Block #%d - Data hash: %s", blockNumber, Hex.encodeHexString(returnedBlock.getDataHash())));
//
//				if (height - current > MAX_LOG_COUNT) break;
//			}


// User 등록


//	TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_18").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("registerUser");
//					//[userID, pwd, role]
//					String[] arguments = { "qwer4", "qwer", "admin"};
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);

// pjt 등록
//TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_18").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("registerDonationProject");
//					//[projecttID, organizer, targetAmount, fundraisingEndDate, projectGoals, goalAmounts]
//					String[] arguments = { "qwerPJT", "qwer", "120000", "2020-05-20", "1,2,3", "40000,40000,40000"};
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);


// donation

//	TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_18").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("donateToProject");
//					//[projecttID, organizer, targetAmount, fundraisingEndDate, projectGoals, goalAmounts]
//					String[] arguments = { "qwer2", "qwerPJT", "120000"};
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);

// dontaion 검색
//String[] args1 = {"searchDonation","qwer2","qwerPJT"};
//
//
//	Collection<ProposalResponse>  responses1Query = channelClient.queryByChainCode("donationtest_v_18", "query", args1);
//			System.out.println(responses1Query.size());
//					for (ProposalResponse pres : responses1Query) {
//					String stringResponse = new String(pres.getChaincodeActionResponsePayload());
//					System.out.println(stringResponse);
//					}
//
//					} catch (Exception e) {
//					e.printStackTrace();
//					}


// startDonationPJT
//TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_27").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("startDonationProject");
//					String[] arguments = { "qwerPJT", "qwer" };
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);

//  startDonationProject
//
//	TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_29").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("startDonationProject");
//					String[] arguments = { "qwerPJT", "qwer" };
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);



//compeleteGoalsDonationProject 목표 완료 등록

//	TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_31").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("compeleteGoalsDonationProject");
////			[projecttID, organizer, goalID, contents ]
//					String[] arguments = { "qwerPJT", "qwer", "1", "1 suc"};
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);


//requestRefund

//	TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_31").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("requestRefund");
////			[userID, projectID]
//					String[] arguments = { "qwer2", "qwerPJT"};
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);

// doNotCompeleteGoal

//	TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
//	ChaincodeID ccid = ChaincodeID.newBuilder().setName("donationtest_v_31").build();
//			request.setChaincodeID(ccid);
//					request.setFcn("doNotCompeleteGoal");
////			[projectID]
//					String[] arguments = { "qwerPJT"};
//					request.setArgs(arguments);
//					request.setProposalWaitTime(1000);
//
//					Map<String, byte[]> tm2 = new HashMap<>();
//		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
//		// in transient map
//		tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
//		tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
//		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
//		request.setTransientMap(tm2);