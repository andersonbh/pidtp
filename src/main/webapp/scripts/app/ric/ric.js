'use strict';

angular.module('pidtpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ric', {
                parent: 'site',
                url: '/ric',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/ric/ric.html',
                        controller: 'RICController'
                    }
                },
                resolve: {

                }
            });
    });
