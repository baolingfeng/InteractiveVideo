var starttime = "";
var intervalId = null;

function initQuestionnaire()
{
	$.ajax({
		type: 'post',
		contentType: "application/json",
		url: '/InteractiveVideo/QuestionnaireSession?type=get',
		success: function(d){
			console.log(d);
			
			var starttime = new Date();
			if(d != "")
			{	
				$(".qdiv").each(function(){
					$(this).removeClass("disable-text");
					$(this).addClass("w3-light-grey");
				});
				
				for(i=1; i<=questionNumber; i++)
				{
					$("input[name=q"+i+"]").prop('disabled', false);
				}
				
				$("input[name=starttime]").val(d);
				starttime = createDateAsUTC(new Date(d));
				
				intervalId = setInterval(function () {
					//console.log(new Date() + " - " + starttime);
					var second = (createDateAsUTC(new Date()) - starttime) / 1000;
					$("#timecounter").html(second);
				}, 1000);
				
				$("#timecounter_span").css("display", "");
				//$("#timecounter").html(0);
				
				$("#controlBtn").val(" 提交 ");
			}
			else
			{
				for(i=1; i<=questionNumber; i++)
				{
					$("input[name=q"+i+"_time]").val('');
				}
			}
		}
	});
}

function beginAnswer(n, videoName, group)
{
	$(".qdiv").each(function(){
		$(this).removeClass("disable-text");
		$(this).addClass("w3-light-grey");
	});
	
	for(i=1; i<=n; i++)
	{
		$("input[name=q"+i+"]").prop('disabled', false);
	}
	
	starttime = createDateAsUTC(new Date());
	$("#timecounter_span").css("display", "");
	$("#timecounter").html(0);
	
	$("input[name=starttime]").val(starttime.toISOString().slice(0, 19));
	
	intervalId = setInterval(function () {
		var second = (createDateAsUTC(new Date()) - starttime) / 1000;
		$("#timecounter").html(second);
	}, 1000);
	
	$("#controlBtn").val(" 提交 ");
	
	$.ajax({
		type: 'post',
		contentType: "application/json",
		url: '/InteractiveVideo/QuestionnaireSession?type=set&starttime='+starttime.toISOString().slice(0, 19),
		success: function(d){
			console.log(d);
			
			window.open("/InteractiveVideo/ViewVideo?name="+videoName+"&group="+group, "_blank");
		}
	});
}

function submitQuestionnaire(n, videoName, group)
{
	var r = confirm("确认要提交问卷么？");
	if (!r)  return;
	
	console.log("submit...");
	answers = {};
	
	answers['starttime'] = $("input[name=starttime]").val();
	answers['endtime'] = createDateAsUTC(new Date()).toISOString().slice(0, 19);
	
	answers['qnumber'] = n;
	answers['email'] = $("input[name=useremail]").val();
	answers['group'] = $("input[name=group]").val();
	answers['video'] = videoName;
	
	var canSubmit = true;
	for(i=1; i<=n; i++)
	{
		var type = $("input[name=q"+i+"]").prop('type');
		if(type == "checkbox" || type == "radio")
		{
			var checkedValues = [];
			$('input[name="q'+ i + '"]:checked').each(function() {
				checkedValues.push(this.value);
			});
			
			if(checkedValues.length > 0)
			{
				answers["q"+i] = checkedValues.join(",");
				answers["q"+i+"_time"] = $("input[name=q"+i+"_time]").val();
				$("span[name=q"+i+"_warning]").addClass("hidden-text");
			}
			else
			{
				canSubmit = false;
				$("span[name=q"+i+"_warning]").removeClass("hidden-text");
			}
		}
		else if(type == "text" || type == "textarea")
		{
			var inputValues = [];
			$("input[name=q"+i+"]").each(function() {
				if(this.value.trim() != "")
				{
					inputValues.push(this.value);
				}
			});
			
			if(inputValues.length > 0)
			{
				answers["q"+i] = inputValues.join(",");
				answers["q"+i+"_time"] = $("input[name=q"+i+"_time]").val();
				$("span[name=q"+i+"_warning]").addClass("hidden-text");
			}
			else
			{
				canSubmit = false;
				$("span[name=q"+i+"_warning]").removeClass("hidden-text");
			}
		}
	}
	console.log(answers);
	
	if(canSubmit)
	{
		$(".overall-warning").addClass("hidden-text");
		$.ajax({
			type: 'post',
			url: '/InteractiveVideo/SubmitQuestionnaire',
			data: {"answers": JSON.stringify(answers)},
			success: function(d){
				console.log(d);
				
				var r = confirm("提交成功");
				
				if(group == "1")
				{
					window.location = "rate.jsp?record=" + d;
				}
				else
				{
					resetAnswer(n, videoName, group);
				}
				
				//closeWindow();
			}
		});
	}
	else
	{
		$(".overall-warning").removeClass("hidden-text");
	}
}

function startAnswer(n, button, videoName, group)
{
	console.log(button);
	
	if(button.trim() == "打开视频并开始答题")
	{
		beginAnswer(n, videoName, group);
		//服务器保存一下开始时间
	}
	else if(button.trim() == "提交")
	{
		submitQuestionnaire(n, videoName, group);
	}
	
}

function closeWindow()
{
	var browserName = navigator.appName;
    var browserVer = parseInt(navigator.appVersion);
    
    if(browserName == "Microsoft Internet Explorer"){
        var ie7 = (document.all && !window.opera && window.XMLHttpRequest) ? true : false;  
        if (ie7)
        {  
          window.open('','_parent','');
          window.close();
        }
       else
        {
          this.focus();
          self.opener = this;
          self.close();
        }
    }
    else{  
       try{
           this.focus();
           self.opener = this;
           self.close();
       }catch(e){}

       try{
           window.open('','_self','');
           window.close();
       }catch(e){ }
   }
}

function resetAnswer(n, videoName, group)
{
	$.ajax({
		type: 'post',
		contentType: "application/json",
		url: '/InteractiveVideo/QuestionnaireSession?type=reset',
		success: function(d){
			console.log(d);
			
			$(".qdiv").each(function(){
				$(this).addClass("disable-text");
				$(this).removeClass("w3-light-grey");
			});
			
			for(i=1; i<=n; i++)
			{
				$("input[name=q"+i+"]").prop('disabled', true);
				
				var type = $("input[name=q"+i+"]").prop('type');
				if(type == "checkbox")
				{
					$("input[name=q"+i+"]").prop('checked', false);
				}
				else if(type == "text")
				{
					$("input[name=q"+i+"]").val('');
				}
				
				$("input[name=q"+i+"_time]").val('');
			}
			
			$("#controlBtn").val(" 打开视频并开始答题 ");
			$("#timecounter_span").css("display", "none");
			$(".overall-warning").addClass("hidden-text");
			
			clearInterval(intervalId);
		}
	});
	
}

function backToTop()
{
	$("html, body").animate({ scrollTop: 0 }, "slow");
}

function createDateAsUTC(date) {
    return new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds()));
}

function convertDateToUTC(date) { 
    return new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(), date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds()); 
}