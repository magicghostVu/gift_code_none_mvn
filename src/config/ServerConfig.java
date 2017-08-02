package config;

/**
 * Created by magic_000 on 20/06/2017.
 */
public class ServerConfig {

    public static String HOST_MONGO;
    public static Integer PORT_MONGO;
    public static String DB_NAME;

    public static String GIFT_CODE_FOR_USER_COLL;
    public static String GIFT_CODE_GENERAL;

    public static String TYPE_MAPPING;

    public static String COLLECTIONNAME_GENERATED;

    public static String USER_COLLECTION_MAPPING;


    //public static Gson globalGson = new Gson();


    public static void initConfig() {
        ServerPropertiesLoader properties = new ServerPropertiesLoader();
        HOST_MONGO = properties.getString("host_mongo");
        PORT_MONGO = properties.getInt("port_mongo");
        DB_NAME = properties.getString("db_name");
        GIFT_CODE_FOR_USER_COLL = properties.getString("gift_code_for_user_coll");
        GIFT_CODE_GENERAL = properties.getString("gift_code_general");
        TYPE_MAPPING = properties.getString("type_mapping");
        COLLECTIONNAME_GENERATED = properties.getString("collectionNameGenerated");
        USER_COLLECTION_MAPPING = properties.getString("userCollectionMapping");
    }


}
