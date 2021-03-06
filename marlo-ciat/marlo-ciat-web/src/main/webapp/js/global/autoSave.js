var timeoutAutoSave;
var $draftTag, $editedBy, $cancelButton;
var notification;

$(document).ready(function() {

  $draftTag = $('[name="save"]').find('.draft');
  $editedBy = $('#lastUpdateMessage');
  $cancelButton = $('.button-cancel');

  /* Event triggers */
  $(document).on('updateComponent', changeDetected);
  $('form:first :input').on('keyup change', changeDetected);

  if($('#generalMessages ul.messages li').exists()) {
    // Validate section
    validateThisSection();
  }

});

function autoSave() {
  $.ajax({
      dataType: 'json',
      method: 'POST',
      url: baseURL + '/autosaveWriter.do',
      data: {
        autoSave: JSON.stringify($('form:first').serializeObject())
      },
      beforeSend: function(xhr,opts) {
        if(autoSaveActive) {
          $draftTag.text('... Saving');
        } else {
          // Auto save Cancelled
          xhr.abort();
        }
      },
      success: function(data) {
        if(data.status.status) {

          successNotification('Draft saved...');
          // $draftTag.text('(Draft Version)').addClass('animated flipInX');
          // $cancelButton.css('display', 'inline-block');
          $editedBy.find('.datetime').text(data.status.activeSince);
          $editedBy.find('.modifiedBy').text(data.status.modifiedBy);

          draft = true;

          // Validate section
          validateThisSection();

          // Send push for saving
          pushSave();

        } else {
          errorNotification('Auto save error' + data.status.statusMessage);
        }
      },
      complete: function() {
      },
      error: function(e) {
        errorNotification('Auto save ' + e.statusText);
      }
  });
}

function successNotification(msj) {
  var notyOptions = jQuery.extend({}, notyDefaultOptions);
  notyOptions.text = msj;
  notyOptions.type = 'info';
  notyOptions.layout = 'topCenter';
  notyOptions.animation = {
      open: 'animated fadeInDown',
      close: 'animated fadeOutUp'
  };
  notification = noty(notyOptions);
}

function errorNotification(msj) {
  var notyOptions = jQuery.extend({}, notyDefaultOptions);
  notyOptions.text = msj;
  notyOptions.type = 'error';
  notyOptions.layout = 'topCenter';
  notyOptions.animation = {
      open: 'animated fadeInDown',
      close: 'animated fadeOutUp'
  };
  notification = noty(notyOptions);
}

function changeDetected(e) {
  if(isChanged()) {
    // Hide concurrence message
    $('#concurrenceMessage').fadeOut();

    if(autoSaveActive) {
      if(timeoutAutoSave) {
        clearTimeout(timeoutAutoSave);
      }
      // Start a timer that will search when finished
      timeoutAutoSave = setTimeout(function() {
        autoSave();
      }, 7 * 1000);
    }
  }
}

function validateThisSection() {
  var $sectionMenu = $('#secondaryMenu .currentSection');
  if(!$sectionMenu.exists()) {
    return;
  }

  var sectionName = ($sectionMenu.attr('id')).split("-")[1];

  var validateService = "";
  var sectionData = {};
  sectionData.sectionName = sectionName;

  // Validate impact pathway
  if(isImpactPathwaySection()) {
    sectionData.programID = $('input[name="programID"]').val();
    validateService = "/validateImpactPathway.do";
  }

  // Validate monitoring
  if(isMonitoringSection()) {
    sectionData.projectID = $('input[name="projectID"]').val();
    validateService = "/validateProject.do";
  }

  $.ajax({
      url: baseURL + validateService,
      data: sectionData,
      beforeSend: function() {
        $sectionMenu.removeClass('animated flipInX').addClass('loadingSection');
      },
      success: function(data) {
        // Process Ajax results here
        if(jQuery.isEmptyObject(data)) {
          $sectionMenu.removeClass('submitted');
        } else {
          if(data.section.missingFields == "") {
            $sectionMenu.addClass('submitted').removeClass('toSubmit');
          } else {
            $sectionMenu.removeClass('submitted').addClass('toSubmit');

          }
        }
        $sectionMenu.removeClass('loadingSection');
      },
      complete: function(data) {
        $sectionMenu.addClass('animated flipInX');
      },
      error: function(error) {
        console.log(error)
      }
  });
}
