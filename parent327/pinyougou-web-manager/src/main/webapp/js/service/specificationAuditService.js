//服务层
app.service('specificationAuditService',function($http){

    //分页
    this.findPage=function(page,rows){
        return $http.get('../specificationAudit/findPage.do?page='+page+'&rows='+rows);
    }
    // //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../specificationAudit/search.do?page='+page+"&rows="+rows,searchEntity);
    }

    this.updateStatus = function(ids,status){
        return $http.get('../specificationAudit/updateStatus.do?ids='+ids+"&status="+status);
    }
});
