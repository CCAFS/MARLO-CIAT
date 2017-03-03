$(document).ready(init);
var countID = 0;

function init() {
  /* Init Select2 plugin */
  $('form select').select2({
    width: "100%"
  });

  // Events
  $(".addFundingSource").on("click", addFundingSource);
  $(".removeFundingSource").on("click", removeFundingSource);
}

/** FUNCTIONS * */

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