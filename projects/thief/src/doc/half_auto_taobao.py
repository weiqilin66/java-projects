from selenium import webdriver
import sched
import time
import tkinter.messagebox  # 弹窗库
from tkinter import *
from playsound import playsound
import re
import json
import pymysql
import random
from selenium import common
import openpyxl

""" 半自动实现 """


def start(random_good):
    chrome_path = r'd:/driverAndPlugs/chromedriver.exe'
    mp3_path = r'd:/菊花台.mp3'
    options = webdriver.ChromeOptions()
    # 不加载图片
    options.add_experimental_option("prefs", {"profile.managed_default_content_settings.images": 2})
    # 设置为开发者模式，防止被各大网站识别出来使用了Selenium  window.navigator.webdriver  检测
    options.add_experimental_option('excludeSwitches', ['enable-automation'])
    chrome = webdriver.Chrome(executable_path=chrome_path, options=options)
    url = 'https://s.taobao.com/'
    chrome.get(url)
    # chrome.maximize_window()  # 窗口最大化方便扫码
    ser_input = chrome.find_elements_by_xpath("//input[@name='q']")[0]
    ser_input.send_keys(random_good)
    time.sleep(1)
    ser_btn = chrome.find_elements_by_xpath("//button")[0]
    ser_btn.click()
    return chrome


# 解析json数据
def json2info(json_):
    json_dic = json.loads(json_)
    try:
        good_items = json_dic['mods']['itemlist']['data']['auctions']
    except:
        print('筛选条件过多,搜索不到该商品!')
        return 1

    good_list = []
    for good_item in good_items:
        goods = {
            'shop': good_item['nick'],  # 店铺名
            'title': good_item['raw_title'],  # 宝贝标题
            'price': good_item['view_price'],  # 价格
            'sales': good_item['view_sales'],  # 销量
            'freight': good_item['view_fee'],  # 运费
            'detail_url': good_item['detail_url'],  # 详情页
            'pic_url': good_item['pic_url']
        }
        good_list.append(goods)
    return good_list


