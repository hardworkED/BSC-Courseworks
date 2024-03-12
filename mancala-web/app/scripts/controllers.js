"use strict";

/* global $, BoardModel, RandomAI, MinimaxAI, AlphaBetaAI */

function BoardController(boardSize=14, stonesPerPit=4) {
    this.boardSize = boardSize;
    this.stonesPerPit = stonesPerPit;
}

BoardController.prototype.startGame = function(humanPlayer) {
    this.model = new BoardModel(this.boardSize, this.stonesPerPit);
    this.p0ai = null;
    this.p1ai = null;
    this.difficulty = Number($('input[name=difficulty]:checked').val());
    if (humanPlayer == 0) {
        if (this.difficulty === 0) {
            this.p1ai = new RandomAI(1);
        } else {
            this.p1ai = new MinimaxAI(1);
        }
    } else if (humanPlayer == 1) {
        if (this.difficulty === 0) {
            this.p0ai = new RandomAI(0);
        } else {
            this.p0ai = new MinimaxAI(0);
        }
    }
    this.drawBoard("Bottom player goes first");
    // AI goes first
    if (this.p0ai) {
        this.playMove(this.p0ai.playMove(this.model));
    }
}

BoardController.prototype.drawBoard = function(msg, lastMove) {
    for (var i in this.model.board) {
        if (this.model.board[i] == 0) {
            // if pit empty, display empty string
            $('#' + i).text('');
        } else {
            // else display seed value
            $('#' + i).text(this.model.board[i]);
        }
        $('#' + i).removeClass("last-move");
    }
    $('#score0').text(this.model.score[0]);
    $('#score1').text(this.model.score[1]);
    $('#' + lastMove).addClass("last-move");
    // disable move if opponent's turn
    for (var i = 0; i < this.model.boardSize / 2; i++) {
        $('#' + i).prop("disabled", (this.model.playerTurn === 1 || this.p0ai));
    }
    for (var i = this.model.boardSize / 2; i < this.model.boardSize; i++) {
        $('#' + i).prop("disabled", (this.model.playerTurn === 0 || this.p1ai));
    }
    $('#turn').text(msg);
};

BoardController.prototype.playMove = function(id) {
    // user makes move
    var playerTurn = this.model.playMove(id);
    var msg;
    if (playerTurn === -1) {
        msg = "Game over - ";
        if (this.model.score[0] > this.model.score[1]) {
            $('.score').removeClass('winner');
            $('#score0').addClass('winner');
            msg += "bottom player wins!";
        } else if (this.model.score[0] < this.model.score[1]) {
            $('.score').removeClass('winner');
            $('#score1').addClass('winner');
            msg += "top player wins!";
        } else {
            msg += "It's a Tie!";
        }
        msg += "  Start a new game with the buttons above.";
    } else if (playerTurn === 0) {
        msg = "Bottom player's turn";
    } else {
        msg = "Top player's turn";
    }
    // if player vs player
    if (!this.p0ai || !this.p1ai) {
        this.drawBoard(msg, id);
    }
    // AI play automatically
    if (playerTurn === 0 && this.p0ai) {
        this.playMove(this.p0ai.playMove(this.model));
    } else if (playerTurn === 1 && this.p1ai) {
        this.playMove(this.p1ai.playMove(this.model));
    }
};
