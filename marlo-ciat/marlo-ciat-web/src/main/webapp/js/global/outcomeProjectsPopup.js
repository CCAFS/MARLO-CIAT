var $modal, $modalProjects;
$(document).ready(function() {

  console.log('outcomeProjectsPopup.js');

  $modal = $('#outcomeProjectsModal');
  $modalProjects = $modal.find("ul.projectsList");

  // This event fires immediately when the show instance method is called.
  $modal.on('show.bs.modal', function(e) {
    console.log(e);
    var outcomeID = $(e.relatedTarget).classParam("outcomeProjects");

    setModalTitle("Associated Projects to the OC" + outcomeID);

    $.ajax({
        url: baseURL + '/outcomeTree.do',
        data: {
          outcomeID: outcomeID
        },
        beforeSend: function() {
          $modal.find(".loading").fadeIn();
          $modalProjects.empty();
        },
        success: function(data) {

          // Projects
          if(data.dataProjects.length > 0) {

            $.each(data.dataProjects, function(i,project) {
              var item = "<li> <p> P" + project.id + " - " + project.name + "</p>";

              item += "<ul>";
              // Outputs
              if(project.outputs.length > 0) {
                item += "<li><p class='text-muted'> Associated Outputs </p>";
                item += "<ul >";
                $.each(project.outputs, function(i,output) {
                  item += "<li>  O" + output.id + " - " + output.name + "";
                });
                item += "</ul></li>";
              }

              // Deliverables
              if(project.deliverables.length > 0) {
                item += "<li><p class='text-muted'> Associated Deliverables </p>";
                item += "<ul>";
                $.each(project.deliverables, function(i,deliverable) {
                  item += "<li>  D" + deliverable.id + " - " + deliverable.name + "";
                });
                item += "</ul></li>";
              }

              item += "</ul></li>";

              // Adding Project Item
              $modalProjects.append(item);
            });

          } else {
            $modalProjects.append("<p>No associated projects to this outcome</p>");
          }
        },
        complete: function() {
          $modal.find(".loading").fadeOut();
          // Setting event to new DOM created
          $modalProjects.find('li p').on('click', collapseList);
        }
    });

  });

});

// Toogle slide
function collapseList() {
  $(this).parent().find('ul').slideToggle();
}

function setModalTitle(text) {
  $modal.find(".modal-title").html(text);
}
