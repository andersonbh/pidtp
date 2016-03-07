'use strict';

angular.module('pidtpApp')
    .controller('RICController', function ($scope, Principal, $state) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.desenhando = false;
        this.undo = function(){
            this.version--;
        };
        $scope.imagemselecionada = false;


        $scope.imagem = {
            width:  710,
            height : 600
        };

        $scope.hexColor='#000000';
        //Funcao para adicionar cor na lista de cores
        $scope.adicionarCor = function(){
            if(checarHex()){
                $scope.selectcolors.push($scope.hexColor)

                $scope.hexColor ='#000000';
            }
            else{
                alert('Código Hexadecimal não está no formato !!')
            }

        };
        //Cores inicias
        $scope.selectcolors= ['#000', '#00f', '#0f0', '#f00','#c1d82f'];
        function checarHex(){
            return /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test($scope.hexColor);
        }

        $scope.setFile = function(element) {
            $scope.currentFile = element.files[0];
            var reader = new FileReader();

            reader.onload = function(event) {
                $scope.$apply()

            }
            // when the file is read it triggers the onload event above.
            reader.readAsDataURL(element.files[0]);
        }

        function readURL(input) {

            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#blah').attr('src', e.target.result);
                }

                reader.readAsDataURL(input.files[0]);
                $scope.imagem.caminho = input.files[0].name;
                $scope.imagemselecionada = true;

            }
        }

        $("#imgInp").change(function(){
            readURL(this);
            $scope.reload();
        });

        //Recarrega a imagem depois que a outra substitui, temos que enviar para a mesma url por enquanto

        $scope.reload = function(){
            $state.go($state.current, {}, {reload: true});
        };



    });
