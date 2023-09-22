Elasticsearch

# 第1章 Elasticsearch概述

## 1.1 Elasticsearch是什么

Elasticsearch 是一个分布式，restful风格的搜索和数据分析引擎。

The Elastic Stack, 包括 Elasticsearch、Kibana、Beats 和 Logstash（也称为 ELK Stack）。

Elaticsearch，简称为ES， ES是一个开源的高扩展的分布式全文搜索引擎，是整个Elastic Stack技术栈的核心。它可以近乎实时的存储、检索数据；本身扩展性很好，可以扩展到上百台服务器，处理PB级别的数据。

The Elastic Stack包括 Elasticsearch、Kibana、Beats 和 Logstash（也称为 ELK Stack），Elasticsearch与Logstash的[数据收集](https://baike.baidu.com/item/数据收集/2245693?fromModule=lemma_inlink)和日志解析引擎以及名为Kibana的分析和可视化平台一起开发的。这三个产品被设计成一个集成解决方案，就叫ELK Stack。

## 1.2 全文搜索引擎

一般传统数据库，全文检索都实现的很鸡肋，因为一般也没人用数据库存文本字段。Google，百度类的网站搜索它们都是根据网页中的关键字生成索引，我们在搜索的时候输入关键字，它们会将该关键字即索引匹配到的所有网页返回。这些都是非结构化数据，对于关系型数据库效果不佳。

搜索引擎主要解决问题：

* 搜索的数据对象是大量的非结构化的文本数据。
* 文件记录量达到数十万或数百万个甚至更多。
* 支持大量基于交互式文本的查询。
* 需求非常灵活的全文搜索查询。
* 对高度相关的搜索结果的有特殊需求，但是没有可用的关系数据库可以满足
* 对不同记录类型、非文本数据操作或安全事务处理的需求相对较少的情况。

## 1.3 Elasticsearch And Solr

Lucene是Apache软件基金会Jakarta项目组的一个子项目，提供了一个简单却强大的应用程式接口，能够做全文索引和搜寻。但Lucene只是一个提供全文搜索功能类库的核心工具包，使用它还需要一个完善的服务框架搭建起来进行应用。

目前市面上流行的搜索引擎软件，主流的就两款：Elasticsearch和Solr,这两款都是基于Lucene搭建的。因为内核相同两者除了服务器安装、部署、管理、集群以外，对于数据的操作 修改、添加、保存、查询等等都十分类似。

两则都有优缺点，Elasticsearch趋于商业，Solr趋于开源。

## 1.4 Elasticsearch Or Solr

* Solr是最受欢迎的搜索引擎之一，拥有强大的社区和开源支持。
* Elasticsearch易于安装且非常轻巧，但是这个特点也容易导致管理不当出现问题。
* Solr更成熟，但ES增长迅速，更稳定。
* Solr是一个非常有据可查的产品，具有清晰的示例和API用例场景。Elasticsearch的文档组织良好，但它缺乏好的示例和清晰的配置说明。
* Solr拥有更大，更成熟的用户，开发者和贡献者社区。ES虽拥有的规模较小但活跃的 用户社区以及不断增长的贡献者社区。
* Elasticsearch有良好的可伸缩性和以及性能分布式
* Elasticsearch主要用于日志搜索

## 1.5 Elasticsearch引用案例

* GitHub:2013年初，抛弃了Solr，采 Elasticsearch来做PB级的搜索。
* 维基百科:启动以 Elasticsearch为基础的核心搜索架构。
* SoundCloud:使用Elasticsearch为1.8亿用户提供即时而精准的音乐搜索服务
* 百度:Elasticsearch作为文本数据分析，采集百度所有服务器上的各类指

# 第2章 Elasticsearch 入门

## 2.1 Elasticsearch安装

### 2.1.1 下载软件

Elasticsearch的官方地址： https://www.elastic.co/cn/

Elasticsearch下载地址：https://www.elastic.co/cn/downloads/past-releases#elasticsearch

Elasticsearch分为Linux和Windows版本。

### 2.1.2 安装

Windows版的Elasticsearch的安装很简单，解压即安装完毕，解压后的 Elasticsearch的目录结构如下

| 目录    | 含义           |
| ------- | -------------- |
| bin     | 可执行脚本目录 |
| config  | 配置目录       |
| jdk     | 内置JDK目录    |
| lib     | 类库           |
| logs    | 日志目录       |
| modules | 模块目录       |
| plugins | 插件目录       |

解压后，进入bin文件目录，点击elasticsearch.bat文件启动ES服务

9300端口为Elasticsearch集群间组件的通信端口，9200端口为浏览器访问的http协议RESTful端口。

访问http://localhost:9200，测试结果

```json
{
  "name" : "instance-0000000000",
  "cluster_name" : "56eebc56a01c473abbc97b33f58c6bdd",
  "cluster_uuid" : "QPf6VBFPS7OWNAlRG_ltFw",
  "version" : {
    "number" : "7.9.3",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "c4138e51121ef06a6404866cddc601906fe5c868",
    "build_date" : "2020-10-16T10:36:16.141335Z",
    "build_snapshot" : false,
    "lucene_version" : "8.6.2",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

### 2.1.3 常见问题提出

Elasticsearch是使用java开发的，且7.8版本的ES需要JDK版本1.8以上，默认安装包带有jdk环境，如果系统配置JAVA_HOME，那么使用系统默认默认的JDK，如果没有配置使用自带的JDK ，一般建议使用系统配置的JDK。

双击启动窗口闪退，通过路径访问追踪错误，如果是“空间不足”，请修改config/jvm.options配置文件

```
# 设置 JVM 初始内存为 1G 。此值可以设置与 Xmx 相同，以避免每次垃圾回收完成后 JVM 重新分配内存
# Xms represents the initial size of total heap space
# 设置 JVM 最大可用内存为 1G
# Xmx represents the maximum size of total heap space
-Xms1g
-Xmx1g
```

## 2.2 Elasticsearch 基本操作

### 2.2.1 RESTful

