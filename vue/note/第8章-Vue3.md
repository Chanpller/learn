# 第8章：Vue3

## 8.1 vue3简介

* 2020你那9月18日，vue.js发布3.0版本，代号：One Piece（海贼王）

* 耗时2年多、2600+次提交、30+RFC、600+此PR、99位贡献者

* github的tags地址：https://github.com/vuejs/core/releases/tag/v3.0.0
  
  ## 8.2 vue3带来了什么
  
  ### 8.3.1 性能提升

* 打包大小减少41%

* 初次渲染快55%,更新渲染快133%

* 内存减少54%
  
  ### 8.3.2 源码的升级

* 使用Proxy代替defineProperty实现响应式

* 重写虚拟DOM的实现和Tree-Sharking（去除多余代码）
  
  ### 8.3.3 拥抱TypeScriprt

* vue3可以更好的支持TypeScriprt
  
  ### 8.3.4 新特性
1. Composition(组合API)
* setup配置
* ref和reactive
* watch与watchEffect
* provide与inject
2. 新的内置组件
* Fragment
* Teleport
  Suspense
3. 其他改变
* 新的生命周期钩子

* data选项应始终被声明一个函数（不能声明为对象）

* 移除KeyCode支持作为v-on的修饰符（不能使用数字键了，比如@key-up.13这种不能使用了）
  
  ## 8.3 创建vue3.0
  
  ### 8.3.1 使用vue-cli创建
  
  ```##
  ## 查看版本号
  vue --version
  ## 全局安装，vue/cli脚手架必须时4.5.0以上
  npm install -g @vue/cli
  vue create vue3_test 创建vue3的工程
  ```
  
  ### 8.3.2 使用vite创建
  
  官方文档:https://v3.cn.vuejs.org/guide/installation.html#vite  
  vite官网:https://cn.vitejs.dev/  
1. 什么时vite?---新一代前端构建工具
2. 优势如下：
* 开发环境中，无需打包操作，可快速的冷启动。
* 轻量快速的热重载(HMR)。
3. 真正的按需编译，不再等待整个应用编译完成。
   传统构建与vite构建对比图
   ![](./../image/bundler.37740380.png)
   ![](./../image/esm.3070012d.png)

```#
## 创建项目，也可以用 npm init vite-app <project-name>
$ npm init vite@latest <project-name> --template vue

# npm 7+，需要加上额外的双短横线
$ npm init vite@latest <project-name> -- --template vue

$ cd <project-name>
$ npm install
$ npm run dev
```

## 8.4 初识vue3工程结构

### 8.4.1 main.js改变

* 引入的不再是vue的构造函数了，引入的时一个名为createApp的工厂函数

* 创建的应用实例对象--app（类似与vue2中的vm，但是app比vm更“轻”,app是个对象）

* vue2中的Vue不再是构造函数，已经移除，不能使用下面方式创建了。

* vue文件中的template可以没有根标签
  demo
  
  ```vue
  //引入的不再是vue的构造函数了，引入的时一个名为createApp的工厂函数，不再是构造函数。
  import { createApp } from 'vue'
  import App from './App.vue'
  //创建的应用实例对象--app（类似与vue2中的vm，但是app比vm更“轻”,app是个对象）
  const app = createApp(App)
  console.log(app)
  app.mount('#app')
  ```

// setTimeout(()=>{
//     app.unmount('#app');//卸载
// },2000)

// createApp(App).mount('#app')

//vue2中的Vue不再是构造函数，已经移除，不能使用下面方式创建了。Vue是undfined
// import Vue from 'vue'
// const  vm = new Vue({
//     render:h=>h(App)
// })

```
## 8.5 常用Composition API
### 8.5.1 拉开序幕的setup
1. 理解:vue3.0中一个新的配置项，值为一个函数。
2. setup是所有Composition API（组合API）“表演的舞台”
3. 组合中所有用到的数据、方法、声明周期钩子等等，均要配置在setup中。
4. setup函数的两种返回值
* 若返回一个对象，则对象中的属性、方法，在模板中均可直接使用。（重点关注）
* 若返回一个渲染函数，则可以自定义渲染内容。（了解）
5. 注意点:
* 尽量不要与vue2.x配置混用
* vue2.x配置（data、methods、computed..）中可以访问到setup中的属性、方法。
* 但在setup中不能访问vue2.x配置（data、methods、computed..）
* 如有重名，setup优先(错的)，以方法的放置顺序有关。谁放在后面就以谁的为主，覆盖之前的。
* setup不能是一个async函数，因为返回值不再是return的对象，而是promise,模板中看不到return对象中的属性。(Promise是一个构造函数，自己身上有all、reject、resolve这几个眼熟的方法，原型上有then、catch等同样很眼熟的方法。要取出属性，需要使用.then，vue无法识别，没做兼容。await与.then配合使用，所以有些同学容易写成async函数)

Demo vue2与vue3的基本使用和注意事项
```vue 
<template>
  <h1>一个人的信息</h1>
  <h2>姓名:{{ name }}</h2>
  <h2>年龄:{{ age }}</h2>
  <h2>性别:{{ sex }}</h2>
  <h2>num:{{ num }}</h2>
  <button @click="sayHellow()">显示信息</button>
  <button @click="test2()">vue2中读取vue3数据</button>
  <button @click="test3()">vue3 setup中读取vue2数据</button>
  <XuanRanHanShu></XuanRanHanShu>
