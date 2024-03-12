"use strict";

function WrongTurnError(message) {
    this.name = "WrongTurnError";
    this.message = (message || "It's not that player's turn right now.");
}
WrongTurnError.prototype = Error.prototype;

function PlayEmptyPitError(message) {
    this.name = "PlayEmptyPitError";
    this.message = (message || "You cannot play an empty pit.");
}
PlayEmptyPitError.prototype = Error.prototype;

function BoardModel(boardSize=14, stonesPerPit=4) {
    this.boardSize = boardSize;
    this.stonesPerPit = stonesPerPit;
    this.board = [];
    this.score = [0, 0];
    for (var i = 0; i < this.boardSize; i++) {
        this.board[i] = this.stonesPerPit;
    }
    // Bottom player is Player 0, top player is Player 1
    // Bottom player always starts
    this.playerTurn = 0;
}

BoardModel.prototype.isGameOver = function() {
    var offset = this.playerTurn * (this.boardSize / 2);
    for (var i = (0 + offset); i < ((this.boardSize / 2) + offset); i++) {
        if (this.board[i] > 0) return false;
    }
    return true;
};

BoardModel.prototype.playMove = function(pit, proceed = false) {
    var currentSpot, pebble;
    if (this.isGameOver() && !proceed) {
        // may throw exeption at deeper depths, safely ignore
        throw new WrongTurnError("The game is over - no more turns.");
    }
    if (+this.board[pit] === 0) throw new PlayEmptyPitError();

    // ensure pit position does not exceed boardSize
    pit %= this.boardSize;
    currentSpot = +pit;
    pebble = this.board[pit];
    this.board[pit] = 0;
    while (pebble > 0) {
        currentSpot = (currentSpot + 1) % this.boardSize;
        this.board[currentSpot] += 1;
        pebble -= 1;
    }

    // if next spot empty; pebble = 0
    if (this.board[currentSpot + 1] === 0) {
        // skip spot twice
        currentSpot = (currentSpot + 2) % this.boardSize;
        // increase current player's score
        this.score[this.playerTurn] += this.board[currentSpot];
        this.board[currentSpot] = 0;
        // switch player
        this.playerTurn = (this.playerTurn + 1) % 2;
    } else {
        // var proceed is used to prevent following special case
        // case: if initial move results in own board row empty, don't throw WrongTurnError
        var proceed = this.isGameOver();
        this.playMove(currentSpot + 1, proceed);
    }
    // add up remaining seeds when gameover
    if (this.isGameOver()) {
        this.playerTurn = -1;
        for (var i = 0; i < this.boardSize; i++) {
            if (this.board[i] > 0) {
                if (i < this.boardSize / 2) {
                    this.score[0] += this.board[i];
                } else {
                    this.score[1] += this.board[i];
                }
                this.board[i] = 0;
            }
        }
    }
    return this.playerTurn;
};
