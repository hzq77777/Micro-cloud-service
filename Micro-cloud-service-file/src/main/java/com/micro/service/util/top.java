package com.micro.service.util;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrator on 2018/7/9.
 */
public class top<T> {

    List<T> ls=new ArrayList<T>();
    public  void  add(T newElement){
        ls.add(newElement);
    }
    public  T getTop(){
        T tmp=null;
       if(ls.size()>0){
           tmp=ls.get(0);
       }
        Collections.sort(ls, new Comparator<T>(){
            @Override
            public int compare(T o1, T o2) {
                return 0;
            }
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        }) ;
          return  tmp;
    }
}
