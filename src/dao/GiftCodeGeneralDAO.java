package dao;

import model.GiftCodeGeneral;

/**
 * Created by Fresher on 28/07/2017.
 */
public interface GiftCodeGeneralDAO {
    GiftCodeGeneral getGiftCodeGeneralById(String id);
    boolean save(GiftCodeGeneral giftCodeGeneral);
    boolean giftCodeExist(String code);
}
