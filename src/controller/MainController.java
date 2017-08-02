package controller;

import app.context.AppContext;
import com.google.gson.Gson;
import dao.DocumentDAO;
import dao.GiftCodeForUserDao;
import dao.GiftCodeGeneralDaoImpl;
import dao.UserCollectionMappingDAO;
import model.GiftCodeForUser;
import model.GiftCodeGeneral;
import model.UserCollectionMappingModel;
import model.sub_properties.GameItem;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import response.*;
import utils.db.DBUtils;
import utils.db.GiftCodeType;
import utils.db.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Fresher on 28/07/2017.
 */
@EnableAutoConfiguration
@RestController
public class MainController {

    private GiftCodeGeneralDaoImpl giftCodeGeneralDao = AppContext.getInstance().getBean("gift_code_general", GiftCodeGeneralDaoImpl.class);
    private GiftCodeForUserDao giftCodeForUserDao = AppContext.getInstance().getBean("gift_code_for_user", GiftCodeForUserDao.class);
    private DocumentDAO documentDAO = AppContext.getInstance().getBean("documentDAO", DocumentDAO.class);
    private DBUtils dbUtils = AppContext.getInstance().getBean("db_utils", DBUtils.class);
    private UserCollectionMappingDAO userCollectionMappingDAO = AppContext.getInstance().getBean("user_mapping_collection", UserCollectionMappingDAO.class);


    private Gson gson = new Gson();

    @RequestMapping(value = "/gen-giftcode-general", method = RequestMethod.POST, consumes = "application/json")
    public String genGiftCodeGeneral(@RequestBody String jsonData) {
        return handleGenGiftCodeGeneral(jsonData);
    }


    @RequestMapping(value = "/gen-giftcode-for-user", method = RequestMethod.POST, consumes = "application/json")
    public String genGiftCodeForUser(@RequestBody String jsonData) {
        return handleGenGiftCodeForUser(jsonData);

    }

