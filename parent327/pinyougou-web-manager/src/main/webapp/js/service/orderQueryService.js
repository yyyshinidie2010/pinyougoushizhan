// 定义服务层:
app.service("orderQueryService",function($http){

    this.findOne=function(orderId){
        return $http.get("../orderQuery/findOne.do?id="+orderId);
    }
    this.search = function(page,rows,searchEntity){
        return $http.post("../orderQuery/search.do?pageNum="+page+"&pageSize="+rows,searchEntity);
    }
});