package com.block.chain.news.service;

import com.block.chain.news.domain.fabric.FabricClient;
import com.block.chain.news.domain.fabric.*;
import com.block.chain.news.web.dto.fabric.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


@Slf4j
@Service
@NoArgsConstructor
public class FabricCCService {
    /**
     * Information for using Fabric network
     */
    @Value("${fabric.caServer.url}")
    private String CA_SERVER_URL;

    @Value("${fabric.caServer.adminName}")
    private String CA_SERVER_ADMIN_NAME;

    private String CA_SERVER_PEM_FILE =  "fabric-ca.pem";

    @Value("${fabric.org.name}")
    private String ORG_NAME;

    @Value("${fabric.org.mspName}")
    private String ORG_MSP_NAME;

    @Value("${fabric.org.adminName}")
    private String ORG_ADMIN_NAME;

    @Value("${fabric.peer.name}")
    private String PEER_NAME;

    @Value("${fabric.peer.url}")
    private String PEER_URL;

    private String PEER_PEM_FILE = "fabric-peer.pem";;

    @Value("${fabric.orderer.name}")
    private String ORDERER_NAME;

    @Value("${fabric.orderer.url}")
    private String ORDERER_URL;

    private String ORDERER_PEM_FILE = "fabric-orderer.pem";

    @Value("${fabric.org.userName}")
    private String USER_NAME;

    @Value("${fabric.org.userSecret}")
    private String USER_SECRET;

    @Value("${fabric.channel.name}")
    private String CHANNEL_NAME;

    private final String CHAINCODE_NAME = "news_51";

    private final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private final String EXPECTED_EVENT_NAME = "event";

    private FabricClient fabClient;
    private ChannelClient channelClient;
    private Channel channel;
    private JsonParser jsonParser = new JsonParser();

