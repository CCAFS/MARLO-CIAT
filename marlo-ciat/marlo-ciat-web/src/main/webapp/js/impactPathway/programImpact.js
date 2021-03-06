$(document).ready(init);

function init() {
  /* Init Select2 plugin */
  $('form select').select2({
    width: '100%'
  });
  /* Declaring Events */
  attachEvents();

  /* Color picker widget */
  $('form .color-picker').colorPicker();
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
    var option = $(this).find("option:selected");
    var $select = $(this).parents(".beneficiary").find(".focusSelect ");
    var url = baseURL + "/beneficiaryByType.do";
    var data = {
      "beneficiaryTypeID": option.val()
    }
    // remove options
    $select.find("option").each(function(i,e) {
      $(e).remove();
    });
    $.ajax({
        url: url,
        type: 'GET',
        dataType: "json",
        data: data
    }).done(function(m) {
      for(var i = 0; i < m.beneficiaries.length; i++) {
        // Add beneficiary option
        $select.addOption(m.beneficiaries[i].id, m.beneficiaries[i].name);
      }
      $select.trigger("change.select2");
    });
  });

  $(".srfIdoSelect").on("change", function() {
    var option = $(this).find("option:selected");
    if(option.val() == "-1") {
      $(this).parents(".programImpact").find(".otherSrf").find("input").val("");
      $(".otherSrf").show("slow");
    } else {
      $(".otherSrf").hide("slow");
    }
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
