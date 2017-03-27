var dialog, notyDialog;
var $elementSelected, $dialogContent;

$(document).ready(function() {
  /** Initialize */
  $dialogContent = $("#dialog-searchProjects");
  var dialogOptions = {
      autoOpen: false,
      width: 550,
      modal: true,
      dialogClass: 'dialog-searchUsers',
      open: function(event,ui) {
      }
  };

  // Initializing Manage users dialog
  dialog = $dialogContent.dialog(dialogOptions);

  // Loading initial data with all users
  // getData('');

  /** Events */

  // Change target unit value
  $(".tagetUnitPopup").on("change", function() {
    var option = $(this).find("option:selected");
    if(option.val() != "-1") {
      $(".targetValuePopup").parent().parent().show("slow");
    } else {
      $(".targetValuePopup").parent().parent().hide("slow");
    }
  });

  $('.close-dialog').on('click', function() {
    dialog.dialog("close");
  });

  // Event to open dialog box and search an contact person
  $(".addMilestone").on("click", function(e) {
    e.preventDefault();
    openSearchDialog($(this));
  });

  // Event to Create a funding source clicking in the button
  $dialogContent.find(".create-button").on("click", function() {
    $dialogContent.find('.warning-info').empty().hide();
    var targetUnit = "-1";
    var targetValue = null;
    if($(".tagetUnitPopup").find("option:selected").val() != "-1") {
      targetUnit = $(".tagetUnitPopup").find("option:selected").val();
      targetValue = $(".targetValuePopup").val();
    }
    var data = {
        title: $(".statementPopup").val(),
        targetUnit: targetUnit,
        value: targetValue,
        year: $(".targetYearPopup").find("option:selected").val(),
        outcomeID: $("input[name='outcomeID']").val()
    }
    $.ajax({
        'url': baseURL + '/addMilestone.do',
        method: 'POST',
        data: data,
        beforeSend: function() {
          $dialogContent.find('.loading').show();
        },
        success: function(response) {
          if(response.status == "OK") {
            console.log(response);
          } else {
            $dialogContent.find('.warning-info').text(response.message).fadeIn('slow');
          }
        },
        complete: function(response) {
          $dialogContent.find('.loading').fadeOut();
        }
    });

  });

  $dialogContent.find("form").on("submit", function(e) {
    event.preventDefault();
  });

  /** Functions * */

  openSearchDialog = function(selected) {

    $elementSelected = $(selected);
    selectedPartnerTitle = $elementSelected.parents('.projectPartner').find('.partnerTitle').text();
    $dialogContent.find('.cgiarCenter').text(selectedPartnerTitle);
    institutionSelected = $elementSelected.parents('.projectPartner').find('.partnerInstitutionId').text();
    selectedYear = $elementSelected.parents('.tab-pane').attr('id').split('-')[1];

    dialog.dialog("open");

  }

  addProject = function(fundingSource) {
    dialog.dialog("close");
  }

  addUserMessage = function(message) {
    $elementSelected.parent().find('.username-message').remove();
    $elementSelected.after("<p class='username-message note animated flipInX'>" + message + "</p>");
  }

});// End document ready event

