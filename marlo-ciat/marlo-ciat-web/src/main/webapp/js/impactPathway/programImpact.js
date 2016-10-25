$(document).ready(init);

function init() {
  /* Declaring Events */
  attachEvents();
}

function attachEvents() {
  // Add a program impact
  $('.addImpact').on('click', addImpact);
  // Remove a program impact
  $('.removeProgramImpact').on('click', removeProgramImpact);
}

/**
 * Research Topic Functions
 */

function addImpact() {
  var $list = $('.elements-list');
  var $item = $('#programImpact-template').clone(true).removeAttr("id");

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
  // All Program Impacts
  $('.elements-list').find('.programImpact').each(function(i,outcome) {
    $(outcome).find('span.index').html(i + 1);
    $(outcome).setNameIndexes(1, i);
  });

  // Update component event
  $(document).trigger('updateComponent');

}
