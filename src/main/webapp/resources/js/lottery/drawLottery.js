var turnplate = {
    restaraunts: [], // 大转盘奖品名称
    colors: [], // 大转盘奖品区块对应背景颜色
    outsideRadius: 120, // 大转盘外圆的半径
    textRadius: 70, // 大转盘奖品位置距离圆心的距离
    insideRadius: 25, // 大转盘内圆的半径
    startAngle: 0, // 开始角度
    bRotate: false,// false:停止;ture:旋转
    canvasWidth: 300,
    canvasHeight: 300

};

// 旋转转盘 item:奖品位置; txt：提示语;
var rotateFn = function (item, txt) {
    var angles = item * (360 / turnplate.restaraunts.length)
        - (360 / (turnplate.restaraunts.length * 2));
    if (angles < 270) {
        angles = 270 - angles;
    } else {
        angles = 360 - angles + 270;
    }
    $('#wheelcanvas').stopRotate();
    $('#wheelcanvas').rotate({
        angle: 0,
        animateTo: angles + 1800,
        duration: 3000,
        callback: function () {
            zzrw.alert(txt);
            turnplate.bRotate = !turnplate.bRotate;
        }
    });
};

var noPrizeStr = "谢谢参与";// 未抽中奖的区域标示
var noGetPrize = "谢谢您的参与";// 未中奖提示前缀
var alreadyPrize = "您的抽奖机会已经用完了,敬请期待我们下一次活动";// 抽奖机会用完提示
var noLeftPrize = "您来晚了，奖项已抽完,敬请期待我们下一次活动";// 奖池已空提示
var prizePrefixHint = "恭喜您抽中";// 获奖提示前缀
var prizeSuffixHint = "，奖品将在3个工作日内发送给您！";// 提示后缀。

var prizeColors = ["#f08852", "#fdf087"];// 奖品区域背景颜色集合
var prizeNameList = ["谢谢参与"];

/**
 * 生成n到m的随机数
 */
function rnd(n, m) {
    var random = Math.floor(Math.random() * (m - n + 1) + n);
    return random;

}

/**
 * 发送Ajax
 *
 * @param tel
 */
function sendAjax(tel) {
    $.ajax({
        type: "post",
        url: urlRoot + "/web/nm/getDrawLottery",
        async: false,
        data: {
            telephone: tel
        },
        success: function (ret) {
            var item = parseInt(ret);
            if (item > 0) {
                turnplate.bRotate = !turnplate.bRotate;
                rotateFn(item, prizePrefixHint
                    + turnplate.restaraunts[item - 1] + prizeSuffixHint);
            } else {
                switch (item) {
                    case 0:
                        zzrw.alert(alreadyPrize);
                        break;
                    case -1:
                        zzrw.alert(noLeftPrize);
                        break;
                    case -2:
                        turnplate.bRotate = !turnplate.bRotate;
                        rotateFn(turnplate.restaraunts.length, noGetPrize
                            + prizeSuffixHint);
                        break;
                }
            }
        }
    });
}

/**
 * 配置转盘参数,主要是奖项名称和对应转盘颜色
 */
function setTurnplate() {

    for (var i = 0; i < prizeNameList.length; i++) {
        turnplate.restaraunts.push(prizeNameList[i]);
    }

    for (var i = 0; i < prizeNameList.length; i++) {
        turnplate.restaraunts.push(prizeNameList[i]);
    }

    for (var i = 0; i < 2 * (prizeNameList.length); i++) {
        turnplate.colors.push(prizeColors[i % prizeColors.length]);
    }
}

/**
 * 构建转盘
 */
function drawRouletteWheel(canvasWidth, canvasHeight) {
    var canvas = document.getElementById("wheelcanvas");
    if (canvas.getContext) {
        // 根据奖品个数计算圆周角度
        var arc = Math.PI / (turnplate.restaraunts.length / 2);
        var ctx = canvas.getContext("2d");
        // 在给定矩形内清空一个矩形
        ctx.clearRect(0, 0, canvasWidth, canvasHeight);
        // strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式
        ctx.strokeStyle = "#FFBE04";
        // font 属性设置或返回画布上文本内容的当前字体属性
        ctx.font = '16px Microsoft YaHei';
        for (var i = 0; i < turnplate.restaraunts.length; i++) {
            var angle = turnplate.startAngle + i * arc;
            ctx.fillStyle = turnplate.colors[i];
            ctx.beginPath();
            // arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）
            ctx.arc(canvasWidth / 2, canvasHeight / 2, turnplate.outsideRadius, angle, angle + arc,
                false);
            ctx.arc(canvasWidth / 2, canvasHeight / 2, turnplate.insideRadius, angle + arc, angle, true);
            ctx.stroke();
            ctx.fill();
            // 锁画布(为了保存之前的画布状态)
            ctx.save();

            // ----绘制奖品开始----
            ctx.fillStyle = "#E5302F";
            var text = turnplate.restaraunts[i];
            var line_height = 16;
            // translate方法重新映射画布上的 (0,0) 位置
            ctx.translate(canvasWidth / 2 + Math.cos(angle + arc / 2)
                * turnplate.textRadius, canvasHeight / 2 + Math.sin(angle + arc / 2)
                * turnplate.textRadius);

            // rotate方法旋转当前的绘图
            ctx.rotate(angle + arc / 2);

            ctx.fillText(text, -ctx.measureText(text).width / 4, line_height / 2);

            // 把当前画布返回（调整）到上一个save()状态之前
            ctx.restore();
            // ----绘制奖品结束----
        }
    }
}
