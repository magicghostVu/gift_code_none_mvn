package dao;

import model.UserCollectionMappingModel;

/**
 * Created by magic_000 on 02/08/2017.
 */
public interface UserCollectionMappingDAO {
    UserCollectionMappingModel getUserCollectionModelByCollectionName(String name);
    boolean saveAModel(UserCollectionMappingModel model);
    boolean modelExist(UserCollectionMappingModel model);
}
