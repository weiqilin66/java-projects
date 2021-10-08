from lxml import etree

# 根据class查找时候必须匹配所有class
# xpath数组下标从1开始

tree = etree.parse('../beautifulSoup/soup_test.html')
tree2 = etree.HTML('../beautifulSoup/soup_test.html')  # parse接收任意文档 html解析html更好

ret = tree.xpath('//div[1]/a')
# 返回对象是列表
print(ret[0].text)

# bookstore	选取 bookstore 元素的所有子节点。
# /bookstore    选取根元素 bookstore。
# bookstore/book	选取属于 bookstore 的子元素的所有 book 元素。
# //book	选取所有 book 子元素，而不管它们在文档中的位置。
# bookstore//book	选择属于 bookstore 元素的后代的所有 book 元素，而不管它们位于 bookstore 之下的什么位置。
# //@lang	选取名为 lang 的所有属性。

# /bookstore/book[1]	选取属于 bookstore 子元素的第一个 book 元素。
# /bookstore/book[last()]	选取属于 bookstore 子元素的最后一个 book 元素。
# /bookstore/book[last()-1]	选取属于 bookstore 子元素的倒数第二个 book 元素。
# /bookstore/book[position()<3]	选取最前面的两个属于 bookstore 元素的子元素的 book 元素。
# //title[@lang]	选取所有拥有名为 lang 的属性的 title 元素。
# //title[@lang='eng']	选取所有 title 元素，且这些元素拥有值为 eng 的 lang 属性。
# /bookstore/book[price>35.00]	选取 bookstore 元素的所有 book 元素，且其中的 price 元素的值须大于 35.00。
# /bookstore/book[price>35.00]/title	选取 bookstore 元素中的 book 元素的所有 title 元素，且其中的 price 元素的值须大于 35.00。

# *	匹配任何元素节点。
# @*	匹配任何属性节点。
# node()	匹配任何类型的节点。
# /bookstore/*	选取 bookstore 元素的所有子元素。
# //*	选取文档中的所有元素。
# //title[@*]	选取所有带有属性的 title 元素。
# .  当前节点

# 函数运算符
# contains(text(),'') 包含''内容  contains(@class,'l') class属性包含l
# starts-with(@class,"ba")
# /text() 显示文本   //text()标签下所有文本
# and or  !=  mod除
# string(.)可以用于提取标签嵌套标签的所有内容

# 额外的字符串函数
# replace(x,y) 替换字符串中的x为y
# strip('x y z') 删除字符串中包含的所有 x y z xyz xy zz...
