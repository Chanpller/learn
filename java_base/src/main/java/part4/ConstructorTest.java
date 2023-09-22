package part4;

public class ConstructorTest {
    private String name;
    private String age;
    public ConstructorTest(){

    }
    public ConstructorTest(String name){
        this.name = name;
    }
    public ConstructorTest(String name,String age){
        this(name);
        this.age = name;
    }

    public static void main(String[] args) {
        ConstructorTest constructorTest = new ConstructorTest("mingzi");
        System.out.println(constructorTest.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
