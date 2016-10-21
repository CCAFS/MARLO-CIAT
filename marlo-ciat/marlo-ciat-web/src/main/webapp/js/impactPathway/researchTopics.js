$(document).ready(init);
var currentSubIdo;
var saveObj;

function init() {

  /* Declaring Events */
  attachEvents();

}

function attachEvents() {

  // Add an Outcome
  $('.addResearchTopic').on('click', addResearchTopic);
  // Remove an Outcome
  $('.removeResearchTopic').on('click', removeResearchTopic);

}

/**
 * Research Topic Functions
 */

function addResearchTopic() {
  var $list = $('.outcomes-list');
  var $item = $('#researchTopic-template').clone(true).removeAttr("id");
  // $item.find('select').select2({
  // width: '100%'
  // });
  $list.append($item);
  updateAllIndexes();
  $item.show('slow');
}

function removeResearchTopic() {
  console.log('asdasd');
  var $list = $(this).parents('.outcomes-list');
  var $item = $(this).parents('.researchTopic');
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
  $('.outcomes-list').find('.researchTopic').each(function(i,outcome) {
    var outcomesName = 'researchTopics' + '[' + i + '].';
    $(outcome).find('span.index').html(i + 1);
    $(outcome).find('.researchTopicInput').attr('name', outcomesName + 'researchTopic');
  });

  // Update component event
  $(document).trigger('updateComponent');

}
