$(document).ready(function(){
    setTimeout(status, 100);
    $('.all').click(function(){
        grailsEvents.send('initLoading', {});
    });
});
var grailsEvents = new grails.Events("/gala");

grailsEvents.on('start', 
    function(data){
        $(".bar").remove();
    }
); 
grailsEvents.on('loadingStatus', 
    function(data){
        console.log(data);
        if(data.inWork){
            $('.button').attr('disabled', 'disabled');
            showProgress(data.progress)
        }
    }
    ); 
grailsEvents.on('loadingProgress', 
    function(data){
        progressDraw(data.id, data.portion);
    }
); 
grailsEvents.on('startLoading',
    function(data){
        console.log('start!!!!');
        alert('ok!');
        startDraw(data.id, data.length)
    }
);
    
grailsEvents.on('endLoading',
    function(data){
        console.log("end: "+data.id);
        endDraw(data.id);
    }
);
    


var segments = [];

function initLoading(id){
    console.log("load seg: "+id);
    grailsEvents.send('initSegment', {
        segment: id
    });
}

function status(){
    grailsEvents.send('getLoadingStatus', {}); 
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
    var percent =(progress / segments[segment].len) * 100;
    $(".segment_"+segment).parent().find(".bar").find(".ready").css('width', percent+'%');
}

function endDraw(segment){
    $(".segment_"+segment+" .button").removeAttr("disabled");
    $(".segment_"+segment).parent().find(".bar").find(".ready").css('width', '100%').css('background-color', 'green');
}

function startDraw(segment, length){
    segments[segment] = { len: length,  portion: 0};
    var bar = $("<div class='bar span4' style='background-color: blue;  height: 10px !important; padding: 0px;'>"+
        "<div class='ready' style='width: 0%; height: 30px; background-color: red;'></div></div>")
    $(".segment_"+segment).parent().find(".bar").remove();
    $(".button").attr("disabled", "disabled");
    $(".segment_"+segment+" .button").parent().after(bar);
}