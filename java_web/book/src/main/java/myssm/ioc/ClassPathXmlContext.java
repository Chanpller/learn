package myssm.ioc;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlContext implements BeanFactory{
    private Map<String,Object> beanMap = new HashMap<>();
    private String path = "applicationContext.xml";
    public ClassPathXmlContext(){
        this(null);
    }
    public ClassPathXmlContext(String path){
       try {
           SAXReader reader = new SAXReader();
           Document document = null;
           try {
               if(path!=null){
                   this.path = path;
               }
               document = reader.read(this.getClass().getClassLoader().getResourceAsStream(path));
           } catch (DocumentException e) {
               e.printStackTrace();
           }
           Element root = document.getRootElement();

           List<Element> beans = root.elements("bean");
           for (Element bean : beans) {
               String id = bean.attributeValue("id");
               String className = bean.attributeValue("class");

               Object controller = null;
               controller = Class.forName(className).newInstance();
               beanMap.put(id,controller);
           }
           for (Element bean : beans) {
               String id = bean.attributeValue("id");
               String className = bean.attributeValue("class");

               Object controller = Class.forName(className).newInstance();
               beanMap.put(id,controller);
           }

           for (Element bean : beans) {
               String id = bean.attributeValue("id");
               List<Element> propertys = bean.elements("property");
               if(propertys!=null && propertys.size()>0){
                   for (Element property : propertys) {
                       String name = property.attributeValue("name");
                       String ref = property.attributeValue("ref");

                       Object refBean = beanMap.get(ref);

                       Object beanObject = beanMap.get(id);

                       Field declaredField = beanObject.getClass().getDeclaredField(name);
                       declaredField.setAccessible(true);
                       declaredField.set(beanObject,refBean);
                   }
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    @Override
    public Object get(String key) {
        return beanMap.get(key);
    }
}
