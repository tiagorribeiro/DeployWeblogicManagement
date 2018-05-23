
var deps = ['ui-notification'];
var app = angular.module('app', deps)
	.config(function(NotificationProvider) {
		NotificationProvider.setOptions({
			delay: 5000,
			startTop: 100,
			startRight: 10,
			verticalSpacing: 10,
			horizontalSpacing: 10,
			positionX: 'right',
			positionY: 'top'
	})});

app.controller('formPathController', function($scope, $http, $location, Notification){
	$scope.submitForm = function(){
		var url = $location.absUrl() + "post-paths";
		
		var config = {
                headers : {
                    'Accept': 'text/plain'
                }
        }
		var data = {
			weblogicPath: $scope.weblogicPath,
			gitPath: $scope.gitPath
        };
		
		$http.post(url, data, config).then(function (response) {
			$scope.postResultMessage = response.data;
			Notification.success("PATHs salvo com sucesso");
		}, function error(response) {
			$scope.postResultMessage = "Error with status: " +  response.statusText;
		});
	}
});

app.controller('formListController', function($scope, $http, $location, Notification) {
	
	$scope.wars = [];
	
	$scope.getfunction = function(){
		
		var url = $location.absUrl() + "get-wars";
		
		$http.get(url).then(function (response) {
			$scope.wars = response.data;
			Notification.success("WARs Listados com sucesso");
		},
		function error(response) {
			$scope.postResultMessage = "Error with status: " +  response.statusText;
		});
	}

	$scope.submitForm = function(){

		var url = $location.absUrl() + "post-wars-deploy";

		var config = {
			headers : {
				'Accept': 'text/plain'
			}
		}

		$http.post(url, $scope.wars, config).then(function (response) {
			Notification.success("Deploy realizado com sucesso, aguarde a inicialização do WEBLOGIC");
		}, function error(response) {
			$scope.postResultMessage = "Error with status: " +  response.statusText;
		});
	}
});