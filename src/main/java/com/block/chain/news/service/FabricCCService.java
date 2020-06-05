package com.block.chain.news.service;

import com.block.chain.news.domain.fabric.FabricClient;
import com.block.chain.news.domain.fabric.*;
import com.block.chain.news.web.dto.fabric.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.*;
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
     * 패브릭 네트워크를 이용하기 위한 정보
     */
    //    @Value("${fabric.ca-server.url}")
    private String CA_SERVER_URL= "http://l02bch4.p.ssafy.io:8054";
    //    @Value("${fabric.ca-server.admin.name}")
    private String CA_SERVER_ADMIN_NAME = "admin";
    //    @Value("${fabric.ca-server.pem.file}")
    private String CA_SERVER_PEM_FILE =  "fabric-ca.pem";
    //    @Value("${fabric.org.name}")
    private String ORG_NAME = "HFTeam2";
    //    @Value("${fabric.org.msp.name}")
    private String ORG_MSP_NAME = "HFTeam2MSP";
    //    @Value("${fabric.org.admin.name}")
    private String ORG_ADMIN_NAME = "Admin@HFTeam2";
    //    @Value("${fabric.peer.name}")
    private String PEER_NAME = "peer0.HFTeam2";
    //    @Value("${fabric.peer.url}")
    private String PEER_URL = "grpc://l02bch4.p.ssafy.io:8051";
    //    @Value("${fabric.peer.pem.file}")
    private String PEER_PEM_FILE = "fabric-peer.pem";;
    //    @Value("${fabric.orderer.name}")
    private String ORDERER_NAME = "orderer0.ordererorg";;
    //    @Value("${fabric.orderer.url}")
    private String ORDERER_URL = "grpc://l02bch4.p.ssafy.io:7050";;
    //    @Value("${fabric.orderer.pem.file}")
    private String ORDERER_PEM_FILE = "fabric-orderer.pem";
    //    @Value("${fabric.org.user.name}")
    private String USER_NAME ="Admin@HFTeam2";
    //    @Value("${fabric.org.user.secret}")
    private String USER_SECRET = "HFTeam2pwd";
    //    @Value("${fabric.channel.name}")
    private String CHANNEL_NAME = "team2channel";
    private final String CHAINCODE_NAME = "news_51"; //****

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
    public boolean registerUser(String userID, String role) {
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "registerUser";
        String[] args = {userID, role};;
        return requestToLedger(fcn, args);
    }

    public FabricUser queryUser(String userID) {
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "user",userID };
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
    public boolean registerNews(String NewsID, String userID, String subject, String content) {
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "registerNews";
        String[] args = {NewsID, userID, subject, content};;
        return requestToLedger(fcn, args);
    }
    public FabricNews queryNews(String newsID) {
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "news",newsID };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String newsId = jsonElement.getAsJsonObject().get("newsID").toString();
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
    public boolean updateNews(String NewsID, String userID, String subject, String content){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "updateNews";
        String[] args = {NewsID, userID, subject, content};;
        return requestToLedger(fcn, args);
    }
    public boolean deleteNews(String NewsID){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "deleteNews";
        String[] args = {NewsID};;
        return requestToLedger(fcn, args);
    }
    //AD regi, query //registerAdvertisement
    //args[0]: AdvertisementID, args[1]: UserID, args[2]: amount, args[3] : months
    public boolean registerAD(String ADID,String UserID, String amount, String months){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "registerAdvertisement";
        String[] args = {ADID,UserID, amount, months};
        return requestToLedger(fcn, args);
    }
    public FabricAD queryAD(String ADID) {
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "AD",ADID };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String adID = jsonElement.getAsJsonObject().get("advertisementID").toString();
                String userID = jsonElement.getAsJsonObject().get("userID").toString();
                String amount = jsonElement.getAsJsonObject().get("amount").toString();
                String months = jsonElement.getAsJsonObject().get("months").toString();
                return new FabricAD(adID,userID,amount,months);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
    //totalADAmountCalculation << 달시작할때
    public boolean totalADAmountCalculation(String[] ADList){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "totalADAmountCalculation";
        String str = ADList[0];

        for(int i=1; i<ADList.length; i++){
            str += ","+(ADList[i]);
        }
        System.out.println(str);
        String[] args = {str};
        return requestToLedger(fcn, args);
    }
    //divisionAmount << 달 시작할때 이전 달의 수익 분
    //args[0] : newsID List or 기자ID List
    public boolean divisionAmount(String[] reporterList){
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
    public boolean clickNews(String UserID, String NewsID){
        if(fabClient == null){
            loadChannel();
        }
        String fcn = "clickNews";
        String[] args = {UserID, NewsID};
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
    public FabricUserAccount userAccount(String UserID){
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "userAccount", UserID };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String amount = jsonElement.getAsJsonObject().get("amount").toString();
                return new FabricUserAccount(UserID, amount);
            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
    //userNewsView
    public FabricUserView userNewsView(String UserID){
        if(fabClient == null) {
            loadChannel();
        }
        try{
            String[] args = { "userNewsView", UserID };
            Collection<ProposalResponse> responses1Query = queryToLedger(args);

            for (ProposalResponse pres : responses1Query) {
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                JsonElement jsonElement = jsonParser.parse(stringResponse);
                String count = jsonElement.getAsJsonObject().get("count").toString();
                return new FabricUserView(UserID, count);

            }
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
    //FabricUser, //FabricNews, //FabricAD, //FabricTotalAmount, //FabricUserView, //FabricUserAccount

    public static void main(String[] args){
        FabricCCService fbcc = new FabricCCService();
//        System.out.println(fbcc.registerUser("qwer1234","repoter"));
//        fbcc.queryUser("qwer123");
//        fbcc.registerNews("news1234","qwer1234","sports","content123");
//        fbcc.queryNews("news123");
//        fbcc.updateNews("news123","qwer123","sports","TTTTTTTT");
//        fbcc.registerAD("ad1234","qwer123","1000000","3");
//        fbcc.queryAD("ad123");
//        String[] arr = {"ad123","ad1234"};
//        System.out.println(fbcc.totalADAmountCalculation(arr));
//        System.out.println(fbcc.clickNews("qwer1234","news123"));
//        fbcc.userNewsView("qwer123");
//        String[] arr = {"qwer123","qwer1234"};
//        System.out.println(fbcc.divisionAmount(arr));
//        fbcc.userAccount("qwer1234");
    }
}

