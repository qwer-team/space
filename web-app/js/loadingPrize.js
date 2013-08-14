$(document).ready(function(){
    setTimeout(status, 300);
    $('.all').click(function(){
        grailsEvents.send('initPrizeLoading', {});
    });
    $('.button').click(function(){
        var id = $(this).data('id');
        console.log("load seg: "+id);
        grailsEvents.send('initPrizeSegment', {
            segment: id
        });
    });

var grailsEvents = new grails.Events("/gala");
grailsEvents.on('startPrize', 
    function(data){
        dasable();
        $(".bar").remove();
    }
); 
grailsEvents.on('loadingPrizeStatus', 
    function(data){
        console.log(data);
        if(data.inWork){
            $('.button').attr('disabled', 'disabled');
            showProgress(data.progress)
        }
    }
    ); 
grailsEvents.on('loadingPrizeProgress', 
    function(data){
        progressDraw(data.id, data.portion);
    }
); 
grailsEvents.on('startPrizeLoading',
    function(data){
        console.log('start!!!!');
        console.log(data);
        dasable();
        startDraw(data.id, data.length)
    }
);
    
grailsEvents.on('endPrizeLoading',
    function(data){
        console.log("end: "+data.id);
        endDraw(data.id);
    }
);
    
grailsEvents.on('endPrizeSegment', function(data){
     setTimeout(enebale, 1000);
}); 
grailsEvents.on('endPrize', function(data){
    setTimeout(enebale, 1000);
});

function status(){
    grailsEvents.send('getPrizeLoadingStatus', {}); 
}

function showProgress(progress){
    $.each(progress, function(i, v){
        startDraw(i, v.length)
        if(v.portion >= v.length){
            endDraw(i);
        } else{
            progressDraw(i, v.progress)
        }
        
    });
}

function progressDraw(segment, progress){
    if(!(segment in segments)){
        return;
    }
    var percent =(progress / segments[segment].len) * 100;
    $(".segment_"+segment).parent().find(".bar").find(".ready").css('width', percent+'%');
}

function endDraw(segment){
    $(".segment_"+segment).parent().find(".bar").find(".ready").css('width', '100%').css('background-color', 'green');
    segments[segment] = { len: length,  portion: 0};
}
var segments = [];
function startDraw(segment, length){
    segments = [];
    segments[segment] = { len: length,  portion: 0};
    var bar = $("<div class='bar span4' style='background-color: blue;  height: 10px !important; padding: 0px;'>"+
        "<div class='ready' style='width: 0%; height: 30px; background-color: red;'></div></div>")
    $(".segment_"+segment).parent().find(".bar").remove();
    $(".segment_"+segment+" .button").parent().after(bar);
}
function dasable(){
    $(".button, .all").attr("disabled", "disabled");
}
function enebale(){
    $(".button, .all").removeAttr("disabled");
}
});