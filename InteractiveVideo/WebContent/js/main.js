var events;

var track;

var fileset = new Array();
var fileEditor = {};

var origin_video_width = 1920;
var origin_video_height = 1080;
var scale = 0.75;
var width = $(document).width();

$(document).ready(function(){
	console.log(width);
	
	$('#v').width(origin_video_width * scale);
	$('#v').height(origin_video_height * scale);
	
	$('#video_content').width(origin_video_width * scale);
	$('#toolbar').width(origin_video_width * scale);
	$('#intro').width(origin_video_width * scale);
	
	$('#popup').popup();
	$('#search').popup();
	$('#dialog').popup();
	
	var v = document.getElementById('v');
	v.addEventListener("loadedmetadata", function() { 
		track = this.addTextTrack("descriptions", "English", "en"); 
		track.mode = "showing";
	});
	
	$.ajax({
		type: 'post',
		contentType: "application/json",
		url: '/InteractiveVideo/GetVideoData?video='+videoName,
		success: function(data){
			events = data;
			console.log(events);
			for(var i=0; i<events.length; i++)
	        {
				$('#tbl_events').append(generateTR(events[i], i));
	        }
			
			generateFileContent();
			
			$('.imgtd').on('click', 'img', function(){
				var eventidx = $(this).parent().parent().attr('eventidx');
				v.currentTime = events[eventidx].interval;
			});
			
			$.contextMenu({
				selector: '.import', 
	            callback: function(key, options) {
	                if(key == "javadoc")
	                {
	                	showJavadoc($(this).text(), '', "false");
	                }
	                else
	                {
	                	
	                }
	                
	            },
	            items: {
	                "javadoc": {name: "View API Documentation"},
	                "stackoverflow": {name: "View Related Posts in StackOverflow"}
	            }
			});
			
			$.contextMenu({
				selector: '.api', 
	            callback: function(key, options) {
	            	var cls = $(this).attr('import');
	            	console.log(cls + '/' + $(this).text());
	            	if(cls == undefined)
	            	{
	            		cls = '';
	            	}
	            	
	                if(key == "javadoc")
	                {
	                	showJavadoc(cls, $(this).text(), "false");
	                }
	                else
	                {
	                	
	                }
	                
	            },
	            items: {
	                "javadoc": {name: "View API Documentation"},
	                "stackoverflow": {name: "View Related Posts in StackOverflow"}
	            }
			});
			
		}
	});
	
	var hIdx = -1;
    var preHIdx = -1;
    v.addEventListener('timeupdate', function(){
        cw = v.clientWidth;
        ch = v.clientHeight;
        
        $('#tbl_events tr').each(function(index){
        	var t = $(this).attr('eventidx');
        	
        	if(v.currentTime < events[t].interval)
        	{
        		hIdx = index - 1;
        		return false;
        	}
       	});
        
        tr = $('#tbl_events tr')[hIdx];
    	while(hIdx >= 0)
    	{
    		if($(tr).find('.imgtd').length > 0) 
    		{
    			break;
    		}
    		
    		hIdx--;
    		tr = $('#tbl_events tr')[hIdx];
    	}
        
        if(hIdx >= 0 && hIdx != preHIdx)
        {
        	highlight(hIdx);
        	
        	//$('#display').scrollTop(tr.offsetTop - 10);
        	
			var eventidx = $(tr).attr('eventidx');
        	updateFiles(eventidx);
        	
        	console.log("indexes:" + hIdx + '/' + preHIdx + "/" + eventidx);
        	
        	removeHighlight(preHIdx);
        	
        	preHIdx = hIdx;
        }
        else if(hIdx < 0 && preHIdx >= 0)
        {
        	removeHighlight(preHIdx)
        }
    },false);
    
    

    $("[name='searchbox']").keypress(function(event) {
    	if (event.which == 13) {
            event.preventDefault();
            console.log('query: ' + this.value);
            
            if(this.value.length <= 2)
            {
            	console.log("the length of query can not be less than 3");
            	return;
            }
            
            search(this.value);
        }
    });
});

