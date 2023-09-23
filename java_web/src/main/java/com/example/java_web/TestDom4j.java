package com.example.java_web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class TestDom4j {
    /* 读取 xml 文件中的内容
     */
    @Test
    public void readXML() throws DocumentException {
// 需要分四步操作:
// 第一步，通过创建 SAXReader 对象。来读取 xml 文件，获取 Document 对象
// 第二步，通过 Document 对象。拿到 XML 的根元素对象
// 第三步，通过根元素对象。获取所有的 book 标签对象
// 第四小，遍历每个 book 标签对象。然后获取到 book 标签对象内的每一个元素，再通过 getText() 方法拿到
//        起始标签和结束标签之间的文本内容
// 第一步，通过创建 SAXReader 对象。来读取 xml 文件，获取 Document 对象
        SAXReader reader = new SAXReader();
        Document document = reader.read("src/books.xml");
// 第二步，通过 Document 对象。拿到 XML 的根元素对象
        Element root = document.getRootElement();
// 打印测试
// Element.asXML() 它将当前元素转换成为 String 对象
// System.out.println( root.asXML() );
// 第三步，通过根元素对象。获取所有的 book 标签对象
// Element.elements(标签名)它可以拿到当前元素下的指定的子元素的集合
        List<Element> books = root.elements("book");
// 第四小，遍历每个 book 标签对象。然后获取到 book 标签对象内的每一个元素，
        for (Element book : books) {
// 测试
// System.out.println(book.asXML());
// 拿到 book 下面的 name 元素对象
            Element nameElement = book.element("name");
// 拿到 book 下面的 price 元素对象
            Element priceElement = book.element("price");
// 拿到 book 下面的 author 元素对象
            Element authorElement = book.element("author");
// 再通过 getText() 方法拿到起始标签和结束标签之间的文本内容
            System.out.println("书名" + nameElement.getText() + " , 价格:"
                    + priceElement.getText() + ", 作者：" + authorElement.getText());
        }
    }
}
