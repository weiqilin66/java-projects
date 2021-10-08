from bs4 import BeautifulSoup

# 生成对象
soup = BeautifulSoup(open('soup_test.html', encoding='utf8'), 'html.parser')
# soup2 = BeautifulSoup(open('soup_test.html',encoding='utf-8'),'lxml')
print(soup)
print(type(soup))  # 对象
# 1. 根据标签名查找
print(soup.a)  # 找到第一个a标签
print(type(soup.a))  # 对象
# 2. 获取属性
print(soup.a['href'])
print(soup.a['title'])
print(soup.a.attrs)  # 所有属性
print(soup.a.attrs['href'])  # 第二种写法

# 3. 获取内容
print(soup.a.text)
print(soup.a.get_text())
print(soup.a.string)  # 只能获取本级标签内容

# 4. find 可配置条件  找到的都是第一个符合要求的
print(soup.find('a'))
print(soup.find('a', title='7'))
print(soup.find('a', class_='7'))  # class是关键字 得加下划线
# 5. findall  所有符合要求的

# 6. select选择器  返回列表list
# 1. 标签选择器
# a
print('------------------标签选择器----------------------------')
print(soup.select('a'))
# 2. 类选择器
# .className
print('------------------类选择器----------------------------')
print(soup.select('.song'))
# 3. id选择器
#  #id
print('------------------id选择器----------------------------')
print(soup.select('#秦'))
# 4. 组合选择器 ,逗号隔开
# a, .class , #id

# 5. 层级选择器
# div> p> a> .class  >明确的下一级  空格 下级
print('------------------层级选择器获取精确子层级----------------------------')
print(soup.select('.tang > ul > li >a')[1])
print(soup.select('.tang > ul > li >a')[1].text)     # 选择器配合获取属性和获取内容

#  div .duu #id2   很多级都选中
# 6. 伪类选择器
# 7. 属性选择器