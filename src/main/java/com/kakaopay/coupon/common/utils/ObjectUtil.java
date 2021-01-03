package com.kakaopay.coupon.common.utils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class ObjectUtil {
  public static Object convertMapToObject(Map<String, Object> map, Object obj) throws SecurityException {
    String keyAttribute;
    String setMethodString = "set";
    String methodString;
    Iterator itr = map.keySet().iterator();

    while (itr.hasNext()) {
      keyAttribute = (String) itr.next();
      methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
      Method[] methods = obj.getClass().getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        if (methodString.equals(methods[i].getName())) {
          try {
            Class[] classesType = methods[i].getParameterTypes();
            if (classesType[0].getName().equals("java.lang.Long") && map.get(keyAttribute) instanceof Integer) {
              methods[i].invoke(obj, ((Integer) map.get(keyAttribute)).longValue());
            } else {
              methods[i].invoke(obj, map.get(keyAttribute));
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    return obj;
  }

}
