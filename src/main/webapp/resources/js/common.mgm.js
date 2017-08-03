// JavaScript Document
$.browser = {};
$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
// JavaScript Document
if (!console) {
    var console = {
        debug: function () {
            return false;
        },
        info: function () {
            return false;
        },
        log: function () {
            return false;
        }
    }

}


function showAlert(msg) {
    var comform = $('<div class="alertpan dilog"></div>');
    comform.append($('<div class="msg">' + msg + '</div>'));
    var mask = $('<div class="mask light dilog"></div>');

    $('body').append(mask);
    $('body').append(comform);
    var zd_width = $(window).width();
    var zd_height = $(document).height();
    mask.css({
        height: zd_height,
        width: zd_width
    });


    comform.css({
        "z-index": "99999"
    });
    mask.css({
        "z-index": "99998"
    });
    mask.show();
    comform.show()

    comform.css({
        left: (zd_width - comform.width()) / 2,
        top: ($(window).height() - comform.height()) / 2

    })


    setTimeout('closeDilog()', 2000);


    mask.click(function () {

        closeDilog();
    })

    return mask;
}
function closeDilog() {
    closehide();
}

function closehide() {
    $('.dilog').remove();
}

function hasAuth(rights) {
    var bool = false;
    if (typeBitmap) {

        var arr = rights.split(',');

        for (var i = 0; i < arr.length; i++) {

            if (arr[i] == 0) {
                if (typeBitmap.charAt(0) == '1') {
                    bool = true;
                }
            } else if (arr[i] == 1) {
                if (typeBitmap.charAt(1) == '1') {
                    bool = true;
                }


            } else if (arr[i] == 2) {
                if (typeBitmap.charAt(2) == '1') {
                    bool = true;
                }


            } else if (arr[i] == 3) {
                if (typeBitmap.charAt(3) == '1') {
                    bool = true;
                }

            }

        }

    }
    return bool;


}


function showConfirm(msg, btnarr) {
    var comform = $('<div class="comform dilog"></div>');


    comform.append($('<div class="msg">' + msg + '</div>'));
    var btns = $('<ul class="btns"></ul>')
    comform.append(btns);

    if (btnarr) {

        var w = " ";

        if (btnarr.length == 1) {
            w = " w1 ";

        } else if (btnarr.length == 2) {
            w = " w5 ";

        } else if (btnarr.length == 3) {
            w = " w33 ";
        } else if (btnarr.length == 4) {
            w = " w25 ";
        }
        $.each(btnarr, function (index, item) {
            var btnc = $('<font class="btn">' + item.title + '</font>');
            if (item.fun) {
                btnc.click(item.fun);
            } else {
                btnc.click(function () {
                    closeDilog();
                });
            }
            var cls = "";
            if (index > 0) {
                cls = "bordleft";
            }

            var li = $('<li class="' + cls + w + '"></li>')
            var btnb = $('<div class="button_border"></div>');
            btnb.append(btnc);
            li.append(btnb);
            btns.append(li);
        })
    } else {

        var btnc = $('<font class="btn">确认</font>');

        btnc.click(function () {
            closeDilog();
        });

        var li = $('<li class="w1"></li>')
        var btnb = $('<div class="button_border"></div>');
        btnb.append(btnc);
        li.append(btnb);
        btns.append(li);


    }

    var mask = $('<div class="mask dilog"></div>');

    $('body').append(mask);
    $('body').append(comform);
    var zd_width = $(window).width();
    var zd_height = $(document).height();
    mask.css({
        height: zd_height,
        width: zd_width
    });


    comform.css({
        "z-index": "99999"
    });
    mask.css({
        "z-index": "99998"
    });
    mask.show();
    comform.show();

    comform.css({
        left: (zd_width - comform.width()) / 2,
        top: ($(window).height() - comform.height()) / 2

    });
    mask.click(function () {

        closeDilog();

    });

    return mask;
}

function showModal() {
    var divModal = $('<div class="divModal mask"></div>');
    $('body').append(divModal);
    var zd_width = $(document).width();
    var zd_height = $(document).height();
    divModal.css({
        height: zd_height,
        width: zd_width,
        display: "block"
    });

    return divModal;
}
