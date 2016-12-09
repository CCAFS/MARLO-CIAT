$(function() { // on dom ready

  var url = baseURL + "/impactPathwayGraphByProgram.do";
  var graphicContent = "mini-graphic";
  var panningEnable = false;
  var crpProgram = $("input[name='programID']").val();

  if(crpProgram != "" && crpProgram != null) {
    data = {
      programID: crpProgram
    };

    ajaxService(url, data, graphicContent, panningEnable, false, 'breadthfirst', false);
  }
  // function to create a new graph

});

function createGraphic(json,graphicContent,panningEnable,inPopUp,nameLayout,tooltip) {
  var crps;
  var flagships;
  var outcomes;
  var clusters;
  var keyOutputs;
  cy = cytoscape({
      container: document.getElementById(graphicContent),

      boxSelectionEnabled: false,
      autounselectify: true,
      minZoom: 0.1,
      maxZoom: 5.5,
      zoomingEnabled: true,
      userZoomingEnabled: true,
      userPanningEnabled: panningEnable,

      style: cytoscape.stylesheet().selector('node').css({
          'shape': 'roundrectangle',
          'height': 30,
          'width': 110,
          'background-fit': 'cover',
          'border-width': 0.7,
          'border-opacity': 0.7,
          'label': 'data(label)',
          'background-color': '#2388ae',
          'color': 'white',
          'text-outline-width': 1,
          'text-outline-color': '#888',
          'z-index': '5',
          'padding': 2
      }).selector('.eating').css({
          'border-width': 2,
          'background-color': '#163799'
      }).selector('.eater').css({
          'border-width': 9,
          'color': 'white'
      }).selector('edge').css({
          'width': 2,
          'source-arrow-shape': 'triangle',
          'line-color': '#eee',
          'source-arrow-color': '#eee',
          'curve-style': 'bezier',
          'z-index': '1'
      }).selector('.center-center').css({
          'text-valign': 'center',
          'text-halign': 'center'
      }).selector('.bottom-center').css({
          'text-valign': 'bottom',
          'text-halign': 'center'
      }),

      elements: json,

      layout: {
        name: 'preset'
      }
  });

// cy init

  // Nodes init
  var nodesInit = cy.$('node');
  var colorFlagship;
  nodesInit.addClass('center-center');
  nodesInit.forEach(function(ele) {
    ele.css('background-color', ele.data('color'));
    if(ele.data('type') === 'F') {
      colorFlagship = ele.data('color');
    }

    // NODES WITH CHILDRENS
    if(ele.data('type') === 'A') {
      ele.css({
          'shape': 'rectangle',
          'color': '#884809',
          'text-outline-width': 0
      });
      if(ele.children().length > 0) {
        ele.css({

        });
        ele.addClass('bottom-center');
      }
    }

    if(ele.data('type') === 'P') {
      ele.css({
          'shape': 'rectangle',
          'color': '#884809',
          'text-outline-width': 0
      });
      if(ele.children().length > 0) {
        ele.css({

        });
        ele.addClass('bottom-center');
      }
    }
    if(ele.data('type') === 'T') {
      ele.css({
          'shape': 'rectangle',
          'color': '#884809',
          'text-outline-width': 0
      });
      if(ele.children().length > 0) {
        ele.css({

        });
        ele.addClass('bottom-center');
      }
    }

  });

  if(inPopUp === true) {
    /*
     * cy.panzoom({ // options here... }); $(".cy-panzoom").css('position', 'absolute'); $(".cy-panzoom").css("right",
     * '10%'); $(".cy-panzoom").css('top', '17%'); $(".cy-panzoom").css('z-index', '99');
     */
  }

// tap a node
  cy.on('tap', function(event) {

    cy.$('node').css('background-opacity', '0.4');
    cy.$('node').css('text-opacity', '0.4');
    cy.$('edge').css('line-opacity', '0.4');
    cy.$('edge').css('line-color', '#eee');
    cy.$('edge').css('source-arrow-color', '#eee');
    cy.$('edge').css('target-arrow-color', '#eee');
    cy.$('edge').css('z-index', '1');
    $(".panel-body ul").empty();
    crps = [];
    flagships = [];
    outcomes = [];
    clusters = [];
    keyOutputs = [];

    if(event.cyTarget == cy) {

      cy.$('node').removeClass('eating');
      cy.$('node').css('background-opacity', '1');
      cy.$('node').css('text-opacity', '1');

    } else if(event.cyTarget.isEdge()) {

      cy.$('node').removeClass('eating');
      cy.$('node').css('background-opacity', '1');
      cy.$('node').css('text-opacity', '1');
      cy.$('edge').css('line-color', '#999999');
      cy.$('edge').css('source-arrow-color', '#999999');
      cy.$('edge').css('target-arrow-color', '#999999');

    } else if(event.cyTarget.isNode()) {

      cy.$('node').removeClass('eating');
      var $this = event.cyTarget;

      // IF NODE HAS CHILDRENS
      if($this.isParent()) {
        var childrens = $this.children();
        childrens.forEach(function(ele) {
          nodeSelected(ele);
          ele.predecessors().forEach(function(ele1) {
            nodeSelected(ele1);
          });
        });
      }
      // IF NODE HAS PARENT
      if($this.isChild()) {
        var parent = $this.parent();
        nodeSelected(parent);
      }

      var successors = $this.successors();
      var predecessors = $this.predecessors();

      predecessors.forEach(function(ele) {
        nodeSelected(ele);
      });
      nodeSelected($this);
      successors.forEach(function(ele) {
        nodeSelected(ele);
      });

      if(inPopUp === true) {
        // add info in Relations panel
        crps.forEach(function(ele) {
          $(".panel-body ul").append("<label>CRP:</label><li>" + ele + "</li>")
        });
        flagships.forEach(function(ele) {
          $(".panel-body ul").append("<label>" + ele[1] + ":</label><li>" + ele[0] + "</li>")
        });
        outcomes.forEach(function(ele) {
          $(".panel-body ul").append("<label>" + ele[1] + ":</label><li>" + ele[0] + "</li>")
        });
        clusters.forEach(function(ele) {
          $(".panel-body ul").append("<label>" + ele[1] + ":</label><li>" + ele[0] + "</li>")
        });
        keyOutputs.forEach(function(ele) {
          $(".panel-body ul").append("<label>" + ele[1] + ":</label><li>" + ele[0] + "</li>")
        });
      }
    }
  });
  function nodeSelected(ele) {
    var stop;
    if(ele.isChild()) {
      var parent = ele.parent();
      nodeSelected(parent);
    }

    // change Styles
    ele.addClass('eating');
    ele.css('background-opacity', '1');
    ele.css('text-opacity', '1');
    ele.css('z-index', '9');
    ele.css('line-color', '#999999');
    ele.css('source-arrow-color', '#999999');
    ele.css('target-arrow-color', '#999999');

    // Validate if the node exists in any array

    // In flagships array
    flagships.forEach(function(array) {
      if(ele.data('description') === array[0]) {
        console.log("asd");
        stop = 1;
      }
    });

    // In Outcomes array
    outcomes.forEach(function(array) {
      if(ele.data('description') === array[0]) {
        console.log("asd");
        stop = 1;
      }
    });

    // In Outcomes array
    clusters.forEach(function(array) {
      if(ele.data('description') === array[0]) {
        console.log("asd");
        stop = 1;
      }
    });

    // In Outcomes array
    keyOutputs.forEach(function(array) {
      if(ele.data('description') === array[0]) {
        console.log("asd");
        stop = 1;
      }
    });

    // Break nodeSelected function
    if(stop == 1) {
      return;
    }

    // arrays information
    if(ele.data('description') != 'undefined' && ele.data('description') != null) {
      var data = [];
      if(ele.data('type') === 'C') {
        crps.push(ele.data('description'));
      } else if(ele.data('type') === 'F') {
        data.push(ele.data('description'));
        data.push(ele.data('label'));
        flagships.push(data);
      } else if(ele.data('type') === 'O') {
        data.push(ele.data('description'));
        data.push(ele.data('label'));
        outcomes.push(data);
      } else if(ele.data('type') === 'CoA') {
        data.push(ele.data('description'));
        data.push(ele.data('label'));
        clusters.push(data);
      } else if(ele.data('type') === 'KO') {
        data.push(ele.data('description'));
        data.push(ele.data('label'));
        keyOutputs.push(data);
      }
    }

  }

  // Download

  $("#buttonDownload").on("click", function() {
    var image = new Image();
    image = cy.jpg();
    var name = "impactPathway_Graphic";
    $('a.download').attr({
        href: image,
        download: name
    })
  });

  // tooltip
  if(tooltip) {
    cy.$("node").qtip({
        content: function() {
          var desc = "";
          if(this.data('type') === 'C') {
            return this.data('description');
          } else {
            desc = this.data("label");
            return desc + " - " + this.data('description');
          }

        },
        show: {
          event: 'mouseover'
        },
        hide: {
          event: 'mouseout'
        },
        position: {
            my: 'top center',
            at: 'bottom center'
        },
        style: {
            classes: 'qtip-bootstrap',
            tip: {
                width: 16,
                height: 8
            }
        }
    });
  }

}
// Controls

