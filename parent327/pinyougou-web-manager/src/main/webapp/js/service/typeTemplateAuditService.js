//服务层
app.service('type_templateAuditService',function($http){

    //分页
    this.findPage=function(page,rows){
        return $http.get('../typeTemplateAudit/findPage.do?page='+page+'&rows='+rows);
    }
    // //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../typeTemplateAudit/search.do?page='+page+"&rows="+rows,searchEntity);
    }

    this.updateStatus = function(ids,status){
        return $http.get('../typeTemplateAudit/updateStatus.do?ids='+ids+"&status="+status);
    }
});
