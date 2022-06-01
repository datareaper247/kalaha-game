$(document).ready(function () {
    const PLAYER1 = "player1";
    const PLAYER2 = "player2";
    const ENABLED_CLASS = "enabled";
    const DISABLED_CLASS = "disabled";

    loadGame(false);

    $(".pit").click(function (event) {
        if ($(this).hasClass(DISABLED_CLASS)) return;

        console.log("$(this).index",$(this).index());

        let gameID = localStorage.getItem('gameId');
        $.ajax({
            url: "/move",
            type: "POST",
            data:
                JSON.stringify({
                    gameId: gameID,
                    player: $(this).parent().attr('id'),
                    pitNumber: $(this).index() - 1
                }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (result) {
                drawBoard(result);
            },
            error: function (result) {
                alert("Status: " + JSON.stringify(result));
            }
        });
    });


    $("#start-game").click(function () {
        loadGame(true);
    });
    $("#show-hide-rules").click(function () {
        $("#rules").toggle();
    });

    function loadGame(isNewGame = false) {
        $("#winner").hide();
        let gameID = null;
        if (!isNewGame) {
            gameID = localStorage.getItem('gameId');
        }
        $.ajax({
            url: "/start",
            data: {
                gameId: gameID
            },
            success: function (result) {
                drawBoard(result);
            },
            error: function (result) {
                alert("Status: " + JSON.stringify(result));
            }
        });
    }

    function drawBoard(result) {
        localStorage.setItem('gameId', result.gameId);
        $.each(result.player1.pits, function (i, item) {
            $("#player1 .pit").eq(i).html("<p>"+item+"</p>")
        });

        $.each(result.player2.pits, function (i, item) {
            $("#player2 .pit").eq(i).html("<p>"+item+"</p>")
        });
        $("#treasury1 #score").html("<p>"+result.player1.treasury+"</p>");
        $("#treasury2 #score").html("<p>"+result.player2.treasury+"</p>");


        switch (result.currentPlayer) {
            case PLAYER1:
                $("#player1 .pit").removeClass(DISABLED_CLASS).addClass(ENABLED_CLASS);
                $("#player2 .pit").removeClass(ENABLED_CLASS).addClass(DISABLED_CLASS);
                $("#turninfo > span").html(PLAYER1);
                break;
            case PLAYER2:
                $("#player1 .pit").removeClass(ENABLED_CLASS).addClass(DISABLED_CLASS);
                $("#player2 .pit").removeClass(DISABLED_CLASS).addClass(ENABLED_CLASS);
                $("#turninfo > span").html(PLAYER2);
                break;
        }
        if (result.isWinnerExist) {
            $("#player1 div, #player2 div").removeClass(ENABLED_CLASS).addClass(DISABLED_CLASS);
            $('#winner').show().children("span").html(result.winner);
        }
    }
});
