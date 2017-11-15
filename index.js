/**
 * @providesModule UPIDirectConnect2
 */

'use strict';
import React from 'react';
import {  Platform, NativeModules } from 'react-native';

const UPIDirectConnect = NativeModules.UPIDirectConnect;

const UPIDirect = {
  initPayment(title) {
     UPIDirectConnect.initPayment((title||null), null);
  }
    // alert(title, text, buttonConfig) {
    //     let _buttonConfig = (buttonConfig||[{ type: UPIDirect.POSITIVE_BUTTON, text: 'OK', }]);
    //     if (Platform.OS === 'ios') {
    //         for(let j=0; j<_buttonConfig.length; j++) {
    //             if("type" && _buttonConfig[j]) delete _buttonConfig[j]['type'];
    //         }
    //         AlertIOS.alert.apply(AlertIOS, [title, text, _buttonConfig]);
    //     } else {
    //         let _masterCallback = (buttonType) => {
    //             for(let j=0; j<_buttonConfig.length; j++) {
    //                 if("type" && _buttonConfig[j] && _buttonConfig[j].type === buttonType && ("onPress" in _buttonConfig[j] && typeof _buttonConfig[j].onPress == "function")) {
    //                     _buttonConfig[j].onPress.apply(null, [{type: buttonType}]);
    //                 }
    //             }
    //         };
    //
    //         SimpleAlertAndroid.alert((title||null), (text||null), _buttonConfig, _masterCallback);
    //     }
    // }

};

module.exports = UPIDirect;