</template>

<script>
import XuanRanHanShu from "@/components/XuanRanHanShu";
export default {
  name: 'App',
  components:{
    XuanRanHanShu
  },
  methods:{
    test2(){//所有属性可以正常读取
      console.log(this.name)
      console.log(this.age)
      console.log(this.sex)
      console.log(this.sayHellow)
    }
  },
  setup(){//setup函数
    let name = '张三'
    let age = 18
    let num = 300
    function sayHellow(){
      alert(`我叫${name}，我${age}岁了，你好啊`)
    }
    function test3(){
      console.log(name)
      console.log(age)
      console.log(this.sex)//无法读取，结果是undefined
      console.log(this.test2)//无法读取，结果是undefined
    }
    return {//将数据返回出去
      name,
      age,
      sayHellow,
      test3,
      num
    }
  },
  data(){
    return{
      sex:'男',
      num:200//页面呈现num为200,谁在后面就覆盖谁
    }
  }
}
</script>

<style>
</style>
```

Demo 返回一个渲染函数

```vue
<template>

</template>

<script>
import {h} from 'vue'//引入渲染函数h
export default {
  name: "XuanRanHanShu",
  setup(){
    return ()=>{//这里需要使用箭头函数
      return h('h1','返回渲染函数')//返回渲染函数
    }
  }
}
</script>

<style scoped>

</style>
```

## 8.6 ref函数

### 8.6.1 作用

定义一个响应式的数据

### 8.6.2 语法

* const xxx = ref(initValue)

* 创建一个包含响应式数据的引用对象（reference对象，简称ref对象）

* 操作数据：xxx.value

* 模板中读取数据：不需要xxx.value，直接写：{{xxx}}

* 如果是对象类型，模板中直接写 对象名.属性名。

* 对象类型修改数据，xxx.value.属性名
  
  ### 8.6.3 备注

* 接收的数据可以是：基本类型，也可以是对象类型

* 基本类型的数据：响应式依旧是靠Object.defineProperty()的get与set完成。

* 对象类型的数据：内部“求组”了vue3.0中的一个新函数----reactive函数变成Proxy对象(封装了es6 proxy，ref引用reactive函数，reactive函数再引用proxy实现)。
  
  ```vue
  <template>
  <h1>一个人的信息</h1>
  <h2>姓名:{{ name }}</h2>
  <h2>年龄:{{ age }}</h2>
  <h2>职位:{{ job.type }}</h2>
  <h2>薪资:{{ job.salary }}</h2>
  <button @click="sayHellow()">显示信息</button>
  <button @click="changeName()">改变名字</button>
  <button @click="changeAge()">改变年龄</button>
  <button @click="changeJob()">修改薪资</button>
  </template>
  ```

<script>
import {ref} from 'vue'
export default {
  name: 'App',
  components:{
  },
  setup(){//setup函数
    let name = '张三'
    let age = ref(18)
    let job = ref({
      type:'前端工程师',
      salary:'30k'
    })
    function sayHellow(){
      alert(`我叫${name}，我${age.value}岁了，你好啊`)
    }
    function changeName(){
      console.log('修改前',name)
      name = '李四'
      console.log('修改',name)
    }
    function changeAge(){
      console.log('修改前',age)
      age.value = 100//响应式修改基本类型
      console.log('修改',age)
    }
    function changeJob(){
      console.log('修改前',job)
      job.value.salary = '100k'//响应式修改对象类型
      console.log('修改',job)
    }

    return {//将数据返回出去
      name,
      age,
      job,
      sayHellow,
      changeName,
      changeAge,
      changeJob
    }
  }
}
</script>

<style>
</style>

```
## 8.7 reactive函数
### 8.7.1 作用
定义一个对象类型的响应式数据（无法处理基本数据类型，基本数据类型需要使用ref函数）
### 8.7.2 语法
const xxx = reactive(initValue)，initValue可以是对象也可以是数组，返回一个代理对象Proxy对象
* 创建一个包含响应式数据的Proxy对象
* 操作数据：xxx.属性名
* 模板中读取数据：直接写：{{xxx.属性名}}
### 8.7.3 备注
* reactive定义的响应式数据是深层次的，数据也可以操作（vue2的数据代理只能是那几个固定函数，vue3可以直接引用修改）
* 内部基于ES6的Proxy实现，通过代理对象操作源对象内部数据进行操作。
```vue
<template>
  <h1>一个人的信息</h1>
  <h2>姓名:{{ person.name }}</h2>
  <h2>年龄:{{ person.age }}</h2>
  <h2>职位:{{ person.job.type }}</h2>
  <h2>薪资:{{ person.job.salary }}</h2>
  <h2>爱好:</h2>
  <ul>
    <li v-for="hoby in person.hoby " v>{{hoby}}</li>
  </ul>
  <button @click="sayHellow()">显示信息</button>
  <button @click="changeName()">改变名字</button>
  <button @click="changeAge()">改变年龄</button>
  <button @click="changeJob()">修改薪资</button>
  <button @click="changehoby()">修改爱好</button>

</template>

<script>
import {reactive} from 'vue'
export default {
  name: 'App',
  components:{
  },
  setup(){//setup函数
    let person = reactive({
      age:18,
      name:'张三',
      job:{
        type:'前端工程师',
        salary:'30k'
      },
      hoby:['抽烟','喝酒','烫头']
    })
    function sayHellow(){
      alert(`我叫${person.name}，我${person.age}岁了，你好啊`)
    }
    function changeName(){
      person.name = '李四'//reactive的可以直接修改
    }
    function changeAge(){
      person.age = 100
    }
    function changeJob(){
      person.job.salary = '100k'
    }
    function changehoby(){
      person.hoby[0] = '看美女'//vue3可以直接修改引用，vue2不可以无法检测到
    }

    return {//将数据返回出去
      person,
      sayHellow,
      changeName,
      changeAge,
      changeJob,
      changehoby
    }
  }
}
</script>

<style>
</style>
```

