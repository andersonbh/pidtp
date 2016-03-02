'use strict';

angular.module('pidtpApp')
    .controller('RICController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        this.undo = function(){
            this.version--;
        };
        $scope.hexColor='';
        $scope.pushColor = function(){
            if(checkHex()){
                $scope.selectcolors.push($scope.hexColor);
                $scope.hexColor ='';
            }
            else{
                alert('Hex Color code is wrong !!')
            }

        };
        $scope.selectcolors= ['#000', '#00f', '#0f0', '#f00','#c1d82f'];

        function checkHex(){
            var re  = /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test($scope.hexColor) ;
            return re;
        }
    });
