import urllib.request
import urllib.parse
import re
import os
import time
import urllib.error
import random
from pathlib import Path

url = "http://fanpai.net/plugin.php?id=chs_waterfall:waterfall&page="
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9'
    , 'Accept-Language': 'zh-CN,zh;q=0.9,en;q=0.8'
    ,

}

# 发送请求下载图片
def downLoadImg(imgPath):
    for imgUrl in imgPath:
        # 创建文件夹
        dirName = '饭拍秀'
        if not os.path.exists(dirName):
            os.mkdir(dirName)
        # 图片处理
        fileName = imgUrl.split('/')[-1]
        filePath = dirName + '/' + str(random.randint(0,10000)) +fileName
        urllib.request.urlretrieve(imgUrl,filePath)

def mainImg(resp):
    p1 = re.compile(r'<ignore_js_op>.*?</ignore_js_op>', re.S)
    res1 = p1.findall(resp)
    p2 = re.compile(r'file=".*?"')
    res2 = p2.findall(str(res1))
    paths = re.compile(r'http.*?.jpg').findall(str(res2))
    return paths

def main():
    start = int(input("请输入起始页码："))
    end = int(input("请输入结束页码："))
    for page in range(start, end + 1):
        targetUrl = url + str(page) + '/'
        print('第%s 页开始下载'%page)
        request = urllib.request.Request(targetUrl, headers=headers)
        response = urllib.request.urlopen(request)
        try:
            # 默认utf-8 还可以设置GBK
            resp = response.read().decode('ISO-8859-15')
        except Exception as e:
            print(e)
        #print(resp)
        # 获取每个子网页链接
        # <div class="waterfall_cover" style="min-height:30px;">
        # <a href="forum.php?mod=viewthread&amp;tid=7597" target="_blank" style="display: block;max-height: 629px;overflow:hidden;"><img src="data/attachment/./waterfall/7a/9e/347Xauto/7597.jpg" width="347"></a>
        # </div>

        pattern = re.compile(r'<div class="waterfall_cover" style="min-height:30px;">.*?</div>',re.S)
        ret=pattern.findall(resp)
        ret2 = re.compile(r'tid=.+?"').findall(str(ret))
        ret3 = re.sub(r'tid=','http://fanpai.net/thread-',str(ret2))
        ret4 = re.sub(r'"','-1-1.html',ret3)
        ret4 = ret4[1:-1]
        arr = ret4.split(',')
        ret5 = re.compile("'http.*?\'").findall(str(arr))
        # print(ret5)
        for sonUrl in ret5:
            sonUrl = sonUrl[1:-1]
            print('sonUrl= '+ sonUrl)
            sonRequest = urllib.request.Request(sonUrl,headers = headers)
            sonResponse = urllib.request.urlopen(sonRequest).read().decode('ISO-8859-15')
            # print(sonResponse)
            #主图
            paths= mainImg(sonResponse)
            downLoadImg(paths)
        print('第%s页下载完成'%page)
        time.sleep(1)


if __name__ == '__main__':
    main()
