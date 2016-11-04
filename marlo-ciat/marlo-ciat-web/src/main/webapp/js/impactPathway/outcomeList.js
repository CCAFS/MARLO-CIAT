$(document).ready(function() {

  var $deliverableList = $('table.outcomeList');

  var table = $deliverableList.DataTable({
      "bPaginate": true, // This option enable the table pagination
      "bLengthChange": true, // This option disables the select table size option
      "bFilter": true, // This option enable the search
      "bSort": true, // this option enable the sort of contents by columns
      "bAutoWidth": false, // This option enables the auto adjust columns width
      "iDisplayLength": 3, // Number of rows to show on the table
      "fnDrawCallback": function() {
        // This function locates the add activity button at left to the filter box
        var table = $(this).parent().find("table");
        if($(table).attr("id") == "currentActivities") {
          $("#currentActivities_filter").prepend($("#addActivity"));
        }
      },
      aoColumnDefs: [
          {
              bSortable: false,
              aTargets: [
                  -1, -2, -3,
              ]
          }, {
              sType: "natural",
              aTargets: [
                0
              ]
          }
      ]
  });
  
  $('form select').select2({
    width: '100%'
  });
  
  $("#researchTopics").on("change",function(){
    var programID=$("#programSelected").html();
    var option = $(this).find("option:selected");
    if(option.val()!="-1"){
      var url=baseURL+"/impactPathway/"+centerSession+"/outcomesList.do?programID="+programID+"&edit="+editable+"&topicID="+option.val();
      window.location = url;
    }
  });
  
});