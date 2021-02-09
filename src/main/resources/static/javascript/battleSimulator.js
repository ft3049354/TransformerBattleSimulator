/**
 * The main javascript for battle simulator
 */

$().ready(function(){
	$.get("/defaultTransformers.txt",function(data){
		$('#transformerCompetitors').val(data);	
	});
    $('#result').hide();
})

function battleStart(){
	$('#result').html();
	var transformerCompetitors = deleteEmptyLine($('#transformerCompetitors').val());
	var competitorsArray = transformerCompetitors.split("\n");
	if(valueCheck(competitorsArray)){
		$.ajax({
			type:"POST",
			url:"/battle",
			dataType:"json",
			data:{  
				competitors: competitorsArray
			},
			success:function(resultJson){
				if(resultJson == undefined || resultJson == null){
					$('#result').html("<p>There is something wrong with the server, please contact the web administrator.</p>");
				}else if(resultJson.errorMsg){
					$('#result').html("<p> Error: " + resultJson.errorMsg + "</p>");
				}else if(resultJson.arenaDestroied){
					$('#result').html("<p>The battle is ended with all competitors destroyed!</p>");
				}else{
					$('#result').html("<p>" + resultJson.totalAmount + " battle </p>");
					if(resultJson.winningTeam == '' || resultJson.winningTeam == null || resultJson.winningTeam == undefined){
						$('#result').append("<p>The two teams get the same score, it is a tied battle.</p>");
					}else{
						var winningTeam;
						var losingTeam;
						if(resultJson.winningTeam == 'A'){
							winningTeam = 'Autobot';
							losingTeam = 'Decepticon';
						}else {
							winningTeam = 'Decepticon';
							losingTeam = 'Autobot';
						}
						$('#result').append("<p>Winning Team(" + winningTeam + "): " + resultJson.winners + "</p>");
						$('#result').append("<p>Survivors from the losing team (" + losingTeam + "): " + resultJson.surviors + "</p>");
					}
				}
				$('#result').show();
			}
		});
	}
}

function valueCheck(competitors){
	var isPassed = true;
	if(competitors == undefined || competitors == null || competitors == '' || competitors.length <= 1){
		alert("Please give at least two transformers to start the battle!");
		return false;
	}

	$.each(competitors, function(index, value){
		var attributes = value.split(",");
		var alertMsg = '';

		if(attributes.length < 10){
			alertMsg = "The transformer No." + (index+1) + " only has " + attributes.length + " attribute(s), please complete all 10 attributes."
			isPassed = false;
		}else if(attributes[1] == undefined || attributes[1] == null || attributes[1] == '' || (attributes[1].trim().toUpperCase() != 'D' && attributes[1].trim().toUpperCase() != 'A')){
			alertMsg += "Please define the team for transformer No." + (index+1) + ". It must be either D for decepticons or A for autobots.";
			isPassed = false;	
		}
		var allAmountIsNumber = attributes.every(function(value, index, array){
			if(index > 2 && (value=='' || isNaN(value))){return false;}else return true;});
		if(!allAmountIsNumber){
			alertMsg += "Please make sure each attribute value is numeric for transformer No." + (index+1) +"."
		}
		var allAmountLessthanTen = attributes.every(function(value, index, array){
			if(index > 2 && value > 10){return false;}else return true;});
		if(!allAmountLessthanTen){
			alertMsg += "Please make sure all attribute amounts is no more than 10 for transformer No." + (index+1) +"."
		}
		if(alertMsg != ''){
			alert(alertMsg);
		}
		});	
	return isPassed;
}
	
function deleteEmptyLine(content){
    var newArr = [];
    var arr = content.split("\n");
    $.each(arr,function(index,value){
        var val = $.trim(value);
        if(val != ''){
			var trimedVal = val.split(",").map(function(el){return el.trim();});
        	newArr.push(trimedVal);
		}
    });
    return newArr.join("\n");
}

