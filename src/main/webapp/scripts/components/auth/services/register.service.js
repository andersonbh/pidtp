'use strict';

angular.module('pidtpApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


