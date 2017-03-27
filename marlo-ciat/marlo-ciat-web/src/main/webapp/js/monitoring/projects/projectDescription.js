$(document).ready(init);
var countID = 0;

function init() {
  checkOutputsToRemove();
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

  $(".outputSelect").on("change", addOutput);
  $(".removeOutput").on("click", removeOutput);

  $('.blockTitle').on('click', function() {
    if($(this).hasClass('closed')) {
      $(this).parent().find('.blockTitle').removeClass('opened').addClass('closed');
      $(this).removeClass('closed').addClass('opened');
    } else {
      $(this).removeClass('opened').addClass('closed');
    }
    $(this).next().slideToggle('slow', function() {
      $(this).find('textarea').autoGrow();
      $(this).find(".errorTag").hide();
      $(this).find(".errorTag").css("left", $(this).find(".deliverableWrapper").outerWidth());
      $(this).find(".errorTag").fadeIn(2000);
    });
  });
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
  var items = $(block).find('.fundingSources').length;
  if(items == 0) {
    $(block).parent().find('p.inf').fadeIn();
  } else {
    $(block).parent().find('p.inf').fadeOut();
  }
}

/** FUNCTIONS Outputs* */

// Add a new funding source element
function addOutput() {
  var $select = $(this);
  var option = $select.find("option:selected");
  if(option.val() != "-1") {
    $.ajax({
        url: baseURL + "/outputInfo.do",
        type: 'GET',
        data: {
          outputID: option.val()
        },
        success: function(m) {
          console.log(m);
          var $list = $(".outputList");
          var $item = $("#output-template").clone(true).removeAttr("id");
          $item.find("span.index").text("O" + m.outputInfo.id);
          $item.find("div.oStatement").text(option.text());
          $item.find("div.rTopic").text(m.outputInfo.topicName);
          $item.find("div.outcome").text(m.outputInfo.outcomeName);
          $item.find(".outputId").val(m.outputInfo.id);
          $list.append($item);
          $item.show('slow');
          updateOutputs();
          checkOutputItems($list);
          $select.find(option).remove();
          $select.val("-1").trigger("change");
        },
        error: function(e) {
          console.log(e);
        }
    });
  }

}

// Remove Funding source element
function removeOutput() {
  var $list = $(this).parents('.outputList');
  var $item = $(this).parents('.outputs');
  var $select = $(".outputSelect");
  $select.addOption($item.find("input.outputId").val(), $item.find("div.oStatement").text());
  $item.hide(1000, function() {
    $item.remove();
    checkOutputItems($list);
    updateOutputs();
  });

}

function updateOutputs() {
  $(".outputList").find('.outputs').each(function(i,e) {
    // Set indexes
    $(e).setNameIndexes(1, i);
  });
}

function checkOutputItems(block) {
  var items = $(block).find('.outputs').length;
  if(items == 0) {
    $(block).find('p.outputInf').fadeIn();
  } else {
    $(block).find('p.outputInf').fadeOut();
  }
}

function checkOutputsToRemove(){
  $(".outputList").find(".outputs").each(function(i,e){
    var option=$(".outputSelect").find("option[value='"+$(e).find(".outputId").val()+"']");
    option.remove();
  });
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