    @RequestMapping(value = "/use-a-giftcode", method = RequestMethod.POST, consumes = "application/json")
    public String useAGiftCode(@RequestBody String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String giftCode = jsonObject.getString("gift_code");
            Integer uid = jsonObject.getInt("uid");
            return handleUseAGiftCode(giftCode, uid);
        } catch (JSONException je) {
            je.printStackTrace();
            return new BaseResponse(ErrorDefine.INVALID_REQUEST_BODY).toJson();
        }
    }


    @RequestMapping(value = "/get-all-giftcodes-by-collection-name", method = RequestMethod.POST)
    public String getCollectionOfGiftCodeByCollectionName(@RequestParam(value = "collectionName") String collectionName) {
        return handleGetCollectionGiftCode(collectionName);
    }

    @RequestMapping(value = "/get-all-collection-generated", method = RequestMethod.POST)
    public String getAllCollectionGenerated() {
        List<String> allCollName = dbUtils.getAllColltionGenerated();
        return new ResopnseAllCollectionGenerated(ErrorDefine.SUCCESS, allCollName).toJson();
    }

    @RequestMapping(value = "/get-list-uid-use-gift-code-event", consumes = "application/json", method = RequestMethod.POST)
    public String getListUidUseGiftCodeByCollection(@RequestBody String collectionNameData) {
        return handleGetListUidUsedGiftCodeByCollectionName(collectionNameData);
    }


    private String handleGetCollectionGiftCode(String collectionName) {
        List<Document> allGiftCodeByCollection = documentDAO.getDocumentsByCollectionName(collectionName);
        return new ResponseGetAllGiftCodeByCollectionName(ErrorDefine.SUCCESS, allGiftCodeByCollection).toJson();
    }


    private String handleGetListUidUsedGiftCodeByCollectionName(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String nameCollection = jsonObject.getString("nameCollection");
            UserCollectionMappingModel mappingModel = userCollectionMappingDAO.
                    getUserCollectionModelByCollectionName(nameCollection);
            if (mappingModel == null) {
                return new BaseResponse(ErrorDefine.COLLECTION_NAME_NOT_EXIST).toJson();
            } else {
                return new ResponseListUidByCollectionName(ErrorDefine.SUCCESS, mappingModel.getUidUsed()).toJson();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ErrorDefine.INVALID_REQUEST_BODY).toJson();
        }

    }


    private String handleUseAGiftCode(String giftCodeID, Integer uid) {

        if (Utils.checkGiftCodeValid(giftCodeID)) {
            Document d = documentDAO.getDocumentById(giftCodeID);
            if (d == null) {
                return new BaseResponse(ErrorDefine.GIFT_CODE_NOT_EXIST).toJson();
            } else {
                String typeGiftCode = d.getString("type");
                GiftCodeType type = GiftCodeType.valueOf(typeGiftCode);
                switch (type) {
                    case FOR_USER: {
                        GiftCodeForUser giftCodeForUser = Utils.convertDocumentToGiftCodeForUser(d);
                        //int uidInGiftCode = giftCodeForUser.getUid();
                        long currentTimeStamp = System.currentTimeMillis() / 1000L;
                        boolean notExp = giftCodeForUser.getExpTime() > currentTimeStamp;
                        boolean isForYou = giftCodeForUser.getUid() == uid;
                        boolean alreadyUsed = giftCodeForUser.getTimesUsed() >= giftCodeForUser.getTimesCanUsed();

                        //approve
                        if (notExp && isForYou && !alreadyUsed) {
                            giftCodeForUser.setTimesUsed(giftCodeForUser.getTimesUsed() + 1);
                            giftCodeForUserDao.save(giftCodeForUser);
                            List<GameItem> gameItems = giftCodeForUser.getGameItems();
                            return new ResponseUseGiftCodeSuccess(ErrorDefine.SUCCESS, gameItems).toJson();
                        }
                        // reject
                        else {
                            return new BaseResponse(ErrorDefine.GIFT_CODE_INVALID).toJson();
                        }
                        //break;
                    }

                    case GENERAL: {
                        GiftCodeGeneral giftCodeGeneral = Utils.convertDocumentToGiftCodeGeneral(d);
                        long currentTime = System.currentTimeMillis() / 1000L;
                        UserCollectionMappingModel mappingModel
                                = userCollectionMappingDAO.
                                getUserCollectionModelByCollectionName(giftCodeGeneral.getNameOfCollection());
                        Set<Integer> setUidUsed = new HashSet<>(mappingModel
                                .getUidUsed());
                        boolean userUsedThisCollection = setUidUsed.contains(uid);
                        boolean notExp = giftCodeGeneral.getExpTime() > currentTime;
                        boolean alreadyUsedRunOutOfTime = giftCodeGeneral.getTimesUsed() >= giftCodeGeneral.getTimesCanUsed();
                        //approve
                        if (notExp && !alreadyUsedRunOutOfTime && !userUsedThisCollection) {
                            giftCodeGeneral.setTimesUsed(giftCodeGeneral.getTimesUsed() + 1);
                            giftCodeGeneralDao.save(giftCodeGeneral);
                            List<GameItem> gameItems = giftCodeGeneral.getGameItems();
                            mappingModel.getUidUsed().add(uid);
                            userCollectionMappingDAO.saveAModel(mappingModel);
                            //System.out.println();
                            return new ResponseUseGiftCodeSuccess(ErrorDefine.SUCCESS, gameItems).toJson();
                        } else {
                            return new BaseResponse(ErrorDefine.GIFT_CODE_INVALID).toJson();
                        }
                        //break;
                    }
                    default: {
                        return new BaseResponse(ErrorDefine.GIFT_CODE_INVALID).toJson();
                        //break;
                    }
                }
            }
        } else {
            return new BaseResponse(ErrorDefine.GIFT_CODE_INVALID).toJson();
        }


    }


    private String handleGenGiftCodeForUser(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            long timeExp = jsonObject.getLong("expTime");
            JSONArray gameItems = jsonObject.getJSONArray("gameItems");
            List<GameItem> listGameItems = IntStream.range(0, gameItems.length())
                    .mapToObj(gameItems::getJSONObject)
                    .map(jsonObject1 -> gson.fromJson(jsonObject1.toString(), GameItem.class))
                    .collect(Collectors.toList());
            int timeCanUse = jsonObject.getInt("timeCanUse");
            int uid = jsonObject.getInt("uid");
            String code_id = genGiftCodeId();
            GiftCodeForUser giftCodeForUser = new GiftCodeForUser(code_id, listGameItems, 0L, timeCanUse, 0, timeExp, uid);
            giftCodeForUser.setType(GiftCodeType.FOR_USER);
            giftCodeForUserDao.save(giftCodeForUser);
            BaseResponse baseResponse = new BaseResponse(ErrorDefine.SUCCESS);
            baseResponse.setData(giftCodeForUser.get_id());
            return baseResponse.toJson();

        } catch (Exception e) {
            BaseResponse response = new BaseResponse(ErrorDefine.INVALID_REQUEST_BODY);
            return response.toJson();
        }
        //return null;
    }

    private String handleGenGiftCodeGeneral(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            int quantity = jsonObject.getInt("quantity");
            boolean resAddACollectionName = dbUtils.addACollectionName(jsonObject.getString("nameOfCollection"));
            if (!resAddACollectionName) {
                return new BaseResponse(ErrorDefine.COLLECTION_NAME_EXIST).toJson();
            }
            List<GiftCodeGeneral> generatedList = IntStream.range(0, quantity)
                    .mapToObj(index -> {
                        JSONArray gameItems = jsonObject.getJSONArray("gameItems");
                        List<GameItem> listGameItems = IntStream.range(0, gameItems.length())
                                .mapToObj(gameItems::getJSONObject)
                                .map(jsonObject1 -> gson.fromJson(jsonObject1.toString(), GameItem.class))
                                .collect(Collectors.toList());
                        int timeCanUse = jsonObject.getInt("timeCanUse");


                        // need refactor this block
                        GiftCodeGeneral giftCodeGeneral = new GiftCodeGeneral();
                        giftCodeGeneral.set_id(genGiftCodeId());
                        long timeExp = jsonObject.getLong("expTime");
                        giftCodeGeneral.setExpTime(timeExp);
                        giftCodeGeneral.setGameItems(listGameItems);
                        giftCodeGeneral.setTimesUsed(0);
                        giftCodeGeneral.setTimesCanUsed(timeCanUse);
                        String nameCollection = jsonObject.getString("nameOfCollection");
                        giftCodeGeneral.setNameOfCollection(nameCollection);
                        giftCodeGeneral.setType(GiftCodeType.GENERAL);


                        return giftCodeGeneral;
                    }).collect(Collectors.toList());
            generatedList.forEach(giftCodeGeneralDao::save);

            // save a mapping
            UserCollectionMappingModel newModel = new UserCollectionMappingModel(jsonObject.getString("nameOfCollection"));
            userCollectionMappingDAO.saveAModel(newModel);
            List<String> codesID = generatedList.stream().map(GiftCodeGeneral::get_id).collect(Collectors.toList());
            return new ResponseGenGeneralGiftCode(ErrorDefine.SUCCESS, codesID).toJson();
        } catch (Exception je) {
            BaseResponse response = new BaseResponse(ErrorDefine.INVALID_REQUEST_BODY);
            return response.toJson();
        }
    }


    private String genGiftCodeId() {
        Long timeStamp = System.nanoTime();
        String MD5TimeStamp = Utils.genMD5(timeStamp.toString());
        //System.out.println(MD5TimeStamp.length());
        String first8 = MD5TimeStamp.substring(0, 8);
        //System.out.println(first8.length());
        String first2MD5OfFirst8 = Utils.genMD5(first8).substring(0, 2);
        StringBuilder builderRes = new StringBuilder();
        builderRes.append(first8).append(first2MD5OfFirst8);
        return builderRes.toString();
    }

}