## 8.8 vue3.0中的响应式原理

### 8.8.1 vue2.x的响应式

1. 实现原理
* 对象类型：通过Object.defineProperty()对属性的读取，修改进行拦截（数据劫持）。
* 数组类型：通过重写更新数组的一系列方法来实现拦截。（对数组的变更方法进行包裹）
2. 存在问题
* 新增属性、删除属性，界面不会更新。需要借助（vm实例的$set(对象，属性名，值)、$delete（对象，属性名，值）或Vue的set或delete才能实现页面更新）

* 直接通过下标修改数组，界面不会自动更新。需要借助（vm实例的$set(对象，属性名，值)、$delete（对象，属性名，值）或Vue的set或delete才能实现页面更新），或者数组的splice等方法才能更新。
  
  ### 8.8.2 vue3的响应式
1. 是实现原理
* 通过Proxy(代理):拦截对象中任意属性的变化，包括属性的读写、属性的添加、属性的删除等。

* 通过Reflect(反射):对被代理对象的属性进行操作。

* MDN文档中描述的Proxy和Reflect:
  Proxy:https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Proxy  
  Reflect:https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Reflect
  
  ```vue
  <script>
        const proxyPerson = {
            name:'张三',
            age:18
        }
        //模拟vue3中实现响应式
        const  p = new Proxy(proxyPerson,{
            //有人读取p的某个属性时调用。
            //target是proxyPerson，propName是属性名，receiver是接收者Proxy对象。
            get(target, propName, receiver) {
                console.log('get-target',target)
                console.log('get-propName',propName)
                console.log('get-receiver',receiver)
                //一般方法直接设置
                return target[propName]
            },
            //有人修改p的某个属性、或给追加属性时调用
            set(target, propName, value, receiver) {
                console.log('set-target',target)
                console.log('set-propName',propName)
                console.log('set-value',value)
                console.log('set-receiver',receiver)
  
                //vue3通过Reflect进行设置的，该方法返回一个设置成功或失败。
                //Reflect如果有相同对象，相同的属性名，修改值不会报错，只会返回成功或失败。
                //如果借助Object.defineProperty进行set操作，有相同对象，相同的属性名会报错。
                return Reflect.set(target,propName,value)
            },
            //有人删除P的某个属性时被调用
            deleteProperty(target, propName) {
                console.log('deleteProperty-target',target)
                console.log('deleteProperty-propName',propName)
                return delete  target[propName]
            }
        })
    </script>
  ```
  
  ## 8.9 reactive对比ref
