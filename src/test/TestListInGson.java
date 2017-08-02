package test;

import com.google.gson.Gson;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magic_000 on 28/07/2017.
 */
public class TestListInGson extends TestCase {


    class A {
        List<String> strings;

        int s;

        public A(){
            strings= new ArrayList<>();
            strings.add("akjnask");
            strings.add("kjadcndkja");
            s=8;
        }

        public List<String> getStrings() {
            return strings;
        }

        public void setStrings(List<String> strings) {
            this.strings = strings;
        }

        public int getS() {
            return s;
        }

        public void setS(int s) {
            this.s = s;
        }
    }

    public void testGsonList() {
        A a= new A();
        System.out.println(new Gson().toJson(a));
    }







}