$(".tool").on("click", function() {

  var level = cy.zoom();
  var action = $(this).attr("id");
  switch (action) {
    case "zoomIn":
      level += 0.2;
      cy.zoom({
        level: level
      });
      break;
    case "zoomOut":
      level -= 0.2;
      cy.zoom({
        level: level
      });
      break;
    case "panRight":
      cy.panBy({
          x: -100,
          y: 0
      });
      break;
    case "panDown":
      cy.panBy({
          x: 0,
          y: -100
      });
      break;
    case "panLeft":
      cy.panBy({
          x: 100,
          y: 0
      });
      break;
    case "panUp":
      cy.panBy({
          x: 0,
          y: 100
      });
      break;
    case "resize":
      cy.zoom({
        level: 1
      });
      cy.center();
      break;
  }
})
// EVENTS

$("#mini-graphic").on("mouseenter", function() {
  $("#overlay").css("display", "block");
});

$("#mini-graphic").on("mouseleave", function() {
  $("#overlay").css("display", "none");
});

// Open PopUp Graph
$("#overlay .btn").on("click", function() {
  $("#impactGraphic-content").dialog({
      resizable: false,
      width: '90%',
      modal: true,
      height: $(window).height() * 0.80,
      show: {
          effect: "blind",
          duration: 500
      },
      hide: {
          effect: "fadeOut",
          duration: 500
      },
      open: function(event,ui) {
        var url = baseURL + "/impactPathwayGraphByProgram.do";
        var crpProgram = $("input[name='programID']").val();
        var data = {
          programID: crpProgram
        }
        ajaxService(url, data, "impactGraphic", true, true, 'breadthfirst', false);
      }
  });

});