1. 从定义数据角度对比
* ref用来定义**<font color='red'>基本数据类型</font>**数据
* reactive用来定义**<font color='red'>对象或数组类型</font>**数据
* ref可以用来定义**<font color='red'>对象或数组类型</font>**数据，它的内部会自动通过reactive转化为代理对象
2. 从原理角度对比
* ref通过Object.defineProperty()的get和set实现响应式（数据劫持）
* reactive通过使用Proxy来实现响应式（数据劫持），并通过<font color='red'>Reflect</font>操作<font color='orange'>源对象</font>内部的数据
3. 从使用角度对比
* ref定义的数据，操作数据**<font color='red'>需要</font>**.value，读取数据时模板中直接读取，不需要使用.value
* reactive定义的数据操作数据与读取数据**<font color='red'>均不需要</font>**.value

## 8.11 setup的两个注意点

1. setup执行的时机
* 在beforeCreate之前执行一次，this是undefined
2. setup的参数
* props：值为对象，包含：组件外部传递过来，且组件内部声明接收了的属性。
* context：上下文对象。里面包含三个属性：attrs:值为对象，包含：组件外部传递过来，但没有在props配置中声明的属性，相当于vue2中的this.$attrs。slots:收到的插槽内容，相当于Vue2中的this.$slots。emit:分发自定义事件的函数，相当于vue2中的this.$emit。 

## 8.12计算属性与监视

1. computed函数
   
   * 与vue2中computed配置功能一致。也可以按照vue2的方式写
   
   * 写法
     
     ```
     import {computed} from 'vue'
     
     setup(){
         ...
         //计算属性简写，不是响应式的，只能读取
         let fullName = computed(()=>{
             return person.firstName + '-' + person.lastName
         })
         //计算属性完整写法，响应式的
         let fullName = computed({
             get(){
               return person.firstName + '-' + person.lastName
             },
             set(value){
               const nameArr = value.split('-')
               person.firstName = nameArr[0]
               person.lastName = nameArr[1]
             }
         })
     
     }
     ```
* 调试computed 3.2+
  
  `computed` 可接受一个带有 `onTrack` 和 `onTrigger` 选项的对象作为第二个参数：
  
  - `onTrack` 会在某个响应式 property 或 ref 作为依赖被追踪时调用。
  - `onTrigger` 会在侦听回调被某个依赖的修改触发时调用。
  
  所有回调都会收到一个 debugger 事件，其中包含了一些依赖相关的信息。推荐在这些回调内放置一个 `debugger` 语句以调试依赖。
  
  ```js
  const plusOne = computed(() => count.value + 1, {
    onTrack(e) {
      // 当 count.value 作为依赖被追踪时触发
      debugger
    },
    onTrigger(e) {
      // 当 count.value 被修改时触发
      debugger
    }
  })
  // 访问 plusOne，应该触发 onTrack
  console.log(plusOne.value)
  // 修改 count.value，应该触发 onTrigger
  count.value++
  ```
  
  `onTrack` 和 `onTrigger` 仅在开发模式下生效。
1. watch 函数
   
   * 与vue2中watch配置功能一致。
   
   * 监视对象：数组，ref对象（需要手动开启深度监视），reactive对象（默认开启深度监视），不能监视基本数据类型。
   
   * 注意事项：
     
     * 监视reactive定义的响应式数据时：oldValue无法正确获取、强制开启了深度监视（deep配置失效）（和vue2中是一样的，都是对象的引用）
   
   * 监视reactive定义的响应式数据中某个属性时：deep配置有效。 
