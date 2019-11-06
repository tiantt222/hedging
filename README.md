# 说明 #

hedging 菠菜对冲提醒


引入阿里私有仓库
手动配置修改Settings.xml，在servers节点添加如下配置
<servers>
    <server>
        <id>rdc-releases</id>
        <username>X1peMo</username>
        <password>BmFUL8f4tf</password>
    </server>
    <server>
        <id>rdc-snapshots</id>
        <username>X1peMo</username>
        <password>BmFUL8f4tf</password>
    </server>
</servers>



## 软件要求 ##

* JDK 1.8+
* MySQL 5.7


## 环境说明 ##

在工程的src/main/resources/目录下，分别配置了不同环境的配置文件

*   dev: 开发

*   integ: 集成测试

*   qa: 准生产

*   prod: 生产

##  服务器运行  ##
用maven生成jar包

命令行中运行 
* 测试环境
 # nohup java -jar hedging-0.0.1-SNAPSHOT.jar --spring.profiles.active=integ > /dev/null &
* 生产环境
 # nohup java -jar hedging-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > /dev/null &
