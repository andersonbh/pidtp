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
                        var canvasRatio = canvas.height / canvas.width;
                        var windowRatio = $scope.imagem.height / $scope.imagem.width;
                        var width;
                        var height;

                        if (windowRatio < canvasRatio) {
                            height = window.innerHeight;
                            width = height / canvasRatio;
                        } else {
                            width = window.innerWidth;
                            height = width * canvasRatio;
                        }
                        canvas.style.width = width + 'px';
                        canvas.style.height = height + 'px';
                        ctx.drawImage(this, 0, 0);
                        $scope.imagem.width = c.width;
                        console.log($scope.imagem.width);
                        //$scope.imagem.width = image.width;
                        //$scope.imagem.height = image.height;
                        //$scope.imagem.caminho = $scope.imageurl;
                        //});
                    }
                    //$('#blah').attr('src', image.src);
                    console.log($scope.imagem.width);
                };

                reader.readAsDataURL(input.files[0]);
                //Inicia uma imagem para pegar width e height


            }
        }

        //Monitora o envio de imagens e recarrega
        $("#imgEnv").change(function () {
            lerURL(this);
            $scope.reload();
        });

        //Recarrega a imagem depois que a outra substitui, temos que enviar para a mesma url por enquanto
        $scope.reload = function () {
            $state.go($state.current, {}, {reload: true});
        };

        //Ação do botão de fazer download que pega o canvas, gera um png e baixa para desenho.png
        $scope.fazerDownload = function () {
            var cnvs = document.getElementById('pwCanvasMain');
            var button = document.getElementById('btn-download');
            var dataURL = cnvs.toDataURL('image/png');
            button.download = 'desenho.png';
            button.href = dataURL;
        }

    });