* 监视的newValue,oldValue始终返回该对象的当前值和上一个状态值的引用，普通数据引用会改变，如果是对象，将会是proxy对象，引用是不会改变的。所以newValue,oldValue结果是一样的。
  
  * 写法
    
    * 监听单个数据
      
      ```js
      // 侦听一个 getter
      const state = reactive({ count: 0 })
      watch(
       () => state.count,
       (count, prevCount) => {
         /* ... */
       }
      )
      
      // 直接侦听ref
      const count = ref(0)
      watch(count, (count, prevCount) => {
       /* ... */
      ```
    
    })
    
    ```
    * 监听多个数据
    
    ```js
    const firstName = ref('')
    const lastName = ref('')
    
    watch([firstName, lastName], (newValues, prevValues) => {
      console.log(newValues, prevValues)
    })
    
    firstName.value = 'John' // logs: ["John", ""] ["", ""]
    lastName.value = 'Smith' // logs: ["John", "Smith"] ["John", ""]
    ```
    
    * 监听响应式对象
      
      ```js
      const numbers = reactive([1, 2, 3, 4])
      
      watch(
       () => [...numbers],
       (numbers, prevNumbers) => {
         console.log(numbers, prevNumbers)
       }
      )
      ```
    
    numbers.push(5) // logs: [1,2,3,4,5] [1,2,3,4]
    
    ```
    * 深度嵌套对象或数组中的 property 变化时，仍然需要 `deep` 选项设置为 true。
    
    ```js
    const state = reactive({ 
      id: 1,
      attributes: { 
        name: '',
      }
    })
    
    watch(
      () => state,
      (state, prevState) => {
        console.log('not deep', state.attributes.name, prevState.attributes.name)
      }
    )
    
    watch(
      () => state,
      (state, prevState) => {
        console.log('deep', state.attributes.name, prevState.attributes.name)
      },
      { deep: true }
    )
    ```
    
    state.attributes.name = 'Alex' // 日志: "deep" "Alex" "Alex"
    
    ```
    * 如果你在同一个函数里同时改变这些被监听的来源，监听器仍只会执行一次：
    
    ```js
    setup() {
      const firstName = ref('')
      const lastName = ref('')
    
      watch([firstName, lastName], (newValues, prevValues) => {
        console.log(newValues, prevValues)
      })
    
      const changeValues = () => {
        firstName.value = 'John'
        lastName.value = 'Smith'
        // 打印 ["John", "Smith"] ["", ""]
      }
    
      return { changeValues }
    }
    ```
    
    * 通过更改设置 `flush: 'sync'`，我们可以为每个更改都强制触发监听器，尽管这通常是不推荐的。或者，可以用 [nextTick](https://v3.cn.vuejs.org/api/global-api.html#nexttick) 等待侦听器在下一步改变之前运行。
      
      ```js
      const changeValues = async () => {
       firstName.value = 'John' // 打印 ["John", ""] ["", ""]
       await nextTick()
       lastName.value = 'Smith' // 打印 ["John", "Smith"] ["John", ""]
      }
      ```
    
    * 监听一个响应式对象或数组将始终返回该对象的当前值和上一个状态值的引用。为了完全侦听深度嵌套的对象和数组，可能需要对值进行深拷贝。这可以通过诸如 [lodash.cloneDeep](https://lodash.com/docs/4.17.15#cloneDeep) 这样的实用工具来实现
      
      ```js
      import _ from 'lodash'
      
      const state = reactive({
       id: 1,
       attributes: {
         name: '',
       }
      })
      
      watch(
       () => _.cloneDeep(state),
       (state, prevState) => {
         console.log(state.attributes.name, prevState.attributes.name)
       }
      )
      
      state.attributes.name = 'Alex' // 日志: "Alex" ""
      ```

## 8.13 watchEffect函数

* watch的套路是：既要指明监视的属性，也要指明监视的回调。

* watchEffect的套路是：不指明监视那个属性，监视的回调中用到哪个属性，那就监视哪个属性。

* watchEffect有点项computed:

但是computed注重计算的计算出来的值（回调函数的返回值），所以必须要写返回值。

watchEffect注重的是过程（回调函数的函数体），所以不用写返回值。

* 应用场景：报销流程，ajax请求

```js
setup(){
    let sum = ref(0)
    let age = ref(10)

    watch(sum,(newValue,oldValue)=>{
      console.log('sum newValue == oldValue',newValue===oldValue)
      console.log('sum',newValue,oldValue)
    },{deep:false})//此处的deep配置失效

    watchEffect(()=>{
      console.log("sum="+sum.value++);//可以操作数据，执行1此，watch函数执行两次
      console.log("age="+age.value);
    })

    return {
      sum,
      age
    }
  }
