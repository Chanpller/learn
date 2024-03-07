package chapter19;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
public class UserClassLoader extends ClassLoader {
    private String rootDir;
    public UserClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }
    /**
     * 编写findClass方法的逻辑
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 获取类的class文件字节数组
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            //直接生成class对象
            return defineClass(name, classData, 0, classData.length);
        }
    }
    /**
     * 编写获取class文件并转换为字节码流的逻辑 * @param className * @return
     */
    private byte[] getClassData(String className) {
        // 读取类文件的字节
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            // 读取类文件的字节码
            while ((len = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 类文件的完全路径
     */
    private String classNameToPath(String className) {
        return rootDir + "\\" + className.replace('.', '\\') + ".class";
    }

    public static void main(String[] args) {
        String rootDir = "D:\\ideaprojects\\learn\\jvm\\project\\src\\main\\java\\";

        try {
            //创建自定义的类的加载器1
            UserClassLoader loader1 = new UserClassLoader(rootDir);
            Class clazz1 = loader1.findClass("chapter19.User");
            //创建自定义的类的加载器2
            UserClassLoader loader2 = new UserClassLoader(rootDir);
            Class clazz2 = loader2.findClass("chapter19.User");
            //不能再加载了，会报错
            //loader (instance of  chapter19/UserClassLoader): attempted  duplicate class definition for name: "chapter19/User"
//            Class clazz25 = loader2.findClass("chapter19.User");
            System.out.printf(loader2.getParent().toString());

            System.out.println(clazz1 == clazz2); //clazz1与clazz2对应了不同的类模板结构。
            System.out.println(clazz1.getClassLoader());//chapter19.UserClassLoader@6e0be858
            System.out.println(clazz2.getClassLoader());//chapter19.UserClassLoader@610455d6

            //######################
            Class clazz3 = ClassLoader.getSystemClassLoader().loadClass("chapter19.User");
            //可以重复在家
            Class clazz4 = ClassLoader.getSystemClassLoader().loadClass("chapter19.User");

            System.out.println(clazz4==clazz3);//ture
            System.out.println(clazz3.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2


            System.out.println(clazz1.getClassLoader().getParent());//sun.misc.Launcher$AppClassLoader@18b4aac2

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
