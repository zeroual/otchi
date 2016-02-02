angular.module('helpers', []);
angular.module('stream', ['ngResource', 'helpers']);
angular.module('publisher', ['ngResource']);
var app = angular.module("otchi", ['publisher', 'stream', 'authentication', 'ui.router', 'ui.bootstrap']);