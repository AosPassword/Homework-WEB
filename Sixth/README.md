代码实现了利用xml配置文件来获取数据库名 数据库端口 用户名密码等等

配置文件config.xml附在项目里。



代码需要以下jar包：

Dom4j：

https://mvnrepository.com/artifact/dom4j/dom4j/1.6.1



另外，Mysql表结构如下：

CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` varchar(12) NOT NULL,
  `path` varchar(255) NOT NULL,
  `last_modified` timestamp NULL DEFAULT NULL,
  `can_execute` varchar(3) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9681 DEFAULT CHARSET=utf8;



