package test;

import app.context.AppContext;
import config.ServerConfig;
import dao.GiftCodeGeneralDAO;
import junit.framework.TestCase;
import model.GiftCodeGeneral;
import model.sub_properties.GameItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fresher on 28/07/2017.
 */
public class TestInsertAndGet extends TestCase {

    //AppContext appContext



    public void testSave() {

        ServerConfig.initConfig();
        GiftCodeGeneralDAO giftCodeGeneralDAO = AppContext.getInstance().getBean("gift_code_general", GiftCodeGeneralDAO.class);

        GiftCodeGeneral giftCodeGeneral = new GiftCodeGeneral();
        giftCodeGeneral.set_id("kjhbvfvbvdkjhg");
        giftCodeGeneral.setLastModified(1987328974L);
        giftCodeGeneral.setTimesCanUsed(182738);
        giftCodeGeneral.setTimesUsed(328795);

        List<String> arrIdGameItem = Arrays.asList("id1", "id2", "id3");

        List<GameItem> gameItems = arrIdGameItem.stream().map(id -> new GameItem(id, 1L)).collect(Collectors.toList());

        giftCodeGeneral.setGameItems(gameItems);


        giftCodeGeneralDAO.save(giftCodeGeneral);

    }

    public void testGet(){
        ServerConfig.initConfig();
        GiftCodeGeneralDAO giftCodeGeneralDAO = AppContext.getInstance().getBean("gift_code_general", GiftCodeGeneralDAO.class);


        GiftCodeGeneral codeGeneral= giftCodeGeneralDAO.getGiftCodeGeneralById("kjhbvfvbvdkjhg");

        System.out.println();

    }


}
