$(document).ready(init);

function init() {

  // Add Select2
  $('select').select2();

  attachEvents();
}

function attachEvents() {

  // Add a partner
  $('.partnerSelect select').on('change', addPartner);

  // Remove a partner
  $('.removePartner').on('click', removePartner);

}

function addPartner() {
  var $item = $('#partner-template').clone(true).removeAttr('id');
  var $list = $(this).parents('.parntersBlock').find(".partnersList");
  // var year = ($list.parents('.tab-pane').attr('id')).split('-')[1];
  var title = $(this).find('option:selected').text();
  var partnerInstitutionId = $(this).find('option:selected').val();

  // Set the partner parameters
  $item.find('.title').text(title);
  $item.find('.partnerInstitutionId').val(partnerInstitutionId);

  $list.find('.emptyMessage').hide();

  // Add the partner to the list
  $list.append($item);

  // Show the new partner
  $item.show('slow');

  // Remove option from select
  $(this).find('option:selected').remove();
  $(this).trigger("change.select2");

}

function removePartner() {
  var $parent = $(this).parent();
  var $select = $parent.parents('.parntersBlock').find('.partnerSelect select');
  var value = $parent.find('.partnerInstitutionId').val();
  var name = $parent.find('.title').text();

  console.log($parent);

  $parent.hide('slow', function() {
    // Remove partner block
    $parent.remove();

    // Update partners list
    $select.parents('.parntersBlock').find('.partner').each(function(i,e) {
      $(e).find('.index').text(i + 1);
    });

    // Add partner option again to select
    $select.addOption(value, name);
    $select.trigger("change.select2");
  });
}
