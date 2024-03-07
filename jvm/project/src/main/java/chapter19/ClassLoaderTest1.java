package chapter19;

/**
 * @author shkstart
 * @create 15:32
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) {
        //获取系统该类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);//sun.misc.Launcher$AppClassLoader@18b4aac2
        //获取扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);//sun.misc.Launcher$ExtClassLoader@1540e19d
        //试图获取引导类加载器：失败
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);//null

        //###########################
        try {
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);//null  String是引导类加载器加载的
            //自定义的类默认使用系统类加载器
            ClassLoader classLoader1 = Class.forName("chapter19.ClassLoaderTest1").getClassLoader();
            System.out.println(classLoader1);//sun.misc.Launcher$AppClassLoader@14dad5dc

            //关于数组类型的加载:使用的类的加载器与数组元素的类的加载器相同
            String[] arrStr = new String[10];
            System.out.println(arrStr.getClass().getClassLoader());//null:表示使用的是引导类加载器

            ClassLoaderTest1[] arr1 = new ClassLoaderTest1[10];
            System.out.println(arr1.getClass().getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2

            int[] arr2 = new int[10];
            System.out.println(arr2.getClass().getClassLoader());//null:不需要类的加载器


            System.out.println(Thread.currentThread().getContextClassLoader());//线程是系统类加载器 sun.misc.Launcher$AppClassLoader@14dad5dc
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
