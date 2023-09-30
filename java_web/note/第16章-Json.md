# 第16章-Json

## 16.1 什么是 JSON

- JSON (JavaScript Object Notation) 是一种轻量级的数据交换格式。
- 易于人阅读和编写。同时也易于机器解析和生成。
- JSON 采用完全独立于语言的文本格式，而且很多语言都提供了对 json 的支持（包括 C, C++, C#, Java, JavaScript, Perl, Python 等）。 这样就使得 JSON 成为理想的数据交换格式。
-  json 是一种轻量级的数据交换格式。 轻量级指的是跟 xml 做比较。 数据交换指的是客户端和服务器之间业务数据的传递格式。

## 16.2 JSON定义

* json 是由键值对组成，并且由花括号（大括号）包围。每个键由引号引起来，键和值之间使用冒号进行分隔， 多组键值对之间进行逗号进行分隔。

```javascript
var jsonObj = {
    "key1":12,
    "key2":"abc",
    "key3":true,
    "key4":[11,"arr",false],
    "key5":{
        "key5_1" : 551,
        "key5_2" : "key5_2_value"
        },
    "key6":[{
            "key6_1_1":6611,
            "key6_1_2":"key6_1_2_value"
        	},{
            "key6_2_1":6621,
            "key6_2_2":"key6_2_2_value"
            }]
};
```

## 16.3 JSON 在 JavaScript 中的使用

- json 本身是一个对象。
-  json 中的 key 我们可以理解为是对象中的一个属性。
-  json 中的 key 访问就跟访问对象的属性一样： json

```javascript
alert(typeof(jsonObj));// object json 就是一个对象
alert(jsonObj.key1); //12
alert(jsonObj.key2); // abc
alert(jsonObj.key3); // true
alert(jsonObj.key4);// 得到数组[11,"arr",false]
// json 中 数组值的遍历
for(var i = 0; i < jsonObj.key4.length; i++) {
alert(jsonObj.key4[i]);
}
alert(jsonObj.key5.key5_1);//551
alert(jsonObj.key5.key5_2);//key5_2_value
alert( jsonObj.key6 );// 得到 json 数组
// 取出来每一个元素都是 json 对象
var jsonItem = jsonObj.key6[0];
// alert( jsonItem.key6_1_1 ); //6611
alert( jsonItem.key6_1_2 ); //key6_1_2_value
```

- json 的存在有两种形式。 
  - 一种是：对象的形式存在，我们叫它 json 对象。 
  - 一种是：字符串的形式存在，我们叫它 json 字符串。
-  一般我们要操作 json 中的数据的时候，需要 json 对象的格式。 
- 一般我们要在客户端和服务器之间进行数据交换的时候，使用 json 字符串。
-  JSON.stringify() 把 json 对象转换成为 json 字符串 JSON.parse() 把 json 字符串转换成为 json 对

```javascript
// 把 json 对象转换成为 json 字符串
var jsonObjString = JSON.stringify(jsonObj); // 特别像 Java 中对象的 toString
alert(jsonObjString)
// 把 json 字符串。转换成为 json 对象
var jsonObj2 = JSON.parse(jsonObjString);
alert(jsonObj2.key1);// 12
alert(jsonObj2.key2);// abc
```

## 16.4 JSON 在 java 中的使用

* 使用Gson转换对象注意事项

  * 通过new Gson()创建Gson对象

  * 通过Gson对象.toJson()转换为json字符串

  * 通过Gson对象.fromJson()将字符串转化为Java对象

    * fromJson()可以通过class确认对象类型

    * fromJson()转换为泛型对象时，通过泛型对象继承com.google.gson.reflect.TypeToken<T>指定泛型，然后通过继承对象.getType()作为入参获取到泛型类型，从而转换为自己想要的对象。

      ```java
      public class PersonListType extends TypeToken<ArrayList<Person>> {
      }
      ```

      ```java
       List<Person> list = new Gson().fromJson(personListJsonString, new PersonListType().getType());
      ```

      

    * 可以通过匿名内部类创建一个TypeToken<T>实例获取对象

      ```java
       new Gson().fromJson(personMapJsonString, new TypeToken<HashMap<Integer,Person>>(){}.getType())
      ```

* javaBean 和 json 的

```java
    public static void test1(){
        Person person = new Person(1,"国哥好帅!");
        // 创建Gson对象实例
        Gson gson = new Gson();
        // toJson方法可以把java对象转换成为json字符串
        String personJsonString = gson.toJson(person);
        System.out.println(personJsonString);
        // fromJson把json字符串转换回Java对象
        // 第一个参数是json字符串
        // 第二个参数是转换回去的Java对象类型
        Person person1 = gson.fromJson(personJsonString, Person.class);
        System.out.println(person1);
    }
```

* List 和 json 的

```java
   public  static void test2() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person(1, "国哥"));
        personList.add(new Person(2, "康师傅"));

        Gson gson = new Gson();

        // 把List转换为json字符串
        String personListJsonString = gson.toJson(personList);
        System.out.println(personListJsonString);

        List<Person> list = gson.fromJson(personListJsonString, new PersonListType().getType());
        System.out.println(list);
        Person person = list.get(0);
        System.out.println(person);
    }
```

* map 和 json 的互转

```java
 public static void test3(){
        Map<Integer,Person> personMap = new HashMap<>();

        personMap.put(1, new Person(1, "国哥好帅"));
        personMap.put(2, new Person(2, "康师傅也好帅"));

        Gson gson = new Gson();
        // 把 map 集合转换成为 json字符串
        String personMapJsonString = gson.toJson(personMap);
        System.out.println(personMapJsonString);

//        Map<Integer,Person> personMap2 = gson.fromJson(personMapJsonString, new PersonMapType().getType());
        Map<Integer,Person> personMap2 = gson.fromJson(personMapJsonString, new TypeToken<HashMap<Integer,Person>>(){}.getType());

        System.out.println(personMap2);
        Person p = personMap2.get(1);
        System.out.println(p);

    }
```

