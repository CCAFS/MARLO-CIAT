// CAPACITY DEVELOPMENT functions


//add Objective
$(".addObjective").on("click", function (){
	var $list = $(".objectivesList");
	var $item = $("#objective-template").clone(true).removeAttr("id");
 	$list.append($item);
 	$item.show('slow', function() {
      	width: "100%"
    });
  
	
	checkItems($list);
	updateFundingSource();

});

// remove Objective
 $(".removeObjective").on("click", function () {
  var $list = $(this).parents('.objectivesList');
  var $item = $(this).parents('.objective');
  $item.hide(1000, function() {
    $item.remove();
    checkItems($list);
    updateFundingSource();
  });

});


// add discipline
$( ".disciplines" ).change(function() {
	console.log("cambio");
  var $list = $(".approachesList");
  var $item = $("#approach-template").clone(true).removeAttr("id");
  $list.append($item);
 	$item.show('slow', function() {
      	width: "50%"
    });

    updateDisciplineList($list);
    
});



//remove discipline 
 $(".removeDiscipline").on("click", function () {
  var $list = $(this).parents('.approachesList');
  var $item = $(this).parents('.approach');
  $item.hide(1000, function() {
    $item.remove();
    updateDisciplineList($list);
    //updateFundingSource();
  });

});


//add outCome
$( ".outComes" ).change(function() {
  var $list = $(".outComesList");
  var $item = $("#outcome-template").clone(true).removeAttr("id");
  $list.append($item);
 	$item.show('slow', function() {
      	width: "50%"
    });

    updateOutComesList($list);
    
});


//remove outCome
$(".removeOutCome").on("click", function () {
  var $list = $(this).parents('.outComesList');
  var $item = $(this).parents('.outcome');
  $item.hide(1000, function() {
    $item.remove();
    updateOutComesList($list);
    //updateFundingSource();
  });

});


function checkItems(block) {
  var items = $(block).find('.objective').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  }
}


function updateFundingSource() {
  $(".objectivesList").find('.objective').each(function(i,e) {
    // Set indexes
    $(e).setNameIndexes(1, i);
  });
}


function updateDisciplineList(block){
	var items = $(block).find('.approach').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  } 
}


function updateOutComesList(block){
	var items = $(block).find('.outcome').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  } 
}
