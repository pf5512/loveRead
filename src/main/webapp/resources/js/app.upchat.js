(function (e, n, t) {
    n.M = n.M || {};
    n.M.NAPI = n.M.NAPI || {};
    var o = function () {
        var e = window.PhoneGap || window.Cordova || window.cordova;
        return e
    };
    var i = function (e) {
        return typeof e == "string" && e.constructor == String
    };

    function r() {
        var e = navigator.userAgent.toLowerCase();
        return /upchat/.test(e) ? true : false
    }

    function a() {
        var e = navigator.userAgent.toLowerCase();
        var n = new RegExp(/(android)\s+([\d.]+)/);
        return n.test(e)
    }

    var s = function (e, n, t, r, a) {
        o().exec(function (n) {
            var t = "";
            try {
                t = i(n) ? JSON.parse(n) : n
            } catch (o) {
                t = n
            }
            !!e && e.apply(null, [t])
        }, function (e) {
            var t = "";
            try {
                t = i(e) ? JSON.parse(e) : e
            } catch (o) {
                t = e
            }
            !!n && n.apply(null, [t])
        }, t, r, [a])
    };
    e.extend(n.M.NAPI, {
        showWaitingView: function () {
            if (r()) {
                s(null, null, "UPWebUI", "showWaitingView", {})
            }
        },
        showLoadingView: function () {
            if (r()) {
                s(null, null, "UPWebUI", "showLoadingView", {})
            }
        },
        showFlashInfo: function (e) {
            if (r()) {
                s(null, null, "UPWebUI", "showFlashInfo", e)
            }
        },
        showAlertView: function (e) {
            if (r()) {
                s(null, null, "UPWebUI", "showAlertView", e)
            }
        },
        dismiss: function () {
            if (r()) {
                s(null, null, "UPWebUI", "dismiss", {})
            }
        },
        setBarStatus: function (e) {
            if (r()) {
                s(null, null, "UPWebBars", "setBarStatus", e)
            }
        },
        setNavigationBarTitle: function (e) {
            if (r()) {
                s(null, null, "UPWebBars", "setNavigationBarTitle", e)
            }
        },
        getSecurity: function (e, n) {
            if (r()) {
                s(e, n, "UPChatPluginPublicAccount", "getSecurity", {})
            }
        },
        getLocation: function (e, n) {
            if (r()) {
                s(e, n, "UPLocation", "getLocation", {})
            }
        },
        openFileChooser: function (e, n) {
            if (r() && a()) {
                s(e, n, "FileChooser", "open", {})
            }
        },
        choosePhoto: function (e, n) {
            if (r()) {
                s(e, n, "UPImagePicker", "chooseImage", {})
            }
        },
        fetchUserId: function (e, n) {
            if (r()) {
                s(e, n, "UPChatPlugin", "fetchUserId", {})
            }
        }
    });
    n.M.init = function (e) {
        if (r()) {
            document.addEventListener("deviceready", e, false)
        } else {
            e()
        }
    };
    n.M.isNative = r
})(jQuery, window.UPCHAT = window.UPCHAT || {});
var query2Obj = function (e) {
    if (e[0] === "?" || e[0] === "#") {
        e = e.substring(1)
    }
    var n = {};
    e.replace(/\b([^&=]*)=([^&=]*)/g, function (e, t, o) {
        if (typeof n[t] != "undefined") {
            n[t] += "," + decodeURIComponent(o)
        } else {
            n[t] = decodeURIComponent(o)
        }
    });
    return n
};
var isNative = function () {
    var e = navigator.userAgent.toLowerCase();
    return /upchat/.test(e) ? true : false
};
var isIos = function () {
    var e = navigator.userAgent.toLowerCase();
    var n = new RegExp(/iphone|ipad/);
    return n.test(e)
};
var osp = isIos() ? "ios_" : "android_", version = "3.0.0";
var query = query2Obj(location.search);
var cordova_ver = query.cver ? query.cver : "";
cordova_ver = cordova_ver || version;
var p = window.location.href.indexOf("https") == 0 ? "https" : "http";
if (isNative()) {
    document.write('<script type="text/javascript" src="' + p
        + "://upchat.95516.net/public/scripts/cordova/min/cordova." + osp
        + cordova_ver + '-min.js"></script>')
}