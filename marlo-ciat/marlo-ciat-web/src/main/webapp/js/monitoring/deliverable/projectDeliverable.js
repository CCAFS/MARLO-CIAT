$(document).ready(init);
var countID = 0;

function init() {
  /* Init Select2 plugin */
  $('form select').select2({
    width: "100%"
  });

  datePickerConfig({
      "startDate": "#deliverable\\.startDate",
      "endDate": "#deliverable\\.endDate"
  });

  // Events
  $(".addDocument").on("click", addDocument);
  $(".removeDocument").on("click", removeDocument);
  $(".link").on("keyup",checkUrl);
}

/** FUNCTIONS documents * */

function checkUrl() {
  var input = $(this);
  var inputData = $.trim(input.val());
  var uri = new Uri(inputData);
  $(input).removeClass("fieldError");
  if(inputData != "") {
    if(uri.protocol()=="http" || uri.protocol()=="https"){
      $(input).removeClass("fieldError");
    }else{
      $(input).addClass("fieldError");
    }
  }
}

// Add a new document element
function addDocument() {
  var $list = $(".documentList");
  var $item = $("#document-template").clone(true).removeAttr("id");
  $list.append($item);
  $item.show('slow');
  checkItems($list);
  updateDocument();

}

// Remove document element
function removeDocument() {
  var $list = $(this).parents('.documentList');
  var $item = $(this).parents('.documents');
  $item.hide(1000, function() {
    $item.remove();
    checkItems($list);
    updateDocument();
  });

}

function updateDocument() {
  $(".documentList").find('.documents').each(function(i,e) {
    // Set indexes
    $(e).setNameIndexes(1, i);
  });
}

function checkItems(block) {
  var items = $(block).find('.documents').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  }
}

/**
 * Attach to the date fields the datepicker plugin
 */
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