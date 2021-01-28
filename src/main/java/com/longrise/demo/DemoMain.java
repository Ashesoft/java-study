package com.longrise.demo;

import java.util.HashMap;
import java.util.Map;

public class DemoMain {
  public static void main(String[] args) {
    MiDengDemo mDemo = new MiDengDemo();
    String onlyFlag = mDemo.getOnlyFlag();
    Map<String, Object> bean = new HashMap<>();
    bean.put("only_flag", onlyFlag);
    bean.put("data", "data");
    for (int i = 0; i < 3; i++) {
      boolean bool = mDemo.insertData(bean);
      if(bool){
        System.out.println("sucess");
      }else{
        System.out.println("fail");
      }
    }

    for (int i = 0; i < 3; i++) {
      onlyFlag = mDemo.getOnlyFlag();
      bean = new HashMap<>();
      bean.put("only_flag", onlyFlag);
      bean.put("data", "data");
      boolean bool = mDemo.insertData(bean);
      if(bool){
        System.out.println("sucess");
      }else{
        System.out.println("fail");
      }
    }
  }
}
