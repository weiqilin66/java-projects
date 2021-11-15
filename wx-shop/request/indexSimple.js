
export const request = (params) => {
    return new Promise((resolve, reject) => {
        var reqTask = wx.request({
            ...params,
            success: (result) => {
                resolve(result);//传给resolve的数据，能在接下来的then方法中拿到
            },
            fail: (err) => {
                reject(err);//catch方法,作为reject的回调,then作为resolve成功返回的回调
            }
        });
    })
}

