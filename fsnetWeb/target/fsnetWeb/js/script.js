$.noConflict();
jQuery(document).ready(function($) {
    
	$('#calendar').fullCalendar({
		
		header:{
			    left:   'today prev,next',
			    center: 'title',
			    right:  'month agendaWeek agendaDay'
			},
		weekMode:"liquid",
		aspectRatio: 2,
		editable: false,
		theme:true,
	    eventRender: function(event, element) {
	        element.qtip({
	            content: event.description,
	            position: {
	                my: 'top right',  // Position my top left...
	                at: 'right center', // at the bottom right of...
	                target: 'mouse' // my target
	             },
	             style: {
	                 classes: 'ui-tooltip-dark ui-tooltip-shadow ui-tooltip-rounded'
	              }
	        });
	    },
	    events: function(start, end, callback) {
	        $.ajax({
	            url: '/fsnetWeb/GenerateJsonEvents.do',
	            data: {
	                start: Math.round(start.getTime() / 1000),
	                end: Math.round(end.getTime() / 1000)
	            },
	            success: function(data, textStatus, jqXHR) {
	            	var e = jQuery.parseJSON(jqXHR.getResponseHeader("X-JSON")); 
	                var events = [];
	                $(e.events).each(function(i) {
	                	var str = (e.events[i]).split(",");
	                	var alldayevent = (str[3] == "false" ? false : true);
		                    events.push({
		                        title: str[0],
		                        start: str[1],
		                        end: str[2],
		                        allDay:alldayevent,
		                        description:str[4]
		                    });

	                });
	                callback(events);
	                
	            }
	        });
	        
	    }
     });

});