```

## 8.14 生命周期

1. Vue3中可以继续使用Vue2中的生命周期钩子，但有两个被更名：
* beforeDestroy改名为beforeUnmount

* destroyed改名为unmounted
2. Vue3也提供了Composition API形式的生命周期钩子，与Vue2的对应关系如下：

| 选项式 API           | Hook inside `setup`     |
| ----------------- | ----------------------- |
| `beforeCreate`    | Not needed*（setup相当于这个） |
| `created`         | Not needed*（setup相当于这个） |
| `beforeMount`     | `onBeforeMount`         |
| `mounted`         | `onMounted`             |
| `beforeUpdate`    | `onBeforeUpdate`        |
| `updated`         | `onUpdated`             |
| `beforeUnmount`   | `onBeforeUnmount`       |
| `unmounted`       | `onUnmounted`           |
| `errorCaptured`   | `onErrorCaptured`       |
| `renderTracked`   | `onRenderTracked`       |
| `renderTriggered` | `onRenderTriggered`     |
| `activated`       | `onActivated`           |
| `deactivated`     | `onDeactivated`         |

3. setup中的生命钩子比在外面定义的生命周期钩子先执行
   
   ```vue
   <template>
     <button @click="isShow = !isShow">开关隐藏</button>
    <LifeCycle v-if="isShow"></LifeCycle>
   </template>
   
   <script>
   import LifeCycle from "@/components/LifeCycle";
   import {ref,onBeforeMount,onMounted,onBeforeUpdate,onUpdated,onBeforeUnmount,onUnmounted} from 'vue'
   export default {
     name: 'App',
     components:{
       LifeCycle
     },
     beforeCreate(){
       console.log("-------beforeCreate--------")
     },
     created(){
       console.log("-------created--------")
     },
     beforeMount(){
       console.log("-------beforeMount--------")
     },
     mounted(){
       console.log("-------mounted--------")
     },
     beforeUpdate(){
       console.log("-------beforeUpdate--------")
     },
     updated(){
       console.log("-------updated--------")
     },
     beforeUnmount(){
       console.log("-------beforeUnmount--------")
     },
     unmounted(){
       console.log("-------unmounted--------")
     },
     setup(){
       let isShow = ref(true);
       console.log("-------init--------")
       onBeforeMount(()=>{
         console.log("-------onBeforeMount--------")
       })
       onMounted(()=>{
         console.log("-------onMounted--------")
       })
       onBeforeUpdate(()=>{
         console.log("-------onBeforeUpdate--------")
       })
       onUpdated(()=>{
         console.log("-------onUpdated--------")
       })
       onBeforeUnmount(()=>{
         console.log("-------onBeforeUnmount--------")
       })
       onUnmounted(()=>{
         console.log("-------onUnmounted--------")
       })
   
       return{
         isShow
       }
     }
   
     //控制带输出结果为
   /*    App.vue?91a0:15 -------init--------
       App.vue?91a0:15 -------beforeCreate--------
       App.vue?91a0:18 -------created--------
       App.vue?91a0:42 -------onBeforeMount--------
       App.vue?91a0:21 -------beforeMount--------
       App.vue?91a0:45 -------onMounted--------
       App.vue?91a0:24 -------mounted--------
       App.vue?91a0:48 -------onBeforeUpdate--------
       App.vue?91a0:27 -------beforeUpdate--------
       App.vue?91a0:51 -------onUpdated--------
       App.vue?91a0:30 -------updated--------
       App.vue?91a0:48 -------onBeforeUpdate--------
       App.vue?91a0:27 -------beforeUpdate--------
       App.vue?91a0:51 -------onUpdated--------
       App.vue?91a0:30 -------updated--------*/
   }
   </script>
   
   <style>
   </style>
   ```

## 8.15 自定义hook函数

* 概念：本质时一个函数，把setup函数中使用的compostion API进行了封装。

* 类似于vue2中的mixin

* 自定义hook的优势，复用代码，让setup中的逻辑更清楚易懂。

* 使用:定义一个js，将内容拿出去，返回对应数据。
  
  DemoDemo组件
  
  ```vue
  <template>
    <h2>当前点击时鼠标的坐标为：x:{{point.x}},y:{{point.y}}</h2>
  </template>
  
  <script>
  import usePoint from '../hooks/usePoint'
  export default {
    name: "DemoDemo",
    setup(){
      const point = usePoint();
      return {
        point
      }
    }
  }
  </script>
  ```
  
   usePoint.js
  
  ```json
  import {onBeforeMount, onBeforeUnmount, reactive} from "vue";
  
  export  default  function (){
  
      let point = reactive({
          x:0,
          y:0
      })
      function  savePoint(event){
          point.x = event.pageX
          point.y = event.pageY
      }
      onBeforeMount(()=>{
          window.addEventListener('click',savePoint)
      })
      onBeforeUnmount(()=>{
          window.removeEventListener('click',savePoint)
      })
      return point
  }
  ```

## 8.17 toRef和toRefs

作用：创建一个ref对象，其<font color='red'>value值指向另一个对象中的某个属性</font>。

语法：const name = toRef(对象名,'属性名')

应用：要将响应式对象中的某个属性单独提供给外部使用时。（即不想在模板做写对象名，直接写属性）

扩展：toRefs与toRef功能一致，但可以批量创建多个ref对象，语法：toRefs(对象名)

注意：与ref有区别，ref做出来的时基本数据类型响应式，创建了一个新对象，无法做到原对象的响应式。

```vue
<template>

  <h2>姓名：{{person.name}}</h2>
  <h2>年龄：{{age}}</h2>
  <h2>薪资：{{person.job.ji.salary}}K</h2>
  <h2>名字：{{mingzi}}K</h2>
  <button @click="person.name += '改名'">修改姓名 </button>
  <button @click="age ++ ">增长年龄</button>
  <button @click="person.job.ji.salary ++">涨薪</button>
  <button @click="mingzi += '-' ">修改名字，姓名不会改变</button>
  <br/>
  <h2>学生姓名：{{studentName}}</h2>
  <h2>学生年龄：{{studentAge}}</h2>
  <button @click="studentAge++">修改学生年龄 </button>
  <br/>
