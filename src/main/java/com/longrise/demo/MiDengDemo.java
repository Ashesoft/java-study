package com.longrise.demo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MiDengDemo {
  private static List<String> only_cache = new ArrayList<>();

  public boolean insertData(Map<String, Object> bean){
    String onlyFlag = (String) bean.get("only_flag");
    if(only_cache.contains(onlyFlag)){
      only_cache.remove(onlyFlag);
      return true;
    }
    return false;
  }

  public String getOnlyFlag(){
    String only_flag = LocalTime.now().toString();
    only_cache.add(only_flag);
    return only_flag;
  }
}
