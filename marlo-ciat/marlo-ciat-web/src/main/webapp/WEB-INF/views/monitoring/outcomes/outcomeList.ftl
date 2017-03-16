[#ftl]
[#assign title = "MiLE outcomes monitoring" /]
[#assign currentSectionString = "${actionName?replace('/','-')}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs"] /]
[#assign customJS = ["${baseUrl}/js/outcomes/outcomesList.js" ] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "projects" /]


[#assign breadCrumb = [
  {"label":"outcomesList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/outcomes"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/outcomeListTemplate.ftl" as outcomesListMonitoring /]

<div class="container helpText viewMore-block">
  <div class="helpMessage infoText">
    [#--  <img class="col-md-2" src="${baseUrl}/images/global/icon-help.jpg" />--]
    <p class="col-md-10"> [@s.text name="projectsList.help"][/@s.text] </p>
  </div> 
  <div style="display:none" class="viewMore closed"></div>
</div>

<section class="container">
  <article class="row" id="mainInformation">
    <div class="col-md-12">
      [#include "/WEB-INF/views/monitoring/project/submenu-project.ftl" /]
      [#-- Outcomes List --]
      <h3 class="headTitle text-center">${selectedProgram.name}- Outcomes</h3>
      <div class="loadingBlock"></div>
      <div style="display:none">[@outcomesListMonitoring.outcomesListMonitoring outcomes=outcome canValidate=true canEdit=true namespace="/monitoring" defaultAction="${(centerSession)!}/outcomes" /]</div>
      <div class="clearfix"></div>
    </div>
    
  </article>
</section>


[#include "/WEB-INF/global/pages/footer.ftl"]
