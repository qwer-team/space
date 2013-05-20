function initLoading(id){
    grailsEvents.send('initLoading', {segment: id});
}

var grailsEvents = new grails.Events("/gala");
var segments = [];

grailsEvents.on('loadingProgress', 
    function(data){
        segments[data.id].portion += data.portion;
        var percent =(segments[data.id].portion / segments[data.id].len) * 100;
        $(".segment_"+data.id+" .bar .ready").css('width', percent+'%');
    }
); 
grailsEvents.on('startLoading',
    function(data){
        segments[data.id] = {len: data.length, portion: 0};
        var bar = $("<div class='bar' style='width: 100%; height: 15px; background-color: blue;'>"+
            "<div class='ready' style='width: 0%; height: 100%; background-color: red;'></div></div>")
        $(".segment_"+data.id+" .bar").remove();
        $(".segment_"+data.id+" .button").attr("disabled", "disabled");
        $(".segment_"+data.id+" .button").after(bar);
    }
);
    
grailsEvents.on('endLoading',
    function(data){
        $(".segment_"+data.id+" .button").removeAttr("disabled");
        $(".segment_"+data.id+" .bar .ready").css('width', '100%').css('background-color', 'green');
    }
);


