## 4.1Vue发送ajax请求

发送ajax请求几种方式：

- xhr原生方式，写起来比较复杂
- jquery大部分时操作dom代码，发送ajax代码占比小，封装的xhr
- axios专门为ajax设计,封装的xhr
- fetch有些浏览器不支持与xhr时同一级别的

1、安装axios:  npm i axios

2、引入：import axios from 'axios'

4、使用 axios.get(url).then{response=>{
                  console.log("请求成功",response.data)
                }, error=>{
                  console.log("请求失败",error.message)
                }}

```
<script>
    import axios from 'axios'
    export default {
        methods:{
          sendBaidu(){
            axios.get("https://www.baidu.com").then(
                response=>{
                  console.log("请求成功",response.data)
                },
                error=>{
                  console.log("请求失败",error.message)
                }
            )
          }
        }
      }
</script>
```

## 4.2Vue脚手架配置代理

方式一 在vue.config.js添加devServer配置项，

```
module.exports = {
  devServer:{
    proxy:"http://localhost:8089"
  }
}
```

优点：配置简单，请求资源主页面找不到就转发到后台服务器，优先匹配前端服务器。

缺点：不能配置多个代理服务器。

方式二 在vue.config.js添加devServer配置项，

```vue
module.exports = {
  devServer:{
    proxy: {
      '/path1':{/*请求路径*/
          target:"http://localhost:8089",/*转发地址*/
          changeOrigin:true,/*是否隐藏被代理地址*/
          pathRewrite:{/*路径替换*/
            '/path1':''
          }
      },
      'path2':{
        target:"http://localhost:8090",
        changeOrigin:true,
        pathRewrite:{
          '/path1':''
        }
      }
    }
  }
}
```

## 4.3Vue脚手架引入外部css注意事项

如果是放在项目的assets目录中，通过import引入，会严格校验里面内容，比如字体，如果找不到会编译报错。

可以放在public目录中，通过index.html的link baseulr来引入

## 4.4Vue对象COPY赋值

this.info = {...this.info,...newObject}表示以后面newObject为主，如果newObject中没有info的某个属性，那就直接取info的属性和值给newObject。

## 4.5vue-resource

vue-resource官网的ajax请求库，现在已经交出去了，不推荐使用了。Vue 1.0使用的比较多，是Vue的一个插件库。

1、安装vue-resourece：npm i vue-resource

2、main.js引入vue-resource：import vueResource from 'vue-resource'

3、main.js加载vue-resource插件：Vue.use(vueResource);

4、使用 this.$http.get(url).then{response=>{
                  console.log("请求成功",response.data)
                }, error=>{
                  console.log("请求失败",error.message)
                }}

Demo

```vue
<template>
    <div>
        <button   @click="sendPost()">发送请求</button>
    </div>
</template>

<script>
    export default {
        methods:{
          sendPost(){
            this.$http.get("http://localhost:8081/account/queryAsset").then(
                response=>{
                  console.log("请求成功",response.data)
                },
                error=>{
                  console.log("请求失败",error.message)
                }
            )
          }
        }
      }
</script>

<style scoped>
h2{
  background-color: coral;
}
</style>
```

## 4.6插槽

### 4.6.1插槽作用

作用：让父组件可以向子组件指定<font color='red'>位置插入html结构</font>（父组件引入子组件时，在引入的子组件标签内写的html内容），也是一种组件通信的方式，适用于父组件===>子组件。

### 4.6.2分类

默认插槽、具名插槽、作用域插槽

### 4.6.3使用方式

1、默认插槽

```Vue
<!--默认插槽-->
<template>
    <div>
        <h2>{{title}}</h2>
        <slot>插槽默认值1</slot>
        <slot>插槽默认值2</slot>
    </div>
</template>

<script>
    export default {
        name: "AcquiesceSlot",
        props:['title']
    }
</script>

<style scoped>
    div{
        background: skyblue;
        width: 200px;
        height: 300px;
    }
    h2{
        background: orange;
        text-align: center;
    }
</style>
```

2、具名插槽

```vue
<!--具名插槽-->
<template>
    <div>
        <h2>{{title}}</h2>
        <slot name="top">具名插槽1</slot>
        <slot name="footer">
            <ul>
                <li>具名插槽2</li>
            </ul>
        </slot>
    </div>
</template>

<script>
    export default {
        name: "HasNameSlot",
        props:['title']
    }
</script>

<style scoped>
    div{
        background: skyblue;
        width: 200px;
        height: 300px;
    }
    h2{
        background: orange;
        text-align: center;
    }
</style>
```

3、作用域插槽

数据在组件的自身，但数据生成的机构需要组件的使用者决定，使用作用域插槽

