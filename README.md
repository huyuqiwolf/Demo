# Demo
该仓库代码都是一些解决实际问题的方案

#com.ken.demo 脚本启动执行任务序列

##问题：
    程序除了正常的启动执行外，可以使用脚本调起程序，执行一些任务
    这些任务可能是一个任务集合，序列

##使用的文件
    app/src/main/res/xml/task.xml文件，抽象出来的任务描述

##使用的脚本
    adb push task.xml /sdcard/
    adb shell am start -n com.ken.demo/.MainActivity --ez from true

##task.xml的dtd约束
    <!DOCTYPE tasks [
        <!ELEMENT tasks (task+)>
        <!ELEMENT task (name,index,testCount)>
        <!ELEMENT name (#PCDATA)>
        <!ELEMENT index (#PCDATA)>
        <!ELEMENT testCount (#PCDATA)>
        ]>
