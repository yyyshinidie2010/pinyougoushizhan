// 定义服务层:
app.service("seckillQueryService",function($http){

    this.findOne=function(id){
        return $http.get("../seckillQuery/findOne.do?id="+id);
    }
    this.search = function(page,rows,searchEntity){
        return $http.post("../seckillQuery/search.do?pageNum="+page+"&pageSize="+rows,searchEntity);
    }
});