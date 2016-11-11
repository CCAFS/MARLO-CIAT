$(document).ready(init);
var currentSubIdo;
var saveObj;

function init() {

  /* Declaring Events */
  attachEvents();

  /* Init Select2 plugin */
  $('form select').select2();
  /* Numeric Inputs */
  $('input.targetValue , input.targetYear').numericInput();

  /* Percentage Inputs */
  $('.outcomes-list input.contribution').percentageInput();

  $("#researchTopics").on(
      "change",
      function() {
        var programID = $("#programSelected").html();
        var option = $(this).find("option:selected");
        if(option.val() != "-1") {
          var url =
              baseURL + "/impactPathway/" + centerSession + "/outcomesList.do?programID=" + programID + "&edit="
                  + editable + "&topicID=" + option.val();
          window.location = url;
        }
      });

}

function attachEvents() {

  // Change a target unit
  $('select.targetUnit').on('change', function() {
    var valueId = $(this).val();
    var $targetValue = $(this).parents('.target-block').find('.targetValue-block');
    if(valueId != "-1") {
      $targetValue.show('slow');
    } else {
      $targetValue.hide('slow');
    }

  });

  // Add a Milestone
  $('.addMilestone').on('click', addMilestone);
  // Remove a Milestone
  $('.removeMilestone').on('click', removeMilestone);

  $('input.outcomeYear, input.milestoneYear').on('keyup', function() {
    var $target = $(this);
    var targetVal = parseInt($target.val());
    var $milestonesYearInputs = $(this).parents('.outcome').find('.milestones-list input.targetYear');

    $target.removeClass('fieldError');

    if($target.hasClass('milestoneYear')) {
      var outcomeYearVal = parseInt($(this).parents('.outcome').find('input.outcomeYear').val()) || 0;
      if(targetVal > outcomeYearVal) {
        $target.addClass('fieldError');
      }
    } else {
      $milestonesYearInputs.each(function(i,input) {
        $(input).removeClass('fieldError');
        if(parseInt($(input).val()) > targetVal) {
          $(input).addClass('fieldError');
        }
      });
    }
  });
  $('input.outcomeYear, input.milestoneYear').trigger('keyup');

}

/**
 * Milestone Functions
 */

function addMilestone() {
  var $list = $('.milestones-list');
  var $item = $('#milestone-template').clone(true).removeAttr("id");
  // $item.find('select').select2({
  // width: '100%'
  // });
  $list.append($item);
  updateAllIndexes();
  $item.show('slow');
  // Hide empty message
  $('.milestones-list p.message').hide();
}

function removeMilestone() {
  var $list = $('.milestones-list');
  var $item = $(this).parents('.milestone');
  $item.hide(function() {
    $item.remove();
    updateAllIndexes();
  });
}

/**
 * General Function
 */

function updateAllIndexes() {
  // Update Milestones
  $('form .milestone').each(function(i,milestone) {

    $(milestone).find('span.index').text(i + 1);

    $(milestone).setNameIndexes(1, i)

  });

  // Update component event
  $(document).trigger('updateComponent');

}
