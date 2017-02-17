[#ftl]
[#assign title = "MiLE Projects" /]
[#assign currentSectionString = "${actionName?replace('/','-')}" /]
[#assign pageLibs = ["datatables.net", "datatables.net-bs"] /]
[#assign customJS = ["${baseUrl}/js/projects/projectsList.js" ] /]
[#assign customCSS = ["${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "projects" /]
[#assign currentStage = (filterBy)!"all" /]


[#assign breadCrumb = [
  {"label":"projectsList", "nameSpace":"/monitoring", "action":"${(centerSession)!}/projectList"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/macros/projectsListTemplate.ftl" as projectList /]

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
      [#-- Projects List (My Projects) --]
      <h3 class="headTitle text-center">${selectedProgram.name}- Projects</h3>
      <div class="loadingBlock"></div>
      <div style="display:none">[@projectList.projectsList projects=myProjects canValidate=true canEdit=true namespace="/projects" defaultAction="${(centerSession)!}/description" /]</div>
  
      [#-- Section Buttons --]
      <div class="buttons">
        <div class="buttons-content">
        
          <a class="addButton" href="[@s.url action=''/]">[@s.text name="Add project" /]</a>
          <div class="clearfix"></div>
        </div>
      </div>

      
      <div class="clearfix"></div>
    </div>
    
  </article>
</section>
[@customForm.confirmJustification action="deleteProject.do" namespace="/${currentSection}" title="Remove Project" /]


[#include "/WEB-INF/global/pages/footer.ftl"]
