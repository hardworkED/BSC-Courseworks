"use strict";

/* global $, BoardController */


$(document).ready(function(){
    var $rowOne = $('#row1');
    var $rowTwo = $('#row2');
    
    $('button.new-game').on('click', function() {
        var pits = [];
        var td1 = '';
        var td2 = '';
        var $boardSize = Number($('#boardsize').val());
        var $seedSize = Number($('#seedsize').val());

        // setup board
        for (var i = 0; i < $boardSize / 2 ; i++) {
            pits.unshift(($boardSize / 2) + i);
            pits.push(i);
        }
        for (var i = 0; i < $boardSize; i++) {
            if (i < $boardSize / 2) {
                td1 += '<td><button id="' + pits[i] + '" class="pit top-player"></button></td>';
            } else {
                td2 += '<td><button id="' + pits[i] + '" class="pit bottom-player"></button></td>';
            }
        }
        $rowOne.html(td1);
        $rowTwo.html(td2);
        var controller = new BoardController($boardSize, $seedSize);
        $('button.pit').click(function(){
            controller.playMove($(this).attr('id'));
        });

        controller.startGame($(this).data('type'));
    });
});