function highlight(hIdx)
{
	if(hIdx < 0) return;
	
	tr = $('#tbl_events tr')[hIdx];
	if($(tr).find('.imgtd').length <= 0) return;
	
	$(tr).css('background-color', 'yellow');
	hIdx++;
	tr = $('#tbl_events tr')[hIdx];
	
	while(hIdx < $('#tbl_events tr').length)
	{
		if($(tr).find('.imgtd').length > 0) 
		{
			break;
		}
		
		$(tr).css('background-color', 'yellow');
		hIdx++;
		tr = $('#tbl_events tr')[hIdx];
	}
}

function getExpressionHtml(expr, type)
{
	str = '';
	if(type == 'Import')
	{
		var arr = expr.split(',');
		for(var i=0; i<arr.length; i++)
		{
			str += '<span class="import">' + arr[i] + '</span>';
			str += '<br/>'
		}
	}
	else if(type == 'APICall')
	{
		var arr = expr.split(',');
		for(var i=0; i<arr.length; i++)
		{
			idx1 = arr[i].lastIndexOf('.');
			idx2 = arr[i].indexOf('(');
			idx3 = arr[i].indexOf(')');
			
			if(idx2 > 0)
			{
				str +=  arr[i].substring(0, idx2+1);
				str += '<span class="import">' + arr[i].substring(idx2+1, idx3) + '</span>';
				str += ').<span class="api" import="' + arr[i].substring(idx2+1, idx3) +'">' + arr[i].substring(idx1+1) + '</span>';
				str += '<br/>'
			}
			else{
				var firstChar = arr[i].substring(0, idx1+1)[0];
				if(firstChar >= 'A' && firstChar <= 'Z')
				{
					str += '<span class="import">' + arr[i].substring(0, idx1) + '</span>.';
					str += '<span class="api" import="' + arr[i].substring(0, idx1) + '">' + arr[i].substring(idx1+1) + '</span>';
				}
				else
				{
					str += arr[i].substring(0, idx1+1);
					str += '<span class="api">' + arr[i].substring(idx1+1) + '</span>';
				}
				
				str += '<br/>'
			}
		}
		
	}
	else if(type == 'Field' || type == 'Variable')
	{
		var arr = expr.split(',');
		for(var i=0; i<arr.length; i++)
		{
			idx1 = arr[i].indexOf('(');
			idx2 = arr[i].indexOf(')');
			
			str += arr[i].substring(0, idx1+1);
			str += '<span class="import">' + arr[i].substring(idx1+1, idx2) + '</span>)'
			str += '<br/>'
		}
	}
	else{
		str += '<span class="expression">' + expr + '<span>';
	}
	str += ''
	return str;
}