# 搜索爬取数据
def data_by_search(conn, cursor, etl_date, etl_time, chrome, search_good, crawl_type, pages):
    try:
        tb_input = chrome.find_elements_by_xpath("//input[@name='q']")[0]
        tb_btn = chrome.find_elements_by_xpath("//button")[0]
        tb_input.clear()
        tb_input.send_keys(search_good)
        tb_btn.click()
        time.sleep(random.randint(10, 15))
        # 排序按钮     综合排序/销量/信用/价格从低到高/价格从高到低/总价从低到高/总价从高到低
        sort_btn = chrome.find_elements_by_xpath("//li/a[@data-key='sort']")
        # 销量排序
        if crawl_type == 1:  # 下一页不用再按销量排行和搜索
            sort_btn[1].click()
            time.sleep(random.randint(10, 15))
            # 价格100+
            # chrome.find_elements_by_xpath("//input[@class='J_SortbarPriceInput input']")[0].send_keys(100)
            # time.sleep(1)
            # chrome.find_elements_by_xpath("//button")[1].click()
            # time.sleep(random.randint(10, 15))
    except common.exceptions.WebDriverException as e:
        print('--webdriver异常: ', e)
        # playsound(mp3_path)
        tkinter.messagebox.showinfo('tip', 'webdriver异常')
        # print("关闭弹窗休息200s")
        # time.sleep(200)
        tb_input = chrome.find_elements_by_xpath("//input[@name='q']")[0]
        tb_btn = chrome.find_elements_by_xpath("//button")[0]
        tb_input.clear()
        tb_input.send_keys(search_good)
        tb_btn.click()
        time.sleep(random.randint(10, 15))
        sort_btn = chrome.find_elements_by_xpath("//li/a[@data-key='sort']")
        if crawl_type == 1:
            sort_btn[1].click()
            time.sleep(random.randint(10, 15))

    # 下拉到底部
    chrome.execute_script('window.scrollTo(0, document.body.scrollHeight)')
    time.sleep(2)
    # 拉取html
    html = chrome.page_source
    json_ = re.findall(r'g_page_config = (.*?)}};', html)[0]
    if json_ == '':
        print('未搜索到: ', search_good)
        return
    json_ = json_ + '}}'
    # 分析json 写入数据库
    print("crawl: %s" % search_good)
    good_list = json2info(json_)
    if good_list==1:
        return
    print('首页size: ', len(good_list))
    info2mysql(good_list, conn, cursor, etl_date, etl_time, search_good)
    # 下一页
    if pages == 1:
        return

    for page in range(0, pages - 1):
        # 下一页按钮不可用 返回
        totalPage = chrome.find_elements_by_xpath("//div[@class='pager']//ul[@class='items']//li/a[@class='link']/span[@class='icon icon-btn-next-2-disable']")  # 只有一页
        if len(totalPage) > 0:
            return
        try:
            next_btn = chrome.find_element_by_xpath('//li[@class="item next"]//a')
            next_btn.click()
            time.sleep(random.randint(10, 15))
        except common.exceptions.WebDriverException as e:
            print('--webdriver异常: ', e)
            # playsound(mp3_path)
            tkinter.messagebox.showinfo('tip', 'webdriver异常')
            # print("关闭弹窗休息200s")
            # time.sleep(200)
            next_btn = chrome.find_element_by_xpath('//li[@class="item next"]//a')  # 窗口长度不能缩小变短 加载不了下一页js
            next_btn.click()
            time.sleep(random.randint(10, 15))

        # 下拉到底部
        chrome.execute_script('window.scrollTo(0, document.body.scrollHeight)')
        # 拉取html
        html = chrome.page_source
        json_ = re.findall(r'g_page_config = (.*?)}};', html)[0]
        if json_ == '':
            print('翻页未搜索到: ', search_good)
            return
        json_ = json_ + '}}'
        # 分析json
        good_list = json2info(json_)
        print('第',page+2,'页size: ', len(good_list))
    # 写入数据库
        info2mysql(good_list, conn, cursor, etl_date, etl_time, search_good)


# 插入数据库1
def info2mysql(good_list, conn, cursor, etl_date, etl_time, kw):
    # 一个good_list是一个网页数据 统一设置一个时间

    for good in good_list:
        shop = good['shop']  # 店铺名
        title = good['title']  # 宝贝标题
        price = float(good['price'])  # 价格
        if '万' in good['sales']:
            sales = float(good['sales'].strip('+万人付款收货')) * 10000  # 销量
        else:
            sales = int(good['sales'].strip('+人付款收货'))  # 销量
        if good['freight'] == '':  # 运费
            freight = 0
        else:
            freight = float(good['freight'])

        detail_url = good['detail_url']  # 详情页
        pic_url = good['pic_url']

        # 不录入价格小于100 大于800的数据的数据
        # if price<100 or price>800:
        #     continue
        # 这部分商品低价或者高价销量高是有研究价值的
        # sql语句
        del_sql = """
            delete from goods where title = %s and etl_date = %s and shop =%s
        """
        cursor.execute(del_sql, (title, etl_date, shop))
        conn.commit()
        insert_sql = """
        insert into goods(shop,title,price,sales,freight,etl_date,etl_time,kw,detail_url,img_url) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
        """
        # 执行插入数据到数据库操作
        cursor.execute(insert_sql, (shop, title, price, sales, freight, etl_date, etl_time, kw, detail_url, pic_url))
        # 提交，不进行提交无法保存到数据库
        conn.commit()


