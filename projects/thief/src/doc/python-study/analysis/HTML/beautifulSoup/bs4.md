###将html文档转换为指定对象 类似dom对象 通过对象方法属性查找对应内容
    1. 转化本地文件
        soup = BeautifulSoup(open('本地文件'),'lxml')
    2. 转化网络文件
        soup = BeautifulSoup('字符串/字节类型','lxml')
    3. python3 存在的版本问题中使用如下写法
        soup = BeautifulSoup(open('soup_test.html',encoding='utf8'),'html.parser')
####用法
    1. 根据标签名查找
    2. 获取属性
    3. 获取内容
    4. find 
    5. findall
    6. select选择器
####选择器
    1. 标签选择器
    2. 类选择器
    3. id选择器
    4. 组合选择器
    5. 层级选择器
    6. 伪类选择器
    7. 属性选择器
     
        