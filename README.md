# Globe Connect for React Native

## Introduction
Globe Connect for React Native platform provides an implementation of Globe APIs e.g Authentication, Amax,
Sms etc. that is easy to use and can be integrated in your existing React Native application. Below shows
some samples on how to use the API depending on the functionality that you need to integrate in your
application.

## Basic Usage

###### Figure 1. Authentication

```js
import GlobeConnect from 'react-native-globeconnect';

var auth = GlobeConnect.Authentication(
    '[app_id]',
    '[app_secret]');

auth.getDialogUrl(function() {
    console.log(arguments);
}, function() {
    console.log(arguments);
});

var code = '[code]';

auth.getAccessToken(code, function() {
    console.log(arguments);
}, function() {
    console.log(arguments);
});
```

###### Figure 2. Amax

```js
import GlobeConnect from 'react-native-globeconnect';

var amax = GlobeConnect.Amax(
    '[app_id]',
    '[app_secret]'
);

amax
    .setAddress('[+63 subscriber_number]')
    .setRewardsToken('[rewards_token]')
    .setPromo('[promo]');

amax.sendRewardRequest(function() {
    console.log(arguments);
}, function() {
    console.log(arguments);
});
```

###### Figure 3. Binary SMS

```js
import GlobeConnect from 'react-native-globeconnect';

var binary = GlobeConnect.BinarySms(
    '[short_code]',
    '[access_token]'
);

binary
    .setUserDataHeader('[data_header]')
    .setDataCodingScheme([scheme])
    .setReceiverAddress('[+63 subscriber_number]')
    .setBinaryMessage('[message]');

binary.sendBinaryMessage(function() {
    console.log(arguments);
}, function() {
    console.log(arguments);
});
```

###### Figure 4. Location

```js
import GlobeConnect from 'react-native-globeconnect';

var location = GlobeConnect.Location('[access_token]');

location
    .setAddress('[+63 subscriber_number]')
    .setRequestedAccuracy(10);

location.getLocation(function() {
    console.log(arguments);
}, function() {
    console.log(arguments);
});
```

###### Figure 5. Payment (Send Payment Request)

```js
import GlobeConnect from 'react-native-globeconnect';

var payment = GlobeConnect.Payment('[access_token]');

payment
    .setAppId('[app_id]')
    .setAppSecret('[app_secret]')
    .setAmount([amount])
    .setDescription('[description]')
    .setEndUserId('[+63 subscriber_number]')
    .setReferenceCode('[reference_code]')
    .setTransactionOperationStatus('[status]')
    .sendPaymentRequest(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
```

###### Figure 6. Payment (Get Last Reference ID)

```js
import GlobeConnect from 'react-native-globeconnect';

var payment = GlobeConnect.Payment('[access_token]');

payment
    .setAppId('[app_id]')
    .setAppSecret('[app_secret]')
    .getLastReferenceCode(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
```

###### Figure 7. Sms

```js
import GlobeConnect from 'react-native-globeconnect';

var sms = GlobeConnect.Sms(
    '[short_code]',
    '[access_token]'
);

sms
    .setClientCorrelator('[client_correlator]')
    .setReceiverAddress('[+63 subscriber_number]')
    .setMessage('[message]');

sms.sendMessage(function() {
    console.log(arguments);
}, function() {
    console.log(arguments);
});
```

###### Figure 8. Subscriber (Get Balance)

```java
var subscriber = GlobeConnect.Subscriber('[access_token]');

subscriber
    .setAddress('[+63 subscriber_number]')
    .getSubscriberBalance(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
```

###### Figure 9. Subscriber (Get Reload Amount)

```js
import GlobeConnect from 'react-native-globeconnect';

var subscriber = GlobeConnect.Subscriber('[access_token]');

subscriber
    .setAddress('[+63 subscriber_number]')
    .getSubscriberReloadAmount(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
```

###### Figure 10. USSD (Send)

```js
import GlobeConnect from 'react-native-globeconnect';

var ussd = GlobeConnect.Ussd('[access_token]');

ussd
    .setSenderAddress('[short_code]')
    .setUssdMessage('[message]')
    .setAddress('[+63 subscriber_number]')
    .setFlash([flash])
    .sendUssdRequest(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
```

###### Figure 11. USSD (Reply)

```js
import GlobeConnect from 'react-native-globeconnect';

var ussd = GlobeConnect.Ussd('[access_token]');

ussd
    .setSessionId('[session_id]')
    .setAddress('[+63 subscriber_number]')
    .setSenderAddress('[short_code]')
    .setUssdMessage('[message]')
    .setFlash([flash])
    .replyUssdRequest(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
```
