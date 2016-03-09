'use strict';

angular.module('pidtpApp')
    .controller('RICController', function ($scope, $http, Principal, $state) {

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

        $http.get('/rtc').
        success(function(data) {
            $scope.nomeImagens = data.data;
        });

        console.log($scope.nomeImagens);

        //Metodos para o grafico ###########################
        $scope.options = {
            chart: {
                type: 'lineChart',
                height: 450,
                margin : {
                    top: 20,
                    right: 20,
                    bottom: 40,
                    left: 55
                },
                //width: 'auto',
                //x: function(d){ return d.x; },
                //y: function(d){ return d.y; },
                useInteractiveGuideline: true,
                //dispatch: {
                //    stateChange: function(e){ console.log("stateChange"); },
                //    changeState: function(e){ console.log("changeState"); },
                //    tooltipShow: function(e){ console.log("tooltipShow"); },
                //    tooltipHide: function(e){ console.log("tooltipHide"); }
                //},
                xAxis: {
                    axisLabel: 'Time (ms)'
                },
                yAxis: {
                    axisLabel: 'Voltage (v)',
                    //tickFormat: function(d){
                    //    return d3.format('.02f')(d);
                    //},
                    axisLabelDistance: -10
                },
                callback: function(chart){
                    console.log("!!! lineChart callback !!!");
                }
            }//,
            //title: {
            //    enable: true,
            //    text: 'Histograma da Imagem'
            //}
        };

        $scope.data = sinAndCos();

        /*Random Data Generator */
        function sinAndCos() {
            var sin = [],sin2 = [],
                cos = [];

            //Data is represented as an array of {x,y} pairs.
            for (var i = 0; i < 100; i++) {
                sin.push({x: i, y: Math.sin(i/10)});
                sin2.push({x: i, y: i % 10 == 5 ? null : Math.sin(i/10) *0.25 + 0.5});
                cos.push({x: i, y: .5 * Math.cos(i/10+ 2) + Math.random() / 10});
            }

            //Line chart data should be sent as an array of series objects.
            return [
                {
                    values: sin,      //values - represents the array of {x,y} data points
                    key: 'Sine Wave', //key  - the name of the series.
                    color: '#ff7f0e'  //color - optional: choose your own line color.
                },
                {
                    values: cos,
                    key: 'Cosine Wave',
                    color: '#2ca02c'
                },
                {
                    values: sin2,
                    key: 'Another sine wave',
                    color: '#7777ff',
                }
            ];
        };
        //#######################################################


    });
