'use strict';

angular.module('pidtpApp')
    .filter('slice', function () {
        return function (arr, end) {
            return arr.slice(0, end);
        };
    })
    .controller('RICController', function ($scope, $http, Principal, $state) {

        /**
         * pwCanvasMain é o nome padrao do canvas do desenho
         *
         */

        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        this.undo = function () {
            this.version--;
        };

        $scope.selectImagem = function (imagem) {
            $scope.selectedImagem = imagem;
            $scope.imagemFiltrada = null;
        };

        $scope.iniciar = function () {
            $scope.recriarDesenho();
            $scope.carregarImagens();
        }

        $scope.recriarDesenho = function () {
            $scope.imagem = {
                width: 710,
                height: 600
            };
            $scope.imagemFiltrada = null;
        };


        $scope.normalizar = false;

        $scope.processarImagem = function(){
            $http.post("/ric/processar",
                {
                    nomeImagem: $scope.selectedImagem.nome,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            })
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
        $scope.selectcolors = ['#000', '#00f', '#0f0', '#f00', '#c1d82f', '#ffffff'];
        function checarHex() {
            return /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test($scope.hexColor);
        }

        $scope.reload = function () {
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
                        if (image.width <= $scope.imagem.width && image.height <= $scope.imagem.height) {
                            c.width = image.width;
                            c.height = image.height;
                            ctemp.width = c.width;
                            ctemp.height = c.height;
                            var ctx = c.getContext("2d");
                            ctx.fillRect(0, 0, c.width, c.height);
                            $scope.imagem.width = image.width;
                            $scope.imagem.height = image.height;
                            ctx.drawImage(this, 0, 0);
                        } else {
                            c.width = $scope.imagem.width;
                            c.height = $scope.imagem.height;
                            ctemp.width = c.width;
                            ctemp.height = c.height;
                            var ctx2 = c.getContext("2d");
                            ctx2.fillRect(0, 0, c.width, c.height);
                            ctx2.drawImage(image, 0, 0, image.width, image.height, 0, 0, $scope.imagem.width, $scope.imagem.height);
                        }
                    };
                };
                reader.readAsDataURL(input.files[0]);
            }
        }

        //Monitora o envio de imagens e recarrega
        $("#imgEnv").change(function () {
            //Volta o tamanho do plano para o original
            $scope.recriarDesenho();
            lerURL(this);
        });

        //Ação do botão de fazer download que pega o canvas, gera um png e baixa para desenho.png
        $scope.fazerDownload = function () {
            var cnvs = document.getElementById('pwCanvasMain');
            var button = document.getElementById('btn-download');
            var dataURL = cnvs.toDataURL('image/png');
            button.download = 'desenho.png';
            button.href = dataURL;
        };

        $scope.salvarNoDisco = function () {
            var cnvs = document.getElementById('pwCanvasMain');
            var dataURL = cnvs.toDataURL('image/jpeg');
            $http.post("/ric/uploadimg",
                {
                    dataURL: dataURL,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            });
        };
        $scope.salvarECalcDist = function () {
            var cnvs = document.getElementById('pwCanvasMain');
            var dataURL = cnvs.toDataURL('image/jpeg');
            $http.post("/ric/uploadimg",
                {
                    dataURL: dataURL,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                $http.post("/ric/calcDist",
                    {
                        tipoDistancia: $scope.distancia,
                        ajax : true}, {
                        transformRequest: function(data) {
                            return $.param(data);
                        },
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                    }).success(function(response){
                    $scope.carregarImagens();
                    console.log('aeee' + response.message);
                }).error(function(response){
                    console.log('merda');
                });
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            });
        };

        $scope.salvarEFiltro = function (nomeImagem) {
            $scope.imagemFiltrada = null;
            var cnvs = document.getElementById('pwCanvasMain');
            var dataURL = cnvs.toDataURL('image/jpeg');
            $http.post("/ric/uploadimg",
                {
                    dataURL: dataURL,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                    // se o filtro for media
                    $scope.filtro(nomeImagem);
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            });
        };

        $scope.filtro = function (nomeImagem) {
            if($scope.selectedFiltroImagem.valor < 5 || $scope.selectedFiltroImagem.valor >= 8){
                $scope.variaveisFiltros = 0;
            }
            if($scope.selectedFiltroImagem.valor == 7){
                if($scope.sobelFiltro == 1)
                    $scope.variaveisFiltros = 1;
                else if($scope.sobelFiltro == 2)
                    $scope.variaveisFiltros = 2;
                else
                    $scope.variaveisFiltros = 3;
            }
            $http.post("/ric/filtro",
                {
                    nomeImagem: nomeImagem,
                    tipo: $scope.selectedFiltroImagem.valor,
                    variaveis: $scope.variaveisFiltros,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                $scope.imagemFiltrada = response.data[0];
                $scope.variaveisFiltros = null;
            }).error(function(response){
                console.log('merda');
            });
        };

        $scope.salvarEHistograma = function (imagem) {
            var cnvs = document.getElementById('pwCanvasMain');
            var dataURL = cnvs.toDataURL('image/jpeg');
            $http.post("/ric/uploadimg",
                {
                    dataURL: dataURL,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                $scope.histogramaPrincipal(imagem);
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            });
        };


        $scope.carregarImagens = function () {
            $http.get('/ric').success(function (data) {
                $scope.imagens = data.data;
            });
        };



        //Metodos para o grafico ###########################
        $scope.options = {
            chart: {
                type: 'lineChart',
                height: 400,
                width: 700,
                margin: {
                    top: 20,
                    right: 20,
                    bottom: 40,
                    left: 55
                },
                useInteractiveGuideline: true,
                xAxis: {},
                yAxis: {
                    tickFormat: function (d) {
                        return d3.format('.02f')(d);
                    },
                    axisLabelDistance: -10
                }

            }
        };

        $scope.histogramaPrincipal = function (nomeImagem) {
            $scope.modalTitulo = 'Histogramas da imagem';
            var histogramaRGB = [];
            var histogramaYUV = [];
            var histogramaHSV = [];

            $http.post("/ric/histograma",
                {
                    nomeImagem: nomeImagem,
                    normalizar: $scope.normalizar,
                    ajax : true}, {
                    transformRequest: function(data) {
                        return $.param(data);
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                }).success(function(response){
                function plotaPontoRGB(element, index, array) {
                    histogramaRGB.push({x: index, y: element});
                }
                response.data[0].forEach(plotaPontoRGB);
                function plotaPontoHSV(element, index, array) {
                    histogramaHSV.push({x: index, y: element});
                }
                response.data[1].forEach(plotaPontoHSV);
                function plotaPontoYUV(element, index, array) {
                    histogramaYUV.push({x: index, y: element});
                }
                response.data[2].forEach(plotaPontoYUV);
                console.log('aeee' + response.message);
            }).error(function(response){
                console.log('merda');
            });

            $scope.options.chart.xAxis.axisLabel = 'Tons';
            $scope.options.chart.yAxis.axisLabel = 'Frequencia';

            $scope.dataRGB = [
                {
                    values: histogramaRGB,      //values - represents the array of {x,y} data points
                    key: 'Ocorrencia', //key  - the name of the series.
                    color: '#000000',  //color - optional: choose your own line color.
                    area: true
                }
            ];
            $scope.dataHSV = [
                {
                    values: histogramaHSV,      //values - represents the array of {x,y} data points
                    key: 'Ocorrencia', //key  - the name of the series.
                    color: '#000000',  //color - optional: choose your own line color.
                    area: true
                }
            ];
            $scope.dataYUV = [
                {
                    values: histogramaYUV,      //values - represents the array of {x,y} data points
                    key: 'Ocorrencia', //key  - the name of the series.
                    color: '#000000',  //color - optional: choose your own line color.
                    area: true
                }
            ];

            $('#graficos').modal('show');
        };

        $scope.calcDist = function () {
            $scope.salvarECalcDist();


        };

        $scope.order = "distanciaatual";


        $scope.precisaoRevocacao = function () {
            $scope.modalTitulo = 'Precisão x Revocação';

            var precisaoXrevocacao = [];
            precisaoXrevocacao.push({x: 0.1, y: 0.9});
            precisaoXrevocacao.push({x: 0.2, y: 0.7});
            precisaoXrevocacao.push({x: 0.3, y: 0.6});
            precisaoXrevocacao.push({x: 0.4, y: 0.5});
            precisaoXrevocacao.push({x: 0.5, y: 0.4});
            precisaoXrevocacao.push({x: 0.6, y: 0.3});
            precisaoXrevocacao.push({x: 0.7, y: 0.3});
            precisaoXrevocacao.push({x: 0.8, y: 0.2});
            precisaoXrevocacao.push({x: 0.9, y: 0.2});

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

        $scope.filtroRolagem = [
            {valor: 5},
            {valor: 10},
            {valor: 15},
            {valor: 30},
            {valor: 100},
            {valor: 1000}];

        $scope.filtroImagem = [
            {valor: 1, filtro: "Filtro Média"},
            {valor: 2, filtro: "Filtro Mediana"},
            {valor: 3, filtro: "Filtro Máximo"},
            {valor: 4, filtro: "Filtro Mínimo"},
            {valor: 5, filtro: "Transformada Binarizacão"},
            {valor: 6, filtro: "Filtro Laplaciano"},
            {valor: 7, filtro: "Filtro Sobel"},
            {valor: 8, filtro: "Filtro Canny"},
            {valor: 9, filtro: "Transformada Monocromático"},
            {valor: 10, filtro: "Filtro Negativo"}
        ];

        // Filtro para mostrar numero de imagens na Rolagem ################333
        $scope.rolagemFilter = function (imagens) {
            if (imagens.id >= $scope.selectedFiltro.valor) {
                return;
            }
            return imagens;
        };
        //#####################################################################

        $scope.visualizarImagem = function (imagem) {
            $scope.salvarEHistograma(imagem);
        }

    });
