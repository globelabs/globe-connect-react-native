import { NativeModules } from 'react-native';

module.exports = {
    Amax : function(appId, appSecret) {
        if(!(this instanceof module.exports.Amax)) {
            return new module.exports.Amax(appId, appSecret);
        }

        var amax = NativeModules.Amax;

        if(appId) {
            amax.setAppId(appId);
        }

        if(appSecret) {
            amax.setAppSecret(appSecret);
        }

        for(var i in amax) {
            if(typeof amax[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            amax[i].apply(self, args);

                            return self;
                        }

                        amax[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    Authentication : function(appId, appSecret) {
        if(!(this instanceof module.exports.Authentication)) {
            return new module.exports.Authentication(appId, appSecret);
        }

        var authentication = NativeModules.Authentication;

        if(appId) {
            authentication.setAppId(appId);
        }

        if(appSecret) {
            authentication.setAppSecret(appSecret);
        }

        for(var i in authentication) {
            if(typeof authentication[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            authentication[i].apply(self, args);

                            return self;
                        }

                        authentication[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    BinarySms : function(senderAddress, accessToken) {
        if(!(this instanceof module.exports.BinarySms)) {
            return new module.exports.BinarySms(senderAddress, accessToken);
        }

        var binary = NativeModules.BinarySms;

        if(senderAddress) {
            binary.setSenderAddress(senderAddress);
        }

        if(accessToken) {
            binary.setAccessToken(accessToken);
        }

        for(var i in binary) {
            if(typeof binary[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            binary[i].apply(self, args);

                            return self;
                        }

                        binary[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    Location : function(accessToken) {
        if(!(this instanceof module.exports.Location)) {
            return new module.exports.Location(accessToken);
        }

        var location = NativeModules.Location;

        if(accessToken) {
            location.setAccessToken(accessToken);
        }

        for(var i in location) {
            if(typeof location[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            location[i].apply(self, args);

                            return self;
                        }

                        location[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    Payment : function(accessToken) {
        if(!(this instanceof module.exports.Payment)) {
            return new module.exports.Payment(accessToken);
        }

        var payment = NativeModules.Payment;

        if(accessToken) {
            payment.setAccessToken(accessToken);
        }

        for(var i in payment) {
            if(typeof payment[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            payment[i].apply(self, args);

                            return self;
                        }

                        payment[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    Sms : function(senderAddress, accessToken) {
        if(!(this instanceof module.exports.Sms)) {
            return new module.exports.Sms(senderAddress, accessToken);
        }

        var sms = NativeModules.Sms;

        if(senderAddress) {
            sms.setSenderAddress(senderAddress);
        }

        if(accessToken) {
            sms.setAccessToken(accessToken);
        }

        for(var i in sms) {
            if(typeof sms[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            sms[i].apply(self, args);

                            return self;
                        }

                        sms[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    Subscriber : function(accessToken) {
        if(!(this instanceof module.exports.Subscriber)) {
            return new module.exports.Subscriber(accessToken);
        }

        var subscriber = NativeModules.Subscriber;

        if(accessToken) {
            subscriber.setAccessToken(accessToken);
        }

        for(var i in subscriber) {
            if(typeof subscriber[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            subscriber[i].apply(self, args);

                            return self;
                        }

                        subscriber[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    },
    Ussd : function(accessToken) {
        if(!(this instanceof module.exports.Ussd)) {
            return new module.exports.Ussd(accessToken);
        }

        var ussd = NativeModules.Ussd;

        if(accessToken) {
            ussd.setAccessToken(accessToken);
        }

        for(var i in ussd) {
            if(typeof ussd[i] === 'function') {
                (function(self, i) {
                    self[i] = function() {
                        var args = Array.prototype.slice.call(arguments);

                        if(typeof args[0] === 'function') {
                            var success  = args[0];

                            var callback = function(data) {
                                try {
                                    data = JSON.parse(data);
                                } catch(e) { console.log(e); };

                                return success.call(self, data);
                            };

                            args[0] = callback;

                            ussd[i].apply(self, args);

                            return self;
                        }

                        ussd[i].apply(self, args);

                        return self;
                    };
                })(this, i);
            }
        }

        return this;
    }
};
