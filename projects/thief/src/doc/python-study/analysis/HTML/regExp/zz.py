import re
"""goods_match = re.search(r'g_page_config = (.*?)}};', response.text) 简洁写法
"""
str1 = '<p><div><span>猪</span></div></p>'

# 子模式\1 + 单字符\w + 数量修饰符+
# r 不转义后面所有字符
pattern = re.compile(r'<(\w+)><(\w+)>\w+</\2></\1>')

ret = pattern.search(str1)
# print(ret)

# 贪婪模式
str2 = '<span>猪</p></div></p>'
pattern2 = re.compile(r'<\w+>.*</p>')
ret2 = pattern2.search(str2)
print(ret2)
# 不贪婪 既匹配到一次就终止
pattern3 = re.compile(r'<\w+>.*?</p>')
ret3 =pattern3.search(str2)
# print(ret3)

# 单行匹配 多行匹配
str3 = '''<div>call me baby
oh yea!
oh hei</div>'''
pattern4 = re.compile(r'oh')
ret4 = pattern4.search(str3)
print(ret4)
# ret5 = pattern4.findall(str3)
# print(ret5)
# p5 = re.compile(r'<div>.*</div>',re.S) # 单行模式 .可以匹配换行
# ret6 = p5.search(str3)
# print(ret6)

# sub 函数
str4 = 'you fool'
p6 = re.compile('fool')
ret7 = re.sub(p6,'smart',str4)
ret9 = re.sub(r'you','I',ret7)
ret8 = p6.sub('smart',str4)
print(ret9)
print(type(ret9),'type')
# ()匹配取值
string = '<div>love forever</div>'
print(re.compile(r'<div>(.*?)</div>').findall(string))
# 得到love forever 不加括号得到<div>love forever</div>

str1 = """steam正版怪物猎人 Monster Hunter
￥
120
2人想要
陕西
"""
print(str1)
print(re.search(r'￥', str1))
p = re.compile(r'(.*?)\n￥',re.S)
print(p.findall(str1))
p2 = re.compile(r'￥\n(.*?)\n',re.S)
print(p2.findall(str1))