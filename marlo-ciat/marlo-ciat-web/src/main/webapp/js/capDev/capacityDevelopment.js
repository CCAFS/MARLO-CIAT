var dialog;
var $dialogContent;
var timeoutID;

$(document).ready(init);

function init(){
  datePickerConfig({
      "startDate": "#capdev\\.startDate",
      "endDate": "#capdev\\.endDate"
  });


  $dialogContent = $("#dialog-searchContactPerson");
  var dialogOptions = {
      autoOpen: false,
      height: 440,
      width: 450,
      modal: true,
      dialogClass: 'dialog-searchUsers',
      open: function(event,ui) {
       
      }
  };

  // Initializing Manage users dialog
  dialog = $dialogContent.dialog(dialogOptions);


  //event search contact person
 $("input.contactPerson").on("click", function(e) {
        e.preventDefault();
        openSearchDialog($(this));
      });

 //close search contact person dialog
 $('.close-dialog').on('click', function() {
        dialog.dialog("close");
      });


 //Event to find an user according to search field
 $dialogContent.find(".searchcontact-content input").on("keyup", searchUsersEvent);

}



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



//display individual participant form
$( ".radioButton" ).change(function() {
 if (this.value == 'individual') {
            $(".individualparticipantForm").show();
            $(".grupsParticipantsForm ").hide();
        }
  else if (this.value == 'gruops') {
            $(".grupsParticipantsForm ").show();
            $(".individualparticipantForm").hide();
        }

});



function datePickerConfig(element) {
  date($(element.startDate), $(element.endDate));
}

function date(start,end) {
  var dateFormat = "yy-mm-dd";
  var from = $(start).datepicker({
      dateFormat: dateFormat,
      minDate: '2010-01-01',
      maxDate: '2030-12-31',
      changeMonth: true,
      numberOfMonths: 1,
      changeYear: true,
      onChangeMonthYear: function(year,month,inst) {
        var selectedDate = new Date(inst.selectedYear, inst.selectedMonth, 1);
        $(this).datepicker('setDate', selectedDate);
        if(selectedDate != "") {
          $(end).datepicker("option", "minDate", selectedDate);
        }
      }
  });

  var to = $(end).datepicker({
      dateFormat: dateFormat,
      minDate: '2010-01-01',
      maxDate: '2030-12-31',
      changeMonth: true,
      numberOfMonths: 1,
      changeYear: true,
      onChangeMonthYear: function(year,month,inst) {
        var selectedDate = new Date(inst.selectedYear, inst.selectedMonth + 1, 0);
        $(this).datepicker('setDate', selectedDate);
        if(selectedDate != "") {
          $(start).datepicker("option", "maxDate", selectedDate);
        }
      }
  });

  function getDate(element) {
    var date;
    try {
      date = $.datepicker.parseDate(dateFormat, element.value);
    } catch(error) {
      date = null;
    }

    return date;
  }
}







 openSearchDialog = function(target) {
        $elementSelected = $(target);
        dialog.dialog("open");
        //$dialogContent.find(".search-loader").fadeOut("slow");
      }


  function searchUsersEvent(e) {
        var query = $(this).val();
        if(query.length > 1) {
          if(timeoutID) {
            clearTimeout(timeoutID);
          }
          // Start a timer that will search when finished
          timeoutID = setTimeout(function() {
            getData(query);
          }, 400);
        } else {
          // getData('');
        }

      }



  function getData(query) {
        $.ajax({
            'url': baseURL + '/searchContact.do',
            'data': {
              q: query
            },
            'dataType': "json",
            beforeSend: function() {
              console.log("estoy haciendo la consulta, calmese!!!");
              $dialogContent.find(".search-loader").show();
              $dialogContent.find(".panel-body ul").empty();
            },
            success: function(data) {
              console.log("ya hice la consulta");
              var usersFound = (data.users).length;
              console.log(usersFound);
              if(usersFound > 0) {
                $dialogContent.find(".panel-body .userMessage").hide();
                $.each(data.users, function(i,user) {
                  var $item = $dialogContent.find("li#userTemplate").clone(true).removeAttr("id");
                  $item.find('.firstname').html(escapeHtml(user.firstName));
                  $item.find('.lastname').html(user.lastName);
                  $item.find('.email').html(user.email);
                  if(i == usersFound - 1) {
                    $item.addClass('last');
                  }
                  $dialogContent.find(".panel-body ul").append($item);
                });
              } else {
                $dialogContent.find(".panel-body .userMessage").show();
              }

            },
            complete: function() {
              $dialogContent.find(".search-loader").fadeOut("slow");
            }
        });

      }