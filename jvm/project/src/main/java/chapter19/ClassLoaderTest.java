package chapter19;


import java.net.URL;

public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println("**********启动类加载器**************");
        //获取BootstrapClassLoader能够加载的api的路径
        URL[] urLs = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL element : urLs) {
            System.out.println(element.toExternalForm());
        }
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/resources.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/rt.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/sunrsasign.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/jsse.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/jce.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/charsets.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/lib/jfr.jar
//        file:/C:/Program%20Files/Java/jdk1.8.0_102/jre/classes

        //从上面的路径中随意选择一个类,来看看他的类加载器是什么:引导类加载器
        ClassLoader classLoader = java.security.Provider.class.getClassLoader();
        System.out.println(classLoader);//null

        System.out.println("***********扩展类加载器*************");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }
        //C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext
        //C:\WINDOWS\Sun\Java\lib\ext

//        //从上面的路径中随意选择一个类,来看看他的类加载器是什么:扩展类加载器
        ClassLoader classLoader1 = sun.security.ec.CurveDB.class.getClassLoader();
        System.out.println(classLoader1);//ssun.misc.Launcher$ExtClassLoader@63947c6b

    }
}