```vue
<!--具名插槽-->
<template>
    <div>
        <h2>{{title}}</h2>
        <slot :hobys="hobys">作用域插槽</slot>
    </div>
</template>

<script>
    export default {
        name: "ScopeSlot",
        data(){
            return{
                title:'爱好',
                hobys:['吃饭','睡觉','抽烟','喝酒']
            }
        }

    }
</script>

<style scoped>
    div{
        background: skyblue;
        width: 200px;
        height: 300px;
    }
    h2{
        background: orange;
        text-align: center;
    }
</style>
```

Demo

```vue
<template>
  <div id="app">
    <div class="container">
      <Category title="食物" :list="foods" ></Category>
      <Category title="电影" :list="films" ></Category>
      <Category title="游戏" :list="games" ></Category>
    </div>

    <!--默认插槽-->
    <div class="container">
      <!--AcquiesceSlot组件中有几个默认插槽，就会放几次AcquiesceSlot组件中的内容过去-->
      <AcquiesceSlot title="食物">
        <img src="./assets/2.jpg"></img><!--插槽是在该页面解析后再插入组件的slot中，所以img标签的样式可以在这个组件写，也可以在slot标签组件中写-->
        <a href="#">更多图片</a>
      </AcquiesceSlot>
      <AcquiesceSlot title="电影"></AcquiesceSlot><!--如果没写内容，slot中展示标签内容，如果slot没内容则什么也不展示-->
      <AcquiesceSlot title="游戏">
        <ul>
          <li v-for="game of games">{{game}}</li>
        </ul>
      </AcquiesceSlot>
    </div>

    <div class="container">
      <HasNameSlot title="食物">
        <img slot="top" src="./assets/2.jpg"></img>
        <a slot="footer" href="#">更多图片</a>&nbsp;&nbsp;&nbsp;&nbsp;<!--空格在这里补生效，因为插槽是拿元素插入进去，空格在元素外面-->
        <a slot="footer" href="#">更多内容</a>
      </HasNameSlot>
      <HasNameSlot title="电影">
        <!--多个标签放入一个插槽，可以使用template，不会生成多余的dom元素-->
        <template slot="top">
          <a href="#">单机游戏</a>
          <a href="#">网络游戏</a>
        </template>
        <template v-slot:footer><!--vue2.6之后使用template插槽，可以使用v-slot:插槽名-->
          <ul >
            <li ><a href="#">动作游戏</a></li>
            <li ><a href="#">角色扮演</a></li>
            <li ><a href="#">枪击游戏</a></li>
          </ul>
        </template>
      </HasNameSlot>
      <HasNameSlot title="游戏">
        <ul slot="footer" >
          <li v-for="game of games">{{game}}</li>
        </ul>
      </HasNameSlot>
    </div>


    <div class="container">
      <ScopeSlot >
        <template scope="scopeData" >
          <ul >
            <li v-for="hoby in scopeData.hobys">{{hoby}}</li>
          </ul>
        </template>
      </ScopeSlot>
      <ScopeSlot >
        <template slot-scope="scopeData" >
          <ol >
            <li v-for="hoby in scopeData.hobys">{{hoby}}</li>
          </ol>
        </template>
      </ScopeSlot>
      <ScopeSlot title="游戏">
        <template scope="{hobys}" >
            <h4 v-for="hoby in hobys">{{hoby}}</h4>
        </template>
      </ScopeSlot>
    </div>
  </div>

</template>

<script>
import Category from './components/Category.vue';
import AcquiesceSlot from './components/AcquiesceSlot.vue';
import HasNameSlot from './components/HasNameSlot.vue';
import ScopeSlot from './components/ScopeSlot.vue';
export default {
  name: 'App',
  components: {
    Category,
    AcquiesceSlot,
    HasNameSlot,
    ScopeSlot
  },
  data(){
    return {
      foods:['火锅','啤酒','蛋糕','鸡腿'],
      films:['A计划','大话西游','功夫','少林足球'],
      games:['刀剑','笑傲江湖','刀塔','cs在线']
    }
  }
}
</script>

<style scoped>
  .container{
    display: flex;
    justify-content: space-around;
  }
  img{
    width: 100%;
  }
</style>
```

### 4.6.4注意事项

1、默认插槽组件中有几个，就会放几次内容组件中的插槽去

2、插槽是在该页面解析后再插入组件的slot中，所以img标签的样式可以在这个组件写，也可以在slot标签组件中写

3、如果没写内容，slot中展示标签内容，如果slot没内容则什么也不展示

4、空格在元素外不生效，因为插槽是拿元素插入进去。

5、多个标签放入一个插槽，可以使用template，不会生成多余的dom元素

6、vue2.6之后使用template插槽，可以使用v-slot:插槽名

7、作用域插槽也可以有name

8、作用域插槽必须使用template标签接收数据

9、作用域插槽使用template标签scope="所有数据的对象"或slot-scope接收(新版本的vue支持，应该是2.6之后的)，接收的是slot传过来的多个数据组合的数据。

10、scope可以使用scope="{传过来的数据名}"

11、父组件传给子组件的插槽，可以子组件的$slots中查看插槽具体内容。
