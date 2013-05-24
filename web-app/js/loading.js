$(document).ready(function(){
    setTimeout(status, 300);
    $('.all').click(function(){
        grailsEvents.send('initLoading', {});
    });
    $('.button').click(function(){
        var id = $(this).data('id');
        console.log("load seg: "+id);
        grailsEvents.send('initSegment', {
            segment: id
        });
    });

var grailsEvents = new grails.Events("/gala");
grailsEvents.on('start', 
    function(data){
        dasable();
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
        dasable();
        startDraw(data.id, data.length)
    }
);
    
grailsEvents.on('endLoading',
    function(data){
        console.log("end: "+data.id);
        endDraw(data.id);
    }
);
    
grailsEvents.on('endSegment', function(data){
     setTimeout(enebale, 1000);
}); 
grailsEvents.on('end', function(data){
    setTimeout(enebale, 1000);
});

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