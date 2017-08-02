package dao;

import model.GiftCodeForUser;

/**
 * Created by magic_000 on 28/07/2017.
 */
public interface GiftCodeForUserDao {
    GiftCodeForUser getGiftCodeForUserById(String id);
    boolean save(GiftCodeForUser giftCodeForUser);
    boolean giftCodeExist(String id);


}
