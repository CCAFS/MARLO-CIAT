$(document).ready(init);
var currentSubIdo;
var saveObj;

function init() {

  /* Declaring Events */
  attachEvents();

}

function attachEvents() {

  // Add an Outcome
  $('.addImpact').on('click', addImpact);
  // Remove an Outcome
  $('.removeProgramImpact').on('click', removeProgramImpact);

}

/**
 * Research Topic Functions
 */

function addImpact() {
  var $list = $('.elements-list');
  var $item = $('#programImpact-template').clone(true).removeAttr("id");
  // $item.find('select').select2({
  // width: '100%'
  // });
  $list.append($item);
  updateAllIndexes();
  $item.show('slow');
}

function removeProgramImpact() {
  var $list = $(this).parents('.elements-list');
  var $item = $(this).parents('.programImpact');
  $item.hide(function() {
    $item.remove();
    updateAllIndexes();
  });
}

/**
 * General Function
 */

function updateAllIndexes() {
  // All Outcomes List
  $('.elements-list').find('.programImpact').each(function(i,outcome) {
    $(outcome).find('span.index').html(i + 1);
    $(outcome).setNameIndexes(1, i);
  });

  // Update component event
  $(document).trigger('updateComponent');

}
