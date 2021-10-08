package org.wayne.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Description: 任何一个类都是Class 类的实例对象 对于任意一个类，都能够知道&调用这个类的所有属性和方法
 *  //方式1: Class c1=User.class;
 * //方式2: Class c2=u.getClass();
 * //方式3: Class c3=Class.forName("org.wayne.api.entity.User"); --can动态加载
 * @author: lwq
 */
@Slf4j
@Deprecated //记录用使用直接调用jdk的api
public class ReflexUtilQ {

    /**
     * 获取特定方法c.getMethod("add",new Class[]{int.class,int.class});
     * 方法调用method.invoke(对象，参数列表);
     *  method.invoke(a,10,10);
     */
    public static void printClassInfo(Object object){
        Class c=object.getClass();
        System.out.println("类的名称："+c.getName());

        /**
         * 一个成员方法就是一个method对象
         * getMethod()所有的 public方法，包括父类继承的 public
         * getDeclaredMethods()获取该类所有的方法，包括private ,但不包括继承的方法。
         */
        Method[] methods=c.getMethods();//获取方法
        //获取所以的方法，包括private ,c.getDeclaredMethods();

        for(int i=0;i<methods.length;i++){
            //得到方法的返回类型
            Class returnType=methods[i].getReturnType();
            System.out.print(returnType.getName());
            //得到方法名：
            System.out.print(methods[i].getName()+"(");

            Class[] parameterTypes=methods[i].getParameterTypes();
            for(Class class1:parameterTypes){
                System.out.print(class1.getName()+",");
            }
            System.out.println(")");
        }
    }

    /**
     * 获取属性变量
     * @param o
     */
    public static void printFiledInfo(Object o){

        Class c=o.getClass();
        /**
         * getFileds()获取public
         * getDeclaredFields()获取所有
         */
        Field[] fileds=c.getDeclaredFields();

        for(Field f:fileds){
            //获取成员变量的类型
            Class filedType=f.getType();
            System.out.println(filedType.getName()+" "+f.getName());
        }

    }

    /**
     * 获取构造函数
     * @param o
     */
    public static void printConstructInfo(Object o){
        Class c=o.getClass();

        Constructor[] constructors=c.getDeclaredConstructors();
        for (Constructor con:constructors){
            System.out.print(con.getName()+"(");

            Class[] typeParas=con.getParameterTypes();
            for (Class class1:typeParas){
                System.out.print(class1.getName()+" ,");
            }
            System.out.println(")");
        }
    }

}
