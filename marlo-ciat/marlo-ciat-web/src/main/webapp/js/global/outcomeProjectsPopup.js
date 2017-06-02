var $modal, $modalProjects;
$(document).ready(function() {

  $modal = $('#outcomeProjectsModal');
  $modalProjects = $modal.find("ul.projectsList");

  // Events
  attachEvents();

});

function attachEvents() {

  // This event fires immediately when the show instance method is called.
  $modal.on('show.bs.modal', function(e) {
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
              console.log(project.name);
              var item = "<li> <p> P" + project.id + " - " + project.name + "</p>";

              item += "<ul>"
              // Outputs
              if(project.outputs.length > 0) {
                item += "<li><p class='text-muted'>Associated Outputs</p>"
                item += "<ul>"
                $.each(project.outputs, function(i,output) {
                  item += "<li>  O" + output.id + " - " + output.name + "";
                });
                item += "</ul></li>"
              }

              // Deliverables
              if(project.deliverables.length > 0) {
                item += "<li><p class='text-muted'>Associated Deliverables</p>"
                item += "<ul>"
                $.each(project.deliverables, function(i,deliverable) {
                  item += "<li>  D" + deliverable.id + " - " + deliverable.name + "";
                });
                item += "</ul></li>"
              }

              item += "</ul></li>"

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
          $modalProjects.find('p').on('click', collapseList);
        }
    });

  });

  // Toogle slide

}

function collapseList() {

  console.log('asd');
  $(this).parent().find('ul').slideToggle();
}

function setModalTitle(text) {
  $modal.find(".modal-title").html(text);
}
