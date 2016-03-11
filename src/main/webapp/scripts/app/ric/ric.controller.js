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

        $scope.selectImagem = function (imagem) {
            $scope.selectedImagem = imagem;
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

        $scope.reload = function(){
            $state.go($state.current, {}, {reload: true});
        };

        function lerURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    var image = new Image();
                    image.src = reader.result;
                    image.onload = function () {
                        //Pega os canvas do angular-painter, exatamanete com os nomes pwcanvasmain e pwcanvastmp
                        var c = document.getElementById('pwCanvasMain');
                        var ctemp = document.getElementById('pwCanvasTmp');
                        //Redimensiona os dois para o tamanho da imagem lindamente
                        c.width = image.width;
                        c.height = image.height;
                        ctemp.width = c.width;
                        ctemp.height = c.height;
                        var ctx = c.getContext("2d");
                        ctx.fillRect(0, 0, c.width, c.height);
                        $scope.imagem.width = image.width;
                        $scope.imagem.height = image.height;
                        ctx.drawImage(this, 0, 0);
                    };
                };
                reader.readAsDataURL(input.files[0]);
            }
        }

        //Monitora o envio de imagens e recarrega
        $("#imgEnv").change(function () {
            lerURL(this);
            $scope.imagemEnviada = true;
            //$scope.reload();
        });

        //Ação do botão de fazer download que pega o canvas, gera um png e baixa para desenho.png
        $scope.fazerDownload = function () {
            var cnvs = document.getElementById('pwCanvasMain');
            var button = document.getElementById('btn-download');
            var dataURL = cnvs.toDataURL('image/png');
            button.download = 'desenho.png';
            button.href = dataURL;
        }

        $http.get('/ric').
        success(function(data) {
            $scope.imagens = data.data;
            console.log(data.data);
        });

        //$scope.sensitiveSearch = function(imagem) {
        //
        //};


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

                },
                yAxis: {
                    tickFormat: function(d){
                        return d3.format('.02f')(d);
                    },
                    axisLabelDistance: -10
                },
                //callback: function(chart){
                //    console.log("!!! lineChart callback !!!");
                //}
            }//,
            //title: {
            //    enable: true,
            //    text: 'Histograma da Imagem'
            //}
        };


        $scope.niveisDeCinza = function() {
            $scope.modalTitulo = 'Níveis de Cinza';

            var niveisCinza = [];
            niveisCinza.push({x: 0,y: 0.068});
            niveisCinza.push({x: 1/7,y: 0.196});
            niveisCinza.push({x: 2/7,y: 0.296});
            niveisCinza.push({x: 3/7,y: 0.209});
            niveisCinza.push({x: 4/7,y: 0.122});
            niveisCinza.push({x: 5/7,y: 0.048});
            niveisCinza.push({x: 6/7,y: 0.033});
            niveisCinza.push({x: 1,y: 0.028});

            $scope.options.chart.xAxis.axisLabel = 'Time (ms)';
            $scope.options.chart.yAxis.axisLabel = 'Voltage (v)';

            //Line chart data should be sent as an array of series objects.
            $scope.data = [
                {
                    values: niveisCinza,      //values - represents the array of {x,y} data points
                    key: 'Níveis de Cinza', //key  - the name of the series.
                    color: '#000000',  //color - optional: choose your own line color.
                    area: true
                }
            ];

            $('#graficos').modal('show');
        };

        $scope.precisaoRevocacao = function() {
            $scope.modalTitulo = 'Precisão x Revocação';

            var precisaoXrevocacao = [];
            precisaoXrevocacao.push({x: 0.1,y: 0.9});
            precisaoXrevocacao.push({x: 0.2,y: 0.7});
            precisaoXrevocacao.push({x: 0.3,y: 0.6});
            precisaoXrevocacao.push({x: 0.4,y: 0.5});
            precisaoXrevocacao.push({x: 0.5,y: 0.4});
            precisaoXrevocacao.push({x: 0.6,y: 0.3});
            precisaoXrevocacao.push({x: 0.7,y: 0.3});
            precisaoXrevocacao.push({x: 0.8,y: 0.2});
            precisaoXrevocacao.push({x: 0.9,y: 0.2});

            $scope.options.chart.xAxis.axisLabel = 'Revocação';
            $scope.options.chart.yAxis.axisLabel = 'Precisão';
            //Line chart data should be sent as an array of series objects.
            $scope.data = [
                {
                    values: precisaoXrevocacao,      //values - represents the array of {x,y} data points
                    key: 'Precisão x Revocação', //key  - the name of the series.
                    color: '#FF0000',  //color - optional: choose your own line color.
                    //area: true
                }
            ];

            $('#graficos').modal('show');
        };
        //#######################################################



    });