$("#changeGraph .btn").on("click", function() {
  console.log("holi");
  if($(this).hasClass("currentGraph")) {
    var url = baseURL + "/impactPathway/impactPathwayFullGraph.do";
    var dataFull = {
      crpID: currentCrpID
    }
    ajaxService(url, dataFull, "impactGraphic", true, true, 'concentric', false);
    $(this).html("Show section graph");
    $(this).addClass("fullGraph");
    $(this).removeClass("currentGraph");
  } else {
    $(this).html("Show full graph");
    var url = baseURL + "/impactPathway/impactPathwayGraph.do";
    ajaxService(url, data, "impactGraphic", true, true, 'breadthfirst', false);
    $(this).removeClass("fullGraph");
    $(this).addClass("currentGraph");
  }

});

// Functions

// end nodeSelected function

function ajaxService(url,data,contentGraph,panningEnable,inPopUp,nameLayout,tooltip) {
  $.ajax({
      url: url,
      type: 'GET',
      dataType: "json",
      data: data
  }).done(function(m) {
    // Updated
    showHelpText();
    setViewMore();
    // /////////
    console.log("done");
    var nodes = m.elements.nodes;
    var count = {
        SO: 0,
        A: 0,
        P: 0,
        T: 0,
        I: 0,
        OC: 0,
        OP: 0
    };
    var totalWidth = {
        SO: 0,
        A: 0,
        P: 0,
        T: 0,
        I: 0,
        OC: 0,
        OP: 0
    };
    var nodeWidth = 110;
    var nodeMargin = 20;

    // For to count and set position
    for(var i = 0; i < nodes.length; i++) {
      if(nodes[i].data.type == "SO") {
        count.SO++;
      } else if(nodes[i].data.type == "A") {
        count.A++;
      } else if(nodes[i].data.type == "P") {
        count.P++;
      } else if(nodes[i].data.type == "T") {
        count.T++;
      } else if(nodes[i].data.type == "I") {
        count.I++;
      } else if(nodes[i].data.type == "OC") {
        count.OC++;
      } else if(nodes[i].data.type == "OP") {
        count.OP++;
      }
    }

    totalWidth.SO = count.SO * (nodeWidth + nodeMargin);
    totalWidth.A = count.A * (nodeWidth + nodeMargin);
    totalWidth.P = count.P * (nodeWidth + nodeMargin);
    totalWidth.T = count.T * (nodeWidth + nodeMargin);
    totalWidth.I = count.I * (nodeWidth + nodeMargin);
    totalWidth.OC = count.OC * (nodeWidth + nodeMargin);
    totalWidth.OP = count.OP * (nodeWidth + nodeMargin);
    // totalWidth.KO = (count.KO * (nodeWidth + nodeMargin)) + totalWidth.CoA;

    console.log(count.T);
    console.log(count.OC);
    console.log(count.OP);
    
    console.log(totalWidth.T);
    console.log(totalWidth.OC);
    console.log(totalWidth.OP);

    var widthTest = 0;
    if(totalWidth.T > totalWidth.OC) {
      widthTest = totalWidth.T;
    } else {
      widthTest = totalWidth.OC;
    }

    if(widthTest > totalWidth.OP) {
      widthTest;
    } else {
      widthTest = totalWidth.OP;
    }

    console.log(widthTest);

    var move = {
        SO: -(totalWidth.SO / 2),
        A: -(totalWidth.A / 2),
        P: -(totalWidth.P / 2),
        T: -(widthTest/ 2),
        I: -(totalWidth.I / 2),
        OC: -(widthTest / 2),
        OP: -(widthTest / 2)
    };

    for(var i = 0; i < nodes.length; i++) {
      if(nodes[i].data.type == "SO") {
        move.SO = (move.SO + (nodeWidth + nodeMargin));
        nodes[i].position = {
            x: move.SO,
            y: 0
        };
      } else if(nodes[i].data.type == "A") {
        move.A = (move.A + (nodeWidth + nodeMargin));
        nodes[i].position = {
            x: move.A,
            y: 200
        };
      } else if(nodes[i].data.type == "P") {
        move.P = (move.P + (nodeWidth + nodeMargin));
        nodes[i].position = {
            x: move.P,
            y: 200
        };
      } else if(nodes[i].data.type == "I") {
        move.I = (move.I + (nodeWidth + nodeMargin + 20));
        // console.log(move.KO);
        nodes[i].position = {
            x: move.I,
            y: 200
        };
      } else if(nodes[i].data.type == "T") {
        if(nodes[i + 1] && nodes[i + 1].data.type == "OC") {
        } else {
          move.OC = (move.OC + (nodeWidth + nodeMargin + 20));
        }
        // console.log(move.KO);
        nodes[i].position = {
            x: move.OC,
            y: 300
        };
      } else if(nodes[i].data.type == "OC") {
        if(nodes[i + 1] && nodes[i + 1].data.type == "OP") {
        } else {
          move.OP = (move.OP + (nodeWidth + nodeMargin + 20));
        }
        move.OC = (move.OC + (nodeWidth + nodeMargin + 20));
        // console.log(move.KO);
        nodes[i].position = {
            x: move.OC,
            y: 300
        };
      } else if(nodes[i].data.type == "OP") {
        move.OP = (move.OP + (nodeWidth + nodeMargin + 20));
        if(nodes[i + 1] && nodes[i + 1].data.type == "OC" || nodes[i + 1].data.type == "T") {
          move.OC = (move.OP );
        } else {
          
        }
        
        // console.log(move.KO);
        nodes[i].position = {
            x: move.OP,
            y: 400
        };
      }/*
         * else if(nodes[i].data.type == "CoA") { if(nodes[i + 1] && nodes[i + 1].data.type == "KO") { move.KO; } else {
         * move.KO = (move.KO + (nodeWidth + nodeMargin + 20)); } // console.log(move.KO); nodes[i].position = { x:
         * move.KO, y: 400 }; }
         */
    }

    createGraphic(m.elements, contentGraph, panningEnable, inPopUp, 'breadthfirst', tooltip);
  });
}

function showHelpText() {
  $('.helpMessage').show();
  $('.helpMessage').addClass('animated flipInX');
}