function generateTR(e, i)
{
	var trclass = i % 2 == 0 ? "treven" : "trodd";
	
	var str = "";
	var cueStr = "";
	if(e.summary.hasOwnProperty('codechanges'))
	{
		var len = e.summary.codechanges.length;
		for(j=0; j<len; j++)
		{
			var operation = e.summary.codechanges[j].operation;
			var type = e.summary.codechanges[j].type;
			var expr = e.summary.codechanges[j].expression;
			
			cueStr += expr + "\n";
			if(j == 0)
			{
				str += '<tr class="' + trclass + '"';
				//str += ' interval=' + e.interval;
				//str += ' timestamp=' + e.timestamp;
				str += ' eventidx=' + i + ">"
				str += '<td class="imgtd" rowspan=' + len + '><img src="images/clock.png"/></td>';
				str += '<td><span class="op' + operation + '">' + operation + '<span></td>';
				str += '<td><span class="' + type.toLowerCase() + 'type">' + type + '<span></td>';
				
				str += '<td>' + getExpressionHtml(expr, type) + '</td>';
				
				str += '<td><span class="file">' + e.summary.codepatch.fileName + '</span></td></tr>';
			}
			else
			{
				str += '<tr class="' + trclass + '"';
				str += ' eventidx=' + i + ">";
				str += '<td><span class="op' + operation + '">' + operation + '<span></td>';
				str += '<td><span class="' + type.toLowerCase() + 'type">' + type + '<span></td>';
				str += '<td>' + getExpressionHtml(expr, type) + '</td>';
				str += '<td><span class="file">' + e.summary.codepatch.fileName + '</span></td></tr>';
			}
		}
	}
	else if(e.summary.hasOwnProperty('codepatch'))
	{
		str += '<tr class="' + trclass + '"';
		str += ' eventidx=' + i + ">"
		str += '<td class="imgtd"><img src="images/clock.png"/></td>';
		str += '<td colspan="4">Open Source Code File <span class="file">' + e.summary.codepatch.fileName + '</span></td></tr>'
		
		if(fileset.indexOf(e.summary.codepatch.fileName) < 0){
			fileset.push(e.summary.codepatch.fileName);
		}
	}
	else if(e.summary.hasOwnProperty('normalfile'))
	{
		if(!e.summary.hasOwnProperty('normalfilediff'))
		{
			str += '<tr class="' + trclass + '"';
			str += ' eventidx=' + i + ">"
			str += '<td class="imgtd"><img src="images/clock.png"/></td>';
			str += '<td colspan="4">Open File <span class="file">' + e.summary.normalfile + '</span></td></tr>'
		}
		else{
			str += '<tr class="' + trclass + '"';
			str += ' eventidx=' + i + ">"
			str += '<td class="imgtd"><img src="images/clock.png"/></td>';
			str += '<td colspan="4">Edit File <span class="file">' + e.summary.normalfile + '</span>'; 
			str += '(<a href="#" onclick=openFileDiff("' + e.summary.normalfile + '",' + i + ')>see edit detail</a>)'
			str += '<div id="' + e.summary.normalfile + '_' + i + '_div" class="hiddendiv">' + e.summary.normalfilediff + '</div></td></tr>'
		}
		
		if(fileset.indexOf(e.summary.normalfile) < 0){
			fileset.push(e.summary.normalfile);
		}
	}
	else if(e.summary.hasOwnProperty('console'))
	{
		str += '<tr class="' + trclass + '"';
		str += ' eventidx=' + i + ">"
		str += '<td class="imgtd"><img src="images/clock.png"/></td>';
		str += '<td colspan="2">Exception</td><td colspan="2">' + e.summary.console + '</td></tr>'
	}
	
	//console.log(cueStr);
	var cue = new VTTCue(e.interval, e.interval + 3, cueStr);
	cue.line = 1;
	track.addCue(cue); 
	
	return str;
}

function openFileDiff(fileName, i)
{
	$('#dialog').empty();
	
	var diff = $('#'+fileName+'_'+i+'_div').html();
	diff = diff.replace(new RegExp('\\\\n', 'gm'), '<br/>');
	diff = diff.replace(new RegExp('\\\\t', 'gm'), '&nbsp;&nbsp;&nbsp;&nbsp;');
	
//	$('#dialog').append('<textarea id="' + fileName + '_content">' + diff +'</textarea>')
	
//	var editor = CodeMirror.fromTextArea(document.getElementById(fileName + '_content'), {
//	     lineNumbers: true,
//	     matchBrackets: true,
//	     readOnly: true,
//	     mode: "text/html"
//	});
	
	//editor.setValue(diff);
	$('#dialog').css('width', '800px');
	$('#dialog').css('height', '100%');
	$('#dialog').html(diff);
	
	$('#dialog').popup('show');
	
}

function generateFileContent()
{
	for(var i=0; i<fileset.length; i++)
	{
		//var s = fileset[i].replace('.', '_');
		$('#tabnames').append('<li><a href="#' + fileset[i] + '">' + fileset[i] + '</a></li>')
		
		$('#filetabs').append('<div id="' + fileset[i] + '"><textarea id="' + fileset[i] + '_content"></textarea></div>')
	
		var editor = CodeMirror.fromTextArea(document.getElementById(fileset[i] + '_content'), {
	     lineNumbers: true,
	     matchBrackets: true,
	     readOnly: true,
	     mode: "text/x-java"
	    });
		
		editor.on('keypress', function(cm, keyEvent){
			var selectedText = cm.getSelection().trim();
			if(selectedText == "" || keyEvent.key != ' ') return;
			
			console.log("selected text: " + selectedText);
			showJavadoc(selectedText, '', 'true');
		});
		
		editor.setSize(900, 1000);
		
		fileEditor[fileset[i]] = editor;
	}
	
	$('#filetabs').tabs();
}

