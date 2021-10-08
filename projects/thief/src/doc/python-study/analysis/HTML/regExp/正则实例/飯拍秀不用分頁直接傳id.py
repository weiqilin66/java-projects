import urllib.request
import urllib.parse

url = 'http://fanpai.net/plugin.php?id=chs_waterfall:threads'

post_data={
    'tids[]': 1000

}
post_data = urllib.parse.urlencode(post_data).encode()
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9'
    , 'Accept-Language': 'zh-CN,zh;q=0.9,en;q=0.8'
    ,

}
request = urllib.request.Request(url,headers=headers)
response = urllib.request.urlopen(request,data=post_data).read().decode('ISO-8859-15')
print(response)