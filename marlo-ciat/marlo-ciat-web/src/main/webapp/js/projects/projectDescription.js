$(document).ready(init);
var countID = 0;

function init() {
  /* Init Select2 plugin */
  $('form select').select2({
    width: "100%"
  });
  
  datePickerConfig({
    "startDate": "#project\\.startDate",
    "endDate": "#project\\.endDate"
});

  // Events
  $(".addFundingSource").on("click", addFundingSource);
  $(".removeFundingSource").on("click", removeFundingSource);
  
  $(".addOutput").on("click", addOutput);
  $(".removeOutput").on("click", removeOutput);
}

/** FUNCTIONS Funding Sources * */

// Add a new funding source element
function addFundingSource() {
  var $list = $(".fundingSourceList");
  var $item = $("#fundingSource-template").clone(true).removeAttr("id");
  $list.append($item);
  $item.show('slow', function() {
    $item.find("select").select2({
      width: "100%"
    });
  });
  checkItems($list);
  updateFundingSource();

}

// Remove Funding source element
function removeFundingSource() {
  var $list = $(this).parents('.fundingSourceList');
  var $item = $(this).parents('.fundingSources');
  $item.hide(1000, function() {
    $item.remove();
    checkItems($list);
    updateFundingSource();
  });

}

function updateFundingSource() {
  $(".fundingSourceList").find('.fundingSources').each(function(i,e) {
    // Set indexes
    $(e).setNameIndexes(1, i);
  });
}

function checkItems(block) {
  console.log(block);
  var items = $(block).find('.fundingSources').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  }
}

/** FUNCTIONS Outputs* */

//Add a new funding source element
function addOutput() {
var $list = $(".outputList");
var $item = $("#output-template").clone(true).removeAttr("id");
$list.append($item);
$item.show('slow');
checkItems($list);
updateFundingSource();

}

//Remove Funding source element
function removeOutput() {
var $list = $(this).parents('.outputList');
var $item = $(this).parents('.output');
$item.hide(1000, function() {
 $item.remove();
 checkOutputItems($list);
 updateOutputs();
});

}

function updateOutputs() {
$(".outputList").find('.output').each(function(i,e) {
 // Set indexes
 $(e).setNameIndexes(1, i);
});
}

function checkOutputItems(block) {
console.log(block);
var items = $(block).find('.output').length;
if(items == 0) {
 $(block).parent().find('p.outputInf').fadeIn();
} else {
 $(block).parent().find('p.outputInf').fadeOut();
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