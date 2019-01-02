// 定义控制器:
app.controller("seckillQueryController",function($scope,$controller,seckillQueryService){
    // AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});


    // 查询一个:
    $scope.findById = function(id){
        seckillQueryService.findOne(id).success(function(response){
            // {id:xx,name:yy,firstChar:zz}
            $scope.entity = response;
        });
    }


    $scope.searchEntity={};

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        seckillQueryService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }

});
