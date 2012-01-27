$.noConflict();
jQuery(document).ready(function($) {
	
			var defaults = {
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
				                        description:str[4],
				                        url:'/fsnetWeb/DisplayEvent.do?eventId='+str[5]
				                    });

			                });
			                callback(events);
			                
			            }
			        });
			    },
			};

        	var FullCalendarTranslations = {};
        	FullCalendarTranslations['fr'] = {
        			monthNames:
        				['Janvier','Février','Mars','Avril','Mai','Juin','Juillet','Août','Septembre','Octobre','Novembre','Décembre'],
        				monthNamesShort:
        				['janv.','févr.','mars','avr.','mai','juin','juil.','août','sept.','oct.','nov.','déc.'],
        				dayNames: ['Dimanche','Lundi','Mardi','Mercredi','Jeudi','Vendredi','Samedi'],
        				dayNamesShort: ['Dim','Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
        				titleFormat: {
        				 	month: 'MMMM yyyy', // ex : Janvier 2010
        				 	week: "d[ MMMM][ yyyy]{ - d MMMM yyyy}", // ex : 10 — 16 Janvier 2010, 
        				 	day: 'dddd d MMMM yyyy' // ex : Jeudi 14 Janvier 2010
        				},
        				columnFormat: {
        				 	month: 'ddd', // Ven.
        				 	week: 'ddd d', // Ven. 15
        					day: '' // affichage déja complet au niveau du 'titleFormat'
        				},
        				axisFormat: 'H:mm',
        				timeFormat: {
        					'': 'H:mm', // événements vue mensuelle.
        					agenda: 'H:mm{ - H:mm}' // événements vue agenda
        				},
        				firstDay:1, // Lundi premier jour de la semaine
        				buttonText: {
        		            today: 'aujourd\'hui',
        		            day: 'jour',
        		            week:'semaine',
        		            month:'mois'
        		        },
        		        allDayText:'toute la journée',
        	};
        	
        	var fc = $.fullCalendar = {};
        	var setDefaults = fc.setDefaults = function(d) {
        	    $.extend(true, defaults, d);
        	};

        	
        	(function($){
        		  function fullCalendarLocale(locale) {
        			  if (locale == 'fr') return $.extend({}, defaults, FullCalendarTranslations['fr']);
        			  return null;
        		  }

        		  $.fullCalendarLocale = {
              			fr : $.extend({}, defaults, FullCalendarTranslations['fr']),
              			en : defaults
        		  };

        		  $.fn.fullCalendarLocale = fullCalendarLocale;
        		})(jQuery);


});