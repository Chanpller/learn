# 第20章-ThreadLocal源码分析

## 20.1源码分析

* set时以当前线程作为key，获取当前线程的ThreadLocalMap容器
* 如果当前线程没有这个容器，则创建一个容器然后存放数据
* 如果当前线程有这个容器，则直接从这个容器中获取数据
* ThreadLocal只能获取当前线程的容器的值。如果时父子类线程则无法获取子类或父类线程的值。

```java
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
    
        public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
    
     private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
 	void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
  
```

