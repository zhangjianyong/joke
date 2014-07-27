	/**
     * 无缝循环播放
     * 使用条件：
     * 一：节点的个数必须大于等于每屏展示的个数
     * 二：节点的高度一至
     */
	function log(v, t, u) {
		if (window.console && console.log) {
			console[t && console[t] ? t : "log"](v);
		}
	}
    var
        picTimer = 3000,
        isTouch = 0,
        isPlay = 0,
        autoDirection = 1,
        showCount = 5,
        index = -showCount,
        stage = $( '#focus' ),
        box = stage.find( 'ul' ),
        source_list = box.find( 'li' ).clone(),
        show_list,
        len = source_list.length,
        nodeGap = 0,
        sWidth = 194 + nodeGap,
        prevDirection,
        prevCount,
        prevBut = $( '#prev_but' ).hide(),
        nextBut = $( '#next_but' ).hide();

    function init() {
        /**显示的个数必须超过节点总数，则将不执行*/
        if ( showCount >= len ) return;

        /**鼠标移上去时，停止自动播放*/
        stage.parent().hover(
            function(){ prevBut.show(); nextBut.show(); isTouch = 1; },
            function(){ prevBut.hide(); nextBut.hide(); isTouch = 0; }
        );

        /**上一张*/
        prevBut.click( function(){ play( 0, showCount ); } );

        /**下一张*/
        nextBut.click( function(){ play( 1, showCount );} );

        /**自动播放*/
        setInterval( function () {
            if ( !isTouch && !isPlay ) {
                play( autoDirection, 1 );
            }
        }, picTimer );
    }

    /**排序组合*/
    function orderList( list, index, count, residue  ) {
        residue = residue || count;

        for ( var i = index; i < len; i++) {
            if ( residue <= 0 ) return list;
            residue --;
            list.push(i);
        }

        if ( residue > 0 )
            return orderList( list, 0, count, residue );
        else
            return list;
    }

    /**
     * 偏移坐标计算
     */
    function offsetIndex( direction, index, count ) {

        if ( direction ) {
            if ( prevDirection == undefined || prevDirection == direction ) {
                index += count;
            }
            if ( prevCount != undefined && prevCount != count ) {
                index = index - count + prevCount;
            }

            if ( index < 0 ) index = 0;
            if ( index > len ) {
                index = index - len;
            }
        } else {
            if ( prevDirection == undefined || prevDirection == direction  ) {
                index -= count;
            }

            if ( prevCount != undefined && prevCount != count ) {
                index = (index - count ) + prevCount;
            }

            if ( index < 0 ) {
                index = len + index;
                if ( index == 1 ) index = count + 1;
            }
            if ( index > len ) index = len;
        }

        return index;
    }

    /**播放*/
    function play( direction, count ) {
        if ( isPlay ) return;

        count = count > showCount ? showCount : count < 1 ? 1 : count;

        /**偏移坐标计算*/
        index = offsetIndex( direction, index,  count );

        /**排序组合*/
        var _list = orderList( [], index, showCount + count );

        /**组合转换成对应的节点*/
        show_list = [];
        for ( var i = 0; i < _list.length; i++ ) {
            show_list.push( source_list.eq( _list[i] ).clone() );
        }
        /**切换坐标计算*/
        var _left = 0,_oldLeft = '-' + ( count * sWidth ) + 'px';
        if ( direction ) {
            _left = ( count * sWidth);
            _oldLeft = 0;
        }

        /**执行动画*/
        isPlay = 1;
        box.css( { width : (sWidth * (showCount + count)) + 'px',  left : _oldLeft } ).html( show_list ).animate( { left : '-' + _left + 'px' }, function () {
            isPlay = 0;
        });

        /**记录执行的动作*/
        prevDirection = direction;
        prevCount = count;
    }

    init();
