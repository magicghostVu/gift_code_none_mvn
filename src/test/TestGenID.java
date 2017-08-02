package test;

import junit.framework.TestCase;
import utils.db.Utils;

/**
 * Created by Fresher on 28/07/2017.
 */
public class TestGenID extends TestCase {
    public void testGenId(){

        boolean res= Utils.checkGiftCodeValid("478d5b452d");

        System.out.println(res);

    }


}
