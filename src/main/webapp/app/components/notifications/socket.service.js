angular.module("socket", ['toaster'])
    .service("SocketService", function ($q) {
        var self = this;
        var stompClient;
        self.connect = function () {
            var connected = $q.defer();

            if (!isAlreadyConnected()) {
                var url = buildSocketServerURL();
                var socket = new SockJS(url);
                stompClient = Stomp.over(socket);
                var headers = {};
                stompClient.connect(headers, function () {
                    connected.resolve("success");
                });
            } else {
                connected.resolve("success");
            }
            return connected.promise;
        };


        self.subscribe = function (topic) {
            var deferred = $q.defer();
            if (isAlreadyConnected()) {
                stompClient.subscribe(topic, function (payload) {
                    deferred.resolve(payload);
                });
            } else {
                deferred.reject('no websocket established with the server ');
            }
            return deferred.promise;
        };

        function buildSocketServerURL() {
            var loc = window.location;
            var url = '//' + loc.host + loc.pathname + 'socket';
            return url;
        }

        function isAlreadyConnected() {
            return stompClient != null && stompClient.connected
        }
    })
    .service("NotificationsHandlerService", function (SocketService, ToasterService) {
        var self = this;
        self.handleNotifications = function () {
            SocketService.subscribe("/user/topic/notifications").then(function (notification) {
                ToasterService.info('somebody like your post,we need to custom this next US :D');
            });
        }
    });