    private boolean requestToLedger(String fcn, String[] args){
        try {
            TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
            ChaincodeID ccid = ChaincodeID.newBuilder().setName(CHAINCODE_NAME).build();
            request.setChaincodeID(ccid);
            request.setFcn(fcn);
            String[] arguments = args;
            request.setArgs(arguments);
            request.setProposalWaitTime(1000);

            Map<String, byte[]> tm2 = new HashMap<>();
            tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); // Just some extra junk
            // in transient map
            tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); // ditto
            tm2.put("result", ":)".getBytes(UTF_8)); // This should be returned see chaincode why.
            tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); // This should trigger an event see chaincode why.
            request.setTransientMap(tm2);

            Iterator<String> mapIter = tm2.keySet().iterator();

            while (mapIter.hasNext()) {
                String key = mapIter.next();
                byte[] value = tm2.get(key);
            }
            Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);

            List<ProposalResponse> invalid = responses.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    log.info(response.getMessage());
                });
            }
            CompletableFuture<BlockEvent.TransactionEvent> cf = channel.sendTransaction(responses);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private Collection<ProposalResponse> queryToLedger(String[] args){
        try{
            String[] args1 = args;
            Collection<ProposalResponse> responses1Query = channelClient.queryByChainCode(CHAINCODE_NAME, "query", args1);

            return responses1Query;
        }catch(Exception e){
            return null;
        }
    }

    private void loadChannel() {
        // TODO
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
            String name = "user" + System.currentTimeMillis();
            userContext.setName(name);
            userContext.setAffiliation(ORG_NAME);
            userContext.setMspId(ORG_MSP_NAME);

            String eSecret = caClient.registerUser(name, ORG_NAME);

            userContext = caClient.enrollUser(userContext, eSecret);
            fabClient = new FabricClient(adminUserContext);

            channelClient = fabClient.createChannelClient(CHANNEL_NAME);
            channel = channelClient.getChannel();
            Peer peer = fabClient.getInstance().newPeer(PEER_NAME, PEER_URL);
            EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
            Orderer orderer = fabClient.getInstance().newOrderer(ORDERER_NAME, ORDERER_URL);
            channel.addPeer(peer);
            channel.addEventHub(eventHub);
            channel.addOrderer(orderer);
            channel.initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //User regi, query update
    //registerUser args[0]: userID, args[1]: role
    public boolean registerUser(String userId, String role) {
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "registerUser";
        String[] args = {userId, role};;
        return requestToLedger(fcn, args);
    }

    public FabricUser queryUser(String userId) {
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = {"user", userId};
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String id = jsonElement.getAsJsonObject().get("userID").toString();
                String role = jsonElement.getAsJsonObject().get("role").toString();
                System.out.println(id);
                return new FabricUser(id, role);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    //News regi, query update, delete
    //args[0]: NwesID, args[1]: userID, args[2]:subject, args[3]: content
    public boolean registerNews(String newsId, String userId, String subject, String content) {
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "registerNews";
        String[] args = {newsId, userId, subject, content};;
        return requestToLedger(fcn, args);
    }

    public FabricNews queryNews(String newsId) {
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = {"news", newsId };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String userId = jsonElement.getAsJsonObject().get("userID").toString();
                String subject = jsonElement.getAsJsonObject().get("subject").toString();
                String content = jsonElement.getAsJsonObject().get("content").toString();
                return new FabricNews(newsId, userId, subject, content);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    public boolean updateNews(String newsId, String userId, String subject, String content){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "updateNews";
        String[] args = {newsId, userId, subject, content};;
        return requestToLedger(fcn, args);
    }

    public boolean deleteNews(String newsId){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "deleteNews";
        String[] args = {newsId};;
        return requestToLedger(fcn, args);
    }

    //AD regi, query //registerAdvertisement
    //args[0]: AdvertisementID, args[1]: UserID, args[2]: amount, args[3] : months
    public boolean registerAD(String adId, String userId, String amount, String months){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "registerAdvertisement";
        String[] args = {adId, userId, amount, months};
        return requestToLedger(fcn, args);
    }

    public FabricAD queryAD(String adId) {
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "AD", adId };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String userId = jsonElement.getAsJsonObject().get("userID").toString();
                String amount = jsonElement.getAsJsonObject().get("amount").toString();
                String months = jsonElement.getAsJsonObject().get("months").toString();
                return new FabricAD(adId,userId,amount,months);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    //totalADAmountCalculation << 달시작할때
    public boolean totalADAmountCalculation(String[] adList){ // 광고 Id
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "totalADAmountCalculation";
        String str = adList[0];

        for(int i=1; i<adList.length; i++){
            str += ","+(adList[i]);
        }
        System.out.println(str);
        String[] args = {str};
        return requestToLedger(fcn, args);
    }

    //divisionAmount << 달 시작할때 이전 달의 수익 분
    //args[0] : newsID List or 기자ID List
    public boolean divisionAmount(String[] reporterList){ // userId(email)
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "divisionAmount";
        String str = reporterList[0];

        for(int i=1; i<reporterList.length; i++){
            str += ","+(reporterList[i]);
        }
        System.out.println(str);
        String[] args = {str};
        return requestToLedger(fcn, args);
    }

    //clickNews
    //args[0]: UserID, args[1]: NewsID
    public boolean clickNews(String userId, String newsId){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "clickNews";
        String[] args = {userId, newsId};
        return requestToLedger(fcn, args);
    }

    //totalAmount
    public FabricTotalAmount totalAmount(){
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "totalAmount" };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String amount = jsonElement.getAsJsonObject().get("totalADAmount").toString();
                System.out.println(amount);
                return new FabricTotalAmount(amount);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    //userAccount
    public FabricUserAccount userAccount(String userId){
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "userAccount", userId };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String amount = jsonElement.getAsJsonObject().get("amount").toString();
                return new FabricUserAccount(userId, amount);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }

    //userNewsView
    public FabricUserView userNewsView(String userId){
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "userNewsView", userId };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String count = jsonElement.getAsJsonObject().get("count").toString();
                return new FabricUserView(userId, count);

            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
}