# 写入数据库2
def item2mysql(cursor, conn, item, good, etl_date, etl_time):
    insert_sql = """
        insert into goods_shop(shop,price,title,sales,detail_url,img_url,kw,etl_date,etl_time) values (%s,%s,%s,%s,%s,%s,%s,%s,%s)
    """
    cursor.execute(insert_sql, (
        item['shop'], item['price'], item['title'], item['sales'], item['detail_url'], item['pic_url'], good, etl_date,
        etl_time))
    conn.commit()


#     爬取店铺所有
def all2mysql(cursor, conn, item, etl_date, etl_time):
    insert_sql = """
        insert into goods_shop(shop,price,title,sales,detail_url,img_url,etl_date,etl_time) values (%s,%s,%s,%s,%s,%s,%s,%s)
    """
    cursor.execute(insert_sql, (
        item['shop'], item['price'], item['title'], item['sales'], item['detail_url'], item['pic_url'], etl_date,
        etl_time))
    conn.commit()


def login(chrome):
    if chrome.current_url.startswith('https://login'):
        # tkinter.messagebox.showerror('提示', '请登录')
        print('未登录...')
        time.sleep(10)
        login(chrome)


# 查询店铺
def sel_shops(cursor):
    # 数据库查询店铺及地址
    sel_sql = """
        select * from shop where enable = 1
    """
    cursor.execute(sel_sql)
    shop_list = cursor.fetchall()
    return shop_list


# 根据店铺查询指定宝贝
def data_by_shop(chrome, good, shop):
    try:
        shop_input = chrome.find_elements_by_xpath("//input[@name='q']")[0]
        shop_btn = chrome.find_elements_by_xpath("//button[2]")[0]
        shop_input.send_keys(good)
        shop_btn.click()
    except common.exceptions.WebDriverException as e:
        print('--webdriver异常: ', e)
        tkinter.messagebox.showinfo('tip', 'webdriver异常')
        # 手动关闭滑块
        shop_input = chrome.find_elements_by_xpath("//input[@name='q']")[0]
        shop_btn = chrome.find_elements_by_xpath("//button[2]")[0]
        shop_input.send_keys(good)
        shop_btn.click()
    try:
        item = {
            'shop': shop[1],
            'price':
                chrome.find_elements_by_xpath("//div[@class='shop-hesper-bd grid']//div[@class='cprice-area']/span")[
                    0].text,
            'title':
                chrome.find_elements_by_xpath("//div[@class='shop-hesper-bd grid']//a[@class='item-name J_TGoldData']")[
                    0].text,
            'sales': chrome.find_elements_by_xpath("//div[@class='shop-hesper-bd grid']//div[@class='sale-area']/span")[
                0].text,
            'detail_url':
                chrome.find_elements_by_xpath("//div[@class='shop-hesper-bd grid']//a[@class='item-name J_TGoldData']")[
                    0].get_attribute('href'),
            'pic_url': chrome.find_elements_by_xpath("//div[@class='shop-hesper-bd grid']//dt[@class='photo']//img")[
                0].get_attribute('src')
        }
        print('  %s' % good)
    except:
        print('未获取到: %s' % good)
        item = {
            'shop': shop[1],
            'price': 0,
            'title': '无此商品',
            'sales': 0,
            'detail_url': 'null',
            'pic_url': 'null'
        }
    return item


def read_goods_by_excel():
    # 读取excel中的数据
    # 第一步：打开工作簿
    wb = openpyxl.load_workbook('D:\workSpaceXD\MyTarget\goods.xlsx')
    # 第二步：选取表单
    sh = wb['Sheet1']
    # 第三步：读取数据
    # 参数 row:行  column：列
    ce = sh.cell(row=1, column=1)  # 读取第一行，第一列的数据
    # print(ce.value)
    # 按行读取数据 list(sh.rows)
    # print(list(sh.rows)[1:])
    # 按行读取数据，去掉第一行的表头信息数据
    switch_list = []
    ps4_list = []
    search_goods = []
    for cases in list(sh.rows)[1:]:
        if cases[0].value is not None:
            case_switch = 'switch ' + cases[0].value
            switch_list.append(case_switch)
            search_goods.append(case_switch)

        if cases[2].value is not None:
            case_ps4 = 'ps4 ' + cases[2].value
            ps4_list.append(case_ps4)
            search_goods.append(case_ps4)

    # 关闭工作薄
    wb.close()
    return search_goods