</template>

<script>
import {ref,reactive,toRef,toRefs} from 'vue'
export default {
  name: "DemoDemo",
  setup(){
    let person = reactive({
      name:'张三',
      age:18,
      job:{
        ji:{
          salary:20
        }
      }
    })
    let student = reactive({
      studentName:'张三',
      studentAge:18,
    })
    return {
      mingzi:ref(person.name),
      age:toRef(person,'age'),
      person,
      ...toRefs(student)
    }
  }
}
</script>

<style scoped>

</style>
```

## 8.18 shallowReactive与shallowRef

shallowReactive：值处理对象最外层属性的响应式（浅响应式，创建一个响应式代理，它跟踪其自身 property 的响应性，但不执行嵌套对象的深层响应式转换 (暴露原始值)）。

shallowRef：只处理基本数据类型的响应式，不进行对象的响应式处理（创建一个跟踪自身 .value 变化的 ref，但不会使其值也变成响应式的。）。

什么时候用：如果有一个对象数据，结构比较深，但变化时只是外出属性变化，使用shallowReacitve。

如果有一个对象数据，后续功能不会修改该对象中的属性，而是生成新的对象来替换，使用shallowRef。

```vue
<template>
  <h2>num：{{num}}</h2>
  <button @click="num++">num加1 </button>
  <br/>
  <h2>学生名字：{{student}}</h2>
  <button @click="student.studentName += '名字' ">修改学生名字 </button>
  <button @click="student.age += 1 ">修改学生年龄</button>
  <br/>
  <h2>姓名：{{person.name}}</h2>
  <h2>年龄：{{person.age}}</h2>
  <h2>薪资：{{person.job.ji.salary}}K</h2>
  <button @click="person.name += '改名'">修改姓名 </button>
  <button @click="person.age ++ ">增长年龄</button>
  <button @click="person.job.ji.salary ++">涨薪</button>
  <br/>

</template>

<script>
import {shallowRef,shallowReactive} from 'vue'
export default {
  name: "DemoDemo",
  setup(){
    /*可以正常修改，只会解析第一层*/
    let num = shallowRef(0);
    /*单独修改不生效，但是模板重新解析的时候，页面会修改*/
    let student = shallowRef({
      studentName:'学生',
      age:100
    });
    /*只有第一层属性生效，里面的修改，需要等待整个对象重新解析时，才能呈现*/
    let person = shallowReactive({
      name:'张三',
      age:18,
      job:{
        ji:{
          salary:20
        }
      }
    })

    return {
      num,
      person,
      student
    }
  }
}
</script>

<style scoped>

</style>
```

## 8.19 readonly与shallowReadonly

* readonly：让一个响应式数据变为只读的（深只读）

* shallowReadonly：让一个响应式数据变为只读的（浅只读）

* 应用场景：不希望数据被修改时，比如别的组件传给你的数据，表单校验后不允许修改。
  
  ```vue
  <template>
  <h2>num：{{num}}</h2>
  <button @click="num++">num加1 </button>
  <br/>
  <h2>学生名字：{{student}}</h2>
  <button @click="student.studentName += '名字' ">修改学生名字 </button>
  <button @click="student.age += 1 ">修改学生年龄</button>
  <br/>
  <h2>姓名：{{person.name}}</h2>
  <h2>年龄：{{person.age}}</h2>
  <h2>薪资：{{person.job.ji.salary}}K</h2>
  <button @click="person.name += '改名'">修改姓名 </button>
  <button @click="person.age ++ ">增长年龄</button>
  <button @click="person.job.ji.salary ++">涨薪</button>
  <br/>
  ```

</template>

<script>
import { ref, readonly, shallowReadonly, reactive} from 'vue'
export default {
  name: "DemoDemo",
  setup(){
    /*可以正常修改，只会解析第一层*/
    let num = ref(0);
    /*单独修改不生效，但是模板重新解析的时候，页面会修改*/
    let student = ref({
      studentName:'学生',
      age:100
    });
    //第一层不能修改，剩下的可以修改
    num = readonly(num)
    let person = reactive({
      name:'张三',
      age:18,
      job:{
        ji:{
          salary:20
        }
      }
    })
    //第一层不能修改，剩下的可以修改
    person = shallowReadonly(person)
    return {
      num,
      person,
      student
    }
  }
}
</script>

<style scoped>

</style>

```
## 8.20 toRaw与markRaw
* toRaw:
    * 作用：将一个由reactive生成的响应式对象转换为普通对象，ref生成的不行（基本类型和对象类型都不行）。
    * 使用场景：用于读取响应式对象对应的普通对象，对这个普通对象的所有操作不会引起页面更新。
