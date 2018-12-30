//服务层
app.service('seckillService',function($http){

    //分页
    this.findPage=function(page,rows){
        return $http.get('../seckill/findPage.do?page='+page+'&rows='+rows);
    }
    // //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../seckill/search.do?page='+page+"&rows="+rows,searchEntity);
    }

    this.updateStatus = function(ids,status){
        return $http.get('../seckill/updateStatus.do?ids='+ids+"&status="+status);
    }
});
