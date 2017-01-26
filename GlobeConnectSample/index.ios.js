/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import GlobeConnect from 'react-native-globeconnect';

function testAuthentication() {
  var auth = GlobeConnect.Authentication(
      '5ozgSgeRyeHzacXo55TR65HnqoAESbAz',
      '3dbcd598f268268e13550c87134f8de0ec4ac1100cf0a68a2936d07fc9e2459e');

  auth.startLogin(function () {
      console.log(arguments);
  }, function () {
      console.log(arguments);
  })

  // auth.getDialogUrl(function() {
  //     console.log(arguments);
  // }, function() {
  //     console.log(arguments);
  // });
  //
  // var code = 'G4HBMexKfaM9E7SG4LpkHRBoLGf9Go6qSnBno8HRKXnes7doqEukgq4bCq59nKfR7KX6Uorknysa8EXyHoxEaRhzGo57tLn4gduLkaE7S9ke9RtpBjgauaeRKpu4RcoX6y4cRaxuGzjkKuyzedXtkra8qSbe47LueyonxtgoEorhpkEoaHLkkResXyKR4U4K996f4EqB7CRLoKGuBjXorsAxnrpH9poqrSAEo6ef7XLGXHyK9R9SLregxfaM6XxH';
  //
  // auth.getAccessToken(code, function() {
  //     console.log(arguments);
  // }, function() {
  //     console.log(arguments);
  // });
};

function testAmax() {
    var amax = GlobeConnect.Amax(
        '5ozgSgeRyeHzacXo55TR65HnqoAESbAz',
        '3dbcd598f268268e13550c87134f8de0ec4ac1100cf0a68a2936d07fc9e2459e'
    );

    amax
        .setAddress('9065272450')
        .setRewardsToken('w7hYKxrE7ooHqXNBQkP9lg')
        .setPromo('FREE10MB');

    amax.sendRewardRequest(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

function testBinarySms() {
    var binary = GlobeConnect.BinarySms(
        '21584130',
        'JO3SpcC-AFiC461wgOxUPDmsOTc5YiMayoK1GnQcduc'
    );

    binary
        .setUserDataHeader('0605040B8423')
        .setDataCodingScheme(1)
        .setReceiverAddress('9065272450')
        .setBinaryMessage('02056A0045C60C037777772E6465762E6D6F62692F69735F66756E2E68746D6C0');

    binary.sendBinaryMessage(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

function testLocation() {
    var location = GlobeConnect.Location('JO3SpcC-AFiC461wgOxUPDmsOTc5YiMayoK1GnQcduc');

    location
        .setAddress('09065272450')
        .setRequestedAccuracy(10);

    location.getLocation(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

function testPayment() {
    var payment = GlobeConnect.Payment('JO3SpcC-AFiC461wgOxUPDmsOTc5YiMayoK1GnQcduc');

    payment
          .setAppId('5ozgSgeRyeHzacXo55TR65HnqoAESbAz')
          .setAppSecret('3dbcd598f268268e13550c87134f8de0ec4ac1100cf0a68a2936d07fc9e2459e')
          .setAmount(0.00)
          .setDescription('My Description')
          .setEndUserId('9065272450')
          .setReferenceCode('41301000113')
          .setTransactionOperationStatus('Charged');

    payment.sendPaymentRequest(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });

    payment.getLastReferenceCode(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

function testSms() {
    var sms = GlobeConnect.Sms(
        '21584130',
        'JO3SpcC-AFiC461wgOxUPDmsOTc5YiMayoK1GnQcduc'
    );

    sms
      .setClientCorrelator('12345')
      .setReceiverAddress('+639065272450')
      .setMessage('Hello World');

    sms.sendMessage(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

function testSubscriber() {
    var subscriber = GlobeConnect.Subscriber('JO3SpcC-AFiC461wgOxUPDmsOTc5YiMayoK1GnQcduc');

    subscriber.setAddress('639065272450');

    subscriber.getSubscriberBalance(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });

    subscriber.getSubscriberReloadAmount(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

function testUssd() {
    var ussd = GlobeConnect.Ussd('JO3SpcC-AFiC461wgOxUPDmsOTc5YiMayoK1GnQcduc');

    ussd
        .setSenderAddress('21584130')
        .setUssdMessage('Simple USSD Message\n1: Hello \n2: Hello')
        .setAddress('9065272450')
        .setFlash(false);

    ussd.sendUssdRequest(function() {
          console.log(arguments);
      }, function() {
          console.log(arguments);
      });

    ussd
        .setSessionId('12345')
        .setAddress('9065272450')
        .setSenderAddress('21584130')
        .setUssdMessage('Simple USSD Message\n1: Foo\n2: Foo')
        .setFlash(false);

    ussd.replyUssdRequest(function() {
        console.log(arguments);
    }, function() {
        console.log(arguments);
    });
};

testAuthentication();
// testAmax();
// testBinarySms();
// testLocation();
// testPayment();
// testSms();
// testSubscriber();
// testUssd();

console.log(GlobeConnect);

export default class GlobeConnectSample extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.ios.js
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('GlobeConnectSample', () => GlobeConnectSample);
