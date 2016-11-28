$(document).ready(init);

function init() {
  /* Init Select2 plugin */
  $('form select').select2({
    width: '100%'
  });
  /* Declaring Events */
  attachEvents();
}

function attachEvents() {
  // Add a program impact
  $('.addImpact').on('click', addImpact);
  // Remove a program impact
  $('.removeProgramImpact').on('click', removeProgramImpact);

// Add a beneficiary
  $('.addBeneficiary').on('click', addBeneficiary);
// Remove a beneficiary
  $('.removeBeneficiary').on('click', removeBeneficiary);

  // When change of beneficiary type
  $(".typeSelect").on("change", function() {
    var url = baseURL + "/beneficiaryByType.do";
    var data = {
      "beneficiaryTypeID": "1"
    }
    $.ajax({
        url: url,
        type: 'GET',
        dataType: "json",
        data: data
    }).done(function(m) {
      console.log(m);
    });
  });
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

    // Update beneficiaries
    $(outcome).find(".beneficiary").each(function(index,beneficiary) {
      $(beneficiary).setNameIndexes(2, index);
    });
  });

  // Update component event
  $(document).trigger('updateComponent');

}

/**
 * beneficiary function
 */
function addBeneficiary() {

  var $list = $(this).parents(".programImpact").find('.beneficiaries-list');
  console.log($list);
  var $item = $('#beneficiary-template').clone(true).removeAttr("id");
  $list.append($item);
  $item.show('slow');
  checkItems($list);
  updateAllIndexes();
}

function removeBeneficiary() {
  var $list = $(this).parents('.beneficiaries-list');
  var $item = $(this).parents('.beneficiary');
  $item.hide(function() {
    $item.remove();
    checkItems($list);
    updateAllIndexes();
  });
}

function checkItems(block) {
  var items = $(block).find('.beneficiary ').length;
  if(items == 0) {
    $(block).parent().find('p.message').fadeIn();
  } else {
    $(block).parent().find('p.message').fadeOut();
  }
}
