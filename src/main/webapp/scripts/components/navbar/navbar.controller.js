'use strict';

angular.module('pidtpApp')
    .controller('NavbarController', function ($scope, $http, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        $scope.calcularDados = function(){
            $http.get("/ric/calculardados").success(function(response){
                console.log('Dados calculados com sucesso' + response.message);
            }).error(function(response){
                console.log('merda');
            })
        };
    });
