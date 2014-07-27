    var box = $( '#focus ul' ),
        first_node = box.find('li:first' ).clone();

    box.find('li:last' ).clone().prependTo( box );
    first_node.appendTo( box );

    var sWidth = $("#focus").width(),
        len = box.find( 'li' ).length,
        index = 1,
        picTimer = 2000,
        isTouch = 0,
        isPlay = 0;

    box.css( {width: (sWidth * len) + 'px', left :  '-' + sWidth + 'px' } );

    /**鼠标移上去时，停止自动播放*/
    $( '#focus, #prev_but, #next_but' ).hover( function(){ isTouch = 1; }, function(){ isTouch = 0; });

    /**上一张*/
    $( '#prev_but' ).click( function(){ play( 0 ); } );

    /**下一张*/
    $( '#next_but' ).click( function(){ play( 1 );} );

    /**播放*/
    function play( direction ) {
        if ( isPlay ) return;
        isPlay = 1;

        var _left;
        if ( direction ) {
            index ++;
            _left = '-=' + sWidth;
        } else {
            index --;
            _left = '+=' + sWidth;
        }

        box.animate( { left : _left }, function () {
            if ( direction && index + 1 >= len ) {
                index = 1;
                box.css( { left :  '-' + sWidth + 'px' } );
            } else
            if ( !direction && index <= 1 ) {
                index = len - 1;
                box.css( { left :  '-' + (sWidth * (len - 2) ) + 'px' } );
            }
            isPlay = 0;
        });
    }
    $(".spe_czg").mouseout(function() {
    	this.className='spe_czg';
    });
    $(".spe_czg").mouseover(function() {
    	this.className='spe_czg_hover';
    });
    $(".yqf_close").click(function() {
    	$(".spe_czg").hide();
    });
    /**自动播放*/
    setInterval( function () { if ( !isTouch && !isPlay ) { play( 1 ); } }, picTimer );
