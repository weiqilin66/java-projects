package org.wayne.common.qlexpress;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.Operator;
import org.wayne.common.qlexpress.entity.DemoQl;

import java.lang.reflect.Field;

/**
 * @Description:
 * @author: lwq
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        go2();
    }
    static void go2() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

//        String express = " map = new HashMap();\n" +
//                "  map.put(\"a\", \"a_value\");\n" +
//                "  map.put(\"b\", \"b_value\");\n" +
//                "  keySet = map.keySet();\n" +
//                "  objArr = keySet.toArray();\n" +
//                "  for (i=0;i<objArr.length;i++) {\n" +
//                "  key = objArr[i];\n" +
//                "   System.out.println(map.get(key));\n" +
//                "  };return 1;"
//               ; 注释目前只支持 /** **/
//        String express = "n=10;\n" +
//                "sum=0;\n" +
//                "for(i=0;i<n;i++){\n" +
//                "sum=sum+i;\n" +
//                "}\n" +
//                "return sum;"
//               ;

        // 脚本中声明函数
        final String ex2 = "function add(int a,int b){\n" +
                "  return a+b;\n" +
                "};\n" +
                "\n" +
                "function sub(int a,int b){\n" +
                "  return a - b;\n" +
                "};\n" +
                "\n" +
                "a=10;\n" +
                "return add(a,4) + sub(a,9);";
        runner.addOperatorWithAlias("如果", "if",null);
        runner.addOperatorWithAlias("则", "then",null);
        runner.addOperatorWithAlias("否则", "else",null);
        String exp ;
        exp = "如果  (语文+数学+英语>270) 则 {return 1;} 否则 {return 0;}";

//        String ex3 = "int 平均分 = (语文+数学+英语+综合考试.科目2)/4.0;return 平均分";
//        ExpressRunner runner2 = new ExpressRunner(true,true);
//        String[] names = runner2.getOutVarNames(ex3);
//        for(String s:names){
//            System.out.println("var : " + s);
//        }
        // 声明类必须导包 字符串必须转义
        runner.addClassField("applyNo", DemoQl.class, String.class, new Operator() {
            @Override
            public Object executeInner(Object[] objects) throws Exception {
                return objects[0];
            }
        });
        String express = "import org.wayne.common.qlexpress.entity.DemoQl;de = new DemoQl();de.setNo(\"1\"); return de;";
        Object r = runner.execute(express, context, null, true, false);


        System.out.println(r);
        System.out.println("end");
    }

    static void go1() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        final Field[] fields = context.getClass().getFields();
        context.put("a",1);
        context.put("b",2);
        context.put("c",3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }
}
