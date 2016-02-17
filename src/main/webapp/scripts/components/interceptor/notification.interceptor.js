 'use strict';

angular.module('pidtpApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-pidtpApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-pidtpApp-params')});
                }
                return response;
            }
        };
    });