# 爬取店铺所有商品
def crawl_all_f_shop(chrome, shop, cursor, conn, etl_date, etl_time):
    totalPage = chrome.find_elements_by_xpath("//span[@class='page-info']")
    if len(totalPage) == 0:
        time.sleep(10)
        return
    total = totalPage[0].text
    total = int(total.split('/')[1])
    error_str = []
    for page in range(1, total + 1):
        try:
            dl_list = chrome.find_elements_by_xpath("//dl//img")  # 多少张图就有多少宝贝 dl与宝贝数不一致
            if len(dl_list) == 0:
                print('网速较慢', 'slow--' * 10)
                chrome.refresh()
                time.sleep(random.randint(10, 15))
                dl_list = chrome.find_elements_by_xpath("//dl//img")
        except common.exceptions.WebDriverException as e:
            print('--webdriver异常: ', e)
            # playsound(mp3_path)
            tkinter.messagebox.showinfo('tip', 'webdriver异常')
            print("关闭弹窗休息200s")
            time.sleep(200)
            dl_list = chrome.find_elements_by_xpath("//dl//img")
        # 手动关闭滑块
        # ------------------------------------------------------------------------
        price_list = chrome.find_elements_by_xpath("//dl//span[@class='c-price']")
        title_list = chrome.find_elements_by_xpath("//dl//img")
        sales_list = chrome.find_elements_by_xpath("//dl//span[@class='sale-num']")
        detail_url = chrome.find_elements_by_xpath("//dl//a[@class='item-name J_TGoldData']")

        for i in range(0, len(dl_list) - 1):
            title = title_list[i].get_attribute('alt')
            ret = re.sub(r'<span class=H>', '', title)
            ret2 = re.sub(r'</span>', '', ret)
            if len(sales_list) == 0:
                good = {
                    'shop': shop[1],
                    'price': price_list[i].text,
                    'title': ret2,
                    'sales': 99999,
                    'detail_url': detail_url[i].get_attribute('href'),
                    'pic_url': title_list[i].get_attribute('src')
                }
            else:
                good = {
                    'shop': shop[1],
                    'price': price_list[i].text,
                    'title': ret2,
                    'sales': sales_list[i].text,
                    'detail_url': detail_url[i].get_attribute('href'),
                    'pic_url': title_list[i].get_attribute('src')
                }
            print(good)
            all2mysql(cursor, conn, good, etl_date, etl_time)
        print('--------爬取第%s' % page, '页结束---------')
        time.sleep(random.randint(10, 15))
        if page != total:
            try:
                next_btn = chrome.find_elements_by_xpath("//a[@class='J_SearchAsync next']")[0]
                next_btn.click()
            except common.exceptions.WebDriverException as e:
                print('--webdriver异常: ', e)
                # playsound(mp3_path)
                tkinter.messagebox.showinfo('tip', 'webdriver异常')  # 手动滑块
                print("关闭弹窗休息200s")
                time.sleep(200)
                next_btn = chrome.find_elements_by_xpath("//a[@class='J_SearchAsync next']")[0]
                next_btn.click()
            time.sleep(5)
    return error_str


def delete_data(cursor, etl_date, shop):
    sel_sql = """
        delete from goods_shop where etl_date = %s and shop = %s
    """
    cursor.execute(sel_sql, (etl_date, shop[1]))


def updateShop(cursor, conn, shop):
    sql = """
        update shop set enable = 0 where name = %s
    """
    cursor.execute(sql, shop[1])
    conn.commit()


def UpdateAllShop(cursor, conn):
    sql = """
        update shop set enable = 1 
    """
    cursor.execute(sql)
    conn.commit()


