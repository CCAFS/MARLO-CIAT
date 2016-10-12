[#ftl]
[#assign title = "Impact Pathway - Outcomes" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${crpProgramID}" /]
[#assign pageLibs = ["select2","cytoscape","cytoscape-panzoom"] /]
[#assign customJS = [ "${baseUrl}/js/impactPathway/programSubmit.js", "${baseUrl}/js/impactPathway/outcomes.js", "${baseUrl}/js/global/autoSave.js", "${baseUrl}/js/global/impactGraphic.js" ] /]
[#assign customCSS = [ "${baseUrl}/css/impactPathway/outcomes.css","${baseUrl}/css/global/impactGraphic.css" ] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "outcomes" /]


[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"outcomes"},
  {"label":"outcomes", "nameSpace":"", "action":""}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]

<section class="marlo-content">
  <div class="container"> 
    [#--if programs?has_content--]
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        
       
    </div>
    [#--else--]
      <p class="text-center borderBox">There is not flagships added</p>
    [#--/#if--]
  </div>
</div>
  
  
  
</section>