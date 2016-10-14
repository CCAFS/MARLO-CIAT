[#ftl]
[#assign title = "Impact Pathway - Research Topics" /]
[#assign currentSectionString = "program-${actionName?replace('/','-')}-${programID}" /]
[#assign pageLibs = ["cytoscape","cytoscape-panzoom","select2"] /]
[#assign customJS = ["${baseUrl}/js/global/usersManagement.js"] /]
[#assign customCSS = [ "${baseUrl}/css/impactPathway/clusterActivities.css" ] /]
[#assign currentSection = "impactPathway" /]
[#assign currentStage = "clusterActivities" /]

[#assign breadCrumb = [
  {"label":"impactPathway", "nameSpace":"", "action":"topics"}
]/]
[#assign leadersName = "leaders"/]
[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/utils.ftl" as utils /]
[#--  marlo cluster of activities--]
<section class="marlo-content">
  <div class="container"> 
    [#if researchAreas?has_content]
    <div class="row">
      <div class="col-md-3">
        [#include "/WEB-INF/views/impactPathway/menu-impactPathway.ftl" /]
      </div>
      <div class="col-md-9">
        [#-- Section Messages --]
        [#include "/WEB-INF/views/impactPathway/messages-impactPathway.ftl" /]
        
        [#-- Research Areas --]
        <ul id="liaisonInstitutions" class="horizontalSubMenu text-left">
          [#list researchAreas as areas]
            [#assign isActive = (areas.id == areaID)/]
            
            <li class="${isActive?string('active','')}">
              <a href="[@s.url][@s.param name ="crpProgramID"]${areas.id}[/@s.param][@s.param name ="edit"]true[/@s.param][/@s.url]">${areas.acronym}</a>
              
              <ul>
                [#list areas.researchPrograms as programs]                
                <li>${programs.name}</li>
                [/#list]
              </ul>           
            </li>
          [/#list]
        </ul>
                
        [@s.form action=actionName enctype="multipart/form-data" ]     
        
        [/@s.form]
      </div>
    </div>
    [#else]
     <p class="text-center borderBox inf">Program impacts are not available in for the selected research area</p>
    [/#if]
  </div>
</section>
[#-- Search users Interface --]
[#import "/WEB-INF/global/macros/usersPopup.ftl" as usersForm/]
[@usersForm.searchUsers/]

[#include "/WEB-INF/global/pages/footer.ftl" /]