def read_goods_by_sql(conn, cursor):
    sel_sql = """
        select name from tb_search where enabled =1
    """
    cursor.execute(sel_sql)
    shop_list = cursor.fetchall()
    return shop_list


def enabled_goods(conn, cursor, shopName):
    update_eql = """
        update tb_search set enabled = 0 where name = %s
    """
    cursor.execute(update_eql, shopName)
    conn.commit()


def enabled_goods_one(conn, cursor):
    update_eql = """
        update tb_search set enabled = 1 
    """
    cursor.execute(update_eql)
    conn.commit()


def main():
    # 链接mysql
    conn = pymysql.connect('localhost', 'root', 'root', 'vhr')
    # 创建游标
    cursor = conn.cursor()
    search_goods = read_goods_by_sql(conn, cursor)
    # search_goods = ["香蕉", "苹果", "梨", "葡萄"]
    # print('爬取列表: ', search_goods)
    random_good = random.randint(0, len(search_goods) - 1)
    chrome = start(search_goods[random_good])
    # 15s手动扫码
    time.sleep(15)
    login(chrome)
    print('登录成功:', chrome.current_url)

    # 数据日期
    etl_date = time.strftime("%Y%m%d", time.localtime())
    etl_time = time.strftime("%H:%M:%S", time.localtime())
    print('-----  销量排行  -----')
    count = 0
    for search_good in search_goods:
        pages = 3
        if count > 40:  # 后续宝贝热度低
            pages = 2
        data_by_search(conn, cursor, etl_date, etl_time, chrome, search_good, 1, pages)
        count = count + 1
        enabled_goods(conn, cursor, search_good)
    enabled_goods_one(conn, cursor)

    # 爬取数据--代表店铺
    # print('-------------  代表店铺  -------------------')
    # shop_list = sel_shops(cursor)
    # print("猎杀名单:")
    # for s in shop_list:
    #     print(s[1])
    # for shop in shop_list:
    #     delete_data(cursor, etl_date,shop)
    #     switch_url = shop[2]
    #     ps4_url = shop[3]
    #     chrome.get(switch_url)
    #     time.sleep(10)
    #     print('########店铺:', shop[1], ' ###### switch #########')
    #     # 爬取switch商品
    #     crawl_all_f_shop(chrome, shop, cursor, conn, etl_date, etl_time)
    # 
    #     if ps4_url is not None:
    #         chrome.get(ps4_url)
    #         time.sleep(10)
    #         print('########店铺:', shop[1], ' ###### ps4 #########')
    #         # 爬取ps4商品
    #         crawl_all_f_shop(chrome, shop, cursor, conn, etl_date, etl_time)
    #     # 该店铺爬取完 enable设置为 0 当天不用重复爬取了
    #     updateShop(cursor, conn, shop)
    #     print(shop[1], "--猎杀完毕--休息下")
    #     time.sleep(random.randint(90, 150))
    # # 全部爬取完 enable设置为 1
    # UpdateAllShop(cursor, conn)
    chrome.close()
    # 关闭游标和连接
    cursor.close()
    conn.close()


if __name__ == '__main__':
    scheduler = sched.scheduler(time.time, time.sleep)
    # 增加调度任务 初始
    scheduler.enter(1, 1, main)
    # 增加调度任务 240-360分钟
    # scheduler.enter(random.randint(240,360)*60, 1, main)
    # scheduler.enter(random.randint(240,360)*60, 1, main)
    # scheduler.enter(random.randint(240,360)*60, 1, main)

    # 所有调度任务 暂定一天调度4次
    # 隐藏tk主窗口
    tkinter.Tk().withdraw()

    for i in scheduler.queue:
        # tkinter.messagebox.showinfo('tip', '即将开始爬取数据')
        # 运行任务
        scheduler.run()

    print('-' * 30)
    print('爬取结束')
