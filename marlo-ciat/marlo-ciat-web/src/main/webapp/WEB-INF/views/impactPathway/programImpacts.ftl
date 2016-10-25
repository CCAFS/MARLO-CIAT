[#ftl]
[#assign title = "Impact Pathway - Program Impacts" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["cytoscape","cytoscape-panzoom","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js", "${baseUrl}/js/impactPathway/programSubmit.js", "${baseUrl}/js/impactPathway/clusterActivities.js", "${baseUrl}/js/global/autoSave.js", "${baseUrl}/js/global/impactGraphic.js"] /]
[#assign customCSS = [ "${baseUrl}/css/impactPathway/clusterActivities.css","${baseUrl}/css/global/impactGraphic.css" ] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "programImpacts" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"programimpacts"}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        [#-- Impact pathway sub menu --]
        [#include "/WEB-INF/views/impactPathway/submenu-impactPathway.ftl" /]
        
        [@s.form action=actionName enctype="multipart/form-data" ]
          
          
        [/@s.form]
      </div>
    </div>
  </div>
</section>
[#-- Search users Interface --]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[@usersForm.searchUsers/]

[#include "/WEB-INF/global/pages/footer.ftl" /]