function getFileName(e)
{
	var fileName = "";
	if(e.summary.hasOwnProperty('normalfile'))
	{
		fileName = e.summary.normalfile;
	}
	else if(e.summary.hasOwnProperty('codepatch'))
	{
		fileName = e.summary.codepatch.fileName;
	}
	
	return fileName;
}

function getFileContent(e)
{
	var actionValue = e.summary.action.actionValue;
	actionValue = actionValue.replace(new RegExp('\\\\n', 'gm'), '\n');
	actionValue = actionValue.replace(new RegExp('\\\\t', 'gm'), '\t');
	
	return actionValue;
}

function updateFiles(eventidx)
{
	var fileName = getFileName(events[eventidx]);
	
	console.log("current file: " + fileName);
	if(fileName != "")
	{
		fileEditor[fileName].setValue(getFileContent(events[eventidx]));
		setTimeout(function() {
			fileEditor[fileName].refresh();
		},1);
		
		if($('#filetabs').css('display') == 'none')
		{
			$('#filetabs').css('display', '');
		}
		
		var index = $('#filetabs a[href="#' + fileName + '"]').parent().index();
		console.log("active tab:" + index);
		$('#filetabs').tabs("option", "active", index);
	}
	
	for(var i=0; i<fileset.length; i++)
	{
		if(fileset[i] == fileName) continue;
		
		var t = eventidx - 1;
		while(t >= 0)
		{
			var f2 = getFileName(events[t]);
			if(f2 == fileset[i])
			{
				//f2 = f2.replace('.', '_');
				fileEditor[f2].setValue(getFileContent(events[t]));
				
				setTimeout(function() {
					fileEditor[f2].refresh();
				},1);
				
				break;
			}
			
			t--;
		}
	}
	
	$.contextMenu({
		selector: '.cm-variable', 
        callback: function(key, options) {
        },
        items: {
            "javadoc": {name: "View API Documentation"},
        }
	});
}

function search(query)
{
	query = query.toLowerCase();
	res = new Array();
	for(i=0; i<events.length; i++)
	{
		e = events[i];
		if(e.summary.hasOwnProperty('codechanges'))
		{
			var len = e.summary.codechanges.length;
			for(j=0; j<len; j++)
			{
				var operation = e.summary.codechanges[j].operation;
				var type = e.summary.codechanges[j].type;
				var expr = e.summary.codechanges[j].expression;
				
				arr = expr.split(',');
				for(k=0; k<arr.length; k++)
				{
					if(arr[k].toLowerCase().indexOf(query) >= 0)
					{
						//console.log("find: " + arr[k]);
						var expr = '<span class="op' + operation + '">' + operation + '</span>&nbsp&nbsp';
						expr += '<span class="' + type.toLowerCase() + 'type">' + type + '</span>&nbsp&nbsp';
						expr += '<span>' + getExpressionHtml(arr[k], type) + '</span>';
						res.push({'interval': e.interval, 'expr': expr});
					}
				}
			}
		}
	}
	
	if(res.length <= 0) return;
	
	$('#search_result').html('');
	
	$('#search_result').append('<ul id="result_list">');
	for(i=0; i<res.length; i++){
		var minute = Math.floor(res[i].interval / 60);
		var second = formatNumberLength(Math.floor(res[i].interval - minute * 60), 2);
		
		var listr = minute + ":" + second + "-->" + res[i].expr;
		
		$('#result_list').append('<li class="ui-widget-content" interval="' + res[i].interval +'">' + listr + '</li>');
	}
	$('#search_result').append('</ul>');
	
	$('#search').popup('show');
	
	var pos = $('[name=searchbox]').position();
	$("#search").css({top: pos.top, left: pos.left, position:'absolute'});
	$('#search_result').css('width', '100%');
	$('#search_result').css('height', '100%');
	
	$("#result_list").selectable({
	      stop: function() {
	    	  $( ".ui-selected", this).each(function() {
	    		  var index = $( "#result_list li" ).index( this );
	    		  var li = $( "#result_list li" )[index];
	    		  
				  v.currentTime = $(li).attr("interval");
	    		  
	    		  $('#search').popup('hide');
	          });
	       }
	});
	
	
}

