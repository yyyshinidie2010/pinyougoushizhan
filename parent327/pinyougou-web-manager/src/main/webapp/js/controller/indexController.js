app.controller("indexController",function($scope,loginService){

	//显示当前登陆人
	$scope.showName = function(){
		loginService.showName().success(function(response){
			$scope.loginName = response.username;//Map
			$scope.time = response.curTime;
		});
	}
	//URL
	//入参:无
	//返回值:Map
	//请求 request  Cookie k:jsessionId V:32

    //后端 Tomcat 70+/保存用户 Session  每一个浏览器对应一个会话(Session)
	
});