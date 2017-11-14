/**
 * @providesModule UPIDirectConnect
 */

'use strict';
import React from 'react';
import { AlertIOS, Platform, NativeModules } from 'react-native';

const UPIDirectConnect = NativeModules.UPIDirectConnect;

const Buttons = {
    POSITIVE_BUTTON: "POSITIVE_BUTTON",
    NEGATIVE_BUTTON: "NEGATIVE_BUTTON",
    NEUTRAL_BUTTON: "NEUTRAL_BUTTON",
};

if (Platform.OS !== 'ios') {
    Buttons = {
        POSITIVE_BUTTON: UPIDirectConnect.POSITIVE_BUTTON,
        NEGATIVE_BUTTON: UPIDirectConnect.NEGATIVE_BUTTON,
        NEUTRAL_BUTTON: UPIDirectConnect.NEUTRAL_BUTTON,
    };
}

const UPIDirectConnect = {
    [Buttons.POSITIVE_BUTTON]: Buttons.POSITIVE_BUTTON,
    [Buttons.NEGATIVE_BUTTON]: Buttons.NEGATIVE_BUTTON,
    [Buttons.NEUTRAL_BUTTON]: Buttons.NEUTRAL_BUTTON,
    alert(title, text, buttonConfig) {
        let _buttonConfig = (buttonConfig||[{ type: UPIDirectConnect.POSITIVE_BUTTON, text: 'OK', }]);

            let _masterCallback = (buttonType) => {
                for(let j=0; j<_buttonConfig.length; j++) {
                    if("type" && _buttonConfig[j] && _buttonConfig[j].type === buttonType && ("onPress" in _buttonConfig[j] && typeof _buttonConfig[j].onPress == "function")) {
                        _buttonConfig[j].onPress.apply(null, [{type: buttonType}]);
                    }
                }
            };

            UPIDirectConnect.alert((title||null), (text||null), _buttonConfig, _masterCallback);

    }
    initPayment(params) {
        UPIDirectConnect.initPayment((params||null), _masterCallback);
    }
};

module.exports = UPIDirectConnect;