* markRaw:
    * 作用：标记一个对象，使其永远不会再成为响应式对象。
    * 应用场景：
        1. 有些值不应被设置为响应式的，例如复杂的第三方类库等。
        2. 当渲染具有不可变数据的大列表时，跳过响应式转换可以提高性能。
```vue
<template>
  <h2>num：{{num}}</h2>
  <button @click="num++">num加1 </button>
  <br/>
  <h2>学生名字：{{student}}</h2>
  <button @click="student.studentName += '名字' ">修改学生名字 </button>
  <button @click="student.age += 1 ">修改学生年龄</button>
  <br/>
  <h2>姓名：{{person.name}}</h2>
  <h2>年龄：{{person.age}}</h2>
  <h2>薪资：{{person.job.ji.salary}}K</h2>
  <h2>座驾：{{person.car}}</h2>
  <button @click="person.name += '改名'">修改姓名 </button>
  <button @click="person.age ++ ">增长年龄</button>
  <button @click="person.job.ji.salary ++">涨薪</button>
  <button @click="showRaw">展示原生对象</button>
  <br/>
  <button @click="addCar">添加一个座驾</button>
  <button @click="person.car.price++">修改座驾价格</button>

</template>

<script>
import { ref, reactive,toRaw,markRaw} from 'vue'
export default {
  name: "DemoDemo",
  setup(){
    /*可以正常修改，只会解析第一层*/
    let num = ref(0);
    /*单独修改不生效，但是模板重新解析的时候，页面会修改*/
    let student = ref({
      studentName:'学生',
      age:100
    });
    let person = reactive({
      name:'张三',
      age:18,
      job:{
        ji:{
          salary:20
        }
      }
    })
    function  showRaw(){
      //无法修改ref的对象为原生对象
      console.log(toRaw(num))
      //无法修改ref的对象为原生对象
      console.log(toRaw(student))
      //可以把reacitve定义的对象修改为原始对象
      console.log(toRaw(person))
    }
    function addCar(){
      const car = {
        name:'奔驰',
        price:'40'
      }
      //直接追加属性是响应式的
      /*person.car = car;*/

      //标记car属性是不能修改的
      person.car = markRaw(car);
    }
    return {
      num,
      person,
      student,
      showRaw,
      addCar
    }
  }
}
</script>

<style scoped>

</style>
```

## 8.21 customRef自定义ref

* 作用：创建一个自定义的ref，并对其以来项跟踪和更新触发进行显示控制。
  
  * 格式：
    
    ```js
    //引入customRef
    import { customRef} from 'vue'
    //定义自己的ref函数
    function myRef(value){
      //track时追踪，trigger触发
      return customRef((track, trigger) => {
      return {
         get() {
         track()//追踪，需要开启后数据才会更新
         return value//追踪，更新后的数据
      },
      set(newValue) {
         value = newValue//修改值
         trigger()//触发，通知vue去重新解析模板
      }
      }
      })
    }
    //使用
    let keyWord = myRef('helloworld')
    ```
    
    demo
    
    ```vue
    <template>
    <input type="text" v-model="keyWord" />
    ```
  
  <h2>{{keyWord}}</h2>
  </template>

<script>
import { customRef} from 'vue'
export default {
  name: "DemoDemo",
  setup(){
    //数据防抖
    //自定义一个myRef的ref函数
    function myRef(value, delay = 3000) {
      let timeout//定时器
      //返回自定义customRef函数，track时追踪，trigger触发
      return customRef((track, trigger) => {
        return {
          get() {
            track()//追踪，需要开启后数据才会更新
            return value//追踪，更新后的数据
          },
          set(newValue) {
            clearTimeout(timeout)//如果有定时，则关闭定时器
            timeout = setTimeout(() => {
              value = newValue//修改值
              trigger()//触发，通知vue去重新解析模板
            }, delay)
          }
        }
      })
    }

    let keyWord = myRef('helloworld')
    return{
      keyWord
    }
  }
}
</script>

<style scoped>

</style>

```

```