var api_javadocs = [];
function showJavadoc(clsName, method, flag){
	$('#popup').popup('show');
	
	$('#APIHelp').html('loading...');
	$('#APIHelp').css('width', '300px');
	$('#APIHelp').css('height', '300px');
	
	$.ajax({
		type: 'post',
		url: '/InteractiveVideo/GetJavadoc',
		data: {
			'class': clsName,
			'method': method,
			'flag':flag
		},
		success: function(data){
			api_javadocs = [];
			
			$('#APIHelp').html('');
			$('#APIHelp').css('width', '800px');
			$('#APIHelp').css('height', '540px');
			$('#APIHelp').css('overflow-y', 'scroll');
			$('#APIHelp').css('background-color', 'rgb(242, 242, 192)');
			
			$('#APIHelp').append('<ul id="api_list">');
			for(i=0; i<data.length; i++){
				api_javadocs.push(data[i].javadoc);
				
				if(data[i].methodName == ''){
					$('#api_list').append('<li class="ui-widget-content">' + data[i].className + '</li>');
				}
				else{
					$('#api_list').append('<li class="ui-widget-content">' + data[i].className + '.' + data[i].methodName + '(' + data[i].methodParams + ')</li>');
				}
			}
			$('#APIHelp').append('</ul>');
			
			$('#APIHelp').append('<p id="api_result">' + api_javadocs[0] + '</p>');
			
			$("#api_list").selectable({
			      stop: function() {
			          var result = $("#api_result").empty();
			          $( ".ui-selected", this).each(function() {
				            var index = $( "#api_list li" ).index( this );
				            result.html( api_javadocs[index] );
			          });
			        }
			});

			$('#api_list li:first').addClass('ui-selected');
			//$('#APIHelp').html(data[0].javadoc);
		}
	});
}

function removeHighlight(hIdx)
{
	if(hIdx < 0) return;
	
	tr = $('#tbl_events tr')[hIdx];
	if($(tr).find('.imgtd').length <= 0) return;
	
	$(tr).removeAttr("style");
	hIdx++;
	tr = $('#tbl_events tr')[hIdx];
	
	while(hIdx < $('#tbl_events tr').length)
	{
		if($(tr).find('.imgtd').length > 0) 
		{
			break;
		}
		
		$(tr).removeAttr("style");
		hIdx++;
		tr = $('#tbl_events tr')[hIdx];
	}
}

function openNav() {
	$('#mySidenav').width('960px');
	$('#main').css('margin-right', '960px');
	
	var vw = $('#v').width();
	var vh = $('#v').height();
	
	$('#v').width(origin_video_width * 0.5);
	$('#v').height(origin_video_height * 0.5);
}

/* Set the width of the side navigation to 0 and the left margin of the page content to 0, and the background color of body to white */
function closeNav() {
	$('#mySidenav').width('0');
	$('#main').css('margin-right', '0');
	
	$('#v').width(origin_video_width * scale);
	$('#v').height(origin_video_height * scale);
}

function openNav2() {
	$('#mySidenav2').width('960px');
	$('#main').css('margin-right', '960px');
	
	var vw = $('#v').width();
	var vh = $('#v').height();
	
	$('#v').width(origin_video_width * 0.5);
	$('#v').height(origin_video_height * 0.5);
}

function closeNav2() {
	$('#mySidenav2').width('0');
	$('#main').css('margin-right', '0');
	
	$('#v').width(origin_video_width * scale);
	$('#v').height(origin_video_height * scale);
}

function formatNumberLength(num, length) {
    var r = "" + num;
    while (r.length < length) {
        r = "0" + r;
    }
    return r;
}