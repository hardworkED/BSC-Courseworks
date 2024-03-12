"use strict";

/* global BoardModel */

function RandomAI(player) {
    this.player = player;
    this.playMove = function(boardModel) {
        do {
            var move = Math.floor(Math.random() * (boardModel.boardSize / 2));
            move += this.player * (boardModel.boardSize / 2);
        } while (boardModel.board[move] === 0);
        return move;
    };
}

function MinimaxAI(player) {
    this.difficulty = Number($('input[name=difficulty]:checked').val());
    this.player = player;
    this.playMove = function(boardModel, depth=0) {
        // if gameover
        if (boardModel.playerTurn == -1) {
            var scoreSelf = boardModel.score[this.player];
            var scoreOpponent = boardModel.score[(this.player + 1) % 2];
            return((scoreSelf - scoreOpponent) * 100);
        } else if (depth >= this.difficulty) {
            var scoreSelf = boardModel.score[this.player];
            var scoreOpponent = boardModel.score[(this.player + 1) % 2];
            return(scoreSelf - scoreOpponent);
        }
        var move;
        var moveValues = [];
        for (var i = 0; i <= boardModel.boardSize / 2 - 1; i++) {
            move = i + boardModel.playerTurn * (boardModel.boardSize / 2);
            // has pebble in pit
            if (boardModel.board[move] !== 0) {
                var nextBoard = new BoardModel(boardModel.boardSize, boardModel.stonesPerPit);
                nextBoard.playerTurn = boardModel.playerTurn;
                for (var j in boardModel.board) {
                    nextBoard.board[j] = boardModel.board[j];
                }
                nextBoard.playMove(move);
                try {
                    moveValues[i] = this.playMove(nextBoard, depth + 1);
                } catch (err) {
                    console.log(err);
                    console.log(nextBoard);
                    console.log("playing " + move);
                    console.log("depth: " + depth);
                }
            }
        }
        if (depth > 0) {
            if (boardModel.playerTurn == this.player) { // MAX
                return moveValues.reduce((curMax, curValue, curIndex, array) => curValue > curMax ? curValue : curMax);
            } else { // MIN
                return moveValues.reduce((curMin, curValue, curIndex, array) => curValue < curMin ? curValue : curMin);
            }
        }
        move = moveValues.reduce((curMaxIndex, curValue, curIndex, array) => curValue > array[curMaxIndex] || array[curMaxIndex] === undefined ? curIndex : curMaxIndex, 0);
        move += this.player * (boardModel.boardSize / 2);
        return move;
    };
}