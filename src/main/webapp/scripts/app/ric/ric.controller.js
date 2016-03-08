'use strict';

angular.module('pidtpApp')
    .controller('RICController', function ($scope, Principal, $state) {

        /**
         * pwCanvasMain é o nome padrao do canvas do desenho
         *
         */

        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        $scope.desenhando = false
        $scope.imagemEnviada = false;
        this.undo = function () {
            this.version--;
        };

        $scope.imagem = {
            width: 710,
            height: 600
        };

        $scope.hexColor = '#000000';
        //Funcao para adicionar cor na lista de cores
        $scope.adicionarCor = function () {
            if (checarHex()) {
                $scope.selectcolors.push($scope.hexColor)

                $scope.hexColor = '#000000';
            }
            else {
                alert('Código Hexadecimal não está no formato !!')
            }

        };
        //Cores inicias
        $scope.selectcolors = ['#000', '#00f', '#0f0', '#f00', '#c1d82f'];
        function checarHex() {
            return /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test($scope.hexColor);
        }

        function lerURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    var image = new Image();
                    image.src = reader.result;
                    image.onload = function () {
                        //$scope.$apply(function() {
                        var c = document.getElementById('pwCanvasMain');
                        var ctx = c.getContext("2d");
                        ctx.drawImage(this,0,0,image.width,image.height,0,0,$scope.imagem.width,$scope.imagem.height);

                    };
                };
                reader.readAsDataURL(input.files[0]);
            }
        }

        //Monitora o envio de imagens e recarrega
        $("#imgEnv").change(function () {
            lerURL(this);
        });

        //Ação do botão de fazer download que pega o canvas, gera um png e baixa para desenho.png
        $scope.fazerDownload = function () {
            var cnvs = document.getElementById('pwCanvasMain');
            var button = document.getElementById('btn-download');
            var dataURL = cnvs.toDataURL('image/png');
            button.download = 'desenho.png';
            button.href = dataURL;
        }

    });
